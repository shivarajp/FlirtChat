package com.flirtchat.app;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.flirtchat.app.adapters.ChatAdapter;
import com.flirtchat.app.models.MessageDataModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String url = "https://flirtchat.firebaseio.com/data";
    private Firebase mFirebase;
    private LinearLayout llchat;
    private ListView lvConversation;
    private ChatAdapter adapter;
    private ImageView ivSend;
    private EditText etNewMsg;
    private ArrayList<MessageDataModel> messages = new ArrayList<MessageDataModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        mFirebase = new Firebase(url + "/groups/mca");
        lvConversation = (ListView) findViewById(R.id.lvConversation);
        llchat = (LinearLayout) findViewById(R.id.rlchat);
        ivSend = (ImageView) findViewById(R.id.ivSend);
        etNewMsg = (EditText) findViewById(R.id.etNewMsg);

        adapter = new ChatAdapter(this, messages);
        lvConversation.setAdapter(adapter);

        mFirebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageDataModel msg = dataSnapshot.getValue(MessageDataModel.class);
                messages.add(msg);
                adapter.notifyDataSetChanged();
                lvConversation.post(new Runnable() {
                    @Override
                    public void run() {
                        lvConversation.smoothScrollToPosition(adapter.getCount() - 1);
                    }
                });
                Snackbar.make(llchat, "Message Recieved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MessageDataModel msg = new MessageDataModel();
                msg.setName("Shivaraj");
                msg.setMessage(etNewMsg.getText().toString());
                mFirebase.push().setValue(msg);
                Snackbar.make(view, "Message Sent", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
