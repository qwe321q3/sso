package com.tianshuo.beta.sso.client.session;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于session的保存，删除
 *
 * @author tianshuo
 */
public class SessionHandler {

    /**
     * 保存票据与客户端session的关系，用于单点登出
     */
    private final static Map<String, HttpSession> ID_TO_SESSION = new HashMap<>();

    /**
     * 保存sesson与票据关系
     *
     * @param ticket
     * @param session
     */
    public static void addSession(String ticket, HttpSession session) {
        ID_TO_SESSION.put(ticket, session);
    }


    /**
     * 删除session
     *
     * @param ticket
     */
    public static void removeSession(String ticket) {
        HttpSession session = ID_TO_SESSION.get(ticket);
        session.invalidate();
        ID_TO_SESSION.clear();
    }


}
