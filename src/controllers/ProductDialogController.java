/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joanl
 */
public class ProductDialogController implements Initializable {

    @FXML
    private TextArea txtDescription;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtShippingFee;
    @FXML
    private TextField txtHandlingTime;

    private Product product;
    private ObservableList<Product> products;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void inicializeVariables(ObservableList<Product> ps) {
        this.products = ps;
    }

    public void inicializeVariables(ObservableList<Product> ps, Product p) {
        this.products = ps;
        this.product = p;

        txtID.setText(p.getproductID());
        txtName.setText(p.getProductName());
        txtPrice.setText(p.getPrice() + "");
        txtDescription.setText(p.getDescription());
        txtShippingFee.setText(p.getShippingFee() + "");
        txtHandlingTime.setText(p.gethandlingTime() + "");
    }

    @FXML
    private void guardar(ActionEvent event) {
        Product p = null;
        boolean todoOk = false;
        String id = null;
        String name = null;
        double price = 0.0d;
        double fee = 0.0d;
        int time = 0;
        String description = "";
        
        try {
            id = this.txtID.getText();
            name = this.txtName.getText();
            price = Double.parseDouble(this.txtPrice.getText());
            fee = Double.parseDouble(this.txtShippingFee.getText());
            time = Integer.parseInt(this.txtHandlingTime.getText());
            description = this.txtDescription.getText();
            
            p = new Product(id, name, description, price, fee, time);
            
            todoOk = true;
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Introduzca datos vcalidos." + ex.getMessage());
            alert.showAndWait();
        }

        if (todoOk) {
            if (!products.contains(p)) {
                //Modificar
                if (this.product != null) {
                    product.setproductID(id);
                    product.setProductName(name);
                    product.setPrice(price);
                    product.setShippingFee(fee);
                    product.sethandlingTime(time);
                    product.setDescription(description);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Información");
                    alert.setContentText("Se ha modificado correctamente");
                    alert.showAndWait();

                } else {
                    //Insertar
                    this.product = p;

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
        this.product = null;
        Stage stage = (Stage) this.btnCancelar.getScene().getWindow();
        stage.close();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
