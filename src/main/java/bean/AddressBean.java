package bean;

import model.Street;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class AddressBean {

    private int streetCode;
    private int houseNumber;

    @ManagedProperty(value = "#{street}")
    private Street streetBean;

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

    public Street getStreetBean() {
        return streetBean;
    }

    public void setStreetBean(Street streetBean) {
        this.streetBean = streetBean;
    }
}
