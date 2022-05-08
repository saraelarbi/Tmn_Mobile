/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;



import com.mycompany.myapp.entities.Publicite;


import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.utils.Statics;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author 21693
 */

public class PubliciteService {
     public ArrayList<Publicite> Publicites;

    public static PubliciteService instance = null;
    public boolean resultOK = true;
    private ConnectionRequest req;

    private PubliciteService() {
        req = new ConnectionRequest();
    }

    public static PubliciteService getInstance() {
        if (instance == null) {
            instance = new PubliciteService();
        }
        return instance;
    }

    public boolean AddPublicite(Publicite Pub) {
        String url = Statics.BASE_URL + "/publicite/addPubliciteJSON?dateCreation=" + Pub.getDate_creation()+ "&domaine=" + Pub.getDomaine()+ "&description=" + Pub.getDescription()+ "&lettreMotivation=" + Pub.getLettre_motivation()+ "&image=" + Pub.getImage();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("Publicite == " + str);

        }
        );

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Publicite> parsePub(String jsonText) throws ParseException  {

        try {

            Publicites = new ArrayList<>();

            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Publicite Pubs = new Publicite();
                         

                String DatePB = obj.get("dateCreation").toString();

                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                String str2 = formater.format(DatePB);
                
                //LocalDate datepub1 = LocalDate.parse(str2);
               // Date datepub1 = Date.valueOf(str2);
                float id = Float.parseFloat(obj.get("idPub").toString());                        
                Pubs.setId_pub((int)id); 
                Date datepub1 = formater.parse(str2);
                Pubs.setDate_creation(datepub1);
                Pubs.setDomaine(obj.get("domaine").toString());
                Pubs.setDescription(obj.get("description").toString());
                Pubs.setLettre_motivation(obj.get("lettreMotivation").toString());
//                Pubs.setCategorie_Pub(obj.get("categoriePub").toString());
                Pubs.setImage(obj.get("image").toString());


                Publicites.add(Pubs);
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }

        return Publicites;
    }

    
    public ArrayList<Publicite> getAllPublicite() {
        String url = Statics.BASE_URL + "/publicite/publiciteJSON/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

               
                try {
                    Publicites = parsePub(new String(req.getResponseData()));
                } catch (ParseException ex) {
                   
                }
                

                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Publicites;
    }

    //delete
    public boolean deletePublicite(int idPub) {
        String url = Statics.BASE_URL + "/publicite/removepubliciteJSON/"+idPub;
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
    
    public void modifierPublicite(Publicite Pub) {
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL + "/publicite/modifPubliciteJSON/"+ Pub.getId_pub()+ "?datePub="+ Pub.getDate_creation()+ "&domaine=" + Pub.getDomaine()+ "&description=" + Pub.getDescription()+ "&lettreMotivation=" + Pub.getLettre_motivation()+ "&image=" + Pub.getImage();
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    } 
    
}
