package cn.itcast.travel.service;

public interface FavoriteService {

    abstract boolean isFavorte(int rid, int uid);

    abstract void add(int rid, int uid);

}
