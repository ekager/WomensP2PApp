package com.ghc2015.womensp2p;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContentCreationFragment extends Fragment implements View.OnClickListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap userBitmap;
    private EditText textContent;
    private Button attachMediaButton;
    private Button cancelButton;
    private Button createButton;
    private Button newPhotoButton;
    private ImageView displayImage;
    private CheckBox reportCheckBox;
    private EditText locationEditText;

    public ContentCreationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_content_creation, container, false);

        textContent = (EditText) v.findViewById(R.id.content_text);
        attachMediaButton = (Button) v.findViewById(R.id.attach_media);
        attachMediaButton.setOnClickListener(this);
        cancelButton = (Button) v.findViewById(R.id.cancel_create_content);
        cancelButton.setOnClickListener(this);
        createButton = (Button) v.findViewById(R.id.create_content_button);
        createButton.setOnClickListener(this);
        newPhotoButton = (Button) v.findViewById(R.id.take_new_photo);
        newPhotoButton.setOnClickListener(this);
        displayImage = (ImageView) v.findViewById(R.id.display_image);
        reportCheckBox = (CheckBox) v.findViewById(R.id.report_checkbox);
        locationEditText = (EditText) v.findViewById(R.id.post_location);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_create_content:
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                break;
            case R.id.create_content_button:
                // TODO post all of these to the internal database if NOT connected to wifi
                String userText = textContent.getText().toString();

                // TODO get time
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                final String currentDateandTime = sdf.format(new Date());

                // TODO get location
                final String userLocation = locationEditText.getText().toString();

                // TODO get boolean if report
                final boolean isReport = reportCheckBox.isChecked();

                ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (mWifi.isConnected()) {
                    // TODO Upload immediately to MONGO DB
                    if (userBitmap != null) {
                        // Post with photo method
                    }
                } else {
                    // TODO save in our database for now

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    userBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] bArray = bos.toByteArray();


                    SQLiteDatabase database = new DatabaseHelper(getActivity()).getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.PostEntry.COLUMN_USER, "Test User");
                    values.put(DatabaseContract.PostEntry.COLUMN_TIME, currentDateandTime);
                    values.put(DatabaseContract.PostEntry.COLUMN_IMAGE, bArray);
                    values.put(DatabaseContract.PostEntry.COLUMN_LOCATION, userLocation);
                    values.put(DatabaseContract.PostEntry.COLUMN_POST_TEXT, userText);
                    values.put(DatabaseContract.PostEntry.COLUMN_REPORT, isReport);

                    long newRowId;
                    newRowId = database.insert(
                            DatabaseContract.PostEntry.TABLE_NAME,
                            DatabaseContract.PostEntry.COLUMN_NULLABLE,
                            values
                    );

                    database.close();
                }
                break;
            case R.id.attach_media:
                Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);
                //comma-separated MIME types
                mediaChooser.setType("video/*, image/*");
                startActivityForResult(mediaChooser, 0);
                break;
            case R.id.take_new_photo:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            if (!TextUtils.isEmpty(targetUri.toString())) {
                String type = getActivity().getContentResolver().getType(targetUri);
                if (!TextUtils.isEmpty(type)) {
                    if (type.contains("image") | type.contains("video")) {
                        try {
                            userBitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
                            displayImage.setImageBitmap(userBitmap);
                            displayImage.setVisibility(View.VISIBLE);
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        // TODO deal with orientation changes and destroy
        super.onDestroy();
    }
}
