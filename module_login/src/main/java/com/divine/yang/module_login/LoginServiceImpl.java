package com.divine.yang.module_login;

import com.divine.yang.lib_router.RouterServiceConstant;
import com.divine.yang.lib_router.services.ILoginService;
import com.sankuai.waimai.router.annotation.RouterService;

/**
 * Author: Divine
 * CreateDate: 2020/11/13
 * Describe:
 */
@RouterService(interfaces = ILoginService.class, key = RouterServiceConstant.SINGLETON, singleton = true)
public class LoginServiceImpl implements ILoginService {
    @Override
    public void loginSuccess() {

    }
}
