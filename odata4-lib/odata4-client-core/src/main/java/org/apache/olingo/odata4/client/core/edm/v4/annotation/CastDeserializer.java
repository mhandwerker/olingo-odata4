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
package org.apache.olingo.odata4.client.core.edm.v4.annotation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.math.BigInteger;
import org.apache.olingo.odata4.client.core.op.impl.AbstractEdmDeserializer;
import org.apache.olingo.odata4.client.core.edm.v4.AnnotationImpl;

public class CastDeserializer extends AbstractEdmDeserializer<Cast> {

  @Override
  protected Cast doDeserialize(final JsonParser jp, final DeserializationContext ctxt)
          throws IOException, JsonProcessingException {

    final Cast cast = new Cast();

    for (; jp.getCurrentToken() != JsonToken.END_OBJECT; jp.nextToken()) {
      final JsonToken token = jp.getCurrentToken();
      if (token == JsonToken.FIELD_NAME) {
        if ("Type".equals(jp.getCurrentName())) {
          cast.setType(jp.nextTextValue());
        } else if ("Annotation".equals(jp.getCurrentName())) {
          cast.setAnnotation(jp.readValueAs( AnnotationImpl.class));
        } else if ("MaxLength".equals(jp.getCurrentName())) {
          cast.setMaxLength(jp.nextTextValue());
        } else if ("Precision".equals(jp.getCurrentName())) {
          cast.setPrecision(BigInteger.valueOf(jp.nextLongValue(0L)));
        } else if ("Scale".equals(jp.getCurrentName())) {
          cast.setScale(BigInteger.valueOf(jp.nextLongValue(0L)));
        } else if ("SRID".equals(jp.getCurrentName())) {
          cast.setSrid(jp.nextTextValue());
        } else {
          cast.setValue(jp.readValueAs( DynExprConstruct.class));
        }
      }
    }

    return cast;
  }

}
