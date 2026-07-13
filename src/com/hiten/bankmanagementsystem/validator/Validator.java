package com.hiten.bankmanagementsystem.validator;

public class Validator {

    public boolean validateEmail(String email){
        return email.endsWith("gmail.com");
    }
}
