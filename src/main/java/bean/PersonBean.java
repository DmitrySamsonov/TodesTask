package bean;

import dao.AddressDao;
import dao.PersonDao;
import dao.StreetDao;
import model.Address;
import model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@ManagedBean
public class PersonBean {

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

    private static final Logger logger = LogManager.getLogger(PersonBean.class);

//    //TODO for test debug...
//    //TODO for test debug...
//    public PersonDao personDao = new PersonDao();
//    public StreetDao streetDao = new StreetDao();

    public List<Object> getPersonsList() {
        try {
            if (searchStreet == null) {
                searchStreet = "";
            }

            if (searchIsEmpty()) {
                return PersonDao.selectAll();
            } else {
                return PersonDao.search(searchName, searchSurname, searchDateFrom, searchDateTo, searchStreet, searchHouseNumber);
            }

        } catch (Exception e) {
            logger.error("Exception in getPersonsList() " + e);
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

    // Checked!
    public Person extractPersonObj(Object item) {
        Object[] row = (Object[]) item;
        return (Person) row[0];
    }

    public String extractPersonObjDate(Object item) {
        Object[] row = (Object[]) item;
        Person person = (Person) row[0];
        Date date = person.getDate();
        if( date == null){
            return "";
        }else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String dateString = dateFormat.format(date);
            return dateString;
        }
    }


    //TODO for test debug...
//    public static void main(String[] args) {
//        PersonBean personBean = new PersonBean();
//        personBean.setName("a");
//        personBean.setSurname("d");
//        personBean.setPatronymic("f");
//        personBean.setDate("23.05.1995");
//
//
//        personBean.addNewPerson();
//    }

    // Checked!
    public String addNewPerson() {
        logger.info("------------addNewPerson-----------");
        try {
            PersonDao.createPerson(getPerson());
        } catch (Exception e) {
            logger.error("Exception in addNewPerson(). " + e);
        }
        return "pagePersonsList.xhtml?faces-redirect=true";
    }

    // Checked!
    private Person getPerson() {
        Person person = new Person();
        person.setName(name);
        person.setSurname(surname);
        person.setPatronymic(patronymic);
        person.setSex(sex);
        person.setDate(parseDate(date));


        //TODO for test debug...
//        AddressBean addressBean = new AddressBean();
//        addressBean.setHouseNumber(88);



        person.setAddress(addressBean.getAddress());
        return person;
    }

    // Checked!
    private Date parseDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date result = null;
        try {
            result = dateFormat.parse(date);
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }

    public String editPersonById(Person person) {
        editPersonId = person.getId();
        name = person.getName();
        surname = person.getSurname();
        patronymic = person.getPatronymic();
        return "pageEditPerson.xhtml";
    }

    public String savePersonEdit() {
        //TODO: говно какое-то. надо попробовать переделать! Попробуй создать вверху поле Person
        Person person = new Person();
        person.setId(editPersonId);
        person.setName(name);
        person.setSurname(surname);
        person.setPatronymic(patronymic);
        return PersonDao.editPerson(person);
    }

    public String deletePersonById() {
        int personId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("PersonId"));
        return PersonDao.deletePerson(personId);
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




