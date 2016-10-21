package unit.controllers;

import com.fasterxml.jackson.databind.JsonNode;
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

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static play.inject.Bindings.bind;
import static play.mvc.Http.Status;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeRequest;
import static utils.Constants.INVALID_DATA_MSG;
import static utils.Constants.PERSISTENCE_EX_MSG;

/**
 * Created by somalley on 30/09/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestUserController extends WithApplication {

    private final UserService mockService = mock(UserService.class);
    private ObjectMapper mapper = new ObjectMapper();

    private FormFactory mockForm;
    private UserController controller;

    private static String NOT_FOUND_MESSAGE = "User not found. You have requested an invalid user[1].";

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
        ObjectNode expected = getTestResponse(NOT_FOUND_MESSAGE, 100);
        assertTrue(contentAsString(response).contains(expected.get("message").toString()));
        verify(mockService).find(1);
    }

    @Test
    public void testGetReturnsErrorCode() {
        when(mockService.find(1)).thenReturn(null);
        Result response = controller.get(1);
        ObjectNode expected = getTestResponse("", 100);
        assertTrue(contentAsString(response).contains(expected.get("errorCode").toString()));
        verify(mockService).find(1);
    }

    @Test
    public void testGetReturnsErrorLink() {
        when(mockService.find(1)).thenReturn(null);
        Result response = controller.get(1);
        ObjectNode expected = getTestResponse("", 100);
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

    @Test
    public void testCreateBadRequestOnFormConversionError() {

        User created = getTestUser("a", "a@b.com");

        Form<User> userData = mock(Form.class);
        when(mockForm.form(User.class).bindFromRequest()).thenReturn(userData);
        when(userData.hasErrors()).thenReturn(true);
        when(userData.get()).thenReturn(created);
        when(mockService.create(created)).thenReturn(created);

        Result response = controller.create();
        assertEquals(Status.BAD_REQUEST, response.status());

        verify(mockForm, times(2)).form(User.class);
        verify(mockForm.form(User.class)).bindFromRequest();
        verify(userData).hasErrors();
        verify(userData, never()).get();
        verify(mockService, never()).create(created);
    }

    @Test
    public void testCreateBadRequestHasErrorCode() {
        Form<User> userData = mock(Form.class);

        try {
            JsonNode expected = (JsonNode) mapper.readTree(getTestResponse("", 102).toString());

            when(mockForm.form(User.class).bindFromRequest()).thenReturn(userData);
            when(userData.hasErrors()).thenReturn(true);
            when(userData.errorsAsJson()).thenReturn(expected);

            Result response = controller.create();

            JsonNode actual = (JsonNode) mapper.readTree(contentAsString(response));

            assertEquals(actual.get("errorCode"), expected.get("errorCode"));
        } catch (IOException e) {
            Assert.fail();
        } finally {
            verify(mockForm, times(2)).form(User.class);
            verify(mockForm.form(User.class)).bindFromRequest();
            verify(userData).hasErrors();
        }
    }


    @Test
    public void testCreateBadRequestHasErrorLink() {
        Form<User> userData = mock(Form.class);
        try {
            JsonNode expected = (JsonNode) mapper.readTree(getTestResponse("", 102).toString());

            when(mockForm.form(User.class).bindFromRequest()).thenReturn(userData);
            when(userData.hasErrors()).thenReturn(true);
            when(userData.errorsAsJson()).thenReturn(expected);

            Result response = controller.create();

            JsonNode actual = (JsonNode) mapper.readTree(contentAsString(response));

            assertEquals(actual.get("additionalInformation"), expected.get("errorLink"));
        } catch (IOException e) {
            Assert.fail();
        } finally {
            verify(mockForm, times(2)).form(User.class);
            verify(mockForm.form(User.class)).bindFromRequest();
            verify(userData).hasErrors();
        }
    }

    @Test
    public void testCreateBadRequestHasErrorMessage() {
        Form<User> userData = mock(Form.class);

        try {
            when(mockForm.form(User.class).bindFromRequest()).thenReturn(userData);
            when(userData.hasErrors()).thenReturn(true);
            when(userData.errorsAsJson()).thenReturn((JsonNode) mapper.readTree("\"username is required\""));

            Result response = controller.create();

            JsonNode actual = (JsonNode) mapper.readTree(contentAsString(response));

            assertTrue(actual.get("message").toString().contains(INVALID_DATA_MSG));
        } catch (IOException e) {
            Assert.fail();
        } finally {
            verify(mockForm, times(2)).form(User.class);
            verify(mockForm.form(User.class)).bindFromRequest();
            verify(userData).hasErrors();
            verify(userData).errorsAsJson();
        }
    }

    @Test
    public void testCreateDuplicateGeneratesDetailMessage() {
        User newUser = getTestUser("a", "a@b.com");
        Form<User> userData = mock(Form.class);
        when(mockForm.form(User.class).bindFromRequest()).thenReturn(userData);
        when(userData.hasErrors()).thenReturn(false);
        when(userData.get()).thenReturn(newUser);
        when(mockService.create(newUser)).thenThrow(mock(PersistenceException.class, RETURNS_DEEP_STUBS));

        try {
            when(mockForm.form(User.class).bindFromRequest()).thenReturn(userData);
            when(userData.hasErrors()).thenReturn(false);
            when(userData.errorsAsJson()).thenReturn((JsonNode) mapper.readTree("\"username is required\""));

            Result response = controller.create();

            JsonNode actual = (JsonNode) mapper.readTree(contentAsString(response));

            assertTrue(actual.get("message").get("description").toString().contains(PERSISTENCE_EX_MSG));
        } catch (IOException e) {
            Assert.fail("Failed to convert response to Json");
        } finally {
            verify(mockForm, atLeastOnce()).form(User.class);
            verify(mockForm.form(User.class)).bindFromRequest();
            verify(userData).hasErrors();
            verify(userData).get();
            verify(mockService).create(newUser);
        }
    }

    @Test
    public void testCreateDuplicateThrowsException() {
        User newUser = getTestUser("a", "a@b.com");

        Form<User> userData = mock(Form.class);
        when(mockForm.form(User.class).bindFromRequest()).thenReturn(userData);
        when(userData.hasErrors()).thenReturn(false);
        when(userData.get()).thenReturn(newUser);
        when(mockService.create(newUser)).thenThrow(mock(PersistenceException.class, RETURNS_DEEP_STUBS));

        Result response = controller.create();
        assertEquals(Status.BAD_REQUEST, response.status());

        verify(mockForm, atLeastOnce()).form(User.class);
        verify(mockForm.form(User.class)).bindFromRequest();
        verify(userData).hasErrors();
        verify(userData).get();
        verify(mockService).create(newUser);
    }

    @Test
    public void testFindAllReturnsStatusOk() {
        List<User> expected = Arrays.asList(new User[]{});
        when(mockService.findAll()).thenReturn(expected);
        Result response = controller.list();
        assertEquals(Status.OK, response.status());
    }

    @Test
    public void testFindAllReturnsEmptyJsonData() {
        List<User> expected = Arrays.asList(new User[]{});
        when(mockService.findAll()).thenReturn(expected);
        Result response = controller.list();

        try {
            JsonNode actual = (JsonNode) mapper.readTree(contentAsString(response));
            assertEquals(0, actual.size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testFindAllReturnsUserReturnsAsJson() {
        User testUser = getTestUser("davey", "davey@hola.com");
        List<User> expected = Arrays.asList(new User[]{testUser});
        when(mockService.findAll()).thenReturn(expected);
        Result response = controller.list();

        try {
            User[] actual = mapper.readValue(contentAsString(response), User[].class);
            assertEquals(testUser.getUsername(), actual[0].getUsername());
        } catch (IOException e) {
            fail();
        }
    }

    private User getTestUser(String username, String email) {
        User user = new User();
        user.username = username;
        user.email = email;
        return user;
    }

    private ObjectNode getTestResponse(String message, int code) {
        ObjectNode expected = Json.newObject();
        expected.put("message", message);
        expected.put("errorCode", code);
        expected.put("errorLink", "localhost/#/errordocs/" + code);
        return expected;
    }

    private User parseResponseData(Result result) {
        return Json.fromJson(Json.parse(contentAsString(result)), User.class);
    }


}
