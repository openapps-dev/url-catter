package com.produo.urlcatter.link;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "link")
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Lob
    @Column
    private String original;
    private String code;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Date lastUse;

    public LinkEntity() {
        this.lastUse = new Date();
    }

    public LinkEntity(String original, String code) {
        this.lastUse = new Date();
        this.original = original;
        this.code = code;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginal() {
        return this.original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getLastUse() {
        return this.lastUse;
    }

    public void setLastUse(Date lastUse) {
        this.lastUse = lastUse;
    }
}
