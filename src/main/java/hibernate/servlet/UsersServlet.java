package hibernate.servlet;

import hibernate.entity.User;
import hibernate.service.UserService;
import hibernate.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        List<User> users = userService.findAll();

        log.info("Loaded {} users", users.size());

        req.setAttribute("users", users);
        req.getRequestDispatcher(JspHelper.getPath("users")).forward(req, resp);
    }
}

