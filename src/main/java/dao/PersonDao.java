package dao;

import model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PersonDao {
    private static final Logger logger = LogManager.getLogger(PersonDao.class);

    private static final String PERSISTENCE_UNIT_NAME = "JpaUnit";
//    private static EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
//    private static EntityManager entityManagerObj = null; /*EntityManagerFactoryWeb.getEntityManager();*/
//    private static EntityTransaction transactionObj = entityManagerObj.getTransaction();


    // Checked!
    public static void createPerson(Person person) {
        EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
        EntityTransaction transactionObj = entityManagerObj.getTransaction();

        if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
        try {
            entityManagerObj.persist(person);
            transactionObj.commit();

        } catch (Exception e) {
            logger.error("Exception in PersonDao.createPerson(person). " + e);
            throw e;
        }

        entityManagerObj.close();
    }

    public static String deletePerson(int personId) {
        EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
        EntityTransaction transactionObj = entityManagerObj.getTransaction();

        if (!transactionObj.isActive()) {
            transactionObj.begin();
        }

        Person person = entityManagerObj.find(Person.class, personId);
        if (person != null) {
            entityManagerObj.remove(person);
        }

        transactionObj.commit();
        entityManagerObj.close();
        return "pagePersonsList.xhtml?faces-redirect=true";
    }

    public static String editPerson(Person person) {
        EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
        EntityTransaction transactionObj = entityManagerObj.getTransaction();

        if (!transactionObj.isActive()) {
            transactionObj.begin();
        }

        Person personManager = entityManagerObj.find(Person.class, person.getId());
        personManager.setName(person.getName());
        personManager.setSurname(person.getSurname());
        personManager.setPatronymic(person.getPatronymic());

        transactionObj.commit();
        entityManagerObj.close();
        return "pagePersonsList.xhtml?faces-redirect=true";
    }

    public static List<Object> selectAll() {
        EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();

        List<Object> personList = entityManagerObj.createQuery("SELECT p, a  FROM Person p LEFT JOIN p.address a").getResultList();

        entityManagerObj.close();
        return personList;
    }

    public static List<Object> search(String searchName, String searchSurname, String searchDateFrom,
                                      String searchDateTo, String searchStreet, String searchHouseNumber) {

        EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT p, a  FROM Person p LEFT JOIN p.address a WHERE ");
        sb.append(" p.name LIKE '" + searchName + "%'");
        sb.append(" AND p.surname LIKE '" + searchSurname + "%'");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date dateFrom = null;
        try {
            dateFrom = dateFormat.parse("00.00.0000");
        } catch (ParseException e) {
            logger.error("Fail parse 00.00.0000");
        }
        Date dateTo = new Date();
        if (!searchDateFrom.isEmpty()) {
            try {
                dateFrom = dateFormat.parse(searchDateFrom);
            } catch (ParseException e) {
                logger.error("Fail parse searchDateFrom");
            }
        }
        if (!searchDateTo.isEmpty()) {
            try {
                dateTo = dateFormat.parse(searchDateTo);
            } catch (ParseException e) {
                logger.error("Fail parse searchDateTo");
            }
        }
        logger.info("Date dateFrom = " + dateFrom.toString());
        logger.info("Date dateTo = " + dateTo.toString());

        sb.append(" AND p.date BETWEEN :start AND :end");

        if (!searchStreet.isEmpty()) {
            sb.append(" AND a.streetCode = (SELECT s.code FROM Street s WHERE s.name = '" + searchStreet + "')");
        }

        if (!searchHouseNumber.isEmpty()) {
            try {
                int searchHouseNumberInt = Integer.parseInt(searchHouseNumber);
                sb.append(" AND a.houseNumber = " + searchHouseNumberInt);
            } catch (NumberFormatException ignore) { /*NOP*/ }
        }

        List<Object> personList = null;
        try {
            Query query = entityManagerObj.createQuery(sb.toString());
            query.setParameter("start", dateFrom, TemporalType.DATE);
            query.setParameter("end", dateTo, TemporalType.DATE);
            personList = query.getResultList();
        } catch (Exception e) {
            logger.error("Exception in search() execute Query. " + e.getStackTrace());
        }

        entityManagerObj.close();
        return personList;
    }
}