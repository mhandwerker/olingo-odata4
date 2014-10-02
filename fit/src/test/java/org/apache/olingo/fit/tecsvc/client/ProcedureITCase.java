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
package org.apache.olingo.fit.tecsvc.client;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import java.util.Collections;

import org.apache.commons.io.IOUtils;
import org.apache.olingo.client.api.CommonODataClient;
import org.apache.olingo.client.api.communication.request.invoke.ODataInvokeRequest;
import org.apache.olingo.client.api.communication.request.invoke.ODataNoContent;
import org.apache.olingo.client.api.communication.request.retrieve.ODataPropertyRequest;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.v4.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.domain.CommonODataProperty;
import org.apache.olingo.commons.api.domain.ODataPrimitiveValue;
import org.apache.olingo.commons.api.domain.ODataValue;
import org.apache.olingo.commons.api.domain.v4.ODataProperty;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.format.ODataFormat;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.fit.AbstractBaseTestITCase;
import org.apache.olingo.fit.tecsvc.TecSvcConst;
import org.junit.Ignore;
import org.junit.Test;

public class ProcedureITCase  extends AbstractBaseTestITCase {

  private static final String SERVICE_URI = TecSvcConst.BASE_URI;
  
  @Override
  protected CommonODataClient<?> getClient() {
    ODataClient odata = ODataClientFactory.getV4();
    odata.getConfiguration().setDefaultPubFormat(ODataFormat.JSON);
    return odata;
  }  

  @Test // primitive return
  public void executeFINRTInt16() {
    final ODataPropertyRequest<CommonODataProperty> request = getClient().getRetrieveRequestFactory()
        .getPropertyRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendOperationCallSegment("FINRTInt16")
            .build());
    
    assertNotNull(request);

    final ODataRetrieveResponse<CommonODataProperty> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());
    assertThat(response.getContentType(), containsString(ContentType.APPLICATION_JSON.toContentTypeString()));

    final CommonODataProperty property = response.getBody();
    assertNotNull(property);
    assertEquals(5, property.getPrimitiveValue().toValue());
  }
  
  @Test
  public void executeFINRTInt16Raw() throws Exception {
    final ODataPropertyRequest<CommonODataProperty> request = getClient().getRetrieveRequestFactory()
        .getPropertyRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendOperationCallSegment("FINRTInt16")
            .build());
    
    assertNotNull(request);

    final ODataRetrieveResponse<CommonODataProperty> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());
    assertThat(response.getContentType(), containsString(ContentType.APPLICATION_JSON.toContentTypeString()));

    assertEquals("{\"@odata.context\":\"$metadata#Edm.Int16\",\"value\":5}", 
        IOUtils.toString(response.getRawResponse(), "UTF-8"));
  }
  
  @Test // collection return
  public void executeUFCRTCollString() throws Exception {
    final ODataPropertyRequest<CommonODataProperty> request = getClient().getRetrieveRequestFactory()
        .getPropertyRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendOperationCallSegment("FICRTCollString")
            .build());
    
    assertNotNull(request);

    final ODataRetrieveResponse<CommonODataProperty> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());
    assertThat(response.getContentType(), containsString(ContentType.APPLICATION_JSON.toContentTypeString()));

    assertEquals("{\"@odata.context\":\"$metadata#Collection(Edm.String)\",\"value\":[\"dummy1\",\"dummy2\"]}", 
        IOUtils.toString(response.getRawResponse(), "UTF-8"));
  }
  
  @Test  // complex return 
  public void executeUFCRTCTTwoPrimParam() throws Exception {
    final ODataPropertyRequest<CommonODataProperty> request = getClient().getRetrieveRequestFactory()
        .getPropertyRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendOperationCallSegment("FICRTCTTwoPrimParam(ParameterString='param1',ParameterInt16=2)")
            .build());
    
    assertNotNull(request);

    final ODataRetrieveResponse<CommonODataProperty> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());
    assertThat(response.getContentType(), containsString(ContentType.APPLICATION_JSON.toContentTypeString()));
    String expected = 
        "{\"@odata.context\":\"$metadata#olingo.odata.test1.CTTwoPrim\","+
        "\"PropertyInt16\":2,"+
        "\"PropertyString\":\"param1\""+
        "}";
    assertEquals(expected, IOUtils.toString(response.getRawResponse(), "UTF-8"));
  }
  
  @Test // complex collection
  public void executeUFCRTCollCTTwoPrimParam() throws Exception {
    final ODataPropertyRequest<CommonODataProperty> request = getClient().getRetrieveRequestFactory()
        .getPropertyRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendOperationCallSegment("FICRTCollCTTwoPrimParam(ParameterString='param1',ParameterInt16=2)")
            .build());
    
    assertNotNull(request);

    final ODataRetrieveResponse<CommonODataProperty> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());
    assertThat(response.getContentType(), containsString(ContentType.APPLICATION_JSON.toContentTypeString()));
    String expected = 
        "{\"@odata.context\":\"$metadata#Collection(olingo.odata.test1.CTTwoPrim)\","+
        "\"value\":[{" +
        "\"PropertyInt16\":2,"+
        "\"PropertyString\":\"param1\"}"+
        ",{" +
        "\"PropertyInt16\":2,"+
        "\"PropertyString\":\"param1\"}"+
        "]}";
    assertEquals(expected, IOUtils.toString(response.getRawResponse(), "UTF-8"));
  }
  
  @Test // Entity type return 
  public void executeUFCRTETAllPrimTwoParam() throws Exception {
    final ODataPropertyRequest<CommonODataProperty> request = getClient().getRetrieveRequestFactory()
        .getPropertyRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendOperationCallSegment("FICRTETAllPrimTwoParam(ParameterString='param1',ParameterInt16=2)")
            .build());
    
    assertNotNull(request);

    final ODataRetrieveResponse<CommonODataProperty> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());
    assertThat(response.getContentType(), containsString(ContentType.APPLICATION_JSON.toContentTypeString()));
    String expected = 
        "{\"@odata.context\":\"$metadata#ESAllPrim/$entity\","
        + "\"PropertyInt16\":32767,"
        + "\"PropertyString\":\"First Resource - positive values\","
        + "\"PropertyBoolean\":true,"
        + "\"PropertyByte\":255,"
        + "\"PropertySByte\":127,"
        + "\"PropertyInt32\":2147483647,"
        + "\"PropertyInt64\":9223372036854775807,"
        + "\"PropertySingle\":1.79E20,"
        + "\"PropertyDouble\":-1.79E19,"
        + "\"PropertyDecimal\":34,"
        + "\"PropertyBinary\":\"ASNFZ4mrze8=\","
        + "\"PropertyDate\":\"2012-12-03\","
        + "\"PropertyDateTimeOffset\":\"2012-12-03T07:16:23Z\","
        + "\"PropertyDuration\":\"PT6S\","
        + "\"PropertyGuid\":\"01234567-89ab-cdef-0123-456789abcdef\""
        + ",\"PropertyTimeOfDay\":\"03:26:05\""
        + "}";
    assertEquals(expected, IOUtils.toString(response.getRawResponse(), "UTF-8"));
  }
  
  @Test // Entity Set return 
  public void executeUFFICRTESMixPrimCollCompTwoParam() throws Exception {
    final ODataPropertyRequest<CommonODataProperty> request = getClient().getRetrieveRequestFactory()
        .getPropertyRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendOperationCallSegment("FICRTESMixPrimCollCompTwoParam(ParameterString='param1',ParameterInt16=2)")
            .build());
    
    assertNotNull(request);

    final ODataRetrieveResponse<CommonODataProperty> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());
    assertThat(response.getContentType(), containsString(ContentType.APPLICATION_JSON.toContentTypeString()));
    String expected = 
        "{\"@odata.context\":\"$metadata#ESMixPrimCollComp\","
        + "\"value\":["
        + "{\"PropertyInt16\":32767,"
        + "\"CollPropertyString\":["
          + "\"Employee1@company.example\",\"Employee2@company.example\",\"Employee3@company.example\"],"
        + "\"PropertyComp\":{\"PropertyInt16\":111,\"PropertyString\":\"TEST A\"},"
        + "\"CollPropertyComp\":["
          + "{\"PropertyInt16\":123,\"PropertyString\":\"TEST 1\"},"
          + "{\"PropertyInt16\":456,\"PropertyString\":\"TEST 2\"},"
          + "{\"PropertyInt16\":789,\"PropertyString\":\"TEST 3\"}"
          + "]"
        + "},";
    assertTrue(IOUtils.toString(response.getRawResponse(), "UTF-8").startsWith(expected));
  }  
  
  //Bounded - binding param enitityset return string
  @Test
  public void executeBFESTwoPrimRTString() throws Exception {
    final ODataPropertyRequest<CommonODataProperty> request = getClient().getRetrieveRequestFactory()
        .getPropertyRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendEntitySetSegment("ESTwoPrim")
            .appendNavigationSegment("olingo.odata.test1")
            .appendOperationCallSegment("BFESTwoPrimRTString")
            .build());
    
    assertNotNull(request);

    final ODataRetrieveResponse<CommonODataProperty> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());
    String expected = "{\"@odata.context\":\"$metadata#Edm.String\","
        + "\"#olingo.odata.test1.BFESTwoPrimRTString(BindingParam)\":{},"
        + "\"value\":\"TEST-A\"}";
    assertEquals(expected, IOUtils.toString(response.getRawResponse(), "UTF-8"));
  } 
  
  //Bounded - binding param enitity return string[]
  @Test
  public void executeBFESTwoPrimRTCollString() throws Exception {
    final ODataPropertyRequest<CommonODataProperty> request = getClient().getRetrieveRequestFactory()
        .getPropertyRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendEntitySetSegment("ESTwoPrim")
            .appendKeySegment(1)
            .appendNavigationSegment("olingo.odata.test1")
            .appendOperationCallSegment("BFESTwoPrimRTCollString")
            .build());
    
    assertNotNull(request);

    final ODataRetrieveResponse<CommonODataProperty> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());
    String expected = "{\"@odata.context\":\"$metadata#Collection(Edm.String)\","
        + "\"#olingo.odata.test1.BFESTwoPrimRTCollString(BindingParam)\":{},"
        + "\"value\":[\"dummy1\",\"dummy2\"]}";
    assertEquals(expected, IOUtils.toString(response.getRawResponse(), "UTF-8"));
  }
  
  //Bounded - binding param enitityset return entity
  @Test
  @Ignore
  public void executenameBFESTwoPrimRTETTwoPrim() throws Exception {
    final ODataPropertyRequest<CommonODataProperty> request = getClient().getRetrieveRequestFactory()
        .getPropertyRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendEntitySetSegment("ESTwoPrim")
            .appendNavigationSegment("olingo.odata.test1")
            .appendOperationCallSegment("BFESTwoPrimRTETTwoPrim(ParameterString='param1')")
            .build());
    
    assertNotNull(request);

    final ODataRetrieveResponse<CommonODataProperty> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());
    String expected = "";
    assertEquals(expected, IOUtils.toString(response.getRawResponse(), "UTF-8"));
  }  
  
  /*
   *  Actions test cases
   */
  
  @Test // primitive return
  public void executeAIRTPrimParam() throws Exception {
    final ODataPrimitiveValue param = getClient().getObjectFactory().newPrimitiveValueBuilder().buildInt16((short)22);
    final ODataInvokeRequest<ODataProperty> request = getClient().getInvokeRequestFactory().
        getActionInvokeRequest(getClient().newURIBuilder(SERVICE_URI).
            appendOperationCallSegment("AIRTPrimParam").build(), ODataProperty.class,
            Collections.<String, ODataValue> singletonMap("ParameterInt16", param));
    final ODataProperty response = request.execute().getBody();    

    assertEquals("return-string", response.getValue().asPrimitive().toValue());
  }
  
  @Test // enrity return
  public void executeActionEnrity() throws Exception {
    //TODO: needs the EntitySetPath fix
  }  
}
