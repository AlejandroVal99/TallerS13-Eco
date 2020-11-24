package com.example.tallers13_eco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactsActivity extends AppCompatActivity {

    Button btn_NewContact;
    Button btn_Singout;
    EditText nameContact;
    EditText teleContact;
    ListView contactList;
    ContactsAdapter adapter;
    FirebaseDatabase db;
    FirebaseAuth auth;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);


        ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CALL_PHONE} ,1);

        btn_NewContact = findViewById(R.id.btn_NewContact);

        nameContact = findViewById(R.id.input_nameC);
        teleContact = findViewById(R.id.input_Number);
        contactList = findViewById(R.id.listContact);
        btn_Singout = findViewById(R.id.btn_Logout);
        adapter = new ContactsAdapter();
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        contactList.setAdapter(adapter);

        btn_NewContact.setVisibility(View.INVISIBLE);

        recorverUser();

        btn_NewContact.setOnClickListener(
                (v)->{

                    Log.e(">>>>>>>>", userId);
                    String name = nameContact.getText().toString();
                    String number = teleContact.getText().toString();

                    boolean inputVer = name.isEmpty() || number.isEmpty();
                    if(inputVer){
                        Toast.makeText(this,"Complete all fields",Toast.LENGTH_LONG).show();
                    }else{
                        String newId = db.getReference().child("contacts").child(userId).push().getKey();
                        DatabaseReference reference = db.getReference().child("tallers14").child("contacts").child(userId).child(newId);
                        Contact newContact = new Contact(
                                newId,
                                userId,
                                name,
                                number
                        );
                        reference.setValue(newContact);
                        nameContact.setText("");
                        teleContact.setText("");
                    }

        });

        btn_Singout.setOnClickListener(
                (v)->{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle("Log out")
                            .setMessage("Are you sure you want to log out")
                            .setNegativeButton("No",(dialog,id)->{
                                dialog.dismiss();
                            })
                            .setPositiveButton("Yes" ,(dialog,id)->{
                                auth.signOut();
                                finish();
                            });
                    builder.show();
                }
        );

    }

    public void recorverUser() {
        if(auth.getCurrentUser() != null){
            String id = auth.getCurrentUser().getUid();

            db.getReference().child("tallers14").child("users").child(id).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            userId = user.getId();
                            loadContacts();
                            btn_NewContact.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }
            );

        }


    }

    public void loadContacts() {

        DatabaseReference ref = db.getReference().child("tallers14").child("contacts").child(userId);
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot snapshot) {
                        adapter.clear();
                        for(DataSnapshot child: snapshot.getChildren()){
                           Contact nContact = child.getValue(Contact.class);
                           adapter.addContact(nContact);
                        }
                    }

                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                }
        );

    }
}