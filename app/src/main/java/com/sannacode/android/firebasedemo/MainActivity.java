package com.sannacode.android.firebasedemo;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "taglog" ;
    private static final String FIREBASE_MESSAGES = "messages";

    private static DatabaseReference last_message_reference;

    DatabaseReference database;
    DatabaseReference databaseMessages;

    ArrayList<String> messageList = new ArrayList<>();
    ArrayList<Model> models = new ArrayList<>();
    ArrayAdapter<String> adapter;

    EditText etMessage;
    EditText etAuthor;
    SeekBar sbRating;
    Button bSend;
    Button bDelete;
    ListView listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage = (EditText) findViewById(R.id.etMessage);
        etAuthor = (EditText) findViewById(R.id.etAuthor);
        sbRating = (SeekBar) findViewById(R.id.sbRating);
        bSend = (Button) findViewById(R.id.bSend);
        bSend.setOnClickListener(this);
        bDelete = (Button) findViewById(R.id.bDelete);
        bDelete.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, messageList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Model m = models.get (position);
                DatabaseReference ref = databaseMessages.child(m.getReference());
                ref.setValue(null);

                return true;
            }
        });

        database = FirebaseDatabase.getInstance().getReference();
        databaseMessages = database.child(FIREBASE_MESSAGES);

        databaseMessages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Model temp = dataSnapshot.getValue(Model.class);
                last_message_reference = dataSnapshot.getRef();
                temp.setReference(last_message_reference.getKey());
                messageList.add(0,temp.message);
                models.add(0, temp);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Model temp = dataSnapshot.getValue(Model.class);
                temp.setReference(dataSnapshot.getKey());
                models.remove(temp);
                messageList.remove(temp.message);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bSend:

                Model model = new Model(etMessage.getText().toString(), sbRating.getProgress(), etAuthor.getText().toString());

                DatabaseReference currentMessage = databaseMessages.push();
                currentMessage.setValue(model);

                String key = currentMessage.getKey();
                Log.d(TAG, key);
                model.setReference(key);


                break;
            case R.id.bDelete:

                last_message_reference.setValue(null);

                break;


        }
    }
}
