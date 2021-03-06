package com.todes.personUtil.bean;

import com.todes.personUtil.dao.PersonDao;
import com.todes.personUtil.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@ManagedBean
public class PersonBean {
    private static final Logger LOGGER = LogManager.getLogger(PersonBean.class);
    private PersonDao personDao;

    private String name;
    private String surname;
    private String patronymic;
    private Person.Sex sex;
    private String date;
    private int editPersonId;
    private String searchName = "";
    private String searchSurname = "";
    private String searchDateFrom = "";
    private String searchDateTo = "";
    private String searchStreet = "";
    private String searchHouseNumber = "";

    @ManagedProperty(value = "#{addressBean}")
    private AddressBean addressBean;

    {
        personDao = new PersonDao();
    }

    public List<Object> getPersonsList() {
        try {
            if (searchStreet == null) {
                searchStreet = "";
            }

            if (searchIsEmpty()) {
                return personDao.selectAll();
            } else {
                return personDao.search(searchName, searchSurname, searchDateFrom, searchDateTo, searchStreet, searchHouseNumber);
            }

        } catch (Exception e) {
            LOGGER.error("Exception in getPersonsList() " + e);
            return new LinkedList<Object>();
        }
    }

    private boolean searchIsEmpty() {
        return (searchName.isEmpty()
                && searchSurname.isEmpty()
                && searchDateFrom.isEmpty()
                && searchDateTo.isEmpty()
                && searchStreet.isEmpty()
                && searchHouseNumber.isEmpty());
    }

    public Person extractPersonObj(Object item) {
        Object[] row = (Object[]) item;
        return (Person) row[0];
    }

    public String extractPersonObjDate(Object item) {
        Object[] row = (Object[]) item;
        Person person = (Person) row[0];
        Date date = person.getDate();
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String dateString = dateFormat.format(date);
            return dateString;
        }
    }


    public String addNewPerson() {
        LOGGER.info("------------addNewPerson-----------");
        try {
            personDao.createPerson(getPerson());
        } catch (Exception e) {
            LOGGER.error("Exception in addNewPerson(). " + e);
        }
        return "pagePersonsList.xhtml?faces-redirect=true";
    }

    private Person getPerson() {
        Person person = new Person();
        person.setName(name);
        person.setSurname(surname);
        person.setPatronymic(patronymic);
        person.setSex(sex);
        person.setDate(parseDate(date));
        person.setAddress(addressBean.getAddress());
        return person;
    }

    private Date parseDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date result = null;
        try {
            result = dateFormat.parse(date);
        } catch (Exception e) {
            LOGGER.error("Exception in PersonBean.parseDate(date). " + e);
        }
        return result;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Person.Sex getSex() {
        return sex;
    }

    public void setSex(Person.Sex sex) {
        this.sex = sex;
    }

    public Person.Sex[] getSexStates() {
        return Person.Sex.values();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEditPersonId() {
        return editPersonId;
    }

    public void setEditPersonId(int editPersonId) {
        this.editPersonId = editPersonId;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchSurname() {
        return searchSurname;
    }

    public void setSearchSurname(String searchSurname) {
        this.searchSurname = searchSurname;
    }

    public String getSearchDateFrom() {
        return searchDateFrom;
    }

    public void setSearchDateFrom(String searchDateFrom) {
        this.searchDateFrom = searchDateFrom;
    }

    public String getSearchDateTo() {
        return searchDateTo;
    }

    public void setSearchDateTo(String searchDateTo) {
        this.searchDateTo = searchDateTo;
    }

    public String getSearchStreet() {
        return searchStreet;
    }

    public void setSearchStreet(String searchStreet) {
        this.searchStreet = searchStreet;
    }

    public String getSearchHouseNumber() {
        return searchHouseNumber;
    }

    public void setSearchHouseNumber(String searchHouseNumber) {
        this.searchHouseNumber = searchHouseNumber;
    }

    public AddressBean getAddressBean() {
        return addressBean;
    }

    public void setAddressBean(AddressBean addressBean) {
        this.addressBean = addressBean;
    }
}




