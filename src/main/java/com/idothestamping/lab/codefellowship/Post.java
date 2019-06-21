package com.idothestamping.lab.codefellowship;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true)
    String body;
    Timestamp ts;

    @ManyToOne
    AppUser creator;

    public Post() {}

    public Post(String body) {
        this.body = body;
        this.ts = new Timestamp(System.currentTimeMillis());
    }

    public long getId() {
        return id;
    }

    public AppUser getCreator() {
        return creator;
    }

    public String getBody() {
        return body;
    }

    public Timestamp getTs() {
        return ts;
    }
}