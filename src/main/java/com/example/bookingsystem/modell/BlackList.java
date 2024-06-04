package com.example.bookingsystem.modell;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class BlackList {
    public int id;
    public String email;
    public String name;
    public String group;
    public String created;
    public boolean ok;

    public BlackList(int id, String email, String name, String group, String created, boolean ok) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.group = group;
        this.created = created;
        this.ok = ok;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
