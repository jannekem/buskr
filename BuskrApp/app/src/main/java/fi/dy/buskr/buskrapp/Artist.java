package fi.dy.buskr.buskrapp;

import java.io.Serializable;

/**
 * Created by Janne on 7.11.2015.
 */
public class Artist implements Serializable{
    private String name;
    private BankAccountInfo accountInfo;
    private String description;

    public Artist(String name, BankAccountInfo info, String description) {
        this.name = name;
        this.accountInfo = info;
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public BankAccountInfo getAccountInfo(){
        return accountInfo;
    }
}
