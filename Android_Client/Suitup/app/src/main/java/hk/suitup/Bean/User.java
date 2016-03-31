package hk.suitup.Bean;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sony on 2015/7/30.
 */
public class User implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;

    String parameter_id;

    //These attributes can be changed in the profile fragment
    String username;
    String password;
    String age;
    String address;

    String email;
    String phonenumber;
    String clientname;
    String avater_id;
    String _id;

    List<String> collections_id;
    List<String> photos_id;
    double account;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getParameter_id() {
        return parameter_id;
    }

    public void setParameter_id(String parameter_id) {
        this.parameter_id = parameter_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvater_id() {
        return avater_id;
    }

    public void setAvater_id(String avater_id) {
        this.avater_id = avater_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getCollections_id() {
        return collections_id;
    }

    public void setCollections_id(List<String> collections_id) {
        this.collections_id = collections_id;
    }

    public List<String> getPhotos_id() {
        return photos_id;
    }

    public void setPhotos_id(List<String> photos_id) {
        this.photos_id = photos_id;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }
}
