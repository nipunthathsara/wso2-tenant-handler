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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.notification.mgt.NotificationManagementException;
import org.wso2.carbon.identity.notification.mgt.bean.PublisherEvent;
import org.apache.axis2.transport.mail.MailConstants;

/**
 * Created by nipun on Nov, 2017
 */
public class NotificationUtil {
    private static final Log log = LogFactory.getLog(NotificationUtil.class);
    private PublisherEvent event;
    public static final String BODY = "body";

    public NotificationUtil (String email, String name, String domain){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hi " + name + ",\n");
        stringBuilder.append("Tenant domain: " + domain + " successfully registered. \n");
        stringBuilder.append("Thanks.");
        try{
            event = new PublisherEvent("TenantOperation");
            event.addEventProperty(MailConstants.MAIL_HEADER_TO, email);
            event.addEventProperty(MailConstants.MAIL_HEADER_SUBJECT, "Tenant domain registration");
            event.addEventProperty(BODY, stringBuilder.toString());
        } catch (NotificationManagementException e){
            log.error("Error while creating publisher event " + e);
        }
    }

    public void sendMail(){
        EmailSendingUtil emailSendingUtil = new EmailSendingUtil();
        try {
            emailSendingUtil.sendMessage(this.event);
        }catch (NotificationManagementException e){
            log.error("Error while sending notification " + e);
        }
    }
}
