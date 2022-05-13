package com.firstproject.gui.back.produit;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.firstproject.entities.Produit;
import com.firstproject.services.ProduitService;
import com.firstproject.utils.Statics;

import java.io.IOException;

public class Manage extends Form {


    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;


    Produit currentProduit;

    Label nomLabel, imageLabel, typeLabel, prixLabel, stockLabel;
    TextField nomTF, imageTF, typeTF,prixTF ,stockTF;


    ImageViewer imageIV;
    Button selectImageButton;

    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentProduit == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentProduit = ShowAll.currentProduit;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {


        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomTF = new TextField();
        nomTF.setHint("Nom");




        typeLabel = new Label("Type : ");
        typeLabel.setUIID("labelDefault");
        typeTF = new TextField();
        typeTF.setHint("type");
        
      



        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");
        
         prixLabel = new Label("Prix : ");
        prixLabel.setUIID("labelDefault");
        prixTF = new TextField();
        prixTF.setHint("prix");
        
         stockLabel = new Label("Stock : ");
        stockLabel.setUIID("labelDefault");
        stockTF = new TextField();
        stockTF.setHint("stock");

        if (currentProduit == null) {

            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));


            manageButton = new Button("Ajouter");
        } else {
            nomTF.setText(currentProduit.getNom());
            typeTF.setText(currentProduit.getType());
            prixTF.setText(String.valueOf(currentProduit.getPrix()));
            stockTF.setText(String.valueOf(currentProduit.getStock()));



            if (currentProduit.getImage() != null) {
                selectedImage = currentProduit.getImage();
                String url = Statics.PRODUIT_IMAGE_URL + currentProduit.getImage();
                Image image = URLImage.createToStorage(
                        EncodedImage.createFromImage(theme.getImage("default.jpg").fill(1100, 500), false),
                        url,
                        url,
                        URLImage.RESIZE_SCALE
                );
                imageIV = new ImageViewer(image);
            } else {
                imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
            }
            imageIV.setFocusable(false);


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                imageLabel, imageIV,
                selectImageButton,
                nomLabel, nomTF,
                typeLabel,typeTF,
                prixLabel,prixTF,
                stockLabel,stockTF,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        selectImageButton.addActionListener(a -> {
            selectedImage = Capture.capturePhoto(900, -1);
            try {
                imageEdited = true;
                imageIV.setImage(Image.createImage(selectedImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectImageButton.setText("Modifier l'image");
        });

        if (currentProduit == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ProduitService.getInstance().add(new Produit(
                                    

                                    nomTF.getText(),
                                    typeTF.getText(),
                                    selectedImage,
                                    Float.parseFloat(prixTF.getText()),
                                    Integer.parseInt(stockTF.getText())
                                    
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "produit ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout du produit. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ProduitService.getInstance().edit(new Produit(
                                    currentProduit.getIdProduit(),


                                    nomTF.getText(),
                                    typeTF.getText(),
                                    selectedImage,
                                    Float.parseFloat(prixTF.getText()),
                                    Integer.parseInt(stockTF.getText())

                            ), imageEdited
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Produit modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification du produit. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }


        if (typeTF.getText() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la dateDePublication", new Command("Ok"));
            return false;
        }


        if (prixTF.getText().equals("")) {
            Dialog.show("Avertissement", "Categorie vide", new Command("Ok"));
            return false;
        }
         if (stockTF.getText().equals("")) {
            Dialog.show("Avertissement", "Categorie vide", new Command("Ok"));
            return false;
        }


           if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }


        return true;
    }
}