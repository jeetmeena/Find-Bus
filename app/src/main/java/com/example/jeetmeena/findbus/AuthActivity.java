package com.example.jeetmeena.findbus;

import android.content.Intent;
import android.os.PatternMatcher;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {
FirebaseAuth mAuth;
EditText email0,password0;
Button signUp;
TextView signUpText;
    static  String email;
   static String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        signUpText=findViewById(R.id.signUp);
        signUp=findViewById(R.id.signIn);
        signUpText.setOnClickListener(this);


    }

//d1SggsLZt2NDqgj78uD672MbEgG2
    @Override
    public void onClick(View v) {
        mAuth=FirebaseAuth.getInstance();
        email0=findViewById(R.id.emailEditText);

        password0=findViewById(R.id.passwerdEditText);
        email=email0.getText().toString();
        password=password0.getText().toString();
        if(R.id.signIn==v.getId()){

            if(email.isEmpty()){
                email0.setError("Please Enter Email");
                return;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                email0.setError("Please Enter a valid Email");
                return;
            }
            if(password.isEmpty() ){

                password0.setError("please Enter Password");
                return;
            }




                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String i=user.getEmail();
                                    String b=user.getUid();
                                    MainActivity mainActivity=MainActivity.getMainActivity();
                                    mainActivity.savesharedPerferns("LogedIn","true");
                                    Toast.makeText(AuthActivity.this, "signInWithEmail:success."+i+b,
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AuthActivity.this,MainActivity.class));
                                    finish();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(AuthActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //  updateUI(null);
                                }

                                // ...
                            }
                        });


            }
        else if(R.id.signUp==v.getId()){

            Intent intent=new Intent(AuthActivity.this,SignUpActivity.class);
            startActivity(intent);
             finish();
        }


    }
}
