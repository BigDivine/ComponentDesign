package com.divine.yang.lib_router;

import com.divine.yang.lib_router.services.ILoginService;
import com.divine.yang.lib_router.services.ITradeService;
import com.sankuai.waimai.router.Router;

/**
 * Author: Divine
 * CreateDate: 2020/11/13
 * Describe:
 */
public class WMRouteServiceManager {
    public static ILoginService getLoginService() {
        return Router.getService(ILoginService.class, RouterServiceConstant.SINGLETON);
    }

    public static ITradeService getTradeService() {
        return Router.getService(ITradeService.class, RouterServiceConstant.SINGLETON);
    }
}
