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

import org.apache.commons.lang3.BooleanUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;

public class EntityTypeDeserializer extends AbstractEdmDeserializer<AbstractEntityType> {

  @Override
  protected AbstractEntityType doDeserialize(final JsonParser jp, final DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    final AbstractEntityType entityType = new org.apache.olingo.client.core.edm.xml.EntityTypeImpl();

    for (; jp.getCurrentToken() != JsonToken.END_OBJECT; jp.nextToken()) {
      final JsonToken token = jp.getCurrentToken();
      if (token == JsonToken.FIELD_NAME) {
        if ("Name".equals(jp.getCurrentName())) {
          entityType.setName(jp.nextTextValue());
        } else if ("Abstract".equals(jp.getCurrentName())) {
          entityType.setAbstractEntityType(BooleanUtils.toBoolean(jp.nextTextValue()));
        } else if ("BaseType".equals(jp.getCurrentName())) {
          entityType.setBaseType(jp.nextTextValue());
        } else if ("OpenType".equals(jp.getCurrentName())) {
          entityType.setOpenType(BooleanUtils.toBoolean(jp.nextTextValue()));
        } else if ("HasStream".equals(jp.getCurrentName())) {
          entityType.setHasStream(BooleanUtils.toBoolean(jp.nextTextValue()));
        } else if ("Key".equals(jp.getCurrentName())) {
          jp.nextToken();
          entityType.setKey(jp.readValueAs(EntityKeyImpl.class));
        } else if ("Property".equals(jp.getCurrentName())) {
          jp.nextToken();
          ((org.apache.olingo.client.core.edm.xml.EntityTypeImpl) entityType).
              getProperties().add(jp.readValueAs(org.apache.olingo.client.core.edm.xml.PropertyImpl.class));
        } else if ("NavigationProperty".equals(jp.getCurrentName())) {
          jp.nextToken();
          ((org.apache.olingo.client.core.edm.xml.EntityTypeImpl) entityType).
              getNavigationProperties().add(jp.readValueAs(
                  org.apache.olingo.client.core.edm.xml.NavigationPropertyImpl.class));
        } else if ("Annotation".equals(jp.getCurrentName())) {
          jp.nextToken();
          ((org.apache.olingo.client.core.edm.xml.EntityTypeImpl) entityType).getAnnotations().
              add(jp.readValueAs(AnnotationImpl.class));
        }
      }
    }

    return entityType;
  }
}
