package ngo.donate.project.app.donatengo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class AccountSettings extends DialogActivity implements View.OnTouchListener, View.OnClickListener {
    DatabaseReference mref;

    FirebaseAuth mAuth;
    TextView name, email, address, phone;
    FloatingActionButton editDP;
    CircleImageView profilePicture;
    private static final int PERMISSION_REQUEST_CODE = 98;

    private static int CAMERA_REQUEST = 1009, GALLERY_PICTURE = 1008;

    File f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);
        init();
        setDetails();

        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        name.setOnTouchListener(this);
        email.setOnTouchListener(this);
        address.setOnTouchListener(this);
        phone.setOnTouchListener(this);

        editDP.setOnClickListener(this);

    }

    private void init() {
        name = (TextView) findViewById(R.id.display_name);
        email = (TextView) findViewById(R.id.display_email);
        address = (TextView) findViewById(R.id.display_address);
        phone = (TextView) findViewById(R.id.display_phone);
        profilePicture = (CircleImageView) findViewById(R.id.display_profile_picture);
        editDP = (FloatingActionButton) findViewById(R.id.edit_profile_picture);
        mAuth = FirebaseAuth.getInstance();
    }

    private void setDetails() {

        final FirebaseUser user = mAuth.getCurrentUser();
        mref = FirebaseDatabase.getInstance().getReference();
        if (user != null) {

            ((TextView) findViewById(R.id.email_verified)).setText("eMailVerified=" + user.isEmailVerified());
            ((TextView) findViewById(R.id.display_name)).setText(user.getDisplayName());
            ((TextView) findViewById(R.id.display_email)).setText(user.getEmail());
            mref.child("Ngos").child("NGO1").child("ngoUsers").child(user.getUid()).child("ngoUser_details").child("email").setValue(user.getEmail());
            mref.child("Ngos").child("NGO1").child("ngoUsers").child(user.getUid()).child("ngoUser_details").child("name").setValue(user.getDisplayName());
            if (user.getPhotoUrl() != null) {
                Glide.with(this).load(user.getPhotoUrl()).into(profilePicture);
            }
            //((EditText) findViewById(R.id.display_providerId)).setText(user.getProviderId());
            //((EditText) findViewById(R.id.display_gender)).setText(mFirebaseAnalytics.);

            if (isNetworkAvailable()) {

                showProgressDialog();
                mref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        address.setText((String) dataSnapshot.child("Ngos").child("NGO1").child("ngoUsers").child(user.getUid()).child("ngoUser_details").child("address").getValue());
                        phone.setText((String) dataSnapshot.child("Ngos").child("NGO1").child("ngoUsers").child(user.getUid()).child("ngoUser_details").child("phone").getValue());
                        hideProgressDialog();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            } else {
                Snackbar.make(getWindow().getDecorView().getRootView(),
                        getString(R.string.no_internet) + " some information may not be available", Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.retry), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setDetails();
                            }
                        }).show();
            }

        }
    }


    @Override
    protected void onStart() {

        setDetails();
        super.onStart();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        switch (v.getId()) {

            //For email Button
            case R.id.display_email:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (email.getRight() - email.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Log.e("Clicked", "Pencil");
                        //mFirebaseAnalytics.setUserProperty("Gender", "Male");
                        Intent i = new Intent(getApplicationContext(), EditDetail.class);
                        i.putExtra("title", getString(R.string.edit_your_email));
                        i.putExtra("type", "Email");
                        i.putExtra("currentValue", email.getText().toString());
                        i.putExtra("singleLine", true);
                        startActivity(i);
                        return true;
                    }
                }
                return false;

            //for name pencil Click
            case R.id.display_name:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (name.getRight() - name.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Log.e("Clicked", "Pencil");
                        //mFirebaseAnalytics.setUserProperty("Gender", "Male");
                        Intent i = new Intent(getApplicationContext(), EditDetail.class);
                        i.putExtra("title", getString(R.string.edit_your_name));
                        i.putExtra("type", "Name");
                        i.putExtra("currentValue", name.getText().toString());
                        i.putExtra("singleLine", true);
                        startActivity(i);
                        return true;
                    }
                }
                return false;

            case R.id.display_address:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (address.getRight() - address.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Log.e("Clicked", "Pencil");
                        //mFirebaseAnalytics.setUserProperty("Gender", "Male");
                        Intent i = new Intent(getApplicationContext(), EditDetail.class);
                        i.putExtra("title", getString(R.string.edit_your_address));
                        i.putExtra("type", "Address");
                        i.putExtra("currentValue", address.getText().toString());
                        i.putExtra("singleLine", false);
                        startActivity(i);
                        return true;
                    }
                }
                return false;

            case R.id.display_phone:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (phone.getRight() - phone.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Log.e("Clicked", "Pencil");
                        //mFirebaseAnalytics.setUserProperty("Gender", "Male");
                        Intent i = new Intent(getApplicationContext(), EditDetail.class);
                        i.putExtra("title", getString(R.string.edit_your_phone));
                        i.putExtra("type", "Phone");
                        i.putExtra("currentValue", phone.getText().toString());
                        i.putExtra("singleLine", true);
                        i.putExtra("inputType", InputType.TYPE_CLASS_PHONE);
                        startActivity(i);
                        return true;
                    }
                }
                return false;
        }

        return false;

    }

    public void lang(View view) {
        final ArrayAdapter<String> languages = new ArrayAdapter<String>(view.getContext(), android.R.layout.select_dialog_item, getResources().getStringArray(R.array.languages));

        AlertDialog.Builder ad = new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.select_language))
                .setNegativeButton(getResources().getString(R.string.cancel), null);
        ad.setAdapter(languages, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedLang = languages.getItem(which);
                changeLanguage(selectedLang);
            }
        }).show();

    }

    private void changeLanguage(String lang) {
        Log.i("Lang", lang);
        if (lang.equals("Hindi")) {
            LocaleHelper.setLocale(this, "hi");
        } else {
            Context context = LocaleHelper.setLocale(this, "en");
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.edit_profile_picture) {

            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermission()) {
                    // Code for above or equal 23 API Oriented Device
                    // Your Permission granted already .Do next code
                    startDialog();
                } else {
                    requestPermission(); // Code for permission
                }
            } else {
                startDialog();
            }


        }
    }

    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Picture");
        myAlertDialog.setMessage(R.string.select_image_from);

        myAlertDialog.setPositiveButton(R.string.gallery,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent pictureActionIntent = null;

                        pictureActionIntent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pictureActionIntent, GALLERY_PICTURE);

                    }
                });

        myAlertDialog.setNegativeButton(getString(R.string.camera),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);

                        f = new File(Environment
                                .getExternalStorageDirectory(), "temp.jpg");

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

                        startActivityForResult(intent, CAMERA_REQUEST);

                    }
                });
        myAlertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FirebaseUser user = mAuth.getCurrentUser();
        if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE && data != null && data.getData() != null) {
            Log.e("Called", "" + CAMERA_REQUEST);
            try {
                InputStream is = getContentResolver().openInputStream(data.getData());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap preview_bitmap = BitmapFactory.decodeStream(is, null, options);
                //profilePicture.setImageBitmap(preview_bitmap);

                final Uri imageUri = getImageUri(this, preview_bitmap);

                UserProfileChangeRequest up = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(imageUri).build();
                showProgressDialog();
                user.updateProfile(up).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.w("App", "UpdateProfile", task.getException());
                        }

                        hideProgressDialog();
                        setDetails();
                        File fDelete = new File(imageUri.getPath());
                        Log.w("Image Path", Environment.getExternalStorageDirectory() + "  " + fDelete.getName());
                        boolean deleted = fDelete.delete();
                    }
                });
                //getContentResolver().delete(imageUri, null, null);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {

                Uri temp = Uri.fromFile(f);
                InputStream is = getContentResolver().openInputStream(temp);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap preview_bitmap = BitmapFactory.decodeStream(is, null, options);
                final Uri imageUri = getImageUri(this, preview_bitmap);

                UserProfileChangeRequest up = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(imageUri).build();
                showProgressDialog();
                user.updateProfile(up).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.w("App", "UpdateProfile", task.getException());
                        }

                        hideProgressDialog();
                        setDetails();
                        Log.w("Image Path", Environment.getExternalStorageDirectory() + "  " + f.getName());
                        boolean deleted = f.delete();
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 0, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else if (result == PackageManager.PERMISSION_DENIED) {
            requestPermission();
            return false;
        }
        return false;
    }


    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {

                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    boolean showRationale = shouldShowRequestPermissionRationale(permissions[i]);
                    if (!showRationale) {
                        Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, PERMISSION_REQUEST_CODE);
                    }
                }
            }
        }
    }


}
