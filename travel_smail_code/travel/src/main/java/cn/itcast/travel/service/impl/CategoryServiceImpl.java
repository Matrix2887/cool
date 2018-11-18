package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll(){

        Jedis jedis = JedisUtil.getJedis();

        Set<Tuple> category = null;

        try {
            category = jedis.zrangeWithScores("category", 0, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Category> categories = null;

        if(category.size() == 0 || category == null){

//            System.out.println("从数据库查询");

            categories = dao.findAll();

            for (Category cs : categories) {

            jedis.zadd("category",cs.getCid(),cs.getCname());

            }

        }else {

//            System.out.println("从内存中查询");

            categories = new ArrayList<Category>();

            for (Tuple tuple : category) {

                categories.add(new Category((int)tuple.getScore(),tuple.getElement()));

            }

        }

        return categories;

    }
}
