package model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int streetCode;
    private int houseNumber;

    @ManyToOne(targetEntity = Street.class)
    private Street street;

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

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }
}
