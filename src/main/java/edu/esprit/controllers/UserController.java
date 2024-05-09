package edu.esprit.controllers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import edu.esprit.entities.SessionManager;
import edu.esprit.entities.SessionUtilisateur;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import edu.esprit.tests.HelloApplication;
import edu.esprit.utils.connexion;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class UserController {
    public final ServiceUser serviceUser = new ServiceUser();
    private static Stage stage;
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
    public final Connection connection = connexion.getInstance().getCnx();
    public static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static final Pattern pattern_email = Pattern.compile(EMAIL_REGEX);
    public static final String PHONE_REGEX =
            "^\\d{8}$";
    public static final Pattern pattern_phone = Pattern.compile(PHONE_REGEX);
    private static final String Name_REGEX =
            "^[a-zA-Z]{3,}$";
    public static final Pattern pattern_name = Pattern.compile(Name_REGEX);

    public static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public static final Pattern pattern_password = Pattern.compile(PASSWORD_REGEX);
    public final String random = generateOTP();
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
    private void handleLoginButtonAction() {
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
    private void handleAboutButtonAction() {
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
    private void handleContactButtonAction() {
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
    public static void slideOut(VBox vbox)
    {
        TranslateTransition slideOutTransition = new TranslateTransition(Duration.millis(300), vbox);
        slideOutTransition.setToY(-1000);
        slideOutTransition.play();
    }
    public static void slideIn(VBox vbox) {
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
                int rowsInserted = serviceUser.ajouter(user) ;
                if (rowsInserted > 0) {
                    System.out.println("User signed up successfully!");
                    sendVerificationEmail(user.getEmail(),opt,"Email Verification");
                    slideOut(registrationForm);
                    slideIn(opt_verf);
                    isRegistrationFormVisible = false;
                    SessionUtilisateur.demarrerSession(user);
                    SessionManager.setUserEmail(SessionUtilisateur.getUtilisateurActuel().getEmail());
                    SessionManager.setUserRole(SessionUtilisateur.getUtilisateurActuel().getRoles());
                    SessionManager.setUserImage("user.png");
                    SessionManager.setUserId(SessionUtilisateur.getUtilisateurActuel().getId());
                    SessionManager.setUserNom(SessionUtilisateur.getUtilisateurActuel().getNom());
                    SessionManager.setUserPrenom(SessionUtilisateur.getUtilisateurActuel().getPrenom());
                    SessionManager.setUserDes("");
                    //SessionManager.setUserNom(user.getNom());
                }
                else {
                    System.out.println("Failed to sign up user.");
                }
            }
            } catch(SQLException e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "An exception occurred", e);
                System.out.println("An error occurred while signing up.");
            } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
    public static String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    public static boolean verifyPassword(String password, String hashedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, hashedPassword);
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
            message.setContent("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
                    "</head>\n" +
                    "<style type=\"text/css\">\n" +
                    "body{background-color:blue;margin: 0px;}\n" +
                    "</style>\n" +
                    "<body>\n" +
                    "<table border=\"0\" width=\"100%\" style=\"margin:auto;padding:30px;background-color: #F3F3F3;border:1px solid #FF7A5A;\">\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table border=\"0\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<a href=\"https://imgbb.com/\"><img src=\"https://i.ibb.co/Sm0vL9V/logo.png\" alt=\"logo\" border=\"0\"></a>\n" +
                    "</td>\n" +
                    "<td>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"text-align:center;width:100%;background-color: #fff;\">\n" +
                    "<tr>\n" +
                    "<td style=\"background-color:rgba(51,48,221,255);height:100px;font-size:50px;color:#fff;\"><i class=\"fa fa-envelope-o\" aria-hidden=\"true\"></i></td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<h1 style=\"padding-top:25px;\">Code Verification</h1>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<p style=\"padding:0px 100px;\">\n" +
                    "To ensure the security of your account, we require a verification code for access.\n" +
                    "Please use the following code: [insert verification code]. Kindly enter this code within the designated\n" +
                    "field to proceed with your login.\n" +
                    "</p>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<strong style=\"margin:10px 0px 30px 0px;padding:10px 20px;color:black;font-size:50px \">"+verificationCode+"</strong>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table border=\"0\" width=\"100%\" style=\"border-radius: 5px;text-align: center;\">\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<h3 style=\"margin-top:30px;\">Stay in touch</h3>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<div style=\"margin-top:20px;\">\n" +
                    "<a href=\"#\" style=\"text-decoration: none;\"><span class=\"twit\" style=\"padding:10px 9px;background-color:#4099FF;color:#fff;border-radius:50%;\"><i class=\"fa fa-twitter\" aria-hidden=\"true\" style=\"height:20px;width:20px;\"></i></span></a>\n" +
                    "<a href=\"#\" style=\"text-decoration: none;\"><span class=\"fb\" style=\"padding:10px 9px;background-color: #3B5998;color:#fff;border-radius:50%;\"><i class=\"fa fa-facebook\" aria-hidden=\"true\" style=\"height:20px;width:20px;\"></i></span></a>\n" +
                    "<a href=\"#\" style=\"text-decoration: none;\"><span class=\"msg\" style=\"padding:10px 9px;background-color: #FFC400;color:#fff;border-radius:50%;\"\"><i class=\"fa fa-envelope-o\" aria-hidden=\"true\" style=\"height:20px;width:20px;\"></i></span></a>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<div style=\"margin-top: 20px;\">\n" +
                    "<span style=\"font-size:12px;\">Lorem ipsum</span><br>\n" +
                    "<span style=\"font-size:12px;\">Copyright &copy; 2024 Wetravel</span>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</body>\n" +
                    "</html>", "text/html");
            // Send message
            Transport.send(message);
            System.out.println("Verification email sent successfully to: " + recipientEmail);
        } catch (MessagingException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "An exception occurred", e);
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
        return switch (currentTextFieldId) {
            case "optField11" -> optField21;
            case "optField21" -> optField31;
            case "optField31" -> optField41;
            case "optField41" -> optField51;
            default -> null; // Retourne null si le champ de texte actuel est le dernier
        };
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
        return switch (currentTextFieldId) {
            case "optField1" -> null; // There is no previous text field
            case "optField2" -> optField1;
            case "optField3" -> optField2;
            case "optField4" -> optField3;
            case "optField5" -> optField4;
            default -> null;
        };
    }
    private TextField getPreviousTextField2(String currentTextFieldId) {
        return switch (currentTextFieldId) {
            case "optField11" -> null; // There is no previous text field
            case "optField21" -> optField11;
            case "optField31" -> optField21;
            case "optField41" -> optField31;
            case "optField51" -> optField41;
            default -> null;
        };
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
    private void handleCodeOptButton() throws SQLException, IOException {
        User user = getTextfieldReg();
        String CodeFromTextField=optField1.getText()+optField2.getText()+optField3.getText()+optField4.getText()+optField5.getText();
        if(opt.equals(CodeFromTextField)){
            int rowsModified = serviceUser.modifyIsVerify(user);
            if (rowsModified > 0) {
                System.out.println("User verfified successefuly!");
            }
            else {
                System.out.println("Failed to verify user.");
            }
            SessionManager.createSession();
            SessionManager.setUserEmail(user.getEmail());
            SessionManager.setUserNom(user.getNom());
            redirectToFrontend();

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
    public void login() throws SQLException, IOException {
        String query = "select * from user where email=?";
        PreparedStatement statement = connection.prepareStatement(query);
        String email = EmailLogin.getText();
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            // User not found
            displayLoginError();
        } else {
            String hashedPasswordFromDatabase = resultSet.getString("password");
            String password = PasswordLogin.getText();

            // Verify the password
            if (verifyPassword(password, hashedPasswordFromDatabase)) {
                // Passwords match, login successful
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

                // Start user session
                SessionUtilisateur.demarrerSession(user);
                // Set session manager variables
                SessionManager.createSession();
                SessionManager.setUserEmail(user.getEmail());
                SessionManager.setUserRole(user.getRoles());
                SessionManager.setUserImage(user.getImageName());
                SessionManager.setUserId(user.getId());
                //SessionManager.setUserDes(user.getDescriptionUser());
                //SessionManager.setUserPhone(user.getPhoneNumber());
                SessionManager.setUserNom(user.getNom());

                // Redirect based on user role
                if (user.getRoles().contains("ROLE_ADMIN")) {
                    redirectToAdminDashboard();
                } else {
                    redirectToFrontend();
                }
            } else {
                // Passwords don't match
                displayLoginError();
            }
        }
    }

    private void displayLoginError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("WeTransfer :: Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Email or Password incorrect !!");
        alert.showAndWait();
    }

    @FXML
    public void handleLoginButton() throws SQLException, IOException {
        login();
    }
    public void redirectToAdminDashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/dashAdmin.fxml"));
        stage.getScene().setRoot(fxmlLoader.load());
        AdminController adminController = fxmlLoader.getController();
        adminController.setStage(stage);
        stage.setTitle("Dashboard");
    }

    public static void redirectToFrontend() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/front.fxml"));
        stage.getScene().setRoot(fxmlLoader.load());
        indexController indexController0 = fxmlLoader.getController();
        indexController0.setStage(stage);
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
    private void GoogleConnexion() throws IOException {
        OAuthGoogleAuthenticator googleAuthenticator = new OAuthGoogleAuthenticator(
                "979015115678-c8939r0q7h9euket4q1pfc90anqtj0e9.apps.googleusercontent.com",
                "http://localhost/dashboard",
                "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",
                "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email"
        );
        googleAuthenticator.startLogin();
    }
    @FXML
    private void FacebookConnexion() throws IOException {
        OAuthFacebookAuthenticator facebookAuthenticator = new OAuthFacebookAuthenticator(
                "1586773298750133",
                "http://localhost/dashboard",
                "446dd7a56fdbecbf9d11222537d77a01",
                "email,name,id"
        );
        facebookAuthenticator.startLogin();
    }

    public void setStage(Stage stage) {
        UserController.stage = stage;
    }
}
