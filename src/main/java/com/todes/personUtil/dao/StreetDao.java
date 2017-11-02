package com.todes.personUtil.dao;

import com.todes.personUtil.model.Street;
import com.todes.personUtil.bean.PersonBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class StreetDao {

    private static final Logger LOGGER = LogManager.getLogger(PersonBean.class);

    public void fillStreetsByDefault() {
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

    private void createStreet(int code, String streetName) {
        Street street = new Street();
        street.setCode(code);
        street.setName(streetName);

        DatabaseDao.getInstance().create(street);
    }

    // Checked!
    public List<Street> selectAll() {

        String jpql = "SELECT s FROM Street s";
        List<Street> streetList = DatabaseDao.getInstance().selectResultList(jpql);

        return streetList;
    }

    // Checked!
    public List<String> selectStreetNames() {

        String jpql = "Select s.name from Street s";
        List<String> namesList = DatabaseDao.getInstance().selectResultList(jpql);

        return namesList;
    }

    // Checked!
    public Street getStreetByName(String name) {

        String jpql = "SELECT s FROM Street s WHERE s.name = '" + name + "'";
        Street street = (Street) DatabaseDao.getInstance().selectSingleResult(jpql);

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
//            LOGGER.error("Exception in StreetDao.getCodeByName(name). " + e);
//        }
//
//        LOGGER.info("code= " + code + "[in StreetDao.getCodeByName(name)]");
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
