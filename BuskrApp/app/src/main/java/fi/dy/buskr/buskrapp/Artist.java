package fi.dy.buskr.buskrapp;

import java.io.Serializable;

/**
 * Created by Janne on 7.11.2015.
 */
public class Artist implements Serializable{
    private String name;
    private BankAccountInfo accountInfo;
    private String description;
    private String id;

    public Artist(String name, String description, String id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getId() { return id; }
}
