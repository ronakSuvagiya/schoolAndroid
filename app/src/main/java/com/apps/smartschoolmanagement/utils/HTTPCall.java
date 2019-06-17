package com.apps.smartschoolmanagement.utils;

import java.util.HashMap;

public class HTTPCall {
    public static final int GET = 1;
    public static final int POST = 2;
    private int methodtype;
    private HashMap<String, String> params;
    private String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMethodtype() {
        return this.methodtype;
    }

    public void setMethodtype(int methodtype) {
        this.methodtype = methodtype;
    }

    public HashMap<String, String> getParams() {
        return this.params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }
}
