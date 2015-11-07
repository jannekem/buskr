package fi.dy.buskr.buskrapp;


import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Janne on 7.11.2015.
 */
public class ArtistResolver {

    Map<String, Artist> artistMap;

    public ArtistResolver(){
        Artist artist = new Artist("James Elliot", new BankAccountInfo(), "I'm the King of Brick Lane");
        artistMap = new HashMap<String, Artist>();
        artistMap.put("1296",artist);
    }

    Artist getArtist(int artistId){
        String artistIdStr = String.valueOf(artistId);
        if(artistMap.containsKey(artistIdStr)) {
            return artistMap.get(String.valueOf(artistIdStr));
        }
        else return null;
    }
}
