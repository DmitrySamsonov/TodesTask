package com.todes.personUtil.dao;

import com.todes.personUtil.model.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddressDao {
    private static final Logger LOGGER = LogManager.getLogger(AddressDao.class);

    public Address getAddressFromDatabase(Address address) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a FROM Address a WHERE a.houseNumber = ");
        sb.append(address.getHouseNumber());
        sb.append(" AND a.street.code = ");
        sb.append(address.getStreet().getCode());

        Address addressRow = null;
        try {
            Object result = DatabaseDao.getInstance().selectSingleResult(sb.toString());
            if (result != null && result instanceof Address) {
                addressRow = (Address) result;
            }

        } catch (Exception e) {
            LOGGER.error("Exception in AddressDao.getAddressFromDatabase(address). " + e);

        }

        return addressRow;
    }


    public void createAddress(Address address) {
        try {
            DatabaseDao.getInstance().create(address);
        } catch (Exception e) {
            LOGGER.error("Exception in AddressDao.createAddress(address). " + e);
            throw e;
        }
    }

}