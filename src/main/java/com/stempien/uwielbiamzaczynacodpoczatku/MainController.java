package com.stempien.uwielbiamzaczynacodpoczatku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.stempien.uwielbiamzaczynacodpoczatku.MsgHelper.showError;

public class MainController {
    private String isbn = null;
    private String autor = null;
    private String title = null;
    private String year = null;
    private String publisher = null;
    private String description = null;
    public TextField txtIsbn;
    public TextField txtYear;
    public TextField txtTitle;
    public TextField txtAutor;
    public TextArea txtDescription;
    public TextField txtPublisher;
    private List<Ksiazki> ksiazki = new ArrayList<>();
    private int numerOfBook = 0;
    private boolean adding=false;
    private boolean editting=false;

    @FXML
    public void initialize() {
        String isbn = null;
        String autor = null;
        String title = null;
        String year = null;
        String publisher = null;
        String description = null;
        List<String> plik = loadFileContent("C:/Users/sirk0/Desktop/x.txt");
        for (int i = 0; i < plik.size(); i++) {
            String plikInaczej = plik.get(i);
            List<String> podzielonyPlik = List.of(plikInaczej.split(","));
            try {
                isbn = podzielonyPlik.get(0);
                autor = podzielonyPlik.get(1);
                title = podzielonyPlik.get(2);
                year = podzielonyPlik.get(3);
                publisher = podzielonyPlik.get(4);
                description = podzielonyPlik.get(5);
                Ksiazki ksiazka = new Ksiazki(isbn, autor, title, year, publisher, description); //wiem że da się to zrobić od razu ale jest bardziej czytelne
                ksiazki.add(ksiazka);
            } catch (ArrayIndexOutOfBoundsException e) {
                showError("Error", "Błąd w czytaniu pliku");
            }
        }
        try {
            wyswieltKsiazke(0);
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    private void wyswieltKsiazke(int number) {
        txtIsbn.setText(ksiazki.get(number).isbn);
        txtAutor.setText(ksiazki.get(number).autor);
        txtTitle.setText(ksiazki.get(number).title);
        txtYear.setText(ksiazki.get(number).year);
        txtPublisher.setText(ksiazki.get(number).publisher);
        txtDescription.setText(ksiazki.get(number).description);
    }

    public static List<String> loadFileContent(String filePath) {
        try {
            List<String> result = Files.readAllLines(Path.of(filePath));
            return result;
        } catch (IOException e) {
            return null;
        }
    }

    public void btnLeftClicked(ActionEvent actionEvent) {
        if (numerOfBook > 0) {
            numerOfBook--;
            wyswieltKsiazke(numerOfBook);
        }
    }

    public void btnRightClicked(ActionEvent actionEvent) {
        if (numerOfBook < ksiazki.size() - 1) {
            numerOfBook++;
            wyswieltKsiazke(numerOfBook);
        }
    }
    public void btnSaveClicked(ActionEvent actionEvent) {
        String text="";
    for(int i = 0;i<ksiazki.size();i++){
            text+=ksiazki.get(i).isbn+","+ksiazki.get(i).autor+","+ksiazki.get(i).title+ksiazki.get(i).year+","+ksiazki.get(i).publisher+","+ksiazki.get(i).description+"\n";
    }
        saveFile("C:/Users/sirk0/Desktop/x.txt",text);
    }
    public static boolean saveFile(String filePath, String text) {
        try {
            Files.write(Path.of(filePath), text.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            showError("Error", "Błąd zapisu pliku");
            System.err.println(e);
            return false;
        }
    }


    public void btnEditBook(ActionEvent actionEvent) {
            isbn = txtIsbn.getText();
            autor = txtAutor.getText();
            title = txtTitle.getText();
            year = txtYear.getText();
            publisher = txtPublisher.getText();
            description = txtDescription.getText();
            ksiazki.remove(numerOfBook);
            Ksiazki ksiazka = new Ksiazki(isbn, autor, title, year, publisher, description);
            ksiazki.add(numerOfBook,ksiazka);
    }

    public void btnAddBook(ActionEvent actionEvent) {
        if(adding){
            try{
                isbn = txtIsbn.getText();
                autor = txtAutor.getText();
                title = txtTitle.getText();
                year = txtYear.getText();
                publisher = txtPublisher.getText();
                description = txtDescription.getText();
                Ksiazki ksiazka = new Ksiazki(isbn, autor, title, year, publisher, description);
                ksiazki.add(ksiazka);
                numerOfBook=ksiazki.size()-1;
                wyswieltKsiazke(numerOfBook);
            }
            catch(NullPointerException e){

            }
        }
        else{
            txtIsbn.setText("");
            txtAutor.setText("");
            txtTitle.setText("");
            txtYear.setText("");
            txtPublisher.setText("");
            txtDescription.setText("");
        }
        adding=!adding;
    }

    public void btnDelBook(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Wybór");
        alert.setContentText("Czy napewno chcesz usunąć książke?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ksiazki.remove(numerOfBook);
            txtIsbn.setText("");
            txtAutor.setText("");
            txtTitle.setText("");
            txtYear.setText("");
            txtPublisher.setText("");
            txtDescription.setText("");
        }
    }
}
