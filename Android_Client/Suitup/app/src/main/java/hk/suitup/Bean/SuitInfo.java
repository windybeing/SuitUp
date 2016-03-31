package hk.suitup.Bean;

import android.content.Context;

import java.io.Serializable;

import hk.suitup.Service.QueryService;

/**
 * Created by hk on 2015/7/27.
 */
public class SuitInfo implements Serializable{

    String name;
    String pic_name;
    String manufacturer_name;

    QueryService.Suit suit;

    public String getPic_name() {
        return pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SuitInfo(){}

    public SuitInfo(String name, String pic_name, String manufacturer_name)
    {
        this.name = name;
        this.pic_name = pic_name;
        this.manufacturer_name = manufacturer_name;
    }

    public int getImageResourceId( Context context )
    {
        try
        {
            return context.getResources().getIdentifier(this.pic_name, "drawable", context.getPackageName());

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    public String getManufacturer_name() {
        return manufacturer_name;
    }

    public void setManufacturer_name(String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
    }

    public QueryService.Suit getSuit() {
        return suit;
    }

    public void setSuit(QueryService.Suit suit) {
        this.suit = suit;
    }
}
