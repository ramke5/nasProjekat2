package ba.nalaz.web.controller;

import java.text.SimpleDateFormat;
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

import ba.nalaz.model.core.Contact;
import ba.nalaz.model.core.Lab;
import ba.nalaz.model.core.ProductConstants;
import ba.nalaz.model.core.User;
import ba.nalaz.service.MailEngine;
import ba.nalaz.service.LabManager;
import ba.nalaz.service.UserManager;
import ba.nalaz.web.form.UserForm;
import ba.nalaz.web.helper.ResourceForbiddenException;
import ba.nalaz.web.validation.UserValidator;

@Controller
@RequestMapping(value = "/signUp")
public class SignUpController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);
    @Autowired
    private UserManager userManager;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private LabManager labManager;
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

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public ModelAndView signUp(final HttpServletRequest request) {
    	LOGGER.info("signUp");
        ModelAndView mav = new ModelAndView("signUp/signUp");
        try {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        String currentDate = "'" + format.format(new Date()).toString() + "'";
	        mav.addObject("currentDate", currentDate);	        

	        User user = new User();
	        UserForm userForm = new UserForm(user);
	        mav.addObject("userForm", userForm);
        	
        } catch (Exception e){
        	LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value="/signUp", method=RequestMethod.POST)
    public ModelAndView signUp(
    		final @ModelAttribute @Valid UserForm userForm, 
    		final BindingResult bindingResult, 
    		final RedirectAttributes redirectAttributes,
    		final HttpServletRequest request)  {
    	LOGGER.info("userForm[{}]", new Object[]{userForm});
        ModelAndView mav = new ModelAndView("signUp/signUp");
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
	        }
	        if (bindingResult.hasErrors()) {
	            return mav;
	        }	        
	        mav = new ModelAndView("redirect:/signUp/success");	        	        
	        Lab lab = new Lab();
	        Contact userContact = new Contact();
	        userContact.setFirstName(userForm.getUser().getFirstName());
	        userContact.setLastName(userForm.getUser().getLastName());
	        userContact.setEmail(userForm.getUser().getEmail());
	        userContact.setMobileNumber(request.getParameter("phone"));
	        Contact contact = labManager.saveContact(userContact);
	        lab.setContact(contact);
	        lab.setEnabled(true);
	        lab.setDeleted(false);
	        lab.setCreatedDate(new Date());
	        lab.setModifiedDate(new Date());
	        String orgName = request.getParameter("org");
	        if (orgName == null || "".equals(orgName)) {
		        lab.setName(userForm.getUser().getUsername() + "-basic");
	        } else {
		        lab.setName(orgName);
	        }
	        lab = labManager.saveLab(lab);	        
	        User user = userForm.getUser();
	        user.setUserType(userManager.getUserType(4L));
	        user.setEnabled(false);
	        user.setLab(lab);
	        String activationKey = UUID.randomUUID().toString();
	        user.setActivationKey(activationKey);
	        // Keep plain text password for email
	        String plainTextPassword = user.getPassword();
	        user = userManager.saveUser(user);
	        
	        lab.setCreatedUser(user);
	        lab.setModifiedUser(user);
	        labManager.saveLab(lab);

        	Map<String,Object> model = new HashMap<String,Object>();
            model.put("user", user.getUsername());
            model.put("pass", plainTextPassword);
            model.put("first", user.getFirstName());
            model.put("last", user.getLastName());
            model.put("url", messageSource.getMessage("application.url", null, null));
            model.put("phoneNo", messageSource.getMessage("phone.no", null, null));
            model.put("activationKey", activationKey);            
            mailEngine.sendMessage(user.getEmail(), messageSource.getMessage("mail.default.from", null, null), "Welcome to nalaz", "welcomeemailuser.vm", model);
	        
	        redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "You have successfully signed up. Check your inbox for an activation link.");
	        redirectAttributes.addFlashAttribute("showEmailLink", true);
        } catch (ResourceForbiddenException e) {
        	LOGGER.warn(ProductConstants.ACCESS_DENIED);
        	throw e;
        } catch (Exception e) {
        	LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView success(final RedirectAttributes redirectAttributes, final HttpServletRequest request) {
    	LOGGER.info("success");
        return new ModelAndView("signUp/success");
    }

    @RequestMapping(value = "/activate/{username}/{activationKey}", method = RequestMethod.GET)
    public ModelAndView activate(
    		final @PathVariable("username") String username, 
    		final @PathVariable("activationKey") String activationKey, 
    		final RedirectAttributes redirectAttributes, 
    		final HttpServletRequest request) {
    	LOGGER.info("username[{}]activationKey[{}]", new Object[]{username, activationKey});
        ModelAndView mav = new ModelAndView("redirect:/welcome");
        try {
        	User user = userManager.getUserByUsername(username);
        	if (user != null && user.getActivationKey().equals(activationKey)) {
    	        userManager.activateUser(user);
    	        authenticateUserAndSetSession(user, request);
        	} else {
        		redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_ERROR, "Invalid activation key.");
            	redirectAttributes.addFlashAttribute("showEmailLink", true);
        	}
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
        return new ModelAndView("signUp/resendActivation");
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
		        mailEngine.sendMessage(user.getEmail(), messageSource.getMessage("mail.default.from", null, null), "Welcome to nalaz", "activateaccount.vm", model);
		    	redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "Activation link has been sent to your email. Click on it to activate your account.");
	    	} else {
	        	redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_ERROR, "Email address not found.");
	    	}
        } catch (Exception e){
        	LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        	redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_ERROR, "Activation link could not be sent.");
        }
        return new ModelAndView("redirect:/signUp/activateAccount");
    }
    
    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

        // generate session if one doesn't exist
        request.getSession();

        SecurityContextHolder.getContext().setAuthentication(token);
    }
}