package com.example.jeetmeena.findbus;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class UdserFrofileActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton imageButton;
   ImageView imageView;
   Button logoutButton;
  TextView userName,userEmail,logOuts;
   static Uri picUri;
    MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udser_frofile);
       imageView =findViewById(R.id.profileImageView);
       logOuts=findViewById(R.id.text6);
       imageButton=findViewById(R.id.asfasfsd);
       imageButton.setOnClickListener(this);
       userEmail=findViewById(R.id.text3);
        Intent intent=getIntent();
        String sd=intent.getStringExtra("userEmail");
        userEmail.setText(sd);
        logOuts.setOnClickListener(this);
        mainActivity=MainActivity.getMainActivity();
      // setPic( mainActivity.getsharedPerfernces("pic_uri","asdf") );


    }

    @Override
    public void onClick(View v) {
        if(R.id.text6==v.getId()){

            mainActivity.logOut();
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish();

           }
           else if(R.id.asfasfsd==v.getId()){

            picImage();


            }

      }


      public void picImage(){

        final CharSequence[] chars={"Choose From Gallery","Take Photo","cancel"};
          final AlertDialog.Builder builder=new AlertDialog.Builder(UdserFrofileActivity.this);
          builder.setTitle("Add ProFile Picture");
          builder.setIcon(R.drawable.ic_image_black_24dp);
          builder.setItems(chars, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
              if(chars[which]=="Choose From Gallery"){
                  Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                  intent.setType("image/*");
                  startActivityForResult(intent,1);
                  Intent chooseIntent=Intent.createChooser(intent,"Select source");

                }
              else if(chars[which]=="Take Photo"){
                  Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  startActivityForResult(takePicture, 2);//zero can be replaced with any action code
              }

              else {
                  dialog.dismiss();
              }

              }

          });

       builder.show();
          Toast.makeText(UdserFrofileActivity.this,"pic Image",Toast.LENGTH_SHORT).show();


      }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Bitmap myBitmap = null;
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                picUri=data.getData();

                    try {
                        myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myBitmap = getResizedBitmap(myBitmap, 500);
                    mainActivity.savesharedPerferns("pic_uri",String.valueOf(picUri));
                    mainActivity.setPicUri(picUri);
                    String path=picUri.getPath();
                    setPic(path);
                    Toast.makeText(UdserFrofileActivity.this,"pic Image"+picUri,Toast.LENGTH_SHORT).show();
              //  imageButton.setImageURI(i);

                        }
                        else {


                        }

                    case 2:
                            if(resultCode==RESULT_OK)
                            {
                                Bundle extras = data.getExtras();
                               myBitmap= (Bitmap) extras.get("data");
                            picUri=data.getData();
                            imageButton.setImageBitmap(myBitmap);
                        }


        }


    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    private Bitmap setPic(String uripath) {
        // Get the dimensions of the View
        int targetW = imageButton.getWidth();
        int targetH =imageButton.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile( uripath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(uripath, bmOptions);
        imageButton.setImageBitmap(bitmap);
        return bitmap;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelable("pic_uri", picUri);

    }

      @Override
      protected void onRestoreInstanceState(Bundle savedInstanceState) {
         super.onRestoreInstanceState(savedInstanceState);
          picUri = savedInstanceState.getParcelable("pic_uri");
       }

}


