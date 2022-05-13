package com.firstproject.gui.front.produit;

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
import com.firstproject.entities.Produit;
import com.firstproject.services.ProduitService;
import com.firstproject.utils.Statics;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowAll extends Form {

    public static Produit currentProduit = null;
    Form previous;
    Resources theme = UIManager.initFirstTheme("/theme");
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label nomLabel, typeLabel, imageLabel, prixLabel , stockLabel;
    ImageViewer imageIV;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Produits", new BoxLayout(BoxLayout.Y_AXIS));
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
        addBtn = new Button("");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);


        ArrayList<Produit> listProduits = ProduitService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher produit par nom");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Produit prod : listProduits) {
                if (prod.getNom().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeProduitModel(prod);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listProduits.size() > 0) {
            for (Produit prod : listProduits) {
                Component model = makeProduitModel(prod);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentProduit = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Produit Prod) {
        Container prodModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        prodModel.setUIID("containerRounded");


        nomLabel = new Label("Nom : " + Prod.getNom());
        nomLabel.setUIID("labelDefault");
        
        typeLabel = new Label("Type : " + Prod.getType());
        nomLabel.setUIID("labelDefault");
        
        imageLabel = new Label("Image : " + Prod.getImage());
        imageLabel.setUIID("labelDefault");
        
        prixLabel = new Label("Prix : " + Prod.getNom());
        prixLabel.setUIID("labelDefault");

        
        stockLabel = new Label("Stock : " + Prod.getStock());
        stockLabel.setUIID("labelDefault");

        if (Prod.getImage() != null) {
            String url = Statics.PRODUIT_IMAGE_URL + Prod.getImage();
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

        prodModel.addAll(
                imageIV,
                nomLabel, typeLabel, prixLabel , stockLabel
        );

        return prodModel;
    }

    private Component makeProduitModel(Produit prod) {

        Container ProduitModel = makeModelWithoutButtons(prod);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

       editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentProduit = prod;
            new Manage(this).show();
        });

         deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce produit ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ProduitService.getInstance().delete(prod.getIdProduit());

                if (responseCode == 200) {
                    currentProduit = null;
                    dlg.dispose();
                    ProduitModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du produit. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

      // btnsContainer.add(BorderLayout.WEST, editBtn);
      //  btnsContainer.add(BorderLayout.CENTER, deleteBtn);


       

        ProduitModel.add(btnsContainer);

        return ProduitModel;
    }

   
}