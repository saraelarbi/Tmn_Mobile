/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.ImageViewer;
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
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import entities.Publication;
import java.io.IOException;
import java.util.ArrayList;
import services.ServicePublication;

/**
 *
 * @author ffsga
 */
public class ListPublication extends BaseForm{
    
     Form current;

    public ListPublication(Resources res) {
        super("Gestion Publications:", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);
        tb.addSearchCommand(e -> {

        });

        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();

       // addTab(swipe, s1, res.getImage("imagenotfound.png"), "", "", res);
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
        RadioButton liste = RadioButton.createToggle("", barGroup);
        liste.setUIID("SelectBar");
        
         Label lAjout = new Label("Ajouter");//test
        lAjout.setUIID("NewsTopLine");
        Style AjoutStyle = new Style(lAjout.getUnselectedStyle());
        AjoutStyle.setFgColor(0x0bc152);
         FontImage AjoutImage = FontImage.createMaterial(FontImage.MATERIAL_ADD, AjoutStyle);
        lAjout.setIcon(AjoutImage); //test button
        
        
        
        RadioButton partage = RadioButton.createToggle("Ajouter", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        lAjout.addPointerPressedListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

           AddPublication a = new AddPublication(res);
            a.show();
            refreshTheme();
        });
        
        
            mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

           ListPublication a = new ListPublication(res);
            a.show();
            refreshTheme();
        });

                   liste.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

           AddPublication a = new AddPublication(res);
            a.show();
            refreshTheme();
        });
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, lAjout),
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
        bindButtonselection(liste, arrow);
        bindButtonselection(partage, arrow);
        //   special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        ArrayList<Publication> ListPub = ServicePublication.getInstance().getAllPublication();
        for (Publication Pub : ListPub) {
            String urlImage = "imagenotfound.png";
            Image PlaceHolder = Image.createImage(120, 90);
            EncodedImage enc = EncodedImage.createFromImage(PlaceHolder, false);
            URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
            addButton(urlim, Pub,res);

            ScaleImageLabel image = new ScaleImageLabel(urlim);
            Container contImage = new Container();
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        }

    }

    private void addTab(Tabs swipe, Label s1, Image image, String string, String string0, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

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
        swipe.addTab("", res.getImage("imagenotfound.png"), page1);
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

    private void addButton(Image img, Publication Pub,Resources res) {

         int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        /*
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Label L1 = new Label("TEST"); */
        String url = "/"+Pub.getImage_Pub();
        EncodedImage enc=null;
         try {
             enc = EncodedImage.create("/"+Pub.getImage_Pub()); // file://C:/Users/ffsga/Documents/TMN/FirstProject/public/uploads/images/
         } catch (IOException ex) {
            
         }
         Image imgs = URLImage.createToStorage(enc,url, url,URLImage.RESIZE_SCALE);
         ImageViewer   imgv = new ImageViewer(imgs);
         Button image = new Button(imgs.fill(width, height));
        
        Container cont = BorderLayout.west(image);

        Label DatePubtxt = new Label("" + Pub.getDate_Pub(), "NewsTopLine2");
        Label TitrePubtxt = new Label("" + Pub.getTitre_Pub(), "NewsTopLine");
        Label DescPubtxt = new Label("" + Pub.getDesc_Pub(), "NewsTopLine");
        Label SourcePubtxt = new Label("" + Pub.getSource_Pub(), "NewsTopLine");
        Label CategoriePubtxt = new Label("" + Pub.getCategorie_Pub(), "NewsTopLine");
       
         Label Images = new Label(""+Pub.getImage_Pub());
        createLineSeparator();
       
          /* Image imge;
                try {
                    imge = Image.createImage("file://C:/Users/ffsga/Documents/TMN/FirstProject/public/uploads/images/1b53b5931e8fafdb7db7dbd54e866da7.jpg").scaledWidth(Math.round(Display.getInstance().getDisplayWidth()));
                                        Images.setIcon(img);
                                        

                } catch (IOException ex) {
                }  */
        //supprimer
        Label lsup = new Label("");
        lsup.setUIID("NewsTopLine");
        Style supStyle = new Style(lsup.getUnselectedStyle());
        supStyle.setFgColor(0xf21f1f);

        FontImage suppImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supStyle);
        lsup.setIcon(suppImage);
        lsup.setTextPosition(RIGHT);
        lsup.addPointerPressedListener((ActionEvent l) -> { 
              
        Dialog dig = new Dialog("Supprimer"); 

        if (Dialog.show("Suppression", "Vous Voulez Supprimer cet Publication ? ", "Non", "Oui")) {
            dig.dispose();
        } else {
            dig.dispose();
            if(ServicePublication.getInstance().deletePublication(Pub.getIdPub())){
                System.out.println("done");
            new ListPublication(res).show();
        }
        }
        });
        //Modifier
        Label lModifier = new Label("");
        lModifier.setUIID("NewsTopLine");
        Style ModifierStyle = new Style(lModifier.getUnselectedStyle());
        ModifierStyle.setFgColor(0x0bc152);

        FontImage ModifierImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, ModifierStyle);
        lModifier.setIcon(ModifierImage);
        lModifier.setTextPosition(LEFT);
        lModifier.addPointerPressedListener(l -> {
            //System.out.println("hello update");
            new ModifierPublication(res,Pub).show();
        });
    
        cont.add(BorderLayout.CENTER, BoxLayout.encloseY(
                BoxLayout.encloseX(DatePubtxt),
                BoxLayout.encloseX(TitrePubtxt),
                BoxLayout.encloseX(DescPubtxt),
                BoxLayout.encloseX(SourcePubtxt),
                BoxLayout.encloseX(CategoriePubtxt,lModifier, lsup)));

        add(cont);

    }
    
}
