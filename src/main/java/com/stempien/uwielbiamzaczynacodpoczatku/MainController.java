package com.stempien.uwielbiamzaczynacodpoczatku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    public TextField txtIsbn;
    public TextField txtYear;
    public TextField txtTitle;
    public TextField txtAutor;
    public TextArea txtDescription;
    public TextField txtPublisher;
    private List<Ksiazki> ksiazki = new ArrayList<>();
    private int numerOfBook=0;

    @FXML
    public void initialize() {
        String isbn = null;
        String autor = null;
        String title = null;
        String year = null;
        String publisher = null;
        String description = null;
        List<String> plik = loadFileContent("C:/Users/sirk0/Desktop/x.txt");
        for(int i = 0;i<plik.size();i++) {
            String plikInaczej = plik.get(i);
            List<String> podzielonyPlik = List.of(plikInaczej.split(","));
            System.out.println(podzielonyPlik);
            System.out.println(plik);
            try {
                isbn = podzielonyPlik.get(0);
                autor = podzielonyPlik.get(1);
                title = podzielonyPlik.get(2);
                year = podzielonyPlik.get(3);
                publisher = podzielonyPlik.get(4);
                description = podzielonyPlik.get(5);
                Ksiazki ksiazka = new Ksiazki(isbn,autor,title,year,publisher,description); //wiem że da się to zrobić od razu ale jest bardziej czytelne
                ksiazki.add(ksiazka);
            } catch (ArrayIndexOutOfBoundsException e) {
                MsgHelper.showError("Error", "Błąd w czytaniu pliku");
            }
        }
        wyswieltKsiazke(0);
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
        if (numerOfBook < ksiazki.size()-1) {
            numerOfBook++;
            wyswieltKsiazke(numerOfBook);
        }
    }
}
