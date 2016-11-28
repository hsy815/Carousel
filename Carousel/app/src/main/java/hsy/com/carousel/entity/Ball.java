package hsy.com.carousel.entity;

import java.util.List;

/**
 * Created by hsy on 16/9/22.
 */

public class Ball {

    private int id;
    private int type;
    private String name;
    private int state;
    private String phase;
    public List<Contents> content;
    public Contento contents;

    public class Contents {

        public Result result;

        public class Result {
            private List<String> red;
            private List<String> blue;

            public List getRed() {
                return red;
            }

            public List getBlue() {
                return blue;
            }

            public void setRed(List<String> red) {
                this.red = red;
            }

            public void setBlue(List<String> blue) {
                this.blue = blue;
            }
        }
    }

    public class Contento {

        public Result result;

        public class Result {
            private List<String> red;
            private List<String> blue;

            public List getRed() {
                return red;
            }

            public List getBlue() {
                return blue;
            }

            public void setRed(List<String> red) {
                this.red = red;
            }

            public void setBlue(List<String> blue) {
                this.blue = blue;
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }
}
