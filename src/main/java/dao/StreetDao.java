package dao;

import model.Street;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class StreetDao {


    public static void fillStreetsByDefault() {

        createStreet(43, "Gagarina");
        createStreet(55, "Kulman");
        createStreet(62, "Yakuba Kolasa");
        createStreet(78, "Sportivnaya");
        createStreet(80, "Orlovskaya");
        createStreet(93, "Gaya");
        createStreet(108, "Shevchenko");
        createStreet(112, "Sverdlova");
        createStreet(125, "Akademicheskaya");
        createStreet(137, "Gogolya");

    }

    public static void createStreet(int code, String streetName) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Street street = new Street();
        street.setCode(code);
        street.setName(streetName);

        entitymanager.persist(street);

        entitymanager.getTransaction().commit();
    }


    public static List<String> selectAllNames() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
        EntityManager entitymanager = emfactory.createEntityManager();

//        Query query = entitymanager.createNativeQuery("SELECT steet.name FROM street", Street.class);
//        List<String> namesList = query.getResultList();

        Query query = entitymanager.createQuery("Select s.name from Street s");
        List<String> namesList = query.getResultList();

        entitymanager.close();
        emfactory.close();
        return namesList;
    }

    public static List<Street> selectAll() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
        EntityManager entitymanager = emfactory.createEntityManager();

        Query query = entitymanager.createNativeQuery("SELECT * FROM street", Street.class);
        List<Street> streetList = query.getResultList();

        entitymanager.close();
        emfactory.close();
        return streetList;
    }

    public static void main(String[] args) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
        EntityManager entitymanager = emfactory.createEntityManager();

//        Query query = entitymanager.createNativeQuery("SELECT code FROM street WHERE street.name = 'Gaya'", Street.class);
//        Object res = query.getSingleResult();

        Query query = entitymanager.createQuery("SELECT s.code FROM Street s WHERE s.name = 'Gaya'");
//        Street c = (Street)query.getSingleResult();
        int c = Integer.valueOf(query.getSingleResult().toString());

//        System.out.println(c.getCode());
        System.out.println(c);


        entitymanager.close();
        emfactory.close();

    }

    public static int getCodeByName() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("JpaUnit");
        EntityManager entitymanager = emfactory.createEntityManager();

        Query query = entitymanager.createQuery("SELECT s.code FROM Street s WHERE s.name = 'Gaya'");
        int code = Integer.valueOf(query.getSingleResult().toString());

        entitymanager.close();
        emfactory.close();
        return code;
    }
}
