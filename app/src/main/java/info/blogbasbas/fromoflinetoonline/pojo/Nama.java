package info.blogbasbas.fromoflinetoonline.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 26/02/2018.
 */

public class Nama {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
