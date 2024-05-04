package edu.esprit.Api;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    public static void send(String toEmail ) {
        String from = "oussama.sfaxi@esprit.tn"; // Change this to your sender email address
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("oussama.sfaxi@esprit.tn", "211JMT6879"); // Change this to your sender email password
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress(from, "EduHUB", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Annulation de La reservation ");
            message.setText("Cher/Chère Client ,\n" +
                    "\n" +
                    "Nous regrettons de devoir vous informer que votre réservation de séance de sport  doit être annulée en raison de circonstances imprévues.\n" +
                    "\n" +
                    "Malheureusement, des événements indépendants de notre volonté ont rendu impossible la tenue de votre séance à l'heure convenue. Nous comprenons à quel point cela peut être décevant et nous vous présentons nos plus sincères excuses pour tout inconvénient que cela pourrait vous causer.\n" +
                    "\n" +
                    "Nous travaillons activement pour résoudre cette situation et nous vous encourageons à nous contacter pour reprogrammer votre séance dès que possible. Nous ferons tout notre possible pour vous offrir des alternatives qui répondent à vos besoins.\n" +
                    "\n" +
                    "Nous apprécions votre compréhension et votre patience pendant cette période. Si vous avez des questions ou des préoccupations, n'hésitez pas à nous contacter.\n" +
                    "\n" +
                    "Encore une fois, nous vous prions de bien vouloir accepter nos excuses pour cette situation et nous vous remercions de votre confiance et de votre coopération.\n" +
                    "\n" +
                    "Cordialement,\n" +
                    "\n" +
                    "[L'Administrateur de la salle de sport]\n" +
                    "[Gofit pro ]\n" +
                    "[nTel : 71501998,Email : info@salledesportgofit.com] "  );

            Transport.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
