package edu.iust.advancejava.jdbc.simpleorm;

import edu.iust.advancejava.jdbc.simpleorm.IEntity;

import java.time.LocalDate;

public class Car implements IEntity {
    private int id;
    private String name;
    private LocalDate dateOfRelease;
    private int maximumSpeed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(LocalDate dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    public int getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(int maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
