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
package org.apache.olingo.client.core.edm.xml.annotation;

import java.io.IOException;

import org.apache.olingo.client.core.edm.xml.AbstractEdmDeserializer;
import org.apache.olingo.client.core.edm.xml.AnnotationImpl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;

public class RecordDeserializer extends AbstractEdmDeserializer<RecordImpl> {

  @Override
  protected RecordImpl doDeserialize(final JsonParser jp, final DeserializationContext ctxt)
          throws IOException, JsonProcessingException {

    final RecordImpl record = new RecordImpl();

    for (; jp.getCurrentToken() != JsonToken.END_OBJECT; jp.nextToken()) {
      final JsonToken token = jp.getCurrentToken();
      if (token == JsonToken.FIELD_NAME) {
        if ("Type".equals(jp.getCurrentName())) {
          record.setType(jp.nextTextValue());
        } else if ("Annotation".equals(jp.getCurrentName())) {
          record.getAnnotations().add(jp.readValueAs(AnnotationImpl.class));
        } else {
          record.getPropertyValues().add(jp.readValueAs(PropertyValueImpl.class));
        }
      }
    }

    return record;
  }

}
