package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    abstract User findByUsername(String username);

    abstract void save(User user);

    User findByCode(String code);

    abstract void updateStatus(User user);

    User findByUsernameAndPassword(String username, String password);
}
