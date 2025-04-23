package lk.ijse.mentalHealthTherapyCenter.config;

import lk.ijse.mentalHealthTherapyCenter.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static FactoryConfiguration instance;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration config = new Configuration().configure();

        config.addAnnotatedClass(User.class);
        config.addAnnotatedClass(Patient.class);
        config.addAnnotatedClass(Registration.class);
        config.addAnnotatedClass(TherapyProgram.class);
        config.addAnnotatedClass(Therapist.class);
        config.addAnnotatedClass(lk.ijse.mentalHealthTherapyCenter.entity.Session.class);

        sessionFactory = config.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        return  (instance == null) ? instance = new FactoryConfiguration() : instance;
    }

    public Session getSession() {return sessionFactory.openSession();}
}
