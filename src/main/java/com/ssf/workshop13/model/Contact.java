package com.ssf.workshop13.model;

import jakarta.json.JsonObject;

import java.util.List;
import java.util.Random;

public class Contact {
    private String id;
    private String name;
    private String email;
    private int phoneNumber;

    public Contact() {
        this.id = this.generateId();
    }

    public Contact(String name, String email, int phoneNumber) {
        this.id = this.generateId();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Contact(String id, String name, String email, int phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Contact(String id, List<Contact> contactList) {
        this.id = id;
        this.name = String.valueOf(contactList.get(0));
        this.email = String.valueOf(contactList.get(1));
        this.phoneNumber = Integer.parseInt(String.valueOf(contactList.get(2)));
    }

    private synchronized String generateId() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while (sb.length() < 8) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 8);
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

    public int getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public static Contact create(JsonObject object) {
        final Contact contact = new Contact();
        contact.setId(object.getString("id"));
        contact.setName(object.getString("name"));
        contact.setEmail(object.getString("email"));
        contact.setPhoneNumber(Integer.parseInt(object.getString("phoneNumber")));
        return contact;
    }
}
