package com.example.aleron08.ilearning.view.inter;

import android.support.v4.app.FragmentTransaction;

/**
 * Created by aleron08 on 2018/10/5.
 */

public interface IMainView {
    public void initBottomBar();

    public void setDefaultFragment();

    public void hideFragment(FragmentTransaction fragmentTransaction);
}
