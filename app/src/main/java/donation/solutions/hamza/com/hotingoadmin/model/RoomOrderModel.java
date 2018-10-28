package donation.solutions.hamza.com.hotingoadmin.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("all")

public class RoomOrderModel implements Serializable {
    String status;

    String creationDate;

    @SerializedName("_id")
    String id;

    String phone;

    String notes;

    String duration;

    UserRoomorder user;

    Room room;

    int v;

    public RoomOrderModel(String status, String creationDate, String id, String phone,
                          String duration, UserRoomorder userRoomorder, Room room, int v, String notes) {
        this.status = status;
        this.creationDate = creationDate;
        this.id = id;
        this.phone = phone;
        this.duration = duration;
        this.user = userRoomorder;
        this.room = room;
        this.v = v;
        this.notes = notes;
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
    public String getNotes() {
        return notes;
    }

    public String getPhone() {
        return phone;
    }

    public String getDuration() {
        return duration;
    }

    public UserRoomorder getUserRoomorder() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public int getV() {
        return v;
    }

    public static class UserRoomorder implements Serializable {
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

        public UserRoomorder(String type, String img, String creationDate, String id, String name,
                             String phone, String email, String password, int v) {
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

    public static class Room implements Serializable {
        private final List<String> imgs;

        private final String creationDate;

        @SerializedName("_id")
        private final String id;

        private final String number;

        private final int price;

        private final String desc;

        private final int v;

        public Room(List<String> imgs, String creationDate, String id, String number, int price,
                    String desc, int v) {
            this.imgs = imgs;
            this.creationDate = creationDate;
            this.id = id;
            this.number = number;
            this.price = price;
            this.desc = desc;
            this.v = v;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public String getId() {
            return id;
        }

        public String getNumber() {
            return number;
        }

        public int getPrice() {
            return price;
        }

        public String getDesc() {
            return desc;
        }

        public int getV() {
            return v;
        }
    }
}
