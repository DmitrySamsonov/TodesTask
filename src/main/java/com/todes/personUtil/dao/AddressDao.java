package com.todes.personUtil.dao;

import com.todes.personUtil.model.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddressDao {
    private static final Logger LOGGER = LogManager.getLogger(AddressDao.class);

    // Checked!
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

        }

        return addressRow;
    }


    //TODO for test debug
//    public static void main(String[] args) {
//        EntityManager entityManagerObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
//
//        Street street = StreetDao.getStreetByName("Kulman");
//        Address address = new Address();
////        address.setStreetCode(93);
//        address.setHouseNumber(7);
//        address.setStreet(street);
//
////        createAddress2(address);
////        entityManagerObj.close();
//
//
//        int id = 9209;
//        Query query = entityManagerObj.createQuery("SELECT a.street.code FROM Address a WHERE a.id = " + id);
////        Query query = entityManagerObj.createQuery("SELECT a.houseNumber FROM Address a WHERE a.code = " +address.getStreet().getCode());
////            Street street = (Street) query.getSingleResult();
////            Address address1 = (Address) query.getSingleResult();
////            List<Object> list = query.getResultList();
//        Object list = query.getSingleResult();
//        System.out.println();
//
//
////        StringBuilder sb = new StringBuilder();
////        sb.append("SELECT a FROM Address a WHERE a.houseNumber = ");
////        sb.append(address.getHouseNumber());
////        sb.append(" AND a.street = ");
////        sb.append(address.getStreet());
//////        sb.append("");
////
////        try {
////            Query query = entityManagerObj.createQuery(sb.toString());
////            Address addressRow = (Address) query.getSingleResult();
////        }catch (Exception e){
////            System.out.println();
////        }finally {
////            entityManagerObj.close();
////        }
//
//
//        System.out.println();
//    }
//
//    //TODO for test debug
//    public static void createAddress2(Address address) {
//
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaUnit");
//        EntityManager entityManagerObj = emf.createEntityManager();
//        EntityTransaction transactionObj = entityManagerObj.getTransaction();
//
//        try {
//            if (!transactionObj.isActive()) {
//                transactionObj.begin();
//            }
//
//            entityManagerObj.persist(address);
//            transactionObj.commit();
//        } catch (Exception e) {
//            LOGGER.error("Exception in AddressDao.createAddress(address). " + e);
//            throw e;
//        } finally {
//            entityManagerObj.close();
//        }
//    }


    // Checked!
    public void createAddress(Address address) {
        try {
            DatabaseDao.getInstance().create(address);
        } catch (Exception e) {
            LOGGER.error("Exception in AddressDao.createAddress(address). " + e);
            throw e;
        }
    }


//
//    public static void main(String[] args) {
//        if (StreetDao.selectAll().isEmpty()) {
//            StreetDao.fillStreetsByDefault();
//        }
//
//        Street street = new Street();
//        street.setCode(93);
//        street.setName("Gaya");
//
//        Address address = new Address();
////        address.set(93);
//        address.setHouseNumber(4);
////        address.setStreet(street);
//
//        createAddress(address);
//    }
//

}