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

import java.util.List;
import java.util.Map;

import org.apache.olingo.client.api.edm.xml.ComplexType;
import org.apache.olingo.client.api.edm.xml.Schema;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.EdmAnnotation;
import org.apache.olingo.commons.api.edm.EdmNavigationProperty;
import org.apache.olingo.commons.api.edm.EdmProperty;
import org.apache.olingo.commons.api.edm.EdmTerm;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.core.edm.AbstractEdmComplexType;
import org.apache.olingo.commons.core.edm.EdmAnnotationHelper;
import org.apache.olingo.commons.core.edm.EdmStructuredTypeHelper;
import org.apache.olingo.commons.core.edm.EdmTypeInfo;

public class EdmComplexTypeImpl extends AbstractEdmComplexType {

  private final EdmStructuredTypeHelper typeHelper;

  private EdmAnnotationHelper annotationHelper;

  public static EdmComplexTypeImpl getInstance(final Edm edm, final FullQualifiedName fqn,
          final List<? extends Schema> xmlSchemas, final ComplexType complexType) {

    FullQualifiedName baseTypeName = null;
    if (complexType instanceof org.apache.olingo.client.api.edm.xml.ComplexType) {
      final String baseType = ((org.apache.olingo.client.api.edm.xml.ComplexType) complexType).getBaseType();
      baseTypeName = baseType == null
              ? null : new EdmTypeInfo.Builder().setTypeExpression(baseType).build().getFullQualifiedName();
    }
    final EdmComplexTypeImpl instance = new EdmComplexTypeImpl(edm, fqn, baseTypeName, xmlSchemas, complexType);
    instance.baseType = instance.buildBaseType(baseTypeName);

    return instance;
  }

  private EdmComplexTypeImpl(final Edm edm, final FullQualifiedName fqn, final FullQualifiedName baseTypeName,
          final List<? extends Schema> xmlSchemas, final ComplexType complexType) {

    super(edm, fqn, baseTypeName);
    this.typeHelper = new EdmStructuredTypeHelperImpl(edm, getFullQualifiedName(), xmlSchemas, complexType);
    if (complexType instanceof org.apache.olingo.client.api.edm.xml.ComplexType) {
      this.annotationHelper = new EdmAnnotationHelperImpl(edm,
              (org.apache.olingo.client.api.edm.xml.ComplexType) complexType);
    }
  }

  @Override
  protected Map<String, EdmProperty> getProperties() {
    return typeHelper.getProperties();
  }

  @Override
  protected Map<String, EdmNavigationProperty> getNavigationProperties() {
    return typeHelper.getNavigationProperties();
  }

  @Override
  public boolean isOpenType() {
    return typeHelper.isOpenType();
  }

  @Override
  public boolean isAbstract() {
    return typeHelper.isAbstract();
  }

  @Override
  public EdmAnnotation getAnnotation(final EdmTerm term) {
    return annotationHelper == null ? null : annotationHelper.getAnnotation(term);
  }

  @Override
  public List<EdmAnnotation> getAnnotations() {
    return annotationHelper == null ? null : annotationHelper.getAnnotations();
  }

}
