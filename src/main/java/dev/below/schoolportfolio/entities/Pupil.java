package dev.below.schoolportfolio.entities;

import java.time.LocalDateTime;

public class Pupil {
    private String name;
    private String surname;
    private LocalDateTime birthDate;
    private String grade;
    private String favouriteSubject;

    public Pupil(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Pupil(String name, String surname, LocalDateTime birthDate, String grade, String favouriteSubject) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.grade = grade;
        this.favouriteSubject = favouriteSubject;
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

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
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
}
