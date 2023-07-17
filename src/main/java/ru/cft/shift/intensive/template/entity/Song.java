package ru.cft.shift.intensive.template.entity;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("song")
public class Song {
    private int id;
    private String name;
    private String owner;
    private String description;
    private int duration;

    public Song() {
    }

    public Song(int id, String name, String description, int duration, String owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.owner = owner;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
