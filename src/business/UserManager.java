package business;

import dao.UserDao;
import entity.User;

public class UserManager {
    private final UserDao userDao;


    public UserManager() {
        this.userDao = new UserDao();
    }

    public User findByLogin(String userName, String password){
        return this.userDao.findByLogin(userName,password);
    }
}
