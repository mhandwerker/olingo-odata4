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
package org.apache.olingo.commons.core.data;

import org.apache.olingo.commons.api.data.DeletedEntity;

import java.net.URI;

public class DeletedEntityImpl extends AbstractAnnotatedObject implements DeletedEntity {

  private URI id;
  private Reason reason;

  @Override
  public URI getId() {
    return id;
  }

  public void setId(final URI id) {
    this.id = id;
  }

  @Override
  public Reason getReason() {
    return reason;
  }

  public void setReason(final Reason reason) {
    this.reason = reason;
  }

}
