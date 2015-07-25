package ua.in.petybay.entity;

/**
 * Created by slavik on 25.07.15.
 */
public class Title {
    String language;
    String title;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Title{" +
                "language='" + language + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
