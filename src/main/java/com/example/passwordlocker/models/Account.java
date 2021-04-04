package com.example.passwordlocker.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;

    private String username;
    private String password;
    private String description;
    private String type;
    @CreationTimestamp
    private Timestamp createdOn;
    private long createdBy;
    @UpdateTimestamp
    private Timestamp lastUpdate;
    private long lastUpdatedBy;
/*
    public Account(
            String username,
            String password,
            String description,
            String type,
            Timestamp createdOn,
            long createdBy,
            Timestamp lastUpdate,
            long lastUpdatedBy) {
        this.username = username;
        this.password = password;
        this.description = description;
        this.type = type;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
*/
    //protected Account() {}

    @Override
    public String toString() {
        return "Account{" +
                "id=" + accountId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", createdOn=" + createdOn +
                ", createdBy=" + createdBy +
                ", lastUpdate=" + lastUpdate +
                ", lastUpdatedBy=" + lastUpdatedBy +
                '}';
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
