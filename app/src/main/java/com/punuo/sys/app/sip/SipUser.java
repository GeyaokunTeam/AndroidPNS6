package com.punuo.sys.app.sip;

import android.content.Context;
import android.util.Log;

import com.punuo.sys.app.GlobalSetting;
import com.punuo.sys.app.IUserLogin;
import com.punuo.sys.app.Status;
import com.punuo.sys.app.UserProfile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.zoolu.sip.address.SipURL;
import org.zoolu.sip.message.Message;
import org.zoolu.sip.provider.SipProvider;
import org.zoolu.sip.provider.Transport;
import org.zoolu.sip.provider.TransportConnId;

import java.io.StringReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class SipUser extends SipProvider {
    private Context mContext;
    private static final String TAG = "SipUser";
    private static String[] PROTOCOLS = {"udp"};
    //线程池
    private ExecutorService pool = Executors.newFixedThreadPool(3);
    IUserLogin mIUserLogin;

    public void setIUserLogin(IUserLogin mIUserLogin) {
        this.mIUserLogin = mIUserLogin;
    }

    public SipUser(String via_addr, int host_port, Context context) {
        super(via_addr, host_port, PROTOCOLS, null);
        this.mContext = context;
    }

    public TransportConnId sendMessage(Message msg) {
        return sendMessage(msg, GlobalSetting.serverIp, GlobalSetting.SERVER_PORT_USER);
    }

    public TransportConnId sendMessage(final Message msg, final String destAddr, final int destPort) {
        Log.v(TAG, "<----------send sip message---------->");
        Log.v(TAG, msg.toString());
        TransportConnId id = null;
        try {
            id = pool.submit(new Callable<TransportConnId>() {
                public TransportConnId call() {
                    return sendMessage(msg, "udp", destAddr, destPort, 0);
                }
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void shutDown() {
        pool.shutdown();
    }

    @Override
    public synchronized void onReceivedMessage(Transport transport, Message msg) {
        Log.v(TAG, "<----------receive sip message---------->");
        Log.v(TAG, msg.toString());
        //sip消息来源的RemoteProt,6060为设备,6061为用户
        int port = msg.getRemotePort();
        if (port == GlobalSetting.SERVER_PORT_USER) {
            if (msg.isRequest()) {

            } else {
                int code = msg.getStatusLine().getCode();
                switch (code) {
                    case 200:
                        responseParse(msg);
                        break;
                    case 401://密码错误
                        mIUserLogin.OnLogin2Failed(Status.REGISTER_PASSWORD_ERROR);
                        break;
                    case 402://账号不存在
                        mIUserLogin.OnLogin1Failed(Status.REGISTER_ACCOUNT_NOT_EXSIT);
                        break;
                }
            }
        }
    }

    private void responseParse(Message msg) {
        String body = msg.getBody();
        if (body != null) {
            StringReader sr = new StringReader(body);
            InputSource is = new InputSource(sr);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            Document document;
            try {
                builder = factory.newDocumentBuilder();
                document = builder.parse(is);
                Element root = document.getDocumentElement();
                final String type = root.getTagName();
                Element codeElement;
                String code;
                switch (type) {
                    case "negotiate_response":/*注册第一步响应*/
                        Element seedElement = (Element) root.getElementsByTagName("seed").item(0);
                        Element userIdElement = (Element) root.getElementsByTagName("user_id").item(0);
                        if (userIdElement != null) {//如果掉线服务器会当成设备注册第一步
                            Element saltElement = (Element) root.getElementsByTagName("salt").item(0);
                            Element phoneNumElement = (Element) root.getElementsByTagName("phone_num").item(0);
                            Element realNameElement = (Element) root.getElementsByTagName("real_name").item(0);
                            UserProfile user = new UserProfile();
                            String seed;
                            String salt;
                            user.userId = userIdElement.getFirstChild().getNodeValue();
                            if (realNameElement != null)
                                user.userRealName = realNameElement.getFirstChild().getNodeValue();
                            if (phoneNumElement != null)
                                user.userPhoneNumber = phoneNumElement.getFirstChild().getNodeValue();
                            if (seedElement != null) {
                                seed = seedElement.getFirstChild().getNodeValue();
                            } else {
                                if (mIUserLogin != null)
                                    mIUserLogin.OnLogin1Failed(Status.REGISTER_SEED_LOST);
                                break;
                            }
                            if (saltElement != null) {
                                salt = saltElement.getFirstChild().getNodeValue();
                            } else {
                                if (mIUserLogin != null)
                                    mIUserLogin.OnLogin1Failed(Status.REGISTER_SALT_LOST);
                                break;
                            }
                            SipURL local = new SipURL(user.userId, GlobalSetting.serverIp, GlobalSetting.SERVER_PORT_USER);
                            GlobalSetting.user_from.setAddress(local);
                            Log.v(TAG, "收到用户注册第一步响应");
                            if (mIUserLogin != null)
                                mIUserLogin.OnLogin1(salt, seed);
                        } else {
                            Log.e(TAG, "掉线");

                        }
                }
            } catch (Exception e) {
                Log.e(TAG, "responseParse: ", e);
            }
        } else {
            Log.i(TAG + "responseParse", "BODY IS NULL");
        }
    }
}
