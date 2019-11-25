package com.frint.frint;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.frint.frint.Service.WebService;

import java.net.URI;

public class BrowseImage extends AppCompatActivity {

    private EditText field_typemsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.browseimage);


    }
    public void onClick_preview(View view){
        Intent intent =new Intent(this,Preview.class);
        startActivity(intent);
    }
    public  void onClick_openGallery(View view)
    {
        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, WebService.RESULT_LOAD_IMAGE);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==WebService.RESULT_LOAD_IMAGE && resultCode==RESULT_OK )
        {
            Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_LONG).show();
            Uri selectedImage=data.getData();
            String[] filePathColumn={MediaStore.Images.Media.DATA};
            Cursor cursor=getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
            String picturePath=cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            int height = bitmap.getHeight(), width = bitmap.getWidth();
            Log.e("abc", height + "  " + width);

            if (height > 1280 && width > 960){

                Toast.makeText(getApplicationContext(),"Image too large",Toast.LENGTH_LONG).show();
            }else {
                ImageView imageView=(ImageView)findViewById(R.id.selectedImage);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));


            }






        }
    }
}
