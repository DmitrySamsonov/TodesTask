package com.todes.personUtil.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EntityManagerFactoryWeb implements ServletContextListener {

    private static final String PERSISTENCE_UNIT_NAME = "JpaUnit";
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    final static Logger logger = LogManager.getLogger(EntityManagerFactoryWeb.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("### ServletContextListener : contextInitialized - Inititalizing EntityManagerFactoryWeb for PersistanceUnit: " + PERSISTENCE_UNIT_NAME);
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        logger.info("### ServletContextListener : contextInitialized - Init EntityManagerFactoryWeb done for PersistanceUnit: " + PERSISTENCE_UNIT_NAME);
        entityManager = entityManagerFactory.createEntityManager();
        logger.info("### ServletContextListener : contextInitialized - Init EntityManager done for PersistanceUnit: " + PERSISTENCE_UNIT_NAME);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        entityManager.close();
        logger.info("### ServletContextListener : contextDestroyed - Closing EntityManagerFactoryWeb for PersistanceUnit: " + PERSISTENCE_UNIT_NAME);
        entityManagerFactory.close();
        logger.info("### ServletContextListener : contextDestroyed - Closed EntityManagerFactoryWeb done for PersistanceUnit " + PERSISTENCE_UNIT_NAME);
    }


    public static synchronized EntityManager getEntityManager() {
        return entityManager;
    }

}


