package ru.itis.dis403.lab05.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession(false);

        if (!checkExcluded(req.getServletPath()) &&
                (session == null || session.getAttribute("user") == null)) {
            resp.sendRedirect("login");
        } else {
            filterChain.doFilter(req, resp);
        }
    }


    private boolean checkExcluded(String resources) {
        return resources.contains("/login") ||
                resources.contains("/usercheck");
    }
}
