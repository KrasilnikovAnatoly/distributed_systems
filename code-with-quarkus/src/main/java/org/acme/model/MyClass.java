package org.acme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="myclasses")
public class MyClass {

    @Column
    @Id
    private String name;
    @Column
    private String surname;
    public MyClass(String name,String surname){
        this.name=name;
        this.surname=surname;
    }
    public MyClass(){}
    public void setName(String name){
        this.name=name;
    }
    public void setSurname(String surname){
        this.surname=surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
