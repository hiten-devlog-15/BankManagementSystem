package com.hiten.bankmanagementsystem.validator;

import com.hiten.bankmanagementsystem.enums.AccountStatus;
import com.hiten.bankmanagementsystem.exception.InvalidAmountException;
import com.hiten.bankmanagementsystem.exception.InvalidEmailException;
import com.hiten.bankmanagementsystem.exception.InvalidPhoneNumberException;
import com.hiten.bankmanagementsystem.model.Account;


public class Validator {


    public void validateEmail(String email){
        if(!email.endsWith("@gmail.com")){
            throw new InvalidEmailException();
        }
    }

    public void validatePhoneNumber(String phoneNumber){
         if(!(phoneNumber.length() == 10)){
             throw new InvalidPhoneNumberException();
         }
    }

    public void validateAmount(int amount){
        if(!(amount>0)){
            throw new InvalidAmountException();
        }
    }

    public boolean isAccountActive  (Account account){
        if(account.getAccountStatus() != AccountStatus.CLOSED){
            return true;
        }
        return false;
    }

}
