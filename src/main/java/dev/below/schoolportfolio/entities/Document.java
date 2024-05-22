package dev.below.schoolportfolio.entities;

import javafx.beans.property.SimpleStringProperty;

public class Document {
    private final SimpleStringProperty document;

    public Document(String document) {
        this.document = new SimpleStringProperty(document);
    }

    public String getDocument() {
        return document.get();
    }

    public void setDocument(String document) {
        this.document.set(document);
    }
}
