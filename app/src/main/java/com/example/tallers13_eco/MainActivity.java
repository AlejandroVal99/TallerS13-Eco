package com.example.tallers13_eco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button goContacts;
    EditText eT_Username;
    FirebaseDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance();
        goContacts = findViewById(R.id.btn_goContacts);
        eT_Username = findViewById(R.id.ediT_Username);

        goContacts.setOnClickListener(
                (v) -> {

                    String username = eT_Username.getText().toString().trim().toLowerCase();

                    db.getReference().child("Users").orderByChild("username").equalTo(username).addListenerForSingleValueEvent(

                            new ValueEventListener() {

                                public void onDataChange(DataSnapshot data) {

                                    if(data.exists()){
                                        for(DataSnapshot child: data.getChildren()) {
                                            User userExist = child.getValue(User.class);
                                            String oldId = userExist.getId();

                                            Log.e(">>>>>>>", "Existo ");
                                            Log.e("OldUser", " "+oldId );
                                            Intent e = new Intent(MainActivity.this, ContactsActivity.class);
                                            e.putExtra("idUser", oldId);
                                            startActivity(e);
                                        }
                                    }else{
                                        String newId = db.getReference().child("Users").push().getKey();
                                        DatabaseReference reference = db.getReference("Users").child(newId);
                                        User newUser = new User(
                                                newId,
                                                username
                                        );
                                        reference.setValue(newUser);
                                        Log.e("NewUser", newUser.getUsername() );
                                        Intent i = new Intent(MainActivity.this, ContactsActivity.class);
                                        i.putExtra("idUser", newId);
                                        startActivity(i);

                                    }




                                    Log.e("Usuario que existe", "onDataChange: " );


                                }
                                public void onCancelled(DatabaseError error) {
                                }
                            }
                    );

                }

        );

    }


}

/*  if (userExist == null) {
                                            Log.e(">>>>>>>", "No existo ");

                                        } else {
                                            String oldId = userExist.getId();

                                            Log.e(">>>>>>>", "Existo ");
                                            Log.e("OldUser", " "+oldId );
                                            Intent e = new Intent(MainActivity.this, ContactsActivity.class);
                                            e.putExtra("idUser", oldId);
                                            startActivity(e);
                                        }*/