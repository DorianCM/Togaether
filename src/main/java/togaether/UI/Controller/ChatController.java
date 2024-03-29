package togaether.UI.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import togaether.BL.ChatClient;
import togaether.BL.Facade.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import togaether.BL.Model.*;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.BL.TogaetherException.TravelNotFoundException;
import togaether.UI.SceneController;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatController {
  @FXML
  private Button returnButton;
  @FXML
  private TextArea inputMessage;
  @FXML
  private ListView messageList;
  @FXML
  private Button sendButton;
  @FXML
  private Label infoLabel;

  /**
   * Service permettant de voir les nouveaux messages en direct (nécessite un ChatServer actif)
   */
  private ChatClient chatClient = null;

  @FXML
  protected void initialize() {
    this.reloadMessages();
    try {
      this.chatClient = new ChatClient("127.0.0.1", 5555, this);
    } catch (IOException e) {
      this.displayInfo("Attention : connection au chat en direct échouée");
    }
    //Scroller tout en bas du chat, pour voir les messages les plus récents
    Platform.runLater( () -> this.messageList.scrollTo(this.messageList.getItems().size()-1));
  }

  public void reloadMessages() {
    ChatFacade chat = ChatFacade.getInstance();
    TravelFacade travelfacade = TravelFacade.getInstance();

    try {
      ArrayList<Message> liste = (ArrayList<Message>) chat.getMessagesByTravelId(travelfacade.getTravel().getIdTravel());
      for(Message m : liste) {
        addMessage(m.toString());
      }

    } catch (TravelNotFoundException e) {
      this.displayInfo("Erreur lors du chargement des données : Le voyage n'a pas été trouvé");
    } catch (DBNotFoundException e) {
      this.displayInfo("Erreur lors du chargement des données : La connection à la base de données a échoué");
    }
  }

  public void onReturnButtonClicked(ActionEvent event) {
    this.close();
    SceneController.getInstance().switchToTravel(event);
  }
  public void onSendButtonClicked(ActionEvent event) {
    String text = this.inputMessage.getText();
    if(text.length() == 0)
      this.displayInfo("Attention : Message vide");
    else if(text.length() > 255)
      this.displayInfo("Attention : Nombre de caractères trop grand (supérieur à 255)");
    else {
      ChatFacade chat = ChatFacade.getInstance();
      Boolean success = chat.sendMessage(text);
      if(success) {
        this.inputMessage.clear();

        TravelFacade travelFacade = TravelFacade.getInstance();
        Travel t = travelFacade.getTravel();
        Collaborator c = travelFacade.getCollaborator();
        if(chatClient!=null) chatClient.handleMessageFromChatController(new Message(0, t.getIdTravel(), c, text, new Timestamp(System.currentTimeMillis())).toString());
      }
      else
        this.displayInfo("Attention : Le message n'a pas pu être envoyé, veuillez réessayer");
    }
  }

  public void addMessage(String s) {
    Platform.runLater(() -> messageList.getItems().add(s));
  }

  public void displayInfo(String info) {
    this.infoLabel.setText(info);
  }
  public void close() {
    if(this.chatClient != null)
      this.chatClient.quit();
  }
}
