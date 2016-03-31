package hk.suitup.Bean;


import java.io.Serializable;

/**
 * Created by hk on 2015/9/1.
 */
public class Cart implements Serializable{
    //entity to store a shopping cart item
    SuitInfo suitInfo;
    int count;

    public SuitInfo getSuitInfo() {
        return suitInfo;
    }

    public void setSuitInfo(SuitInfo suitInfo) {
        this.suitInfo = suitInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
