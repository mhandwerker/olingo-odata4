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
package org.apache.olingo.fit.proxy.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.ext.proxy.EntityContainerFactory;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.InMemoryEntities;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.Person;
import org.junit.Test;

public class KeyAsSegmentTestITCase extends AbstractTestITCase {

  private InMemoryEntities getContainer() {
    final EntityContainerFactory ecf = EntityContainerFactory.getV3(testKeyAsSegmentServiceRootURL);
    ecf.getConfiguration().setKeyAsSegment(true);
    ecf.getConfiguration().setDefaultBatchAcceptFormat(ContentType.APPLICATION_OCTET_STREAM);
    return ecf.getEntityContainer(InMemoryEntities.class);
  }

  @Test
  public void read() {
    assertNotNull(getContainer().getAccounts().get(101));
  }

  @Test
  public void createAndDelete() {
    createAndDeleteOrder(getContainer());
  }

  @Test
  public void update() {
    Person person = getContainer().getPeople().get(5);
    person.setMiddleName("middleN");

    container.flush();

    person = getContainer().getPeople().get(5);
    assertEquals("middleN", person.getMiddleName());
  }

}
