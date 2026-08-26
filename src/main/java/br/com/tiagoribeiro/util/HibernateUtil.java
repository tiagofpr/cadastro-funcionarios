package br.com.tiagoribeiro.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Properties dbProps = loadDbProperties();

            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            // Injeta as propriedades sensíveis vindas do config.properties
            configuration.setProperty("hibernate.connection.driver_class", dbProps.getProperty("db.driver"));
            configuration.setProperty("hibernate.connection.url", dbProps.getProperty("db.url"));
            configuration.setProperty("hibernate.connection.username", dbProps.getProperty("db.username"));
            configuration.setProperty("hibernate.connection.password", dbProps.getProperty("db.password"));

            return configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Erro ao inicializar o SessionFactory: " + e);
        }
    }

    private static Properties loadDbProperties() throws IOException {
        Properties props = new Properties();
        try (InputStream input = HibernateUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Arquivo config.properties não encontrado em src/main/resources");
            }
            props.load(input);
        }
        return props;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
