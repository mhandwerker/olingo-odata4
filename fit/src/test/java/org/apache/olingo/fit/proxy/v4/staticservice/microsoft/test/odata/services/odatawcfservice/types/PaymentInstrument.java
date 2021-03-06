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
package org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types;
//CHECKSTYLE:OFF (Maven checkstyle)
import org.apache.olingo.ext.proxy.api.annotations.Key;
import org.apache.olingo.ext.proxy.api.AbstractEntitySet;
import org.apache.olingo.commons.api.edm.constants.EdmContentKind;
import org.apache.olingo.client.api.edm.ConcurrencyMode;
//CHECKSTYLE:ON (Maven checkstyle)


@org.apache.olingo.ext.proxy.api.annotations.Namespace("Microsoft.Test.OData.Services.ODataWCFService")
@org.apache.olingo.ext.proxy.api.annotations.EntityType(name = "PaymentInstrument",
        openType = false,
        hasStream = false,
        isAbstract = false)
public interface PaymentInstrument 
  extends org.apache.olingo.ext.proxy.api.Annotatable,
  org.apache.olingo.ext.proxy.api.EntityType<PaymentInstrument>, org.apache.olingo.ext.proxy.api.StructuredQuery<PaymentInstrument>   {


    

    @Key
    
    @org.apache.olingo.ext.proxy.api.annotations.Property(name = "PaymentInstrumentID", 
                type = "Edm.Int32", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = true,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.None,
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    java.lang.Integer getPaymentInstrumentID();

    void setPaymentInstrumentID(java.lang.Integer _paymentInstrumentID);
    
    
    @org.apache.olingo.ext.proxy.api.annotations.Property(name = "FriendlyName", 
                type = "Edm.String", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = true,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.None,
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    java.lang.String getFriendlyName();

    void setFriendlyName(java.lang.String _friendlyName);
    
    
    @org.apache.olingo.ext.proxy.api.annotations.Property(name = "CreatedDate", 
                type = "Edm.DateTimeOffset", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = true,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.None,
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    java.sql.Timestamp getCreatedDate();

    void setCreatedDate(java.sql.Timestamp _createdDate);
    

    @org.apache.olingo.ext.proxy.api.annotations.NavigationProperty(name = "TheStoredPI", 
                type = "Microsoft.Test.OData.Services.ODataWCFService.StoredPI", 
                targetSchema = "Microsoft.Test.OData.Services.ODataWCFService", 
                targetContainer = "InMemoryEntities", 
                targetEntitySet = "StoredPIs",
                containsTarget = false)
    org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.StoredPI getTheStoredPI();

    void setTheStoredPI(org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.StoredPI _theStoredPI);
    
        
    @org.apache.olingo.ext.proxy.api.annotations.NavigationProperty(name = "BackupStoredPI", 
                type = "Microsoft.Test.OData.Services.ODataWCFService.StoredPI", 
                targetSchema = "Microsoft.Test.OData.Services.ODataWCFService", 
                targetContainer = "InMemoryEntities", 
                targetEntitySet = "StoredPIs",
                containsTarget = false)
    org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.StoredPI getBackupStoredPI();

    void setBackupStoredPI(org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.StoredPI _backupStoredPI);
    


        Operations operations();

    interface Operations extends org.apache.olingo.ext.proxy.api.Operations{
    
        }
    Annotations annotations();

    interface Annotations {

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "PaymentInstrumentID",
                   type = "Edm.Int32")
        org.apache.olingo.ext.proxy.api.Annotatable getPaymentInstrumentIDAnnotations();

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "FriendlyName",
                   type = "Edm.String")
        org.apache.olingo.ext.proxy.api.Annotatable getFriendlyNameAnnotations();

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "CreatedDate",
                   type = "Edm.DateTimeOffset")
        org.apache.olingo.ext.proxy.api.Annotatable getCreatedDateAnnotations();



        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForNavigationProperty(name = "TheStoredPI", 
                  type = "Microsoft.Test.OData.Services.ODataWCFService.StoredPI")
        org.apache.olingo.ext.proxy.api.Annotatable getTheStoredPIAnnotations();

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForNavigationProperty(name = "BillingStatements", 
                  type = "Microsoft.Test.OData.Services.ODataWCFService.Statement")
        org.apache.olingo.ext.proxy.api.Annotatable getBillingStatementsAnnotations();

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForNavigationProperty(name = "BackupStoredPI", 
                  type = "Microsoft.Test.OData.Services.ODataWCFService.StoredPI")
        org.apache.olingo.ext.proxy.api.Annotatable getBackupStoredPIAnnotations();
    }

      @org.apache.olingo.ext.proxy.api.annotations.NavigationProperty(name = "BillingStatements", 
                type = "Microsoft.Test.OData.Services.ODataWCFService.StoredPI", 
                targetSchema = "Microsoft.Test.OData.Services.ODataWCFService", 
                targetContainer = "InMemoryEntities", 
                targetEntitySet = "StoredPIs",
                containsTarget = true)
    org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.PaymentInstrument.BillingStatements getBillingStatements();
    void setBillingStatements(org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.PaymentInstrument.BillingStatements _billingStatements);

            
    
    @org.apache.olingo.ext.proxy.api.annotations.EntitySet(name = "BillingStatements", contained = true)
    interface BillingStatements 
      extends org.apache.olingo.ext.proxy.api.EntitySet<org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.Statement, org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.StatementCollection>, 
      org.apache.olingo.ext.proxy.api.StructuredCollectionQuery<BillingStatements>,
      AbstractEntitySet<org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.Statement, java.lang.Integer, org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.StatementCollection> {
    }

  }
