package br.com.tiagoribeiro.controller;

import br.com.tiagoribeiro.model.Funcionario;
import br.com.tiagoribeiro.service.FuncionarioService;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException, IOException, ServletException {

        List<Funcionario> funcionarios = service.listarTodos();

        request.setAttribute("funcionarios", funcionarios);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/viwes/listar.jsp");
        dispatcher.forward(request, response);
    }

}
