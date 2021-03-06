package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String check = request.getParameter("check");

        HttpSession session = request.getSession();

        String checkcode = (String) session.getAttribute("CHECKCODE_SERVER");

        session.removeAttribute("CHECKCODE_SERVER");

        ResultInfo info = new ResultInfo();

        if(checkcode == null || !checkcode.equals(check)){

            info.setFlag(false);
            info.setErrorMsg("登陆失败,验证码错误");

        }else {

            Map<String, String[]> map = request.getParameterMap();

            User user = new User();

            try {
                BeanUtils.populate(user,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            UserService service = new UserServiceImpl();

            User loginUser = service.login(user);

            if(loginUser == null){

                info.setFlag(false);
                info.setErrorMsg("用户名或密码错误");

            }else if(!"Y".equals(loginUser.getStatus())){

                info.setFlag(false);
                info.setErrorMsg("用户尚未激活,请激活");

            }else {

                info.setFlag(true);

                session.setAttribute("user",loginUser);

            }

        }

        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json;charset=utf-8");

        mapper.writeValue(response.getOutputStream(),info);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
