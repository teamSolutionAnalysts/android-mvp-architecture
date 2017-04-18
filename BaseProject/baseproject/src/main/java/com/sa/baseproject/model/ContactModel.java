package com.sa.baseproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mavya.soni on 01/04/17.
 */

public class ContactModel {

    @SerializedName("contacts")
    private ArrayList<Contact> contacts = null;
    private Contact contact = new Contact();

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }


    public Contact getContact() {
        return contact;
    }

    public class Contact {

        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("email")
        private String email;
        @SerializedName("address")
        private String address;
        @SerializedName("gender")
        private String gender;
        @SerializedName("phone")
        private Phone phone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Phone getPhone() {
            return phone;
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

    }


    public class Phone {

        @SerializedName("mobile")
        private String mobile;
        @SerializedName("home")
        private String home;
        @SerializedName("office")
        private String office;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getOffice() {
            return office;
        }

        public void setOffice(String office) {
            this.office = office;
        }

    }


}
