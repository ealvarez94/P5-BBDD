/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.Product;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joanl
 */
public class ProductController implements Initializable {

    @FXML
    private TableView<Product> tblProducts;
    @FXML
    private TableColumn colID;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colPrice;
    @FXML
    private TableColumn colFee;
    @FXML
    private TableColumn colHandlingTime;
    @FXML
    private TextArea txtDescription;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    ObservableList<Product> products;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inicializar el ObservableList o lo que sea que usemos
        products = FXCollections.observableArrayList();

        //Inicializar la tabla
        this.tblProducts.setItems(products);

        //Inicializar la columna poniendo el nombre del atributo del modelo
        this.colID.setCellValueFactory(new PropertyValueFactory("productID"));
        this.colName.setCellValueFactory(new PropertyValueFactory("productName"));
        this.colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        this.colFee.setCellValueFactory(new PropertyValueFactory("shippingFee"));
        this.colHandlingTime.setCellValueFactory(new PropertyValueFactory("handlingTime"));

        //Seteando Filtro
    }

    @FXML
    public void addProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ProductDialogView.fxml"));
            Parent root = loader.load();

            ProductDialogController controller = loader.getController();
            controller.inicializeVariables(products);

            Scene scene = new Scene(root);

            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            //Tras esperar a cerrar la otra ventana...
            Product p = controller.getProduct();
            if (p != null) {
                this.products.add(p);
                //Para que aparezca en el filtro
                
                this.tblProducts.refresh();

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
    private void updateProduct(ActionEvent event) {
        Product p = this.tblProducts.getSelectionModel().getSelectedItem();

        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se ha seleccionado ningun Product");
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ProductDialogView.fxml"));
                Parent root = loader.load();

                ProductDialogController controller = loader.getController();
                controller.inicializeVariables(products, p);

                Scene scene = new Scene(root);

                Stage stage = new Stage();

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();

                //Tras esperar a cerrar la otra ventana...
                Product pSeleccionado = controller.getProduct();
                if (pSeleccionado != null) {
                    
                    //Para que aparezca en el filtro

                    this.tblProducts.refresh();

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
    private void deleteCustomer(ActionEvent event) {
        Product p = this.tblProducts.getSelectionModel().getSelectedItem();

        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se ha seleccionado ningun Product");
            alert.showAndWait();
        } else {

            this.products.remove(p);
            this.tblProducts.refresh();

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
        Product p = this.tblProducts.getSelectionModel().getSelectedItem();
        
        if(p != null){
            txtDescription.setText(p.getDescription());
        }
    }
}
