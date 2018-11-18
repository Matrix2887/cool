package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.ArrayList;
import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();


    /*
      // 总记录数 根据数据库查询出来 count
    private int totalCount;

    // 总页数 根据总记录数/每页显示的条数 计算出来
    private int totalPage;

    // 当前页码 根据前端页面接收回来
    private int currentPage;

    // 每页显示的条数 根据前端页面接收回来
    private int pageSize;

    // 每页显示的数据集合 根据limit进行计算 计算公式为 (当前页码 - 1) * 每页显示的条数
    private List<T> list;
     */

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {

        // 总记录数 根据数据库查询出来 count
        int totalCount = routeDao.findTotalCount(cid, rname);

        // 总页数 根据总记录数/每页显示的条数 计算出来
        int totalPage = (totalCount % pageSize) == 0 ? (totalCount / pageSize) : ((totalCount / pageSize) + 1);

        // 判断当前页是否是最后一页
        if(currentPage < 1){
            currentPage = 1;
        }

        if(currentPage > totalPage){
            currentPage = totalPage;
        }

        // 查询起始记录
        int start = (currentPage - 1) * pageSize;

        List<Route> pages = routeDao.findByPage(cid, start, pageSize, rname);

        PageBean<Route> routePageBean = new PageBean<Route>(totalCount, totalPage, currentPage, pageSize, pages);

        return routePageBean;
    }

    @Override
    public Route findOne(int rid) {

        Route route = routeDao.findOne(rid);

        int count = favoriteDao.findCountByRid(rid);

        List<RouteImg> routeImgs = routeImgDao.findByRid(route.getRid());

        route.setRouteImgList(routeImgs);

        Seller seller = sellerDao.findById(route.getSid());

        route.setSeller(seller);

        route.setCount(count);

//        System.out.println(route);

        return route;
    }

    @Override
    public PageBean<Route> myFavoriteRoute(int uid, int currentPage, int pageSize) {

        // 总记录
        int totalCount = favoriteDao.findCountByUid(uid);

        // 总页数 根据总记录数/每页显示的条数 计算出来
        int totalPage = (totalCount % pageSize) == 0 ? (totalCount / pageSize) : ((totalCount / pageSize) + 1);

        // 判断当前页是否是最后一页
        if(currentPage < 1){
            currentPage = 1;
        }

        if(currentPage > totalPage){
            currentPage = totalPage;
        }

        // 查询起始记录
        int start = (currentPage - 1) * pageSize;


        List<Route> routes = new ArrayList<>();

        List<Favorite> favorites = favoriteDao.findByFavorite(uid,start,pageSize);

        for (Favorite favorite : favorites) {

            Route route = routeDao.findOne(favorite.getRid());

            int count = favoriteDao.findCountByRid(favorite.getRid());

            Seller seller = sellerDao.findById(route.getSid());

            List<RouteImg> routeImgs = routeImgDao.findByRid(route.getRid());

            route.setRouteImgList(routeImgs);

            route.setCount(count);

            route.setSeller(seller);

            routes.add(route);

        }

        PageBean<Route> routePageBean = new PageBean<Route>(totalCount, totalPage, currentPage, pageSize, routes);


        return routePageBean;
    }
}
