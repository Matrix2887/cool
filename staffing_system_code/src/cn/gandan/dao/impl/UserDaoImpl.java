package cn.gandan.dao.impl;

import cn.gandan.dao.UserDao;
import cn.gandan.domain.User;
import cn.gandan.util.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());

    @Override
    public List<User> findAll() {

        try {
            String sql = "SELECT * FROM user";

            List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));

            return users;
        } catch (DataAccessException e) {

            return null;

        }

    }

    @Override
    public void addUser(User user) {
        String sql = "  INSERT INTO USER VALUE(NULL,?,?,?,?,?,?)";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM USER WHERE id=?";
        template.update(sql,id);
    }

    @Override
    public User findUserById(int id) {
        String sql = "SELECT * FROM USER WHERE id=?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        return user;
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE USER SET name=?,gender=?,age=?,address=?,qq=?,email=? WHERE id=?";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> map) {
        String sql = "SELECT count(*) from user where 7=7";

        StringBuilder sb = new StringBuilder(sql);

        // 定义参数集合
        List<Object> params = new ArrayList<Object>();

        Set<String> keySet = map.keySet();

        for (String key : keySet) {
            // 排除多余的参数
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            // 获取value
            String value = map.get(key)[0];

            if(value != null && !"".equals(value)){
                // 有数据
                sb.append(" and "+key+" like ? ");
                // 添加参数
                params.add("%"+value+"%");
            }
        }
        System.out.println(sb.toString());
        System.out.println(params);

        return template.queryForObject(sb.toString(),Integer.class,params.toArray());
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> map) {
        String sql = "SELECT * FROM user where 7=7";

        StringBuilder sb = new StringBuilder(sql);

        // 定义参数集合
        List<Object> params = new ArrayList<Object>();

        Set<String> keySet = map.keySet();

        for (String key : keySet) {
            // 排除多余的参数
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            // 获取value
            String value = map.get(key)[0];

            if(value != null && !"".equals(value)){
                // 有数据
                sb.append(" and "+key+" like ? ");
                // 添加参数
                params.add("%"+value+"%");
            }
        }

        sb.append(" limit ? , ?");

        params.add(start);
        params.add(rows);

        sql = sb.toString();
        System.out.println(sql);
        System.out.println(params);

        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),params.toArray());
    }


}
