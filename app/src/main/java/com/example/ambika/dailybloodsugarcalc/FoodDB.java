package com.example.ambika.dailybloodsugarcalc;

import android.app.Activity;
import android.util.Log;

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

//read google docs FoodDB and create hashtable<string, int> with food names and glycemic index values
public class FoodDB {

    public static HashMap<String, Integer> foodEntries = new HashMap<String, Integer>();
    public static ArrayList<String> listoffoods = new ArrayList<String>();

//------------------------ METHOD TO ACCESS GOOGLE DOC DIRECTLY ---------------------------------
    public static void main(String[] args) {

        String applicationName = "com.example.ambika.dailybloodsugarcalc";
        String key = args[0];
        SpreadsheetService service = new SpreadsheetService(applicationName);

        try {
            URL url = FeedURLFactory.getDefault().getWorksheetFeedUrl(key, "public", "basic");

            WorksheetFeed feed = service.getFeed(url, WorksheetFeed.class);
            List<WorksheetEntry> worksheetList = feed.getEntries();
            WorksheetEntry worksheetEntry = worksheetList.get(0);

            ListQuery listQuery = new ListQuery(worksheetEntry.getListFeedUrl());
            ListFeed listFeed = service.query(listQuery, ListFeed.class);
            List<ListEntry> list = listFeed.getEntries();
            int numEntries = 0;

            for (ListEntry row : list) {
                    String foodInput = row.getPlainTextContent();
                    FoodDB.listoffoods.add(numEntries, foodInput.substring("name: ".length(), foodInput.indexOf(", " +
                            "glycemicindex: ")));
                    FoodDB.foodEntries.put(foodInput.substring("name: ".length(), foodInput.indexOf(", glycemicindex: ")),
                            Integer.parseInt(foodInput.substring(foodInput.indexOf(", glycemicindex: ") + (", glycemicindex: " +
                                    "").length())));
                    numEntries++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


