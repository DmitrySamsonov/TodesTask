package com.todes.personUtil.dao;

import com.todes.personUtil.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.persistence.internal.helper.ConversionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PersonDao {
    private static final Class SQL_DATE = java.sql.Date.class;
    private static final Logger LOGGER = LogManager.getLogger(PersonDao.class);

    // Checked!
    public void createPerson(Person person) {
        try {
            DatabaseDao.getInstance().create(person);
        } catch (Exception e) {
            LOGGER.error("Exception in PersonDao.createPerson(person). " + e);
            throw e;
        }
    }

    public String deletePerson(int personId) {
        EntityManager entityManagerObj = EntityManagerFactoryWeb.getEntityManager();

        Person person = entityManagerObj.find(Person.class, personId);
        if (person != null) {
            DatabaseDao.getInstance().delete(person);
        }

        entityManagerObj.close();
        return "pagePersonsList.xhtml?faces-redirect=true";
    }

    public List<Object> selectAll() {
        String jpql = "SELECT p, a  FROM Person p LEFT JOIN p.address a";
        List<Object> personList = DatabaseDao.getInstance().selectResultList(jpql);
        return personList;
    }

    public List<Object> search(String searchName, String searchSurname, String searchDateFrom,
                                      String searchDateTo, String searchStreet, String searchHouseNumber) {

        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT p, a  FROM Person p LEFT JOIN p.address a WHERE ");
        jpql.append(" p.name LIKE '" + searchName + "%'");
        jpql.append(" AND p.surname LIKE '" + searchSurname + "%'");
        appendDate(searchDateFrom, searchDateTo, jpql);
        appendStreet(searchStreet, jpql);
        appendHouseNumber(searchHouseNumber, jpql);

        List<Object> personList = null;
        try {
            personList = DatabaseDao.getInstance().selectResultList(jpql.toString());
        } catch (Exception e) {
            LOGGER.error("Exception in search() execute Query. " + e.getStackTrace());
        }

        return personList;
    }

    private void appendDate(String searchDateFrom, String searchDateTo, StringBuilder sb) {
        if (!searchDateFrom.isEmpty() || !searchDateTo.isEmpty()) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            Date dateFrom = null;
            try {
                dateFrom = dateFormat.parse("00.00.0000");
            } catch (ParseException e) {
                LOGGER.error("Fail parse 00.00.0000");
            }

            Date dateTo = new Date();

            if (!searchDateFrom.isEmpty()) {
                try {
                    dateFrom = dateFormat.parse(searchDateFrom);
                } catch (ParseException e) {
                    LOGGER.error("Fail parse searchDateFrom");
                }
            }
            LOGGER.info("Date dateFrom = " + dateFrom.toString());

            if (!searchDateTo.isEmpty()) {
                try {
                    dateTo = dateFormat.parse(searchDateTo);
                } catch (ParseException e) {
                    LOGGER.error("Fail parse searchDateTo");
                }
            }
            LOGGER.info("Date dateTo = " + dateTo.toString());

            Object start = null;
            Object end = null;
            try {
                ConversionManager conversionManager = new ConversionManager();
                start = conversionManager.convertObject(dateFrom, SQL_DATE);
                end = conversionManager.convertObject(dateTo, SQL_DATE);
            } catch (Exception e) {
                LOGGER.error("Exception when convert Date to Time. " + e);
            }

            sb.append(" AND p.date BETWEEN '" + start + "' AND '" + end + "'");
        }
    }

    private void appendStreet(String searchStreet, StringBuilder sb) {
        if (!searchStreet.isEmpty()) {
            sb.append(" AND a.street.name = '" + searchStreet + "'");
        }
    }

    private void appendHouseNumber(String searchHouseNumber, StringBuilder sb) {
        if (!searchHouseNumber.isEmpty()) {
            try {
                int searchHouseNumberInt = Integer.parseInt(searchHouseNumber);
                sb.append(" AND a.houseNumber = " + searchHouseNumberInt);
            } catch (NumberFormatException ignore) { /*NOP*/ }
        }
    }


}