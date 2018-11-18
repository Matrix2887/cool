package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {

    abstract PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    abstract Route findOne(int i);

    abstract PageBean<Route> myFavoriteRoute(int uid, int currentPage, int pageSize);
}
