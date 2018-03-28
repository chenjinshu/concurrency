package com.mmall.concurrency;

import com.mmall.concurrency.example.threadLocal.RequestHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static Logger log = LoggerFactory.getLogger(HttpInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       // return super.preHandle(request, response, handler);
        log.info("preHandle");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // super.afterCompletion(request, response, handler, ex);
        RequestHolder.remove();    // 请求结束后从ThreadLocal中移除该线程对应的键值对，避免内存泄漏。
        log.info("afterCompletion");
        return;
    }
}
