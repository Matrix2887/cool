package cn.itcast.travel.web.classServlet;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    /**
     * 重写 service方法, service方法原来是根据页面发来的请求来进行doGet,doPost 判断的,现在通过重写service方法,原来通过doGet,
     * doPost判断的方法,直接通过请求虚拟路径的名称进行相关方法的匹配,因此不需要doGet,doPost 方法,从而实现对于方法的调用,至于虚拟路径
     * 都是通过同一个路径来访问的,只不过,最后的方法名称不同  /userServlet/*  但是都要通过 userServlet 这个虚拟路径访问 ,或者其他的虚拟路径访问
     * 这是运用的反射的思想,通过提取同一个方法来避免代码的重复
     * @param req 接收页面请求
     * @param resp 对于接收到的请求服务器做出的回应
     * @throws ServletException 服务器异常
     * @throws IOException  输入,输出异常
     */
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1.获取请求路径
        String uri = req.getRequestURI();
//        System.out.println("uri -->" + uri);

        // 2.截取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
//        System.out.println("method -->" + methodName);

        // 3.获取方法名称
//        System.out.println(this);

        try {

            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

            method.invoke(this,req,resp);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void writeValue(Object obj, HttpServletResponse response) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json;charset=utf-8");

        mapper.writeValue(response.getOutputStream(),obj);

    }

    public void writeValueAsString(Object obj, HttpServletResponse response) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        String string = mapper.writeValueAsString(obj);

        response.setContentType("text/html; charset=utf-8");

        response.getWriter().write(string);

    }

}
