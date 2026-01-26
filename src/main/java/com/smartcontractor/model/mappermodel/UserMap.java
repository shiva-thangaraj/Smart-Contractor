package com.smartcontractor.model.mappermodel;

import jakarta.persistence.Id;

public class UserMap {

    private String userId;
    private String userName;
    private String userEmail;
    private String userPass;
    private String isUserActive;
    private String userCreatedAt;
    private String accessToken;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getIsUserActive() {
        return isUserActive;
    }

    public void setIsUserActive(String isUserActive) {
        this.isUserActive = isUserActive;
    }

    public String getUserCreatedAt() {
        return userCreatedAt;
    }

    public void setUserCreatedAt(String userCreatedAt) {
        this.userCreatedAt = userCreatedAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
