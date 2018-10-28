package donation.solutions.hamza.com.hotingoadmin.model;

import com.google.gson.annotations.SerializedName;

public class UserResponce {

    @SerializedName("user")
    private User user;

    @SerializedName("token")
    private String token;

    public UserResponce() {
    }

    public UserResponce(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserResponce{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}
