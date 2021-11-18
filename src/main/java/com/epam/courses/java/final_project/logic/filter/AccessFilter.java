package com.epam.courses.java.final_project.logic.filter;

import com.epam.courses.java.final_project.model.User.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.epam.courses.java.final_project.util.CommandConstant.*;
import static com.epam.courses.java.final_project.util.Constant.LOG_TRACE;

public class AccessFilter implements Filter {

    private final Logger log = LogManager.getLogger(LOG_TRACE);
    private final Map<String, Role> accessLevel = new HashMap<>();

    public AccessFilter() {
        accessLevel.put(INDEX, null);
        accessLevel.put(SIGN_IN, null);
        accessLevel.put(SIGN_UP, null);
        accessLevel.put(LOGOUT, Role.Customer);
        accessLevel.put(AVAILABLE_ROOMS, Role.Customer);
        accessLevel.put(PROFILE, Role.Customer);
        accessLevel.put(DELETE_USER, Role.Customer);
        accessLevel.put(BOOK_SPECIFIC_ROOM, Role.Customer);
        accessLevel.put(CREATE_REQUEST, Role.Customer);
        accessLevel.put(CANCEL_REQUEST, Role.Customer);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getServletPath();
        String reqName = path.substring(1).replace(".act", "");
        Role commandRole = accessLevel.get(reqName);
        log.trace("command: " + reqName + "(" + commandRole + ")");

        Object obj = req.getSession().getAttribute(ATTRIBUTE_ROLE);

        if (commandRole == null){
            chain.doFilter(request, response);
        } else {
            if (obj == null){
                resp.sendRedirect(SIGN_IN_JSP);
                return;
            }

            Role userRole = Role.valueOf((String) obj);
            if (commandRole.getValue() == userRole.getValue() || commandRole.getValue() < userRole.getValue()){
                chain.doFilter(request, response);
            } else {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("initialization");
    }

    @Override
    public void destroy() {
        log.info("filter destroyed");
    }
}
