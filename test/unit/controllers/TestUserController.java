package unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.UserController;
import models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import play.Application;
import play.data.Form;
import play.data.FormFactory;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import services.UserService;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static play.inject.Bindings.bind;
import static play.mvc.Http.Status;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeRequest;
/**
 * Created by somalley on 30/09/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestUserController extends WithApplication {

    private final UserService mockService = mock(UserService.class);

    private FormFactory mockForm;
    private UserController controller;

    @Before
    public void setup() {
        mockForm = mock(FormFactory.class, RETURNS_DEEP_STUBS);
        this.controller = new UserController(mockService, mockForm);
        Http.Context context = mock(Http.Context.class);
        when(context.request()).thenReturn(fakeRequest().build());
        Http.Context.current.set(context);
    }

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().overrides(bind(UserService.class).toInstance(mockService)).build();
    }

    @Test
    public void testGetReturnsResultNotFound() {
        when(mockService.find(1)).thenReturn(null);
        Result response = controller.get(1);
        assertEquals(Status.NOT_FOUND, response.status());
        verify(mockService).find(1);
    }

    @Test
    public void testGetReturnsErrorMessage() {
        when(mockService.find(1)).thenReturn(null);
        Result response = controller.get(1);
        ObjectNode expected = getTestResponse();
        assertTrue(contentAsString(response).contains(expected.get("message").toString()));
        verify(mockService).find(1);
    }

    @Test
    public void testGetReturnsErrorCode() {
        when(mockService.find(1)).thenReturn(null);
        Result response = controller.get(1);
        ObjectNode expected = getTestResponse();
        assertTrue(contentAsString(response).contains(expected.get("errorCode").toString()));
        verify(mockService).find(1);
    }

    @Test
    public void testGetReturnsErrorLink() {
        when(mockService.find(1)).thenReturn(null);
        Result response = controller.get(1);
        ObjectNode expected = getTestResponse();
        assertTrue(contentAsString(response).contains(expected.get("errorLink").toString()));
        verify(mockService).find(1);
    }

    @Test
    public void testGetReturnsUser() {
        User expected = new User();
        expected.username = "username";
        when(mockService.find(1)).thenReturn(expected);
        Result response = controller.get(1);
        assertEquals(expected.username, parseResponseData(response).username);
        verify(mockService).find(1);
    }

    @Test
    public void testGetReturnsSuccessStatus() {
        User expected = new User();
        expected.username = "username";
        when(mockService.find(1)).thenReturn(expected);
        Result response = controller.get(1);
        assertEquals(Status.OK, response.status());
        verify(mockService).find(1);
    }

    @Test
    public void testCreateReturnsCreated() {

        User expected = getTestUser("a", "a@b.com");
        User created = getTestUser("a", "a@b.com");
        created.id = 5;

        Form<User> userData = mock(Form.class);
        when(mockForm.form(User.class).bindFromRequest()).thenReturn(userData);
        when(userData.hasErrors()).thenReturn(false);
        when(userData.get()).thenReturn(expected);
        when(mockService.create(expected)).thenReturn(created);

        Result response = controller.create();

        assertEquals(Status.CREATED, response.status());

        verify(mockForm, times(2)).form(User.class);
        verify(mockForm.form(User.class)).bindFromRequest();
        verify(userData).hasErrors();
        verify(userData).get();
        verify(mockService).create(expected);
    }

    @Test
    public void testCreateReturnsUser() {

        User created = getTestUser("a", "a@b.com");
        created.id = 5;

        Form<User> userData = mock(Form.class);
        when(mockForm.form(User.class).bindFromRequest()).thenReturn(userData);
        when(userData.hasErrors()).thenReturn(false);
        when(userData.get()).thenReturn(created);
        when(mockService.create(created)).thenReturn(created);

        Result response = controller.create();
        try {
            ObjectMapper mapper = new ObjectMapper();
            User actual = mapper.readValue(contentAsString(response), User.class);
            assertEquals(actual, created);
        } catch (IOException e) {
            Assert.fail();
        } finally {
            verify(mockForm, times(2)).form(User.class);
            verify(mockForm.form(User.class)).bindFromRequest();
            verify(userData).hasErrors();
            verify(userData).get();
            verify(mockService).create(created);
        }

    }

    private User getTestUser(String username, String email) {
        User user = new User();
        user.username = username;
        user.email = email;
        return user;
    }

    private ObjectNode getTestResponse() {
        ObjectNode expected = Json.newObject();
        expected.put("message", "User not found. You have requested an invalid user[1].");
        expected.put("errorCode", 100);
        expected.put("errorLink", "localhost/#/errordocs/100");
        return expected;
    }

    private User parseResponseData(Result result) {
        return Json.fromJson(Json.parse(contentAsString(result)), User.class);
    }


}
