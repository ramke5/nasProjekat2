package ba.nalaz.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ba.nalaz.AppConstants;
import ba.nalaz.model.core.Lab;
import ba.nalaz.model.core.ProductConstants;
import ba.nalaz.model.core.User;
import ba.nalaz.service.AccessControlManager;
import ba.nalaz.service.LabManager;
import ba.nalaz.service.MailEngine;
import ba.nalaz.service.UserManager;
import ba.nalaz.util.PasswordGenerator;
import ba.nalaz.web.form.UserForm;
import ba.nalaz.web.helper.Pagination;
import ba.nalaz.web.helper.PartialList;
import ba.nalaz.web.helper.ResourceForbiddenException;
import ba.nalaz.web.validation.UserValidator;

@Controller
@RequestMapping(value="/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserManager userManager;
    @Autowired
    private LabManager labManager;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private MailEngine mailEngine;
    @Autowired
    private AccessControlManager accessControlManager;

    @InitBinder("userForm")
    private void initBinderUser(WebDataBinder binder) {
    try {
    binder.setValidator(userValidator);
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
    }

    @RequestMapping(value="/create", method=RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ModelAndView createUser(final HttpServletRequest request) {
    LOGGER.info("createUser");
        ModelAndView mav = new ModelAndView("core/userCreate");
    try {
       User user = new User();
       UserForm userForm = new UserForm(user);
       user.setUserType(userManager.getUserType(1L));
       userForm.getUser().setEnabled(true);
       mav.addObject("userForm", userForm);
        } catch (ResourceForbiddenException e) {
        LOGGER.warn(ProductConstants.ACCESS_DENIED);
        throw e;
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ModelAndView createUser(@ModelAttribute @Valid UserForm userForm,
    final BindingResult bindingResult,
    final RedirectAttributes redirectAttributes,
    final HttpServletRequest request)  {
    LOGGER.info("userForm[{}]bindingResult[{}]", new Object[]{userForm, bindingResult});
        ModelAndView mav = new ModelAndView("redirect:/user/list");
        try {
       Integer statusReturn = userManager.isUserEmailExists(userForm.getUser().getEmail(), userForm.getUser().getUsername());
       if (statusReturn == 0) {
           bindingResult.rejectValue("user.email",null,"Email already exist");
           bindingResult.rejectValue("user.username",null,"Username already exist");
           return new ModelAndView("core/userCreate");
       } else if(statusReturn == 2) {
           bindingResult.rejectValue("user.email",null,"Email already exist");
           return new ModelAndView("core/userCreate");
       } else if(statusReturn == 3) {
           bindingResult.rejectValue("user.username",null,"Username already exist");
           return new ModelAndView("core/userCreate");
       }
       if (bindingResult.hasErrors()){
           return new ModelAndView("core/userCreate");
       }
       userForm.getUser().setUserType(userManager.getUserType(1L));
       userManager.saveUser(userForm.getUser());
       redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "User was successfully created.");
        } catch (ResourceForbiddenException e) {
        LOGGER.warn(ProductConstants.ACCESS_DENIED);
        throw e;
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ModelAndView getUsers(final HttpServletRequest request) {
    LOGGER.info("getUsers");
        ModelAndView mav = new ModelAndView("core/userList");
        try {
       Pagination pagination = new Pagination(request);
       PartialList partialList = userManager.getUsers(pagination);
       mav.addObject("search",pagination.getQuery());
       mav.addObject("total", partialList.getSize());
       mav.addObject("data", partialList.getList());
        } catch (ResourceForbiddenException e) {
        LOGGER.warn(ProductConstants.ACCESS_DENIED);
        throw e;
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ModelAndView editUser(@PathVariable("id") Long id,
    final HttpServletRequest request) {
    LOGGER.info("id[{}]", new Object[]{id});
        ModelAndView mav = new ModelAndView("core/userEdit");
        try {
       User user = userManager.getUser(id);
       UserForm userForm = new UserForm(user);
       mav.addObject("userForm", userForm);
        } catch (ResourceForbiddenException e) {
        LOGGER.warn(ProductConstants.ACCESS_DENIED);
        throw e;
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ModelAndView editUser(@ModelAttribute @Valid UserForm userForm,
    final BindingResult bindingResult,
    final RedirectAttributes redirectAttributes,
    final HttpServletRequest request)  {
    LOGGER.info("userForm[{}]bindingResult[{}]", new Object[]{userForm, bindingResult});
        ModelAndView mav = new ModelAndView("redirect:/user/list");
        try {
       Boolean statusReturn = userManager.isUserEmailExistsEdit(userForm.getUser().getId(),userForm.getUser().getEmail());
       if (statusReturn) {
           bindingResult.rejectValue("user.email",null,"Email already exist");
           return new ModelAndView("core/userEdit");
       }
       if (bindingResult.hasErrors()){
           return new ModelAndView("core/userEdit");
       }
       userForm.getUser().setUserType(userManager.getUserType(1L));
       userManager.saveUser(userForm.getUser());
       redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "User was successfully updated.");
        } catch (ResourceForbiddenException e) {
        LOGGER.warn(ProductConstants.ACCESS_DENIED);
        throw e;
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value="/delete", method=RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ModelAndView deleteUser(@RequestParam("selectedId") Long id,
    final RedirectAttributes redirectAttributes,
    final HttpServletRequest request) {
    LOGGER.info("id[{}]", new Object[]{id});
        ModelAndView mav = new ModelAndView("redirect:/user/list");
        try {
       User user = userManager.getUser(id);
       List<User> list = userManager.getUsers();
       if(list.size() > 1) {
       Boolean status = userManager.canDeleteUser(user);
       if(status){
           redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_INFO, "Can't delete. User is being used ");
       } else {
           userManager.removeUser(id);
           redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_INFO, "User was successfully deleted.");
       }
       } else {
           redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_INFO, "Can't delete.");
       }
        } catch (ResourceForbiddenException e) {
        LOGGER.warn(ProductConstants.ACCESS_DENIED);
        throw e;
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @SuppressWarnings("unchecked")
@RequestMapping(value = "/labUser/list/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN_KOMPANIJE')")
    public ModelAndView getLabUsers(@PathVariable("id") Long id,
    final HttpServletRequest request) {
    LOGGER.info("id[{}]", new Object[]{id});
        ModelAndView mav = new ModelAndView("labadmin/labUserList");
        try {
       User loggedInUser = (User) request.getAttribute(AppConstants.USER_OBJ);
       if (!accessControlManager.checkUserLabVsLab(loggedInUser,id)){
           throw new ResourceForbiddenException();
       }
       Pagination pagination = new Pagination(request);
       List<User> list = userManager.getLabUser(pagination, id);
       mav.addObject("search",pagination.getQuery());
       mav.addObject("total", list.size());
       mav.addObject("data", list);
        } catch (ResourceForbiddenException e) {
        LOGGER.warn(ProductConstants.ACCESS_DENIED);
        throw e;
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value="/labUser/create", method=RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN_KOMPANIJE')")
    public ModelAndView createLabUser(@RequestParam("labId") Long labId,
    final HttpServletRequest request) {
    LOGGER.info("labId[{}]", new Object[]{labId});
        ModelAndView mav = new ModelAndView("labadmin/labUserCreate");
        try {
       User loggedInUser = (User) request.getAttribute(AppConstants.USER_OBJ);
       if (!accessControlManager.checkUserLabVsLab(loggedInUser,labId)){
           throw new ResourceForbiddenException();
       }
       User user = new User();
       user.setUserType(userManager.getUserType(3L));
       UserForm userForm = new UserForm(user);
       userForm.getUser().setEnabled(true);
       userForm.setLabId(labId);
       mav.addObject("userForm", userForm);
        } catch (ResourceForbiddenException e) {
        LOGGER.warn(ProductConstants.ACCESS_DENIED);
        throw e;
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value="/labUser/create", method=RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN_KOMPANIJE')")
    public ModelAndView createLabUser(@ModelAttribute @Valid UserForm userForm,
    //@PathVariable("labId") Long labId,
    final BindingResult bindingResult,
    final RedirectAttributes redirectAttributes,
    final HttpServletRequest request)  {
    LOGGER.info("userForm[{}]bindingResult[{}]labId[{}]", new Object[]{userForm, bindingResult});
        ModelAndView mav = new ModelAndView("redirect:/user/labUser/list/"+userForm.getLabId());
        try {
       User loggedInUser = (User) request.getAttribute(AppConstants.USER_OBJ);
       if (!accessControlManager.checkUserLabVsLab(loggedInUser,userForm.getLabId())){
           throw new ResourceForbiddenException();
       }
       Integer statusReturn = userManager.isUserEmailExists(userForm.getUser().getEmail(), userForm.getUser().getUsername());
       if (statusReturn == 0) {
           bindingResult.rejectValue("user.email",null,"Email already exist");
           bindingResult.rejectValue("user.username",null,"Username already exist");
           return new ModelAndView("labadmin/labUserCreate");
       } else if(statusReturn == 2) {
           bindingResult.rejectValue("user.email",null,"Email already exist");
           return new ModelAndView("labadmin/labUserCreate");
       } else if(statusReturn == 3) {
           bindingResult.rejectValue("user.username",null,"Username already exist");
           return new ModelAndView("labadmin/labUserCreate");
       }
       User user=userForm.getUser();
       user.setLab(labManager.getLab(userForm.getLabId()));
       user.setUserType(userManager.getUserType(3L));
       if (bindingResult.hasErrors()){
           ModelAndView modelAndView = new ModelAndView("labadmin/labUserCreate");
           modelAndView.addObject("userForm", userForm);
           return modelAndView;
       }
       String mailerList = userForm.getUser().getEmail();
       if (userForm.getSystemGeneratedPassword()) {
           PasswordGenerator passwordGenerator = new PasswordGenerator();
           String sysGenPassword = passwordGenerator.generatePassword();
           userForm.getUser().setPassword(sysGenPassword);
       }
       if (userForm.getWelcomeEmail()) {
           Map<String,Object> model = new HashMap<String,Object>();
           model.put("user", userForm.getUser().getUsername());
           model.put("pass", userForm.getUser().getPassword());
           model.put("org", userForm.getUser().getLab().getName());
           model.put("first", userForm.getUser().getFirstName());
           model.put("last", userForm.getUser().getLastName());
           model.put("url", messageSource.getMessage("application.url", null, null));
           model.put("phoneNo", messageSource.getMessage("phone.no", null, null));
           mailEngine.sendMessage(mailerList, messageSource.getMessage("mail.default.from",null,null), "Welcome to nalaz", "welcomeemaillabuser.vm", model);
       }
       user.setAgreed(true);
       userManager.saveUser(user);
       redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "User was successfully created.");
        } catch (ResourceForbiddenException e) {
        LOGGER.warn(ProductConstants.ACCESS_DENIED);
        throw e;
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value = "/labUser/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN_KOMPANIJE')")
    public ModelAndView editLabUser(@PathVariable("id") Long id,
    final HttpServletRequest request) {
    LOGGER.info("id[{}]", new Object[]{id});
        ModelAndView mav = new ModelAndView("labadmin/labUserEdit");
        try {
    User loggedInUser = (User) request.getAttribute(AppConstants.USER_OBJ);
       User user = userManager.getUser(id);
       if (!accessControlManager.checkUserLabVsLab(loggedInUser,user.getLab().getId())){
           throw new ResourceForbiddenException();
       }
       UserForm userForm = new UserForm(user);
       mav.addObject("userForm", userForm);
        } catch (ResourceForbiddenException e) {
        LOGGER.warn(ProductConstants.ACCESS_DENIED);
        throw e;
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value = "/labUser/edit/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN_KOMPANIJE')")
    public ModelAndView editLabUser(@ModelAttribute @Valid UserForm userForm,
    final BindingResult bindingResult,
    final RedirectAttributes redirectAttributes,
    final HttpServletRequest request) {
    LOGGER.info("userForm[{}]bindingResult[{}]", new Object[]{userForm, bindingResult});
    ModelAndView mav = null;
    try {
       User loggedInUser = (User) request.getAttribute(AppConstants.USER_OBJ);
       User user = userManager.getUser(userForm.getUser().getId());
       Long labId = user.getLab().getId();
       if (!accessControlManager.checkUserLabVsLab(loggedInUser,labId)){
           throw new ResourceForbiddenException();
       }
       Boolean statusReturn = userManager.isUserEmailExistsEdit(userForm.getUser().getId(),userForm.getUser().getEmail());
       if (statusReturn) {
           bindingResult.rejectValue("user.email",null,"Email already exist");
           return new ModelAndView("labadmin/labUserEdit");
       }
       Lab org = (Lab)request.getAttribute(AppConstants.LAB_OBJ)   ;
       userForm.getUser().setLab(org);
       userForm.getUser().setUserType(userManager.getUserType(3L));
       userForm.getUser().setAgreed(true);
       if (bindingResult.hasErrors()) {
           ModelAndView modelAndView = new ModelAndView("labadmin/labUserEdit");
           modelAndView.addObject("userForm", userForm);
           return modelAndView;
       }
       mav = new ModelAndView("redirect:/user/labUser/list/" + labId);
       String mailerList = userForm.getUser().getEmail();
       if (userForm.getSystemGeneratedPassword()) {
           PasswordGenerator passwordGenerator = new PasswordGenerator();
           String sysGenPassword = passwordGenerator.generatePassword();
           userForm.getUser().setPassword(sysGenPassword);
       }
       if (userForm.getWelcomeEmail()) {
           Map<String,Object> model = new HashMap<String,Object>();
           model.put("user", userForm.getUser().getUsername());
           model.put("pass", userForm.getUser().getPassword());
           model.put("org", userForm.getUser().getLab().getName());
           model.put("first", userForm.getUser().getFirstName());
           model.put("last", userForm.getUser().getLastName());
           model.put("url", messageSource.getMessage("application.url", null, null));
           model.put("phoneNo", messageSource.getMessage("phone.no", null, null));
           mailEngine.sendMessage(mailerList, messageSource.getMessage("mail.default.from",null,null), "Welcome to nalaz", "updatelabuseremail.vm", model);
       }
       userManager.saveUser(userForm.getUser());
       redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "Korisnik laboratorije je uspješno izmijenjen.");
        } catch (ResourceForbiddenException e) {
        LOGGER.warn(ProductConstants.ACCESS_DENIED);
        throw e;
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value = "/labUser/delete", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN_KOMPANIJE')")
    public ModelAndView deleteLabUser(@RequestParam("selectedId") Long id,
    final RedirectAttributes redirectAttributes,
    final HttpServletRequest request) {
    LOGGER.info("id[{}]", new Object[]{id});
    ModelAndView mav = null;
    try {
    User loggedInUser = (User) request.getAttribute(AppConstants.USER_OBJ);
       User user = userManager.getUser(id);
       Long labId = user.getLab().getId();
       if (!accessControlManager.checkUserLabVsLab(loggedInUser,labId)){
           throw new ResourceForbiddenException();
       }
       mav = new ModelAndView("redirect:/user/labUser/list/" + labId);
       if(userManager.hasUserUsedAnyProducts(user)){
           redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_INFO, "Korisnik laboratorije ne može biti obrisan.");
       } else {
       userManager.removeUser(id);
       redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_INFO, "Korisnik laboratorije je uspješno obrisan.");
       }
        } catch (ResourceForbiddenException e) {
        LOGGER.warn(ProductConstants.ACCESS_DENIED);
        throw e;
        } catch (Exception e) {
       redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_ERROR, "Korisnik laboratorije nije uspješno obrisan.");
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value = "/checkEmail")
    @ResponseBody
    public Integer EmailCheck(@RequestParam("email") String email,
    @RequestParam("username") String username,
    final HttpServletRequest request) {
    LOGGER.info("email[{}]username[{}]", new Object[]{email, username});
        int status = 0;
        try {
            Integer statusReturn = userManager.isUserEmailExists(email, username);
            if (statusReturn == 0) {
                status = 0;
            } else if(statusReturn == 1) {
                status = 1;
            } else if(statusReturn == 2) {
                status = 2;
            } else {
                status = 3 ;
            }
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return status;
    }

    @RequestMapping(value="/changePassword/change", method=RequestMethod.GET)
    public ModelAndView changePassword() {
    LOGGER.info("changePassword");
        ModelAndView mav = new ModelAndView("core/changePassword");
        try {
       User user=new User();
       UserForm userForm = new UserForm(user);
       mav.addObject("userForm", userForm);
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value="/changePassword/change", method=RequestMethod.POST)
    public ModelAndView changePassword(@ModelAttribute UserForm userForm,
    final RedirectAttributes redirectAttributes,
    final HttpServletRequest request) {
    LOGGER.info("userForm[{}]", new Object[]{userForm});
        ModelAndView mav = new ModelAndView("core/changePassword");
    try {
    String password=userForm.getUser().getPassword();
       String confirmPassword= userForm.getConfirmPassword();
       if("".equals(password) && "".equals(confirmPassword)) {
           mav.addObject("userForm", userForm);
           mav.addObject(ProductConstants.MESSAGE_ERROR, "Enter password and confirm password");
       } else if("".equals(password)) {
           mav.addObject("userForm", userForm);
           mav.addObject(ProductConstants.MESSAGE_ERROR, "Password Required");
       } else if("".equals(confirmPassword)) {
           mav.addObject("userForm", userForm);
           mav.addObject(ProductConstants.MESSAGE_ERROR, "Confirm Password Required");
       } else if(!password.equals(confirmPassword)) {
           mav.addObject("userForm", userForm);
           mav.addObject(ProductConstants.MESSAGE_ERROR, "Check confirm password");
       } else {
       mav.addObject("userForm", userForm);
       User user = (User) request.getAttribute(AppConstants.USER_OBJ);
       user.setPassword(password);
       userManager.saveUser(user);
       mav.addObject(ProductConstants.MESSAGE_SUCCESS, "Password was successfully updated.");
       }
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }
}