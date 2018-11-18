package cn.gandan.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String requestURI = request.getRequestURI();
        if(requestURI.contains("/login.jsp") || requestURI.contains("/loginServlet") || requestURI.contains("css") || requestURI.contains("fonts") || requestURI.contains("js") || requestURI.contains("checkCode")){
            chain.doFilter(req, resp);
        }else{
            Object manger = request.getSession().getAttribute("manger");
            if(manger != null){
                chain.doFilter(req, resp);
            }else {
                request.setAttribute("login_error","请登陆");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }
        }


//        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

}
