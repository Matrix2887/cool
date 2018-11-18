package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid, String rname) {

        int count = 0;
        try {

            String sql = "SELECT COUNT(*) FROM tab_route WHERE 7 = 7";

            StringBuilder sb = new StringBuilder(sql);

            List params = new ArrayList();

            if(cid != 0){

                sb.append(" AND cid = ? ");

                params.add(cid);

            }
            if(!"null".equals(rname) && rname.length()>0){

                sb.append(" AND rname LIKE ? ");

                params.add("%"+rname+"%");

            }

            sql = sb.toString();


            count = template.queryForObject(sql,Integer.class,params.toArray());

        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {

        List<Route> routes = null;
        try {

//            String sql = "SELECT * FROM tab_route WHERE cid = ? LIMIT ? , ?";

            // 注意SQL语句的拼接
            String sql = "SELECT * FROM tab_route WHERE 7 = 7";

            StringBuilder sb = new StringBuilder(sql);

            List params = new ArrayList();

            if(cid != 0){

                sb.append(" AND cid = ? ");

                params.add(cid);

            }
            if(!"null".equals(rname) && rname.length()>0){

                sb.append(" AND rname LIKE ? ");

                params.add("%"+rname+"%");

            }

            sb.append(" LIMIT ? , ? ");

            params.add(start);

            params.add(pageSize);

            sql = sb.toString();

            routes = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());

        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return routes;
    }

    @Override
    public Route findOne(int rid) {

        String sql = "SELECT * FROM tab_route WHERE rid = ?";

        Route route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);

        return route;
    }
}
