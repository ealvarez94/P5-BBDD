/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Iterator;

/**
 *
 * @author irene
 */
public class OrderList extends List<Order> {
    @Override
    public void add(Order order) throws Exception {
        if (contains(order.getorderID())) {
            throw new Exception("El pedido ya existe");
        } else
            this.list.add(order);
    }

    public boolean contains(int i) {
        for (Iterator<Order> iter = this.list.iterator(); iter.hasNext();) {
            Order o = (Order) iter.next();
            if (o.getorderID()==(i)) {
                return true;
            }
        }
        return false;
    }
}
