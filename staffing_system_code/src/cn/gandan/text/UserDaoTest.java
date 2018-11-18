package cn.gandan.text;


import cn.gandan.dao.impl.MangerDaoImpl;
import cn.gandan.domain.Manger;
import org.junit.Test;

public class UserDaoTest {

    @Test
    public void testLogin(){
        Manger loginuser = new Manger("shengli520","5201314");

        MangerDaoImpl userDao = new MangerDaoImpl();
        Manger login = userDao.login(loginuser);
        System.out.println(login);
    }
}