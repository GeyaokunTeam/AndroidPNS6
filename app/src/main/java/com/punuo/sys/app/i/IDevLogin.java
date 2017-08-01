package com.punuo.sys.app.i;

/**
 * Author chzjy
 * Date 2016/12/19.
 */

public interface IDevLogin extends ILogin {
    void OnDevLogin1();

    void OnDevLogin2();

    void OnDevLoginFailed();

    void OnDevLoginTimeOut();
}
