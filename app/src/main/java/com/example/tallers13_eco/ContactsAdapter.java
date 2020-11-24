package com.example.tallers13_eco;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ContactsAdapter extends BaseAdapter {
    ArrayList<Contact> contactData;
    FirebaseDatabase db;

    public ContactsAdapter(){
        contactData = new ArrayList<>();
        db = FirebaseDatabase.getInstance();
    }

    public void addContact(Contact newContact){
        contactData.add(newContact);
        notifyDataSetChanged();
    }

    public void clear(){
        contactData.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return contactData.size();
    }

    @Override
    public Object getItem(int position) {
        return contactData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int pos, View row, ViewGroup list) {

        LayoutInflater inflater = LayoutInflater.from(list.getContext());
        View rowView = inflater.inflate(R.layout.contact_row, null);

        Contact viewContact = contactData.get(pos);

        Button btnCall = rowView.findViewById(R.id.btn_Call);
        Button btnDelete = rowView.findViewById(R.id.btn_Delete);
        TextView contact_Name = rowView.findViewById(R.id.contact_Name);
        TextView contact_Number = rowView.findViewById(R.id.contact_Number);

        contact_Name.setText(viewContact.getName());
        contact_Number.setText(viewContact.getNumber());

        btnDelete.setOnClickListener(
                (v)->{
                    String userId = viewContact.getUserId();
                    String id =viewContact.getId();

                    Log.e(">>>>>>", id + "   "+userId);
                    DatabaseReference ref = db.getReference().child("tallers14").child("contacts").child(userId).child(id);
                    ref.setValue(null);
                }
        );

        btnCall.setOnClickListener(
                (v)->{
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    String numberCall =  viewContact.getNumber();
                    callIntent.setData(Uri.parse("tel:"+numberCall));
                    rowView.getContext().startActivity(callIntent);


                }
        );
        return rowView;
    }
}
