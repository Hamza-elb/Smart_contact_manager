package com.elbourissi.scm.Helper;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
public class SessionHelper {
    public static void removeMessage() {
        try {
            System.out.println("removing message from session");
            HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest()
                    .getSession();
            session.removeAttribute("message");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
