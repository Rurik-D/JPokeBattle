package it.rd.jpokebattle.view;

import it.rd.jpokebattle.controller.menu.MenuController;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Classe che definisce le schede profilo contenute nello ScrollPane nel
 * menù di visualizzazione profili.
 */
public class ProfileBox extends HBox {
    private Profile profile;
    private Label nickname;
    private ImageView avatar;

    public ProfileBox(Profile profile) {
        this.profile = profile;
        setNickname();
        setAvatar();
        setProfileBox();
    }

    private void setProfileBox() {
        this.setSpacing(15);
        this.setPadding(new Insets(15));
        this.setStyle("-fx-border-color: #616161; -fx-border-width: 2; -fx-border-radius: 5px;"); // Bordo intorno al profilo
        this.setMaxWidth(275);
        this.setMinWidth(275);
        this.setCursor(Cursor.HAND);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setOnMouseClicked(e -> getController(e).profileModifyPreview(profile));
        this.getChildren().addAll(avatar, nickname);
    }

    private void setNickname() {
        this.nickname = new Label(profile.getName());
        this.nickname.setStyle("-fx-font-family: \"Lucida Console\"; -fx-font-size: 22px; -fx-text-fill: #D1D1D1;");
    }

    private void setAvatar() {
        Image image = ResourceLoader.loadImage(profile.getAvatarSrcName());
        this.avatar = new ImageView();
        this.avatar.setImage(image);
        this.avatar.setFitWidth(40); // Larghezza dell'avatar
        this.avatar.setFitHeight(40); // Altezza dell'avatar
        this.avatar.setPreserveRatio(true);
    }

    /**
     * A partire dall'evento di input ricava lo stage e poi il controller del
     * menù.
     *
     * @param e     Click del mouse
     * @return      Il controller del menù
     */
    private static MenuController getController(MouseEvent e) {
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        FXMLLoader loader = (FXMLLoader) stage.getScene().getUserData();
        return loader.getController();
    }
}
