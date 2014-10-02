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
package org.apache.olingo.server.api.serializer;

import org.apache.olingo.commons.api.edm.EdmAction;
import org.apache.olingo.commons.api.edm.EdmFunction;

public class BoundProcedureOption {
  private String procedureName;
  private String title;
  private String target;

  public String getProcedureName() {
    return procedureName;
  }
  public String getTitle() {
    return title;
  }
  public String getTarget() {
    return target;
  }
  
  public static Builder with() {
    return new Builder();
  }

  public static final class Builder {
    private BoundProcedureOption procedure = new BoundProcedureOption();
    private EdmFunction function;
    private EdmAction action;
    
    public Builder setFunction(EdmFunction function) {
      this.function = function;
      return this;
    }
    public Builder setAction(EdmAction action) {
      this.action = action;
      return this;
    }    
    public Builder setTitle(String title) {
      procedure.title = title;
      return this;
    }
    public Builder setTarget(String target) {
      procedure.target = target;
      return this;
    }
    public BoundProcedureOption build() {
      if (this.function != null && this.function.isBound()) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.function.getFullQualifiedName().getFullQualifiedNameAsString());
        if (!this.function.getParameterNames().isEmpty()) {
          sb.append("(");
          for (int i = 0; i < this.function.getParameterNames().size(); i++) {
            if (i > 0) {
              sb.append(",");
            }
            sb.append(this.function.getParameterNames().get(i));
          }
          sb.append(")");
        }
        this.procedure.procedureName = sb.toString();
      } else if (this.action != null && this.action.isBound()) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.action.getFullQualifiedName().getFullQualifiedNameAsString());
        if (!this.action.getParameterNames().isEmpty()) {
          sb.append("(");
          for (int i = 0; i < this.action.getParameterNames().size(); i++) {
            if (i > 0) {
              sb.append(",");
            }
            sb.append(this.action.getParameterNames().get(i));
          }
          sb.append(")");
        }
        this.procedure.procedureName = sb.toString();
        
      }
      return this.procedure;
    }
  }
}
