package com.example.a302.mytestapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MemberActivity extends AppCompatActivity {
    EditText useremail;
    EditText password;
    private FirebaseAuth mAuth;
    String TAG="MembersActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberlayout);

        useremail = (EditText) findViewById(R.id.signupemail);
        password = (EditText) findViewById(R.id.signuppwd);

        mAuth = FirebaseAuth.getInstance();

        Button membership = (Button) findViewById(R.id.signupbutton);

        membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stemail=useremail.getText().toString();
                String stpwd=password.getText().toString();

                createAccount(stemail,stpwd);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    public void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(MemberActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(MemberActivity.this,"회원가입 성공",Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MemberActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }



                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = useremail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            useremail.setError("Required.");
            valid = false;
        } else {
            useremail.setError(null);
        }

        String pwd =password .getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }
    private void updateUI(FirebaseUser user) {

        if (user != null) {

        } else {

        }
    }


}
