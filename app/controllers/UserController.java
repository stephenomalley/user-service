package controllers;

import com.avaje.ebean.annotation.Transactional;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;

import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;
import services.UserService;

import javax.inject.Inject;

import static utils.Constants.*;

/**
 * Created by somalley on 28/09/16.
 */
public class UserController extends Controller {

    private final UserService userService;
    private FormFactory formFactory;
//    private static Form<User> userForm = formFactory.form(User.class);

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
        return created(Json.toJson(userService.create(userData.get())));
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
