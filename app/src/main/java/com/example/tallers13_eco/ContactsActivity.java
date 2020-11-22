package com.example.tallers13_eco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactsActivity extends AppCompatActivity {

    Button btn_NewContact;
    EditText nameContact;
    EditText teleContact;
    ListView contactList;
    ContactsAdapter adapter;
    FirebaseDatabase db;
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
        adapter = new ContactsAdapter();
        db = FirebaseDatabase.getInstance();
        contactList.setAdapter(adapter);
        userId = getIntent().getExtras().getString("idUser");

        btn_NewContact.setOnClickListener(
                (v)->{
                    Log.e(">>>>>>>>", userId);
                    String name = nameContact.getText().toString();
                    String number = teleContact.getText().toString();
                    String newId = db.getReference().child("Contacts").child(userId).push().getKey();
                    DatabaseReference reference = db.getReference("Contacts").child(userId).child(newId);
                    Contact newContact = new Contact(
                            newId,
                            userId,
                            name,
                            number

                    );
                    reference.setValue(newContact);
        });

        loadContacts();
    }

    public void loadContacts() {

        DatabaseReference ref = db.getReference().child("Contacts").child(userId);
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        adapter.clear();
                        for(DataSnapshot child: snapshot.getChildren()){
                           Contact nContact = child.getValue(Contact.class);
                           adapter.addContact(nContact);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

    }
}