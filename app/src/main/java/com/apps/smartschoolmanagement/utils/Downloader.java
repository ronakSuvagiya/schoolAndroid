package com.apps.smartschoolmanagement.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
    public static void DownloadFile(String fileURL, File directory) {
        try {
            FileOutputStream f = new FileOutputStream(directory);
            HttpURLConnection c = (HttpURLConnection) new URL(fileURL).openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            while (true) {
                int len1 = in.read(buffer);
                if (len1 > 0) {
                    f.write(buffer, 0, len1);
                } else {
                    f.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
