package com.tianshuo.beta.sso.ticket;

/**
 * 票据异常
 */
public class TicketException extends RuntimeException {

    public TicketException() {
    }

    public TicketException(String message) {
        super(message);
    }

    public TicketException(String message, Throwable cause) {
        super(message, cause);
    }
}
