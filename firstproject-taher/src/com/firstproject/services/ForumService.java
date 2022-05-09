package com.firstproject.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.firstproject.entities.Forum;
import com.firstproject.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForumService {

    public static ForumService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Forum> listForums;


    private ForumService() {
        cr = new ConnectionRequest();
    }

    public static ForumService getInstance() {
        if (instance == null) {
            instance = new ForumService();
        }
        return instance;
    }

    public ArrayList<Forum> getAll() {
        listForums = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/forum");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listForums = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listForums;
    }

    private ArrayList<Forum> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Forum forum = new Forum(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        (String) obj.get("questions"),
                        (int) Float.parseFloat(obj.get("etat").toString())

                );

                listForums.add(forum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listForums;
    }

    public int add(Forum forum) {
        return manage(forum, false);
    }

    public int edit(Forum forum) {
        return manage(forum, true);
    }

    public int manage(Forum forum, boolean isEdit) {

        cr = new ConnectionRequest();


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/forum/edit");
            cr.addArgument("id", String.valueOf(forum.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/forum/add");
        }

        cr.addArgument("questions", forum.getQuestions());
        cr.addArgument("etat", String.valueOf(forum.getEtat()));


        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int forumId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/forum/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(forumId));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }
}
