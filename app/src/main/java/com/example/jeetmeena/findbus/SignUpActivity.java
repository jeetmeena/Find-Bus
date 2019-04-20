package com.example.jeetmeena.findbus;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth mAuthSignUP;
    EditText email0,password0;
    Button signUp;
    static  String email;
    static String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUp=findViewById(R.id.signUp);
    }





    @Override
    public void onClick(View v) {
        mAuthSignUP=FirebaseAuth.getInstance();
        email0=findViewById(R.id.emailEditText);

        password0=findViewById(R.id.passwerdEditText);
        email=email0.getText().toString();
        password=password0.getText().toString();


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
            if(password.length()<6){
                password0.setError("Please Enter Minimum length of 6 ");
            }

            if(!email.isEmpty() && !password.isEmpty()){
            mAuthSignUP.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuthSignUP.getCurrentUser();
                            MainActivity mainActivity=MainActivity.getMainActivity();
                            mainActivity.savesharedPerferns("LogedIn","true");
                            // String i=user.getEmail();
                            // String b=user.getUid();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //   updateUI(null);
                        }

                        // ...
                    }
                });}

    }
}
