package hk.suitup.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hk on 2015/9/13.
 */
public class Manufacturer implements Serializable{

    private String _id;
    private List<String> address;
    private String identification;
    private String manufacturername;
    private List<String> information;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getManufacturername() {
        return manufacturername;
    }

    public void setManufacturername(String manufacturername) {
        this.manufacturername = manufacturername;
    }

    public List<String> getInformation() {
        return information;
    }

    public void setInformation(List<String> information) {
        this.information = information;
    }
}
