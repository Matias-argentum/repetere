package com.mat.repetere.interceptor;

import com.mat.repetere.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ForcePasswordChangeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws java.lang.Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();

            if (user.isForcePasswordChange()){
                String currentUri = request.getRequestURI();
                String context = request.getContextPath();

                boolean isProfileView = currentUri.equals(context + "/profile");
                boolean isPostUpdateForPasswordUpdate = currentUri.equals(context + "/profile/update-password");
                boolean isLogout = currentUri.equals(context + "/logout");

                if (!isProfileView && !isPostUpdateForPasswordUpdate && !isLogout) {
                    response.sendRedirect(context + "/profile");
                    return false;
                }
            }
        }
        return true;
    }

}
