package com.five.user.service;

import com.five.payment.model.Wallet;
import com.five.payment.service.PaymentService;
import com.five.user.dao.UserDao;
import com.five.user.model.MyMessage;
import com.five.user.model.User;
import com.five.user.utils.CodeUtil;
import com.five.user.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by haoye on 17-6-6.
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PaymentService paymentService;

    public MyMessage doRegister(User user) {
        if (user == null) {
            return new MyMessage(0, "user 不能为空");
        }
        String username = user.getUsername();
        String password  = user.getPassword();
        String email = user.getEmail();
        if (username == null) {
            return new MyMessage(0, "用户名不能为空");
        } else if (password == null) {
            return new MyMessage(0, "密码不能为空");
        } else if (email == null) {
            return new MyMessage(0, "邮箱不能为空");
        }
//        List<User> usersByUsername = userDao.findByUsername(user.getUsername());
        User usersByUsername = userDao.findByUsername(username);
        User usersByEmail = userDao.findbyEmail(email);
        if (usersByUsername != null) { // 没有找到，可以注册
            return new MyMessage(0, "用户名已存在");
        } else if (usersByEmail != null) {
            return new MyMessage(0, "邮箱已存在");
        }

        String code = CodeUtil.generateUniqueCode();
        new Thread(new MailUtil(email,code)).start();
        user.setCode(code);

        User registeredUser = userDao.save(user);

        MyMessage message = (MyMessage) paymentService.addWalletForNewUserById(registeredUser.getId(), 100);
        return new MyMessage(1, "注册成功," + message.getMessage());
    }

    public MyMessage doLogin(String username, String password, HttpSession session) {
        User users = userDao.findByUsername(username);
        MyMessage message = new MyMessage();
//        message.setStatus(0);
//        if (users.size() > 1) return new MyMessage(0,"用户名重复，请更改用户名");
//        User user = users.get(0);
//        if (users.size() == 0) {
//            message.setMessage("用户不存在");
//        } else if (!user.getPassword().equals(password)) {
//            message.setMessage("密码错误");
//        } else {
//            message.setStatus(1);
//            message.setMessage("登陆成功");
//            session.setAttribute("userId", user.getId());
//        }
        if (users == null) {
            message.setMessage("用户不存在");
        } else if (!users.getPassword().equals(password)) {
            message.setMessage("密码错误");
        } else {
            if (users.isState() == User.ACTIVE) {
                message.setStatus(1);
                message.setMessage("登陆成功");
                session.setAttribute("userId", users.getId());
            } else {
                message.setMessage("未激活");
            }
        }
        return message;
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        User users = userDao.findByUsername(username);
//        if (users.size() == 0) { // 没有找到，可以注册
//            return null;
//        } else if (users.size() > 1) {
//            return null;
//        }
        return users;
    }

    @Override
    public void reload() {
        userDao.reload();
    }

    @Override
    public boolean active(String code) {
        User user = userDao.findByCode(code);
        if (null != user) {
            user.setState(User.ACTIVE);
            user.setCode(null);
            userDao.save(user);
            return true;
        }
        return false;
    }

}
