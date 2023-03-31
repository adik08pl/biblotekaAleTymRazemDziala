package com.stempien.uwielbiamzaczynacodpoczatku;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    public TextField txtUser;
    public TextField txtPassword;


    @FXML
    protected void onButtonClick() {
        String password = txtPassword.getText();
        String login = txtUser.getText();
        if (login.equals("Bob") && password.equals("Bob")) {
            WindowHelper.openWindow(WindowType.FRM_MAIN);
            WindowHelper.closeWindow(txtUser);
        }
        else{
            MsgHelper.showError("Błąd","Nie znaloziono takich danych w naszym zbiorze loginów i haseł");
        }
    }
}