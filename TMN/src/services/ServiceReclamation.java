/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.ui.events.ActionListener;
import entities.Publication;
import entities.Reclamation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Statics;

/**
 *
 * @author ffsga
 */
public class ServiceReclamation {
    
     public ArrayList<Reclamation> Reclamations;

    public static ServiceReclamation instance = null;
    public boolean resultOK = true;
    private ConnectionRequest req;

    private ServiceReclamation() {
        req = new ConnectionRequest();
    }

    public static ServiceReclamation getInstance() {
        if (instance == null) {
            instance = new ServiceReclamation();
        }
        return instance;
    }

    public boolean AddReclamation(Reclamation rec) {
        String url = Statics.BASE_URL + "/reclamation/addJSON?description=" + rec.getDescription() + "&idu=" + rec.getIdu() + "&idPub=" + rec.getIdPub() + "&idPod=" + rec.getIdPod() + "&idBlog=" + rec.getIdBlog() + "&etat=" + rec.getEtat();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("Reclamation == " + str);

        }
        );

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Reclamation> parseRec(String jsonText) throws ParseException  {

        try {

            Reclamations = new ArrayList<>();

            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Reclamation recs = new Reclamation();
                    
                float id = Float.parseFloat(obj.get("idreclamation").toString());                        
                recs.setIdreclamation((int)id); 
                // float id1 = Float.parseFloat(obj.get("idu").toString());                        
                recs.setIdu(1); 
                recs.setDescription(obj.get("description").toString());
                
                String strID = (obj.get("idpub").toString());
               float idP = Float.parseFloat(strID.substring(strID.indexOf("=")+1,strID.indexOf(","))); 
               // System.out.println(strID.substring(strID.indexOf("=")+1,strID.indexOf(",")));
                recs.setIdPub((int)idP); 
                recs.setEtat(obj.get("etat").toString());


                Reclamations.add(recs);
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }

        return Reclamations;
    }
    public Publication makePub(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Publication publication = new Publication();
        publication.setIdPub((int) Float.parseFloat(obj.get("idPub").toString()));
        return publication;
    }

    
    public ArrayList<Reclamation> getAllReclamation() {
        String url = Statics.BASE_URL + "/reclamation/displayJSON/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

               
                try {
                    Reclamations = parseRec(new String(req.getResponseData()));
                } catch (ParseException ex) {
                   
                }
                

                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Reclamations;
    }

    //delete
    public boolean deleteReclamation(int idReclamation) {
        String url = Statics.BASE_URL + "/reclamation/deleteJSON/"+idReclamation;
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
    
    public void modifierReclamation(Reclamation rec) {
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL + "/reclamation/updateJSON/"+rec.getIdreclamation()+"?description=" + rec.getDescription() + "&idu=" + rec.getIdu() + "&idPub=" + rec.getIdPub() + "&idPod=" + rec.getIdPod() + "&idBlog=" + rec.getIdBlog() + "&etat=" + rec.getEtat();
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    } 
    
}
