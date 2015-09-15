package com.landaojia.mvc.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * Customize HttpServletRequest, which is used for mount 3rd party http session
 * @author Jason
 *
 * 2015年9月15日
 */
public class RemoteSessionRequest extends HttpServletRequestWrapper {

    public RemoteSessionRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public HttpSession getSession() {
        return RemoteSessionHandler.getInstance(super.getSession());
    }
}