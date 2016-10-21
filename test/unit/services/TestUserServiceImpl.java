package unit.services;

import daos.UserDAO;
import models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import services.UserServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by somalley on 30/09/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestUserServiceImpl {

    @Mock
    public UserDAO mockDao;

    public UserServiceImpl service;

    @Before
    public void setup(){
        service = new UserServiceImpl(mockDao);
    }

    @Test
    public void testServiceCallsDao(){
        when(mockDao.find(1)).thenReturn(mock(User.class));
        service.find(1);
        verify(mockDao).find(1);
    }

    @Test
    public void testServiceReturnsUser(){
        when(mockDao.find(1000)).thenReturn(mock(User.class));
        User actual = service.find(1000);
        assertTrue(actual instanceof User);
        verify(mockDao).find(1000);
    }

    @Test
    public void testServiceReturnsNull(){
        when(mockDao.find(2)).thenReturn(null);
        User actual = service.find(2);
        assertNull(actual);
        verify(mockDao).find(2);
    }

    @Test
    public void testServiceCreatesUser() {
        User user = new User();
        when(mockDao.create(user)).thenReturn(user);
        User actual = service.create(user);
        assertEquals(user, actual);
        verify(mockDao).create(user);
    }

    @Test
    public void testServiceFindsAllUsers() {
        List<User> expected = Arrays.asList(new User[]{mock(User.class)});
        when(mockDao.findAll()).thenReturn(expected);
        List<User> actual = service.findAll();
        assertEquals(expected, actual);
        verify(mockDao).findAll();
    }
}
