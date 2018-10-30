package pl.coderslab.model;

import java.util.Date;

public class SolutionAW {

    private int id;
    private String description;
    private Date created;
    private Date updated;

    public SolutionAW(){}
    public SolutionAW(String description) {
        this.id = 0;
        this.description = description;
        this.created = new Date();
        this.updated = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
