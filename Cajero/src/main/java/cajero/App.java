package cajero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
       FXMLLoader loader = new FXMLLoader (getClass().getResource("/cajero/login.fxml"));
       Parent root = loader.load();
       Scene scene = new Scene(root);
       stage.setScene(scene);
       Login controller = loader.getController();
       controller.setStage(stage);
       stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    

}