package lk.ijse.mentalHealthTherapyCenter.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.mentalHealthTherapyCenter.AppInitializer;
import lk.ijse.mentalHealthTherapyCenter.bo.BOFactory;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.UserBO;
import lk.ijse.mentalHealthTherapyCenter.dto.custom.LoginPageData;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private TextField txtSearch;

    @FXML
    private Pane focusHolder;

    @FXML
    private AnchorPane ancMain;

    @FXML
    private ImageView iconHome;

    @FXML
    private ImageView imgDropDown;

    @FXML
    private StackPane stpAddNew;

    @FXML
    private StackPane stpHome;

    @FXML
    private StackPane stpPatient;

    @FXML
    private StackPane stpSchedule;

    @FXML
    private StackPane stpTProgram;

    @FXML
    private StackPane stpTherapist;

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.User);

    LoginPageData loginPageData = LoginPageData.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        focusHolder.setFocusTraversable(true);
        focusHolder.requestFocus();

        txtSearch.setPromptText("      🔎  Search appointment, patient, ets");

        try {
            navigateTo("/view/AdminOverView.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Tooltip tooltip = new Tooltip("HOME| Go to the home page to access all key features easily.");
        Tooltip.install(iconHome, tooltip);
        tooltip.setShowDelay(Duration.millis(100));
        tooltip.setHideDelay(Duration.millis(200));


        //drop down
        ContextMenu contextMenu = new ContextMenu();

        MenuItem manageAccount = new MenuItem("Manage Account");
        MenuItem signOut = new MenuItem("Sign Out");

        // Apply styles directly
        manageAccount.setStyle("-fx-padding: 8px 16px; -fx-font-size: 14px; -fx-font-family: Arial; -fx-text-fill: white;");
        signOut.setStyle("-fx-padding: 8px 16px; -fx-font-size: 14px; -fx-font-family: Arial; -fx-text-fill: white;");

        // ContextMenu background styling
        contextMenu.setStyle("-fx-background-color: #556B2F; -fx-border-color: #8FBC8F; -fx-border-radius: 5px;");


        contextMenu.getItems().addAll(manageAccount, signOut);

        imgDropDown.setOnMouseClicked(event -> {
            contextMenu.show(imgDropDown, event.getScreenX(), event.getScreenY());
        });

        manageAccount.setOnAction(this::handleManageAccount);
        signOut.setOnAction(this::handleSignOut);

        String role = userBO.getRoleByUsername(loginPageData.getUsername());
//        System.out.println("user name from login page: " + loginPageData.getUsername());
//        System.out.println("Role by custom method: " + role);

        switch (role) {
            case "Admin": {
                stpPatient.setVisible(false);
                stpSchedule.setVisible(false);
                break;
            }
            case "Receptionist": {
                stpAddNew.setVisible(false);
                stpTherapist.setVisible(false);
                stpTProgram.setVisible(false);
            }
        }

    }

    private void navigateTo(String fxmlPath) throws IOException {
        try {
            ancMain.getChildren().clear();
            AnchorPane load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            load.prefHeightProperty().bind(ancMain.heightProperty());
            load.prefWidthProperty().bind(ancMain.widthProperty());
            ancMain.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page").show();
        }
    }

    @FXML
    void onAddNewUserClick(MouseEvent event) throws IOException {
        System.out.println("Add New User clicked");
        navigateTo("/view/userManagement.fxml");
    }

    @FXML
    void onHomeClick(MouseEvent event) throws IOException {
        System.out.println("Home clicked");
        navigateTo("/view/AdminOverView.fxml");
    }

    @FXML
    void onTherapyProgramClick(MouseEvent event) throws IOException {
        System.out.println("Therapy Program clicked");
        navigateTo("/view/therapyProgramManage.fxml");
    }

    @FXML
    void onRegisterPatientClick(MouseEvent event) throws IOException {
        System.out.println("Register Patient clicked");
        navigateTo("/view/patientManage.fxml");
    }

    @FXML
    void onTherapistClick(MouseEvent event) throws IOException {
        System.out.println("Manage Therapist clicked");
        navigateTo("/view/therapistManage.fxml");
    }

    @FXML
    void onScheduleSessionClick(MouseEvent event) throws IOException {
        System.out.println("Schedule Session clicked");
        navigateTo("/view/scheduleSession.fxml");
    }

    // Method to handle "Manage Account" button click
    private void handleManageAccount(ActionEvent event) {
        System.out.println("Manage Account clicked 123");
        // Add additional code for managing account here
    }

    // Method to handle "Sign Out" button click
    @FXML
    private void handleSignOut(ActionEvent event) {
        System.out.println("Sign Out clicked");

        // Confirm the sign-out action with the user.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to sign out?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            // Close the current stage using a node from scene
            Stage currentStage = (Stage) txtSearch.getScene().getWindow();
            currentStage.close();

            // Load the custom loading screen FXML
            final Stage loadingStage = new Stage();
            try {
                Parent loadingRoot = FXMLLoader.load(getClass().getResource("/view/loading.fxml"));
                Scene loadingScene = new Scene(loadingRoot);
                loadingStage.setScene(loadingScene);
                loadingStage.initStyle(StageStyle.UNDECORATED);
                loadingStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load the loading screen").showAndWait();
                return; // Exit if loading screen cannot be loaded.
            }

            // Use a PauseTransition to simulate a delay before loading the login form (e.g., 3 seconds).
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> {
                try {
                    Parent loginRoot = FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"));
                    Scene loginScene = new Scene(loginRoot);
                    Stage loginStage = new Stage();
                    loginStage.setTitle("Serenity Mental Health Therapy Center");
                    loginStage.setScene(loginScene);
                    loginStage.initStyle(StageStyle.UNDECORATED);
                    loginStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon1.png")));
                    loginStage.show();
                    System.out.println("Loaded login form");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Failed to load login form").showAndWait();
                } finally {
                    // Close the loading screen after loading the login form.
                    loadingStage.close();
                }
            });
            pause.play();
        }
    }



}
