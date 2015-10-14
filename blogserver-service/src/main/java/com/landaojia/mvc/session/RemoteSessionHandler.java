package com.landaojia.mvc.session;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

/**
 * The implement details of proxing default Http session object 
 * @author Jason
 *
 * 2015年9月15日
 */
public class RemoteSessionHandler implements InvocationHandler {
    /**
     * simulate remote session dataBase;
     * the key of map is the id of session,the value of map is the true session map; 
     */
    private static Map<String, Map<String, Object>> map = new ConcurrentHashMap<String, Map<String, Object>>();
    
    /**
     * store current session object, just like the value of map;
     */
    private HttpSession session = null;

    private RemoteSessionHandler(HttpSession httpSession) {
        this.session = httpSession;
    };

    public static HttpSession getInstance(HttpSession httpSession) {
        InvocationHandler handler = new RemoteSessionHandler(httpSession);
        return (HttpSession) Proxy.newProxyInstance(httpSession.getClass().getClassLoader(), httpSession.getClass().getInterfaces(), handler);
    }

    /**
     * rewrite setter and getter of sessionMap methods
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //find the key on remote DB by id of current session; 
        String id = session.getId();
        Map<String, Object> m = map.get(id);
        if ("setAttribute".equals(method.getName())) {
            if (m == null) {
                m = new HashMap<String, Object>();
                map.put(id, m);
            }
            m.put((String) args[0], args[1]);
            System.out.println("[存入]key:" + args[0] + ",value:" + args[1]);
            return null;
        } else if ("getAttribute".equals(method.getName())) {
            if (m == null) {
                return null;
            }
            Object result = m.get(args[0]);
            System.out.println("[取出]key:" + args[0] + ",value:" + result);
            return result;
        }
        //deal with other methods
        return method.invoke(session, args);
    }

}
