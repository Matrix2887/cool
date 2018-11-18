package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {

        Favorite favorite = null;

        try {

            String sql = "SELECT * FROM tab_favorite WHERE rid = ? AND uid = ?";

            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return favorite;

    }

    @Override
    public int findCountByRid(int rid) {

        String sql = "SELECT COUNT(*) FROM tab_favorite WHERE rid = ?";

        Integer count = template.queryForObject(sql, Integer.class, rid);

        return count;
    }

    @Override
    public void add(int rid, int uid) {

        String sql = "insert into tab_favorite values(?, ?, ?)";

        template.update(sql, rid, new Date(), uid);

    }

    @Override
    public int findCountByUid(int uid) {

        String sql = "SELECT COUNT(*) FROM tab_favorite WHERE uid = ?";

        Integer count = template.queryForObject(sql, Integer.class, uid);

        return count;
    }

    @Override
    public List<Favorite> findByFavorite(int uid, int start, int pageSize) {

        String sql = "SELECT * FROM tab_favorite WHERE uid = ? LIMIT ? , ?";


        List<Favorite> favorites = template.query(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), uid, start, pageSize);


/*
SELECT * FROM (SELECT r.`rname`, r.`price` price , r.rid , COUNT(f.rid) number FROM tab_route r LEFT JOIN tab_favorite f ON r.rid = f.rid GROUP BY r.rid ) e WHERE 7=7 AND price BETWEEN 999 AND 10200 AND rname LIKE '%西%'  ORDER BY  e.number DESC LIMIT 0 , 5
*/

/*
SELECT * , COUNT(f.rid) number FROM tab_route r LEFT JOIN tab_favorite f  ON 7=7 AND r.rid = f. rid AND r.rname LIKE '%西%' AND r.price BETWEEN 1089 AND 10200 GROUP BY r.rid ORDER BY  number DESC LIMIT 0 , 100
SELECT * , COUNT(f.rid) number FROM tab_route r LEFT JOIN tab_favorite f  ON 7=7 AND r.rid = f. rid AND r.rname LIKE    ?   AND r.price BETWEEN  ?   AND   ?   GROUP BY r.rid ORDER BY  number DESC LIMIT ? , ?
 */

        return favorites;

    }

}
