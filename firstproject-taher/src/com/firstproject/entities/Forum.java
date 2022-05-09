package com.firstproject.entities;

public class Forum {

    private int id;
    private String questions;
    private int etat;

    public Forum() {
    }

    public Forum(int id, String questions, int etat) {
        this.id = id;
        this.questions = questions;
        this.etat = etat;
    }

    public Forum(String questions, int etat) {
        this.questions = questions;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }


    @Override
    public String toString() {
        return "Forum : " +
                "id=" + id
                + ", Questions=" + questions
                + ", Etat=" + etat
                ;
    }


}