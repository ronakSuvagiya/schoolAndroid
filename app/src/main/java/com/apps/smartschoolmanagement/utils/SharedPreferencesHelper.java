package com.apps.smartschoolmanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import com.apps.smartschoolmanagement.models.User;

public class SharedPreferencesHelper {
    private static final String data = "Login";
    public static final String isloggedin = "ISLOGGEDIN";
    Editor editor = this.settings.edit();
    SharedPreferences settings;

    public SharedPreferencesHelper(Context context) {
        this.settings = context.getSharedPreferences(data, 0);
    }

    public void savePreferences(int type, User user) {
        this.editor.putString(isloggedin, "true");
        this.editor.putString("" + type, new Gson().toJson(user));
        if (this.settings.contains("" + type)) {
            this.editor.commit();
        } else {
            this.editor.apply();
        }
    }

    public void editPreferences(int type, User user) {
        this.editor.putString("" + type, new Gson().toJson(user, User.class));
        this.editor.commit();
    }

    public void logout() {
        this.editor.putString(isloggedin, "false");
        if (this.settings.contains(isloggedin)) {
            this.editor.apply();
        } else {
            this.editor.commit();
        }
    }

    public String getIsloggedin() {
        return this.settings.getString(isloggedin, null);
    }

    public User getData(int type) {
        Gson gson = new Gson();
        if (this.settings.contains("" + type)) {
            return (User) gson.fromJson(this.settings.getString("" + type, ""), User.class);
        }
        return null;
    }

    public String getId(int type) {
        Gson gson = new Gson();
        if (this.settings.contains("0")) {
            return ((User) gson.fromJson(this.settings.getString("" + type, ""), User.class)).getId();
        }
        return null;
    }

    public <GenericClass> GenericClass getSavedObjectFromPreference(String preferenceKey, Class<GenericClass> classType) {
        if (this.settings.contains(preferenceKey)) {
            return new Gson().fromJson(this.settings.getString(preferenceKey, ""), classType);
        }
        return null;
    }
}
