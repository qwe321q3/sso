package com.tianshuo.beta.sso.client.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于session的保存，删除
 *
 * @author tianshuo
 */
public class SessionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionHandler.class);
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
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("add sessionId[{}] and ticket[{}]", session.getId(), ticket);
        }
        ID_TO_SESSION.put(ticket, session);
    }


    /**
     * 删除session
     *
     * @param ticket
     */
    public static void removeSession(String ticket) {
        try {
            HttpSession session = ID_TO_SESSION.get(ticket);
            session.invalidate();
        } catch (Exception e) {
            LOGGER.error("{}", e);
        } finally {
            ID_TO_SESSION.clear();
        }
    }


}
