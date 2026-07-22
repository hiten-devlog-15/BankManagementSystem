package com.hiten.bankmanagementsystem.validator;

public class Validator {

    public boolean validateEmail(String email){
        return email.endsWith("@gmail.com");
    }

    public boolean validatePhoneNumber(String phoneNumber){
        return phoneNumber.length() == 10;
    }

    public boolean validateAmount(int amount){
        if(amount>0){
            return true;
        }
        return false;
    }

}
