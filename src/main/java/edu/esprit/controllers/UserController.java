package edu.esprit.controllers;
import edu.esprit.entities.User;
import edu.esprit.entities.SessionUtilisateur;
import edu.esprit.HelloApplication;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Properties;
import java.util.regex.Pattern;
public class UserController {
    private Stage stage;
    @FXML
    public Button loginButton;
    @FXML
    public Button aboutButton;
    @FXML
    public Button registerButton;
    @FXML
    public Button contact;
    @FXML
    public TextField nom_registre;
    @FXML
    public TextField prenom_registre;
    @FXML
    public TextField phone_number_reg;
    public TextField email_registre;
    @FXML
    public PasswordField password_reg;
    @FXML
    public PasswordField comfirm_pass;
    @FXML
    public JFXButton SignUpButton;
    @FXML
    public Text password_error;
    @FXML
    public Text phone_number_error;
    @FXML
    public Text email_error;
    @FXML
    public Text prenom_error;
    @FXML
    public Text nom_error;
    @FXML
    public Text conf_password_error;
    @FXML
    public VBox opt_verf;
    @FXML
    public TextField optField1;
    @FXML
    public TextField optField2;
    @FXML
    public TextField optField3;
    @FXML
    public TextField optField4;
    @FXML
    public TextField optField5;
    @FXML
    public JFXButton Submit_CODE_REST;
    @FXML
    public TextField optField51;
    @FXML
    public TextField optField41;
    @FXML
    public TextField optField31;
    @FXML
    public TextField optField21;
    @FXML
    public TextField optField11;
    @FXML
    public VBox code_verf1;
    @FXML
    public VBox EmailForget;
    @FXML
    public TextField EmailForgetPassword;
    @FXML
    public JFXButton CodeButton;
    @FXML
    public VBox RestForm;
    @FXML
    public PasswordField RestPassword;
    @FXML
    public JFXButton Submit_Button_Rest;
    @FXML
    public PasswordField RestComfirmPassword;
    @FXML
    public TextField textfield;
    @FXML
    private VBox contactus;
    @FXML
    private VBox AboutUS;
    @FXML
    private VBox loginForm;
    @FXML
    private VBox registrationForm;
    @FXML
    private TextField PasswordLogin;
    @FXML
    private TextField EmailLogin;
    public final String opt=generateOTP();
    private boolean isLoginFormVisible = true;
    private boolean isRegistrationFormVisible = false;
    private boolean isAboutUsVisible = false;
    private boolean isContactUsVisible = false;
    private final Connection connection = connexion.getInstance().getCnx();
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
    private final String random = generateOTP();
    @FXML
    private void initialize() {
            // Proceed with initialization logic
            registrationForm.setTranslateY(-1000);
            AboutUS.setTranslateY(-1000);
            contactus.setTranslateY(-1000);
            opt_verf.setTranslateY(-1000);
            code_verf1.setTranslateY(-1000);
            EmailForget.setTranslateY(-1000);
            RestForm.setTranslateY(-1000);
            textfield.setVisible(false);
            textfield.setManaged(false);
            textfield.textProperty().bindBidirectional(PasswordLogin.textProperty());
        }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        if (!isLoginFormVisible) {
            slideIn(loginForm);
            slideOut(registrationForm);
            slideOut(AboutUS);
            slideOut(contactus);
            slideOut(opt_verf);
            slideOut(code_verf1);
            slideOut(EmailForget);
            slideOut(RestForm);
            isLoginFormVisible = true;
            isRegistrationFormVisible = false;
            isAboutUsVisible = false;
            isContactUsVisible = false;
        }
    }

    @FXML
    private void handleRegisterButtonAction() {
        if (!isRegistrationFormVisible) {
            slideIn(registrationForm);
            slideOut(loginForm);
            slideOut(AboutUS);
            slideOut(contactus);
            slideOut(opt_verf);
            slideOut(code_verf1);
            slideOut(EmailForget);
            slideOut(RestForm);
            isAboutUsVisible = false;
            isLoginFormVisible = false;
            isRegistrationFormVisible = true;
            isContactUsVisible = false;
        }
    }

    @FXML
    private void handleAboutButtonAction(ActionEvent event) {
        if (!isAboutUsVisible) {
            slideIn(AboutUS);
            slideOut(loginForm);
            slideOut(registrationForm);
            slideOut(contactus);
            slideOut(opt_verf);
            slideOut(code_verf1);
            slideOut(EmailForget);
            slideOut(RestForm);
            isLoginFormVisible = false;
            isRegistrationFormVisible = false;
            isAboutUsVisible = true;
            isContactUsVisible = false;
        }
    }
    @FXML
    private void handleContactButtonAction(ActionEvent event) {
        if (!isContactUsVisible) {
            slideIn(contactus);
            slideOut(loginForm);
            slideOut(registrationForm);
            slideOut(AboutUS);
            slideOut(opt_verf);
            slideOut(code_verf1);
            slideOut(EmailForget);
            slideOut(RestForm);
            isLoginFormVisible = false;
            isRegistrationFormVisible = false;
            isContactUsVisible = true;
            isAboutUsVisible = false;
        }
    }
    private void slideOut(VBox vbox)
    {
        TranslateTransition slideOutTransition = new TranslateTransition(Duration.millis(300), vbox);
        slideOutTransition.setToY(-1000);
        slideOutTransition.play();
    }
    private void slideIn(VBox vbox) {
        TranslateTransition slideInTransition = new TranslateTransition(Duration.millis(300), vbox);
        slideInTransition.setToY(0);
        slideInTransition.play();
    }

    public void signUp(User user) {
        // Get user input from text fields
        String password = password_reg.getText();
        String confirmPassword = comfirm_pass.getText();
        String nom = nom_registre.getText();
        String prenom = prenom_registre.getText();
        String phoneNumber = phone_number_reg.getText();
        String email = email_registre.getText();
        String[] roles = {"ROLE_USER"};
        JSONArray rolesArray = new JSONArray(roles);
        String rolesJson = rolesArray.toString();
        LocalDateTime createdAt = LocalDateTime.now(); // Current timestamp
        // Insert user into the database
        try {
            if (nom.isEmpty()) {
                nom_error.setText("Name must be filled");
            } else if (!pattern_name.matcher(nom).matches()) {
                nom_error.setText("First name must contain at least 3 charactes (only characters)");
            } else {
                nom_error.setText("");
            }
            if(prenom.isEmpty()) {
                prenom_error.setText("Last name must be filled");
            }
            else if (!pattern_name.matcher(prenom).matches()) {
                prenom_error.setText("Last name must contain at least 3 charactes (only characters)");
            } else {
                prenom_error.setText("");
            }
            if(password.isEmpty()) {
                password_error.setText("Password must be filled");
            } else if (!pattern_password.matcher(password).matches()) {
               password_error.setText("Weak password,It must containt at lest uppercase,lowercase,digit,special charactere:@$!%*?&");
            }
            else {
                password_error.setText("");
            }
            if(email.isEmpty()) {
                email_error.setText("email must be filled");
            }else if (!pattern_email.matcher(email).matches())
            {
                email_error.setText("email is not valid : must like this exemple@exemple.exemple");
            }else {
                email_error.setText("");
            }
            if (phoneNumber.isEmpty()) {
                phone_number_error.setText("Phone number must be filled");
            } else if (!pattern_phone.matcher(phoneNumber).matches()) {
                phone_number_error.setText("Phone number must conatin only 8 digits");
            }
            else{
                phone_number_error.setText("");
            }
            if(confirmPassword.isEmpty()) {
                conf_password_error.setText("Comfirm password must be filled");
            } else if (!password.equals(confirmPassword)) {
                conf_password_error.setText("Password and Comfirm Password is not match");
            }
            else {
                conf_password_error.setText("");
            }
            if (!nom.isEmpty() && !prenom.isEmpty() && !password.isEmpty() && !email.isEmpty() && !phoneNumber.isEmpty() &&
            !confirmPassword.isEmpty() && pattern_name.matcher(nom).matches() && pattern_name.matcher(prenom).matches() && pattern_password.matcher(password).matches() &&
            password.equals(confirmPassword) && pattern_email.matcher(email).matches() && pattern_phone.matcher(phoneNumber).matches()) {

                String query = "INSERT INTO user (nom, prenom, phone_number, email, password, roles, created_at,is_verified,image_name) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, user.getNom());
                statement.setString(2, user.getPrenom());
                statement.setString(3, user.getPhoneNumber());
                statement.setString(4, user.getEmail());
                statement.setString(5, hashPassword(user.getPassword()));
                statement.setString(6, rolesJson);
                statement.setTimestamp(7, Timestamp.valueOf(createdAt)); // Set the created_at parameter
                statement.setBoolean(8, false);
                statement.setString(9, "defaultProfile.png");
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("User signed up successfully!");
                    sendVerificationEmail(user.getEmail(),opt,"Email Verification");
                    slideOut(registrationForm);
                    slideIn(opt_verf);
                    isRegistrationFormVisible = false;
                }
                else {
                    System.out.println("Failed to sign up user.");
                }
            }
            } catch(SQLException e){
                e.printStackTrace();
                System.out.println("An error occurred while signing up.");
            } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
    @FXML
    private void handleSignUpButtonAction() {
        User user=getTextfieldReg();
        signUp(user);
    }

    public String generateOTP() {
        final String DIGITS = "0123456789";
        final SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            otp.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }

        return otp.toString();
    }
    public void sendVerificationEmail(String recipientEmail, String verificationCode,String header) {
        final String senderEmail = "aymenbenchhaaben@gmail.com"; // Change this
        final String senderPassword = "qowmgmopaiswnguu"; // Change this

        // Setup mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Change this
        properties.put("mail.smtp.port", "587"); // Change this

        // Get the Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(senderEmail));

            // Set To: header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Set Subject: header field
            message.setSubject(header);

            // Set the email content
            message.setText("Your verification code is: " + verificationCode);

            // Send message
            Transport.send(message);
            System.out.println("Verification email sent successfully to: " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleKeyTyped(KeyEvent event) {
        TextField currentTextField = (TextField) event.getSource();
        String character = event.getCharacter();
        // Vérifie si le caractère saisi est un chiffre
        if (character.matches("[0-9]")) {
            // Met à jour le texte actuel du champ de texte
            currentTextField.setText(character);
            // Récupère l'ID du champ de texte actuel
            String currentTextFieldId = currentTextField.getId();
            // Détermine le champ de texte suivant
            TextField nextTextField = getNextTextField(currentTextFieldId);

            // Si un champ de texte suivant existe, déplace le focus vers ce champ
            if (nextTextField != null) {
                nextTextField.requestFocus();
            }
        }
    }
        @FXML
        private void handleKeyTyped2(KeyEvent event) {
            TextField currentTextField = (TextField) event.getSource();
            String character = event.getCharacter();
            // Vérifie si le caractère saisi est un chiffre
            if (character.matches("[0-9]")) {
                // Met à jour le texte actuel du champ de texte
                currentTextField.setText(character);
                // Récupère l'ID du champ de texte actuel
                String currentTextFieldId = currentTextField.getId();
                // Détermine le champ de texteu suivant
                TextField nextTextField = getNextTextField2(currentTextFieldId);

                // Si un champ de texte suivant existe, déplace le focus vers ce champ
                if (nextTextField != null) {
                    nextTextField.requestFocus();
                }
            }

        // Consomme l'événement pour empêcher la saisie de plusieurs caractères
        event.consume();
    }
    private TextField getNextTextField(String currentTextFieldId) {
        return switch (currentTextFieldId) {
            case "optField1" -> optField2;
            case "optField2" -> optField3;
            case "optField3" -> optField4;
            case "optField4" -> optField5;
            default -> null; // Retourne null si le champ de texte actuel est le dernier
        };
    }
    private TextField getNextTextField2(String currentTextFieldId) {
        switch (currentTextFieldId) {
            case "optField11":
                return optField21;
            case "optField21":
                return optField31;
            case "optField31":
                return optField41;
            case "optField41":
                return optField51;
            default:
                return null; // Retourne null si le champ de texte actuel est le dernier
        }
    }
    private void handleSupprimerButtonAction() {
        TextField currentTextField = getCurrentTextField();

        // Clear the text of the current text field
        currentTextField.clear();

        // Determine the previous text field
        TextField previousTextField = getPreviousTextField(currentTextField.getId());

        // If a previous text field exists, move focus to it
        if (previousTextField != null) {
            previousTextField.requestFocus();
        }
    }
    private void handleSupprimerButtonAction2() {
        TextField currentTextField = getCurrentTextField2();

        // Clear the text of the current text field
        currentTextField.clear();

        // Determine the previous text field
        TextField previousTextField = getPreviousTextField2(currentTextField.getId());

        // If a previous text field exists, move focus to it
        if (previousTextField != null) {
            previousTextField.requestFocus();
        }
    }

    // Method to get the current text field
    private TextField getCurrentTextField() {
        if (optField1.isFocused()) {
            return optField1;
        } else if (optField2.isFocused()) {
            return optField2;
        } else if (optField3.isFocused()) {
            return optField3;
        } else if (optField4.isFocused()) {
            return optField4;
        } else {
            return optField5;
        }
    }
    private TextField getCurrentTextField2() {
        if (optField11.isFocused()) {
            return optField11;
        } else if (optField21.isFocused()) {
            return optField21;
        } else if (optField31.isFocused()) {
            return optField31;
        } else if (optField41.isFocused()) {
            return optField41;
        } else {
            return optField51;
        }
    }
    // Method to get the previous text field
    private TextField getPreviousTextField(String currentTextFieldId) {
        switch (currentTextFieldId) {
            case "optField1":
                return null; // There is no previous text field
            case "optField2":
                return optField1;
            case "optField3":
                return optField2;
            case "optField4":
                return optField3;
            case "optField5":
                return optField4;
            default:
                return null;
        }
    }
    private TextField getPreviousTextField2(String currentTextFieldId) {
        switch (currentTextFieldId) {
            case "optField11":
                return null; // There is no previous text field
            case "optField21":
                return optField11;
            case "optField31":
                return optField21;
            case "optField41":
                return optField31;
            case "optField51":
                return optField41;
            default:
                return null;
        }
    }
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if (keyCode == KeyCode.DELETE || keyCode == KeyCode.BACK_SPACE) {
            // Trigger the Supprimer button action
            handleSupprimerButtonAction();
        }
    }
    @FXML
    private void handleKeyPressed2(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if (keyCode == KeyCode.DELETE || keyCode == KeyCode.BACK_SPACE) {
            // Trigger the Supprimer button action
            handleSupprimerButtonAction2();
        }
    }
    @FXML
    private void handleCodeOptButton() throws SQLException {
        User user = getTextfieldReg();
        String CodeFromTextField=optField1.getText()+optField2.getText()+optField3.getText()+optField4.getText()+optField5.getText();
        if(opt.equals(CodeFromTextField)){
            String query = "UPDATE user SET is_verified = ? where email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBoolean(1, true);
            statement.setString(2, user.getEmail());
            int rowsModified = statement.executeUpdate();
            if (rowsModified > 0) {
                System.out.println("User verfified successefuly!");
            }
            else {
                System.out.println("Failed to verify user.");
            }
            SessionUtilisateur.demarrerSession(user);
            if (SessionUtilisateur.estSessionActive()) {
                User utilisateurActuel = SessionUtilisateur.getUtilisateurActuel();
                System.out.println("Session active pour : " + utilisateurActuel);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Travel Me :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Code OPT incorrect !!");
            alert.showAndWait();
        }
    }

    private User getTextfieldReg() {
        String password = password_reg.getText();
        String nom = nom_registre.getText();
        String prenom = prenom_registre.getText();
        String phoneNumber = phone_number_reg.getText();
        String email = email_registre.getText();
        String[] roles = {"ROLE_USER"};
        JSONArray rolesArray = new JSONArray(roles);
        String rolesJson = rolesArray.toString();
        LocalDateTime createdAt = LocalDateTime.now(); // Current timestamp
        return new User(email,nom,prenom,password,phoneNumber,rolesJson,false,createdAt);
    }
    public void login() throws SQLException, NoSuchAlgorithmException, IOException {
            String query = "select * from user where email=?  and password=?";
            PreparedStatement statement = connection.prepareStatement(query);
            String email = EmailLogin.getText();
            String password = PasswordLogin.getText();
            statement.setString(1, email);
            statement.setString(2,hashPassword(password));
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("WeTransfet :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Email or Password incorrect !!");
                alert.showAndWait();
            }
            else {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setNom(resultSet.getString("nom"));
                user.setPassword(resultSet.getString("password"));
                user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                user.setPrenom(resultSet.getString("prenom"));
                user.setDescriptionUser(resultSet.getString("description_user"));
                user.setPays(resultSet.getString("pays"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setGoogleId(resultSet.getString("google_id"));
                user.setRoles(resultSet.getString("roles"));
                user.setImageName(resultSet.getString("Image_name"));
                user.setVerified(resultSet.getBoolean("is_verified"));
                SessionUtilisateur.demarrerSession(user);
                if (SessionUtilisateur.estSessionActive()) {
                    System.out.println("User login succussefuly");
                    User utilisateurActuel = SessionUtilisateur.getUtilisateurActuel();
                    System.out.println("Session active pour : " + utilisateurActuel);
                    if (user.getRoles().contains("ROLE_ADMIN")) {
                        // Redirection vers le tableau de bord admin
                        redirectToAdminDashboard();
                    } else {
                        // Redirection vers le front-end
                        redirectToFrontend();
                    }
                }
            }
    }
    @FXML
    public void handleLoginButton() throws SQLException, NoSuchAlgorithmException, IOException {
        login();
    }
    private void redirectToAdminDashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/dashAdmin.fxml"));
        stage.getScene().setRoot(fxmlLoader.load());
        AdminController adminController = fxmlLoader.getController();
        adminController.setStage(stage);
        stage.setTitle("Dashboard");
    }

    private void redirectToFrontend() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/front.fxml"));
        stage.getScene().setRoot(fxmlLoader.load());
        stage.setTitle("Acceuil");
    }

    @FXML
    private void handleButtonforgetPassword()
    {
        slideOut(loginForm);
        slideIn(EmailForget);
        isLoginFormVisible = false;
    }
    @FXML
    private void sendCodeButton() throws SQLException {

        String email = EmailForgetPassword.getText();
        String query = "select * from user where email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1,email);
        ResultSet resultSet = statement.executeQuery();
        if(!resultSet.next())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WeTransfet :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Email Introuvable !!");
            alert.showAndWait();
        }
        else {
            sendVerificationEmail(email,random,"Rest Password");
            slideOut(EmailForget);
            slideIn(code_verf1);
        }

    }

    @FXML
    private void handleSubmitRestButton() throws SQLException, NoSuchAlgorithmException {
        String restPassword = RestPassword.getText();
        String comfirmRestPassword = RestComfirmPassword.getText();
        if(restPassword.isEmpty() || comfirmRestPassword.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WeTransfet :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("fields must be filed !!");
            alert.showAndWait();
        }
        else if (!restPassword.equals(comfirmRestPassword)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WeTransfet :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Password not match !!");
            alert.showAndWait();
        } else if (!pattern_password.matcher(restPassword).matches()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WeTransfet :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Weak password,It must containt at lest uppercase,lowercase,digit,special charactere:@$!%*?&");
            alert.showAndWait();
        }
        else {
            String query = "update user set password=? where email=?";
            String email = EmailForgetPassword.getText();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,hashPassword(restPassword));
            statement.setString(2,email);
            int row_modified = statement.executeUpdate();
            if(row_modified<=0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("WeTransfet :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Problem in Rest password");
                alert.showAndWait();
            }
            else {
                slideOut(RestForm);
                slideIn(loginForm);
                isLoginFormVisible=true;
            }
        }
    }
    @FXML
    private void handleCodeRestButton()
    {
        String CodeFromTextField=optField11.getText()+optField21.getText()+optField31.getText()+optField41.getText()+optField51.getText();
        if(random.equals(CodeFromTextField))
        {
            slideOut(code_verf1);
            slideIn(RestForm);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WeTransfet :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("code Incorrect !!");
            alert.showAndWait();
        }
    }
    @FXML
    public void handleshowPassword() {
        if(PasswordLogin.isVisible())
        {
            PasswordLogin.setVisible(false);
            PasswordLogin.setManaged(false);
            textfield.setVisible(true);
            textfield.setManaged(true);
        }
        else {
            PasswordLogin.setVisible(true);
            PasswordLogin.setManaged(true);
            textfield.setVisible(false);
            textfield.setManaged(false);
        }

    }
    @FXML
    private void GoogleConnexion()
    {
        OAuthGoogleAuthenticator googleAuthenticator = new OAuthGoogleAuthenticator(
                "979015115678-c8939r0q7h9euket4q1pfc90anqtj0e9.apps.googleusercontent.com",
                "http://localhost/dashboard",
                "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",
                "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email"
        );
        googleAuthenticator.startLogin();
    }
    @FXML
    private void FacebookConnexion()
    {
        OAuthFacebookAuthenticator facebookAuthenticator = new OAuthFacebookAuthenticator(
                "1586773298750133",
                "http://localhost/dashboard",
                "446dd7a56fdbecbf9d11222537d77a01",
                "email,name,id"
        );
        facebookAuthenticator.startLogin();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
