package br.com.tiagoribeiro;

import br.com.tiagoribeiro.dao.FuncionarioDAO;
import br.com.tiagoribeiro.dao.FuncionarioDAOImpl;
import br.com.tiagoribeiro.model.Funcionario;
import br.com.tiagoribeiro.service.FuncionarioService;
import br.com.tiagoribeiro.util.HibernateUtil;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class TesteFuncionario {
    public static void main(String[] args) {
        FuncionarioService service = new FuncionarioService();

        Funcionario novo = new Funcionario();
        novo.setNome("Pedro Pereira");
        novo.setCargo("Desenvolvedor backend");
        novo.setSalario(new BigDecimal("4000.00"));

        service.cadastrar(novo);
        System.out.println("Funcionario salvo com o Id: " + novo.getId());

        try {
            Funcionario invalido = new Funcionario();
            invalido.setNome("");
            invalido.setCargo("teste");
            invalido.setSalario(new BigDecimal("1000"));
            service.cadastrar(invalido);
        }catch (IllegalArgumentException e){
            System.out.println("Validação de funcionario: "+ e.getMessage());
        }

        //dao.deletar(4L);

//        List<Funcionario> todos = dao.listarTodos();
//        System.out.println("Total de funcionarios " + todos.size());
//        todos.forEach(
//                f -> System.out.println(" - "
//                        + f.getNome()
//                        + " | "
//                        + f.getCargo()));
//
//        Funcionario porId = dao.buscarPorId(5L);
//        System.out.println(porId.getNome()+ " | " + porId.getCargo());
//
//        porId.setCargo("Desenvolvedor Java Junior");
//        dao.atualizar(porId);
//        System.out.println("Cargo atualizado com sucesso: " + porId.getCargo());

    }
}
