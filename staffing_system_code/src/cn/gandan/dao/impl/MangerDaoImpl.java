package cn.gandan.dao.impl;

import cn.gandan.dao.MangerDao;
import cn.gandan.domain.Manger;
import cn.gandan.domain.User;
import cn.gandan.util.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MangerDaoImpl implements MangerDao {

        private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());

        @Override
        public Manger login(Manger loginUser){
            try {
                String sql = "SELECT * FROM MANGER WHERE user=? AND password=? ";
                Manger manger = template.queryForObject(sql, new BeanPropertyRowMapper<>(Manger.class),
                        loginUser.getUser(),
                        loginUser.getPassword());
                return manger;
            } catch (DataAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
