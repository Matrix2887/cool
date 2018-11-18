package cn.gandan.service;

import cn.gandan.domain.PageBean;
import cn.gandan.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    // 查询所有
    public List<User> findAll();

    // 保存用户
    void addUser(User user);

    // 根据id删除用户
    void deleteUser(int id);

    // 根据id查询用户
    User findUserById(int id);

    // 修改用户数据
    void updateUser(User user);

    // 批量删除用户的数据
    void delete(String[] id);

    // 分页查询
    PageBean<User> findUserByPage(int currentPage, int rows, Map<String, String[]> map);

}
