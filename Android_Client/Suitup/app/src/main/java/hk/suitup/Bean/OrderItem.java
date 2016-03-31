package hk.suitup.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hk on 2015/9/5.
 */
public class OrderItem implements Serializable{

    @SerializedName("suit_id")
    private String suit_id;
    @SerializedName("manufacturer_id")
    private String manufacturer_id;
    @SerializedName("count")
    private Integer count;
    @SerializedName("price")
    private Double price;
    @SerializedName("seller_id")
    private String seller_id;

    public String getSuit_id() {
        return suit_id;
    }

    public void setSuit_id(String suit_id) {
        this.suit_id = suit_id;
    }

    public String getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(String manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}