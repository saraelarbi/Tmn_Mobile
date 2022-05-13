package com.firstproject.gui.back.commande;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.firstproject.entities.Commande;
import com.firstproject.services.CommandeService;

public class Manage extends Form {


    Commande currentCmd;

    Label qtiteLabel, methodeLabel,etatLabel,totalLabel;
    TextField qtiteTF,methodeTF,etatTF,totalTF;



    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentCmd == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentCmd = ShowAll.currentCmd;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {


        qtiteLabel = new Label("Quantite : ");
        qtiteLabel.setUIID("labelDefault");
        qtiteTF = new TextField();
        qtiteTF.setHint("quantite");
        
        methodeLabel = new Label("Methode de paiement : ");
        methodeLabel.setUIID("labelDefault");
        methodeTF = new TextField();
        methodeTF.setHint("methodedepaiement");
        
        etatLabel = new Label("Etat : ");
        etatLabel.setUIID("labelDefault");
        etatTF = new TextField();
        etatTF.setHint("etat");
        
        totalLabel = new Label("Total : ");
        totalLabel.setUIID("labelDefault");
        totalTF = new TextField();
        totalTF.setHint("total");


       


        if (currentCmd == null) {


            manageButton = new Button("Ajouter");
        } else {
            qtiteTF.setText(String.valueOf(currentCmd.getQuantite()));
            methodeTF.setText(currentCmd.getMethode_de_paiement());
            etatTF.setText(currentCmd.getEtat());
            totalTF.setText(String.valueOf(currentCmd.getTotal()));
           


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                qtiteLabel, qtiteTF,
                methodeLabel, methodeTF,
                etatLabel, etatTF,
                totalLabel, totalTF,

                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentCmd == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = CommandeService.getInstance().add(new Commande(

                                    Integer.parseInt(qtiteTF.getText()),
                                    methodeTF.getText(),
                                    etatTF.getText(),
                                    Float.parseFloat(totalTF.getText()))
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "commande ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de commande. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = CommandeService.getInstance().edit(new Commande(
                                    currentCmd.getNumCmd(),


                                   
                                    Integer.parseInt(qtiteTF.getText()),
                                    methodeTF.getText(),
                                    etatTF.getText(),
                                    Float.parseFloat(totalTF.getText()))

                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "commande modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de la cmd. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }



    }

    private void showBackAndRefresh() {
        ((ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (qtiteTF.getText().equals("")) {
            Dialog.show("Avertissement", "Quantite vide", new Command("Ok"));
            return false;
        }
        if (methodeTF.getText().equals("")) {
            Dialog.show("Avertissement", "Methode de paiement vide", new Command("Ok"));
            return false;
        }


        if (etatTF.getText() == null) {
            Dialog.show("Avertissement", "Veuillez saisir l'etat", new Command("Ok"));
            return false;
        }


        if (totalTF.getText().equals("")) {
            Dialog.show("Avertissement", "Categorie vide", new Command("Ok"));
            return false;
        }
        

        return true;
    }
}