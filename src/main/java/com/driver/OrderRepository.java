package com.driver;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String,Order> orders;
    HashMap<String,DeliveryPartner> partner;
    HashMap<String, ArrayList<Order>> orderPartnerPair;
    HashMap<String,String> assignedorders;

    public OrderRepository() {

        orders=new HashMap<String,Order>();
        partner=new HashMap<String,DeliveryPartner>();
        orderPartnerPair=new HashMap<String, ArrayList<Order>>();
        assignedorders=new HashMap<String,String>();

    }

    public void addOrder(Order order) {

        orders.put(order.getId(),order);
    }

    public void addPartner(String partnerId) {
        partner.put(partnerId,new DeliveryPartner(partnerId));
        return;
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        ArrayList<Order> orders1=orderPartnerPair.getOrDefault(partnerId,new ArrayList<Order>());
        Order currentOrder=orders.get(orderId);
        assignedorders.put(orderId,partnerId);   //keep a track on the assigned orders
        orders1.add(currentOrder);
        orderPartnerPair.put(partnerId,orders1);
        int numberOfOrders= partner.get(partnerId).getNumberOfOrders();
        partner.get(partnerId).setNumberOfOrders(numberOfOrders+1);
        return;

    }

    public Order getOrderById(String orderId) {
        return orders.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partner.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return partner.get(partnerId).getNumberOfOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        int count=0;
        for(String s:orders.keySet())
        {
            if(!assignedorders.containsKey(s))
            {
                count++;
            }
        }
        return count;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        ArrayList<Order> ordersOfThePartner=orderPartnerPair.get(partnerId);
        List<String> result=new ArrayList<>();
        for(Order res:ordersOfThePartner)
        {
            result.add(res.getId());
        }
        return result;
    }

    public List<String> getAllOrders() {
        List<String> result=new ArrayList<>();
        for(String res:orders.keySet())
        {
            result.add(res);
        }

        return result;
    }

    public void deletePartnerById(String partnerId) {
       if(orderPartnerPair.containsKey(partnerId))
       {
           ArrayList<Order> ordersOfThePartner=orderPartnerPair.get(partnerId);

           for(Order order:ordersOfThePartner)
           {
               if(assignedorders.containsKey(order.getId()))
               {
                   assignedorders.remove(order.getId());
               }
           }
           orderPartnerPair.remove(partnerId);
       }
       partner.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        String partnerId="";
        if(assignedorders.containsKey(orderId))
        {
            partnerId=assignedorders.get(orderId);
            orderPartnerPair.get(partnerId).remove(orders.get(orderId));
        }
        orders.remove(orderId);
    }
}
