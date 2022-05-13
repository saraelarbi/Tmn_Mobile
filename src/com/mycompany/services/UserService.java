/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.AcceuilForm;
import com.mycompany.myapp.SessionManager;
import com.mycompany.myapp.SignInForm;
import com.mycompany.utils.Static;
import java.util.Map;


/**
 *
 * @author Abirn
 */
public class UserService {

    //Singleton
    public static UserService instance = null;
    public static boolean resultOk = true;
    String json;

    //Initialisation du connection request
    private ConnectionRequest req;

    public static UserService getInstance() {

        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public UserService() {
        req = new ConnectionRequest();
    }

    //Register
    public void signup(TextField username, TextField userfname, TextField num, TextField email,TextField adresse ,TextField password, TextField confirmPassword, Picker dtp, Resources res) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String bday = format.format(dtp.getDate());
        String url = "http://127.0.0.1:8000/user/AddJSON?nom=" + username.getText().toString() + "&prenom=" + userfname.getText().toString()
                 + "&numtel=" + Integer.parseInt(num.getText())
                + "&email=" + email.getText().toString() + "&adresse=" + adresse.getText().toString() + "&password=" + password.getText().toString() + "&datenaissance=" + bday;

        req.setUrl(url);

        // Controle de saisie
        if (username.getText().equals(" ") || userfname.getText().equals(" ") ||  num.getText().equals(" ") || email.getText().equals(" ") ||adresse.getText().equals(" ") || password.getText().equals(" ")) {

            Dialog.show("ERREUR", "Veuillez remplir les champs", "OK", null);
        }

        //Execution de l'URL 
        req.addResponseListener((e) -> {

            byte[] data = (byte[]) e.getMetaData();
            String responsableData = new String(data);

            System.out.println("data =>" + responsableData);
        }
        );
        //Aprés l'exécution de l'URL on attend la réponse du serveur 
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    //Login
    public void signin(TextField email, TextField password, Resources rs) {

        String url = "http://127.0.0.1:8000/user/LoginJSON?email=" + email.getText().toString()
                + "&password=" + password.getText().toString();

        req = new ConnectionRequest(url, false);//l'URL pas encore envoyer au serveur

        req.setUrl(url);
        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();
            String json = new String(req.getResponseData()) + "";

            try {

                if (json.equals("failed")) {
                    Dialog.show("Echec d'authentification", "Veuillez vérifier votre MAIL ou PASSWORD", "OK", null);
                } else {
                    System.out.println("data=>" + json);
                    Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

                    //Session
               //   float id = Float.parseFloat(user.get("idU").toString());

       //   SessionManager.setId((int) id);//Get idUser et enregister dans session 
                    
                    SessionManager.setPassowrd(user.get("password").toString());
                    SessionManager.setUserName(user.get("nom").toString());
                    SessionManager.setUserFname(user.get("prenom").toString());
                    SessionManager.setEmail(user.get("email").toString());
                    SessionManager.setAdresse(user.get("adresse").toString());
                    SessionManager.setBirthday(user.get("datenaissance").toString());
                    SessionManager.setNum(user.get("numtel").toString());

                  

                    System.out.println("User logged =>"+SessionManager.getId() + SessionManager.getEmail() + "," + SessionManager.getUserName() + ","
                            + SessionManager.getUserFname() + "," + SessionManager.getPassowrd());

                    if (user.size() > 0)//User found
                    {
                        new AcceuilForm(rs).show();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        //Aprés l'exécution de l'URL on attend la réponse du serveur 
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    //Forfot Password
    public String getPasswordByEmail(String email, Resources rs) {

        String url ="http://127.0.0.1:8000/user/getPasswordByEmail?email=" + email;

        req = new ConnectionRequest(url, false);//l'URL pas encore envoyer au serveur

        req.setUrl(url);
        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();
            json = new String(req.getResponseData()) + "";

            try {

                System.out.println("data=>" + json);
                Map<String, Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //Aprés l'exécution de l'URL on attend la réponse du serveur 
        NetworkManager.getInstance().addToQueueAndWait(req);
        return json;
    }

//Delete
    public boolean deleteUser(Resources res) {
        String url = "http://127.0.0.1:8000/user/RemoveJSON/" + SessionManager.getId();

        req.setUrl(url);

        req.setUrl(url);
        req.addResponseListener((e) -> {
            JSONParser j = new JSONParser();
            String json = new String(req.getResponseData()) + "";
            try {
                if (json.equals("deleted")) {
                    Dialog.show("Succes de Suppression", "Compte Supprimé avec succés", "OK", null);
                    logout(res);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //Aprés l'exécution de l'URL on attend la réponse du serveur 
        NetworkManager.getInstance().addToQueueAndWait(req);

        return resultOk;
    }

    //Update
    public void update(TextField nom, TextField prenom, TextField email, TextField num, Picker dtp, Resources res) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String bday = format.format(dtp.getDate());
        String url = "http://127.0.0.1:8000/user/UpdateJSON/" + SessionManager.getId() + "?name=" + nom.getText().toString() + "&fname=" + prenom.getText().toString() + "&birthday=" + bday
                 + "&num=" + Integer.parseInt(num.getText()) + "&email=" + email.getText().toString();

        req.setUrl(url);
        req.addResponseListener((e) -> {
            JSONParser j = new JSONParser();
            String json = new String(req.getResponseData()) + "";
            try {
                if (json.equals("updated")) {
                    Dialog.show("Succes de changement", "Profil modifié avec succés", "OK", null);
                    new AcceuilForm(res).show();
                } else {
                    Dialog.show("Echec de Changement", "Vérifier les champs!", "OK", null);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        //Aprés l'exécution de l'URL on attend la réponse du serveur 
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    //Changer PWD

    public void changepwd(TextField email, TextField ancien, TextField mdp, TextField confirm, Resources rs) {
        String url = Static.BASE_URL + "/changepwdJSON/" + SessionManager.getId() + "?email=" + email.getText().toString()
                + "&ancien=" + ancien.getText().toString() + "&mdp=" + mdp.getText().toString() + "&confirm=" + confirm.getText().toString();
      
        req.setUrl(url);
        
        req.addResponseListener((e) -> {
            JSONParser j = new JSONParser();
            String json = new String(req.getResponseData()) + "";
            
            try {
                if (json.equals("pasUser")) {
                    Dialog.show("Echec de Changement", "Utilisateur n'existe pas!", "OK", null);
                } else
                    if (json.equals("fauxemail")) {
                    Dialog.show("Echec de Changement", "Vérifier votre email!", "OK", null);
                } else 
                        if (json.equals("ancienfaux")) {
                    Dialog.show("Echec de Changement", "Vérifier l'ancien Mot de passe!", "OK", null);
                } else 
                            if (json.equals("mmancien")) {
                    Dialog.show("Echec de Changement", "Vous avez tapé un ancien mot de passe", "OK", null);
                } else 
                                if (json.equals("confirmer")) {
                    Dialog.show("Echec de Changement", "Confirmer le nouveau mot de passe", "OK", null);
                } else 
                                    if (json.equals("changed")) {
                    Dialog.show("Succes de Changement", "Mot de passe changé avec sucées", "OK", null);
                    new AcceuilForm(rs).show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    //logout
    public void logout(Resources res) {
        SessionManager.pref.clearAll();
        Storage.getInstance().clearStorage();
        Storage.getInstance().clearCache();
        new SignInForm(res).show();

    }

}
