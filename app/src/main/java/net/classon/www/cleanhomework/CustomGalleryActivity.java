package net.classon.www.cleanhomework;

/**
 * Created by classon6513 on 3/14/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CustomGalleryActivity extends Activity {

    private static final String ROOT = "cleanhomework";

    private int currentPic = 0;
    private Gallery picGallery;
    private ImageView picView;
    private ImageAdapter imgAdapt;
    private ArrayList<File> images;
    private String currentFolder;
    File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + ROOT);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_gallery);

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

        picView = (ImageView) findViewById(R.id.picture);
        picGallery = (Gallery) findViewById(R.id.gallery);
        imgAdapt = new ImageAdapter(this, images);
        picGallery.setAdapter(imgAdapt);
        picView.setImageBitmap(imgAdapt.getBitmap(0));
        picView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        //set long click listener for each gallery thumbnail item
        picGallery.setOnItemLongClickListener(new OnItemLongClickListener() {
            //handle long clicks
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                //delete pic
                currentPic = position;
                File nomore = images.get(position);
                if(nomore.delete()){
                    images.remove(position);
                    imgAdapt.notifyDataSetChanged();
                }
                return true;
            }
        });

        picGallery.setOnItemClickListener(new OnItemClickListener() {
            //handle long clicks
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //take a new pic
                if (position != 0) {
                    picView.setImageBitmap(imgAdapt.getBitmap(position));
                } else {
                    Intent intent = new Intent(CustomGalleryActivity.this.getApplicationContext(), CustomCameraActivity.class);

                    //String message = editText.getText().toString();
                    intent.putExtra("EXTRA_NAME", currentFolder);
                    intent.putExtra("EXTRA_FILES", images); //prob inefficient
                    startActivity(intent);
                }

            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        //use the default gallery background image
        int defaultItemBackground;

        //gallery context
        private Context galleryContext;

        //array to store bitmaps to display
        private ArrayList<Bitmap> imageBitmaps;

        //placeholder bitmap for empty spaces in gallery
        Bitmap placeholder;
        private ArrayList<File> mList;
        public ImageAdapter(Context c, ArrayList<File> list) {

            galleryContext = c;
            imageBitmaps  = new ArrayList<Bitmap>();
            placeholder = BitmapFactory.decodeResource(getResources(), R.drawable.folderbg);

            mList = list; // instantiate http://code.tutsplus.com/tutorials/android-sdk-displaying-images-with-an-enhanced-gallery--mobile-11130

            for(int i = 0; i < mList.size(); i++) {
                ArrayList<Bitmap> temp = new ArrayList<Bitmap>();
                temp.set(i, null);

                Bitmap bm = BitmapFactory.decodeFile(mList.get(i).toString()); //stringy
                temp.set(i, bm);

                imageBitmaps = temp;
            }
            imageBitmaps.add(0, placeholder);

            TypedArray styleAttrs = galleryContext.obtainStyledAttributes(R.styleable.PicGallery);
            defaultItemBackground = styleAttrs.getResourceId(R.styleable.PicGallery_android_galleryItemBackground, 0);
            styleAttrs.recycle();
        }
        // returns the number of images
        public int getCount() {
            return imageBitmaps.size();
        }
        // returns the ID of an item
        public Object getItem(int position) {
            return position;
        }
        // returns the ID of an item
        public long getItemId(int position) {
            return position;
        }
        // returns an ImageView view
        public View getView(int position, View convertView, ViewGroup parent) {

            //create the view
            ImageView imageView = new ImageView(galleryContext);
            //specify the bitmap at this position in the array
            imageView.setImageBitmap(imageBitmaps.get(position));
            //set layout options
            imageView.setLayoutParams(new Gallery.LayoutParams(300, 200));
            //scale type within view area
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //set default gallery item background
            imageView.setBackgroundResource(defaultItemBackground);
            //return the view
            return imageView;
        }

        public Bitmap getBitmap(int position){
            return imageBitmaps.get(position);
        }
    }
}
