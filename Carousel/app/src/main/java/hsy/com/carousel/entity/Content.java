package hsy.com.carousel.entity;

import java.util.List;

/**
 * Created by hsy on 16/9/22.
 */

public class Content {
    private int total;
    private List<Ball> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Ball> getData() {
        return data;
    }

    public void setData(List<Ball> data) {
        this.data = data;
    }
}
