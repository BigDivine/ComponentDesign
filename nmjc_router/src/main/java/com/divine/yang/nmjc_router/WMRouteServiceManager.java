package com.divine.yang.nmjc_router;


import com.divine.yang.nmjc_router.services.ILoginService;
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


}
