package com.todes.personUtil.bean;

import com.todes.personUtil.dao.StreetDao;
import com.todes.personUtil.model.Street;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean
public class StreetBean {
    private static final Logger LOGGER = LogManager.getLogger(StreetBean.class);
    private StreetDao streetDao;

    private int code;
    private String name;


    {
        streetDao = new StreetDao();
        FillStreetsAtStart();
    }

    public StreetBean() {

    }

    public void FillStreetsAtStart() {
        if (isEmptyDataInStreets()) {
            streetDao.fillStreetsByDefault();
        }
    }

    private boolean isEmptyDataInStreets() {
        return streetDao.selectAll().isEmpty();
    }


    public List<String> getNamesList() {
        return streetDao.selectStreetNames();
    }

    public Street getStreet() {
        Street street = streetDao.getStreetByName(name);
        return street;
    }


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
