package com.stempien.uwielbiamzaczynacodpoczatku;

public class Ksiazki {

    public String isbn;
    public String autor;
    public String title;
    public String publisher;
    public String year;
    public String description;

    public Ksiazki(String isbnOfBook, String autorOfBook, String titleOfBook, String yearOfBook, String publisherofBook, String descriptionOfBook) {
        isbn = isbnOfBook;
        autor = autorOfBook;
        title = titleOfBook;
        publisher = publisherofBook;
        year = yearOfBook;
        description = descriptionOfBook;
    }
}
