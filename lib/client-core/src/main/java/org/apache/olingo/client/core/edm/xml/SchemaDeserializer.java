/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.client.core.edm.xml;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;

public class SchemaDeserializer extends AbstractEdmDeserializer<AbstractSchema> {

  @Override
  protected AbstractSchema doDeserialize(final JsonParser jp, final DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    final AbstractSchema schema = new org.apache.olingo.client.core.edm.xml.SchemaImpl();

    for (; jp.getCurrentToken() != JsonToken.END_OBJECT; jp.nextToken()) {
      final JsonToken token = jp.getCurrentToken();
      if (token == JsonToken.FIELD_NAME) {
        if ("Namespace".equals(jp.getCurrentName())) {
          schema.setNamespace(jp.nextTextValue());
        } else if ("Alias".equals(jp.getCurrentName())) {
          schema.setAlias(jp.nextTextValue());
        } else if ("ComplexType".equals(jp.getCurrentName())) {
          jp.nextToken();

          ((org.apache.olingo.client.core.edm.xml.SchemaImpl) schema).
              getComplexTypes().add(jp.readValueAs(
                  org.apache.olingo.client.core.edm.xml.ComplexTypeImpl.class));

        } else if ("EntityType".equals(jp.getCurrentName())) {
          jp.nextToken();

          ((org.apache.olingo.client.core.edm.xml.SchemaImpl) schema).
              getEntityTypes().add(jp.readValueAs(
                  org.apache.olingo.client.core.edm.xml.EntityTypeImpl.class));

        } else if ("EnumType".equals(jp.getCurrentName())) {
          jp.nextToken();

          ((org.apache.olingo.client.core.edm.xml.SchemaImpl) schema).
              getEnumTypes().add(jp.readValueAs(
                  org.apache.olingo.client.core.edm.xml.EnumTypeImpl.class));

        } else if ("EntityContainer".equals(jp.getCurrentName())) {
          jp.nextToken();

          org.apache.olingo.client.core.edm.xml.EntityContainerImpl entityContainer =
              jp.readValueAs(
                  org.apache.olingo.client.core.edm.xml.EntityContainerImpl.class);
          entityContainer.setDefaultEntityContainer(true);
          ((org.apache.olingo.client.core.edm.xml.SchemaImpl) schema).
              setEntityContainer(entityContainer);

        } else if ("Action".equals(jp.getCurrentName())) {
          jp.nextToken();
          ((org.apache.olingo.client.core.edm.xml.SchemaImpl) schema).getActions().
              add(jp.readValueAs(ActionImpl.class));
        } else if ("Function".equals(jp.getCurrentName())) {
          jp.nextToken();
          ((org.apache.olingo.client.core.edm.xml.SchemaImpl) schema).getFunctions().
              add(jp.readValueAs(FunctionImpl.class));
        } else if ("TypeDefinition".equals(jp.getCurrentName())) {
          jp.nextToken();
          ((org.apache.olingo.client.core.edm.xml.SchemaImpl) schema).
              getTypeDefinitions().add(jp.readValueAs(TypeDefinitionImpl.class));
        }
      } else if ("Annotations".equals(jp.getCurrentName())) {
        jp.nextToken();

        ((org.apache.olingo.client.core.edm.xml.SchemaImpl) schema).getAnnotationGroups().
            add(jp.readValueAs(org.apache.olingo.client.core.edm.xml.AnnotationsImpl.class));

      } else if ("Annotation".equals(jp.getCurrentName())) {
        jp.nextToken();
        ((org.apache.olingo.client.core.edm.xml.SchemaImpl) schema).getAnnotations().
            add(jp.readValueAs(AnnotationImpl.class));
      } else if ("Term".equals(jp.getCurrentName())) {
        jp.nextToken();
        ((org.apache.olingo.client.core.edm.xml.SchemaImpl) schema).getTerms().
            add(jp.readValueAs(TermImpl.class));
      }
    }

    return schema;
  }
}
