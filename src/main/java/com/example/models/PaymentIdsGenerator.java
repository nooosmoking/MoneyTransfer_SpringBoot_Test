package com.example.models;

public class PaymentIdsGenerator {
    private int lastId = 0;
    private static PaymentIdsGenerator obj;

    public static PaymentIdsGenerator getInstance(){
        if (obj == null) {
            obj = new PaymentIdsGenerator();
        }
        return obj;
    }

    public int generateId(){
        lastId++;
        return lastId;
    }

}
