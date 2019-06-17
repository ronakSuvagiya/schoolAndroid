package com.apps.smartschoolmanagement.models;

public class PhotoAlbum {
    public String imagePaths;
    public String[] pathList = null;
    public String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePaths() {
        return this.imagePaths;
    }

    public void setImagePaths(String imagePaths) {
        this.imagePaths = imagePaths;
    }

    public String[] getPathList() {
        return this.pathList;
    }

    public void setPathList(String[] pathList) {
        this.pathList = pathList;
    }
}
