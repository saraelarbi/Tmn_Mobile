/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;



import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.mycompany.myapp.entities.Produit;
import java.util.ArrayList;
import com.mycompany.myapp.service.ProduitService;
/**
 *
 * @author 21693
 */

public class Produit_list extends BaseForm{
    
     Form current;

    public Produit_list(Resources res) {
        super("NewsFeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        add(new Label("Ajouter Produit"));
        getTitleArea().setUIID("Container");
        setTitle("List Produit");
        getContentPane().setScrollVisible(false);
        tb.addSearchCommand(e -> {

        });

        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();

      
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
        
        RadioButton partage = RadioButton.createToggle("Ajouter", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        partage.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

           Produi_add a = new Produi_add(res);
            a.show();
            refreshTheme();
        });
        
        
            mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

           Produit_list a = new Produit_list(res);
            a.show();
            refreshTheme();
        });

               
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        mesListes.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(mesListes, arrow);
        });
        bindButtonselection(mesListes, arrow);
        //bindButtonselection(liste, arrow);
        bindButtonselection(partage, arrow);
        //   special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        ArrayList<Produit> ListProd = ProduitService.getInstance().getAllPruducts();
        for (Produit Prod : ListProd) {
            String urlImage = "titanic.png";
           Image PlaceHolder = Image.createImage(120, 90);
            EncodedImage enc = EncodedImage.createFromImage(PlaceHolder, false);
            URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
            addButton(urlim, Prod,res);

            ScaleImageLabel image = new ScaleImageLabel(urlim);
            Container contImage = new Container();
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        }

    }

    private void addTab(Tabs swipe, Label s1, Image image, String string, String string0, Resources res) {
        /* int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

        if (image.getHeight() < size) {
            image = image.scaledHeight(size);
        }

        if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);

        }
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overLay = new Label("", "ImageOverlay");
        Container page1
                = LayeredLayout.encloseIn(
                        imageScale,
                        overLay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(string0, "LargeWhiteText"),
                                        s1
                                )
                        )
                );
        swipe.addTab("", res.getImage("titanic.png"), page1);*/
    }

    private void bindButtonselection(Button mesListes, Label arrow) {
        mesListes.addActionListener(e -> {
            if (mesListes.isSelected()) {
                updateArrowPosition(mesListes, arrow);
            }

        });
    }

    private void updateArrowPosition(Button mesListes, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, mesListes.getX() + mesListes.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();
    }

    private void addButton(Image img, Produit Prod,Resources res) {

        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);

         Button image = new Button(img.fill(width, height));
        image.setUIID("Label");

        Container cont = BorderLayout.west(image);

        Label NomProdtxt = new Label("" + Prod.getNom(), "NewsTopLine");
        Label TypeProdtxt = new Label("" + Prod.getType(), "NewsTopLine");
        Label PrixProdtxt = new Label("" + Prod.getPrix(), "NewsTopLine");
        Label StockProdtxt = new Label("" + Prod.getStock(), "NewsTopLine");
       
         Label Images = new Label(""+Prod.getImage());
        createLineSeparator();
     
        Label lsup = new Label("");
        lsup.setUIID("NewsTopLine");
        Style supStyle = new Style(lsup.getUnselectedStyle());
        supStyle.setFgColor(0xf21f1f);

        FontImage suppImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supStyle);
        lsup.setIcon(suppImage);
        lsup.setTextPosition(RIGHT);
        lsup.addPointerPressedListener(l -> { 
              
        Dialog dig = new Dialog("Supprimer"); 

        if (dig.show("Suppression", "Vous Voulez Supprimer ce Produit ? ", "Non", "Oui")) {
            dig.dispose();

        } else {
            dig.dispose();
            if(ProduitService.getInstance().deleteProduit(Prod.getIdProduit())){
                System.out.println("done");
            new Produit_list(res).show();
        }
        }
        });
    
        cont.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                BoxLayout.encloseX(NomProdtxt),
                BoxLayout.encloseX(TypeProdtxt),
                BoxLayout.encloseX(PrixProdtxt),
                BoxLayout.encloseX(StockProdtxt, lsup)));

        add(cont);

    }
    
}
