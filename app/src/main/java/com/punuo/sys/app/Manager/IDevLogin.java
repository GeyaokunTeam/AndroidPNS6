package com.punuo.sys.app.Manager;

/**
 * Author chzjy
 * Date 2016/12/19.
 */

public interface IDevLogin extends ILogin {
    void OnDevLogin1();

    void OnDevLogin2();

    void OnDevLogin1Failed();

    void OnDevLogin2Failed();

    void OnDevLoginTimeOut();
}
