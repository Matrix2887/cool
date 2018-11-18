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
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Scanner;

//@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {

        String check = request.getParameter("check");

        HttpSession session = request.getSession();

        String checkCode = (String) session.getAttribute("CHECKCODE_SERVER");

        session.removeAttribute("CHECKCODE_SERVER");

        ResultInfo info = new ResultInfo();

        if(checkCode == null || !checkCode.equals(check)){

            info.setFlag(false);
            info.setErrorMsg("注册失败,验证码错误");

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

            boolean flag = service.regist(user);

            if(flag){

                info.setFlag(true);

            }else{

                info.setFlag(false);
                info.setErrorMsg("注册失败,用户已存在");

            }

        }



        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(info);

        response.setContentType("application/json;charset=utf-8");

        response.getWriter().write(json);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        this.doPost(request, response);
    }
}
