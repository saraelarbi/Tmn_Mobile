/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Publicite;
import com.mycompany.myapp.services.PubliciteService;

/**
 *
 * @author ffsga
 */
public class ModifierPublicite extends BaseForm{
    
    Form current;
    public ModifierPublicite(Resources res , Publicite Pub) {
         super("Publication",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modifier Publication");
        getContentPane().setScrollVisible(false);
        tb.addSearchCommand(e -> {
        });
        
        
//        super.addSideMenu(res);
        
Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();

       // addTab(swipe, s1, res.getImage("event.png"), "", "", res);
        // deb 
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

       // rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("List", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Categorie", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Ajouter", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        partage.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

           Publicite_add a = new Publicite_add(res);
            a.show();
            refreshTheme();
        });
        
        
            mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

           Publicite_list a = new Publicite_list(res);
            a.show();
            refreshTheme();
        });

                   liste.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

           Publicite_add a = new Publicite_add(res);
            a.show();
            refreshTheme();
        });
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonselection(mesListes, arrow);
        bindButtonselection(liste, arrow);
        bindButtonselection(partage, arrow);
        //   special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        Picker datePub = new Picker();
        datePub.setDate(Pub.getDate_creation());
        datePub.setType(Display.PICKER_TYPE_DATE);
        TextField DomainePub = new TextField(Pub.getDomaine(), "Domaine" , 20 , TextField.ANY);
        TextField descriptionPub = new TextField(Pub.getDescription(), "Description" , 20 , TextField.ANY);
        TextField lettrePub = new TextField(Pub.getLettre_motivation() , "lettre de motivation" , 20 , TextField.ANY);
//        ComboBox typepubCombo = new ComboBox();
//        
//        typepubCombo.addItem("type 1");
//        typepubCombo.addItem("type 2");
//        typepubCombo.addItem("type 3");
//        typepubCombo.addItem("type 4");
//        typepubCombo.addItem("type 5");
        
//        
//        if("Sport".equals(Pub.getCategorie_Pub()) ) {
//            typepubCombo.setSelectedIndex(0);
//        }
//        else if("Politique".equals(Pub.getCategorie_Pub()) ) {
//            typepubCombo.setSelectedIndex(1);
//        }
//        else if(Pub.getCategorie_Pub() == "Santé" ) {
//            typepubCombo.setSelectedIndex(2);
//        }
//        else if(Pub.getCategorie_Pub() == "Affaire" ) {
//            typepubCombo.setSelectedIndex(3);
//        }
//        else 
//            CatPubCombo.setSelectedIndex(4);
            
        datePub.setUIID("TextFieldBlack");       
        DomainePub.setUIID("NewsTopLine");
        descriptionPub.setUIID("NewsTopLine");
        lettrePub.setUIID("NewsTopLine");
//        typepubCombo.setUIID("TextFieldBlack");
        
        
        
    
       
        DomainePub.setSingleLineTextArea(true);
        descriptionPub.setSingleLineTextArea(true);
        lettrePub.setSingleLineTextArea(true);
        
        
        
         addStringValue("date_Pub : ", datePub);
         addStringValue("DomainePub : ", DomainePub);
         addStringValue("descriptionPub : ", descriptionPub);
         addStringValue("lettrePub : ", lettrePub);
//         addStringValue("typepubCombo : ", typepubCombo);
         
        
        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");
        addStringValue("", btnModifier);
       
       //Event onclick btnModifer
       
            btnModifier.addPointerPressedListener((e) -> {
            try {
                if (DomainePub.getText().equals("") || descriptionPub.getText().equals("") || lettrePub.getText().equals(""))  {
                    Dialog.show("Erreur", "Veuillez vérifier les données ", "Annuler", "OK");
                } else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                    
                   
                    Pub.setDate_creation(Pub.getDate_creation()); 
                    Pub.setDomaine(DomainePub.getText());
                    Pub.setDescription(descriptionPub.getText());
                    Pub.setLettre_motivation(lettrePub.getText());
//                    Pub.setId_typepub((String) CatPubCombo.getSelectedItem());
                    Pub.setImage(Pub.getImage());
                   
//                    
//               Sms SMS=new Sms();
//     SMS.SendSMS("un nouvel Evénement a été ajouté, veuillez consulter notre application pour plus de détails");
//                   

                    PubliciteService.getInstance().modifierPublicite(Pub);
                    iDialog.dispose();
                    new Publicite_list(res).show();
                }
            } catch (Exception ex) {
            }
        });

        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                e -> current.showBack()); // Revenir vers l'interface précédente
        
        
    }
    
    private void addStringValue(String s, Component V) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).add(BorderLayout.CENTER, V));
        add(createLineSeparator(0xeeeeee));

    }
    
    private void bindButtonselection(Button mesListes, Label arrow) {
        mesListes.addActionListener(e -> {
            if (mesListes.isSelected()) {
                updateArrowPosition(mesListes, arrow);
            }

        });
    }

    private void updateArrowPosition(Button partage, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, partage.getX() + partage.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();
    }
}
