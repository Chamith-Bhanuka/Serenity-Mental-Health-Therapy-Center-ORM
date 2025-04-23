package lk.ijse.mentalHealthTherapyCenter.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import lk.ijse.mentalHealthTherapyCenter.bo.BOFactory;
import lk.ijse.mentalHealthTherapyCenter.bo.custom.UserBO;
import lk.ijse.mentalHealthTherapyCenter.dto.custom.LoginPageData;

public class AdminOverView implements Initializable {
    @FXML
    private PieChart pcMF;

    @FXML
    private LineChart<String, Number> lineChartAly;

    @FXML
    private TableView<Item> tblHistory;

    @FXML
    private TableColumn<Item, Button> colButton;

    @FXML
    private TableColumn<Item, ImageView> colImg;

    @FXML
    private TableColumn<Item, String> colName;

    @FXML
    private HBox hbxLeftBottom;

    @FXML
    private VBox vbxBottomRight;

    @FXML
    private Label lblMainName;



    private File file;
    private Media media;
//    private MediaPlayer mediaPlayer;

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.User);
    LoginPageData loginPageData = LoginPageData.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Create PieChart data
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Male", 60), // 60 males
                new PieChart.Data("Female", 40) // 40 females
        );

        // Set data to the PieChart
        pcMF.setData(pieData);

        // Customize colors
        for (PieChart.Data data : pieData) {
            if (data.getName().equals("Male")) {
                data.getNode().setStyle("-fx-pie-color: #3074ef;"); // Assign blue color for males
            } else if (data.getName().equals("Female")) {
                data.getNode().setStyle("-fx-pie-color: #64ff64;"); // Assign pink color for females
            }
        }

        ////line chart///
            // Create dummy data series
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("Mon", 12));
            series.getData().add(new XYChart.Data<>("Tue", 25));
            series.getData().add(new XYChart.Data<>("Wed", 35));
            series.getData().add(new XYChart.Data<>("Thu", 45));
            series.getData().add(new XYChart.Data<>("Fri", 55));

            // Add the data series to the chart
            lineChartAly.getData().add(series);

            lineChartAly.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
            series.getNode().setStyle("-fx-stroke: green");

            tblHistory.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            Platform.runLater(() -> {
                ((TableHeaderRow) tblHistory.lookup("TableHeaderRow")).setVisible(false);
            });
        });

            //table records bottom border only for available records

        tblHistory.setRowFactory(tv -> new TableRow<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    // No bottom border for empty rows
                    setStyle("-fx-border-color: transparent;");
                } else {
                    // Bottom border for rows with data
                    setStyle("-fx-border-color: transparent transparent lightgrey transparent; -fx-border-width: 0 0 1 0;");
                }
            }
        });

        //add custom styles for buttons


        //table center

        colName.setCellFactory(tc -> {
            TableCell<Item, String> cell = new TableCell<Item, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                    setAlignment(Pos.CENTER); // Align text to center
                }
            };
            return cell;
        });

        colImg.setCellFactory(tc -> {
            TableCell<Item, ImageView> cell = new TableCell<Item, ImageView>() {
                @Override
                protected void updateItem(ImageView item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(item);
                    }
                    setAlignment(Pos.CENTER); // Align image to center
                }
            };
            return cell;
        });

        colButton.setCellFactory(tc -> {
            TableCell<Item, Button> cell = new TableCell<Item, Button>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        item.setText("...");
                        item.getStyleClass().add("button-rounded");
                        setGraphic(item);
                    }
                    setAlignment(Pos.CENTER); // Align button to center
                }
            };
            return cell;
        });


        // Link columns to the model's properties
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colImg.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        colButton.setCellValueFactory(new PropertyValueFactory<>("button"));

        tblHistory.setItems(getTableData());


        lblMainName.setText(loginPageData.getUsername());

        String role = userBO.getRoleByUsername(loginPageData.getUsername());

        switch (role) {
            case "Admin": {
                setAdminView();
                break;
            }
            case "Receptionist": {
                setReceptionistView();
                break;
            }
        }
    }

    private ObservableList<Item> getTableData() {
        ObservableList<Item> data = FXCollections.observableArrayList();

        // Sample data
//        data.add(new Item("Alice", new ImageView(new Image(getClass().getResource("/images/show.png").toExternalForm())), new Button("Click Me")));
//        data.add(new Item("Bob", new ImageView(new Image(getClass().getResource("/images/show.png").toExternalForm())), new Button("Press Me")));
//        data.add(new Item("Charlie", new ImageView(new Image(getClass().getResource("/images/show.png").toExternalForm())), new Button("Try Me")));

        ImageView imageView = new ImageView(new Image(getClass().getResource("/images/man.png").toExternalForm()));
        imageView.setFitWidth(50); // Set desired width
        imageView.setFitHeight(50);// Set desired height
        data.add(new Item("Alice", imageView, new Button()));

        ImageView imageView2 = new ImageView(new Image(getClass().getResource("/images/man.png").toExternalForm()));
        imageView2.setFitWidth(50); // Set desired width
        imageView2.setFitHeight(50); // Set desired height
        data.add(new Item("Alice", imageView2, new Button()));

        ImageView imageView3 = new ImageView(new Image(getClass().getResource("/images/man.png").toExternalForm()));
        imageView3.setFitWidth(50); // Set desired width
        imageView3.setFitHeight(50); // Set desired height
        data.add(new Item("Alice", imageView3, new Button()));

        ImageView imageView4 = new ImageView(new Image(getClass().getResource("/images/man.png").toExternalForm()));
        imageView4.setFitWidth(50); // Set desired width
        imageView4.setFitHeight(50); // Set desired height
        data.add(new Item("Alice", imageView4, new Button()));

        ImageView imageView5 = new ImageView(new Image(getClass().getResource("/images/man.png").toExternalForm()));
        imageView5.setFitWidth(50); // Set desired width
        imageView5.setFitHeight(50); // Set desired height
        data.add(new Item("Alice", imageView5, new Button()));

        ImageView imageView6 = new ImageView(new Image(getClass().getResource("/images/man.png").toExternalForm()));
        imageView6.setFitWidth(50); // Set desired width
        imageView6.setFitHeight(50); // Set desired height
        data.add(new Item("Alice", imageView6, new Button()));

        ImageView imageView7 = new ImageView(new Image(getClass().getResource("/images/man.png").toExternalForm()));
        imageView7.setFitWidth(50); // Set desired width
        imageView7.setFitHeight(50); // Set desired height
        data.add(new Item("Alice", imageView7, new Button()));

        ImageView imageView8 = new ImageView(new Image(getClass().getResource("/images/man.png").toExternalForm()));
        imageView8.setFitWidth(50); // Set desired width
        imageView8.setFitHeight(50); // Set desired height
        data.add(new Item("Alice", imageView8, new Button()));


//        data.add(new Item("c", imageView3, new Button()));
//        data.add(new Item("d", imageView3, new Button()));
//        data.add(new Item("e", imageView3, new Button()));
//        data.add(new Item("f", imageView3, new Button()));


        return data;
    }

    // Model class
    public static class Item {
        private String name;
        private ImageView imageView;
        private Button button;

        public Item(String name, ImageView imageView, Button button) {
            this.name = name;
            this.imageView = imageView;
            this.button = button;
        }

        public String getName() {
            return name;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public Button getButton() {
            return button;
        }
    }


    @FXML
    void onTrackPaymentClick(ActionEvent event) {

    }

    private void setReceptionistView() {
        StackPane newContainer = new StackPane();

// Bind newContainer's size dynamically to vbxBottomRight's layout bounds
        newContainer.minWidthProperty().bind(hbxLeftBottom.layoutBoundsProperty().map(bounds -> bounds.getWidth() - 10));
        newContainer.minHeightProperty().bind(hbxLeftBottom.layoutBoundsProperty().map(bounds -> bounds.getHeight() - 10));
        newContainer.prefWidthProperty().bind(hbxLeftBottom.layoutBoundsProperty().map(bounds -> bounds.getWidth() - 10));
        newContainer.prefHeightProperty().bind(hbxLeftBottom.layoutBoundsProperty().map(bounds -> bounds.getHeight() - 10));

// Initialize the media file and MediaPlayer
        file = new File("C:\\Users\\User\\Downloads\\banner.mp4");
        media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

// Bind MediaView size directly to newContainer's size
        mediaView.fitWidthProperty().bind(newContainer.widthProperty());
        mediaView.fitHeightProperty().bind(newContainer.heightProperty());
        mediaView.setPreserveRatio(false); // Disable ratio preservation for full resizing

// Add the MediaView to the new container
        newContainer.getChildren().add(mediaView);

// Clear vbxBottomRight and add the new container
        hbxLeftBottom.getChildren().clear();
        hbxLeftBottom.getChildren().add(newContainer);

// Debugging: Track resizing to verify bindings
        hbxLeftBottom.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            System.out.println("vbxBottomRight layout bounds: " + newBounds.getWidth() + " x " + newBounds.getHeight());
        });
        newContainer.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            System.out.println("newContainer layout bounds: " + newBounds.getWidth() + " x " + newBounds.getHeight());
        });

// Set MediaPlayer to loop
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(mediaPlayer.getStartTime()));
        mediaPlayer.play();

// Error listener for MediaPlayer
        mediaPlayer.setOnError(() -> {
            System.out.println("Error: " + mediaPlayer.getError().getMessage());
        });

    }

    private void setAdminView() {
        StackPane newContainer = new StackPane();

// Bind newContainer's size dynamically to vbxBottomRight's layout bounds
        newContainer.minWidthProperty().bind(vbxBottomRight.layoutBoundsProperty().map(bounds -> bounds.getWidth() - 10));
        newContainer.minHeightProperty().bind(vbxBottomRight.layoutBoundsProperty().map(bounds -> bounds.getHeight() - 10));
        newContainer.prefWidthProperty().bind(vbxBottomRight.layoutBoundsProperty().map(bounds -> bounds.getWidth() - 10));
        newContainer.prefHeightProperty().bind(vbxBottomRight.layoutBoundsProperty().map(bounds -> bounds.getHeight() - 10));

// Initialize the media file and MediaPlayer
        file = new File("C:\\Users\\User\\Downloads\\banner.mp4");
        media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

// Bind MediaView size directly to newContainer's size
        mediaView.fitWidthProperty().bind(newContainer.widthProperty());
        mediaView.fitHeightProperty().bind(newContainer.heightProperty());
        mediaView.setPreserveRatio(false); // Disable ratio preservation for full resizing

// Add the MediaView to the new container
        newContainer.getChildren().add(mediaView);

// Clear vbxBottomRight and add the new container
        vbxBottomRight.getChildren().clear();
        vbxBottomRight.getChildren().add(newContainer);

// Debugging: Track resizing to verify bindings
        vbxBottomRight.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            System.out.println("vbxBottomRight layout bounds: " + newBounds.getWidth() + " x " + newBounds.getHeight());
        });
        newContainer.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            System.out.println("newContainer layout bounds: " + newBounds.getWidth() + " x " + newBounds.getHeight());
        });

// Set MediaPlayer to loop
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(mediaPlayer.getStartTime()));
        mediaPlayer.play();

// Error listener for MediaPlayer
        mediaPlayer.setOnError(() -> {
            System.out.println("Error: " + mediaPlayer.getError().getMessage());
        });
    }

    @FXML
    void onViewReportsClick(ActionEvent event) {

    }

}
