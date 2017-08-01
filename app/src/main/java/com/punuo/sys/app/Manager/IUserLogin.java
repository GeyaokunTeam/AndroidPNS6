package com.punuo.sys.app.Manager;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public interface IUserLogin extends ILogin {
    void OnUserLogin1(String salt, String seed);

    void OnUserLogin2();

    void OnUserLogin1Failed(int Error);

    void OnUserLogin2Failed(int Error);

    void OnUserLoginTimeOut();
}
