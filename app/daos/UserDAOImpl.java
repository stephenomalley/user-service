package daos;

import com.avaje.ebean.Model.Finder;
import models.User;

/**
 * Created by somalley on 28/09/16.
 */
public class UserDAOImpl implements UserDAO {

    public Finder<Integer, User> finder;

    public UserDAOImpl(Finder<Integer, User> finder) {
        this.finder = finder;
    }

    @Override
    public User find(Integer id) {
        return this.finder.byId(id);
    }
}
