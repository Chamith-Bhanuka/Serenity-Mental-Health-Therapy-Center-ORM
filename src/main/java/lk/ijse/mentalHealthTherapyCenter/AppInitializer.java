package lk.ijse.mentalHealthTherapyCenter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.mentalHealthTherapyCenter.config.FactoryConfiguration;
import lk.ijse.mentalHealthTherapyCenter.entity.User;
import lk.ijse.mentalHealthTherapyCenter.util.PasswordUtil;
import org.hibernate.Session;

import java.io.IOException;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/loginForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Serenity Mental Health Therapy Center");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon1.png")));
        stage.show();

    }

    public static void main(String[] args) {
        Session session = FactoryConfiguration.getInstance().getSession();
        launch();

//         int id = 1;
//         String name = "Admin";
//         String password = "1234";
//         String role = "Admin";
//         String contact = "0112564897";
//         String email = "admin@gmail.com";
//
//        String hashedPassword = PasswordUtil.hashPassword(password);
//
//        User user = new User();
//        user.setName(name);
//        user.setPassword(hashedPassword);
//        user.setRole(role);
//        user.setContact(contact);
//        user.setEmail(email);
//
//
//        session.beginTransaction();
//        session.persist(user);



        session.close();
    }
}