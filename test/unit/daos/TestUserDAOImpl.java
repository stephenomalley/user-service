package unit.daos;

import com.avaje.ebean.Model.Finder;
import daos.UserDAOImpl;
import models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

/**
 * Created by somalley on 28/09/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestUserDAOImpl {

    @Mock
    public static Finder<Integer, User> mockFinder;

    private UserDAOImpl dao;

    @Before
    public void setup() {
        dao = new UserDAOImpl();
        dao.setFinder(mockFinder);

    }

    @Test
    public void testFindUserReturnsUser() {
        User expected = new User();
        when(mockFinder.byId(1)).thenReturn(expected);
        User actual = dao.find(1);
        assertEquals(actual, expected);
        verify(mockFinder).byId(1);
    }

    @Test
    public void testFindUserWhenUserMissing() {
        when(mockFinder.byId(1)).thenReturn(null);
        User actual = dao.find(1);
        assertNull(actual);
        verify(mockFinder).byId(1);
    }

    @Test
    public void testCreateNewUser() {
        User user = mock(User.class);
        dao.create(user);
        verify(user).save();
    }

    @Test
    public void testFindAll() {
        when(mockFinder.findList()).thenReturn(new ArrayList<>());
        dao.findAll();
        verify(mockFinder).findList();
    }

    @Test
    public void testFindAllReturnsList() {
        when(mockFinder.findList()).thenReturn(Arrays.asList(new User[]{mock(User.class)}));
        List<User> actual = dao.findAll();
        assertEquals(1, actual.size());
        verify(mockFinder).findList();
    }

}
