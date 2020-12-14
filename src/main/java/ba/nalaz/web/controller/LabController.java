package ba.nalaz.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ba.nalaz.AppConstants;
import ba.nalaz.model.core.Contact;
import ba.nalaz.model.core.Lab;
import ba.nalaz.model.core.ProductConstants;
import ba.nalaz.model.core.User;
import ba.nalaz.service.LabManager;
import ba.nalaz.service.MailEngine;
import ba.nalaz.service.UserManager;
import ba.nalaz.util.PasswordGenerator;
import ba.nalaz.web.form.UserForm;
import ba.nalaz.web.helper.Pagination;
import ba.nalaz.web.helper.PartialList;
import ba.nalaz.web.validation.LabValidator;
import ba.nalaz.web.validation.UserValidator;

@Controller
@RequestMapping(value = "/lab")
public class LabController {
private static final Logger LOGGER = LoggerFactory.getLogger(LabController.class);
   
    @Autowired
    private LabManager labManager;
    @Autowired
    private MailEngine mailEngine;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private LabValidator labValidator;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private UserManager userManager;

    @InitBinder("lab")
    private void initBinder(WebDataBinder binder) {
    try {
    binder.setValidator(labValidator);
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
    }

    @InitBinder("userForm")
    private void initBinderUser(WebDataBinder binder) {
    try {
    binder.setValidator(userValidator);
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ModelAndView createLab(HttpServletRequest request) {
        LOGGER.info("createLab");
        ModelAndView mav = new ModelAndView("core/labCreate");
        try {
       Lab lab = new Lab();
       lab.setContact(new Contact());
       mav.addObject("lab", lab);
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ModelAndView createLab(@ModelAttribute @Valid Lab lab, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        LOGGER.info("lab[{}]bindingResult[{}]", new Object[]{lab, bindingResult});
        ModelAndView mav = new ModelAndView("redirect:/lab/list");
        try {
       User user = (User) request.getAttribute(AppConstants.USER_OBJ);
       if (bindingResult.hasErrors()) {
           return new ModelAndView("core/labCreate");
       }
       Contact contact;
       contact = labManager.saveContact(lab.getContact());
       lab.setContact(contact);
       lab.setEnabled(true);
       lab.setDeleted(false);
       lab.setCreatedDate(new Date());
       lab.setCreatedUser(user);
       lab.setModifiedUser(user);
       lab.setModifiedDate(new Date());
       labManager.saveLab(lab);
       redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "Laboratorija je uspješno kreirana.");
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ModelAndView getLab(HttpServletRequest request) {
        LOGGER.info("getLab");
        ModelAndView mav = new ModelAndView("core/labList");
        try {
       Pagination pagination = new Pagination(request);
       PartialList partialList = labManager.getLabList(pagination);
       mav.addObject("search", pagination.getQuery());
       mav.addObject("total", partialList.getSize());
       mav.addObject("data", partialList.getList());
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ModelAndView editLab(@PathVariable("id") Long id, HttpServletRequest request) {
        LOGGER.info("id[{}]", new Object[]{id});
        ModelAndView mav = new ModelAndView("core/labEdit");
        try {
       Lab lab = labManager.getLab(id);
       mav.addObject("lab", lab);
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ModelAndView editLab(@ModelAttribute @Valid Lab lab, @PathVariable("id") Long id,
    BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        LOGGER.info("lab[{}]id[{}]bindingResult[{}]", new Object[]{lab, id, bindingResult});
        ModelAndView mav = null;
        try {
       User user = (User) request.getAttribute(AppConstants.USER_OBJ);
       if (bindingResult.hasErrors()) {
           return new ModelAndView("core/labEdit");
       }
       mav = new ModelAndView("redirect:/lab/edit/" + lab.getId());
       Contact contact;
       contact = labManager.saveContact(lab.getContact());
       lab.setContact(contact);
       lab.setEnabled(true);
       lab.setDeleted(false);
       lab.setModifiedUser(user);
       lab.setModifiedDate(new Date());
       labManager.saveLab(lab);
       redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "Laboratorija je uspješno izmijenjena.");
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteLab(@RequestParam("selectedId") Long id, final RedirectAttributes redirectAttributes, HttpServletRequest request) {
        LOGGER.info("id[{}]", new Object[]{id});
        ModelAndView mav = new ModelAndView("redirect:/lab/list");
        try {
       Lab org = labManager.getLab(id);
       Boolean status1 = labManager.canDeleteLab(org);
       if (status1) {
           redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_INFO, "Nije moguće obrisati. Laboratorija se koristi.");
       } else {
           labManager.removeLab(id);
           redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_INFO, "Laboratorija je uspješno obrisana.");
       }
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/labAdministrator/list/{labId}", method = RequestMethod.GET)
    public ModelAndView getLabAdministrator(@PathVariable("labId") Long labId, HttpServletRequest request) {
        LOGGER.info("labId[{}]", new Object[]{labId});
        ModelAndView mav = new ModelAndView("core/labAdministratorList");
        try {
       Pagination pagination = new Pagination(request);
       PartialList partialList = labManager.getLabAdministratorList(pagination, labId);
       Lab lab = labManager.getLab(labId);
       mav.addObject("search", pagination.getQuery());
       mav.addObject("total", partialList.getSize());
       mav.addObject("data", partialList.getList());
       mav.addObject("lab", lab);
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/create/labAdministrator/{labId}", method = RequestMethod.GET)
    public ModelAndView createLabAdministrator(@PathVariable("labId") Long labId, HttpServletRequest request) {
        LOGGER.info("labId[{}]", new Object[]{labId});
        ModelAndView mav = new ModelAndView("core/labAdministratorCreate");
        try {
       Lab lab = labManager.getLab(labId);
       UserForm userForm = new UserForm(new User());
       userForm.getUser().setLab(lab);
       userForm.getUser().setUserType(userManager.getUserType(2L));
       userForm.getUser().setEnabled(true);
       mav.addObject("userForm", userForm);
       mav.addObject("lab", lab);
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/create/labAdministrator/{labId}", method = RequestMethod.POST)
    public ModelAndView createLabAdministrator(@ModelAttribute @Valid UserForm userForm,
    BindingResult bindingResult, RedirectAttributes redirectAttributes, @PathVariable("labId") Long labId, HttpServletRequest request) throws MessagingException {
        LOGGER.info("userForm[{}]bindingResult[{}]labId[{}]", new Object[]{userForm, bindingResult, labId});
        ModelAndView mav = null;
        try {
       Lab lab = labManager.getLab(labId);
       Integer statusReturn = userManager.isUserEmailExists(userForm.getUser().getEmail(), userForm.getUser().getUsername());
       if (statusReturn == 0) {
           mav = new ModelAndView("core/labAdministratorCreate");
           bindingResult.rejectValue("user.email", null, "Email already exist");

           bindingResult.rejectValue("user.username", null, "Username already exist");
           mav.addObject("userForm", userForm);
           mav.addObject("lab", lab);
           return mav;
       } else if (statusReturn == 2) {
           mav = new ModelAndView("core/labAdministratorCreate");
           bindingResult.rejectValue("user.email", null, "Email already exist");
           mav.addObject("userForm", userForm);
           mav.addObject("lab", lab);
           return mav;
       } else if (statusReturn == 3) {
           mav = new ModelAndView("core/labAdministratorCreate");
           bindingResult.rejectValue("user.username", null, "Username already exist");

           mav.addObject("userForm", userForm);
           mav.addObject("lab", lab);
           return mav;
       }
       if (bindingResult.hasErrors()) {
           ModelAndView modelAndView = new ModelAndView("core/labAdministratorCreate");
           modelAndView.addObject("userForm", userForm);
           modelAndView.addObject("lab", lab);
           return modelAndView;
       }
       userForm.getUser().setLab(lab);
       userForm.getUser().setUserType(userManager.getUserType(2L));
       userForm.getUser().setAgreed(true);
       String mailerList = userForm.getUser().getEmail();

       mav = new ModelAndView("redirect:/lab/labAdministrator/list/" + labId);
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
           mailEngine.sendMessage(mailerList, messageSource.getMessage("mail.default.from", null, null), "Welcome to nalaz", "welcomeemaillabadmin.vm", model);
       }
       userManager.saveUser(userForm.getUser());
       redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "Admin laboratorije je uspješno kreiran.");
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/labAdministrator/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editLabAdministrator(@PathVariable("id") Long id, HttpServletRequest request) {
        LOGGER.info("id[{}]", new Object[]{id});
        ModelAndView mav = new ModelAndView("core/labAdministratorEdit");
        try {
       User user = userManager.getUser(id);
       UserForm userForm = new UserForm(user);
       mav.addObject("userForm", userForm);
       mav.addObject("lab", user.getLab());
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/labAdministrator/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editLabAdministrator(@ModelAttribute @Valid UserForm userForm, BindingResult bindingResult,
    RedirectAttributes redirectAttributes, HttpServletRequest request) throws MessagingException {
        LOGGER.info("userForm[{}]bindingResult[{}]", new Object[]{userForm, bindingResult});
        ModelAndView mav = null;
       try {
       User user = userManager.getUser(userForm.getUser().getId());
       Long labId = user.getLab().getId();
       Lab lab = labManager.getLab(labId);
       Boolean statusReturn = userManager.isUserEmailExistsEdit(userForm.getUser().getId(), userForm.getUser().getEmail());
       if (statusReturn) {
           mav = new ModelAndView("core/labAdministratorEdit");
           bindingResult.rejectValue("user.email", null, "Email already exist");

           mav.addObject("userForm", userForm);
           mav.addObject("lab", lab);
           return mav;
       }
       userForm.getUser().setLab(lab);
       userForm.getUser().setUserType(userManager.getUserType(2L));
       userForm.getUser().setAgreed(true);
       if (bindingResult.hasErrors()) {
           ModelAndView modelAndView = new ModelAndView("core/labAdministratorEdit");
           modelAndView.addObject("userForm", userForm);
           modelAndView.addObject("lab", lab);
           return modelAndView;
       }
       mav = new ModelAndView("redirect:/lab/labAdministrator/list/" + labId);
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
           mailEngine.sendMessage(mailerList, messageSource.getMessage("mail.default.from", null, null), "Welcome to nalaz", "updatelabadminemail.vm", model);
       }
       userManager.saveUser(userForm.getUser());
       redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "Admin laboratorije je uspješno izmijenjen.");
       } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
       }
        return mav;
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/labAdministrator/delete", method = RequestMethod.POST)
    public ModelAndView deleteLabAdministrator(@RequestParam("selectedId") Long id, final RedirectAttributes redirectAttributes, HttpServletRequest request) {
        LOGGER.info("id[{}]", new Object[]{id});
        ModelAndView mav = null;
        try {
       User user = userManager.getUser(id);
       Long labId = user.getLab().getId();
       mav = new ModelAndView("redirect:/lab/labAdministrator/list/" + labId);
       Boolean status = labManager.canDeleteLabAdmin(user);
       Boolean status1 = labManager.canDeleteLabAdm(user);
       if (status|| status1) {
           redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_INFO, "Nije moguće obrisati. Admin laboratorije se koristi.");
       } else {
           labManager.removeLabAdministrator(id);
           redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_INFO, "Admin laboratorije je uspješno obrisan.");
       }
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_KOMPANIJE')")
    @RequestMapping(value = "/labProfile/{id}", method = RequestMethod.GET)
    public ModelAndView labProfile(@PathVariable("id") Long id, HttpServletRequest request) {
        LOGGER.info("id[{}]", new Object[]{id});
        ModelAndView mav = new ModelAndView("labadmin/labProfile");
        try {
       Lab lab = labManager.getLab(id);
       mav.addObject("lab", lab);
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_KOMPANIJE')")
    @RequestMapping(value = "/labProfile/{id}", method = RequestMethod.POST)
    public ModelAndView labProfile(@ModelAttribute @Valid Lab lab, @PathVariable("id") Long id,
    BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        LOGGER.info("lab[{}]id[{}]bindingResult[{}]", new Object[]{lab, id, bindingResult});
        ModelAndView mav = null;
        try {
       if (bindingResult.hasErrors()) {
           return new ModelAndView("/labadmin/labProfile");
       }
       mav = new ModelAndView("redirect:/lab/labProfile/" + lab.getId());
       Contact contact;
       contact = labManager.saveContact(lab.getContact());
       lab.setContact(contact);
       lab.setEnabled(true);
       lab.setDeleted(false);
       labManager.saveLab(lab);
       redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "Profil kompanije je uspješno izmijenjen.");
        } catch (Exception e) {
        LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
        }
        return mav;
    }    
}