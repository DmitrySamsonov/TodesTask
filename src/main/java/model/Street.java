package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "street")
public class Street implements Serializable {

    @Id
    private int code;

    @Column(unique = true)
    private String name;

    @OneToMany(targetEntity = Address.class, mappedBy = "street", cascade = CascadeType.PERSIST)
    private List<Address> addresses = new ArrayList<>();


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
