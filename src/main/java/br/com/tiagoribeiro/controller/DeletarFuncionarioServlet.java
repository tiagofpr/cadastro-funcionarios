package br.com.tiagoribeiro.controller;

import br.com.tiagoribeiro.service.FuncionarioService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeletarFuncionarioServlet extends HttpServlet {

    private final FuncionarioService service = new FuncionarioService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String idTexto = request.getParameter("id");

        try{
            Long id = Long.parseLong(idTexto);
            service.deletar(id);
        }catch (IllegalArgumentException e){
            //Por enquanto não irei tratat esse erro.
        }
        response.sendRedirect(request.getContextPath() + "/funcionarios?sucesso=deletado");
    }
}
