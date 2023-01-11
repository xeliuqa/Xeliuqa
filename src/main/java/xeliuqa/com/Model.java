package xeliuqa.com;

public class Model {

    public String id;
    public String percentage_change_1h;
    public String percentage_change_24h;
    public String percentage_change_7d;

    public Model() {
    }

    public Model(String id, String percentage_change_1h, String percentage_change_24h, String percentage_change_7d) {
        this.id = id;
        this.percentage_change_1h = percentage_change_1h;
        this.percentage_change_24h = percentage_change_24h;
        this.percentage_change_7d = percentage_change_7d;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPercentage_change_1h() {
        return percentage_change_1h;
    }

    public void setPercentage_change_1h(String percentage_change_1h) {
        this.percentage_change_1h = percentage_change_1h;
    }

    public String getPercentage_change_24h() {
        return percentage_change_24h;
    }

    public void setPercentage_change_24h(String percentage_change_24h) {
        this.percentage_change_24h = percentage_change_24h;
    }

    public String getPercentage_change_7d() {
        return percentage_change_7d;
    }

    public void setPercentage_change_7d(String percentage_change_7d) {
        this.percentage_change_7d = percentage_change_7d;
    }
}

