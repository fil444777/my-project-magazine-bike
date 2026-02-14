package hibernate.servlet;

import hibernate.service.MotorcyclesService;
import hibernate.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/motorcycles")
public class MotorcyclesServlet extends HttpServlet {
    private final MotorcyclesService motorcyclesService = MotorcyclesService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Integer userId = Integer.valueOf(req.getParameter("userId"));

        req.setAttribute("motorcycles", motorcyclesService.findAllByCustomerId(userId));
        req.getRequestDispatcher(JspHelper.getPath("motorcycles")).forward(req, resp);

    }
}
