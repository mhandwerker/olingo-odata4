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
package org.apache.olingo.server.api.processor;

import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.ODataResponse;
import org.apache.olingo.server.api.uri.UriInfo;

/**
 * Processor interface for handling a $count
 */
public interface CountProcessor extends Processor {

  /**
   * Read entity data from persistency and puts serialized content and status into the response.
   *  @param request - OData request object containing raw http information.
   *  @param response - OData response object for collecting response data
   *  @param uriInfo - information of a parsed OData uri
   */
  void readCount(ODataRequest request, ODataResponse response, UriInfo uriInfo);
  
}
