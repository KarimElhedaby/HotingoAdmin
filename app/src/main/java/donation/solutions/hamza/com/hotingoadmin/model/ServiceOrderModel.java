package donation.solutions.hamza.com.hotingoadmin.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("all")
public class ServiceOrderModel implements Serializable {
    private final String status;

    private final String creationDate;

    @SerializedName("_id")
    private final String id;

    private final String note;

    private final int roomNo;

    private final UserServicesOrded user;

    private final Service service;

    private final int v;

    public ServiceOrderModel(String status, String creationDate, String id, String note, int roomNo,
                             UserServicesOrded userServicesOrded, Service service, int v) {
        this.status = status;
        this.creationDate = creationDate;
        this.id = id;
        this.note = note;
        this.roomNo = roomNo;
        this.user = userServicesOrded;
        this.service = service;
        this.v = v;
    }

    public String getStatus() {
        return status;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public UserServicesOrded getUserServicesOrded() {
        return user;
    }

    public Service getService() {
        return service;
    }

    public int getV() {
        return v;
    }

    public static class UserServicesOrded implements Serializable {
        private final String type;

        private final String img;

        private final String creationDate;

        @SerializedName("_id")
        private final String id;

        private final String name;

        private final String phone;

        private final String email;

        private final String password;

        private final int v;

        public UserServicesOrded(String type, String img, String creationDate, String id,
                                 String name, String phone, String email, String password, int v) {
            this.type = type;
            this.img = img;
            this.creationDate = creationDate;
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.password = password;
            this.v = v;
        }

        public String getType() {
            return type;
        }

        public String getImg() {
            return img;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public int getV() {
            return v;
        }
    }

    public static class Service implements Serializable {
        private final String img;

        private final String creationDate;

        @SerializedName("_id")
        private final String id;

        private final String desc;

        private final String name;

        private final int v;

        public Service(String img, String creationDate, String id, String desc, String name,
                       int v) {
            this.img = img;
            this.creationDate = creationDate;
            this.id = id;
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
            return id;
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
}
