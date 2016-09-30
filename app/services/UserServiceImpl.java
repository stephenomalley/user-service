package services;

import daos.UserDAO;
import models.User;

/**
 * Created by somalley on 28/09/16.
 */
public class UserServiceImpl implements UserService {

    public UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User find(Integer id) {
        return userDAO.find(id);
    }
}
