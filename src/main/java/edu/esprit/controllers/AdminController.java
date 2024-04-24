package edu.esprit.controllers;
import edu.esprit.entities.SessionUtilisateur;
import edu.esprit.entities.User;
import edu.esprit.HelloApplication;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

import static java.nio.file.Files.newInputStream;


public class AdminController {
    private final Connection connection = connexion.getInstance().getCnx();
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
    public ComboBox country_modif;
    private Stage stage;

    @FXML
    private void initialize() throws IOException {
        AddForm.setTranslateY(2000);
        Details.setTranslateY(2000);
        modifForm1.setTranslateY(2000);
        showAllUsers2();
        ObservableList<String> items = FXCollections.observableArrayList(
                "Tunisia",
                "United state",
                "Spain",
                "England",
                "Belguim"
        );
        combo.setItems(items);
        emailprofile.setText(SessionUtilisateur.getUtilisateurActuel().getEmail());
        String EditPathProfile = "C:\\Users\\User\\Desktop\\projetJava\\PidevJava\\src\\main\\resources\\assets\\"+SessionUtilisateur.getUtilisateurActuel().getImageName();
        Image image = new Image(new File(EditPathProfile).toURI().toString());
        profileImage.setImage(image);
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
    public ObservableList<User> getUserList() {

        ObservableList<User> UserList = FXCollections.observableArrayList();
        try {
            String query2 = "SELECT * FROM  user ";
            PreparedStatement smt = connection.prepareStatement(query2);
            User user;
            ResultSet resultSet = smt.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("nom"), resultSet.getString("prenom"),
                        resultSet.getString("password"), resultSet.getString("phone_number"), resultSet.getString("roles"), resultSet.getBoolean("is_verified"), resultSet.getTimestamp("created_at").toLocalDateTime(), resultSet.getString("pays"), resultSet.getString("description_user"),resultSet.getString("image_name"));
                UserList.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return UserList;
    }
    public void showAllUsers2() throws IOException {
        List<User> users = getUserList();
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
            String SupprimerPath = "C:\\Users\\User\\Desktop\\projetJava\\PidevJava\\src\\main\\resources\\assets\\supprimer2.png";
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
            String EditPath = "C:\\Users\\User\\Desktop\\projetJava\\PidevJava\\src\\main\\resources\\assets\\crayon.png";
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
                String EditPathProfile = "C:\\Users\\User\\Desktop\\projetJava\\PidevJava\\src\\main\\resources\\assets\\"+user.getImageName();
                Image image = new Image(new File(EditPathProfile).toURI().toString());
                imageProfile11.setImage(image);
                slideOut2(scroll);
                slideIn(modifForm1);
                System.out.println("Button Edit clicked!");
            });
            HBox buttons = new HBox(10);
            buttons.setStyle("-fx-padding: 50px 0 0 0;");
            buttons.getChildren().addAll(imageViewEdit,imageViewSupprimer);
            String imagePath = "C:\\Users\\User\\Desktop\\projetJava\\PidevJava\\src\\main\\resources\\assets\\" + user.getImageName();
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
                String imagePath2 = "C:\\Users\\User\\Desktop\\projetJava\\PidevJava\\src\\main\\resources\\assets\\" + user.getImageName();
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
            deleteUser(user);
            showAllUsers2(); // Refresh UI after deletion
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    // Method to show user details
    private void showUserDetails(User user) {
        // Implement your logic to display user details here
        // You can show the details in a popup window, dialog, or any other UI component
        System.out.println("User details: " + user);
    }
    private void deleteUser(User user) throws SQLException {
        String query="delete from user where email=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getEmail());
        int row_delete = statement.executeUpdate();
        if(row_delete>0)
        {
            System.out.println("User deleted  succesufully");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WeTransfet :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Cannot delete User !!");
            alert.showAndWait();
        }
    }
    @FXML
    private void handlebuttonAddAdmin(ActionEvent event)
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
    }
    @FXML
    private void handlebuttonListAdmin(ActionEvent event)
    {
        slideIn2(scroll);
        isListUsersVisible = true;
        slideOut(AddForm);
        slideOut(modifForm1);
        isAddFormVisible = false;
        slideOut(Details);
    }
    @FXML
    private void handleOpenButtonAction(ActionEvent event) throws MalformedURLException {
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
        String[] roles = {"ROLE_USER","ROLE_ADMIN"};
        JSONArray rolesArray = new JSONArray(roles);
        String rolesJson = rolesArray.toString();
        LocalDateTime createdAt = LocalDateTime.now(); // Current timestamp
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

                String query = "INSERT INTO user (nom, prenom, phone_number, email, password, roles, created_at,is_verified,pays,description_user,image_name) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, user.getNom());
                statement.setString(2, user.getPrenom());
                statement.setString(3, user.getPhoneNumber());
                statement.setString(4, user.getEmail());
                statement.setString(5, hashPassword(user.getPassword()));
                statement.setString(6, rolesJson);
                statement.setTimestamp(7, Timestamp.valueOf(createdAt)); // Set the created_at parameter
                statement.setBoolean(8, false);
                statement.setString(9, user.getPays());
                statement.setString(10,user.getDescriptionUser());
                statement.setString(11, user.getImageName());
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    copyImageToFolder(user);
                    System.out.println("Admin added  successfully!");
                    slideOut(AddForm);
                    slideIn2(scroll);
                    showAllUsers2();
                }
                else {
                    System.out.println("Failed to add admin.");
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
            System.out.println("An error occurred while signing up.");
        } catch (
                NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    void ModifyAdminFunction(User user, User userModify) throws RuntimeException {
        String nom = nom_modif.getText();
        String prenom = prenom_modif.getText();
        String phoneNumber = phone_modif.getText();
        String email = email_modif.getText();
        String[] roles = {"ROLE_USER","ROLE_ADMIN"};
        JSONArray rolesArray = new JSONArray(roles);
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

                String query = "UPDATE user SET email=?,nom=?,prenom=?,phone_number=?,pays=?,description_user=?,image_name=? WHERE email =?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(2, user.getNom());
                statement.setString(3, user.getPrenom());
                statement.setString(4, user.getPhoneNumber());
                statement.setString(1, user.getEmail());
                statement.setString(5, user.getPays());
                statement.setString(6,user.getDescriptionUser());
                statement.setString(7, user.getImageName());
                statement.setString(8, userModify.getEmail());
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    copyImageToFolder(user);
                    System.out.println("Admin modified  successfully!");
                    slideOut(modifForm1);
                    slideIn2(scroll);
                    showAllUsers2();
                }
                else {
                    System.out.println("Failed to modifiy admin.");
                }
            }
        } catch(SQLException | FileNotFoundException e){
            e.printStackTrace();
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
        Path destinationPath = Paths.get("C:\\Users\\User\\Desktop\\projetJava\\PidevJava\\src\\main\\resources\\assets\\", user.getImageName());
        // Copy the file to the destination directory
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
    @FXML
    public void InsertAdminButton(ActionEvent event) {
        User user=getTextfieldAdd();
        AddAdminFunction(user);
    }
    private User getTextfieldAdd() {
        String password = password_add.getText();
        String description0 = description.getText();
        String pays = combo.getValue();
        String confirmPassword = comfirm_pass_add.getText();
        String nom = nom_add.getText();
        String prenom = prenom_add.getText();
        String phoneNumber = phone_number_add.getText();
        String email = email_add.getText();
        String[] roles = {"ROLE_USER","ROLE_ADMIN"};
        JSONArray rolesArray = new JSONArray(roles);
        String rolesJson = rolesArray.toString();
        LocalDateTime createdAt = LocalDateTime.now(); // Current timestamp
        File file2 =new File(imageProfile.getImage().getUrl());
        User user = new User(email,nom,prenom,password,phoneNumber,rolesJson,false,createdAt,pays,description0,file2.getName());
        return user;
        //api key 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
    }
    private User getTextfieldModif() {
        String description0 = description_modif.getText();
        String pays = (String) country_modif.getValue();
        String nom = nom_modif.getText();
        String prenom = prenom_modif.getText();
        String phoneNumber = phone_modif.getText();
        String email = email_modif.getText();
        String[] roles = {"ROLE_USER","ROLE_ADMIN"};
        JSONArray rolesArray = new JSONArray(roles);
        String rolesJson = rolesArray.toString();
        LocalDateTime createdAt = LocalDateTime.now(); // Current timestamp
        File file2 =new File(imageProfile11.getImage().getUrl());
        User user = new User(email,nom,prenom,phoneNumber,rolesJson,false,createdAt,pays,description0,file2.getName());
        return user;
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
        SessionUtilisateur.arreterSession();
        System.out.println("Logout est faite par success");
    }
    private void redirectToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/login.fxml"));
        stage.getScene().setRoot(fxmlLoader.load());
        stage.setTitle("Login");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
