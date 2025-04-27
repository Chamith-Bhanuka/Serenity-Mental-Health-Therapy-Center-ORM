package lk.ijse.mentalHealthTherapyCenter.controller;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.mentalHealthTherapyCenter.AppInitializer;
import lk.ijse.mentalHealthTherapyCenter.config.FactoryConfiguration;
import lk.ijse.mentalHealthTherapyCenter.dto.custom.LoginPageData;
import lk.ijse.mentalHealthTherapyCenter.entity.User;
import lk.ijse.mentalHealthTherapyCenter.util.PasswordUtil;
import org.hibernate.Session;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    @FXML
    private AnchorPane mainAnc;

    @FXML
    private ImageView imgBrain;

    @FXML
    private Button btnSignIn;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUserName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPasswordShow;

    @FXML
    private ImageView imgShowHide;

    @FXML
    private StackPane stkBtn;

    @FXML
    private Label lblErrorMessege;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label loadingLabel;


    private boolean isPasswordVisible = false;

    LoginPageData loginPageData = LoginPageData.getInstance();



    @FXML
    void onSignInClicked(ActionEvent event) throws IOException, LoginException {
        System.out.println("onSignInClicked");
//        startLoadingProcess();

        loginPageData.setUsername(txtUserName.getText());
        System.out.println("Get username from login Page: " + loginPageData.getUsername());

        if (login(txtUserName.getText(), txtPassword.getText())) {
            getDashboard();
//            startLoadingProcess();

//            close stage when going to dashboard
            Stage currentStage = (Stage) lblUserName.getScene().getWindow();
            currentStage.close();

        }

        lblErrorMessege.setText("Invalid username or password");
        lblErrorMessege.setVisible(true);

        throw new LoginException("Invalid username or password. Please try again.");

    }

    private boolean login(String userName, String attemptPassword) {
        Session session = FactoryConfiguration.getInstance().getSession();
        User user = session.createQuery("FROM User u WHERE u.name=:username", User.class)
                .setParameter("username", userName)
                .uniqueResult();

        session.close();

        if (user != null) {
            if (PasswordUtil.checkPassword(attemptPassword, user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @FXML
    void onCloseIconClicked(MouseEvent event) {
        System.out.println("Bye..!");
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoAnimation();
        scaleLabels();
        showHide();
    }

    private void logoAnimation() {

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), imgBrain);
        scaleTransition.setFromX(1.0);
        scaleTransition.setToX(1.15);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToY(1.15);
        scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
        scaleTransition.setAutoReverse(true);

        scaleTransition.play();
    }

    private void startLoadingProcess() {

        progressBar.setVisible(true);
        stkBtn.setVisible(true);
        loadingLabel.setVisible(true);
        btnSignIn.setVisible(false);
        lblErrorMessege.setVisible(false);


        String[] statusMessages = {
                "Initializing system...",
                "Loading data modules...",
                "Optimizing resources...",
                "Preparing dashboard..."
        };

        Timeline statusUpdater = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    int index = (int) (Math.random() * statusMessages.length);
                    loadingLabel.setText(statusMessages[index]);
                })
        );
        statusUpdater.setCycleCount(Timeline.INDEFINITE);
        statusUpdater.play();

        Task<Void> loadingTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(30);
                    updateProgress(i, 100);
                }
                return null;
            }
        };

        progressBar.progressProperty().bind(loadingTask.progressProperty());

        loadingTask.setOnSucceeded(event -> {
            statusUpdater.stop();
            try {
                getDashboard();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                Stage currentStage = (Stage) lblUserName.getScene().getWindow();
                currentStage.close();
            }
        });

        new Thread(loadingTask).start();
    }

    private void scaleLabels() {

        txtUserName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // TextField is focused
                lblUserName.setStyle("-fx-font-size: 14px;");
                lblErrorMessege.setVisible(false);

            } else {
                // TextField is not focused
                lblUserName.setStyle("-fx-font-size: 12px;");
                lblErrorMessege.setVisible(false);
            }
        });

        txtPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // TextField is focused
                lblPassword.setStyle("-fx-font-size: 14px;");

            } else {
                // TextField is not focused
                lblPassword.setStyle("-fx-font-size: 12px;");
            }
        });

        txtPasswordShow.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // TextField is focused
                lblPassword.setStyle("-fx-font-size: 14px;");

            } else {
                // TextField is not focused
                lblPassword.setStyle("-fx-font-size: 12px;");
            }
        });
    }

    private void showHide() {
        //Load images
        Image showImage = new Image(Objects.requireNonNull(getClass().getResource("/images/show.png")).toExternalForm());
        Image hideImage = new Image(Objects.requireNonNull(getClass().getResource("/images/hide.png")).toExternalForm());

        imgShowHide.setImage(showImage);

        imgShowHide.setOnMouseClicked(event -> {
            if (isPasswordVisible) {
                //Hide password
                txtPassword.setText(txtPasswordShow.getText());
                txtPassword.setVisible(true);
                txtPasswordShow.setVisible(false);
                imgShowHide.setImage(showImage);
            } else {
                System.out.println("first click");
                txtPasswordShow.setText(txtPassword.getText());
                txtPasswordShow.setVisible(true);
                txtPassword.setVisible(false);
                imgShowHide.setImage(hideImage);
            }
            isPasswordVisible = !isPasswordVisible;
        });
    }

    private void getDashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Serenity Mental Health Therapy Center");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon1.png")));
        stage.show();
    }

}
