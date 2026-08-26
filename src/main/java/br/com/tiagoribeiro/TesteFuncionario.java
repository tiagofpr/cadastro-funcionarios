package br.com.tiagoribeiro;

import br.com.tiagoribeiro.util.HibernateUtil;
import org.hibernate.Session;

public class TesteFuncionario {
    public static void main(String[] args) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            System.out.println("Conexão com o banco estabelecida com sucesso");
        } catch (Exception e){
            System.out.println("Falha de conexão: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
