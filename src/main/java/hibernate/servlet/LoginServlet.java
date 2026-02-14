package hibernate.servlet;

import hibernate.entity.User;
import hibernate.service.UserService;
import hibernate.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static hibernate.utils.UrlPath.LOGIN;

@Slf4j
@WebServlet(LOGIN)
public class LoginServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");

        log.info("Login attempt for email: {}", email);

        userService.login(req.getParameter("email"), req.getParameter("password"))
                .ifPresentOrElse(userDto -> onLoginSucces(userDto, req, resp),
                        () -> onLoginFail(req, resp));
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        log.warn("Login failed for email: {}", req.getParameter("email"));
        resp.sendRedirect("/login?error&email=" + req.getParameter("email"));
    }

    @SneakyThrows
    private void onLoginSucces(User user, HttpServletRequest req, HttpServletResponse resp) {
        log.info("User '{}' successfully logged in", user.getEmail());

        req.getSession().setAttribute("user", user);
        resp.sendRedirect("/customers");
    }
}
