package ba.nalaz.web.helper;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import ba.nalaz.model.core.User;
import ba.nalaz.model.core.UserLog;
import ba.nalaz.service.UserManager;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserManager userManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        User user = (User)authentication.getPrincipal();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if(ipAddress==null){
            ipAddress=request.getRemoteAddr();
        }
        UserLog userLog = new UserLog();
        userLog.setUser(user);
        userLog.setLab(user.getLab());
        userLog.setLoggedInTime(new Date());
        userLog.setRequestedIP(ipAddress);
        userManager.saveUserLog(userLog);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}