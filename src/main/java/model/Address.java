package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int streetCode;
    private int houseNumber;

    @OneToMany( targetEntity=Person.class )
    private List personList;

    public int getId() {
        return id;
    }

    public int getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(int streetCode) {
        this.streetCode = streetCode;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public List getPersonList() {
        return personList;
    }

    public void setPersonList(List personList) {
        this.personList = personList;
    }
}
