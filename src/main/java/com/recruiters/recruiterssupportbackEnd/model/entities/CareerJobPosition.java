package com.recruiters.recruiterssupportbackEnd.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author JorgeDíaz
 */
@Entity
@Table(name = "career_job_position")
public class CareerJobPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Column(name = "id_career")
    private String careerId;
    @Column(name = "id_job_position")
    private int jobPositionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCareerId() {
        return careerId;
    }

    public void setCareerId(String careerId) {
        this.careerId = careerId;
    }

    public int getJobPositionId() {
        return jobPositionId;
    }

    public void setJobPositionId(int jobPositionId) {
        this.jobPositionId = jobPositionId;
    }

}