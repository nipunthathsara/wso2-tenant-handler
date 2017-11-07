package org.wso2.sample.tenant.mgt.listener.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.stratos.common.listeners.TenantMgtListener;
import org.wso2.sample.tenant.mgt.listener.impl.TenantHandler;

/**
 * @scr.component name="tenant.mgt.workflow" immediate="true"
 */
public class TenantMgtServiceComponent {

    private static Log log = LogFactory.getLog(TenantMgtServiceComponent.class);

    protected void activate(ComponentContext context) {
        BundleContext bundleContext = context.getBundleContext();
        bundleContext.registerService(TenantMgtListener.class.getName(), new TenantHandler(), null);
    }
}
