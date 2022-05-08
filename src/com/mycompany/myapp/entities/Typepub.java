/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author bolba
 */
public class Typepub {
    private int id;
    private String cat;
    
     
    public Typepub(){
    }
    public Typepub(int id, String cat) {
        this.id = id;
        this.cat = cat;
    }

    public Typepub( String cat) {
        this.cat = cat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
    public String getCat() {
        return cat;
    }

    @Override
    public String toString() {
        return "Typepub{" + "id=" + id + ", cat=" + cat + '}';
    }
    
}
