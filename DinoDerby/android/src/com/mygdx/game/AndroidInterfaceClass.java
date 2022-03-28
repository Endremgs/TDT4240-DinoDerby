package com.mygdx.game;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static android.content.ContentValues.TAG;


public class AndroidInterfaceClass implements FireBaseInterface {
//    FireBaseDatabase database

    FirebaseDatabase database;
    DatabaseReference myRef;
    public AndroidInterfaceClass() {
        // Write a message to the database
        this.database = FirebaseDatabase.getInstance("https://dino-derby-default-rtdb.europe-west1.firebasedatabase.app");
        this.myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }

    @Override
    public void SomeFunction() {
        try {
            myRef.setValue("hallelujah");
        System.out.println("det funker");

        }
        catch (Exception e){
            System.out.println("oh no");
            System.out.println(e);
        }

    }

    @Override
    public void firstFireBaseText() {
        System.out.println(myRef);
        if (myRef != null) {
            myRef.setValue("Det funker faktisk");
        }
    }


}
