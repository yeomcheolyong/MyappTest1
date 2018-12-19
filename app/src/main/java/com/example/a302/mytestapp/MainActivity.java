package com.example.a302.mytestapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    EditText editid;
    EditText editpasswd;
    private FirebaseAuth mAuth;
    String TAG="MainActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editid = (EditText) findViewById(R.id.editid);
        editpasswd = (EditText) findViewById(R.id.editpasswd);
        mAuth = FirebaseAuth.getInstance();



        Button btlogin = (Button) findViewById(R.id.btlogin);
        Button btsign=(Button)findViewById(R.id.btsignup);
        Button btout=(Button)findViewById(R.id.btout);

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid=editid.getText().toString();
                String userpwd=editpasswd.getText().toString();
                signIn(userid,userpwd);

            }
        });


        btsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),MemberActivity.class);
                startActivity(in);
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
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }



        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        editid = (EditText) findViewById(R.id.editid);
                        String userid=editid.getText().toString();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent=new Intent(getApplicationContext(),ChatActivity.class);
                            intent.putExtra("user_id",userid);
                            //Toast.makeText(MainActivity.this,userid,Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "로그인실패.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                    }
                });

    }


    private boolean validateForm() {
        boolean valid = true;

        String email = editid.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editid.setError("Required.");
            valid = false;
        } else {
            editid.setError(null);
        }

        String pwd =editpasswd.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            editpasswd.setError("Required.");
            valid = false;
        } else {
            editpasswd.setError(null);
        }

        return valid;
    }
    private void updateUI(FirebaseUser user) {

        if (user != null) {

        } else {

        }
    }

}
