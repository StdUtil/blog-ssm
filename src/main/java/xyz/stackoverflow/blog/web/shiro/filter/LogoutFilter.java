package xyz.stackoverflow.blog.web.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 注销过滤器
 *
 * @author 凉衫薄
 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        return super.preHandle(request, response);
    }
}