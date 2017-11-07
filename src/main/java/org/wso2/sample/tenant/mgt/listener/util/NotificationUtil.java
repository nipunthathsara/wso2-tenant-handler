package org.wso2.sample.tenant.mgt.listener.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.axis2.transport.mail.MailConstants;
import org.wso2.carbon.identity.notification.mgt.NotificationManagementException;
import org.wso2.carbon.identity.notification.mgt.bean.PublisherEvent;

public class NotificationUtil {
    private static final Log log = LogFactory.getLog(NotificationUtil.class);
    private PublisherEvent event;
    public static final String BODY = "body";

    public NotificationUtil(String email, String name, String domain) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hi " + name + ",\n");
        stringBuilder.append("Tenant domain: " + domain + " successfully registered. \n");
        stringBuilder.append("Thanks.");
        try {
            event = new PublisherEvent("TenantOperation");
            event.addEventProperty(MailConstants.MAIL_HEADER_TO, email);
            event.addEventProperty(MailConstants.MAIL_HEADER_SUBJECT, "Tenant domain registration");
            event.addEventProperty(BODY, stringBuilder.toString());
        } catch (NotificationManagementException e) {
            log.error("Error while creating publisher event " + e);
        }
    }

    public void sendNotification() {
        (new Thread(new EmailSendingUtil(event))).start();
    }
}
