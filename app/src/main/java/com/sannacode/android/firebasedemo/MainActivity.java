package com.sannacode.android.firebasedemo;

import android.support.v7.app.AppCompatActivity;
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
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "taglog" ;

    EditText etReference, etMessage;
    Button bSend;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etReference = (EditText) findViewById(R.id.etReference);
        etMessage= (EditText) findViewById(R.id.etMessage);
        bSend = (Button) findViewById(R.id.bSend);
        bSend.setOnClickListener(this);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

    }

    @Override
    public void onClick(View v) {
        myRef = database.getReference(etReference.getText().toString());
        Map <String,Object> map= new HashMap<>();
        map.put("something", etMessage.getText().toString());
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        map.put("date", "" + cal.get(Calendar.HOUR) + "." + cal.get(Calendar.MINUTE) + "." + cal.get(Calendar.SECOND));
        myRef.setValue(map);
        SimpleObject so = new SimpleObject();
        so.name = "myname";
        so.age = 16;
        so.time = "" + cal.get(Calendar.HOUR) + "." + cal.get(Calendar.MINUTE) + "." + cal.get(Calendar.SECOND);
        so.something = etMessage.getText().toString();
        //myRef.setValue(etMessage.getText().toString());
        myRef.setValue(so);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
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
