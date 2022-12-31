package togaether.UI.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Model.Travel;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

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

    /**
     * The connected Travel
     */
    Travel travel;

    @FXML
    protected void initialize() {
        // Récupérer user connected
        // Récupérer travel connected
        TravelFacade travelFacade = TravelFacade.createInstance();
        try {
            this.travel = travelFacade.findTravelById(4);
        } catch (Exception e) {
            System.out.println("Attention : Le voyage n'a pas pu être trouvé, veuillez réessayer");
            this.labelError.setText("Attention : Le voyage n'a pas pu être trouvé, veuillez réessayer");
            throw new RuntimeException(e);
        }
        initializeInfo();
    }

    public void initializeInfo(){
        String dateString = "";
        if(this.travel.getDateStart()!=null){
            dateString = this.travel.getDateStart().toString();
        } else {
            dateString = "Date de début indéfini";
        }
        dateString += " - ";
        if(this.travel.getDateEnd()!=null){
            dateString += this.travel.getDateEnd().toString();
        } else {
            dateString += "Date de fin indéfini";
        }
        this.dateTravel.setText(dateString);
        this.nameTravel.setText(this.travel.getNameTravel());
        this.descriptionTravel.setText(this.travel.getDescriptionTravel());
        this.collaborator.setText("Collaborateurs :\n"+this.travel.getOwner().getName());
    }

    public void onReturnButtonClicked() {
        //TO DO return HomePage
        System.exit(1);
    }

    public void editBudget(){
        //TO DO return budget
        System.exit(1);
    }

    public void showExpense(){
        //TO DO return expense
        System.exit(1);
    }

    public void showCollaborator(){
        //TO DO return collaborator
        System.exit(1);
    }

    public void showItinerary(){
        //TO DO return itinerary
        System.exit(1);
    }

    public void showDocument(){
        //TO DO return document
        System.exit(1);
    }

    public void showActivity(){
        //TO DO return activity
        System.exit(1);
    }

    public void showChat(){
        //TO DO return chat
        System.exit(1);
    }

}