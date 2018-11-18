package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();


    @Override
    public boolean isFavorte(int rid, int uid) {

        Favorite favorite = favoriteDao.findByRidAndUid(rid, uid);

        if(favorite == null){

            return false;

        }else{

            return true;

        }

    }

    @Override
    public void add(int rid, int uid) {

        favoriteDao.add(rid,uid);

    }
}
