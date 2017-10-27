package dao;

import model.Address;
import model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.*;

public class PersonDao {

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

//        Query query = entitymanager.createNativeQuery("SELECT * FROM person", Person.class);
//        List<Object> personList = query.getResultList();

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

    public static void main(String[] args) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
        EntityManager entitymanager = emfactory.createEntityManager();

//        Query query = entitymanager.createNativeQuery("SELECT * FROM person", Person.class);
//        List personList = query.getResultList();

        List listObj = entitymanager.createQuery("SELECT p, a  FROM Person p LEFT JOIN p.address a").getResultList();

//        for(Vector v :listObj){
//            System.out.println("a");
//
//        }
        Iterator iter = listObj.listIterator();
        if(iter.hasNext()){
            Object item = iter.next();
            Object[] row = (Object[]) item;
            Person personOb = (Person) row[0];
            Address addressOb = (Address) row[1];
            System.out.println("yes)");
        }

        entitymanager.close();
        emfactory.close();
    }


    public static List<Object> search(String searchName, String searchSurname) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
        EntityManager entitymanager = emfactory.createEntityManager();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM person WHERE");
        sb.append(" name LIKE '" + searchName + "%'");
        sb.append(" AND surname LIKE '" + searchSurname + "%'");

        Query query = entitymanager.createNativeQuery(sb.toString(), Person.class);
        List<Object> personList = query.getResultList();


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



