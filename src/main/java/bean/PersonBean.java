package bean;

import dao.PersonDao;
import model.Person;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean
public class PersonBean {

    private String name;
    private String surname;
    private String patronymic;
    private int editPersonId;
    private String searchName = "";
    private String searchSurname = "";

    public List<Person> getPersonsList() {
        if (searchName.isEmpty() && searchSurname.isEmpty()) {
            return PersonDao.selectAll();
        } else {
            return PersonDao.search(searchName, searchSurname);
        }
    }

    public String method() {
        if ( searchName.isEmpty() && searchSurname.isEmpty()) {
            return "cc = " + searchName + " true";
        } else {
            return "cc = " + searchName + " false";
        }
    }

    public String addNewPerson() {
        Person person = new Person();
        person.setName(name);
        person.setSurname(surname);
        person.setPatronymic(patronymic);
        return PersonDao.createPerson(person);
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

}



