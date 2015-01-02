package com.example.ambika.dailybloodsugarcalc;

import android.app.Activity;

import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;

import java.net.URL;
import java.util.*;
import java.util.List;

//read google docs DB and create hashtable<string, int> with food names and glycemic index values
public class FoodDB {

    public static HashMap<String, Integer> foodEntries = new HashMap<String, Integer>();
    public static ArrayList<String> listoffoods = new ArrayList<String>();

//-----------METHOD TO ACCESS CSV FILE ------------------------------------------------------------
//    public static void buildDB(List foodData) {
//        for (int i = 0; i < foodData.size(); i++) {
//            //FoodDB.foodEntries.put(foodData.get(i)[1], Integer.parseInt(foodData.get(i)[2]));
//            //FoodDB.listoffoods.add(foodData.get(i)[1]);
//            //FoodDB.listoffoods.add(foodData.get(i));
//
//        }
//    }


//------------------------ METHOD TO ACCESS GOOGLE DOC DIRECTLY ---------------------------------
    public static void main(String[] args) {

        String applicationName = "com.example.ambika.dailybloodsugarcalc";
        String key = args[0];
        //String query = args[1];
        SpreadsheetService service = new SpreadsheetService(applicationName);
        // service.setProtocolVersion(SpreadsheetService.Versions.V3);

        try {
            URL url = FeedURLFactory.getDefault().getWorksheetFeedUrl(key, "public", "basic");

//            ListFeed listFeed = service.getFeed(url, ListFeed.class);
//            int numEntries = 0;
//            for (ListEntry entry : listFeed.getEntries()) {
//                CustomElementCollection elements = entry.getCustomElements();
//                FoodDB.listoffoods[numEntries] = elements.getValue("Name");
//                FoodDB.foodEntries.put(elements.getValue("Name"), Integer.parseInt(elements.getValue("Glycemic Index")));
//                numEntries++;
//            }

            WorksheetFeed feed = service.getFeed(url, WorksheetFeed.class);
            List<WorksheetEntry> worksheetList = feed.getEntries();
            WorksheetEntry worksheetEntry = worksheetList.get(0);

            ListQuery listQuery = new ListQuery(worksheetEntry.getListFeedUrl());
            //listQuery.setSpreadsheetQuery(query);
            ListFeed listFeed = service.query(listQuery, ListFeed.class);
            List<ListEntry> list = listFeed.getEntries();
            int numEntries = 0;
            for (ListEntry listEntry : list) {
                CustomElementCollection elements = listEntry.getCustomElements();
                FoodDB.listoffoods.add(numEntries, elements.getValue("Name"));
                FoodDB.foodEntries.put(elements.getValue("Name"), Integer.parseInt(elements.getValue("Glycemic Index")));
                numEntries++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
