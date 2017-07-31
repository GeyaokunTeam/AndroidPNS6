package com.punuo.sys.app;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public interface IUserLogin {
    void OnLogin1(String salt, String seed);

    void OnLogin2();

    void OnLogin1Failed(int Error);

    void OnLogin2Failed(int Error);

    void OnLoginTimeOut();
}
