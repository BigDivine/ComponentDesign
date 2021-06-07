package com.divine.yang.module_login;


 import android.content.Context;

 import com.divine.yang.nmjc_router.NmjcRouter;
 import com.divine.yang.nmjc_router.RouterServiceConstant;
 import com.divine.yang.nmjc_router.services.ILoginService;
 import com.sankuai.waimai.router.Router;
 import com.sankuai.waimai.router.annotation.RouterService;

/**
 * Author: Divine
 * CreateDate: 2020/11/13
 * Describe:
 */
@RouterService(interfaces = ILoginService.class, key = RouterServiceConstant.SINGLETON, singleton = true)
public class LoginServiceImpl implements ILoginService {
    @Override
    public void loginSuccess(Context mContext) {
        Router.startUri(mContext, NmjcRouter.mainRouter);
    }
}
