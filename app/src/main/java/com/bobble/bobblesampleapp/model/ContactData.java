package com.bobble.bobblesampleapp.model;

import java.util.List;

/**
 * Created by varunjain on 6/26/17.
 */

public class ContactData {

    private String name;
    private List<String> mobileNumbers;
    private List<String> emails;
    private String birthday;

    public ContactData() {
    }

    public ContactData(String name, List<String> mobileNumbers, List<String> emails, String birthday) {
        this.name = name;
        this.mobileNumbers = mobileNumbers;
        this.emails = emails;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMobileNumbers() {
        return mobileNumbers;
    }

    public void setMobileNumbers(List<String> mobileNumbers) {
        this.mobileNumbers = mobileNumbers;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}