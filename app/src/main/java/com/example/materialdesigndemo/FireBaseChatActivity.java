package com.example.materialdesigndemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Random;

/**
 * Created by plalit on 6/21/2016.
 */
public class FireBaseChatActivity extends AppCompatActivity {

    private static final String TAG = FireBaseChatActivity.class.getSimpleName();
    private Firebase mFireBaseReference;
    private String[] messages = new String[]{"Hi", "How are you?", "I am fine, thanks!", "What are you upto?",
            "Firebase looks great isn't it", "Yeah, Firebase is awesome!"};
    private Random random = new Random();
    private static final String CHAT_ROOM_NAME = "friends";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpFireBase();
        addNewMessage("lalit", messages[random.nextInt(6)]);
        setUpFireBaseValueListener();
        setUpFireBaseChildListener();
    }

    private void setUpFireBase(){
        Firebase.setAndroidContext(this);
        mFireBaseReference = new Firebase("https://lalit3686.firebaseio.com/");
    }

    private void addNewMessage(String sender, String message){
        Chat newChat = new Chat(System.currentTimeMillis(), sender, message);
        mFireBaseReference.child(CHAT_ROOM_NAME).push().setValue(newChat);
    }

    private void setUpFireBaseValueListener(){
        mFireBaseReference.child(CHAT_ROOM_NAME).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Log.e(TAG, "onDataChange - "+dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    private void setUpFireBaseChildListener(){
        mFireBaseReference.child(CHAT_ROOM_NAME).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getValue() != null){
                    Log.e(TAG, "onChildAdded - "+dataSnapshot.getValue().toString());
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    Log.e(TAG, chat.getSender()+" - "+chat.getMessage());
                }
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
    }
}
