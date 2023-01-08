/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.Customer;
import models.CustomerType;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joanl
 */
public class CustomerController implements Initializable {

    @FXML
    private TableView<Customer> tblCustomers;
    @FXML
    private TableColumn colFirstName;
    @FXML
    private TableColumn colLastName;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colAdress;
    @FXML
    private TableColumn colIdCard;
    @FXML
    private TableColumn colType;
    @FXML
    private TableColumn colFee;
    @FXML
    private TableColumn colDiscount;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private RadioButton btnRegular;
    @FXML
    private RadioButton btnPremium;
    @FXML
    private RadioButton btnNothing;

    ToggleGroup tg;
    ObservableList<Customer> customers;
    ObservableList<Customer> filterCustomers;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tg = new ToggleGroup();
        btnRegular.setToggleGroup(tg);
        btnPremium.setToggleGroup(tg);
        btnNothing.setToggleGroup(tg);
        btnNothing.setSelected(true);

        //Inicializar el ObservableList o lo que sea que usemos
        customers = FXCollections.observableArrayList();

        //Inicializar la Tabla
        this.tblCustomers.setItems(customers);

        //Inicializar la columna poniendo el nombre del atributo del modelo
        this.colFirstName.setCellValueFactory(new PropertyValueFactory("firstname"));
        this.colLastName.setCellValueFactory(new PropertyValueFactory("lastname"));
        this.colEmail.setCellValueFactory(new PropertyValueFactory("Email"));
        this.colAdress.setCellValueFactory(new PropertyValueFactory("Address"));
        this.colIdCard.setCellValueFactory(new PropertyValueFactory("idCardNumber"));
        this.colType.setCellValueFactory(new PropertyValueFactory("type"));
        this.colFee.setCellValueFactory(new PropertyValueFactory("customerFee"));
        this.colDiscount.setCellValueFactory(new PropertyValueFactory("customerDiscount"));

        //Seteando Filtro
        filterCustomers = FXCollections.observableArrayList();
    }

    @FXML
    private void select(MouseEvent event) {
    }

    @FXML
    public void addCustomer(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CustomerDialogView.fxml"));
            Parent root = loader.load();

            CustomerDialogController controller = loader.getController();
            controller.inicializeVariables(customers);

            Scene scene = new Scene(root);

            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            //Tras esperar a cerrar la otra ventana...
            Customer c = controller.getCustomer();
            if (c != null) {
                this.customers.add(c);
                //Para que aparezca en el filtro
                if (c.getType() == CustomerType.REGULAR && btnRegular.isSelected()
                        || c.getType() == CustomerType.PREMIUM && btnPremium.isSelected()) {
                    this.filterCustomers.add(c);
                }

                this.tblCustomers.refresh();
            }

        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void updateCustomer(ActionEvent event) {
        Customer c = this.tblCustomers.getSelectionModel().getSelectedItem();

        if (c == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se ha seleccionado ningun Customer");
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CustomerDialogView.fxml"));
                Parent root = loader.load();

                CustomerDialogController controller = loader.getController();
                controller.inicializeVariables(customers, c);

                Scene scene = new Scene(root);

                Stage stage = new Stage();

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();

                //Tras esperar a cerrar la otra ventana...
                Customer cSeleccionado = controller.getCustomer();
                if (cSeleccionado != null) {
                    //Filtro
                    if (cSeleccionado.getType() != CustomerType.REGULAR && this.btnRegular.isSelected()
                            || cSeleccionado.getType() != CustomerType.PREMIUM && this.btnPremium.isSelected()) {
                        this.filterCustomers.remove(cSeleccionado);
                    }
                    
                    this.tblCustomers.refresh();
                }

            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void deleteCustomer(ActionEvent event) {
        Customer c = this.tblCustomers.getSelectionModel().getSelectedItem();

        if (c == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se ha seleccionado ningun Customer");
            alert.showAndWait();
        } else {

            this.customers.remove(c);
            this.tblCustomers.refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("Se ha Eliminado correctamente");
            alert.showAndWait();
        }
    }

    @FXML
    private void filter(ActionEvent event) {
        if (this.btnNothing.isSelected()) {
            this.tblCustomers.setItems(customers);
        } else {
            this.filterCustomers.clear();

            if (this.btnRegular.isSelected()) {
                System.out.println("REGULAR");
                for (Customer c : this.customers) {
                    if (c.getType() == CustomerType.REGULAR) {
                        this.filterCustomers.add(c);
                    }
                }
            } else if (this.btnPremium.isSelected()) {
                System.out.println("PREMIUM");
                for (Customer c : this.customers) {
                    if (c.getType() == CustomerType.PREMIUM) {
                        this.filterCustomers.add(c);
                    }
                }
            }
            this.tblCustomers.setItems(filterCustomers);
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

}
