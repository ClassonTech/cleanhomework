package net.classon.www.cleanhomework;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    private GridView gridview;
    private Button newBtn;
    Context context;
    ArrayList itemList;
    public static String [] prgmNameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        settings.getStringSet("folderNames",null).toArray(prgmNameList);

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new CustomAdapter(this,prgmNameList));

        newBtn = (Button) findViewById(R.id.btnNewButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet("folderNames", new HashSet<>(Arrays.asList(prgmNameList)));
        //editor.putInt("numClicks", 0); refresh settings lol
        editor.commit();
    }

    public void createButton(View view){
        CreateButtonDialog dialog = new CreateButtonDialog();
        dialog.showDialog(this,"");
    }
}
