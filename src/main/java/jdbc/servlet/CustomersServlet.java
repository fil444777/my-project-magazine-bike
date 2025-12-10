package jdbc.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdbc.service.CustomersService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/customers")
public class CustomersServlet extends HttpServlet {
    private final CustomersService customersService = CustomersService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (var writer = resp.getWriter();){

            writer.write("<h1>Список покупателей:</h1>");
            writer.write("<ul>");
            customersService.findAll().stream().forEach(customersDto ->
                    writer.write("""
                            <li>
                            <a href='/motorcycles?customerId=%d'>%s - %s - %s</a>
                            </li>
                            """.formatted(customersDto.getId(),customersDto.getName(), customersDto.getEmail(),
                    customersDto.getPhone())));
            writer.write("</ul>");
        }

    }
}
