/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.google.mail;

import java.util.Map;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.component.google.mail.internal.GoogleMailApiName;
import org.apache.camel.component.google.mail.internal.GoogleMailPropertiesHelper;
import org.apache.camel.util.IntrospectionSupport;
import org.apache.camel.util.component.AbstractApiProducer;
import org.apache.camel.util.component.ApiMethod;

/**
 * The GoogleMail producer.
 */
public class GoogleMailProducer extends AbstractApiProducer<GoogleMailApiName, GoogleMailConfiguration> {

    public GoogleMailProducer(GoogleMailEndpoint endpoint) {
        super(endpoint, GoogleMailPropertiesHelper.getHelper());
    }

    @Override
    protected Object doInvokeMethod(ApiMethod method, Map<String, Object> properties) throws RuntimeCamelException {
        AbstractGoogleClientRequest request = (AbstractGoogleClientRequest) super.doInvokeMethod(method, properties);
        try {
            setProperty(properties, request, "q");
            setProperty(properties, request, "maxResults");
            setProperty(properties, request, "pageToken");
            setProperty(properties, request, "format");
            setProperty(properties, request, "fields");
            return request.execute();
        } catch (Exception e) {
            throw new RuntimeCamelException(e);
        }
    }

    private void setProperty(Map<String, Object> properties, AbstractGoogleClientRequest request, String key) throws Exception {
        if (properties.containsKey(key)) {
            IntrospectionSupport.setProperty(getEndpoint().getCamelContext().getTypeConverter(), request, key, properties.get(key));
        }
    }
}