package com.apps.smartschoolmanagement.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.models.OnlineMaterial;
import java.util.ArrayList;
import java.util.HashMap;

public class ProfileInfo {
    private static volatile ProfileInfo instance;
    public static String savePath = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/SVR/");
    private HashMap<String, ArrayList<CodeValue>> CacheCodes = new HashMap();
    private HashMap<String, String> Params = new HashMap();
    public ArrayList<Bitmap> album_images = new ArrayList();
    public HashMap<String, ArrayList<OnlineMaterial>> dates = new HashMap();
    public HashMap<String, Bitmap> hashmap_images_album = new HashMap();
    private boolean isFirstSaved = false;
    private boolean isLastSaved = false;
    private boolean isLoggedIn = false;
    private String location;
    private HashMap<String, String> loginData = new HashMap();
    private String pendingCount;
    private int pendingFilesCount = 0;
    private String saveLocation;
    public int selectedTab = 0;
    private ListData staffData;
    public ArrayList<ListData> stud = new ArrayList();
    public ArrayList<ListData> teach = new ArrayList();
    private String userEmail;
    private String userId;
    private String userMobile;
    private String userName;
    private String userPic;
    private String volleyResponse = null;

    public HashMap<String, ArrayList<OnlineMaterial>> getDates() {
        return this.dates;
    }

    public void setDates(HashMap<String, ArrayList<OnlineMaterial>> dates) {
        this.dates = dates;
    }

    public ArrayList<ListData> getStud() {
        return this.stud;
    }

    public int getSelectedTab() {
        return this.selectedTab;
    }

    public void setSelectedTab(int selectedTab) {
        this.selectedTab = selectedTab;
    }

    public void setStud(ArrayList<ListData> stud) {
        this.stud = stud;
    }

    public ArrayList<ListData> getTeach() {
        return this.teach;
    }

    public void setTeach(ArrayList<ListData> teach) {
        this.teach = teach;
    }

    public HashMap<String, Bitmap> getHashmap_images_album() {
        return this.hashmap_images_album;
    }

    public void setHashmap_images_album(HashMap<String, Bitmap> hashmap_images_album) {
        this.hashmap_images_album = hashmap_images_album;
    }

    public ArrayList<Bitmap> getAlbum_images() {
        return this.album_images;
    }

    public void setAlbum_images(ArrayList<Bitmap> album_images) {
        this.album_images = album_images;
    }

    public HashMap<String, String> getParams() {
        return this.Params;
    }

    public HashMap<String, String> getLoginData() {
        return this.loginData;
    }

    public void setLoginData(HashMap<String, String> loginData) {
        this.loginData = loginData;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        return this.userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return this.userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }

    public ListData getStaffData() {
        return this.staffData;
    }

    public void setStaffData(ListData staffData) {
        this.staffData = staffData;
    }

    public String getVolleyResponse() {
        return this.volleyResponse;
    }

    public void setVolleyResponse(String volleyResponse) {
        this.volleyResponse = volleyResponse;
    }

    public boolean isLastSaved() {
        return this.isLastSaved;
    }

    public void setLastSaved(boolean lastSaved) {
        this.isLastSaved = lastSaved;
    }

    public boolean isFirstSaved() {
        return this.isFirstSaved;
    }

    public void setFirstSaved(boolean firstSaved) {
        this.isFirstSaved = firstSaved;
    }

    public String getSaveLocation() {
        return this.saveLocation;
    }

    public void setSaveLocation(String location) {
        this.saveLocation = getLocation() + location;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPendingFilesCount() {
        return this.pendingFilesCount;
    }

    public void setPendingFilesCount(int pendingFilesCount) {
        this.pendingFilesCount = pendingFilesCount;
    }

    public static ProfileInfo getInstance() {
        if (instance == null) {
            synchronized (ProfileInfo.class) {
                if (instance == null) {
                    instance = new ProfileInfo();
                }
            }
        }
        return instance;
    }

    public boolean hasCodes(String argUrl) {
        return this.CacheCodes.containsKey(argUrl);
    }

    public void addCodes(String argUrl, ArrayList<CodeValue> argCodes) {
        this.CacheCodes.put(argUrl, argCodes);
    }

    public ArrayList<CodeValue> getCodeValues(String argUrl) {
        return (ArrayList) this.CacheCodes.get(argUrl);
    }

    private ProfileInfo() {
    }

    public void onDestroy(Context context) {
        instance = null;
    }
}
