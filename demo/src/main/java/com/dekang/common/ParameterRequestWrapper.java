package com.dekang.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;
//重新request请求对象
public class ParameterRequestWrapper extends HttpServletRequestWrapper {



    private Map<String, String[]> params = new HashMap<>();


    private Map<String, String> mapCookies;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public ParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        //将参数表，赋予给当前的Map以便于持有request中的参数
        this.params.putAll(request.getParameterMap());
        this.mapCookies = new HashMap<>();
    }

    /**
     * 重载构造方法
     */

    public ParameterRequestWrapper(HttpServletRequest request, Map<String, Object> extendParams) {
        this(request);
        //这里将扩展参数写入参数表
        addAllParameters(extendParams);
    }

    /**
     * 在获取所有的参数名,必须重写此方法，否则对象中参数值映射不上
     *
     * @return
     */
    @Override
    public Enumeration<String> getParameterNames() {
        return new Vector(params.keySet()).elements();
    }

    /**
     * 重写getParameter方法
     *
     * @param name 参数名
     * @return 返回参数值
     */
    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values;
    }

    /**
     * 增加多个参数
     *
     * @param otherParams 增加的多个参数
     */
    public void addAllParameters(Map<String, Object> otherParams) {
        for (Map.Entry<String, Object> entry : otherParams.entrySet()) {
            addParameter(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 增加参数
     *
     * @param name  参数名
     * @param value 参数值
     */
    public void addParameter(String name, Object value) {
        if (value != null) {
            if (value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }

    /**
     * 本类 存入 Cookie
     * @return
     */
    public void putCookie(String name, String value) {
        this.mapCookies.put(name, value);
    }

    /**
     * 获取request中的cookie
     * @param key
     */
    public String getCookieForRequest(String key) {
        Cookie[] cookies = getCookieArrForRequest();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        List<Cookie> cookieList = new ArrayList<>(Arrays.asList(cookies));
        for (int i = 0; i < cookieList.size(); i++) {
            if(cookieList.get(i).getName().equals(key)){
                return cookieList.get(i).getValue();
            }
        }
        return null;
    }


    /**
     * 获取request中的cookie[]
     */
    public Cookie[] getCookieArrForRequest() {
        HttpServletRequest request = (HttpServletRequest) getRequest();
        return request.getCookies();
    }

    /**
     * 获取本类存放的和request中的Cookie
     * @return
     */
    public Cookie[] getCookies() {
        Cookie[] cookies = getCookieArrForRequest();
        if (mapCookies == null || mapCookies.isEmpty()) {
            return cookies;
        }
        if (cookies == null || cookies.length == 0) {
            List<Cookie> cookieList = new LinkedList<>();
            for (Map.Entry<String, String> entry : mapCookies.entrySet()) {
                String key = entry.getKey();
                if (key != null && !"".equals(key)) {
                    cookieList.add(new Cookie(key, entry.getValue()));
                }
            }
            if (cookieList.isEmpty()) {
                return cookies;
            }
            return cookieList.toArray(new Cookie[cookieList.size()]);
        } else {
            List<Cookie> cookieList = new ArrayList<>(Arrays.asList(cookies));
            for (Map.Entry<String, String> entry : mapCookies.entrySet()) {
                String key = entry.getKey();
                if (key != null && !"".equals(key)) {
                    for (int i = 0; i < cookieList.size(); i++) {
                        if(cookieList.get(i).getName().equals(key)){
                            cookieList.remove(i);
                        }
                    }
                    cookieList.add(new Cookie(key, entry.getValue()));
                }
            }
            return cookieList.toArray(new Cookie[cookieList.size()]);
        }
    }






}
