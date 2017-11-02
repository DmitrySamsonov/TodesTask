package com.todes.personUtil.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class DatabaseDao {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseDao.class);
    private static DatabaseDao instance;

    private DatabaseDao() {

    }

    public static synchronized DatabaseDao getInstance() {
        if (instance == null) {
            instance = new DatabaseDao();
        }
        return instance;
    }

    public synchronized void create(Object entity) {
        EntityManager entityManagerObj = EntityManagerFactoryWeb.getEntityManager();
        EntityTransaction transactionObj = entityManagerObj.getTransaction();
        if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
        try {
            entityManagerObj.persist(entity);
            transactionObj.commit();
        } catch (Exception e) {
            LOGGER.error("Exception in DatabaseDao.create(). " + e);
            throw e;
        }
    }

    public synchronized void delete(Object entity) {
        EntityManager entityManagerObj = EntityManagerFactoryWeb.getEntityManager();
        EntityTransaction transactionObj = entityManagerObj.getTransaction();
        if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
        try {
            entityManagerObj.remove(entity);
            transactionObj.commit();
        } catch (Exception e) {
            LOGGER.error("Exception in DatabaseDao.delete(). " + e);
            throw e;
        }
    }

    public synchronized Object selectSingleResult(String jpql) {
        EntityManager entityManagerObj = EntityManagerFactoryWeb.getEntityManager();

        Object result = null;
        try {
            Query query = entityManagerObj.createQuery(jpql);
            result = query.getSingleResult();
        } catch (Exception e) {
            LOGGER.error("Exception in DatabaseDao.selectSingleResult(). " + e);
            throw e;
        }

        return result;
    }

    public synchronized List selectResultList(String jpql) {
        EntityManager entityManagerObj = EntityManagerFactoryWeb.getEntityManager();

        List resultList= null;
        try {
            Query query = entityManagerObj.createQuery(jpql);
            resultList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Exception in DatabaseDao.selectResultList(). " + e);
            throw e;
        }
        return resultList;
    }


}
