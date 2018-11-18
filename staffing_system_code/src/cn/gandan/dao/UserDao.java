package cn.gandan.dao;

import cn.gandan.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    public List<User> findAll();

    void addUser(User user);

    void deleteUser(int id);

    User findUserById (int id);

    void updateUser(User user);

    int findTotalCount(Map<String, String[]> map);

    List<User> findByPage(int start, int rows, Map<String, String[]> map);
}
