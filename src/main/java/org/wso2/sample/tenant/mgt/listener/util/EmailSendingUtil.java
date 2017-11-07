/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.sample.tenant.mgt.listener.util;

import org.wso2.carbon.identity.notification.mgt.AbstractNotificationSendingModule;
import org.wso2.carbon.identity.notification.mgt.NotificationManagementException;
import org.wso2.carbon.identity.notification.mgt.bean.PublisherEvent;
import org.wso2.carbon.identity.notification.mgt.email.EmailModuleConstants;
import org.wso2.carbon.core.CarbonConfigurationContextFactory;
import org.apache.axis2.transport.mail.MailConstants;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axis2.transport.base.BaseConstants;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.Constants;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.ServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by nipun on Nov, 2017
 */
public class EmailSendingUtil extends AbstractNotificationSendingModule implements Runnable {
    private static final Log log = LogFactory.getLog(EmailSendingUtil.class);
    private static OMElement payload;
    private static ServiceClient serviceClient;

    public void sendMessage(PublisherEvent publisherEvent) throws NotificationManagementException {

        payload = OMAbstractFactory.getOMFactory().createOMElement(BaseConstants
                .DEFAULT_TEXT_WRAPPER, null);
        String message = publisherEvent.getEventProperties().getProperty(NotificationUtil.BODY);
        payload.setText(message);
        ConfigurationContext configContext = CarbonConfigurationContextFactory.getConfigurationContext();

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put(MailConstants.MAIL_HEADER_SUBJECT, publisherEvent.getEventProperties()
                .getProperty(MailConstants.MAIL_HEADER_SUBJECT));

        try {
            if (configContext != null) {
                serviceClient = new ServiceClient(configContext, null);
            } else {
                serviceClient = new ServiceClient();
            }
            Options options = new Options();
            options.setProperty(Constants.Configuration.ENABLE_REST, Constants.VALUE_TRUE);
            options.setProperty(MessageContext.TRANSPORT_HEADERS, headerMap);
            options.setProperty(MailConstants.TRANSPORT_MAIL_FORMAT,
                    MailConstants.TRANSPORT_FORMAT_TEXT);
            options.setTo(new EndpointReference(EmailModuleConstants.MAILTO_LABEL + publisherEvent.getEventProperties()
                    .getProperty(MailConstants.MAIL_HEADER_TO)));
            serviceClient.setOptions(options);
            (new Thread(new EmailSendingUtil())).start();
        } catch (AxisFault axisFault) {
            log.error("Error while sending out tenant registration notification email" + axisFault);
        }
    }

    @Override
    public void run() {
        try {
            serviceClient.fireAndForget(payload);
        } catch (AxisFault axisFault) {
            log.error("Error while sending out tenant registration notification email" + axisFault);
        }
    }
}
