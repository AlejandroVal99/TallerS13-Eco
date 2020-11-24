package com.example.tallers13_eco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button btn_SignUp;
    EditText edi_Password, edi_Email, edi_Telephone, edi_ConPassword, edi_Name;
    TextView linkLogin;
    FirebaseDatabase db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        edi_Password = findViewById(R.id.edi_Password);
        edi_Email = findViewById(R.id.edi_Email);
        edi_Telephone = findViewById(R.id.edi_Telephone);
        edi_ConPassword = findViewById(R.id.edi_ConPassword);
        edi_Name = findViewById(R.id.edi_Name);
        btn_SignUp = findViewById(R.id.btn_SignIn);
        linkLogin = findViewById(R.id.linkLogin);

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        btn_SignUp.setOnClickListener(
                (v) -> {
                    //Input verification

                    String password = edi_Password.getText().toString();
                    String conPassword = edi_ConPassword.getText().toString();
                    String telephone = edi_Telephone.getText().toString();
                    String name = edi_Name.getText().toString();
                    String email = edi_Email.getText().toString().trim();

                    boolean passwordVer = password.equals(conPassword);
                    boolean inputVer = password.isEmpty() || conPassword.isEmpty() || telephone.isEmpty() || name.isEmpty() || email.isEmpty();

                    if (inputVer) {
                        Toast.makeText(this, "Complete all fields", Toast.LENGTH_LONG).show();

                    } else {
                        if (passwordVer) {
                            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                                    task -> {
                                        if(task.isSuccessful()){
                                            String id = auth.getCurrentUser().getUid();
                                            User user = new User(
                                                    id,
                                                    name,
                                                    telephone,
                                                    email,
                                                    password
                                            );
                                            db.getReference().child("tallers14").child("users").child(id).setValue(user).addOnCompleteListener(
                                                    taskdb->{
                                                        if(taskdb.isSuccessful()){
                                                            Intent i = new Intent(this, ContactsActivity.class);
                                                            startActivity(i);
                                                            finish();
                                                        }else{

                                                        }
                                                    }
                                            );

                                        }else{
                                            Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                            );
                        } else {
                            Toast.makeText(this, "Passwords is not match", Toast.LENGTH_LONG).show();
                        }
                    }

                }

        );

        linkLogin.setOnClickListener(
                (v) -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                }
        );
    }

}