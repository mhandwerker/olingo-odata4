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
package org.apache.olingo.server.core.testutil;

import java.net.InetSocketAddress;
import java.net.URI;

import javax.servlet.http.HttpServlet;

import org.apache.olingo.commons.api.ODataRuntimeException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class TestServer {

  private static final String DEFAULT_SCHEME = "http";
  private static final String DEFAULT_HOST = "localhost";
  private static final String DEFAULT_PATH = "/test";

  private Server server;
  private URI endpoint; // = URI.create("http://localhost:19080/test"); // no slash at the end !!!

  public URI getEndpoint() {
    return endpoint;
  }

  public void startServer() {
    try {
      final ServletContextHandler contextHandler = createContextHandler();
      final InetSocketAddress isa = new InetSocketAddress(DEFAULT_HOST, 512);
      server = new Server(isa);

      server.setHandler(contextHandler);
      server.start();
      endpoint = new URI(DEFAULT_SCHEME, null, DEFAULT_HOST, isa.getPort(), "/abc" + DEFAULT_PATH, null, null);
    } catch (final Exception e) {
      throw new ODataRuntimeException(e);
    }
  }

  private ServletContextHandler createContextHandler() throws Exception {
    final HttpServlet httpServlet = new ODataTestServlet();
    ServletHolder odataServletHolder = new ServletHolder(httpServlet);

    final ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.setContextPath("/abc");
    contextHandler.addServlet(odataServletHolder, DEFAULT_PATH + "/*");
    return contextHandler;
  }

  public void stopServer() {
    try {
      if (server != null) {
        server.stop();
      }
    } catch (final Exception e) {
      throw new ODataRuntimeException(e);
    }
  }

}
