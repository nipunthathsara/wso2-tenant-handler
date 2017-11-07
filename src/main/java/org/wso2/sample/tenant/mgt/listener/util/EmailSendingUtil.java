package org.wso2.sample.tenant.mgt.listener.util;

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

public class EmailSendingUtil implements Runnable {
    private static final Log log = LogFactory.getLog(EmailSendingUtil.class);
    private ServiceClient serviceClient;
    private PublisherEvent publisherEvent;

    public EmailSendingUtil(PublisherEvent publisherEvent) {
        this.publisherEvent = publisherEvent;
    }

    /**
     * @return
     * @throws NotificationManagementException
     * This method returns the built payload and used
     * in the run() method within the same class
     */
    private OMElement sendMessage() throws AxisFault {

        ConfigurationContext configContext = CarbonConfigurationContextFactory.getConfigurationContext();
        OMElement payload = OMAbstractFactory.getOMFactory().createOMElement(BaseConstants
                .DEFAULT_TEXT_WRAPPER, null);
        //Set body of the email
        String message = publisherEvent.getEventProperties().getProperty(NotificationUtil.BODY);
        payload.setText(message);
        Map<String, String> headerMap = new HashMap();
        //Set subject of the email
        headerMap.put(MailConstants.MAIL_HEADER_SUBJECT, publisherEvent.getEventProperties()
                .getProperty(MailConstants.MAIL_HEADER_SUBJECT));
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
        //Set receiver's address
        options.setTo(new EndpointReference(EmailModuleConstants.MAILTO_LABEL + publisherEvent.getEventProperties()
                .getProperty(MailConstants.MAIL_HEADER_TO)));
        serviceClient.setOptions(options);
        return payload;
    }

    public void run() {
        try {
            OMElement payload = sendMessage();
            serviceClient.fireAndForget(payload);
        } catch (AxisFault e) {
            log.error("Error while sending out tenant registration notification email" + e);
        }
    }
}
