package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

import java.util.List;

public interface FavoriteDao {

    abstract Favorite findByRidAndUid(int rid, int uid);

    abstract int findCountByRid(int rid);

    abstract void add(int rid, int uid);

    abstract int findCountByUid(int uid);

    abstract List<Favorite> findByFavorite(int uid, int start, int pageSize);


}
