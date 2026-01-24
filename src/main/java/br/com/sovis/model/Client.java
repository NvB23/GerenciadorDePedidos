package br.com.sovis.model;

import java.time.LocalDateTime;

public class Client {
    private Long id;
    private final String name;
    private String email;
    private final String phone;
    private final String dateRegister = LocalDateTime.now().toLocalDate().toString();;

    public Client(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Client(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDateRegister() {
        return dateRegister;
    }
}
