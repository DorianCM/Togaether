package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import togaether.App;
import togaether.BL.Facade.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import togaether.UI.SceneController;

import java.io.IOException;

/**
 * Controller de l'interface graphique Login
 */
public class LoginController {
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button seConnecter;
    @FXML
    private Button register;
    @FXML
    private Button forgotPassword;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    /**
     * Action effectuée lors d'une tentative de login
     */
    public void onLoginButtonClick(ActionEvent event) {
        try {
            UserFacade.createInstance().login(email.getText(), password.getText());
            SceneController.getInstance().switchToHomePage(event);
            System.out.println("Login réussi");
        } catch (UserNotFoundException e) {
            System.out.println("Cet utilisateur n'existe pas");
        } catch (UserBadPasswordException e) {
            System.out.println("Mauvais mot de passe");
        } catch (DBNotFoundException e) {
            System.out.println("Erreur lors de la connexion à la DB");
        }

    }
    public void switchToSceneRegisterFrame(ActionEvent event) {
        SceneController.getInstance().switchToRegister(event);
    }
}
