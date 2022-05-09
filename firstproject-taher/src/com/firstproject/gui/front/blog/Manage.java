package com.firstproject.gui.front.blog;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.firstproject.entities.Blog;
import com.firstproject.services.BlogService;
import com.firstproject.utils.Statics;

import java.io.IOException;

public class Manage extends Form {


    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;


    Blog currentBlog;

    Label descriptionLabel, imageLabel, categorieLabel;
    TextField descriptionTF, imageTF, categorieTF;
    PickerComponent dateDePublicationTF;


    ImageViewer imageIV;
    Button selectImageButton;

    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentBlog == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentBlog = ShowAll.currentBlog;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {


        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez le description");


        dateDePublicationTF = PickerComponent.createDate(null).label("DateDePublication");


        categorieLabel = new Label("Categorie : ");
        categorieLabel.setUIID("labelDefault");
        categorieTF = new TextField();
        categorieTF.setHint("Tapez le categorie");


        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (currentBlog == null) {

            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));


            manageButton = new Button("Ajouter");
        } else {
            descriptionTF.setText(currentBlog.getDescription());
            dateDePublicationTF.getPicker().setDate(currentBlog.getDateDePublication());

            categorieTF.setText(currentBlog.getCategorie());


            if (currentBlog.getImage() != null) {
                selectedImage = currentBlog.getImage();
                String url = Statics.BLOG_IMAGE_URL + currentBlog.getImage();
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
                descriptionLabel, descriptionTF,
                dateDePublicationTF,

                categorieLabel, categorieTF,

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

        if (currentBlog == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = BlogService.getInstance().add(
                            new Blog(


                                    descriptionTF.getText(),
                                    dateDePublicationTF.getPicker().getDate(),
                                    selectedImage,
                                    categorieTF.getText()
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Blog ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de blog. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = BlogService.getInstance().edit(
                            new Blog(
                                    currentBlog.getId(),


                                    descriptionTF.getText(),
                                    dateDePublicationTF.getPicker().getDate(),
                                    selectedImage,
                                    categorieTF.getText()

                            ), imageEdited
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Blog modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de blog. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }


        if (dateDePublicationTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la dateDePublication", new Command("Ok"));
            return false;
        }


        if (categorieTF.getText().equals("")) {
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