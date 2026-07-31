package com.hiten.bankmanagementsystem.validator;

import com.hiten.bankmanagementsystem.enums.AccountStatus;
import com.hiten.bankmanagementsystem.enums.AccountType;
import com.hiten.bankmanagementsystem.exception.*;
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

    public void isAccountActive  (Account account){
        if(account.getAccountStatus() == AccountStatus.CLOSED){
            throw new AccountClosedException();
        }
    }

    public void validateInitialDeposit(AccountType accountType, int initialDeposit) {
        if (accountType == AccountType.SAVINGS && initialDeposit < 2000) {
            throw new InvalidAmountException();
        }
        if (accountType == AccountType.CURRENT && initialDeposit < 5000) {
            throw new InvalidAmountException();
        }
    }

    public void checkSameAccount(Account senderAccount, Account receiverAccount){
        if(senderAccount == receiverAccount){
            throw new SameAccountTransferException();
        }
    }

    public void checkBalanceIsZero(Account account){
        if(account.getCurrentBalance() > 0){
            throw new BalanceNotZeroException();
        }
    }
}
