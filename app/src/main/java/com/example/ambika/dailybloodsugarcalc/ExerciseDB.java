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

//read google docs ExerciseDB and create hashtable<string, int> with exercise names and exercise index values
public class ExerciseDB {

    public static HashMap<String, Integer> exerciseEntries = new HashMap<String, Integer>();
    public static ArrayList<String> listofexercises = new ArrayList<String>();

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
                String exerciseInput = row.getPlainTextContent();
                ExerciseDB.listofexercises.add(numEntries, exerciseInput.substring("exercise: ".length(), exerciseInput.indexOf(", " +
                        "exerciseindex: ")));
                ExerciseDB.exerciseEntries.put(exerciseInput.substring("exercise: ".length(), exerciseInput.indexOf(", exerciseindex: ")),
                        Integer.parseInt(exerciseInput.substring(exerciseInput.indexOf(", exerciseindex: ") + (", exerciseindex: " +
                                "").length())));
                numEntries++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


