/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.client.core.communication.request.retrieve;

import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.olingo.client.api.CommonODataClient;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.communication.request.retrieve.XMLMetadataRequest;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.edm.xml.Annotation;
import org.apache.olingo.client.api.edm.xml.Annotations;
import org.apache.olingo.client.api.edm.xml.Include;
import org.apache.olingo.client.api.edm.xml.IncludeAnnotations;
import org.apache.olingo.client.api.edm.xml.Reference;
import org.apache.olingo.client.api.edm.xml.Schema;
import org.apache.olingo.client.api.edm.xml.XMLMetadata;
import org.apache.olingo.client.core.edm.xml.AbstractSchema;
import org.apache.olingo.client.core.edm.xml.AnnotationsImpl;
import org.apache.olingo.client.core.edm.xml.SchemaImpl;
import org.apache.olingo.commons.api.format.ODataFormat;

public class XMLMetadataRequestImpl
        extends AbstractMetadataRequestImpl<org.apache.olingo.client.api.edm.xml.XMLMetadata>
        implements XMLMetadataRequest {

  XMLMetadataRequestImpl(final ODataClient odataClient, final URI uri) {
    super(odataClient, uri);
  }

  @Override
  public ODataRetrieveResponse<org.apache.olingo.client.api.edm.xml.XMLMetadata> execute() {
    final SingleXMLMetadatRequestImpl rootReq = new SingleXMLMetadatRequestImpl((ODataClient) odataClient, uri, null);
    final ODataRetrieveResponse<XMLMetadata> rootRes = rootReq.execute();

    final XMLMetadataResponseImpl response =
            new XMLMetadataResponseImpl(odataClient, httpClient, rootReq.getHttpResponse(), rootRes.getBody());

    // process external references
    for (Reference reference : rootRes.getBody().getReferences()) {
      final SingleXMLMetadatRequestImpl includeReq = new SingleXMLMetadatRequestImpl(
              (ODataClient) odataClient,
              odataClient.newURIBuilder(uri.resolve(reference.getUri()).toASCIIString()).build(),
              uri);
      final XMLMetadata includeMetadata = includeReq.execute().getBody();

      // edmx:Include
      for (Include include : reference.getIncludes()) {
        final Schema includedSchema = includeMetadata.getSchema(include.getNamespace());
        if (includedSchema != null) {
          response.getBody().getSchemas().add(includedSchema);
          if (StringUtils.isNotBlank(include.getAlias())) {
            ((AbstractSchema) includedSchema).setAlias(include.getAlias());
          }
        }
      }

      // edmx:IncludeAnnotations
      for (IncludeAnnotations include : reference.getIncludeAnnotations()) {
        for (Schema schema : includeMetadata.getSchemas()) {
          // create empty schema that will be fed with edm:Annotations that match the criteria in IncludeAnnotations
          final SchemaImpl forInclusion = new SchemaImpl();
          forInclusion.setNamespace(schema.getNamespace());
          forInclusion.setAlias(schema.getAlias());

          // process all edm:Annotations in each schema of the included document
          for (Annotations annotationGroup : ((SchemaImpl) schema).getAnnotationGroups()) {
            // take into account only when (TargetNamespace was either not provided or matches) and
            // (Qualifier was either not provided or matches)
            if ((StringUtils.isBlank(include.getTargetNamespace())
                    || include.getTargetNamespace().equals(
                            StringUtils.substringBeforeLast(annotationGroup.getTarget(), ".")))
                    && (StringUtils.isBlank(include.getQualifier())
                    || include.getQualifier().equals(annotationGroup.getQualifier()))) {

              final AnnotationsImpl toBeIncluded = new AnnotationsImpl();
              toBeIncluded.setTarget(annotationGroup.getTarget());
              toBeIncluded.setQualifier(annotationGroup.getQualifier());
              // only import annotations with terms matching the given TermNamespace
              for (Annotation annotation : annotationGroup.getAnnotations()) {
                if (include.getTermNamespace().equals(StringUtils.substringBeforeLast(annotation.getTerm(), "."))) {
                  toBeIncluded.getAnnotations().add(annotation);
                }
              }
              forInclusion.getAnnotationGroups().add(toBeIncluded);
            }
          }

          if (!forInclusion.getAnnotationGroups().isEmpty()) {
            response.getBody().getSchemas().add(forInclusion);
          }
        }
      }
    }

    return response;
  }

  private class SingleXMLMetadatRequestImpl extends AbstractMetadataRequestImpl<XMLMetadata> {

    private final URI parentURI;
    private HttpResponse httpResponse;

    public SingleXMLMetadatRequestImpl(final ODataClient odataClient, final URI uri, final URI parent) {
      super(odataClient, uri);
      parentURI = parent;
    }

    public HttpResponse getHttpResponse() {
      return httpResponse;
    }

    /** Referenced document's URIs must only have the same scheme, host, and port as the
     *  main metadata document's URI but don't have to start with the service root
     *  as all other OData request URIs. */
    @Override
    protected void checkRequest(final CommonODataClient<?> odataClient, final HttpUriRequest request) {
      if (parentURI == null) {
        super.checkRequest(odataClient, request);
      } else {
        if (!parentURI.getScheme().equals(uri.getScheme())
            || !parentURI.getAuthority().equals(uri.getAuthority())) {
          throw new IllegalArgumentException(
              String.format("The referenced EDMX document has the URI '%s'"
                  + " where scheme, host, or port is different from the main metadata document URI '%s'.",
                  uri.toASCIIString(), parentURI.toASCIIString()));
        }
      }
    }

    @Override
    public ODataRetrieveResponse<XMLMetadata> execute() {
      httpResponse = doExecute();
      return new AbstractODataRetrieveResponse(odataClient, httpClient, httpResponse) {

        private XMLMetadata metadata = null;

        @Override
        public XMLMetadata getBody() {
          if (metadata == null) {
            try {
              metadata = ((ODataClient) odataClient).getDeserializer(ODataFormat.XML).toMetadata(getRawResponse());
            } finally {
              this.close();
            }
          }
          return metadata;
        }
      };
    }
  }

  private class XMLMetadataResponseImpl extends AbstractODataRetrieveResponse {

    private final XMLMetadata metadata;

    private XMLMetadataResponseImpl(final CommonODataClient<?> odataClient, final HttpClient httpClient,
            final HttpResponse res, final XMLMetadata metadata) {

      super(odataClient, httpClient, null);
      this.metadata = metadata;

      statusCode = res.getStatusLine().getStatusCode();
      statusMessage = res.getStatusLine().getReasonPhrase();

      hasBeenInitialized = true;
    }

    @Override
    public XMLMetadata getBody() {
      return metadata;
    }
  }

}
