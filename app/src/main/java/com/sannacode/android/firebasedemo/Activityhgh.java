package com.sannacode.android.firebasedemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;


public class Activityhgh {
    private static final String TAG = "taglog" ;

    EditText etReference, etMessage;
    Button bSend;
    DatabaseReference database;
    DatabaseReference myRef;


    public void onClick(View v) {
        // myRef = database.child("root");
        myRef = myRef.child(etReference.getText().toString());



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SimpleObject ds = dataSnapshot.getValue(SimpleObject.class);

                String value = ds.something;
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
