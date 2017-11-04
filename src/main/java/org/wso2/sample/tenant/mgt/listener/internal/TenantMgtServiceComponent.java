package org.wso2.sample.tenant.mgt.listener.internal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.stratos.common.listeners.TenantMgtListener;
import org.wso2.sample.tenant.mgt.listener.impl.TenantHandler;

/**
 * @scr.component name="tenant.mgt.workflow" immediate="true"
 */
public class TenantMgtServiceComponent {

    protected void activate(ComponentContext context) {
        System.out.println("****************************************************************************" +
                "#######################################################################");
        System.out.println("$$$$$$$$$$  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +
                "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        BundleContext bundleContext = context.getBundleContext();
        bundleContext.registerService(TenantMgtListener.class.getName(), new TenantHandler() , null);
//        bundleContext.registerService(ApplicationMgtListener.class.getName(), new SPWorkflowListener(), null);
//        bundleContext.registerService(WorkflowRequestHandler.class.getName(), new SPCreateHandler(), null);
    }
}
