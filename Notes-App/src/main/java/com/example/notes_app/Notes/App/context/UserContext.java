package com.example.notes_app.Notes.App.context;

public class UserContext {
    private static final ThreadLocal<String> email = new ThreadLocal<>();

    public static String getEmail() {
        return email.get();
    }

    public static void setEmail(String userEmail) {
        email.set(userEmail);
    }

    public static void clear() {
        email.remove();
    }
}
