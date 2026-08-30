package br.com.tiagoribeiro.dao;

import br.com.tiagoribeiro.model.Funcionario;
import br.com.tiagoribeiro.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FuncionarioDAOImpl implements FuncionarioDAO{

   private static final Logger logger = Logger.getLogger(String.valueOf(FuncionarioDAOImpl.class));

    @Override
    public void salvar(Funcionario funcionario){
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            //logger.info("Abrindo conexão com o banco." + transaction.getStatus());
            transaction = session.beginTransaction();
            session.save(funcionario);
            logger.log(Level.INFO, "Funcionario salvo com sucesso: ID {} ", funcionario.getId());
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
            //logger.log(Level.INFO,"Realizando busca de funcionario no banco de dados. {}", id);
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
            //log.info("Iniciado a transasão para atualizar o Funcionario");
            session.update(funcionario);
            transaction.commit();
            //log.log(Level.INFO,"Funcionario atualizado com sucesso {} ", funcionario.getId());
        } catch (Exception e) {
            if (transaction != null){
                transaction.rollback();
               //log.info("Realizando rollback.");
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
            //log.info("Funcionario deletado com sucesso.");
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao deletar funcionario" + e.getMessage());
        }

    }

}
