package br.com.tiagoribeiro;

import br.com.tiagoribeiro.dao.FuncionarioDAO;
import br.com.tiagoribeiro.dao.FuncionarioDAOImpl;
import br.com.tiagoribeiro.model.Funcionario;
import br.com.tiagoribeiro.util.HibernateUtil;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class TesteFuncionario {
    public static void main(String[] args) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            System.out.println("Conexão com o banco estabelecida com sucesso");
        } catch (Exception e){
            System.out.println("Falha de conexão: " + e.getMessage());
            e.printStackTrace();
        }

        FuncionarioDAO dao = new FuncionarioDAOImpl();

        Funcionario novo = new Funcionario();
        novo.setNome("João Pereira");
        novo.setCargo("P.O");
        novo.setSalario(new BigDecimal("9500.00"));

        dao.salvar(novo);
        System.out.println("Funcionario salvo com o Id: " + novo.getId());

        //dao.deletar(4L);

        List<Funcionario> todos = dao.listarTodos();
        System.out.println("Total de funcionarios " + todos.size());
        todos.forEach(
                f -> System.out.println(" - "
                        + f.getNome()
                        + " | "
                        + f.getCargo()));

        Funcionario porId = dao.buscarPorId(5L);
        System.out.println(porId.getNome()+ " | " + porId.getCargo());

        porId.setCargo("Desenvolvedor Java Junior");
        dao.atualizar(porId);
        System.out.println("Cargo atualizado com sucesso: " + porId.getCargo());

    }
}
