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
@WebServlet("/customers")
public class CustomersServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        List<User> customers = userService.findAll();

        log.info("Loaded {} customers", customers.size());

        req.setAttribute("customers", customers);
        req.getRequestDispatcher(JspHelper.getPath("customers")).forward(req, resp);
    }
}

