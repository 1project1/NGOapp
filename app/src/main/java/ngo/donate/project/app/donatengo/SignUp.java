package ngo.donate.project.app.donatengo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by Aakash on 20-Feb-17.
 */

public class SignUp extends DialogActivity implements View.OnClickListener {
    Button signUp;
    EditText userName, password, confirmPassword, name;

    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        init();

         mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User successfully Signed In
                    Log.d("Firebase", "onAuthStateChanged:signed_in:" + user.getUid());

                    UserProfileChangeRequest up = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name.getText().toString()).build();
                    user.updateProfile(up);

                    user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Log.w("App", "EmailVerification", task.getException());
                            }
                        }
                    });
                    finish();
                } else {
                    //User Signed out
                    Log.d("Firebase", "onAuthStateChanged:signed_out");
                }
            }
        };


        signUp.setOnClickListener(this);
    }

    private void init() {
        // FireBase
        mAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.name_signup);
        userName = (EditText) findViewById(R.id.email_signup);
        password = (EditText) findViewById(R.id.password_signup);
        confirmPassword = (EditText) findViewById(R.id.confirm_password_signup);

        signUp = (Button) findViewById(R.id.signUp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUp:
                String n = name.getText().toString();
                String u = userName.getText().toString();
                String p = password.getText().toString();
                String cp = confirmPassword.getText().toString();

                if (n.isEmpty() || u.isEmpty() || p.isEmpty() || cp.isEmpty()) {
                    Snackbar.make(v, "Fill All details", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (!p.equals(cp)) {
                    password.setError("Passowords don't Match");
                    confirmPassword.setError("Passowords don't Match");
                    return;
                }

                showProgressDialog();
                mAuth.createUserWithEmailAndPassword(u, p)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("Firebase", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w("App", "signInWithEmail", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                hideProgressDialog();
                                // ...
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof FirebaseAuthException) {
                                    String error = ((FirebaseAuthException) e).getErrorCode();
                                    Log.e("Error", error);

                                    //[Temp]
                                    TextView tv = (TextView) findViewById(R.id.signup_error);
                                    tv.setText(error);
                                    tv.setVisibility(View.VISIBLE);
                                }
                            }
                        })
                ;

                break;

        }
    }




    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}

