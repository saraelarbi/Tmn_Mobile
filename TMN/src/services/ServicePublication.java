/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Publication;
import utils.Statics;
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
 * @author ffsga
 */
public class ServicePublication {
     public ArrayList<Publication> Publications;

    public static ServicePublication instance = null;
    public boolean resultOK = true;
    private ConnectionRequest req;

    private ServicePublication() {
        req = new ConnectionRequest();
    }

    public static ServicePublication getInstance() {
        if (instance == null) {
            instance = new ServicePublication();
        }
        return instance;
    }

    public boolean AddPublication(Publication Pub) {
        String url = Statics.BASE_URL + "/publication/addJSON?datePub=" + Pub.getDate_Pub() + "&titrePub=" + Pub.getTitre_Pub() + "&descPub=" + Pub.getDesc_Pub() + "&sourcePub=" + Pub.getSource_Pub() + "&categoriePub=" + Pub.getCategorie_Pub() + "&imagePub=" + Pub.getImage_Pub();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("Publication == " + str);

        }
        );

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Publication> parsePub(String jsonText) throws ParseException  {

        try {

            Publications = new ArrayList<>();

            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Publication Pubs = new Publication();
                         

                String DatePB = obj.get("datePub").toString();

                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                String str2 = formater.format(DatePB);
                
                //LocalDate datepub1 = LocalDate.parse(str2);
               // Date datepub1 = Date.valueOf(str2);
                float id = Float.parseFloat(obj.get("idpub").toString());                        
                Pubs.setIdPub((int)id); 
                Date datepub1 = formater.parse(str2);
                Pubs.setDate_Pub(datepub1);
                Pubs.setTitre_Pub(obj.get("titrePub").toString());
                Pubs.setDesc_Pub(obj.get("descPub").toString());
                Pubs.setSource_Pub(obj.get("sourcePub").toString());
                Pubs.setCategorie_Pub(obj.get("categoriePub").toString());
                Pubs.setImage_Pub(obj.get("imagePub").toString());


                Publications.add(Pubs);
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }

        return Publications;
    }

    
    public ArrayList<Publication> getAllPublication() {
        String url = Statics.BASE_URL + "/publication/displayJSON/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

               
                try {
                    Publications = parsePub(new String(req.getResponseData()));
                } catch (ParseException ex) {
                   
                }
                

                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Publications;
    }

    //delete
    public boolean deletePublication(int idPub) {
        String url = Statics.BASE_URL + "/publication/deleteJSON/"+idPub;
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
    
    public void modifierPublication(Publication Pub) {
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL + "/publication/updateJSON/"+ Pub.getIdPub() + "?datePub=" + Pub.getDate_Pub() + "&titrePub=" + Pub.getTitre_Pub() + "&descPub=" + Pub.getDesc_Pub() + "&sourcePub=" + Pub.getSource_Pub() + "&categoriePub=" + Pub.getCategorie_Pub() + "&imagePub=" + Pub.getImage_Pub();
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    } 
    
}
