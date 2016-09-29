import com.avaje.ebean.Model.Finder;
import daos.UserDAOImpl;
import models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static play.test.Helpers.*;

/**
 * Created by somalley on 28/09/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestUserDAOImpl {

    @Mock
    public static Finder<Integer, User> mockFinder;

    @Test
    public void testFindUserReturnsUser() {
        running(fakeApplication(inMemoryDatabase()), () -> {
            User expected = new User("mock");
            UserDAOImpl dao = new UserDAOImpl(mockFinder);
            when(mockFinder.byId(1)).thenReturn(expected);
            User actual = dao.find(1);
            assertEquals(actual, expected);
        });
        verify(mockFinder).byId(1);
    }

    @Test
    public void testFindUserWhenUserMissing() {
        running(fakeApplication(inMemoryDatabase()), () -> {
            UserDAOImpl dao = new UserDAOImpl(mockFinder);
            when(mockFinder.byId(1)).thenReturn(null);
            User actual = dao.find(1);
            assertNull(actual);
        });
        verify(mockFinder).byId(1);
    }
}
