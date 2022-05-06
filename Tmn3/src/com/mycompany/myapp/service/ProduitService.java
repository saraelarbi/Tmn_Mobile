/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.service;


import com.mycompany.myapp.entities.Commande;
import com.mycompany.myapp.entities.Produit;

import com.mycompany.myapp.utils.statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author 21693
 */

public class ProduitService {
     public ArrayList<Produit> Produits;

    public static ProduitService instance = null;
    public boolean resultOK = true;
    private ConnectionRequest req;

    private ProduitService() {
        req = new ConnectionRequest();
    }

    public static ProduitService getInstance() {
        if (instance == null) {
            instance = new ProduitService();
        }
        return instance;
    }

    public boolean AddProduit(Produit Prod) {
        String url = statics.BASE_URL + "/produit/addJSON?nom=" + Prod.getNom()+ "&type=" + Prod.getType()+ "&prix=" + Prod.getPrix()+ "&image=" + Prod.getImage()+ "&stock=" + Prod.getStock();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("Produit == " + str);

        }
        );

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Produit> parseProd(String jsonText) throws ParseException  {

        try {

            Produits = new ArrayList<>();

            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Produit Prods = new Produit();
                  
               
                Prods.setNom(obj.get("nom").toString());
                Prods.setType(obj.get("type").toString());
                Prods.setPrix(Float.parseFloat(obj.get("prix").toString()));
                float stock1=Float.parseFloat(obj.get("stock").toString());
                Prods.setStock((int)stock1);
                float id = Float.parseFloat(obj.get("idproduit").toString());                        
                Prods.setIdProduit((int)id);
                

                Prods.setImage(obj.get("image").toString());


                Produits.add(Prods);
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }

        return Produits;
    }

    
    public ArrayList<Produit> getAllPruducts() {
        String url = statics.BASE_URL + "/produit/displayJSON/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

               
                try {
                    Produits = parseProd(new String(req.getResponseData()));
                } catch (ParseException ex) {
                   
                }
                

                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Produits;
    }

    //delete
    public boolean deleteProduit(int id) {
        String url = statics.BASE_URL + "/produit/deleteJSON/" + id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
     public void modifier(Produit Prod) {
        ConnectionRequest con = new ConnectionRequest();
        String url = statics.BASE_URL + "/produit/updateJSON/"+ Prod.getIdProduit() + "&nom=" + Prod.getNom()+ "&type=" + Prod.getType()+ "&prix=" + Prod.getPrix() + "&image=" + Prod.getImage()+ "&stock=" + Prod.getStock();
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    
}
