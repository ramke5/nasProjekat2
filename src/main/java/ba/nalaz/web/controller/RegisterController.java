package ba.nalaz.web.controller;

import static ba.nalaz.model.core.ProductConstants.DEFAULT_MAIL;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ba.nalaz.model.core.ProductConstants;
import ba.nalaz.model.core.User;
import ba.nalaz.service.MailEngine;
import ba.nalaz.service.UserManager;
import ba.nalaz.web.form.UserForm;
import ba.nalaz.web.validation.UserValidator;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private UserManager userManager;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private MailEngine mailEngine;
    @Autowired
    private MessageSource messageSource;

    @InitBinder("userForm")
    private void initBinderUser(WebDataBinder binder) {
    	try {
    		binder.setValidator(userValidator);
        } catch (Exception e) {
        	LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView register(final HttpServletRequest request) {
    	LOGGER.info("register");
        ModelAndView mav = new ModelAndView("register/register");
        try {
	        User user = new User();
	        UserForm userForm = new UserForm(user);
	        mav.addObject("userForm", userForm);        	
        } catch (Exception e){
        	LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView register(
    		final @ModelAttribute @Valid UserForm userForm, 
    		final BindingResult bindingResult, 
    		final RedirectAttributes redirectAttributes,
    		final HttpServletRequest request)  {
    	LOGGER.info("register");
        ModelAndView mav = new ModelAndView("register/register");
        try {
        	Integer statusReturn = userManager.isUserEmailExists(userForm.getUser().getEmail(), userForm.getUser().getUsername());
	        if (statusReturn == 0) {
	            bindingResult.rejectValue("user.email",null,"Email already exists.");
	            bindingResult.rejectValue("user.username",null,"Username already exists");
	            return mav;
	        } else if (statusReturn == 2) {
	            bindingResult.rejectValue("user.email",null,"Email already exists.");
	            return mav;
	        } else if (statusReturn == 3) {
	            bindingResult.rejectValue("user.username",null,"Username already exists.");
	            return mav;
	        } else if (statusReturn == 1 && userForm.getUser().getEmail().equals(DEFAULT_MAIL)) {
	            bindingResult.rejectValue("user.email",null,"Email already exists.");
	        }
	        if (bindingResult.hasErrors()) {
	            return mav;
	        }
	        mav = new ModelAndView("redirect:/register/success");        
	        User user = userForm.getUser();
	        user.setUserType(userManager.getUserType(4L));
	        user.setEnabled(false);
	        String activationKey = UUID.randomUUID().toString();
	        user.setActivationKey(activationKey);
	        user.setCreatedDate(new Date());
	        user = userManager.saveUser(user);
        	Map<String,Object> model = new HashMap<String,Object>();
            model.put("email", user.getEmail());
            model.put("first", user.getFirstName());
            model.put("last", user.getLastName());
            model.put("company", user.getCompany());
            model.put("url", messageSource.getMessage("application.url", null, null));
	        model.put("phoneNo", messageSource.getMessage("phone.no", null, null));
            model.put("activationKey", activationKey);
            mailEngine.sendMessage(user.getEmail(), messageSource.getMessage("mail.default.from", null, null), "Thanks for Registering for nalaz", "stripeuserwelcome.vm", model);
	        redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "You have successfully registered. Check your inbox for an activation link.");
	        redirectAttributes.addFlashAttribute("showEmailLink", true);
        } catch (Exception e){
        	LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }
    
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView success(final RedirectAttributes redirectAttributes, final HttpServletRequest request) {
    	LOGGER.info("success");
        return new ModelAndView("register/success");
    }

    @RequestMapping(value = "/activate/{email}/{activationKey}", method = RequestMethod.GET)
    public ModelAndView activate(
    		final @PathVariable("email") String email, 
    		final @PathVariable("activationKey") String activationKey, 
    		final RedirectAttributes redirectAttributes, 
    		final HttpServletRequest request) {
    	LOGGER.info("activate");
    	
    	User user = userManager.getUserByEmail(email);
    	if(user==null)
    	{
    		redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_ERROR, "Invalid email.");
    		ModelAndView mav = new ModelAndView("redirect:/login");
        	return mav;
    	}
    	if(user.getActivationKey()==null){
    		redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_INFO, "You are already registered.");
    		ModelAndView mav = new ModelAndView("redirect:/login");
        	return mav;
    	}
    	else{
	        ModelAndView mav = new ModelAndView("register/activate");
	        try {
	        	user = userManager.getUserByEmail(email);     		
	        	if (user != null && user.getActivationKey().equals(activationKey)) {
	//    	        userManager.activateUser(user);
	        	} else {
	        		redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_ERROR, "Invalid activation key.");
	        		mav = new ModelAndView("redirect:/login");
	            	return mav;
	        	}
		        UserForm userForm = new UserForm(user);
		        mav.addObject("userForm", userForm);        	
	        } catch (Exception e){
	        	LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
	        }
	        return mav;
    	}
    }

    @RequestMapping(value = "/activate/{email}/{activationKey}", method = RequestMethod.POST)
    public ModelAndView activate(
    		final @PathVariable("email") String email, 
    		final @PathVariable("activationKey") String activationKey, 
    		final @ModelAttribute @Valid UserForm userForm, 
    		final BindingResult bindingResult, 
    		final RedirectAttributes redirectAttributes,
    		final HttpServletRequest request)  {
    	LOGGER.info("activate");

        ModelAndView mav = new ModelAndView("register/activate");
        if (bindingResult.hasErrors()) {
            return mav;
        }
        try {
        	User userFormUser = userForm.getUser();
        	User user = userManager.getUserByEmail(userFormUser.getEmail());
        	mav = new ModelAndView("redirect:/register/activate/" + email + "/" + activationKey);
        	if(user == null){
        		redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_ERROR, "Invalid email address. Please Check the registration link in the email.");
        		return mav;
        	}
        	if (user != null 
        			&& userFormUser != null
        			&& !user.getActivationKey().equals(activationKey)) { 
        		redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_ERROR, "Invalid activation key.");
        		return mav;
        	}	

    		user.setEmail(userFormUser.getEmail());
    		user.setPassword(userFormUser.getPassword());
    		user.setFirstName(userFormUser.getFirstName());
    		user.setLastName(userFormUser.getLastName());
    		user.setPhoneNumber(userFormUser.getPhoneNumber());
    		user.setEmail(userFormUser.getEmail());
    		user.setCompany(userFormUser.getCompany());
    		user.setActivationDate(new Date());
    		user.setAgreed(true);
	        user = userManager.saveUser(user);
	        
	        userManager.activateUser(user);
	        authenticateUserAndSetSession(user, request);
	        
	        Map<String,Object> model = new HashMap<String,Object>();
            model.put("email", user.getEmail());
            model.put("first", user.getFirstName());
            model.put("last", user.getLastName());
            model.put("company", user.getCompany());
            model.put("url", messageSource.getMessage("application.url", null, null));
	        model.put("phoneNo", messageSource.getMessage("phone.no", null, null));
            model.put("activationKey", activationKey);            
            mailEngine.sendMessage(user.getEmail(), messageSource.getMessage("mail.default.from", null, null), "Thanks for Completing Registration for nalaz", "stripeusercompletedregistration.vm", model);
	        mav = new ModelAndView("redirect:/welcome");	
        } catch (Exception e){
        	LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }
    
    @RequestMapping(value = "/activateAccount", method = RequestMethod.GET)
    public ModelAndView activateAccount(
    		final RedirectAttributes redirectAttributes, 
    		final HttpServletRequest request) {
    	LOGGER.info("activateAccount");
        return new ModelAndView("register/resendActivation");
    }
    
    @RequestMapping(value = "/activateAccount", method = RequestMethod.POST)
    public ModelAndView resendActivation(
    		final RedirectAttributes redirectAttributes, 
    		final HttpServletRequest request) {
    	LOGGER.info("resendActivation");
    	try {
	    	User user = userManager.getUserByEmail(request.getParameter("email").toString());
	    	if (user != null) {
	    		if (user.isEnabled()) {
	            	redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_ERROR, "Account is already activated.");
	                return new ModelAndView("redirect:/register/activateAccount");
                }
		    	Map<String,Object> model = new HashMap<String,Object>();
	            model.put("email", user.getEmail());
		        model.put("user", user.getUsername());
		        model.put("first", user.getFirstName());
		        model.put("last", user.getLastName());
		        model.put("url", messageSource.getMessage("application.url", null, null));
		        model.put("phoneNo", messageSource.getMessage("phone.no", null, null));
		        if (user.getActivationKey() == null || user.getActivationKey().isEmpty()) {
		        	user.setActivationKey(UUID.randomUUID().toString());
		            user.setPassword(null);
		        	userManager.saveUser(user);
		        }
		        model.put("activationKey", user.getActivationKey());
	            mailEngine.sendMessage(user.getEmail(), messageSource.getMessage("mail.default.from", null, null), "Thanks for Registering for nalaz", "stripeuserwelcome.vm", model);
	            redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "Activation link has been sent to your email. Click on it to activate your account.");
	    	} else {
	        	redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_ERROR, "Email address not found.");
	    	}
        } catch (Exception e){
        	LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        	redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_ERROR, "Activation link could not be sent.");
        }
        return new ModelAndView("redirect:/register/activateAccount");
    }
    
    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        // Generate session if one doesn't exist
        request.getSession();
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}