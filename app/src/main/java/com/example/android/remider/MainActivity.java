package com.example.android.remider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Reminder> list;
    ArrayList<String> key = new ArrayList<>();
    CustomAdapter adapter;
    DatabaseReference mdatabasereference;
    Toolbar mToolbar;
    private com.getbase.floatingactionbutton.FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.List);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // Setup toolbar
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        //setup FloatingActionButton
        fab = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, add_Remider.class);
                startActivity(intent);
            }
        });
        ////////setup Adapter
        adapter = new CustomAdapter(MainActivity.this, R.layout.item_listview, list);
        adapter.notifyDataSetChanged();
        //Load data from Firebase
        mdatabasereference = FirebaseDatabase.getInstance().getReference().getRef();
        mdatabasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Reminder reminder = snapshot.getValue(Reminder.class);
                    key.add(snapshot.getKey());
                    list.add(reminder);
                }
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ReminderEditActivity.class);
                String Key = mdatabasereference.getRoot().child(key.get(position)).getKey();
                intent.putExtra("congviec", Key);
                Log.d("KEY", Key);
                startActivity(intent);
            }
        });
    }
}
