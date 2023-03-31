package com.stempien.uwielbiamzaczynacodpoczatku;

import javafx.fxml.FXML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MainController {

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
                isbn = plik.get(0);
                autor = plik.get(1);
                title = plik.get(2);
                year = plik.get(3);
                publisher = plik.get(4);
                description = plik.get(5);

            } catch (ArrayIndexOutOfBoundsException e) {
                MsgHelper.showError("Error", "Błąd w czytaniu pliku");
            }
            Ksiazki ksiazka = new Ksiazki(isbn,autor,title,year,publisher,description);
        }
    }

    public static List<String> loadFileContent(String filePath) {
        try {
            List<String> result = Files.readAllLines(Path.of(filePath));
            return result;
        } catch (IOException e) {
            return null;
        }
    }
}
