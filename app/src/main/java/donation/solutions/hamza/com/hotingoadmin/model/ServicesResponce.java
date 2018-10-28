package donation.solutions.hamza.com.hotingoadmin.model;

import java.io.Serializable;

public class ServicesResponce implements Serializable {

    private final String img;

    private final String creationDate;

    private final String _id;

    private final String desc;

    private final String name;

    private final int v;

    public ServicesResponce(String img, String creationDate, String id, String desc, String name,
                            int v) {
        this.img = img;
        this.creationDate = creationDate;
        this._id = id;
        this.desc = desc;
        this.name = name;
        this.v = v;
    }

    public String getImg() {
        return img;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getId() {
        return _id;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public int getV() {
        return v;
    }
}
