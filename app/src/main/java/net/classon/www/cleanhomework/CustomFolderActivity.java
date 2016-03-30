package net.classon.www.cleanhomework;

import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by classon6513 on 3/29/2016.
 */
public class CustomFolderActivity extends FragmentActivity {

    private static final String ROOT = "cleanhomework";

    MyPageAdapter pageAdapter;
    File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + ROOT);
    private ArrayList<File> images;
    private String currentFolder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                images= null;
                currentFolder = null;
            } else {
                images = (ArrayList<File>)extras.get("EXTRA_FILES");
                currentFolder = extras.getString("EXTRA_NAME");
            }
        } else {
            images= (ArrayList<File>) savedInstanceState.getSerializable("EXTRA_FILES");
            currentFolder = savedInstanceState.getSerializable("EXTRA_NAME").toString();
        }

        List<Fragment> fragments = getFragments(images);
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);
    }

    private List<Fragment> getFragments(ArrayList<File> list){
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(MyFragment.newInstance());
        for(File f: list) {
            fList.add(MyFragment.newInstance(f));
        }

        return fList;
    }

}

class MyPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }


    @Override
    public int getCount() {
        return this.fragments.size();
    }

}




