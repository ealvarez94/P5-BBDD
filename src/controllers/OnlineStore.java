package controllers;

import views.GestionOS;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OnlineStore  extends Application{ 
    private static GestionOS gestionOS;

    public static void printMenu(String[] options) {
        System.out.println("");
        System.out.println("            ONLINE STORE MENU          ");
        System.out.println("---------------------------------------\n");
        for (String option : options) {
            System.out.println("    " + option);
        }
        System.out.println("");
    }


    public static void main(String[] args) throws Exception {
        
        launch(args);


    } 

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(OnlineStore.class.getResource("/views/MainView.fxml"));
            //Cargo la ventana
            Pane ventana = (Pane) loader.load();
            
            //Cargo la escena
            Scene scene = new Scene(ventana);
            
            //Seteo la escena y la muestro
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
