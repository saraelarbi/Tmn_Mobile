package com.firstproject.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.firstproject.entities.Blog;
import com.firstproject.utils.Statics;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlogService {

    public static BlogService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Blog> listBlogs;


    private BlogService() {
        cr = new ConnectionRequest();
    }

    public static BlogService getInstance() {
        if (instance == null) {
            instance = new BlogService();
        }
        return instance;
    }

    public ArrayList<Blog> getAll() {
        listBlogs = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/blog");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listBlogs = getList();
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

        return listBlogs;
    }

    private ArrayList<Blog> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Blog blog = new Blog(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        (String) obj.get("description"),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("dateDePublication")),
                        (String) obj.get("image"),
                        (String) obj.get("categorie")

                );

                listBlogs.add(blog);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listBlogs;
    }

    public int add(Blog blog) {
        return manage(blog, false, true);
    }

    public int edit(Blog blog, boolean imageEdited) {
        return manage(blog, true, imageEdited);
    }

    public int manage(Blog blog, boolean isEdit, boolean imageEdited) {

        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Blog.jpg");


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/blog/edit");
            cr.addArgumentNoEncoding("id", String.valueOf(blog.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/blog/add");
        }

        if (imageEdited) {
            try {
                cr.addData("file", blog.getImage(), "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cr.addArgumentNoEncoding("image", blog.getImage());
        }

        cr.addArgumentNoEncoding("description", blog.getDescription());
        cr.addArgumentNoEncoding("dateDePublication", new SimpleDateFormat("dd-MM-yyyy").format(blog.getDateDePublication()));
        cr.addArgumentNoEncoding("image", blog.getImage());
        cr.addArgumentNoEncoding("categorie", blog.getCategorie());


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

    public int delete(int blogId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/blog/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(blogId));

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
