package ba.nalaz.service;

import static ba.nalaz.model.core.ProductConstants.DEFAULT_MAIL;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ba.nalaz.dao.UserDao;
import ba.nalaz.model.core.Role;
import ba.nalaz.model.core.User;
import ba.nalaz.model.core.UserLog;
import ba.nalaz.model.core.UserType;
import ba.nalaz.web.helper.Pagination;
import ba.nalaz.web.helper.PartialList;

@Service("userManager")
public class UserManager implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserManager.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDao userDao;

    @Autowired(required = false)
    private SaltSource saltSource;

    public User getUser(Long id) {
        return userDao.getUser(id);
    }
    public PartialList getUsers(Pagination pagination) {
        return userDao.getUsers(pagination);
    }
    public List<User> getUsers() {
        return userDao.getUsers();
    }
    public List<User> getUserList(String searchParam, Long labId) {
        return userDao.getUserList(searchParam,labId);
    }

    public List getLabUser(Pagination pagination, Long labId) {
        return userDao.getLabUser(pagination, labId);
    }

    public PartialList getStripeUsers(Pagination pagination) {
        return userDao.getStripeUsers(pagination);
    }

    public List getAllStripeUsers() {
        return userDao.getAllStripeUsers();
    }

    public List<User> getLabUserBasic(Long labId) {
        return userDao.getLabUserBasic(labId);
    }
    
    public User updateUserType(User user, Long userType) {
    	user.setUserType(getUserType(userType));
        Role mainRole = userDao.getRole(user.getUserType().getId());
        user.addRole(mainRole);
    	return userDao.mergeUser(user);
    }
    
    public User updateUserTypeAndRole(User user, Long userType) {
    	user.setUserType(getUserType(userType));
        Role mainRole = userDao.getRole(user.getUserType().getId());
        //TODO remove only one role, not all roles
        user.removeRoles();
        user.addRole(mainRole);
    	return userDao.mergeUser(user);
    }
    
    public User activateUser(User user) {
        user.setEnabled(true);
        user.setActivationKey(null);
    	return userDao.mergeUser(user);
    }

    public User saveUser(User user) {
        if (user.getId() == null) {
            // if new user, lowercase userId
            user.setUsername(user.getUsername().toLowerCase());
            Role mainRole = userDao.getRole(user.getUserType().getId());
            user.addRole(mainRole);
        } else {
            User existingUser = userDao.getUser(user.getId());
            user.setRoles(existingUser.getRoles());
            user.setCredentialsExpired(existingUser.isCredentialsExpired());
        }
        // Get and prepare password management-related artifacts
        boolean passwordChanged = false;
        if (passwordEncoder != null) {
            // Check whether we have to encrypt (or re-encrypt) the password
            if (user.getId() == null) {
                // New user, always encrypt
                passwordChanged = true;
            } else {
                if (user.getPassword() != null && !"".equals(user.getPassword())) {
                    passwordChanged = true;
                }
            }
            // If password was changed (or new user), encrypt it
            if (passwordChanged) {
                if (saltSource == null) {
                    // backwards compatibility
                    user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
                    LOGGER.warn("SaltSource not set, encrypting password w/o salt");
                } else {
                    user.setPassword(passwordEncoder.encodePassword(user.getPassword(), saltSource.getSalt(user)));
                }
            } else {
                String currentPassword = userDao.getUserPassword(user.getId());
                user.setPassword(currentPassword);
            }
        } else {
            LOGGER.warn("PasswordEncoder not set, skipping password encryption...");
        }
        try {
            LOGGER.debug("Password Changed: " + passwordChanged);
            if (user.getId() == null) {
                return userDao.saveUser(user);
            } else {
                return userDao.mergeUser(user);
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            //throw new DataIntegrityViolationException(e.getMessage());
            throw e;
        }
    }
    
    public User saveStripeUser(User user) {
        if (user.getId() == null) {
            // if new user, lowercase userId
            user.setUsername(user.getUsername().toLowerCase());
            Role mainRole = userDao.getRole(user.getUserType().getId());
            user.addRole(mainRole);
        } else {
            User existingUser = userDao.getUser(user.getId());
            user.setRoles(existingUser.getRoles());
            user.setCredentialsExpired(existingUser.isCredentialsExpired());
        }
        try {
            if (user.getId() == null) {
                return userDao.saveUser(user);
            } else {
                return userDao.mergeUser(user);
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            //throw new DataIntegrityViolationException(e.getMessage());
            throw e;
        }
    }

    public Boolean canDeleteUser(User user) {
        return userDao.canDeleteUser(user);
    }

    public boolean hasUserUsedAnyProducts(User user) {
        boolean status1 = userDao.isUserExistInProperty(user);
        boolean status2 = userDao.isUserExistInLRTenant(user);
        boolean status4 = userDao.isUserExistInLRRecord(user);
        return (status1 || status2 || status4);
    }

    public Boolean isUserExistInProperty(User user) {
        Boolean status = userDao.isUserExistInProperty(user);
        return status;
    }

    public Boolean isUserExistInLRTenant(User user) {
        Boolean status = userDao.isUserExistInLRTenant(user);
        return status;
    }

    public Boolean isUserExistInLRRecord(User user) {
        Boolean status = userDao.isUserExistInLRRecord(user);
        return status;
    }

    public void removeUser(Long id) {
        userDao.removeUser(id);
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return (User) userDao.loadUserByUsername(username);
    }

    public List<Role> getRoles() {
        return userDao.getRoles();
    }

    public List<Role> getUserRoles() {
        return userDao.getUserRoles();
    }

    public Role getRole(String rolename) {
        return userDao.getRoleByName(rolename);
    }

    public Role saveRole(Role role) {
        return userDao.saveRole(role);
    }

    public void removeRole(String rolename) {
        userDao.removeRole(rolename);
    }

    public void removeRole(Long id) {
        userDao.removeRole(id);
    }

    public List<UserType> getUserTypes() {
        return userDao.getUserTypes();
    }

    public UserType getUserType(Long id) {
        return userDao.getUserType(id);
    }

    public void saveUserRole(long userId, long[] userRoles) {
        User user = userDao.getUser(userId);
        Set newRoles = new HashSet();
        newRoles.add(userDao.getRole(user.getUserType().getMainRole().getId()));
        for (long item : userRoles) {
            newRoles.add(userDao.getRole(item));
        }
        user.setRoles(newRoles);
        userDao.saveUser(user);
        LOGGER.debug(newRoles.toString());
    }

    public void saveUserLog(UserLog userLog) {
        userDao.saveUserLog(userLog);
    }

    public UserLog getUserLog(Long userId) {
        return userDao.getUserLog(userId);
    }

    public Integer isUserEmailExists(String email, String username) {
        int status = 0;
        Boolean usernameExist = false;
        Boolean emailExist = false;

        if (!"".equals(email)) {
            if (email.equals(DEFAULT_MAIL)) {
                emailExist = false;
            } else {
                emailExist = userDao.isUserEmailExists(email);
            }
        }
        if (!"".equals(username)) {
            usernameExist = userDao.isUserExists(username);
        }
        if (emailExist && usernameExist) {
        	//both exists
            status = 0;               
        } else if (!emailExist && !usernameExist) {
        	//both not exist
            status = 1;             
        } else if (emailExist && !usernameExist) {
        	//email exist
            status = 2;            
        } else if (!emailExist && usernameExist) {
        	//username exist
            status = 3;         
        }
        return status;
    }

    public Boolean isUserEmailExistsEdit(Long id, String email) {
        Boolean emailExist = false;
        if (!"".equals(email)) {
            if (email.equals(DEFAULT_MAIL)) {
                emailExist = false;
            } else {
                emailExist = userDao.isUserEmailExistsEdit(id, email);
            }
        }
        return emailExist;
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public Boolean hasRole(String[] roles) {
        boolean result = false;
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            String userRole = authority.getAuthority();
            for (String role : roles) {
                if (role.equals(userRole)) {
                    result = true;
                    break;
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }

    public boolean userHasAuthority(String authority) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            if (authority.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}