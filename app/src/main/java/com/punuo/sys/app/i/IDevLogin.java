package com.punuo.sys.app.i;

/**
 * Author chzjy
 * Date 2016/12/19.
 * dev register callback
 */

public interface IDevLogin extends ILogin {
    //dev register first step success
    void OnDevLogin1();

    //dev register second step success
    void OnDevLogin2();

    //dev register  failed
    void OnDevLoginFailed();

    //dev register timeout
    void OnDevLoginTimeOut();
}
