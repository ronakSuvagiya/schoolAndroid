package com.apps.smartschoolmanagement.models;

public class OnlineMaterial {
    public String className;
    public String fileName;
    public String filepath;
    public String posteddate;
    public String subjectName;
    public  String DocId;

    public String getDocType() {
        return DocType;
    }

    public void setDocType(String docType) {
        DocType = docType;
    }

    public  String DocType;
    public String getDocId() {
        return DocId;
    }

    public void setDocId(String docId) {
        DocId = docId;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getFilepath() {
        return this.filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPosteddate() {
        return this.posteddate;
    }

    public void setPosteddate(String posteddate) {
        this.posteddate = posteddate;
    }
}
