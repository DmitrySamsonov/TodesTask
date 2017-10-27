package bean;

import dao.PersonDao;
import model.Address;
import model.Person;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@ManagedBean
public class PersonBean {

    private String name;
    private String surname;
    private String patronymic;
    private Person.Sex sex;
    private Date date;
    private int editPersonId;
    private String searchName = "";
    private String searchSurname = "";
    private String searchStreet = "";
    private String searchHouseNumber = "";

    @ManagedProperty(value = "#{addressBean}")
    private AddressBean addressBean;

    public List<Object> getPersonsList() {
        if (searchName.isEmpty() && searchSurname.isEmpty() && searchStreet.isEmpty() && searchHouseNumber.isEmpty()) {
//        if (searchName.isEmpty() && searchSurname.isEmpty() && searchHouseNumber.isEmpty()) {
            return PersonDao.selectAll();
        } else {


//            return PersonDao.search(searchName, searchSurname, "", searchHouseNumber);
            return PersonDao.search(searchName, searchSurname, searchStreet, searchHouseNumber);
        }
    }

    public String method() {
        if (searchName.isEmpty() && searchSurname.isEmpty()) {
            return "cc = " + searchName + " true";
        } else {
            return "cc = " + searchName + " false";
        }
    }

    public Person getPersonObj(Object item){
        Object[] row = (Object[]) item;
        Person personObj = (Person) row[0];
//        Address addressOb = (Address) row[1];
        return personObj;
    }

    public String addNewPerson() {
        Person person = new Person();
        person.setName(name);
        person.setSurname(surname);
        person.setPatronymic(patronymic);
        person.setSex(sex);
        person.setDate(date);

        Address address = new Address();
        addressBean.calculateStreetCode();
        address.setStreetCode(addressBean.getStreetCode());
        address.setHouseNumber(addressBean.getHouseNumber());
        return PersonDao.createPerson(person, address);
    }

    public String editPersonById(Person person) {
        editPersonId = person.getId();
        name = person.getName();
        surname = person.getSurname();
        patronymic = person.getPatronymic();
        return "pageEditPerson.xhtml";
    }

    public String savePersonEdit() {
        //TODO: гогвно какое-то. надо попробовать переделать! Попробуй создать вверху поле Person
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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




