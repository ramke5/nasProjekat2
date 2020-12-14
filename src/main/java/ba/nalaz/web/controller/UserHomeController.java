package ba.nalaz.web.controller;

import static ba.nalaz.model.core.ProductConstants.DEFAULT_MAIL;

import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ba.nalaz.AppConstants;
import ba.nalaz.model.core.ProductConstants;
import ba.nalaz.model.core.Role;
import ba.nalaz.model.core.User;
import ba.nalaz.service.MailEngine;
import ba.nalaz.service.UserManager;
import ba.nalaz.util.PasswordGenerator;
import ba.nalaz.util.ValidationKit;

@Controller
public class UserHomeController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserHomeController.class);
	@Autowired
	private UserManager userManager;
	@Autowired
	private MailEngine mailEngine;
	@Autowired
	private MessageSource messageSource;

    @ResponseBody
	@RequestMapping(value = "/keepalive", method = RequestMethod.GET)
	public String keepalive(HttpServletRequest request) {
		return "OK";
	}
    
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView welcome(HttpServletRequest request) {
		LOGGER.info("welcome");
		User user = (User) request.getAttribute(AppConstants.USER_OBJ);
		Set<Role> roles = user.getRoles();
		Role superAdminRole = userManager.getRole("ROLE_SUPER_ADMIN");
		if (roles.contains(superAdminRole)) {
			return new ModelAndView("redirect:/lab/list");
		}
		return new ModelAndView("redirect:/sample/list/" + user.getLab().getId());
	}

	@RequestMapping(value = "/userHome", method = RequestMethod.GET)
	public ModelAndView userHome(HttpServletRequest request) {
		LOGGER.info("userHome");
		return welcome(request);
	}

	@RequestMapping(value = "/forgotPassword")
	public ModelMap forgotPassword(HttpServletRequest request)
			throws MessagingException {
		LOGGER.info("forgotPassword");
		ModelMap model = new ModelMap();
		try {
			if ("POST".equals(request.getMethod())) {
				String email = request.getParameter("email");
				if ("".equals(email)) {
					model.addAttribute(ProductConstants.MESSAGE_ERROR,
							"Please enter email address");
				} else if (!ValidationKit.isValidEmailAddress(email)) {
					model.addAttribute(ProductConstants.MESSAGE_ERROR,
							"Please enter a valid email address");
				} else if (email.equals(DEFAULT_MAIL)) {
					model.addAttribute(ProductConstants.MESSAGE_ERROR, email
							+ " is not valid");
				} else {
					User user = userManager.getUserByEmail(email);
					if (user != null && user.isCredentialsNonExpired()
							&& user.isEnabled()) {
						PasswordGenerator passwordGenerator = new PasswordGenerator();
						String sysGenPassword = passwordGenerator
								.generatePassword();
						user.setPassword(sysGenPassword);
						model.put("firstName", user.getFirstName());
						model.put("lastName", user.getLastName());
						model.put("userName", user.getUsername());
						model.put("password", sysGenPassword);
						model.put("url", messageSource.getMessage(
								"application.url", null, null));
						mailEngine.sendMessage(email, "mail@mail.com",
								"Passwords Reset", "resetpasswordmail.vm",
								model);
						model.addAttribute(ProductConstants.MESSAGE_SUCCESS,
								"New password has been sent to your registered email address");
						userManager.saveUser(user);
					} else {
						model.addAttribute(ProductConstants.MESSAGE_INFO,
								"Couldn't find user matching your email address");
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
		}
		return model;
	}
}