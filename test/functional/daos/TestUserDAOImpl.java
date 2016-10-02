package functional.daos;

import com.avaje.ebean.Model;
import daos.UserDAOImpl;
import models.User;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

/**
 * Created by somalley on 29/09/16.
 */
public class TestUserDAOImpl extends WithApplication {

    @Before
    public void setup() {
        User user = new User("test-username");
        user.save();
    }

    @Test
    public void testFindUserInDatabase() {
        running(fakeApplication(inMemoryDatabase("test")), () -> {
            UserDAOImpl dao = new UserDAOImpl();
            dao.setFinder(new Model.Finder<>(User.class));
            User actual = dao.find(1);
            assertNotNull(actual);
        });
    }

    @Test
    public void testFindUserInDatabaseCorrectUsername() {
        running(fakeApplication(inMemoryDatabase("test")), () -> {
            UserDAOImpl dao = new UserDAOImpl();
            dao.setFinder(new Model.Finder<>(User.class));
            User actual = dao.find(1);
            assertEquals(actual.userName, "test-username");
        });
    }

    @Test
    public void testCantFindUserNotInDatabase() {
        running(fakeApplication(inMemoryDatabase("test")), () -> {
            UserDAOImpl dao = new UserDAOImpl();
            dao.setFinder(new Model.Finder<>(User.class));
            User actual = dao.find(1000);
            assertNull(actual);
        });
    }

    @Test
    public void testFindCorrectUserInDatabase() {
        running(fakeApplication(inMemoryDatabase("test")), () -> {
            User expected = new User();
            expected.save();
            UserDAOImpl dao = new UserDAOImpl();
            dao.setFinder(new Model.Finder<>(User.class));
            User actual = dao.find(expected.id);
            assertEquals(expected, actual);
        });
    }
}
