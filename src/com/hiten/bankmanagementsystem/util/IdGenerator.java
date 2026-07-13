package com.hiten.bankmanagementsystem.util;

import java.util.Random;

public class IdGenerator {
    int customerId;
    public int generateId(){
        Random random =new Random();
        customerId = 10000 + random.nextInt(90000);
        return customerId;
    }

}
