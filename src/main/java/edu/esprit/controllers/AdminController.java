package edu.esprit.controllers;

import com.jfoenix.controls.JFXButton;
import edu.esprit.entities.SessionManager;
import edu.esprit.entities.SessionUtilisateur;
import edu.esprit.tests.HelloApplication;
import edu.esprit.services.ServiceUser;
import edu.esprit.entities.User;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import static java.nio.file.Files.newInputStream;

public class AdminController {
    @FXML
    public VBox statistique;
    @FXML
    public PieChart NbrPays;
    @FXML
    public BarChart<String, Integer> BarMonth;
    @FXML
    public TextField search;
    private final ServiceUser serviceUser = new ServiceUser();
    public User userModify= new User();
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern_email = Pattern.compile(EMAIL_REGEX);
    private static final String PHONE_REGEX =
            "^\\d{8}$";
    private static final Pattern pattern_phone = Pattern.compile(PHONE_REGEX);
    private static final String Name_REGEX =
            "^[a-zA-Z]{3,}$";
    private static final Pattern pattern_name = Pattern.compile(Name_REGEX);

    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private static final Pattern pattern_password = Pattern.compile(PASSWORD_REGEX);
    @FXML
    public ImageView imageProfile;
    @FXML
    public ScrollPane scroll;

    @FXML
    public TextArea description;
    @FXML
    public FlowPane tableUser2;
    @FXML
    public ComboBox<String> combo;
    @FXML
    public VBox AddForm;
    @FXML
    public TextField nom_add;
    @FXML
    public TextField prenom_add;
    @FXML
    public Text nom_error_add;
    @FXML
    public Text prenom_error_add;
    @FXML
    public TextField email_add;
    @FXML
    public TextField phone_number_add;
    @FXML
    public Text email_error_add;
    @FXML
    public Text phone_number_error_add;
    @FXML
    public PasswordField password_add;
    @FXML
    public PasswordField comfirm_pass_add;
    @FXML
    public Text password_error_add;
    @FXML
    public Text conf_password_error_add;
    @FXML
    public JFXButton AddAdmin;
    public boolean isAddFormVisible = false;
    public boolean isListUsersVisible = true;
    @FXML
    public VBox Details;
    @FXML
    public ImageView imageProfile1;
    @FXML
    public Text nomDetails;
    @FXML
    public Text prenomDetails;
    @FXML
    public Text emailDetails;
    @FXML
    public Text PhoneDetails;
    @FXML
    public Text createdDetails;
    @FXML
    public Text Status;
    @FXML
    public Text descriptionDetails;
    @FXML
    public Text countryDetails;
    @FXML
    public ImageView profileImage;
    @FXML
    public Text emailprofile;
    @FXML
    public VBox modifForm1;
    @FXML
    public ImageView imageProfile11;
    @FXML
    public TextField nom_modif;
    @FXML
    public TextField prenom_modif;
    @FXML
    public Text nom_error_modif;
    @FXML
    public Text prenom_error_modif;
    @FXML
    public TextField email_modif;
    @FXML
    public TextField phone_modif;
    @FXML
    public Text email_error_modif;
    @FXML
    public Text phone_number_error_modif;
    @FXML
    public TextArea description_modif;
    @FXML
    public ComboBox<String> country_modif;
    private Stage stage;

    @FXML
    private void initialize() throws IOException {
        AddForm.setTranslateY(2000);
        statistique.setTranslateY(2000);
        Details.setTranslateY(2000);
        modifForm1.setTranslateY(2000);
        showAllUsers2(serviceUser.getAll());
        ObservableList<String> items = FXCollections.observableArrayList(
                "Tunisia",
                "United state",
                "Spain",
                "England",
                "Belguim"
        );
        combo.setItems(items);
        emailprofile.setText(SessionManager.getUserEmail());
        String EditPathProfile = "C:\\Users\\User\\IdeaProjects\\ProjectHOpe\\src\\main\\resources\\assets\\"+SessionManager.getUserImage();
        Image image = new Image(new File(EditPathProfile).toURI().toString());
        profileImage.setImage(image);
        System.out.println(search.getText().isEmpty());
    }
    private void slideOut(VBox vbox)
    {
        TranslateTransition slideOutTransition = new TranslateTransition(Duration.millis(300), vbox);
        slideOutTransition.setToY(2000);
        slideOutTransition.play();
    }
    private void slideIn(VBox vbox) {
        TranslateTransition slideInTransition = new TranslateTransition(Duration.millis(300), vbox);
        slideInTransition.setToY(0);
        slideInTransition.play();
    }
    private void slideOut2(ScrollPane scrollPane)
    {
        TranslateTransition slideOutTransition = new TranslateTransition(Duration.millis(300), scrollPane);
        slideOutTransition.setToY(2000);
        slideOutTransition.play();
    }
    private void slideIn2(ScrollPane scrollPane) {
        TranslateTransition slideInTransition = new TranslateTransition(Duration.millis(300), scrollPane);
        slideInTransition.setToY(0);
        slideInTransition.play();
    }
    public void showAllUsers2(List<User> users) throws IOException {
        tableUser2.getChildren().clear();
        for (User user : users) {
            HBox userCard = new HBox(10);
            Label nameLabel = new Label(user.getNom());
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-family: 'Franklin Gothic Medium Cond'; -fx-font-size: 15pt;");
            Label PrenomLabel = new Label(user.getPrenom());
            PrenomLabel.setStyle("-fx-font-weight: bold; -fx-font-family: 'Franklin Gothic Medium Cond'; -fx-font-size: 15pt;");
            Label countryLabel = new Label(user.getPays());
            countryLabel.setStyle("-fx-font-family:'Franklin Gothic Medium Cond'; -fx-font-size: 12pt;");
            HBox nameAndPrenom = new HBox(10);
            nameAndPrenom.getChildren().addAll(nameLabel,PrenomLabel);
            String SupprimerPath = "C:\\Users\\User\\IdeaProjects\\ProjectHOpe\\src\\main\\resources\\assets\\supprimer2.png";
            Image imageSupprimer = new Image(new FileInputStream(SupprimerPath));
            ImageView imageViewSupprimer = new ImageView(imageSupprimer);
            imageViewSupprimer.setFitHeight(70);
            imageViewSupprimer.setFitWidth(70);
            imageViewSupprimer.setOnMouseEntered(event -> {
                event.consume();
                imageViewSupprimer.setOpacity(0.7); // Reduce opacity on hover
                imageViewSupprimer.setScaleX(1.05); // Increase scale on hover
                imageViewSupprimer.setScaleY(1.05);
            });

            imageViewSupprimer.setOnMouseExited(event -> {
                event.consume();
                imageViewSupprimer.setOpacity(1.0); // Reset opacity
                imageViewSupprimer.setScaleX(1.0); // Reset scale
                imageViewSupprimer.setScaleY(1.0);
            });
            imageViewSupprimer.setOnMouseClicked(event -> {
                event.consume();
                deleteUserAndUpdateUI(user);
                System.out.println("Button Supprimer clicked!");

            });
            String EditPath = "C:\\Users\\User\\IdeaProjects\\ProjectHOpe\\src\\main\\resources\\assets\\crayon.png";
            Image imageEdit = new Image(new FileInputStream(EditPath));
            ImageView imageViewEdit = new ImageView(imageEdit);
            imageViewEdit.setFitHeight(70);
            imageViewEdit.setFitWidth(70);
            imageViewEdit.setOnMouseEntered(event -> {
                event.consume();
                imageViewEdit.setOpacity(0.7); // Reduce opacity on hover
                imageViewEdit.setScaleX(1.05); // Increase scale on hover
                imageViewEdit.setScaleY(1.05);
            });

            imageViewEdit.setOnMouseExited(event -> {
                event.consume();
                imageViewEdit.setOpacity(1.0); // Reset opacity
                imageViewEdit.setScaleX(1.0); // Reset scale
                imageViewEdit.setScaleY(1.0);
            });
            imageViewEdit.setOnMouseClicked(event -> {
                event.consume();
                nom_modif.setText(user.getNom());
                prenom_modif.setText(user.getPrenom());
                phone_modif.setText(user.getPhoneNumber());
                email_modif.setText(user.getEmail());
                description_modif.setText(user.getDescriptionUser());
                country_modif.setValue(user.getPays());
                userModify = user;
                String EditPathProfile = "C:\\Users\\User\\IdeaProjects\\ProjectHOpe\\src\\main\\resources\\assets\\"+user.getImageName();
                Image image = new Image(new File(EditPathProfile).toURI().toString());
                imageProfile11.setImage(image);
                slideOut2(scroll);
                slideIn(modifForm1);
                System.out.println("Button Edit clicked!");
            });
            HBox buttons = new HBox(10);
            buttons.setStyle("-fx-padding: 50px 0 0 0;");
            buttons.getChildren().addAll(imageViewEdit,imageViewSupprimer);
            String imagePath = "C:\\Users\\User\\IdeaProjects\\ProjectHOpe\\src\\main\\resources\\assets\\" + user.getImageName();
            Image image = new Image(newInputStream(Path.of(imagePath)));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            userCard.setCursor(Cursor.HAND);
            userCard.setOnMouseEntered(e -> userCard.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 50px;-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0);"));
            userCard.setOnMouseExited(e -> userCard.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 50px;"));
            VBox userName = new VBox(10);
            userCard.setPrefWidth(500);
            userCard.setPrefHeight(300);
            userCard.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 100px;");
            userCard.setAlignment(Pos.CENTER);
            userName.getChildren().addAll(nameAndPrenom);
            VBox namePrenomandCountry = new VBox(10);
            namePrenomandCountry.setSpacing(10);
            namePrenomandCountry.getChildren().addAll(userName,countryLabel,buttons);
            namePrenomandCountry.setStyle("-fx-padding: 100px 0 0 0;");
            userCard.setSpacing(50);
            // Add more user details here
            userCard.getChildren().addAll(imageView,namePrenomandCountry);
            userCard.setOnMouseClicked(event -> {
                slideOut2(scroll);
                slideIn(Details);
                nomDetails.setText(user.getNom());
                prenomDetails.setText(user.getPrenom());
                emailDetails.setText(user.getEmail());
                PhoneDetails.setText(user.getPhoneNumber());
                createdDetails.setText(user.getCreatedAt().toString());
                if (user.isVerified())
                {
                    Status.setText("Verified");
                }
                else
                    Status.setText("Not Verified");
                descriptionDetails.setText(user.getDescriptionUser());
                countryDetails.setText(user.getPays());
                String imagePath2 = "C:\\Users\\User\\IdeaProjects\\ProjectHOpe\\src\\main\\resources\\assets\\" + user.getImageName();
                try {
                    Image image2 = new Image(newInputStream(Path.of(imagePath2)));
                    imageProfile1.setImage(image2);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
            tableUser2.getChildren().add(userCard);

        }
    }
    public void deleteUserAndUpdateUI(User user) {
        try {
            serviceUser.supprimer(user);
            showAllUsers2(serviceUser.getAll()); // Refresh UI after deletion
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    // Method to show user details
    @FXML
    private void handlebuttonAddAdmin()
    {
        nom_add.clear();
        prenom_add.clear();
        password_add.clear();
        comfirm_pass_add.clear();
        phone_number_add.clear();
        description.clear();
        email_add.clear();
        combo.setValue(null);
        slideOut2(scroll);
        slideOut(modifForm1);
        isListUsersVisible = false;
        slideIn(AddForm);
        isAddFormVisible = true;
        slideOut(Details);
        slideOut(statistique);
    }
    @FXML
    private void handlebuttonStat() throws SQLException {
        slideIn(statistique);
        slideOut(AddForm);
        slideOut(modifForm1);
        slideOut2(scroll);
        slideOut(Details);
        stati();
        populateBarChart();
    }
    @FXML
    private void handlebuttonListAdmin()
    {
        slideIn2(scroll);
        isListUsersVisible = true;
        slideOut(AddForm);
        slideOut(modifForm1);
        isAddFormVisible = false;
        slideOut(Details);
        slideOut(statistique);
    }
    @FXML
    private void handleOpenButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("ImageFile","*.png","*.jpg","*.gif");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null)
        {
            Image image = new Image(file.toURI().toString());
            imageProfile.setImage(image);
            imageProfile11.setImage(image);
        }
    }
    void AddAdminFunction(User user)
    {
        String password = password_add.getText();
        String confirmPassword = comfirm_pass_add.getText();
        String nom = nom_add.getText();
        String prenom = prenom_add.getText();
        String phoneNumber = phone_number_add.getText();
        String email = email_add.getText();
        // Insert user into the database
        try {
            if (nom.isEmpty()) {
                nom_error_add.setText("Name must be filled");
            } else if (!pattern_name.matcher(nom).matches()) {
                nom_error_add.setText("First name must contain at least 3 charactes (only characters)");
            } else {
                nom_error_add.setText("");
            }
            if(prenom.isEmpty()) {
                prenom_error_add.setText("Last name must be filled");
            }
            else if (!pattern_name.matcher(prenom).matches()) {
                prenom_error_add.setText("Last name must contain at least 3 charactes (only characters)");
            } else {
                prenom_error_add.setText("");
            }
            if(password.isEmpty()) {
                password_error_add.setText("Password must be filled");
            } else if (!pattern_password.matcher(password).matches()) {
                password_error_add.setText("Weak password,It must containt at lest uppercase,lowercase,digit,special charactere:@$!%*?&");
            }
            else {
                password_error_add.setText("");
            }
            if(email.isEmpty()) {
                email_error_add.setText("email must be filled");
            }else if (!pattern_email.matcher(email).matches())
            {
                email_error_add.setText("email is not valid : must like this exemple@exemple.exemple");
            }else {
                email_error_add.setText("");
            }
            if (phoneNumber.isEmpty()) {
                phone_number_error_add.setText("Phone number must be filled");
            } else if (!pattern_phone.matcher(phoneNumber).matches()) {
                phone_number_error_add.setText("Phone number must conatin only 8 digits");
            }
            else{
                phone_number_error_add.setText("");
            }
            if(confirmPassword.isEmpty()) {
                conf_password_error_add.setText("Comfirm password must be filled");
            } else if (!password.equals(confirmPassword)) {
                conf_password_error_add.setText("Password and Comfirm Password is not match");
            }
            else {
                conf_password_error_add.setText("");
            }
            if (!nom.isEmpty() && !prenom.isEmpty() && !password.isEmpty() && !email.isEmpty() && !phoneNumber.isEmpty() &&
                    !confirmPassword.isEmpty() && pattern_name.matcher(nom).matches() && pattern_name.matcher(prenom).matches() && pattern_password.matcher(password).matches() &&
                    password.equals(confirmPassword) && pattern_email.matcher(email).matches() && pattern_phone.matcher(phoneNumber).matches()) {
                int rowsInserted = serviceUser.ajouterAdmin(user);
                if (rowsInserted > 0) {
                    copyImageToFolder(user);
                    System.out.println("Admin added  successfully!");
                    slideOut(AddForm);
                    slideIn2(scroll);
                    showAllUsers2(serviceUser.getAll());
                }
                else {
                    System.out.println("Failed to add admin.");
                }
            }
        } catch(SQLException e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "An exception occurred", e);
            System.out.println("An error occurred while signing up.");
        } catch (
                NoSuchAlgorithmException | IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    void ModifyAdminFunction(User user, User userModify) throws RuntimeException {
        String nom = nom_modif.getText();
        String prenom = prenom_modif.getText();
        String phoneNumber = phone_modif.getText();
        String email = email_modif.getText();
        // Insert user into the database
        try {
            if (nom.isEmpty()) {
                nom_error_modif.setText("Name must be filled");
            } else if (!pattern_name.matcher(nom).matches()) {
                nom_error_modif.setText("First name must contain at least 3 charactes (only characters)");
            } else {
                nom_error_modif.setText("");
            }
            if(prenom.isEmpty()) {
                prenom_error_modif.setText("Last name must be filled");
            }
            else if (!pattern_name.matcher(prenom).matches()) {
                prenom_error_modif.setText("Last name must contain at least 3 charactes (only characters)");
            } else {
                prenom_error_modif.setText("");
            }
            if(email.isEmpty()) {
                email_error_modif.setText("email must be filled");
            }else if (!pattern_email.matcher(email).matches())
            {
                email_error_modif.setText("email is not valid : must like this exemple@exemple.exemple");
            }else {
                email_error_modif.setText("");
            }
            if (phoneNumber.isEmpty()) {
                phone_number_error_modif.setText("Phone number must be filled");
            } else if (!pattern_phone.matcher(phoneNumber).matches()) {
                phone_number_error_modif.setText("Phone number must conatin only 8 digits");
            }
            else{
                phone_number_error_modif.setText("");
            }

            if (!nom.isEmpty() && !prenom.isEmpty()  && !email.isEmpty() && !phoneNumber.isEmpty()
                    && pattern_name.matcher(nom).matches() && pattern_name.matcher(prenom).matches()
                    && pattern_email.matcher(email).matches() && pattern_phone.matcher(phoneNumber).matches()) {
                int rowsInserted = serviceUser.modifer(user,userModify);
                if (rowsInserted > 0) {
                    copyImageToFolder(user);
                    System.out.println("Admin modified  successfully!");
                    slideOut(modifForm1);
                    slideIn2(scroll);
                    showAllUsers2(serviceUser.getAll());
                }
                else {
                    System.out.println("Failed to modifiy admin.");
                }
            }
        } catch(SQLException | FileNotFoundException e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "An exception occurred", e);
            System.out.println("An error occurred while signing up.");
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void copyImageToFolder(User user) throws IOException, URISyntaxException {
        // Convert the URL to a URI first
        URI uri = new URI(imageProfile.getImage().getUrl());

        // Then convert the URI to a Path
        Path sourcePath = Paths.get(uri);

        // Define the destination Path
        Path destinationPath = Paths.get("C:\\Users\\User\\IdeaProjects\\ProjectHOpe\\src\\main\\resources\\assets\\", user.getImageName());
        // Copy the file to the destination directory
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
    @FXML
    public void InsertAdminButton() {
        User user=getTextfieldAdd();
        AddAdminFunction(user);
    }
    private User getTextfieldAdd() {
        String password = password_add.getText();
        String description0 = description.getText();
        String pays = combo.getValue();
        String nom = nom_add.getText();
        String prenom = prenom_add.getText();
        String phoneNumber = phone_number_add.getText();
        String email = email_add.getText();
        String[] roles = {"ROLE_USER","ROLE_ADMIN"};
        JSONArray rolesArray = new JSONArray(roles);
        String rolesJson = rolesArray.toString();
        LocalDateTime createdAt = LocalDateTime.now(); // Current timestamp
        File file2 =new File(imageProfile.getImage().getUrl());
        return new User(email,nom,prenom,password,phoneNumber,rolesJson,false,createdAt,pays,description0,file2.getName());
    }
    private User getTextfieldModif() {
        String description0 = description_modif.getText();
        String pays = country_modif.getValue();
        String nom = nom_modif.getText();
        String prenom = prenom_modif.getText();
        String phoneNumber = phone_modif.getText();
        String email = email_modif.getText();
        String[] roles = {"ROLE_USER","ROLE_ADMIN"};
        JSONArray rolesArray = new JSONArray(roles);
        String rolesJson = rolesArray.toString();
        LocalDateTime createdAt = LocalDateTime.now(); // Current timestamp
        File file2 =new File(imageProfile11.getImage().getUrl());
        return new User(email,nom,prenom,phoneNumber,rolesJson,false,createdAt,pays,description0,file2.getName());

    }
    @FXML
    private void ModifyAdminButton()
    {
        User user = getTextfieldModif();
        System.out.println(userModify);
        ModifyAdminFunction(user,this.userModify);
    }
    @FXML
    private void logoutButtom() throws IOException {
        redirectToLogin();
        SessionManager.endSession();
        SessionUtilisateur.arreterSession();
        System.out.println("Logout est faite par success");
    }
    private void redirectToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/login.fxml"));
        stage.getScene().setRoot(fxmlLoader.load());
        UserController userController = fxmlLoader.getController();
        userController.setStage(stage);
        stage.setTitle("Login");
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void stati() throws SQLException {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ResultSet resultSet = serviceUser.StateNbrPays();
        while (resultSet.next()) {
            String pays = resultSet.getString("pays");
            long nombreUtilisateurs = resultSet.getLong("nombre_utilisateurs");
            pieChartData.add(new PieChart.Data(pays, nombreUtilisateurs));
        }
        NbrPays.setData(pieChartData);
    }
    public void populateBarChart() throws SQLException {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Users Created Per Month");

        Map<String, Integer> data = serviceUser.getUserStatistics(); // Call the method that retrieves data from the database
        data.forEach((month, numberOfUsers) -> System.out.println(month + ": " + numberOfUsers));
        data.forEach((month, count) -> {
            XYChart.Data<String, Integer> chartData = new XYChart.Data<>(month, count);
            series.getData().add(chartData);
        });
        BarMonth.getData().clear(); // Clear previous data if necessary
        BarMonth.getData().add(series);
    }
    @FXML
    public void searchUser() throws IOException {
            System.out.println(search.getText());
            tableUser2.getChildren().clear();
            showAllUsers2(serviceUser.SearchByNomOrPrenom(search.getText()));
    }
}
