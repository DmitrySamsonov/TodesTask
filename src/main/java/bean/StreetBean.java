package bean;

import dao.StreetDao;
import model.Street;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(eager = true)
@ApplicationScoped
public class StreetBean {

    private int code;
    private String name;

    {
        FillStreetsAtStart();
    }

    public void FillStreetsAtStart() {
        if (isEmptyDataInStreets()) {
            StreetDao.fillStreetsByDefault();
        }
    }

    private boolean isEmptyDataInStreets() {
        return StreetDao.selectAll().isEmpty();
    }


    public List<String> getNamesList() {
        return StreetDao.selectAllNames();
    }

    public List<Street> getStreetsList() {
        return StreetDao.selectAll();
    }

    public void calculateCode() {
        code = StreetDao.getCodeByName(name);
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
