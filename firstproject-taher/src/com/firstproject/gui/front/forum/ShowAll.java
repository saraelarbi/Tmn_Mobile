package com.firstproject.gui.front.forum;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.components.ShareButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.ImageIO;
import com.firstproject.entities.Forum;
import com.firstproject.services.ForumService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class ShowAll extends Form {

    public static Forum currentForum = null;
    Form previous;
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label questionsLabel, etatLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Forums", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);


        ArrayList<Forum> listForums = ForumService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher forum par Questions");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Forum forum : listForums) {
                if (forum.getQuestions().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeForumModel(forum);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listForums.size() > 0) {
            for (Forum forum : listForums) {
                Component model = makeForumModel(forum);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentForum = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Forum forum) {
        Container forumModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        forumModel.setUIID("containerRounded");


        questionsLabel = new Label("Questions : " + forum.getQuestions());
        questionsLabel.setUIID("labelDefault");

        etatLabel = new Label("Etat : " + (forum.getEtat() == 1 ? "True" : "False"));
        etatLabel.setUIID("labelDefault");


        forumModel.addAll(

                questionsLabel, etatLabel
        );

        return forumModel;
    }

    private Component makeForumModel(Forum forum) {

        Container forumModel = makeModelWithoutButtons(forum);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentForum = forum;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce forum ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ForumService.getInstance().delete(forum.getId());

                if (responseCode == 200) {
                    currentForum = null;
                    dlg.dispose();
                    forumModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du forum. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.CENTER, deleteBtn);


        Button btnAfficherScreenshot = new Button("Partager");
        btnAfficherScreenshot.addActionListener(listener -> share(forum));

        btnsContainer.add(BorderLayout.EAST, btnAfficherScreenshot);

        forumModel.add(btnsContainer);

        return forumModel;
    }

    private void share(Forum forum) {
        Form form = new Form(new BoxLayout(BoxLayout.Y_AXIS));
        form.add(makeModelWithoutButtons(forum));
        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
        Image screenshot = Image.createImage(
                com.codename1.ui.Display.getInstance().getDisplayWidth(),
                com.codename1.ui.Display.getInstance().getDisplayHeight()
        );
        form.revalidate();
        form.setVisible(true);
        form.paintComponent(screenshot.getGraphics(), true);
        form.removeAll();
        try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
            ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
        } catch (IOException err) {
            Log.e(err);
        }
        Form screenShotForm = new Form("Partager forum", new BoxLayout(BoxLayout.Y_AXIS));
        ImageViewer screenshotViewer = new ImageViewer(screenshot.fill(1000, 2000));
        screenshotViewer.setFocusable(false);
        screenshotViewer.setUIID("screenshot");
        ShareButton btnPartager = new ShareButton();
        btnPartager.setText("Partager ");
        btnPartager.setTextPosition(LEFT);
        btnPartager.setImageToShare(imageFile, "image/png");
        btnPartager.setTextToShare(forum.toString());
        screenShotForm.addAll(screenshotViewer, btnPartager);
        screenShotForm.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        screenShotForm.show();
    }

}