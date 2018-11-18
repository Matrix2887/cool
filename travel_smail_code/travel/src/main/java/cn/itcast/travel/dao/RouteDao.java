package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     * 根据cid查询总记录数
     * @param cid 旅游线路的id
     * @param rname
     * @return
     */
    abstract int findTotalCount(int cid, String rname);

    /**
     * 根据参数查询显示的对象
     * @param cid 旅游线路的id
     * @param start 查询对象起始的id
     * @param pageSize 查询的个数
     * @return
     */
    abstract List<Route> findByPage(int cid, int start, int pageSize, String rname);

    abstract Route findOne(int rid);
}
