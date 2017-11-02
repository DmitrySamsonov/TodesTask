package com.todes.personUtil.bean;

import com.todes.personUtil.dao.AddressDao;
import com.todes.personUtil.model.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class AddressBean {
    private static final Logger LOGGER = LogManager.getLogger(AddressBean.class);
    private AddressDao addressDao;

    private int code;
    private int houseNumber;

    @ManagedProperty(value = "#{streetBean}")
    private StreetBean streetBean;

    {
        addressDao = new AddressDao();
    }

    public Address getAddress() {
        Address address = new Address();
        try {
            address.setHouseNumber(houseNumber);
            address.setStreet(streetBean.getStreet());
        } catch (Exception e) {
            LOGGER.error("Exception in AddressBean.getAddress(). build address " + e);
        }

        try {
            Address addressRow = addressDao.getAddressFromDatabase(address);
            if (addressRow != null) {
                address = addressRow;
            } else {
                try {
                    addressDao.createAddress(address);
                } catch (Exception e) {
                    LOGGER.error("Exception in AddressBean.getAddress(). create address in db " + e);
                }
                address = addressDao.getAddressFromDatabase(address);
            }
        } catch (Exception e) {
            LOGGER.error("Exception in AddressBean.getAddress. get address from db");
        }

        return address;
    }


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
