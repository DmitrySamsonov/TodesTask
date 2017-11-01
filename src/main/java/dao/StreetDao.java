package dao;

import bean.PersonBean;
import model.Street;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class StreetDao {

    private static final String PERSISTENCE_UNIT_NAME = "JpaUnit";
//    private static EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    //    private static EntityManager entityManagerObj = EntityManagerFactoryWeb.getEntityManager();
//    private static EntityTransaction transactionObj = entityManagerObj.getTransaction();


    private static final Logger logger = LogManager.getLogger(PersonBean.class);

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

    private static void createStreet(int code, String streetName) {

        EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
        EntityTransaction transactionObj = entityManagerObj.getTransaction();


        if (!transactionObj.isActive()) {
            transactionObj.begin();
        }

        Street street = new Street();
        street.setCode(code);
        street.setName(streetName);

        entityManagerObj.persist(street);

        transactionObj.commit();

        entityManagerObj.close();
    }

    // Checked!
    public static List<Street> selectAll() {
        EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();

        Query query = entityManagerObj.createQuery("SELECT s FROM Street s");
        List<Street> streetList = query.getResultList();

        entityManagerObj.close();
        return streetList;
    }


    //TODO for test debug...
    public static void main(String[] args) {

        EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();

        Query query = entityManagerObj.createQuery("SELECT s FROM Street s");
        List<Street> streetList = query.getResultList();

        entityManagerObj.close();


        System.out.println();
    }

    // Checked!
    public static List<String> selectStreetNames() {
        EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();

        Query query = entityManagerObj.createQuery("Select s.name from Street s");
        List<String> namesList = query.getResultList();

        entityManagerObj.close();
        return namesList;
    }

    // Checked!
    public static Street getStreetByName(String name) {
        EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();

        Query query = entityManagerObj.createQuery("SELECT s FROM Street s WHERE s.name = '" + name + "'");
        Street street = (Street) query.getSingleResult();

        entityManagerObj.close();
        return street;
    }


//    public static int getCodeByName(String streetName) {
//        EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
//
//
//        int code = -1;
//        try {
//
//            Query query = entityManagerObj.createQuery("SELECT s.code FROM Street s WHERE s.name = '" + streetName + "'");
//            code = Integer.valueOf(query.getSingleResult().toString());
//        } catch (Exception e) {
//            logger.error("Exception in StreetDao.getCodeByName(name). " + e);
//        }
//
//        logger.info("code= " + code + "[in StreetDao.getCodeByName(name)]");
//        entityManagerObj.close();
//
//        return code;
//    }
//
//    public static String getNameByCode(int streetCode) {
//        EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
//
//        Query query = entityManagerObj.createQuery("SELECT s.name FROM Street s WHERE s.code = '" + streetCode + "'");
//        String name = query.getSingleResult().toString();
//
//        entityManagerObj.close();
//
//
//        return name;
//    }
//



}
