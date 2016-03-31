package hk.suitup.Bean;

/**
 * Created by sony on 2015/7/27.
 */
public class QueryInfo {
    private String type;
    private String page;
    private String size;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public QueryInfo(String type, String page, String size) {
        this.type = type;
        this.page = page;
        this.size = size;
    }
}
