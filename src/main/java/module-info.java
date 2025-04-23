module lk.ijse.mentalHealthTherapyCenter.mentalhealththerapycenter {
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires static lombok;
    requires jakarta.persistence;
    requires jbcrypt;
    requires javafx.controls;
    requires javafx.media;


    opens lk.ijse.mentalHealthTherapyCenter.controller to javafx.fxml, javafx.base, javafx.controls, javafx.media;
    opens lk.ijse.mentalHealthTherapyCenter.entity to org.hibernate.orm.core;
    opens lk.ijse.mentalHealthTherapyCenter.config to jakarta.persistence;
    opens lk.ijse.mentalHealthTherapyCenter.view.tdm to javafx.base;
    exports lk.ijse.mentalHealthTherapyCenter;
}