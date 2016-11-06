package com.heapot.qianxun.bean;

/**
 * Created by 15859 on 2016/11/1.
 */
public class AddEducationSchool {

    /**
     * school : 郑州轻工业学院
     * faculty : 软件学院
     * profession : 软件测试
     * startYear : 2005
     * endYear : 2009
     */

    private String school;
    private String faculty;
    private String profession;
    private int startYear;
    private int endYear;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
}
