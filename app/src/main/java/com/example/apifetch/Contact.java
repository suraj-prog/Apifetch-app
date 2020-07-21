package com.example.apifetch;

public class Contact {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String message;

    public Contact( int id,String name,String email,String phone,String message)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getMessage() {
        return message;
    }
}
