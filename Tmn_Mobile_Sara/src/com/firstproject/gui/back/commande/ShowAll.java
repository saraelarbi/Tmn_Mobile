package com.firstproject.gui.back.commande;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.firstproject.entities.Commande;
import com.firstproject.services.CommandeService;

import java.util.ArrayList;

public class ShowAll extends Form {

    public static Commande currentCmd = null;
    Form previous;
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label qtiteLabel, methodeLabel,etatLabel,totalLabel;
    TextField qtiteTF,methodeTF,etatTF,totalTF;  
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Commande", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Commande> listForums = CommandeService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher Commande par methode de paiement");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Commande cmd : listForums) {
                if (cmd.getMethode_de_paiement().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeForumModel(cmd);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listForums.size() > 0) {
            for (Commande forum : listForums) {
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
            currentCmd = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Commande cmd) {
        Container commandeModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        commandeModel.setUIID("containerRounded");


        qtiteLabel = new Label("Quantite : " + cmd.getQuantite());
        qtiteLabel.setUIID("labelDefault");

        methodeLabel = new Label("Methode de paiement : " + cmd.getMethode_de_paiement());
        methodeLabel.setUIID("labelDefault");
        
        etatLabel = new Label("Etat : " + cmd.getEtat());
        etatLabel.setUIID("labelDefault");
        
        totalLabel = new Label("Total : " + cmd.getTotal());
        totalLabel.setUIID("labelDefault");
        
        


        commandeModel.addAll(

                qtiteLabel, methodeLabel,etatLabel,totalLabel
        );

        return commandeModel;
    }

    private Component makeForumModel(Commande cmd) {

        Container commandeModel = makeModelWithoutButtons(cmd);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentCmd = cmd;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer cette commande ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = CommandeService.getInstance().delete(cmd.getNumCmd());

                if (responseCode == 200) {
                    currentCmd = null;
                    dlg.dispose();
                    commandeModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression de la commande. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        commandeModel.add(btnsContainer);

        return commandeModel;
    }

}