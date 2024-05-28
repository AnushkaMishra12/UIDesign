package com.example.uidesign;

public class SignUpDataItem {

    String name;
    String Email;
    String Gender;
    String Designation;
    String phoneNo;
    String Password;
    String image;


    public SignUpDataItem(String name, String email, String gender, String designation, String phoneNo, String password,String image) {
        this.name = name;
        this.Email = email;
        this.Gender = gender;
        this.Designation = designation;
        this.phoneNo = phoneNo;
        this.Password = password;
        this.image=image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
