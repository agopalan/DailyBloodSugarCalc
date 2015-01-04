package com.example.ambika.dailybloodsugarcalc;

import android.util.Log;

import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuildDB {

    public static HashMap<String, Integer> exerciseEntries = new HashMap<String, Integer>();
    public static ArrayList<String> listofexercises = new ArrayList<String>();
    public static HashMap<String, Integer> foodEntries = new HashMap<String, Integer>();
    public static ArrayList<String> listoffoods = new ArrayList<String>();

    //------------------------ METHOD TO ACCESS GOOGLE DOC DIRECTLY ---------------------------------
    public static void main(String keys[]) {

        //iterate through all databases
        for (int i = 0; i < keys.length; i++) {

            String applicationName = "com.example.ambika.dailybloodsugarcalc";
            SpreadsheetService service = new SpreadsheetService(applicationName);

            try {
                //access public google spreadsheet databases
                URL url = FeedURLFactory.getDefault().getWorksheetFeedUrl(keys[i], "public", "basic");

                //get all sheets in spreadsheet file
                WorksheetFeed feed = service.getFeed(url, WorksheetFeed.class);
                List<WorksheetEntry> worksheetList = feed.getEntries();
                WorksheetEntry worksheetEntry = worksheetList.get(0);

                //get all entries for first and only worksheet
                ListQuery listQuery = new ListQuery(worksheetEntry.getListFeedUrl());
                ListFeed listFeed = service.query(listQuery, ListFeed.class);
                List<ListEntry> list = listFeed.getEntries();
                int numEntries = 0;

                //iterate through all row entries of worksheet
                for (ListEntry row : list) {
                    //food database
                    if(i == 0) {
                        String foodInput = row.getPlainTextContent();
                        //get list of names of foods
                        BuildDB.listoffoods.add(numEntries, foodInput.substring("name: ".length(), foodInput.indexOf(", " +
                                "glycemicindex: ")));
                        //get list of names of foods and list of glycemic indices
                        BuildDB.foodEntries.put(foodInput.substring("name: ".length(), foodInput.indexOf(", glycemicindex: ")),
                                Integer.parseInt(foodInput.substring(foodInput.indexOf(", glycemicindex: ") + (", glycemicindex: " +
                                        "").length())));
                    }
                    //exercise database
                    else if(i == 1){
                        String exerciseInput = row.getPlainTextContent();
                        //get list of names of exercises
                        BuildDB.listofexercises.add(numEntries, exerciseInput.substring("exercise: ".length(), exerciseInput.indexOf(", " +
                                "exerciseindex: ")));
                        //get list of exercises and list of exercise indices
                        BuildDB.exerciseEntries.put(exerciseInput.substring("exercise: ".length(), exerciseInput.indexOf(", exerciseindex: ")),
                                Integer.parseInt(exerciseInput.substring(exerciseInput.indexOf(", exerciseindex: ") + (", exerciseindex: " +
                                        "").length())));

                    }
                    numEntries++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
