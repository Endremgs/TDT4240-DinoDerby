package com.mygdx.game;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static android.content.ContentValues.TAG;

public class DesktopInterfaceClass implements FireBaseInterface {

    FirebaseDatabase database;
    DatabaseReference myRef;
    public DesktopInterfaceClass() {
        // Write a message to the database
        this.database = FirebaseDatabase.getInstance();
        this.myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }

    @Override
    public void SomeFunction() {
        System.out.println("det funker");
    }

    @Override
    public void firstFireBaseText() {
        if (myRef != null) {
            myRef.setValue("hello world!");
        }
    }

}
