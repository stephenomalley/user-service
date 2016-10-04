package services;

import models.User;

/**
 * Created by somalley on 28/09/16.
 */
public interface UserService {

    User find(Integer id);
    User create(User user);
}
