package com.wypozyczalnia.mas_project.controller;

import com.wypozyczalnia.mas_project.model.Uzytkownik;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button resetButton;

    @FXML
    private GridPane resetPasswordForm;

    @FXML
    private TextField resetLoginField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    private Uzytkownik mockUser; // Przykładowy użytkownik

    public LoginController() {
        // Tworzymy przykładowego użytkownika (dla testów)
        mockUser = new Uzytkownik("Jan", "Kowalski", "admin", "admin123", null) {};
    }

    /**
     * Obsługa logowania.
     */
    @FXML
    private void handleLogin() {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (mockUser.zaloguj(login, password)) {
            System.out.println("Zalogowano pomyslnie!");
        } else {
            System.out.println("Nieprawidlowy login lub haslo.");
        }
    }

    /**
     * Wyświetla formularz do resetowania hasła.
     */
    @FXML
    private void showPasswordResetForm() {
        resetPasswordForm.setVisible(true);
        resetPasswordForm.setManaged(true);
    }

    /**
     * Obsługa zmiany hasła.
     */
    @FXML
    private void handlePasswordReset() {
        String login = resetLoginField.getText();
        String newPassword1 = newPasswordField.getText();
        String newPassword2 = confirmPasswordField.getText();

        try {
            mockUser.zmienHaslo(login, newPassword1, newPassword2);
            System.out.println("Haslo zostalo zmienione.");
            resetPasswordForm.setVisible(false);
            resetPasswordForm.setManaged(false);
        } catch (IllegalArgumentException e) {
            System.out.println("Blad: " + e.getMessage());
        }
    }
}