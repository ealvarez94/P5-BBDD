/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.DAOException;
import dao.HibernateDAO.HibernateCustomerDao;
import dao.HibernateDAO.HibernateProductDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Customer;
import models.Order;
import models.Product;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author joanl
 */
public class OrderDialogController implements Initializable {

    @FXML
    private TextArea txtDescription;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField colProductID;
    @FXML
    private TextField custEmail;
    @FXML
    private TextField proQuant;


    private Order order;
    private ObservableList<Order> orders;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void inicializeVariables(ObservableList<Order> ps) {
        this.orders = ps;
    }

    public void inicializeVariables(ObservableList<Order> ps, Order p) {
        this.orders = ps;
        this.order = p;

        colProductID.setText(p.getProduct().getproductID());
        custEmail.setText(p.getCustomer().getEmail());
        proQuant.setText(p.getproductQuantity() + "");

    }

    @FXML
    private void guardar(ActionEvent event) {
        Order p = null;
        boolean todoOk = false;

        Product producto = null;
        Customer cliente = null;
        int productQuantity = 0;
        String email = null;
        String productoId = null;
        
        try {
            HibernateProductDao dao = new HibernateProductDao();
            productoId = this.colProductID.getText();
            producto = dao.readOne(productoId);

            HibernateCustomerDao daoCustomer = new HibernateCustomerDao();
            email = this.custEmail.getText();
            cliente = daoCustomer.readOne(email);

            productQuantity = Integer.parseInt(this.proQuant.getText());

            p = new Order(producto, cliente, productQuantity);

            todoOk = true;
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Introduzca datos vcalidos." + ex.getMessage());
            alert.showAndWait();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        if (todoOk) {
            if (!orders.contains(p)) {
                //Modificar
                if (this.order != null) {

                    order.setProduct(producto);
                    order.setCustomer(cliente);
                    order.setproductQuantity(productQuantity);



                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Información");
                    alert.setContentText("Se ha modificado correctamente");
                    alert.showAndWait();

                } else {
                    //Insertar
                    this.order = p;

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Información");
                    alert.setContentText("Se ha añadido correctamente");
                    alert.showAndWait();

                }

                Stage stage = (Stage) this.btnCancelar.getScene().getWindow();
                stage.close();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Customer already exists");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        this.order = null;
        Stage stage = (Stage) this.btnCancelar.getScene().getWindow();
        stage.close();
    }

    public Order getOrder() {
        return order;
    }

    public void setProduct(Product product) {
        this.order = order;
    }

}
