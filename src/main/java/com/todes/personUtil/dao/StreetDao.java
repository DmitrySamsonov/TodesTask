package com.todes.personUtil.dao;

import com.todes.personUtil.model.Street;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class StreetDao {

    private static final Logger LOGGER = LogManager.getLogger(StreetDao.class);

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

    public List<Street> selectAll() {

        String jpql = "SELECT s FROM Street s";
        List<Street> streetList = DatabaseDao.getInstance().selectResultList(jpql);

        return streetList;
    }

    public List<String> selectStreetNames() {

        String jpql = "Select s.name from Street s";
        List<String> namesList = DatabaseDao.getInstance().selectResultList(jpql);

        return namesList;
    }

    public Street getStreetByName(String name) {

        Street street = null;
        try {
            String jpql = "SELECT s FROM Street s WHERE s.name = '" + name + "'";
            street = (Street) DatabaseDao.getInstance().selectSingleResult(jpql);
        } catch (Exception e) {
            LOGGER.error("Exception in StreetDao.getStreetByName(name). " + e);
        }
        return street;
    }


}
