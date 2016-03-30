package net.classon.www.cleanhomework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by classon6513 on 3/30/2016.
 */
public class MyFragment extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    public static final MyFragment newInstance(File message)
    {
        MyFragment f = new MyFragment();
        Bundle bdl = new Bundle(1);
        String str = message.toString();
        bdl.putString(EXTRA_MESSAGE, str);
        f.setArguments(bdl);
        return f;
    }

    public static final MyFragment newInstance() //overloaded method for placeholder, no file
    {
        MyFragment f = new MyFragment();
        Bundle bdl = new Bundle(1);
        String str = "";
        bdl.putString(EXTRA_MESSAGE, str);
        f.setArguments(bdl);
        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String message = getArguments().getString(EXTRA_MESSAGE);
        View v = inflater.inflate(R.layout.myfragment_layout, container, false);
        ImageView iv = (ImageView)v.findViewById(R.id.imageView);
        if(message == null){
            Bitmap placeholder = BitmapFactory.decodeResource(getResources(), R.drawable.folderbg);
            iv.setImageBitmap(placeholder);
        }

        Bitmap bm = BitmapFactory.decodeFile(message);
        iv.setImageBitmap(bm);

        return v;

    }

}

