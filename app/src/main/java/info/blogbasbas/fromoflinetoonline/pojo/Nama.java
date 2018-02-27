package info.blogbasbas.fromoflinetoonline.pojo;

/**
 * Created by User on 26/02/2018.
 */

public class Nama {
    private String name;
    private int status;

    public Nama(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
