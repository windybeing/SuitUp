package hk.suitup.Bean;

import java.io.Serializable;

/**
 * Created by hk on 2015/9/11.
 */
public class Parameters implements Serializable{

    private String bust;
    private String waistline;
    private String hipline;
    private String shoulder;
    private String forebreast;
    private String metathorax;
    private String upperlimb;
    private String lowerlimb;

    public String getBust() {
        return bust;
    }

    public void setBust(String bust) {
        this.bust = bust;
    }

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getHipline() {
        return hipline;
    }

    public void setHipline(String hipline) {
        this.hipline = hipline;
    }

    public String getShoulder() {
        return shoulder;
    }

    public void setShoulder(String shoulder) {
        this.shoulder = shoulder;
    }

    public String getForebreast() {
        return forebreast;
    }

    public void setForebreast(String forebreast) {
        this.forebreast = forebreast;
    }

    public String getMetathorax() {
        return metathorax;
    }

    public void setMetathorax(String metathorax) {
        this.metathorax = metathorax;
    }

    public String getUpperlimb() {
        return upperlimb;
    }

    public void setUpperlimb(String upperlimb) {
        this.upperlimb = upperlimb;
    }

    public String getLowerlimb() {
        return lowerlimb;
    }

    public void setLowerlimb(String lowerlimb) {
        this.lowerlimb = lowerlimb;
    }
}
