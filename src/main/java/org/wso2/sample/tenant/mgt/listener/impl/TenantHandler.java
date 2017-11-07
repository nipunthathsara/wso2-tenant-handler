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
package org.wso2.sample.tenant.mgt.listener.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.stratos.common.listeners.TenantMgtListener;
import org.wso2.carbon.stratos.common.beans.TenantInfoBean;
import org.wso2.carbon.stratos.common.exception.StratosException;
import org.wso2.sample.tenant.mgt.listener.util.NotificationUtil;

/**
 * Created by nipun on Nov, 2017
 */
public class TenantHandler implements TenantMgtListener {

    private static Log log = LogFactory.getLog(TenantHandler.class);

    @Override
    public void onTenantCreate(TenantInfoBean tenantInfoBean) throws StratosException {
        NotificationUtil notificationUtil = new NotificationUtil(tenantInfoBean.getEmail()
                ,tenantInfoBean.getAdmin(), tenantInfoBean.getTenantDomain());
        notificationUtil.sendMail();
    }

    @Override
    public void onTenantUpdate(TenantInfoBean tennantInfoBean) throws StratosException {
        // Any work to be performed after a tenant information update happens
    }

    @Override
    public void onTenantDelete(int i) {
        // Any work to be performed after a tenant deletion.
    }

    @Override
    public void onTenantRename(int i, String s, String s1) throws StratosException {
        // Any work to be performed after a tenant rename happens
    }

    @Override
    public void onTenantInitialActivation(int i) throws StratosException {
        // Any work to be performed after a tenant's initial actication happens
    }

    @Override
    public void onTenantActivation(int i) throws StratosException {
        // Any work to be performed after a tenant activation.
    }

    @Override
    public void onTenantDeactivation(int i) throws StratosException {
        // Any work to be performed after a tenant deactivation
    }

    @Override
    public void onSubscriptionPlanChange(int i, String s, String s1) throws StratosException {
    }

    @Override
    public int getListenerOrder() {
        return 0;
    }

    @Override
    public void onPreDelete(int i) throws StratosException {
        // Any work to be performed before a tenant is deleted
    }

}
