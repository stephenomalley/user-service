package controllers;

import com.avaje.ebean.annotation.Transactional;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import static utils.Constants.*;

/**
 * Created by somalley on 28/09/16.
 */
public class UserController extends Controller {

    private final UserService userService;
    private FormFactory formFactory;

    @Inject
    public UserController(UserService userService, FormFactory formFactory) {
        this.userService = userService;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result get(Integer id) {
        User user = userService.find(id);
        if (user == null) {
            return notFound(getErrorResponse(NOT_FOUND_MSG, 100, id)).as("application/json");
        }
        return ok(Json.toJson(user)).as("application/json");
    }

    @Transactional
    public Result create(){
        Form<User> userData = formFactory.form(User.class).bindFromRequest();
        if (userData.hasErrors()) {
            return badRequest(getErrorResponse(INVALID_DATA_MSG+userData.errorsAsJson(), 102, null)).as("application/json");
        }
        try {
            return created(Json.toJson(userService.create(userData.get())));
        } catch (PersistenceException e) {
            return badRequest(getErrorResponse(DUPLICATE_USER_MSG, 103, null)).as("application/json");
        }
    }

    private ObjectNode getErrorResponse(String message, int errorCode, Integer id) {
        ObjectNode body = Json.newObject();
	    String suffix = (id == null) ? "": "[" + id + "].";
        body.put("message", message + suffix);
        body.put("errorCode", errorCode);
        body.put("additionalInformation", request().host()+"/#/errordocs/"+errorCode);
        return body;
    }

}
