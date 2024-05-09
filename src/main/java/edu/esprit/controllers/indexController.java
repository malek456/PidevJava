package edu.esprit.controllers;

import edu.esprit.entities.SessionManager;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import edu.esprit.tests.HelloApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.io.*;
import java.net.DatagramPacket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.esprit.controllers.ClientThread.socket;
import static edu.esprit.controllers.UserController.*;

public class indexController {
    @FXML
    private VBox generator;
    @FXML
    private ImageView ImagePython;
    @FXML
    private TextField textInput;
    @FXML
    private PasswordField ComfirmPass;
    @FXML
    private PasswordField NewPass;
    @FXML
    private PasswordField oldPass;
    public User userModify= new User();
    @FXML
    private VBox EditProfileSessionVbox;
    @FXML
    private VBox changePasswordVbox;

    @FXML
    private ImageView ProfileFrontSession;


    @FXML
    private ComboBox<String> country_modif_session;

    @FXML
    private TextArea description_modif_session;

    @FXML
    private Label emailFrontSession;

    @FXML
    private Text email_error_modifP;
    @FXML
    private Text nom_error_modifP;

    @FXML
    private TextField email_modif_session;

    @FXML
    private ImageView imageSess;

    @FXML
    private TextField nom_modif_session;

    @FXML
    private TextField phone_modif_session;

    @FXML
    private Text phone_number_error_modifP;

    @FXML
    private Text prenom_error_modifP;

    @FXML
    private TextField prenom_modif_session;
    public final ServiceUser serviceUser = new ServiceUser();

    @FXML
    private void initialize() {
        emailFrontSession.setText(SessionManager.getUserEmail());
        InputStream stream = getClass().getResourceAsStream("/assets/"+SessionManager.getUserImage());
        Image image = new Image(stream);
        ProfileFrontSession.setImage(image);
        imageSess.setImage(image);
        User userSession = serviceUser.getUserByEmail(SessionManager.getUserEmail());
        nom_modif_session.setText(userSession.getNom());
        prenom_modif_session.setText(userSession.getPrenom());
        email_modif_session.setText(userSession.getEmail());
        phone_modif_session.setText(userSession.getPhoneNumber());
        description_modif_session.setText(userSession.getDescriptionUser());
        userModify=userSession;
        UserController.slideOut(changePasswordVbox);
        UserController.slideOut(generator);
        InputStream stream2 = getClass().getResourceAsStream("/assets/image.jpg");
        Image image2 = new Image(stream2);
        ImagePython.setImage(image2);
    }
    void ModifyProfileFunction(User user, User userModify) throws RuntimeException {
        String nom = nom_modif_session.getText();
        String prenom = prenom_modif_session.getText();
        String phoneNumber = phone_modif_session.getText();
        String email = email_modif_session.getText();
        // Insert user into the database
        try {
            if (nom.isEmpty()) {
                nom_error_modifP.setText("Name must be filled");
            } else if (!pattern_name.matcher(nom).matches()) {
                nom_error_modifP.setText("First name must contain at least 3 charactes (only characters)");
            } else {
                nom_error_modifP.setText("");
            }
            if(prenom.isEmpty()) {
                prenom_error_modifP.setText("Last name must be filled");
            }
            else if (!pattern_name.matcher(prenom).matches()) {
                prenom_error_modifP.setText("Last name must contain at least 3 charactes (only characters)");
            } else {
                prenom_error_modifP.setText("");
            }
            if(email.isEmpty()) {
                email_error_modifP.setText("email must be filled");
            }else if (!pattern_email.matcher(email).matches())
            {
                email_error_modifP.setText("email is not valid : must like this exemple@exemple.exemple");
            }else {
                email_error_modifP.setText("");
            }
            if (phoneNumber.isEmpty()) {
                phone_number_error_modifP.setText("Phone number must be filled");
            } else if (!pattern_phone.matcher(phoneNumber).matches()) {
                phone_number_error_modifP.setText("Phone number must conatin only 8 digits");
            }
            else{
                phone_number_error_modifP.setText("");
            }

            if (!nom.isEmpty() && !prenom.isEmpty()  && !email.isEmpty() && !phoneNumber.isEmpty()
                    && pattern_name.matcher(nom).matches() && pattern_name.matcher(prenom).matches()
                    && pattern_email.matcher(email).matches() && pattern_phone.matcher(phoneNumber).matches()) {
                int rowsInserted = serviceUser.modifer(user,userModify);
                if (rowsInserted > 0) {
                    copyImageToFolder(user);
                    System.out.println("Profile modified  successfully!");
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
    private Stage stage;
    @FXML
    private void OpenCommunity() throws IOException {
        ClientThread clientThread = new ClientThread(ChatClient.socket, ChatClient.messageArea);
        clientThread.start();
        // send initialization message to the server
        byte[] uuid = ("init;" + ChatClient.identifier).getBytes();
        DatagramPacket initialize = new DatagramPacket(uuid, uuid.length, ChatClient.address, ChatClient.SERVER_PORT);
        socket.send(initialize);
        ChatClient chatClient = new ChatClient();
        chatClient.start(new Stage());
    }
    @FXML
    private void logout() throws IOException {
        SessionManager.endSession();
        redirectToLogin();
    }
    private void redirectToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/login.fxml"));
        stage.getScene().setRoot(fxmlLoader.load());
        UserController userController = fxmlLoader.getController();
        userController.setStage(stage);
        stage.setTitle("Login");
    }

    public void setStage(Stage stage) {
        this.stage =stage;
    }
    @FXML
    public void GotoEditProfile()
    {
       UserController.slideIn(EditProfileSessionVbox);
        UserController.slideOut(changePasswordVbox);
        UserController.slideOut(generator);
       System.out.println("vbx cliqued");
    }
    @FXML
    public void GotoChangePassword()
    {
        UserController.slideOut(EditProfileSessionVbox);
        UserController.slideIn(changePasswordVbox);
        UserController.slideOut(generator);
        System.out.println("vbx cliqued 2 ");
    }
    public void copyImageToFolder(User user) throws IOException, URISyntaxException {
        // Convert the URL to a URI first
        URI uri = new URI(imageSess.getImage().getUrl());
        // Then convert the URI to a Path
        Path sourcePath = Paths.get(uri);
        // Define the destination Path
        Path destinationPath = Paths.get(getClass().getResource("/assets/" + user.getImageName()).toURI());
        // Copy the file to the destination directory
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
    @FXML
    private void ModifyProfileButton()
    {
        //SessionManager.setUserImage(imageSess.getImage().getUrl());
        SessionManager.setUserEmail(email_modif_session.getText());
        User user = getTextfieldModif();
        System.out.println(userModify);
        ModifyProfileFunction(user,this.userModify);
    }

    private User getTextfieldModif() {
        String description0 = description_modif_session.getText();
        String pays = country_modif_session.getValue();
        String nom = nom_modif_session.getText();
        String prenom = prenom_modif_session.getText();
        String phoneNumber = phone_modif_session.getText();
        String email = email_modif_session.getText();
        String[] roles = {"ROLE_USER","ROLE_ADMIN"};
        JSONArray rolesArray = new JSONArray(roles);
        String rolesJson = rolesArray.toString();
        LocalDateTime createdAt = LocalDateTime.now(); // Current timestamp
        File file2 =new File(imageSess.getImage().getUrl());
        return new User(email,nom,prenom,phoneNumber,rolesJson,false,createdAt,pays,description0,file2.getName());
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
            imageSess.setImage(image);
            ProfileFrontSession.setImage(image);
        }
    }
    private void ChangePassword() throws NoSuchAlgorithmException, SQLException {
        String Old = oldPass.getText();
        System.out.println(Old);
        System.out.println(userModify.getPassword());
        String newP = NewPass.getText();
        String ComfirmP =ComfirmPass.getText();
        userModify = serviceUser.getUserByEmail(SessionManager.getUserEmail());
        if(!Objects.equals(hashPassword(Old), userModify.getPassword()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WeTransfet :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Old Password Incorrect !!");
            alert.showAndWait();
        }
        else if(newP.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WeTransfet :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("New password must not empty !!");
            alert.showAndWait();
        } else if (!newP.equals(ComfirmP)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WeTransfet :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Password and Comfirm password not match !!");
            alert.showAndWait();
        }
        else {
            int rowsInserted = serviceUser.modiferPass(NewPass.getText(),SessionManager.getUserEmail());
            if(rowsInserted>0)
            {
                System.out.println("Password changed with sucess");
                oldPass.clear();
                ComfirmPass.clear();
                NewPass.clear();
            }
        }
    }
    @FXML
    private void ChangePassButton() throws NoSuchAlgorithmException, SQLException {
        ChangePassword();
    }
    @FXML
    private void GenerateImageButton() {
        runPythonScript();
        /*InputStream stream = getClass().getResourceAsStream("/assets/image.jpg");
        Image image = new Image(stream);
        ImagePython.setImage(image);*/
    }

    private void runPythonScript() {
        try {
            String descriptionImage = textInput.getText();
            String imagePath = "src/main/resources/assets/image.jpg";
            String command = "python src/main/java/edu/esprit/GenerateImage.py \"" + descriptionImage + "\"";
            // Execute the Python script and wait for it to finish
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            // Load the image into the ImageView
            Image image = new Image(new FileInputStream(imagePath));
            ImagePython.setImage(image);

            System.out.println("Python script executed successfully.");
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occurred while executing the Python script.");
            e.printStackTrace();
        }
    }

    @FXML
    private void ShowGenerator()
    {
        slideIn(generator);
        slideOut(changePasswordVbox);
        slideOut(EditProfileSessionVbox);
    }
}
