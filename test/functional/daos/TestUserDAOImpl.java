package functional.daos;

import com.avaje.ebean.Model;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.typesafe.config.ConfigFactory;
import daos.UserDAO;
import daos.UserDAOImpl;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.ApplicationLoader;
import play.Configuration;
import play.Environment;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.guice.GuiceApplicationLoader;
import play.test.Helpers;
import services.UserService;
import services.UserServiceImpl;

import static org.junit.Assert.*;

/**
 * Created by somalley on 29/09/16.
 */
public class TestUserDAOImpl {

    private User user;
    private UserDAOImpl dao;

    @Inject
    Application application;

    @Before
    public void setup() {
        Module testModule = new AbstractModule() {
            @Override
            public void configure() {
                bind(UserService.class).to(UserServiceImpl.class);
                bind(UserDAO.class).to(UserDAOImpl.class);
            }
        };
        GuiceApplicationBuilder builder = new GuiceApplicationLoader()
                .builder(new ApplicationLoader.Context(Environment.simple()))
                .overrides(testModule)
                .loadConfig(new Configuration(ConfigFactory.load("application.test.conf")));
        Guice.createInjector(builder.applicationModule()).injectMembers(this);

        Helpers.start(application);
        user = new User();
        user.username = "test-username";
        user.save();
        dao = new UserDAOImpl();
        dao.setFinder(new Model.Finder<>(User.class));
    }

    @After
    public void teardown() {
        user.delete();
        Helpers.stop(application);
    }

    @Test
    public void testFindUserInDatabase() {
        User actual = dao.find(user.id);
        assertNotNull(actual);
    }

    @Test
    public void testFindUserInDatabaseCorrectUsername() {
        User actual = dao.find(user.id);
        assertEquals(actual.username, "test-username");
    }

    @Test
    public void testCantFindUserNotInDatabase() {
        User actual = dao.find(1000);
        assertNull(actual);
    }

    @Test
    public void testFindCorrectUserInDatabase() {
        User expected = new User();
        expected.save();
        User actual = dao.find(expected.id);
        assertEquals(expected, actual);
        expected.delete();
    }
}
