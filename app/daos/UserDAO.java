package daos;

import com.avaje.ebean.Model;
import models.User;

/**
 * Created by somalley on 28/09/16.
 */
public interface UserDAO {

    User find(Integer id);
    User create(User user);
    void setFinder(Model.Finder<Integer, User> finder);
}
