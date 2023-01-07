package togaether.UI.Frame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ItineraryCreateFrame extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginFrame.class.getResource("ItineraryCreate.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Togaether - Create Itinerary");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}