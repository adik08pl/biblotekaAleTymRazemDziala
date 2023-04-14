package com.stempien.uwielbiamzaczynacodpoczatku;

import java.time.LocalDate;

public class Ksiazki {

    public String isbn;
    public String autor;
    public String title;
    public String publisher;
    public String year;
    public String description;
    public boolean isReturned;
    public LocalDate localDate;

    public Ksiazki(String isbnOfBook, String autorOfBook, String titleOfBook, String yearOfBook, String publisherofBook, String descriptionOfBook,boolean returned) {
        isbn = isbnOfBook;
        autor = autorOfBook;
        title = titleOfBook;
        publisher = publisherofBook;
        year = yearOfBook;
        description = descriptionOfBook;
        isReturned=returned;
    }
    public Ksiazki(String isbnOfBook, String autorOfBook, String titleOfBook, String yearOfBook, String publisherofBook, String descriptionOfBook,boolean returned,LocalDate date) {
        isbn = isbnOfBook;
        autor = autorOfBook;
        title = titleOfBook;
        publisher = publisherofBook;
        year = yearOfBook;
        description = descriptionOfBook;
        isReturned=returned;
        localDate = date;
    }
}
