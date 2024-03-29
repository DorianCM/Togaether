package togaether.UI.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import togaether.App;
import togaether.BL.Facade.CollaboratorFacade;
import togaether.BL.Facade.ExpenseFacade;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Model.Collaborator;
import togaether.BL.Model.Expense;
import togaether.BL.Model.Travel;
import togaether.DB.AbstractFactory;
import togaether.DB.CollaboratorDAO;
import togaether.DB.Postgres.PostgresFactory;
import togaether.DB.UserDAO;
import togaether.UI.SceneController;

import java.sql.SQLException;
import java.util.ArrayList;

public class TravelController {

    @FXML
    private Button returnButton;
    // info travel
    @FXML
    private Label labelError;
    @FXML
    private Label nameTravel;
    @FXML
    private Label dateTravel;
    @FXML
    private Text descriptionTravel;
    @FXML
    private Text collaborator;

    // button
    @FXML
    private Button showActivity;
    @FXML
    private Button editBudget;
    @FXML
    private Button showExpense;
    @FXML
    private Button showCollaborator;
    @FXML
    private Button showDocument;
    @FXML
    private Button showItinerary;
    @FXML
    private Button showChat;

    @FXML
    protected void initialize() {
        TravelFacade travelFacade = TravelFacade.getInstance();
        UserFacade userFacade = UserFacade.getInstance();
        CollaboratorFacade collaboratorFacade = CollaboratorFacade.getInstance();

        try {
            travelFacade.setCollaborator(collaboratorFacade.findCollaboratorByUserAndTravel(userFacade.getConnectedUser(), travelFacade.getTravel()));
            travelFacade.setCollaborators(collaboratorFacade.findCollaboratorByTravel(travelFacade.getTravel()));
            initializeInfo();
        } catch (Exception e) {
            System.out.println("Attention : Le voyage n'a pas pu être trouvé, veuillez réessayer");
            this.labelError.setText("Attention : Le voyage n'a pas pu être trouvé, veuillez réessayer");
        }
    }

    public void initializeInfo() {
        TravelFacade travelFacade = TravelFacade.getInstance();
        Travel travel = travelFacade.getTravel();
        String dateString = "";
        if (travel.getDateStart() != null) {
            dateString = travel.getDateStart().toString();
        } else {
            dateString = "Date de début indéfini";
        }
        dateString += " - ";
        if (travel.getDateEnd() != null) {
            dateString += travel.getDateEnd().toString();
        } else {
            dateString += "Date de fin indéfini";
        }
        this.dateTravel.setText(dateString);
        this.nameTravel.setText(travel.getNameTravel());
        this.descriptionTravel.setText(travel.getDescriptionTravel());
        CollaboratorFacade collaboratorFacade = CollaboratorFacade.getInstance();

        ArrayList<Collaborator> collabos = new ArrayList<>(collaboratorFacade.findCollaboratorByTravel(travelFacade.getTravel()));
        String collaboString = "";
        for (int i =0; i < collabos.size(); i++){
            collaboString = collaboString + " - " + collabos.get(i).getName();
        }
        this.collaborator.setText("Collaborateurs :\n"+collaboString);

    }

    public void onReturnButtonClicked(ActionEvent event) {
        TravelFacade.getInstance().setTravel(null);
        TravelFacade.getInstance().setCollaborator(null);
        TravelFacade.getInstance().setCollaborators(null);
        SceneController.getInstance().switchToHomePage(event);
    }

    public void onUpdateTravelButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToUpdateTravel(event);
    }

    public void editBudget(ActionEvent event) {
        SceneController.getInstance().switchToBudget(event);
    }

    public void showExpense(ActionEvent event) {
        SceneController.getInstance().switchToExpense(event);
    }

    public void showCollaborator(ActionEvent event) {
        SceneController.getInstance().switchToCollaborator(event);
    }

    public void showItinerary(ActionEvent event) {
        SceneController.getInstance().switchToItinerary(event);
    }

    public void showDocument(ActionEvent event) {
        SceneController.getInstance().switchToDocument(event);
    }

    public void showActivity(ActionEvent event) {
        //TO DO return activity
        SceneController.getInstance().switchToActivity(event);
    }

    public void showChat(ActionEvent event) {
        SceneController.getInstance().switchToChat(event);
    }

}
