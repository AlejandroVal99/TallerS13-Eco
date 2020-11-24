package com.example.tallers13_eco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button btn_SignUp;
    EditText eT_Password, eT_Email;
    FirebaseDatabase db;
    FirebaseAuth auth;
    TextView linkRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        btn_SignUp = findViewById(R.id.btn_SignUp);
        eT_Password = findViewById(R.id.eT_Password);
        eT_Email = findViewById(R.id.eT_Email);
        linkRegister = findViewById(R.id.linkRegister);


        btn_SignUp.setOnClickListener(
                (v) -> {
                    String email = eT_Email.getText().toString();
                    String password = eT_Password.getText().toString().trim().toLowerCase();

                    boolean inputVer = email.isEmpty() || password.isEmpty();

                    if(inputVer){
                        Toast.makeText(this, "Complete all fields", Toast.LENGTH_LONG).show();
                    }else{
                        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(
                                task ->{
                                    if(task.isSuccessful()){
                                        Intent i = new Intent(this, ContactsActivity.class);
                                        startActivity(i);
                                        finish();
                                    }else{
                                        Toast.makeText(this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                    }


                }

        );

        linkRegister.setOnClickListener(
                (v)->{

                    eT_Email.setText("");
                    eT_Password.setText("");
                    Intent i = new Intent(this, RegisterActivity.class);
                    startActivity(i);
                }
        );

    }


}

