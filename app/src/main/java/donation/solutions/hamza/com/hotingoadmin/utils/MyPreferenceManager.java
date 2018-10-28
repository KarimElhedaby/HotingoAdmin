package donation.solutions.hamza.com.hotingoadmin.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import donation.solutions.hamza.com.hotingoadmin.model.User;
import donation.solutions.hamza.com.hotingoadmin.model.UserResponce;


public class MyPreferenceManager {

    private String TAG = MyPreferenceManager.class.getSimpleName();
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences pref_Notification;


    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor_Notification;

    // Context
    Context _context;

    // Sharedpref file name
    private static final String PREF_NAME = "Donation_Pref";
    private static final String PREF_NOTIFICATION_TOKEN = "Donation_Notification_Token_Pref";


    // All Shared Preferences Keys
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_TOKEN = "user_token";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_PHONE = "user_phone";
    private static final String KEY_USER_TYPE = "user_type";

    private static final String KEY_USER_NOTIFICATION_TOKEN = "user_notification_token";


    // Constructor
    public MyPreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, 0);
        pref_Notification = _context.getSharedPreferences(PREF_NOTIFICATION_TOKEN, 0);
        editor = pref.edit();
        editor_Notification = pref_Notification.edit();
    }


    public void storeUser(UserResponce user) {
        editor.putString(KEY_USER_TOKEN, user.getToken());
        editor.putString(KEY_USER_ID, user.getUser().getUser_id());
        editor.putString(KEY_USER_NAME, user.getUser().getName());
        editor.putString(KEY_USER_EMAIL, user.getUser().getEmail());
        editor.putString(KEY_USER_PHONE, user.getUser().getPhone());
        editor.commit();

        Log.e(TAG, "User is stored in shared preferences. " + user.getUser().getName() + ", " + user.getUser().getEmail());
    }


    public UserResponce getUser() {

        if (pref.getString(KEY_USER_EMAIL, null) != null) {
            String token, id, name, email, phone,type;
            token = pref.getString(KEY_USER_TOKEN, null);
            id = pref.getString(KEY_USER_ID, null);
            name = pref.getString(KEY_USER_NAME, null);
            email = pref.getString(KEY_USER_EMAIL, null);
            phone = pref.getString(KEY_USER_PHONE, null);
            type = pref.getString(KEY_USER_TYPE, null);

            User user = new User(id, email,name,null, phone,type);
            UserResponce userResponce = new UserResponce(user, token);
            return userResponce;
        }
        return null;
    }

    public void storeNotificationToken(String token) {
        editor_Notification.putString(KEY_USER_NOTIFICATION_TOKEN, token);
        editor_Notification.commit();
    }

    public String getNotificationToken() {
        if (pref_Notification.getString(KEY_USER_NOTIFICATION_TOKEN, null) != null) {
            String notifictionToken = pref_Notification.getString(KEY_USER_NOTIFICATION_TOKEN, null);
            return notifictionToken;
        }
        return null;

    }

    public void clear() {
        editor.clear();
        editor.commit();

    }
}