package com.firstproject.gui.front.blog;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.components.ShareButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.firstproject.entities.Blog;
import com.firstproject.services.BlogService;
import com.firstproject.utils.Statics;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowAll extends Form {

    public static Blog currentBlog = null;
    Form previous;
    Resources theme = UIManager.initFirstTheme("/theme");
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label descriptionLabel, dateDePublicationLabel, imageLabel, categorieLabel;
    ImageViewer imageIV;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Blogs", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Blog> listBlogs = BlogService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher blog par Description");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Blog blog : listBlogs) {
                if (blog.getDescription().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeBlogModel(blog);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listBlogs.size() > 0) {
            for (Blog blog : listBlogs) {
                Component model = makeBlogModel(blog);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentBlog = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Blog blog) {
        Container blogModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        blogModel.setUIID("containerRounded");


        descriptionLabel = new Label("Description : " + blog.getDescription());
        descriptionLabel.setUIID("labelDefault");

        dateDePublicationLabel = new Label("DateDePublication : " + new SimpleDateFormat("dd-MM-yyyy").format(blog.getDateDePublication()));
        dateDePublicationLabel.setUIID("labelDefault");

        imageLabel = new Label("Image : " + blog.getImage());
        imageLabel.setUIID("labelDefault");

        categorieLabel = new Label("Categorie : " + blog.getCategorie());
        categorieLabel.setUIID("labelDefault");

        if (blog.getImage() != null) {
            String url = Statics.BLOG_IMAGE_URL + blog.getImage();
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

        blogModel.addAll(
                imageIV,
                descriptionLabel, dateDePublicationLabel, categorieLabel
        );

        return blogModel;
    }

    private Component makeBlogModel(Blog blog) {

        Container blogModel = makeModelWithoutButtons(blog);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentBlog = blog;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce blog ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = BlogService.getInstance().delete(blog.getId());

                if (responseCode == 200) {
                    currentBlog = null;
                    dlg.dispose();
                    blogModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du blog. Code d'erreur : " + responseCode, new Command("Ok"));
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
        btnAfficherScreenshot.addActionListener(listener -> share(blog));

        btnsContainer.add(BorderLayout.EAST, btnAfficherScreenshot);

        blogModel.add(btnsContainer);

        return blogModel;
    }

    private void share(Blog blog) {
        Form form = new Form(new BoxLayout(BoxLayout.Y_AXIS));
        form.add(makeModelWithoutButtons(blog));
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
        Form screenShotForm = new Form("Partager blog", new BoxLayout(BoxLayout.Y_AXIS));
        ImageViewer screenshotViewer = new ImageViewer(screenshot.fill(1000, 2000));
        screenshotViewer.setFocusable(false);
        screenshotViewer.setUIID("screenshot");
        ShareButton btnPartager = new ShareButton();
        btnPartager.setText("Partager ");
        btnPartager.setTextPosition(LEFT);
        btnPartager.setImageToShare(imageFile, "image/png");
        btnPartager.setTextToShare(blog.toString());
        screenShotForm.addAll(screenshotViewer, btnPartager);
        screenShotForm.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        screenShotForm.show();
    }

}