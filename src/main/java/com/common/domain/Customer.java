package com.common.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Kirill Stoianov on 15/09/17.
 */
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GenericGenerator(name="generator", strategy="identity")
    @GeneratedValue(generator="generator")
    private int id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String phone;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
