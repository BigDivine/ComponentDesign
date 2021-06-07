package com.divine.yang.nmjc_main.mvp.presenters;

import com.divine.yang.nmjc_main.mvp.models.MainModel;
import com.divine.yang.nmjc_main.mvp.views.MainView;

/**
 * Author: Divine
 * CreateDate: 2021/1/13
 * Describe:
 */
public class MainPresenter {
    private MainView mainView;
    private MainModel mainModel;

    public MainPresenter(MainView mainView) {
        this.mainModel = new MainModel();
        this.mainView = mainView;
    }
    public void getData(){
    }
}
