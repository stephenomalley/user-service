package daos;

import com.avaje.ebean.Model.Finder;
import com.google.inject.Inject;
import models.User;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by somalley on 28/09/16.
 */
public class UserDAOImpl implements UserDAO {

    public Finder<Integer, User> finder;

    @Inject
    public UserDAOImpl() {
    }

    @Override
    public User find(Integer id) {
        return this.finder.byId(id);
    }

    @Override
    public List<User> findAll() {
        return this.finder.findList();
    }

    @Override
    public User create(User user) throws PersistenceException {
        user.save();
        return user;
    }


    public void setFinder(Finder<Integer, User> finder) {
        this.finder = finder;
    }
}
