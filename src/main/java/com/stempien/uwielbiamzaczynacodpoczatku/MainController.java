package com.stempien.uwielbiamzaczynacodpoczatku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static com.stempien.uwielbiamzaczynacodpoczatku.HelloController.czyAdmin;
import static com.stempien.uwielbiamzaczynacodpoczatku.MsgHelper.showError;

public class MainController {
    public Button btnReturnBook;
    public TextField txtDays;
    public Button wyslijWszystkie;
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
    private LocalDate now = LocalDate.now();

    @FXML
    public void initialize() {
        if(!czyAdmin){
            wyslijWszystkie.setVisible(false);
        }
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        String isbn = null;
        String autor = null;
        String title = null;
        String year = null;
        String publisher = null;
        String description = null;
        LocalDate date;
        boolean returned;
        List<String> plik = loadFileContent("C:/Users/sirk0/Desktop/x.txt");
        toolkit.beep();
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
                if(podzielonyPlik.get(6).equals("false")){
                    returned=true;
                    btnReturnBook.setText("Zwróć");
                    date = LocalDate.parse(podzielonyPlik.get(7));
                    Ksiazki ksiazka = new Ksiazki(isbn, autor, title, year, publisher, description,returned,date); //wiem że da się to zrobić od razu ale jest bardziej czytelne
                    ksiazki.add(ksiazka);
               }
                else{
                    returned=false;
                    txtDays.setVisible(true);
                    btnReturnBook.setText("Wypożycz");
                    Ksiazki ksiazka = new Ksiazki(isbn, autor, title, year, publisher, description,returned); //wiem że da się to zrobić od razu ale jest bardziej czytelne
                    ksiazki.add(ksiazka);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                showError("Error", "Błąd w czytaniu pliku");
            }
        }
        try {
            wyswieltKsiazke(0);
        } catch (IndexOutOfBoundsException e) {

        }
    }

    private void wyswieltKsiazke(int number) {
        txtIsbn.setText(ksiazki.get(number).isbn);
        txtAutor.setText(ksiazki.get(number).autor);
        txtTitle.setText(ksiazki.get(number).title);
        txtYear.setText(ksiazki.get(number).year);
        txtPublisher.setText(ksiazki.get(number).publisher);
        txtDescription.setText(ksiazki.get(number).description);
        if(ksiazki.get(numerOfBook).isReturned){
            btnReturnBook.setText("Zwróć");
        }
        else{
            btnReturnBook.setText("Wypożycz");
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
    for(int i = 0;i<ksiazki.size();i++) {
        if(ksiazki.get(i).localDate==null){
            text += ksiazki.get(i).isbn + "," + ksiazki.get(i).autor + "," + ksiazki.get(i).title + "," + ksiazki.get(i).year + "," + ksiazki.get(i).publisher + "," + ksiazki.get(i).description + "," + ksiazki.get(i).isReturned + "\n";

        }
        else{
            text += ksiazki.get(i).isbn + "," + ksiazki.get(i).autor + "," + ksiazki.get(i).title + "," + ksiazki.get(i).year + "," + ksiazki.get(i).publisher + "," + ksiazki.get(i).description + "," + ksiazki.get(i).isReturned +","+ksiazki.get(i).localDate+ "\n";
        }
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
            Ksiazki ksiazka = new Ksiazki(isbn, autor, title, year, publisher, description,ksiazki.get(numerOfBook).isReturned);
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
                Ksiazki ksiazka = new Ksiazki(isbn, autor, title, year, publisher, description, false);
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

    public void btnDelBookClicked(ActionEvent actionEvent) {
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

    public void btnReturnBookClicked(ActionEvent actionEvent) {
        if(!(ksiazki.get(numerOfBook).isReturned)){
            txtDays.setVisible(true);
            isbn = ksiazki.get(numerOfBook).isbn;
            autor = ksiazki.get(numerOfBook).autor;
            title = ksiazki.get(numerOfBook).title;
            year = ksiazki.get(numerOfBook).year;
            publisher = ksiazki.get(numerOfBook).publisher;
            description = ksiazki.get(numerOfBook).description;
            ksiazki.remove(numerOfBook);
            Integer numerOfDays = 0;
            try {
                numerOfDays = Integer.parseInt(txtDays.getText());
                Ksiazki ksiazka = new Ksiazki(isbn, autor, title, year, publisher, description, false, now.plusDays(numerOfDays));
                ksiazki.add(numerOfBook, ksiazka);
            } catch (NumberFormatException nfe) {
                showError("Error", "Błąd wpisywania dnia");
                Ksiazki ksiazka = new Ksiazki(isbn, autor, title, year, publisher, description, false);
            }
            //sendEmail("adik08.email@gmail.com", now.plusDays(numerOfDays));
            btnReturnBook.setText("Zwróć");
        }
        else {
            btnReturnBook.setText("Wypożycz");
            txtDays.setVisible(false);
            isbn = ksiazki.get(numerOfBook).isbn;
            autor = ksiazki.get(numerOfBook).autor;
            title = ksiazki.get(numerOfBook).title;
            year = ksiazki.get(numerOfBook).year;
            publisher = ksiazki.get(numerOfBook).publisher;
            description = ksiazki.get(numerOfBook).description;
            ksiazki.remove(numerOfBook);
            Ksiazki ksiazka = new Ksiazki(isbn, autor, title, year, publisher, description,true);
            ksiazki.add(numerOfBook,ksiazka);
        }
        wyswieltKsiazke(numerOfBook);

    }

    private void sendEmail(String userEmail, LocalDate data) {
        String to = userEmail;
        String from = "bibloteka@buziaczek.pl";
        String password = "Bibloteka2023";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.poczta.onet.pl");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getInstance(properties, new MyAuthenticator(from, password));
       try {
           Message message = new MimeMessage(session);
           message.setFrom(new InternetAddress(from));
           message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
           message.setSubject("Wypożyczenie");
           message.setText("Witaj, dziękujemy za wypożyczenie książki. Masz czas do: "+data);
           Transport.send(message);
           System.out.println("Wiadomość została wysłana!");
       } catch (AddressException e) {
           e.printStackTrace();
       } catch (MessagingException e) {
            e.printStackTrace();
       }

    }

    public void btnKopiaClicked(ActionEvent actionEvent) {
        String tekst = ksiazki.get(numerOfBook).autor +" - “"+ksiazki.get(numerOfBook).title+"“";
        StringSelection selection = new StringSelection(tekst);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public void btnWyslijWszystkieClicked(ActionEvent actionEvent) {

    }
}
