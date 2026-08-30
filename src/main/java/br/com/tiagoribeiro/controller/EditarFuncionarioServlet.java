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

public class EditarFuncionarioServlet extends HttpServlet {

    private final FuncionarioService service = new FuncionarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{

        try{
            Long id = Long.parseLong(request.getParameter("id"));
            Funcionario funcionario = service.buscarPorId(id);

            request.setAttribute("funcionario", funcionario);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/editar.jsp");
            dispatcher.forward(request, response);
        }catch (IllegalArgumentException e){
            response.sendRedirect(request.getContextPath() + "/funcionarios");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{

        Long id = Long.parseLong(request.getParameter("id"));

        Funcionario dadosNovos = new Funcionario();
        dadosNovos.setNome(request.getParameter("nome"));
        dadosNovos.setCargo(request.getParameter("cargo"));

        try{
            dadosNovos.setSalario(new BigDecimal(request.getParameter("salario")));
            service.atualizar(id, dadosNovos);

            response.sendRedirect(request.getContextPath() + "/funcionarios");
        }catch (NumberFormatException e){
            reexibirFormulario(request, response, id, dadosNovos, "Salario inválido. Use apenas numeros (ex. 3500.00)");
        }catch (IllegalArgumentException e){
            reexibirFormulario(request, response, id, dadosNovos, e.getMessage());
        }
    }

    private void reexibirFormulario(HttpServletRequest request, HttpServletResponse response,
                                    Long id, Funcionario dadosNovos, String mensagemErro)
        throws ServletException, IOException{

        dadosNovos.setId(id);// para garantir que o formulario que vai reexibir sabe qual o id está editando
        request.setAttribute("funcionario", dadosNovos);
        request.setAttribute("erro", mensagemErro);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/editar.jsp");
        dispatcher.forward(request, response);

    }

}
