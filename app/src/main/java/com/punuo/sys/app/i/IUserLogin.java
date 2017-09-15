package com.punuo.sys.app.i;

/**
 * Author chenhan
 * Date 2017/7/31
 * user register callback
 */

public interface IUserLogin extends ILogin {
    //user register first step success
    void OnUserLogin1(String salt, String seed);

    //user register second step success
    void OnUserLogin2();

    //user register first step failed
    void OnUserLogin1Failed(int Error);

    //user register second step failed
    void OnUserLogin2Failed(int Error);

    //user register timeout
    void OnUserLoginTimeOut();
}
