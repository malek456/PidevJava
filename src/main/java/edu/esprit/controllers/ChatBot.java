package edu.esprit.controllers;

import edu.esprit.chatbot.BotResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatBot /*implements Initializable*/ {
    @FXML
    private VBox graphic;
    @FXML
    private Label tainput;
    @FXML
    private TextArea chatBox;

    public ChatBot() {
    }
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//        chatBox.setScrollLeft(0);
//        chatBox.setPromptText("Send a message");
//        chatBox.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
//        chatBox.setWrapText(true);
//        // bot("Hello I am a chatbot. Ask me anything by typing below. Type 'Quit' to end the program.\n");
//        chatBox.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                // Call the conversation function when Enter key is pressed
//                try {
//                    conversation(new ActionEvent());
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//    }


    @FXML
    public void conversation(ActionEvent event) throws Exception {
        String gtext = chatBox.getText();
        HBox hBox = new HBox();

        hBox.setMaxWidth(150);
        hBox.setMinWidth(150);

        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setTranslateX(130);

        Text text = new Text(gtext);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        HBox.setMargin(textFlow,new Insets(10,0,10,0));

        textFlow.setStyle("-fx-background-color: rgb(15,125,242);-fx-background-radius: 20px;");

        hBox.getChildren().add(textFlow);
        graphic.getChildren().add(hBox);
        chatBox.setText("");
        String category = BotResponse.findCategory(gtext);
        String response = respond(category);

        bot(response);
        if (gtext.equals("QUIT\n")) {
            // Node n = (Node) event.getSource();
            Stage stage = (Stage) chatBox.getScene().getWindow();
            stage.close();
        }


    }

    private void bot(String string) {

        HBox hBox = new HBox();

        hBox.setMaxWidth(150);
        hBox.setMinWidth(150);

        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setTranslateX(0);

        Text text = new Text(string);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        HBox.setMargin(textFlow,new Insets(10,0,10,0));
        textFlow.setStyle("-fx-background-color: rgb(195,195,195);-fx-background-radius: 20px;");
        hBox.getChildren().add(textFlow);
        graphic.getChildren().add(hBox);
    }

    private static String respond(String category) {
        switch (category) {
            case "Greeting":
                String[] greetings = {
                        "Hello! How may I assist you today?",
                        "Hi there! What can I help you with?",
                        "Good morning! Welcome to our tourism agency. How can I help you?"
                };
                return getRandomResponse(greetings);
            case "FlightInformation":
                String[] flightResponses = {
                        "We offer a range of flights from New York to Paris. Would you like me to check availability for specific dates?",
                        "Certainly! I can help you find the best flight options from New York to Paris.",
                        "Let me assist you in finding the most suitable flight from New York to Paris."
                };
                return getRandomResponse(flightResponses);
            case "HotelReservation":
                String[] hotelResponses = {
                        "I can assist you with booking a hotel room in Barcelona. Please provide me with your travel dates.",
                        "Certainly! Let me help you find the perfect hotel room in Barcelona.",
                        "I'm here to assist you in booking a hotel room in Barcelona. When are you planning to stay?"
                };
                return getRandomResponse(hotelResponses);
            case "TourPackage":
                String[] tourResponses = {
                        "We have various guided tours available in Rome. Which specific attractions are you interested in?",
                        "Explore Rome with our guided tours! What type of tour are you looking for?",
                        "Let me provide you with information about our guided tours in Rome. What are your preferences?"
                };
                return getRandomResponse(tourResponses);
            case "CarRental":
                String[] carResponses = {
                        "Yes, we provide car rental services in London. What type of vehicle are you looking for?",
                        "Certainly! Let me assist you in renting a car for your trip to London.",
                        "I'm here to help you with car rental services in London. What are your requirements?"
                };
                return getRandomResponse(carResponses);
            case "VisaInformation":
                String[] visaResponses = {
                        "Visa requirements vary depending on your nationality and destination. I can help you with specific information.",
                        "Certainly! Let me provide you with information about visa requirements for your destination.",
                        "I'm here to assist you with visa information. Which country are you planning to visit?"
                };
                return getRandomResponse(visaResponses);
            case "LocalAttractions":
                String[] attractionsResponses = {
                        "Tokyo offers a variety of must-see attractions, from historic temples to modern skyscrapers.",
                        "Explore the vibrant city of Tokyo with our list of top attractions.",
                        "Let me recommend some must-see attractions in Tokyo for your itinerary."
                };
                return getRandomResponse(attractionsResponses);
            case "TravelInsurance":
                String[] insuranceResponses = {
                        "Our travel insurance covers medical emergencies, trip cancellations, and more. Would you like to receive a quote?",
                        "Certainly! Let me provide you with information about our travel insurance options.",
                        "I'm here to assist you with travel insurance. What type of coverage are you interested in?"
                };
                return getRandomResponse(insuranceResponses);
            case "CruiseInformation":
                String[] cruiseResponses = {
                        "We offer a variety of cruises to different destinations. What specific information are you looking for?",
                        "Explore the world with our cruise options! Where would you like to sail?",
                        "Let me provide you with information about our cruise packages. What are your preferences?"
                };
                return getRandomResponse(cruiseResponses);
            case "LanguageTranslation":
                String[] translationResponses = {
                        "Yes, we provide translation services for travel documents. Let me know how I can assist you.",
                        "Certainly! Let me help you with translation services for your travel documents.",
                        "I'm here to assist you with language translation. What documents do you need translated?"
                };
                return getRandomResponse(translationResponses);
            case "CurrencyExchange":
                String[] currencyResponses = {
                        "You can exchange currency at our office or at local banks in Tokyo. We offer competitive exchange rates.",
                        "Certainly! Let me provide you with information about currency exchange services.",
                        "I'm here to assist you with currency exchange. What currency are you looking to exchange?"
                };
                return getRandomResponse(currencyResponses);
            case "EmergencyAssistance":
                String[] emergencyResponses = {
                        "In case of emergencies during your trip, you can reach us at our 24/7 emergency contact number.",
                        "Don't worry! We're here to assist you in case of emergencies. What do you need help with?",
                        "I'm here to help you with any emergencies that may arise during your trip. What assistance do you require?"
                };
                return getRandomResponse(emergencyResponses);
            case "PriceInquiry":
                String[] priceResponses = {
                        "The cost of a round-trip flight from London to Paris depends on various factors such as travel dates and airline choice.",
                        "Certainly! Let me check the price for you. Could you please provide me with more details?",
                        "I can assist you with price inquiries. What specific information are you looking for?"
                };
                return getRandomResponse(priceResponses);
            case "ServiceQualityClaim":
                String[] qualityResponses = {
                        "We apologize for any inconvenience you experienced. Please provide more details so we can address your concerns.",
                        "I'm sorry to hear that. Please tell me more about the issue so we can rectify it.",
                        "Your feedback is important to us. Could you please provide more information about your experience?"
                };
                return getRandomResponse(qualityResponses);
            case "ServiceDelayClaim":
                String[] delayResponses = {
                        "We're sorry to hear about the delay. Let us know how we can assist you further.",
                        "Please accept our apologies for the inconvenience. How can we make it right?",
                        "I understand the frustration caused by the delay. Let's work together to find a solution."
                };
                return getRandomResponse(delayResponses);
            case "PromotionInquiry":
                String[] promotionResponses = {
                        "Yes, we currently have promotions available for hotel bookings. Would you like more information?",
                        "Certainly! Let me provide you with information about our current promotions and discounts.",
                        "I'm here to assist you with promotional offers. What type of promotion are you interested in?"
                };
                return getRandomResponse(promotionResponses);

            case "AIInquiry":
                String[] aiResponses = {
                        "Our AI technology assists in providing personalized travel recommendations based on your preferences.",
                        "We utilize advanced AI algorithms to optimize your travel experience. How can I help you further?",
                        "Our AI system analyzes your travel preferences to offer tailored recommendations. What else would you like to know?"
                };
                return getRandomResponse(aiResponses);
            case "BotInquiry":
                String[] botResponses = {
                        "I am a virtual assistant designed to assist you with your travel inquiries. How can I assist you today?",
                        "As a bot, I'm here to help you with any questions you may have about our services. What can I do for you?",
                        "I'm a chatbot programmed to provide assistance with travel-related queries. What do you need help with?"
                };
                return getRandomResponse(botResponses);
            case "ConversationContinue":
                String[] continueResponses = {
                        "Ok, that's great. What else can I assist you with?",
                        "Excellent! Is there anything else you'd like to know?",
                        "Wonderful! Do you have any other questions?"
                };
                return getRandomResponse(continueResponses);
            case "ConversationComplete":
                String[] completeResponses = {
                        "Thank you for contacting us. Feel free to reach out if you need further assistance.",
                        "It was a pleasure assisting you. Have a great day!",
                        "If you have any more questions in the future, don't hesitate to contact us."

                };
                return getRandomResponse(completeResponses);

        }
        return "Sorry, I'm not sure how to respond to that.";

    }

    private static String getRandomResponse(String[] responses) {
        return (responses) [(int) (Math.random()* responses.length)];
    }

}