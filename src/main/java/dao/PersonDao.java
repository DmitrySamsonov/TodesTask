package dao;

import model.Address;
import model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PersonDao {
    private static final Logger logger = LogManager.getLogger(PersonDao.class);


    public static String createPerson(Person person, Address address) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        //Store Address
        entitymanager.persist(address);

        //Store Person
        person.setAddress(address);
        entitymanager.persist(person);

        entitymanager.getTransaction().commit();
        return "pagePersonsList.xhtml?faces-redirect=true";
    }

    public static String deletePerson(int personId) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Person person = entitymanager.find(Person.class, personId);
        if (person != null) {
            entitymanager.remove(person);
        }

        entitymanager.getTransaction().commit();
        return "pagePersonsList.xhtml?faces-redirect=true";
    }

    public static String editPerson(Person person) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Person personManager = entitymanager.find(Person.class, person.getId());
        personManager.setName(person.getName());
        personManager.setSurname(person.getSurname());
        personManager.setPatronymic(person.getPatronymic());

        entitymanager.getTransaction().commit();
        return "pagePersonsList.xhtml?faces-redirect=true";
    }

    public static List<Object> selectAll() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
        EntityManager entitymanager = emfactory.createEntityManager();


        List<Object> personList = entitymanager.createQuery("SELECT p, a  FROM Person p LEFT JOIN p.address a").getResultList();

        entitymanager.close();
        emfactory.close();
        return personList;
    }

    public static List selectAllwithAddress() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
        EntityManager entitymanager = emfactory.createEntityManager();

        Query query = entitymanager.createNativeQuery("SELECT * FROM person", Person.class);
        List personList = query.getResultList();

        entitymanager.close();
        emfactory.close();
        return personList;
    }

//    public static void main(String[] args) {
//        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
//        EntityManager entitymanager = emfactory.createEntityManager();
//
////        Query query = entitymanager.createNativeQuery("SELECT * FROM person", Person.class);
////        List personList = query.getResultList();
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("SELECT p, a  FROM Person p LEFT JOIN p.address a WHERE ");
//        sb.append(" p.name Like 'a%' ");
//        List listObj = entitymanager.createQuery(sb.toString()).getResultList();
//
////        for(Vector v :listObj){
////            System.out.println("a");
////        }
//        Iterator iter = listObj.listIterator();
//        if (iter.hasNext()) {
//            Object item = iter.next();
//            Object[] row = (Object[]) item;
//            Person personOb = (Person) row[0];
//            Address addressOb = (Address) row[1];
//            System.out.println("yes)");
//        }
//
//        entitymanager.close();
//        emfactory.close();
//    }


    public static List<Object> search(String searchName, String searchSurname, String searchDateFrom,
                                      String searchDateTo, String searchStreet, String searchHouseNumber) {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
        EntityManager entitymanager = emfactory.createEntityManager();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT p, a  FROM Person p LEFT JOIN p.address a WHERE ");
        sb.append(" p.name LIKE '" + searchName + "%'");
        sb.append(" AND p.surname LIKE '" + searchSurname + "%'");

        logger.info("-------------------search------------------------");

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

        logger.info("------------------------------");
        logger.info(sb);

        List<Object> personList = null;
        try {
            Query query = entitymanager.createQuery(sb.toString());
            query.setParameter("start", dateFrom, TemporalType.DATE);
            query.setParameter("end", dateTo, TemporalType.DATE);
            personList = query.getResultList();
        } catch (Exception e) {
            logger.error("Exception in search() execute Query. " + e.getStackTrace());
        }

        entitymanager.close();
        emfactory.close();
        return personList;
    }


    //ненужен
    public static List<Person> getPersonById(int personId) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Person person = entitymanager.find(Person.class, personId);

        List<Person> list = new LinkedList<Person>();
        list.add(person);

        entitymanager.getTransaction().commit();
        return list;
    }
}