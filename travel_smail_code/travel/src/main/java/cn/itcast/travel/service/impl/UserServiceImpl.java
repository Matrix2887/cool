package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();

    // 注册用户
    @Override
    public boolean regist(User user) {

        User u = dao.findByUsername(user.getUsername());

        if(u == null){

            // 设置激活状态
            user.setStatus("N");

            // 获取激活码
            user.setCode(UuidUtil.getUuid());

            dao.save(user);

            String content = "<a href='http://localhost/travel/userServlet/activeUserServlet?code="+user.getCode()+"'>点击进行激活,如不是本人可以略过</a>";

            MailUtils.sendMail(user.getEmail(),content,"激活邮件(内部测试)");

            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean active(String code) {

        User user = dao.findByCode(code);

        if(user != null){

            dao.updateStatus(user);

            return true;

        }else{

            return false;

        }

    }

    @Override
    public User login(User user) {

        User use = dao.findByUsernameAndPassword(user.getUsername(),user.getPassword());

        return use;
    }
}
