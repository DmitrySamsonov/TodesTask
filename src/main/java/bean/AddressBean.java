package bean;

import model.Address;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class AddressBean {

    private int streetCode;
    private int houseNumber;

    @ManagedProperty(value = "#{streetBean}")
    private StreetBean streetBean;


    public void calculateStreetCode() {
        streetBean.calculateCode();
        streetCode = streetBean.getCode();
    }

    public Address getAddressObj(Object item){
        Object[] row = (Object[]) item;
        Address addressObj = (Address) row[1];
        return addressObj;
    }

    public String getStreetNameByCode(Object obj){
        int code = getAddressObj(obj).getStreetCode();
        return streetBean.getStreetNameByCode(code);
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

    public StreetBean getStreetBean() {
        return streetBean;
    }

    public void setStreetBean(StreetBean streetBean) {
        this.streetBean = streetBean;
    }
}
