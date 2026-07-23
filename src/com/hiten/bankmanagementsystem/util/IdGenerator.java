package com.hiten.bankmanagementsystem.util;

import java.util.Random;

public class IdGenerator {
    int customerId;
    public int generateCustomerId(){
        Random random =new Random();
        customerId = 10000 + random.nextInt(19000);
        return customerId;
    }

    public int getCustomerId(){
        return customerId;
    }

    int accountId;
    public int generateAccountId(){
        Random random = new Random();
        accountId = 20000 + random.nextInt(30000);
        return accountId;
    }

    public int getAccountId(){
        return accountId;
    }

}
