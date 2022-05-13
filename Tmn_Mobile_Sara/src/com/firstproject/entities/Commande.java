package com.firstproject.entities;

public class Commande {
    private int numCmd,quantite;
    private String methode_de_paiement ,etat;
    private float Total;

    public Commande(int numCmd, int quantite, String methode_de_paiement, String etat, float Total) {
        this.numCmd = numCmd;
        this.quantite = quantite;
        this.methode_de_paiement = methode_de_paiement;
        this.etat = etat;
        this.Total = Total;
    }

    public Commande(int quantite, String methode_de_paiement, String etat, float Total) {
        this.quantite = quantite;
        this.methode_de_paiement = methode_de_paiement;
        this.etat = etat;
        this.Total = Total;
    }

    public int getNumCmd() {
        return numCmd;
    }

    public void setNumCmd(int numCmd) {
        this.numCmd = numCmd;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getMethode_de_paiement() {
        return methode_de_paiement;
    }

    public void setMethode_de_paiement(String methode_de_paiement) {
        this.methode_de_paiement = methode_de_paiement;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float Total) {
        this.Total = Total;
    }

    @Override
    public String toString() {
        return "Commande{" + "numCmd=" + numCmd + ", quantite=" + quantite + ", methode_de_paiement=" + methode_de_paiement + ", etat=" + etat + ", Total=" + Total + '}';
    }
    

}
