package com.punuo.sys.app.sip;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class BodyFactory {
    public static String createRegisterBody(String password) {
        StringBuilder body = new StringBuilder(
                "<?xml version=\"1.0\"?>\r\n<login_request>\r\n<password>");
        body.append(password);
        body.append("</password>\r\n</login_request>\r\n");
        return body.toString();
    }
}
