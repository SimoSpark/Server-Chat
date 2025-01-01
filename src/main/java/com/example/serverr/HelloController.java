package com.example.serverr;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Button button_send;
    @FXML
    private TextField tf_message;
    @FXML
    private VBox vbox_messages;  // Changed from vBoxMessages to match FXML
    @FXML
    private ScrollPane sp_main;
    private Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(5467);
                Platform.runLater(() -> addLabel("Server started, waiting for client...", vbox_messages));
                server = new Server(serverSocket);
                Platform.runLater(() -> addLabel("Client connected!", vbox_messages));

                // Start receiving messages after client connects
                server.receiveMessageFromClient(vbox_messages);
            } catch(IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> addLabel("Error creating Server...", vbox_messages));
            }
        }).start();

        // Set up scroll pane auto-scroll
        vbox_messages.heightProperty().addListener((observable, oldValue, newValue) ->
                sp_main.setVvalue((Double) newValue));

        // Set up send button handler
        button_send.setOnAction(event -> {
            String messageToSend = tf_message.getText();
            if (!messageToSend.isEmpty() && server != null) {
                sendMessage(messageToSend);


            }