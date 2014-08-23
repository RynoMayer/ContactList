package com.example.ryno.contactlist;


/**
 * Created by Ryno on 20/08/2014.
 */
public class Contact {
     private String Firstname = null;
    private String EmailAddr =null;
    private String HomeAddr = null;
    private String cellNum =null;
    private String surname = null;

    public Contact(){}

    public Contact(String firstname, String emailAddr, String homeAddr, String cellNum, String surname) {
        this.Firstname = firstname;
        this.EmailAddr = emailAddr;
        this.HomeAddr = homeAddr;
        this.cellNum = cellNum;
        this.surname = surname;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getEmailAddr() {
        return EmailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        EmailAddr = emailAddr;
    }

    public String getHomeAddr() {
        return HomeAddr;
    }

    public void setHomeAddr(String homeAddr) {
        HomeAddr = homeAddr;
    }

    public String getCellNum() {
        return cellNum;
    }

    public void setCellNum(String cellNum) {
        this.cellNum = cellNum;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
