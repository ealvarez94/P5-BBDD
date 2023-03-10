package controllers;

import java.util.ArrayList;

import dao.DAOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Order;
import models.Data;
import views.OrdersView;

public class OrdersController {
  private Data dataStore;
  private OrdersView ordersView;

  public OrdersController(Data dataStore) {
    this.dataStore = dataStore;
    this.ordersView = new OrdersView();
  }

  // List all
  public void list() {
    final ArrayList<Order> orders = dataStore.getOrders();
    this.ordersView.renderAll(orders);
  }

  // List Sent Orders
  public void listSentOrders(){
    ArrayList<Order> orders = dataStore.getSentOrders();
    this.ordersView.renderAll(orders);
  }

  // List Pending Orders
  public void listPendingOrders(){
    ArrayList<Order> orders = dataStore.getPendingOrders();
    this.ordersView.renderAll(orders);
  }


  // Create
  public void create(Order order) {
    // Add new order to data store
    try {
      this.dataStore.addOrder(order);
      this.ordersView.render(order);
    } catch (Exception e) {
      // print error
      this.ordersView.error(e);
    }

  }

  // Delete
  public void delete(Order order) throws DAOException {
    dataStore.deleteOrder(order);
  }

  // Return Order from ID/SKU
  public Order returnOrder(int id){
    Order order = null;
    for (int i = 0; i < this.dataStore.lenghtOrders(); i++){
      if (id == this.dataStore.getOrders().get(i).getorderID()){
        order = this.dataStore.getOrders().get(i);
      }
    }
    return order;
  }

}
