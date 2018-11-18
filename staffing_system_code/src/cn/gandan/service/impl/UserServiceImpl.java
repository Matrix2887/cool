package cn.gandan.service.impl;

import cn.gandan.dao.impl.UserDaoImpl;
import cn.gandan.domain.PageBean;
import cn.gandan.domain.User;
import cn.gandan.service.UserService;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService{
    private UserDaoImpl userDao = new UserDaoImpl();
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

//    @Test
//    public void findAllTest() {
//        System.out.println(userDao.findAll());
//    }


    @Override
    public void addUser(User user) {
       userDao.addUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public User findUserById(int id) {
        User user = userDao.findUserById(id);
        return user;
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void delete(String[] ids) {
        if(ids != null && ids.length > 0){
            for (String id : ids) {
                userDao.deleteUser(Integer.parseInt(id));
            }
        }
    }

    /**
     * 分页条件查询的代码
     * @param currentPage 当前页码
     * @param rows 每页显示的条数
     * @param map<String, String[]> 每页显示的条数
     * @return 返回用户的数据
     */
    @Override
    public PageBean<User> findUserByPage(int currentPage, int rows, Map<String, String[]> map) {
        // 1.创建空的PageBean对象
        PageBean<User> pb = new PageBean<User>();

        // 2.设置参数
        pb.setRows(rows);
//        pb.setCurrentPage(currentPage);
        
        // 3 调用userDao查询总记录
        // 利用sql语句中的count得到总数
        int totalCount = userDao.findTotalCount(map);
//        System.out.println("totalCount" + totalCount);
        pb.setTotalCont(totalCount);

        // 5 计算总页码
        int totalPage = totalCount % rows == 0 ? (totalCount / rows):((totalCount / rows) + 1);
        System.out.println("总页码"+totalPage);
        pb.setTotalPage(totalPage);

        // 判断当前是第几页
        if(currentPage <= 0){
            currentPage = 1;
        }
        if(currentPage > totalPage){
            currentPage = totalPage;
        }
        // 2.设置参数
        pb.setCurrentPage(currentPage);

        // 4.调用userDao查询List集合 计算开始的记录索引
        int start = (currentPage - 1) * rows;
        List<User> list = userDao.findByPage(start,rows,map);
        pb.setList(list);



//        System.out.println(pb);

        return pb;
    }
}
