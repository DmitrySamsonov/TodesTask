package com.todes.personUtil.bean;

import com.todes.personUtil.dao.AddressDao;
import com.todes.personUtil.model.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class AddressBean {
    private static final Logger LOGGER = LogManager.getLogger(PersonBean.class);
    private AddressDao addressDao;

    private int code;
    private int houseNumber;

    @ManagedProperty(value = "#{streetBean}")
    private StreetBean streetBean;


    public AddressBean() {
        addressDao = new AddressDao();
    }

    // Checked!
    public Address getAddress() {
        Address address = new Address();
        try {
            address.setHouseNumber(houseNumber);
            address.setStreet(streetBean.getStreet());
        } catch (Exception e) {
            LOGGER.error("Exception in AddressBean.getAddressFromBean(). " + e);
        }

        Address addressRow = addressDao.getAddressFromDatabase(address);
        if (addressRow != null) {
            address = addressRow;
        } else {
            try {
                addressDao.createAddress(address);
            } catch (Exception e) {
                LOGGER.error("Exception in AddressBean.createAddress. " + e);
            }
            address = addressDao.getAddressFromDatabase(address);
        }

        return address;
    }


//    public void calculateStreetCode() {
//        try {
//            streetBean.calculateCodeByName();
//            code = streetBean.getCode();
//        } catch (Exception e) {
//            LOGGER.error("Exception in AddressBean.calculateStreetCode(). " + e);
//        }
//    }

//    public String getStreetNameByCode(Object obj) {
//        int code = extractAddressObj(obj).getCode();
//        return streetBean.getStreetNameByCode(code);
//    }


    public Address extractAddressObj(Object item) {
        Object[] row = (Object[]) item;
        return (Address) row[1];
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
