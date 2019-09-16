package com.recruiters.recruiterssupportbackEnd.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jhanuar sanchez
 */
@Entity
@Table(name = "PostulantRv")
public class PostulantRv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "state")
    private int state;
    @Column(name = "id_postulant")
    private String idPostulant;
    @Column(name = "id_rv")
    private int idRv;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getIdPostulant() {
        return idPostulant;
    }

    public void setIdPostulant(String idPostulant) {
        this.idPostulant = idPostulant;
    }

    public int getIdRv() {
        return idRv;
    }

    public void setIdRv(int idRv) {
        this.idRv = idRv;
    }

 
}