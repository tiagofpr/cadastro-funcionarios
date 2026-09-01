package br.com.tiagoribeiro.dao;

import br.com.tiagoribeiro.model.Funcionario;
import br.com.tiagoribeiro.service.FuncionarioService;
import br.com.tiagoribeiro.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.logging.Level;

public class FuncionarioDAOImpl implements FuncionarioDAO{

    private static final Logger logger = LoggerFactory.getLogger(FuncionarioDAOImpl.class);

    @Override
    public void salvar(Funcionario funcionario){
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            //logger.info("Abrindo conexão com o banco." + transaction.getStatus());
            transaction = session.beginTransaction();
            session.save(funcionario);
            logger.info("Funcionario salvo com sucesso: ID {} ", funcionario.getId());
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao salvao o funcionario. " + e.getMessage());
        }
    }

    @Override
    public Funcionario buscarPorId(Long id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            logger.info("Realizando busca de funcionario no banco de dados. {}", id);
            return session.get(Funcionario.class, id);
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Funcionario> listarTodos() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("FROM Funcionario").list();
        }

    }

    @Override
    public void atualizar(Funcionario funcionario) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            logger.info("Iniciado a transasão para atualizar o Funcionario");
            session.update(funcionario);
            transaction.commit();
            logger.info("Funcionario atualizado com sucesso {} ", funcionario.getId());
        } catch (Exception e) {
            if (transaction != null){
                transaction.rollback();
               logger.info("Realizando rollback.");
            }
            throw new RuntimeException("Erro ao atualizar funcionario" + e.getMessage());
        }

    }

    @Override
    public void deletar(Long id) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Funcionario funcionario = session.get(Funcionario.class, id);
            if(funcionario != null){
                session.delete(funcionario);
            }
            transaction.commit();
            logger.info("Funcionario deletado com sucesso.");
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao deletar funcionario" + e.getMessage());
        }

    }

}
