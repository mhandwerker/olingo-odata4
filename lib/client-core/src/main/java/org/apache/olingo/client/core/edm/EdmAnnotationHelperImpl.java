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
package org.apache.olingo.client.core.edm;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.client.api.edm.xml.Annotatable;
import org.apache.olingo.client.api.edm.xml.Annotation;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.EdmAnnotation;
import org.apache.olingo.commons.api.edm.EdmTerm;
import org.apache.olingo.commons.core.edm.EdmAnnotationHelper;

public class EdmAnnotationHelperImpl implements EdmAnnotationHelper {

  private final Edm edm;

  private final Annotatable annotatable;

  private List<EdmAnnotation> annotations;

  public EdmAnnotationHelperImpl(final Edm edm, final Annotatable annotatable) {
    this.edm = edm;
    this.annotatable = annotatable;
  }

  @Override
  public EdmAnnotation getAnnotation(final EdmTerm term) {
    EdmAnnotation result = null;
    for (EdmAnnotation annotation : getAnnotations()) {
      if (term.getFullQualifiedName().equals(annotation.getTerm().getFullQualifiedName())) {
        result = annotation;
      }
    }

    return result;
  }

  @Override
  public List<EdmAnnotation> getAnnotations() {
    if (annotations == null) {
      annotations = new ArrayList<EdmAnnotation>();
      for (Annotation annotation : annotatable.getAnnotations()) {
        annotations.add(new EdmAnnotationImpl(edm, annotation));
      }
    }
    return annotations;
  }

}
