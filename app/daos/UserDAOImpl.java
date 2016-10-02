package daos;

import com.avaje.ebean.Model.Finder;
import com.google.inject.Inject;
import models.User;

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


    public void setFinder(Finder<Integer, User> finder) {
        this.finder = finder;
    }
}
