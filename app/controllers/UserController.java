package controllers;

import com.avaje.ebean.annotation.Transactional;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;

import javax.inject.Inject;

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
        return null;
    }


}
