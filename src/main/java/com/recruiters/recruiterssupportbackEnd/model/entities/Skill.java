package com.recruiters.recruiterssupportbackEnd.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author seam33
 */
@Entity
@Table(name = "skill")
public class Skill implements UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nameSK")
    private String name;
    @Column(name = "class")
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Skill{" + "id=" + id + ", name=" + name + ", type=" + type + '}';
    }

    public enum TYPE {
        HARD,
        SOFT
    }

    public Skill(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Skill() {
    }

    
    
}
