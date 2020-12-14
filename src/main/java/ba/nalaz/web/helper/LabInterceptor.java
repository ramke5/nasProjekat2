package ba.nalaz.web.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ba.nalaz.AppConstants;
import ba.nalaz.model.core.User;
import ba.nalaz.service.UserManager;

public class LabInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private UserManager userManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            user = userManager.getUser(user.getId());
            if (user != null) {
	            request.setAttribute(AppConstants.USER_OBJ, user);
	            request.setAttribute(AppConstants.LAB_OBJ, user.getLab());
	            if ((user.getAgreed() == null || !user.getAgreed()) && !"/gsa".equals(request.getRequestURI())) {
	            	response.sendRedirect("/gsa");
	            }
            }
        }
        
        return true;
    }
}