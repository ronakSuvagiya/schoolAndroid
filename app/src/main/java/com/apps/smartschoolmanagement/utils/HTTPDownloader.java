package com.apps.smartschoolmanagement.utils;

import java.io.IOException;

public class HTTPDownloader {
    public static void downloadFile(String url, String path) {
        String fileURL = "http://jdbc.postgresql.org/download/postgresql-9.2-1002.jdbc4.jar";
        String saveDir = "E:/Download";
        try {
            HttpDownloadUtility.downloadFile(url, path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
