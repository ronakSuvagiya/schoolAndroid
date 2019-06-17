package com.apps.smartschoolmanagement.utils;

import com.google.common.net.HttpHeaders;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloadUtility {
    private static final int BUFFER_SIZE = 4096;

    public static void downloadFile(String fileURL, String saveDir) throws IOException {
        HttpURLConnection httpConn = (HttpURLConnection) new URL(fileURL).openConnection();
        int responseCode = httpConn.getResponseCode();
        if (responseCode == 200) {
            String fileName = "";
            String disposition = httpConn.getHeaderField(HttpHeaders.CONTENT_DISPOSITION);
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
            if (disposition != null) {
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10, disposition.length() - 1);
                }
            } else {
                String str = fileURL;
                fileName = str.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
            }
            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);
            InputStream inputStream = httpConn.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(saveDir + File.separator + fileName);
            byte[] buffer = new byte[4096];
            while (true) {
                int bytesRead = inputStream.read(buffer);
                if (bytesRead == -1) {
                    break;
                }
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }
}
