package ba.nalaz.web.helper;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import ba.nalaz.model.core.User;
import ba.nalaz.model.core.UserLog;
import ba.nalaz.service.UserManager;

@Component
public class SessionListener implements ApplicationListener<SessionDestroyedEvent> {
    @Autowired
    private UserManager userManager;


    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {

        List<SecurityContext> lstSecurityContext = event.getSecurityContexts();

        for (SecurityContext securityContext : lstSecurityContext) {
            User user = (User) securityContext.getAuthentication().getPrincipal();
            UserLog userLog = userManager.getUserLog(user.getId());
            userLog.setLoggedOutTime(new Date());
            userManager.saveUserLog(userLog);
        }
    }
}