package controllers;

import com.avaje.ebean.annotation.Transactional;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;

import javax.inject.Inject;

import static utils.Constants.NOT_FOUND_MSG;

/**
 * Created by somalley on 28/09/16.
 */
public class UserController extends Controller {

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public Result get(Integer id) {
        User user = userService.find(id);
        if (user == null) {
            return notFound(getErrorResponse(NOT_FOUND_MSG, 100, id)).as("application/json");
        }
        return ok(Json.toJson(user)).as("application/json");
    }

    private ObjectNode getErrorResponse(String message, int errorCode, Integer id) {
        ObjectNode body = Json.newObject();
        body.put("message", message + " [" + id + "].");
        body.put("errorCode", errorCode);
        body.put("additionalInformation", routes.Assets.at("public/html/errorcode.html").absoluteURL(request()));
        return body;
    }

}
