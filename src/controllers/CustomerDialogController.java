/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.Customer;
import models.CustomerType;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joanl
 */
public class CustomerDialogController implements Initializable {

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtIdCardNumber;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private ComboBox<CustomerType> cboxCustomerType;
    
    
    private Customer customer;
    private ObservableList<Customer> customers;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       cboxCustomerType.getItems().setAll(CustomerType.values());
    }    
    
    public void inicializeVariables(ObservableList<Customer> cs){
        this.customers = cs;
    }
    public void inicializeVariables(ObservableList<Customer> cs, Customer c){
        this.customers = cs;
        this.customer = c;
        
        txtFirstName.setText(c.getFirstname());
        txtLastName.setText(c.getLastname());
        txtEmail.setText(c.getEmail());
        txtAddress.setText(c.getAddress());
        txtIdCardNumber.setText(c.getIdCardNumber());
        cboxCustomerType.setValue(c.getType());
    }

    @FXML
    private void cancelar(ActionEvent event) {
        this.customer = null;
        Stage stage = (Stage) this.btnCancelar.getScene().getWindow();
        stage.close();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @FXML
    private void guardar(ActionEvent event) {
        String name = this.txtFirstName.getText();
        String lastName = this.txtLastName.getText();
        String email = this.txtEmail.getText();
        String address = this.txtAddress.getText();
        String idCardNumber = this.txtIdCardNumber.getText();
        CustomerType type = this.cboxCustomerType.getValue();
        
        Customer c = new Customer(name, lastName, email, address, idCardNumber, type);
        
        if(!customers.contains(c)){
            //Modificar
            if(this.customer != null){
                this.customer.setFirstname(name);
                this.customer.setLastname(lastName);
                this.customer.setEmail(email);
                this.customer.setEmail(email);
                this.customer.setAddress(address);
                this.customer.setIdCardNumber(idCardNumber);
                this.customer.setType(type);
                this.customer.setCustomerFee(this.customer.calculateSippingFee());
                this.customer.setCustomerDiscount(this.customer.calculateDiscount());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Información");
                alert.setContentText("Se ha modificado correctamente");
                alert.showAndWait();
            }
            else{
                this.customer = c;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Información");
                alert.setContentText("Se ha añadido correctamente");
                alert.showAndWait();
            }
            Stage stage = (Stage) this.btnCancelar.getScene().getWindow();
            stage.close();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Customer already exists");
            alert.showAndWait();
        }
    }
    
    
}
