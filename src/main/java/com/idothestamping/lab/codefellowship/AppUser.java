package com.idothestamping.lab.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;
import java.util.Date;

@Entity
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String username;
    String password;
    String firstname;
    String lastname;
    @DateTimeFormat(pattern="yyyy-mm-dd")
    Date dob;
    String bio;


    public AppUser() {}

    public AppUser(String username, String password, String firstname, String lastname, Date dob, String bio) {
//    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.bio = bio;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Long getId() { return id; }

    public String getFirstName() { return this.firstname; }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() { return this.lastname; }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public Date getDateofBirth() { return this.dob; }

    public void setDateofBirth(Date dob) {
        this.dob = dob;
    }

    public String getBio() { return this.bio; }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}