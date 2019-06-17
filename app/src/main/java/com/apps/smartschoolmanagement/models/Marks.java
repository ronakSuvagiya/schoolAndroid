package com.apps.smartschoolmanagement.models;

public class Marks {
    public String cgpa;
    public String class_name;
    public String exam_name;
    public String marks_id;
    public String obtained;
    public String result;
    public String student_name;
    public String subject_name;
    public String total;

    public String getMarks_id() {
        return this.marks_id;
    }

    public void setMarks_id(String marks_id) {
        this.marks_id = marks_id;
    }

    public String getStudent_name() {
        return this.student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getSubject_name() {
        return this.subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getClass_name() {
        return this.class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getExam_name() {
        return this.exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getObtained() {
        return this.obtained;
    }

    public void setObtained(String obtained) {
        this.obtained = obtained;
    }

    public String getTotal() {
        return this.total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCgpa() {
        return this.cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
