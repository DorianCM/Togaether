package togaether.UI.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Model.Travel;
import togaether.BL.Model.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TravelUpdateController {

    @FXML
    private Button returnButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmedButton;
    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;
    @FXML
    private TextArea nameTravel;
    @FXML
    private TextArea descriptionTravel;
    @FXML
    private Label labelError;
    @FXML
    private CheckBox nonDefineStart;
    @FXML
    private CheckBox nonDefineEnd;


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
            // Fermer la page pour revenir à la page principale
        } catch (Exception e) {
            System.out.println("Attention : Le voyage n'a pas pu être trouvé, veuillez réessayer");
            this.labelError.setText("Attention : Le voyage n'a pas pu être trouvé, veuillez réessayer");
            throw new RuntimeException(e);
        }
        initializeDatePicker();
    }

    public void initializeDatePicker(){
        if(this.travel.getDateStart()!=null){
            LocalDate dateLocal = Instant.ofEpochMilli(this.travel.getDateStart().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            this.dateStart.setValue(dateLocal);
        } else {
            this.nonDefineStart.setSelected(true);
            nonDefineStart();
        }
        if (this.travel.getDateEnd()!=null){
            LocalDate dateLocal = Instant.ofEpochMilli(this.travel.getDateEnd().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            this.dateEnd.setValue(dateLocal);
        } else {
            this.nonDefineEnd.setSelected(true);
            nonDefineEnd();
        }
    }

    public void nonDefineStart(){
        if(this.nonDefineStart.isSelected()){
            this.dateStart.setDisable(true);
            this.dateStart.setValue(null);
        } else {
            this.dateStart.setDisable(false);
        }
    }

    public void nonDefineEnd(){
        if(this.nonDefineEnd.isSelected()){
            this.dateEnd.setDisable(true);
            this.dateEnd.setValue(null);
        } else {
            this.dateEnd.setDisable(false);
        }
    }

    public void onReturnButtonClicked() {
        //TO DO return HomePage
        System.exit(1);

    }

    public void onCancelButtonClicked() {
        //TO DO return HomePage
        System.exit(1);

    }

    public void onConfirmedButtonClicked() {
        // Récupérer userconnected
        if(!this.nameTravel.getText().isEmpty()){
            this.travel.setNameTravel(this.nameTravel.getText());
        }
        if(!this.descriptionTravel.getText().isEmpty()){
            this.travel.setDescriptionTravel(this.descriptionTravel.getText());
        }
        Date start = null;
        if(!this.nonDefineStart.isSelected()){
            start = java.sql.Date.valueOf(this.dateStart.getValue());
        }
        Date end = null;
        if(!this.nonDefineEnd.isSelected()){
            end = java.sql.Date.valueOf(this.dateEnd.getValue());
        }

        if(start!=null && end!=null && end.before(start)){
            this.labelError.setText("Attention : Les dates séléctionnées ne sont pas valide,\nveuillez mettre une date de départ précédent une date de fin.");
        } else if (start != null && this.travel.getDateEnd() != null && this.travel.getDateEnd().before(start)) {
            this.labelError.setText("Attention : La date de début séléctionnée n'est pas valide,\nveuillez mettre une date de départ précédent une date de fin.");
        } else if (end !=null && this.travel.getDateStart()!=null && !this.travel.getDateStart().before(end)) {
            this.labelError.setText("Attention : La date de fin séléctionnée n'est pas valide,\nveuillez mettre une date de départ précédent une date de fin.");
        } else {
            this.travel.setDateStart(start);
            this.travel.setDateEnd(end);

            TravelFacade travelFacade = TravelFacade.createInstance();
            try {
                travelFacade.updateTravel(this.travel);
                // Fermer la page pour revenir à la page principale
                System.out.println("Voyage modifié !");
            } catch (Exception e) {
                System.out.println("Attention : Le voyage n'a pas pu être modifié, veuillez réessayer");
                this.labelError.setText("Attention : Le voyage n'a pas pu être modifié, veuillez réessayer");
                throw new RuntimeException(e);
            }
        }

    }
}