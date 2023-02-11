package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    String time[];

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        String [] time=deliveryTime.split(":");
        this.deliveryTime=(Integer.parseInt(time[0])*60)+Integer.parseInt(time[1]);
        System.out.println(this.deliveryTime);

        //deliveryTime  = HH*60 + MM
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
