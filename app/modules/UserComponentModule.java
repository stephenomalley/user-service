package modules;

import com.google.inject.AbstractModule;
import daos.UserDAO;
import daos.UserDAOImpl;
import services.UserService;
import services.UserServiceImpl;

/**
 * Created by somalley on 02/10/16.
 */
public class UserComponentModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class);
        bind(UserDAO.class).to(UserDAOImpl.class);
    }
}
