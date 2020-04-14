package edu.csulb.cecs.lavilla;

public class User {
    public String fname, lname, email, userType;

    public User(){

    }

    public User(String fname, String lname, String email, String userType){
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.userType = userType;
    }
    public String getfname(){
        return fname;
    }
    public String getlname(){ return lname;}
    public String getEmail(){ return email;}
}