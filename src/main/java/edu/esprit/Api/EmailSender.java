package edu.esprit.Api;
import javafx.event.ActionEvent;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
public class EmailSender {

    public static void sendConfirmationEmail() throws MessagingException {
        // Configurez les propriétés de la session
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Serveur SMTP sortant de Gmail
        props.put("mail.smtp.port", "587"); // Port SMTP pour TLS
        props.put("mail.smtp.auth", "true"); // Activer l'authentification SMTP
        props.put("mail.smtp.starttls.enable", "true"); // Activer le démarrage TLS

        // Créez une session avec l'authentification
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("jbooo7345@gmail.com", "Pidev2024");
            }
        });

        // Créez un objet Message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("jbooo7345@gmail.com")); // Adresse e-mail de l'expéditeur
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("sarrahammemi524@gmail.com")); // Adresse e-mail du destinataire
        message.setSubject("Sujet de l'e-mail"); // Sujet du courriel
        message.setText("Corps de l'e-mail"); // Corps du courriel

        // Envoyez le message
        Transport.send(message);
    }

    public void sendMail(ActionEvent actionEvent) {
    }
}
