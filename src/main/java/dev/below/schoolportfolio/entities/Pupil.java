package dev.below.schoolportfolio.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pupil {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String grade;
    private String favouriteSubject;
    private String pathToPhoto;
    private List<String> documents;

    public Pupil(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.documents = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getFavouriteSubject() {
        return favouriteSubject;
    }

    public void setFavouriteSubject(String favouriteSubject) {
        this.favouriteSubject = favouriteSubject;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(String pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }

    public void addDocument(String document) {
        documents.add(document);
    }

    public void removeDocument(String document) {
        documents.remove(document);
    }

    public List<String> getDocuments() {
        return documents;
    }
}
