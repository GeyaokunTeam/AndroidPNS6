package com.punuo.sys.app.sip;

import com.punuo.sys.app.GlobalSetting;

import org.zoolu.sip.address.NameAddress;
import org.zoolu.sip.address.SipURL;
import org.zoolu.sip.message.Message;
import org.zoolu.sip.message.MessageFactory;
import org.zoolu.sip.message.SipMethods;
import org.zoolu.sip.provider.SipProvider;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class SipMessageFactory extends MessageFactory {

    private static SipURL createRequestUri(int port) {
        return new SipURL(GlobalSetting.serverIp, port);
    }

    public static Message createRegisterRequest(SipProvider sipProvider, NameAddress to, NameAddress from, int port) {
        String via_addr = sipProvider.getViaAddress();
        int host_port = sipProvider.getPort();
        SipURL sipURL = new SipURL(via_addr, host_port);
        NameAddress contact = new NameAddress(sipURL);
        return createRegisterRequest(sipProvider, createRequestUri(port), to, from, contact);
    }

    public static Message createRegisterRequest(SipProvider sipProvider, NameAddress to, NameAddress from, String body, int port) {
        Message msg = createRegisterRequest(sipProvider, to, from, port);
        msg.setBody("application/xml", body);
        return msg;
    }

    public static Message createNotifyRequest(SipProvider sipProvider, NameAddress to, NameAddress from, String body, int port) {
        String via_addr = sipProvider.getViaAddress();
        int host_port = sipProvider.getPort();
        SipURL sipURL = new SipURL(via_addr, host_port);
        NameAddress contact = new NameAddress(sipURL);
        Message msg = createRequest(sipProvider, SipMethods.NOTIFY, createRequestUri(port), to, from, contact, null);
        msg.setBody("application/xml", body);
        return msg;
    }
}
