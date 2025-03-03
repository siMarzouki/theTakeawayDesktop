
package edu.thetakeaway.gui.reclamation;

import edu.thetakeaway.entities.Reclamation;
import edu.thetakeaway.entities.Reponse;
import edu.thetakeaway.services.ReclamationService;
import edu.thetakeaway.services.ReponseService;
import edu.thetakeaway.utils.SharedData;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


public class ReclamationDetailsAdminScreenController implements Initializable {

    @FXML
    private Button reservationsBtn;
    @FXML
    private Button reclamationsBtn;
    @FXML
    private Label clientLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label sujetLabel;
    @FXML
    private Label statutLabel;
    @FXML
    private Text contenuText;
    @FXML
    private ScrollPane reponsesPane;
    @FXML
    private Button repondreBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private Button reopenBtn;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Reclamation rec = SharedData.selectedReclamation;
        dateLabel.setText(rec.getDate()+" - "+rec.getHeure());
        sujetLabel.setText(rec.getSujet());
        clientLabel.setText(rec.getUser().getNom());
        statutLabel.setText(rec.getStatut());
        contenuText.setText(rec.getContenu());
        loadReponsesInPane();
        if(rec.getStatut().equals("Fermé")){
            repondreBtn.setDisable(true);
            reopenBtn.setDisable(false);
            closeBtn.setDisable(true);
        }else{
            repondreBtn.setDisable(false);
            reopenBtn.setDisable(true);
            closeBtn.setDisable(false);
        }
    }


    private void loadReponsesInPane(){
        Reclamation rec = SharedData.selectedReclamation;
        ReponseService rps = new ReponseService();
        List<Reponse> reponses = rps.getReponsesByReclamatioId(rec);
        VBox  p = new VBox ();
        p.setSpacing(10);
        p.setPadding(new Insets(10));
        for(Reponse r : reponses){
            VBox vb = new VBox();
            HBox hb = new HBox();
            Label author = new Label(r.getAuthor().getId()==SharedData.currentUser.getId()?" Client ":"Admin");
            author.setMinWidth(80);
            author.setStyle(
                    r.getAuthor().getId()==SharedData.currentUser.getId()? 
                            "-fx-background-color: #134F90;-fx-text-fill :#ffffff;-fx-padding:5 5;-fx-font-size:22; "
                            :"-fx-background-color: #FF1E90;-fx-text-fill :#ffffff; -fx-padding:5 5;-fx-font-size:22;");
            Label date = new Label(r.getDate()+" "+r.getHeure());
            date.setStyle("-fx-font-size:14;-fx-font-style:italic;-fx-text-fill :#554433;");
            Label content = new Label(r.getContenu());
            content.setStyle("-fx-font-size:18;-fx-font-style:bold;");
            vb.getChildren().addAll(date,content);
            hb.getChildren().addAll(author,vb);
            hb.setSpacing(50);
            p.getChildren().add(hb);
        }
        reponsesPane.setPannable(true);
        reponsesPane.setContent(p);
    }
    
    @FXML
    private void reopen(ActionEvent actionEvent){
        SharedData.selectedReclamation.setStatut("Ouvert");
        ReclamationService rs = new ReclamationService();
        rs.modifier(SharedData.selectedReclamation);
        navigateReclamationDetails(actionEvent);
    }
    @FXML
    private void close(ActionEvent actionEvent){
        SharedData.selectedReclamation.setStatut("Fermé");
        ReclamationService rs = new ReclamationService();
        rs.modifier(SharedData.selectedReclamation);
        navigateReclamationDetails(actionEvent);
        TrayNotification tray = new TrayNotification();
        tray.setTitle("Reclamation Fermé");
        tray.setMessage("Reclamation Fermé avec succés");
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3200));
    }
    @FXML
    private void delete(ActionEvent actionEvent){
        ReclamationService rs = new ReclamationService();
        rs.supprimer(SharedData.selectedReclamation);
        navigateReclamations(actionEvent);
    }



    //Navigation
    @FXML
    private void navigateReclamationDetails(ActionEvent actionEvent) {
        navigateTo(actionEvent, "ReclamationDetailsAdminScreen.fxml");
    }
    @FXML
    private void navigateReclamations(ActionEvent actionEvent) {
        navigateTo(actionEvent, "ReclamationsAdminScreen.fxml");
    }
        @FXML
    private void navigateToRestaurants(ActionEvent actionEvent) {
        navigateTo(actionEvent, "../restaurant/RestaurantsAdminScreen.fxml");
    }

    @FXML
    private void navigateToMenu(ActionEvent actionEvent) {
        navigateTo(actionEvent, "../menu/MenuAdminScreen.fxml");
    }

    @FXML
    private void navigateToPromotions(ActionEvent actionEvent) {
        navigateTo(actionEvent, "../promotions/PromotionsAdminScreen.fxml");
    }

    @FXML
    private void navigateToDashboard(ActionEvent actionEvent) {
        navigateTo(actionEvent, "../dashboard/AdminDashboardScreen.fxml");
    }

    @FXML
    private void navigateToTables(ActionEvent actionEvent) {
        navigateTo(actionEvent, "../tables/TablesAdminScreen.fxml");
    }

    @FXML
    private void navigateToReservations(ActionEvent actionEvent) {
        navigateTo(actionEvent, "../reservations/ReservationsAdminScreen.fxml");
    }

    @FXML
    private void navigateToReclamations(ActionEvent actionEvent) {
        navigateTo(actionEvent, "../reclamation/ReclamationsAdminScreen.fxml");
    }

    @FXML
    private void navigateToCommandes(ActionEvent actionEvent) {
        navigateTo(actionEvent, "../commande/CommandeAdminScreen.fxml");
    }
    
     @FXML
    private void navigateToSock(ActionEvent actionEvent) {
        navigateTo(actionEvent, "../stock/choisirTable.fxml");
    }

    @FXML
    private void navigateToBlog(ActionEvent actionEvent) {
        navigateTo(actionEvent, "../blog/blogListBack.fxml");
    }
    
    @FXML
    private void navigateToUsers(ActionEvent actionEvent) {
        navigateTo(actionEvent, "../user/ShowUser.fxml");
    }
    @FXML
    private void navigateNewReponse(ActionEvent actionEvent) {
        navigateTo(actionEvent, "NewReponseAdminScreen.fxml");
    }

    private void navigateTo(ActionEvent actionEvent, String path) {
        try {
            Parent exercices_parent = FXMLLoader.load(getClass().getResource(path));
            Scene ex_section_scene = new Scene(exercices_parent);
            Stage second_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            second_stage.setScene(ex_section_scene);
            second_stage.show();
        } catch (IOException ex) {

        }
    }
}
