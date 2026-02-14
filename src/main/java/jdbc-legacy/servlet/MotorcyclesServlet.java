package jdbc.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdbc.service.CustomersService;
import jdbc.service.MotorcyclesService;
import jdbc.utils.JspHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/motorcycles")
public class MotorcyclesServlet extends HttpServlet {
    private final MotorcyclesService motorcyclesService = MotorcyclesService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Integer customersId = Integer.valueOf(req.getParameter("customersId"));

        req.setAttribute("motorcycles", motorcyclesService.findAllByCustomerId(customersId));
        req.getRequestDispatcher(JspHelper.getPath("motorcycles")).forward(req, resp);

    }
}
