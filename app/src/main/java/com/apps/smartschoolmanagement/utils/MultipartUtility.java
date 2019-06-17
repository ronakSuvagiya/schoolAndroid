package com.apps.smartschoolmanagement.utils;

import android.util.Log;
import com.google.common.net.HttpHeaders;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MultipartUtility {
    private static final String LINE_FEED = "\r\n";
    private final String boundary = ("===" + System.currentTimeMillis() + "===");
    private String charset;
    private HttpURLConnection httpConn;
    private OutputStream outputStream;
    private PrintWriter writer;

    public MultipartUtility(String requestURL, String charset) throws IOException {
        this.charset = charset;
        URL url = new URL(requestURL);
        Log.e("URL", "URL : " + requestURL.toString());
        this.httpConn = (HttpURLConnection) url.openConnection();
        this.httpConn.setUseCaches(false);
        this.httpConn.setDoOutput(true);
        this.httpConn.setDoInput(true);
        this.httpConn.setRequestProperty(HttpHeaders.CONTENT_TYPE, "multipart/form-data; boundary=" + this.boundary);
        this.httpConn.setRequestProperty(HttpHeaders.USER_AGENT, "CodeJava Agent");
        this.httpConn.setRequestProperty("Test", "Bonjour");
        this.httpConn.setRequestProperty(HttpHeaders.ACCEPT_ENCODING, "");
        this.outputStream = this.httpConn.getOutputStream();
        this.writer = new PrintWriter(new OutputStreamWriter(this.outputStream, charset), true);
    }

    public void addFormField(String name, String value) {
        this.writer.append("--" + this.boundary).append(LINE_FEED);
        this.writer.append("Content-Disposition: form-data; name=\"" + name + "\"").append(LINE_FEED);
        this.writer.append("Content-Type: text/plain; charset=" + this.charset).append(LINE_FEED);
        this.writer.append(LINE_FEED);
        this.writer.append(value).append(LINE_FEED);
        this.writer.flush();
    }

    public void addFilePart(String fieldName, File uploadFile) throws IOException {
        String fileName = uploadFile.getName();
        this.writer.append("--" + this.boundary).append(LINE_FEED);
        this.writer.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
        this.writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
        this.writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        this.writer.append(LINE_FEED);
        this.writer.flush();
        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        while (true) {
            int bytesRead = inputStream.read(buffer);
            if (bytesRead != -1) {
                this.outputStream.write(buffer, 0, bytesRead);
            } else {
                this.outputStream.flush();
                inputStream.close();
                this.writer.append(LINE_FEED);
                this.writer.flush();
                return;
            }
        }
    }

    public void addHeaderField(String name, String value) {
        this.writer.append(name + ": " + value).append(LINE_FEED);
        this.writer.flush();
    }

    public String finish() throws IOException {
        StringBuffer response = new StringBuffer();
        this.writer.append(LINE_FEED).flush();
        this.writer.append("--" + this.boundary + "--").append(LINE_FEED);
        this.writer.close();
        int status = this.httpConn.getResponseCode();
        if (status == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.httpConn.getInputStream()));
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    response.append(line);
                } else {
                    reader.close();
                    this.httpConn.disconnect();
                    return response.toString();
                }
            }
        }
        throw new IOException("Server returned non-OK status: " + status);
    }
}
