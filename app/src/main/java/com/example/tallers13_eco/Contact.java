package com.example.tallers13_eco;

public class Contact {
    String number;
    String id;
    String name;
    String userId;
    public Contact(String id,String userId,String name,String  number){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.number = number;

    }

    public Contact(){

    }

    public String getNumber() {
        return number;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setNumber(String number) {
        this.number = number;
    }

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
}
