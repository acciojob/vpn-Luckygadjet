package com.driver.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "serviceprovider")
public class ServiceProvider {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;


    @ManyToOne
    @JoinColumn
    Admin admin;

    @OneToMany(mappedBy = "serviceProvider",cascade = CascadeType.ALL)
    List<Connection> connectionList = new ArrayList<>();


    @ManyToMany(mappedBy = "serviceProvider",cascade = CascadeType.ALL)
    List<User> userList = new ArrayList<>();

    @OneToMany(mappedBy = "serviceProvider",cascade = CascadeType.ALL)
    List<Country> countryList = new ArrayList<>();

    public ServiceProvider(){}

    public ServiceProvider(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    public List<User> getUsers() {
        return userList;
    }

    public void setUsers(List<User> userList) {
        this.userList = userList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }
}
