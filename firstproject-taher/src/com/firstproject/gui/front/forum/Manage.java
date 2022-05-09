package com.firstproject.gui.front.forum;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.firstproject.entities.Forum;
import com.firstproject.services.ForumService;

public class Manage extends Form {


    Forum currentForum;

    Label questionsLabel, etatLabel;
    TextField questionsTF;

    CheckBox etatCB;


    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentForum == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentForum = ShowAll.currentForum;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {


        questionsLabel = new Label("Questions : ");
        questionsLabel.setUIID("labelDefault");
        questionsTF = new TextField();
        questionsTF.setHint("Tapez le questions");


        etatCB = new CheckBox("Etat : ");


        if (currentForum == null) {


            manageButton = new Button("Ajouter");
        } else {
            questionsTF.setText(currentForum.getQuestions());
            if (currentForum.getEtat() == 1) {
                etatCB.setSelected(true);
            }


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                questionsLabel, questionsTF,
                etatCB,

                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentForum == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ForumService.getInstance().add(
                            new Forum(


                                    questionsTF.getText(),
                                    etatCB.isSelected() ? 1 : 0
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Forum ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de forum. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ForumService.getInstance().edit(
                            new Forum(
                                    currentForum.getId(),


                                    questionsTF.getText(),
                                    etatCB.isSelected() ? 1 : 0

                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Forum modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de forum. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (questionsTF.getText().equals("")) {
            Dialog.show("Avertissement", "Questions vide", new Command("Ok"));
            return false;
        }


        return true;
    }
}