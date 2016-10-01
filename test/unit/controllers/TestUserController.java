package unit.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Result;
import play.test.WithApplication;
import services.UserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static play.mvc.Http.Status;
import static play.test.Helpers.contentAsString;

/**
 * Created by somalley on 30/09/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestUserController extends WithApplication {

    @Mock
    private UserService mockService;
    private UserController controller;

    @Before
    public void setup() {
        this.controller = new UserController(mockService);
    }

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
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
        assertTrue(contentAsString(response).contains(expected.get("errorCode").toString()));
        verify(mockService).find(1);
    }

    private ObjectNode getTestResponse() {
        ObjectNode expected = Json.newObject();
        expected.put("message", "User not found. You have requested an invalid user [1].");
        expected.put("errorCode", 100);
        expected.put("errorLink", "/errorcode/100");
        return expected;
    }


}
