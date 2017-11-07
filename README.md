# wso2-tenant-handler
Tenant management listener to execute custom code in tenant’s life-cycle

## Configurations
1. In the **<IS_HOME>/repository/conf/axis2/axis2.xml** file, Uncomment the following and add required information appropriately.

>   \<transportSender name="mailto" class="org.apache.axis2.transport.mail.MailTransportSender">
        \<parameter name="mail.smtp.from">wso2demomail@gmail.com\</parameter>
        \<parameter name="mail.smtp.user">wso2demomail\</parameter>
        \<parameter name="mail.smtp.password">mailpassword\</parameter>
        \<parameter name="mail.smtp.host">smtp.gmail.com\</parameter>
        \<parameter name="mail.smtp.port">587\</parameter>
        \<parameter name="mail.smtp.starttls.enable">true\</parameter>
        \<parameter name="mail.smtp.auth">true\</parameter>
   \</transportSender>

2. Build the project using **maven clean install** command.
3. Copy the **org.wso2.sample.tenant.mgt.listener-1.0-SNAPSHOT.jar** to the 
   **<IS_HOME>/repository/components/dropins** directory.
4. Restart the server.
5. Create a new Tenant.

Notification mail will be sent from configured Sender to Tenant Admin.