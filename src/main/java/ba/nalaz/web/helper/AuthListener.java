package ba.nalaz.web.helper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import ba.nalaz.service.UserManager;


public class AuthListener implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    private UserManager userManager;

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        event.getAuthentication().getPrincipal();
        event.getAuthentication();
    }
}