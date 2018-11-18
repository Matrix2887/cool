package cn.itcast.travel.web.classServlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.imageio.ImageIO;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Random;

@WebServlet("/userServlet/*")
public class UserServlet extends BaseServlet {

    private UserService service = new UserServiceImpl();

    // 激活方法
    public void activeUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String code = request.getParameter("code");

        if(code != null){

//            UserService service = new UserServiceImpl();

            boolean flag = service.active(code);

            String msg = null;

            if(flag){

                msg = "激活成功,请 <a href='http://localhost/travel/login.html'>登陆</a>";

            }else{

                msg = "激活失败,请交钱入会";

            }

            response.setContentType("text/html; charset=utf-8");

            response.getWriter().write(msg);

        }

    }

    // 验证码
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");

        //在内存中创建一个长80，宽30的图片，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0,0, width,height);

        //产生4个随机验证码，12Ey
        String checkCode = getCheckCode();
        //将验证码放入HttpSession中
        request.getSession().setAttribute("CHECKCODE_SERVER",checkCode);

        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体",Font.BOLD,24));
        //向图片上写入验证码
        g.drawString(checkCode,15,25);

        //画入随机的五边形
        g.setColor(Color.ORANGE);
        Random ran = new Random();
        for (int i = 0; i < 1; i++) {
            int x1 = ran.nextInt(width);
            int x2 = ran.nextInt(width);
            int x3 = ran.nextInt(width);
            int x4 = ran.nextInt(width);
            int x5 = ran.nextInt(width);
            int y1 = ran.nextInt(height);
            int y2 = ran.nextInt(height);
            int y3 = ran.nextInt(height);
            int y4 = ran.nextInt(height);
            int y5 = ran.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
            g.drawLine(x2, y2, x3, y3);
            g.drawLine(x3, y3, x4, y4);
            g.drawLine(x4, y4, x5, y5);
            g.drawLine(x5, y5, x1, y1);
        }

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        ImageIO.write(image,"PNG",response.getOutputStream());
    }
    /**
     * 产生4位随机字符串
     */
    private String getCheckCode() {
        String base = "0123456789";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();

    }


    // 退出登陆方法
    public void exitServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getSession().invalidate();

        response.sendRedirect(request.getContextPath() + "/login.html");

    }


    // 查询用户方法
    public void findUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        writeValue(user,response);

    }

    // 登陆方法
    public void loginServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

//            UserService service = new UserServiceImpl();

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

        writeValue(info,response);

    }

    // 注册方法
    public void registUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {

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

//            UserService service = new UserServiceImpl();

            boolean flag = service.regist(user);

            if(flag){

                info.setFlag(true);

            }else{

                info.setFlag(false);
                info.setErrorMsg("注册失败,用户已存在");

            }

        }

        writeValueAsString(info,response);

    }

}
