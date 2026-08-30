package br.com.tiagoribeiro.controller;

import br.com.tiagoribeiro.model.Funcionario;
import br.com.tiagoribeiro.service.FuncionarioService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class SalvarFuncionarioServlet extends HttpServlet {

    private final FuncionarioService service = new FuncionarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/cadastro.jsp");
        dispatcher.forward(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String nome = request.getParameter("nome");
        String cargo = request.getParameter("cargo");
        String salarioTexto = request.getParameter("salario");

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCargo(cargo);

        try{
            funcionario.setSalario(new BigDecimal(salarioTexto));
            service.cadastrar(funcionario);

            response.sendRedirect(request.getContextPath() + "/funcionarios");

        } catch (NumberFormatException e ){
            request.setAttribute("erro", "Salario invalido. Use apenas numero (ex: 3500.00).");
            reexibirFormulario(request, response, funcionario);
        } catch (IllegalArgumentException e){
            request.setAttribute("erro", e.getMessage());
            reexibirFormulario(request, response, funcionario);
        }

    }

    private void reexibirFormulario(HttpServletRequest request, HttpServletResponse response,
                                    Funcionario funcionario)
        throws ServletException, IOException{

        request.setAttribute("funcionario", funcionario);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/cadastro.jsp");
        dispatcher.forward(request, response);

    }


}
