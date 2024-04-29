package edu.esprit.entities;
import java.util.prefs.Preferences;

public class SessionManager {
    private static final String EMAIL_KEY = "user_email";
    private static final String IMAGE_KEY = "user_image";
    private static final String SESSION_KEY = "user_session";
    private static final String ROLE_KEY = "user_role";
    private static final String ID_KEY = "user_id";
    private static final String NOM_KEY = "user_nom";
    private static Preferences prefs = Preferences.userRoot().node(SessionManager.class.getName());

    public static int getUserId() {
        return prefs.getInt(ID_KEY, 0); // Return 0 if no id is stored
    }

    public static void setUserId(int id) {
        prefs.putInt(ID_KEY, id);
    }
    public static String getUserNom()
    {
        return prefs.get(NOM_KEY,null);
    }
    public static void setUserNom(String image)
    {
        prefs.put(NOM_KEY,image);
    }

    public static String getUserImage()
    {
        return prefs.get(IMAGE_KEY,null);
    }
    public static void setUserImage(String image)
    {
        prefs.put(IMAGE_KEY,image);
    }
    // Method to retrieve the logged-in user's email
    public static String getUserEmail() {
        return prefs.get(EMAIL_KEY, null); // Returns null if no email is stored
    }

    // Method to store the user's email upon login
    public static void setUserRole(String role) {
        prefs.put(ROLE_KEY, role);
    }
    public static String getUserRole() {
        return prefs.get(ROLE_KEY, null); // Returns null if no email is stored
    }

    // Method to store the user's email upon login
    public static void setUserEmail(String email) {
        prefs.put(EMAIL_KEY, email);
    }


    // Check if the session key has a true value
    public static boolean hasSession() {
        return prefs.getBoolean(SESSION_KEY, false);
    }

    // Set the session key to true
    public static void createSession() {
        prefs.putBoolean(SESSION_KEY, true);
    }

    // Set the session key to false
    public static void endSession() {
        prefs.putBoolean(SESSION_KEY, false);
    }
}
