package org.acme;

import io.smallrye.common.constraint.NotNull;

public class MyClass {

    @NotNull
    private String name;
    @NotNull
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
