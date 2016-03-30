package net.classon.www.cleanhomework;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MainActivity extends Activity implements CreateDialogFragment.DialogListener, EditDialogFragment.DialogListener {

    public static final String PREFS_NAME = "MyPrefsFile";

    private GridView gridview;
    private Button newBtn;
    private ArrayList itemList;
    private CustomAdapter gridAdapter;
    private String temp;
    private static final String ROOT = "cleanhomework";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        //settings.getStringSet("folderNames",null).toArray(prgmNameList);

        itemList = new ArrayList<String>();

        refreshList();

        if(itemList.size()==0){itemList.add("New Folder");} //On first install, before first picture, always created

        gridview = (GridView) findViewById(R.id.gridview);
        newBtn = (Button) findViewById(R.id.btnNewButton);
        newBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createButton(v);
            }
        });
        gridAdapter = new CustomAdapter(this, itemList);
        gridview.setLongClickable(true);
        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                temp = ((TextView)arg1.findViewById(R.id.label)).getText().toString();
                createButton(arg1);
                return true;
            }
        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int pos, long id) {
                //Intent intent = new Intent(MainActivity.this.getApplicationContext(), CustomCameraActivity.class);
                String name = ((TextView) arg1.findViewById(R.id.label)).getText().toString();
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), CustomGalleryActivity.class);
                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + ROOT + File.separator + name);
                ArrayList<File> images = new ArrayList<File>();
                for(File i : f.listFiles()){
                    images.add(i);
                }
                //intent.putExtra("EXTRA_NAME", name);
                //intent.putExtra("EXTRA_FILES", images); //prob inefficient
                startActivity(intent);
            }
        });
        gridview.setAdapter(gridAdapter);

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

        //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        //SharedPreferences.Editor editor = settings.edit();
        //editor.putStringSet("folderNames", new HashSet<>(Arrays.asList(prgmNameList)));
        //editor.putInt("numClicks", 0); refresh settings lol
        //editor.commit();
    }

    public void createButton(View view) {
        FragmentManager manager = getFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_edit_name");
        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }
        if(view.getId()==R.id.btnNewButton){
            CreateDialogFragment editNameDialog = new CreateDialogFragment();
            editNameDialog.show(manager, "fragment_edit_name");
        } else {
            EditDialogFragment editNameDialog = new EditDialogFragment();
            editNameDialog.show(manager, "fragment_edit_name");
        }
    }

    public void handleDialog(String str) {
        String obj = str;
        itemList.add(obj);
        gridAdapter.notifyDataSetChanged();
    }

    public void refreshList() {
        itemList.clear();
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + ROOT);
        if (!f.exists()) {
            if (!f.mkdirs()) {
                //Couldn't make ROOT directory for app, on first install
            }
        }

        if(f.list() != null ) {
            String[] arr = f.list();
            for (String str : arr) {
                itemList.add(str); //Creates a GridView based on albums in the pictures folder... possibly problematic
            }
        }
    }

    @Override
    public void onFinishUserDialog(String str, int i) { //i = 0, create new folder "str". i = 1, edit folder name "temp" to "str"
        if(i==0){
            itemList.add(str);
            gridAdapter.notifyDataSetChanged();
        } else if(i==1){
            File from = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + ROOT + File.separator + temp);
            File to = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + ROOT + File.separator + str);
            from.renameTo(to);
            refreshList();
            gridAdapter.notifyDataSetChanged();
        }
    }
}
