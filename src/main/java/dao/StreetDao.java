package dao;

import model.Street;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class StreetDao {


    private static final String PERSISTENCE_UNIT_NAME = "JpaUnit";
    private static EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    private static EntityTransaction transactionObj = entityManagerObj.getTransaction();


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
        if (!transactionObj.isActive()) {
            transactionObj.begin();
        }

        Street street = new Street();
        street.setCode(code);
        street.setName(streetName);

        entityManagerObj.persist(street);

        transactionObj.commit();
    }


    public static List<String> selectAllNames() {
        Query query = entityManagerObj.createQuery("Select s.name from Street s");
        List<String> namesList = query.getResultList();

        return namesList;
    }

    public static List<Street> selectAll() {
        //TODO Delete Native
        Query query = entityManagerObj.createNativeQuery("SELECT * FROM street", Street.class);
        List<Street> streetList = query.getResultList();

        return streetList;
    }

    public static int getCodeByName(String streetName) {
        Query query = entityManagerObj.createQuery("SELECT s.code FROM Street s WHERE s.name = '" + streetName + "'");
        int code = Integer.valueOf(query.getSingleResult().toString());

        return code;
    }

    public static String getNameByCode(int streetCode) {
        Query query = entityManagerObj.createQuery("SELECT s.name FROM Street s WHERE s.code = '" + streetCode + "'");
        String name = query.getSingleResult().toString();

        return name;
    }

}
