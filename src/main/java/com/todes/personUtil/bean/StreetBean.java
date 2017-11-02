package com.todes.personUtil.bean;

import com.todes.personUtil.dao.StreetDao;
import com.todes.personUtil.model.Street;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean
public class StreetBean {
    private static final Logger LOGGER = LogManager.getLogger(PersonBean.class);
    private StreetDao streetDao;

    private int code;
    private String name;


    // Checked!
    {
        streetDao = new StreetDao();
        FillStreetsAtStart();
    }

    public StreetBean(){

    }

    public void FillStreetsAtStart() {
        if (isEmptyDataInStreets()) {
            streetDao.fillStreetsByDefault();
        }
    }

    private boolean isEmptyDataInStreets() {
        return streetDao.selectAll().isEmpty();
    }


    // Checked!
    public List<String> getNamesList() {
        return streetDao.selectStreetNames();
    }

    // Checked!
    public Street getStreet() {
        Street street = streetDao.getStreetByName(name);
        return street;
    }


//    public void calculateCodeByName() {
//        try {
//            code = StreetDao.getCodeByName(name);
//        } catch (Exception e) {
//            LOGGER.error("Exception in StreetBean.calculateCodeByName(). e= " + e);
//            LOGGER.info("name = " + getName()+"[in StreetBean.calculateCodeByName()]");
//            LOGGER.info("code = " + getCode()+"[in StreetBean.calculateCodeByName()]");
//        }
//    }
//
//    public String getStreetNameByCode(int code) {
//        return StreetDao.getNameByCode(code);
//    }


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
