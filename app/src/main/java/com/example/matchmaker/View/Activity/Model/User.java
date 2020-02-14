package com.example.matchmaker.View.Activity.Model;

public class User {
    private String userId;
    private String userName;
    private String userEmail;
    private String userDateOfBirth;
    private String userContact;
    private String userGender;

    public User() {

    }

    public User(String userName, String userEmail, String userDateOfBirth, String userContact, String userGender) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userDateOfBirth = userDateOfBirth;
        this.userContact = userContact;
        this.userGender = userGender;
    }

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public User(String userId, String userName, String userEmail, String userDateOfBirth, String userContact, String userGender) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userDateOfBirth = userDateOfBirth;
        this.userContact = userContact;
        this.userGender = userGender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserDateOfBirth() {
        return userDateOfBirth;
    }

    public void setUserDateOfBirth(String userDateOfBirth) {
        this.userDateOfBirth = userDateOfBirth;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userDateOfBirth='" + userDateOfBirth + '\'' +
                ", userContact='" + userContact + '\'' +
                ", userGender='" + userGender + '\'' +
                '}';
    }
}
