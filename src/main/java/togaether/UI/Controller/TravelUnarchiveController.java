package togaether.UI.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Model.Travel;

public class TravelUnarchiveController {
    @FXML
    private Label nameVoyage;
    @FXML
    private Text descriptionVoyage;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmedButton;
    @FXML
    private Label labelError;

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
            throw new RuntimeException(e);
        }
        this.nameVoyage.setText(this.travel.getNameTravel());
        this.descriptionVoyage.setText(this.travel.getDescriptionTravel());
    }

    public void onCancelButtonClicked() {
        //TO DO retourner HomePage
        System.exit(1);
    }

    public void onConfirmedButtonClicked() {
        TravelFacade travelFacade = TravelFacade.createInstance();
        try {
            travelFacade.unarchiveTravel(this.travel);
            // Fermer la page pour revenir à la page principale
            System.out.println("Voyage désarchivé !");
        } catch (Exception e) {
            System.out.println("Attention : Le voyage n'a pas pu être archivé, veuillez réessayer");
            this.labelError.setText("Attention : Le voyage n'a pas pu être archivé, veuillez réessayer");
            throw new RuntimeException(e);
        }
    }

}