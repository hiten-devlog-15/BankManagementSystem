package com.hiten.bankmanagementsystem.validator;

import com.hiten.bankmanagementsystem.enums.AccountStatus;
import com.hiten.bankmanagementsystem.model.Account;


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

    public boolean isAccountActive  (Account account){
        if(account.getAccountStatus() != AccountStatus.CLOSED){
            return true;
        }
        return false;
    }

}
