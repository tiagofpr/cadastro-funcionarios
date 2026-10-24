package br.com.tiagoribeiro.controller;

import br.com.tiagoribeiro.model.Funcionario;
import br.com.tiagoribeiro.service.FuncionarioService;
import br.com.tiagoribeiro.util.Pagina;
import org.hibernate.service.spi.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListarFuncionarioServlet extends HttpServlet {

    private final FuncionarioService service = new FuncionarioService();
    private static final int TAMANHO_PAGINA = 5;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException, IOException, ServletException {

        int pagina = 1;
        try {
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }catch (NumberFormatException e) {

        }
        Pagina<Funcionario> resultado = service.listarPaginado(pagina, TAMANHO_PAGINA);
        request.setAttribute("pagina", resultado);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/listar.jsp");
        dispatcher.forward(request, response);
    }

}
