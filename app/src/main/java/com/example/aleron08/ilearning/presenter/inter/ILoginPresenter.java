package com.example.aleron08.ilearning.presenter.inter;

/**
 * Created by aleron08 on 2018/10/7.
 */

public interface ILoginPresenter {
    public void clear();
    public void doLogin(String phone, String pwd);
    public void setProgressBarVisiblity(int visiblity);
}
