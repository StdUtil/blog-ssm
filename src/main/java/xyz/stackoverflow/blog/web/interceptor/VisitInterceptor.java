package xyz.stackoverflow.blog.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.stackoverflow.blog.pojo.entity.Visit;
import xyz.stackoverflow.blog.service.VisitService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 访问量拦截器
 *
 * @author 凉衫薄
 */
public class VisitInterceptor implements HandlerInterceptor {

    @Autowired
    private VisitService visitService;

    /**
     * 记录访问量
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.getSession();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            String uri = request.getRequestURI();
            String param = request.getQueryString();
            Integer status = response.getStatus();
            String ip = request.getRemoteAddr();
            String agent = request.getHeader("User-Agent");
            Date date = new Date();
            String url = param == null ? uri : uri + "?" + param;
            Visit visit = new Visit(null, url, status, ip, agent, date);
            visitService.insertVisit(visit);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}