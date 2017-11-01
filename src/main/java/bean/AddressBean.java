package bean;

import dao.AddressDao;
import model.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class AddressBean {

    private int code;
    private int houseNumber;

    @ManagedProperty(value = "#{streetBean}")
    private StreetBean streetBean;

    private static final Logger logger = LogManager.getLogger(PersonBean.class);


    // Checked!
    public Address getAddress() {
        Address address = new Address();
        try {
            address.setHouseNumber(houseNumber);

            //TODO for test debug...
//            StreetBean streetBean = new StreetBean();
//            streetBean.setName("Kulman");


            address.setStreet(streetBean.getStreet());
        } catch (Exception e) {
            logger.error("Exception in AddressBean.getAddressFromBean(). " + e);
        }

        //TODO for test debug...
//        AddressDao addressDao = new AddressDao();

        //TODO for test debug...
//        Address addressRow = addressDao.getAddressFromDatabase(address);
        Address addressRow = AddressDao.getAddressFromDatabase(address);
        if (addressRow != null) {
            address = addressRow;
        } else {
            try {
                //TODO for test debug...
//                addressDao.createAddress(address);
                AddressDao.createAddress(address);
            } catch (Exception e) {
                logger.error("Exception in AddressBean.createAddress. " + e);
            }
            //TODO for test debug...
//            address = addressDao.getAddressFromDatabase(address);
            address = AddressDao.getAddressFromDatabase(address);
        }

        return address;
    }


//    public void calculateStreetCode() {
//        try {
//            streetBean.calculateCodeByName();
//            code = streetBean.getCode();
//        } catch (Exception e) {
//            logger.error("Exception in AddressBean.calculateStreetCode(). " + e);
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
