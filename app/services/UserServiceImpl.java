package services;

import com.avaje.ebean.Model.Finder;
import com.google.inject.Inject;
import daos.UserDAO;
import models.User;

import javax.persistence.PersistenceException;
import java.util.List;

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
    public List<User> findAll() {
        return userDAO.findAll();
    }


    @Override
    public User create(User user) throws PersistenceException {
        return userDAO.create(user);
    }
}
