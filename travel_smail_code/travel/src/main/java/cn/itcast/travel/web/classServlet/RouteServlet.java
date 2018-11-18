package cn.itcast.travel.web.classServlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@WebServlet("/routeServlet/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();

    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {

        String currentPageStr = request.getParameter("currentPage") + "";
        String pageSizeStr = request.getParameter("pageSize") + "";
        String cidStr = request.getParameter("cid") + "";
        String rname = request.getParameter("rname") + "";

//        rname = new String(rname.getBytes("iso-8859-1","utf-8"));

//        System.out.println(currentPageStr + pageSizeStr + cidStr);

        int currentPage = 1;// 当前页码初始值为1
        int pageSize = 5; // 每页显示条数初始值为5
        int cid = 0;

        if (!"null".equals(currentPageStr) && currentPageStr.length() > 0) {

            currentPage = Integer.parseInt(currentPageStr);

        } else {

            currentPage = 1;// 当前页码初始值为1

        }

        if (!"null".equals(pageSizeStr) && pageSizeStr.length() > 0) {

            pageSize = Integer.parseInt(pageSizeStr);

        } else {

            pageSize = 5; // 每页显示条数初始值为5

        }

        if (!"null".equals(cidStr) && cidStr.length() > 0) {

            cid = Integer.parseInt(cidStr);

        } else {

            cid = 0;

        }

        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize, rname);

        writeValue(pb, response);

    }

    // 根据id查询一个旅游线路的详细信息
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {

        String rid = request.getParameter("rid");

        int i = Integer.parseInt(rid);

        Route route = routeService.findOne(i);

        writeValue(route, response);

    }


    // 判断当前登陆用户是否收藏过该线路
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {

        String rid = request.getParameter("rid");

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        int uid = 0;

        if (user == null) {

            uid = 0;

        } else {

            uid = user.getUid();

        }

        boolean flag = favoriteService.isFavorte(Integer.parseInt(rid), uid);

        writeValue(flag, response);

    }

    public void addRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {

        String rid = request.getParameter("rid");

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        int uid;

        if (user == null) {

            return;

        } else {

            uid = user.getUid();

        }

        favoriteService.add(Integer.parseInt(rid), uid);

    }

    public void myFavoriteRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {

        String currentPageStr = request.getParameter("currentPage") + "";
        String pageSizeStr = request.getParameter("pageSize") + "";

        int currentPage = 1;// 当前页码初始值为1
        int pageSize = 6; // 每页显示条数初始值为6

        if (!"null".equals(currentPageStr) && currentPageStr.length() > 0) {

            currentPage = Integer.parseInt(currentPageStr);

        } else {

            currentPage = 1;// 当前页码初始值为1

        }

        if (!"null".equals(pageSizeStr) && pageSizeStr.length() > 0) {

            pageSize = Integer.parseInt(pageSizeStr);

        } else {

            pageSize = 6; // 每页显示条数初始值为6

        }

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        int uid = 0;

        if (user == null) {

            uid = 0;

        } else {

            uid = user.getUid();

        }

//        System.out.println(uid + "---" + currentPage + "---" + pageSize);

        PageBean<Route> routes = routeService.myFavoriteRoute(uid, currentPage, pageSize);

//        System.out.println(routes);

        writeValue(routes,response);

    }
}
