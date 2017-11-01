package bean;

import dao.StreetDao;
import model.Street;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(eager = true)
public class StreetBean {

    private int code;
    private String name;

    private static final Logger logger = LogManager.getLogger(PersonBean.class);

    // Checked!
    {
        FillStreetsAtStart();
    }

//    public static void main(String[] args) {
//        new StreetBean().FillStreetsAtStart();
//    }

    public void FillStreetsAtStart() {
        if (isEmptyDataInStreets()) {
            StreetDao.fillStreetsByDefault();
        }
    }

    private boolean isEmptyDataInStreets() {
        return StreetDao.selectAll().isEmpty();
    }


    // Checked!
    public List<String> getNamesList() {
        return StreetDao.selectStreetNames();
    }

    // Checked!
    public Street getStreet() {
        Street street = StreetDao.getStreetByName(name);
        return street;
    }


//    public void calculateCodeByName() {
//        try {
//            code = StreetDao.getCodeByName(name);
//        } catch (Exception e) {
//            logger.error("Exception in StreetBean.calculateCodeByName(). e= " + e);
//            logger.info("name = " + getName()+"[in StreetBean.calculateCodeByName()]");
//            logger.info("code = " + getCode()+"[in StreetBean.calculateCodeByName()]");
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
