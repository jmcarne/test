package com.scmspain.middleware.domain.model;

/**
 * Created by josep.carne on 05/02/2017.
 */
public class Resource {
    private final String code;
    private final String name;
    private final String surname;
    private final String password;
    private final String role;

    public Resource() {
        this.code = null;
        this.name = null;
        this.surname = null;
        this.password = null;
        this.role = null;
    }

    public Resource(String code, String name, String surname, String password, String role) {
        this.code = code;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role = role;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

}
