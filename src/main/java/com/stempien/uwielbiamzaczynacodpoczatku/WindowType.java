package com.stempien.uwielbiamzaczynacodpoczatku;

/**
 * Możliwe do otwarcia okna.
 */
public enum WindowType {
    /** Główny ekran aplikacji */
    FRM_MAIN("main-view.fxml", "Biblioteka", 1200, 800),
    /** Okno logowania */
    FRM_LOGIN("hello-view.fxml", "Logowanie", 300, 200);

    private String viewPath;
    private String title;
    private Integer width;
    private Integer height;

    WindowType(String viewPath, String title, Integer width, Integer height) {
        this.viewPath = viewPath;
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public String getViewPath() {
        return viewPath;
    }

    public String getTitle() {
        return title;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}