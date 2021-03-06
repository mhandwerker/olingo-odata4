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
package org.apache.olingo.client.core;

import org.apache.olingo.client.api.CommonODataClient;
import org.apache.olingo.commons.api.format.ODataFormat;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;

public abstract class AbstractTest {

  protected static org.apache.olingo.client.api.ODataClient v4Client;

  protected abstract CommonODataClient<?> getClient();

  @BeforeClass
  public static void setUp() {
    XMLUnit.setIgnoreComments(true);
    XMLUnit.setIgnoreAttributeOrder(true);
    XMLUnit.setIgnoreWhitespace(true);
    XMLUnit.setNormalizeWhitespace(true);
    XMLUnit.setCompareUnmatched(false);
  }

  @BeforeClass
  public static void setClientInstances() {
    v4Client = ODataClientFactory.getV4();
  }

  protected String getSuffix(final ODataFormat format) {
    return format == ODataFormat.ATOM || format == ODataFormat.XML ? "xml" : "json";
  }
}
