package com.example.ambika.dailybloodsugarcalc;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.io.InputStream;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    //String[] language ={"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP"};
    ArrayAdapter<String> exerciseAdapter;
    ArrayAdapter<String> foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//----------------METHOD TO ACCESS FROM DOWNLOADED CSV FILE ---------------------
//        InputStream inputStream = getResources().openRawResource(R.raw.fooddb);
//        ReadCSVFile csvFile = new ReadCSVFile(inputStream);
//        List foodData = csvFile.read();
//        FoodDB.buildDB(foodData);
//-------------------------------------------------------------------------------


        //-------METHOD TO ACCESS STRAIGHT FROM GOOGLE DOCS ---------------
        String args[] = {"1XBl8AvLRoycm034Rh-lMoe_pGHnY14DCtZToBnz4v-w"};
        FoodDB.main(args);
        //-----------------------------------------------------------------

        setViews();

        AutoCompleteTextView autoTV1= (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        autoTV1.setThreshold(1);
        autoTV1.setAdapter(exerciseAdapter);

        AutoCompleteTextView autoTV2= (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
        autoTV2.setThreshold(1);
        autoTV2.setAdapter(foodAdapter);

    }

    public void setViews(){
        exerciseAdapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, FoodDB.listoffoods);
        foodAdapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, FoodDB.listoffoods);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
