package daos;

import models.User;

/**
 * Created by somalley on 28/09/16.
 */
public interface UserDAO {

    public User find(Integer id);
}
