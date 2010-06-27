package ua.com.myjava.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This controller is used to provide functionality for the login page.
 */

@Controller
public class LoginLogoutController extends BaseController{
	@RequestMapping(method=RequestMethod.GET,value="/login.do")
	public void home() {
	}
}