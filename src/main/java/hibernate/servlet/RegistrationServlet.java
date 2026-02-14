package hibernate.servlet;

import hibernate.dto.UserDto;
import hibernate.entity.Gender;
import hibernate.entity.Role;
import hibernate.exception.ValidationException;
import hibernate.service.UserService;
import hibernate.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;

import static hibernate.utils.UrlPath.REGISTRATION;

@Slf4j
@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());

        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDto = UserDto.builder()
                .name(req.getParameter("name"))
                .birthday(req.getParameter("birthday"))
                .email(req.getParameter("email"))
                .password(req.getParameter("pwd"))
                .role(req.getParameter("role"))
                .gender(req.getParameter("gender"))
                .build();

        try {
            log.info("Processing registration for email: {}", userDto.getEmail());

            userService.create(userDto);

            log.info("Registration successful, redirecting to login");

            resp.sendRedirect("/login");
        } catch (ValidationException e) {
            log.warn("Validation failed for registration: {}", e.getErrors());
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }

    }
}
