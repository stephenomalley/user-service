package daos;

import com.avaje.ebean.Model;
import models.User;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by somalley on 28/09/16.
 */
public interface UserDAO {

    User find(Integer id);

    List<User> findAll();
    User create(User user) throws PersistenceException;
    void setFinder(Model.Finder<Integer, User> finder);
}
