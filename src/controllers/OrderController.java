/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Order;
import models.Product;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author joanl
 */
public class OrderController implements Initializable {

    @FXML
    private TableView<Order> tblOrders;
    @FXML
    private TableColumn colOrderID;
    @FXML
    private TableColumn colProductID;
    @FXML
    private TableColumn custEmail;
    @FXML
    private TableColumn proQuant;
    @FXML
    private TableColumn subTotal;
    @FXML
    private TableColumn creationDate;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    ObservableList<Order> orders;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inicializar el ObservableList o lo que sea que usemos
        orders = FXCollections.observableArrayList();

        //Inicializar la tabla
        this.tblOrders.setItems(orders);

        //Inicializar la columna poniendo el nombre del atributo del modelo
        this.colOrderID.setCellValueFactory(new PropertyValueFactory("orderID"));
        this.colProductID.setCellValueFactory(new PropertyValueFactory("productID"));
        this.custEmail.setCellValueFactory(new PropertyValueFactory("customerEmail"));
        this.proQuant.setCellValueFactory(new PropertyValueFactory("productQuantity"));
        this.subTotal.setCellValueFactory(new PropertyValueFactory("subtotal"));
        this.creationDate.setCellValueFactory(new PropertyValueFactory("creationDateTime"));



        //Seteando Filtro
    }

    @FXML
    public void addOrder(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OrderDialogView.fxml"));
            Parent root = loader.load();

            OrderDialogController controller = loader.getController();
            controller.inicializeVariables(orders);

            Scene scene = new Scene(root);

            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            //Tras esperar a cerrar la otra ventana...
            Order p = controller.getOrder();
            if (p != null) {
                this.orders.add(p);
                //Para que aparezca en el filtro
                
                this.tblOrders.refresh();

            }

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void updateOrder(ActionEvent event) {
        Order p = this.tblOrders.getSelectionModel().getSelectedItem();

        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se ha seleccionado ningun Pedido");
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OrderDialogView.fxml"));
                Parent root = loader.load();

                OrderDialogController controller = loader.getController();
                controller.inicializeVariables(orders, p);

                Scene scene = new Scene(root);

                Stage stage = new Stage();

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();

                //Tras esperar a cerrar la otra ventana...
                Order pSeleccionado = controller.getOrder();
                if (pSeleccionado != null) {
                    
                    //Para que aparezca en el filtro

                    this.tblOrders.refresh();

                }

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void deleteOrder(ActionEvent event) {
        Order p = this.tblOrders.getSelectionModel().getSelectedItem();

        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se ha seleccionado ningun Pedido");
            alert.showAndWait();
        } else {

            this.orders.remove(p);
            this.tblOrders.refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("Se ha Eliminado correctamente");
            alert.showAndWait();
        }
    }

    public void closeWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
            Parent root = loader.load();

            MainController controller = loader.getController();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btnAdd.getScene().getWindow();
            myStage.close();
        } catch (Exception e) {
        }
    }

    @FXML
    private void select(MouseEvent event) {
        Order p = this.tblOrders.getSelectionModel().getSelectedItem();

        if(p != null){
        }
    }
}
