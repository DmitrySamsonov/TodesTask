package dao;

import model.Person;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

public class PersonDao {

    public static String createPerson(Object obj) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("HibernateJpa");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        entitymanager.persist(obj);

        entitymanager.getTransaction().commit();
        return "pagePersonsList.xhtml?faces-redirect=true";
    }

    public static String deletePerson(int personId) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("HibernateJpa");
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
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("HibernateJpa");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Person personManager = entitymanager.find(Person.class, person.getId());
        personManager.setName(person.getName());
        personManager.setSurName(person.getSurName());

        entitymanager.getTransaction().commit();
        return "pagePersonsList.xhtml?faces-redirect=true";
    }

    public static List<Person> selectAll(){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("HibernateJpa");
        EntityManager entitymanager = emfactory.createEntityManager();

//        Query query = entitymanager.createQuery("SELECT e FROM Person e");
//        List<Person> personList = new LinkedList<Person>();
//        personList = query.getResultList();

        Query query = entitymanager.createNativeQuery("SELECT * FROM person", Person.class);
        List<Person> personList = query.getResultList();


        entitymanager.close();
        emfactory.close();
        return personList;
    }


    public static List<Person> search(String searchName, String searchSurname) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("HibernateJpa");
        EntityManager entitymanager = emfactory.createEntityManager();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM person WHERE");
        sb.append(" name LIKE '" + searchName +"%'");
        sb.append(" AND surname LIKE '" + searchSurname +"%'");

        Query query = entitymanager.createNativeQuery(sb.toString(), Person.class);
        List<Person> personList = query.getResultList();


        entitymanager.close();
        emfactory.close();
        return personList;
    }




    //ненужен
    public static List<Person> getPersonById(int personId) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("HibernateJpa");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Person person = entitymanager.find(Person.class, personId);

        List<Person> list = new LinkedList<Person>();
        list.add(person);

        entitymanager.getTransaction().commit();
        return list;
    }


}



