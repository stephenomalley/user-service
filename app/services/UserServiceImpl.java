package services;

import com.avaje.ebean.Model.Finder;
import com.google.inject.Inject;
import daos.UserDAO;
import models.User;

/**
 * Created by somalley on 28/09/16.
 */
public class UserServiceImpl implements UserService {

    public UserDAO userDAO;

    @Inject
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.userDAO.setFinder(new Finder<>(User.class));
    }

    @Override
    public User find(Integer id) {
        return userDAO.find(id);
    }

    @Override
    public User create(User user) {
        return userDAO.create(user);
    }
}
