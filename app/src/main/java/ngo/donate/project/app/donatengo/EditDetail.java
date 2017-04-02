package ngo.donate.project.app.donatengo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditDetail extends DialogActivity implements View.OnClickListener {

    DatabaseReference mref;
    FirebaseAuth firebaseAuth;

    EditText data;
    TextInputLayout layout;
    String title, type, currentValue;
    Button save,cancel;
    UserProfileChangeRequest profileUpdates;
    private static final String TAG = "EditDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_detail);
        Bundle b = getIntent().getExtras();

        firebaseAuth= FirebaseAuth.getInstance();
        mref= FirebaseDatabase.getInstance().getReference();

        if (b != null) {
            title = b.getString("title");
            type = b.getString("type");
            currentValue = b.getString("currentValue");
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);


        data = (EditText) findViewById(R.id.edited_data);
        layout = (TextInputLayout) findViewById(R.id.edited_data_layout);
        save = (Button) findViewById(R.id.save_change);
        cancel = (Button) findViewById(R.id.cancel_change);

        data.setSingleLine(b.getBoolean("singleLine"));
        data.setText(currentValue);
        layout.setHint(type);
        data.selectAll();
        data.requestFocus();



        if(type.equals("Phone")){
            Log.w("aaaaa","Phone Input type");
            data.setInputType(b.getInt("inputType"));
        }

        save.setOnClickListener(this);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        switch (type) {
            case "Name":
                Log.d(TAG, "Name changed Button");
                String val = data.getText().toString();
                if (val.isEmpty() || val.contentEquals(currentValue)) return;

                profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(val)
                        .build();
                showProgressDialog();
                mref.child("Ngos").child("NGO1").child("ngoUsers").child(user.getUid()).child("ngoUser_details").child("name").setValue(val);
                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                            Toast.makeText(getApplication(), "Update Success", Toast.LENGTH_SHORT);
                            finish();
                        }
                        hideProgressDialog();
                    }
                });
                break;
            case "Email":
                val = data.getText().toString();
                if (val.isEmpty()) return;

                showProgressDialog();
                mref.child("Ngos").child("NGO1").child("ngoUsers").child(user.getUid()).child("ngoUser_details").child("email").setValue(val);
                user.updateEmail(data.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User email address updated.");
                                    user.sendEmailVerification();
                                    hideProgressDialog();
                                    finish();
                                }
                                hideProgressDialog();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof FirebaseException) {
                                    String error = ((FirebaseException) e).getLocalizedMessage();

                                    TextView tv = (TextView) findViewById(R.id.edit_detail_error);
                                    tv.setText(error);
                                    tv.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                break;

            case "Address":
                //TODO
                // harsh to update address here
                val = data.getText().toString();
                if (val.isEmpty() || val.contentEquals(currentValue)) return;
                else
                    mref.child("Ngos").child("NGO1").child("ngoUsers").child(user.getUid()).child("ngoUser_details").child("address").setValue(val).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                finish();
                            }
                        }
                    });

                break;
            case "Phone":
                //TODO
                // harsh to update phone number here
                val = data.getText().toString();
                if (val.isEmpty() || val.contentEquals(currentValue)) return;
                else
                    mref.child("Ngos").child("NGO1").child("ngoUsers").child(user.getUid()).child("ngoUser_details").child("phone").setValue(val).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                finish();
                            }
                        }
                    });
                break;
        }
    }
}
