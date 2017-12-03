package com.example.radus.learnroomdatabase;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        User user = new User();
        user.setFirstName("NOP");
        user.setLastName("Saini");
        user.setAge(25);
        ArrayList <Integer> aux = new ArrayList<>();
        aux.add(1);
        aux.add(2);
        user.setVector(aux);
        addUser(db, user);
        List<User> list = db.userDao().getAll();
        for (User u : list) {
            List<Integer> l = u.getVector();
                Log.d("TAG", l.get(0).toString() + " " + l.get(1).toString());
        }
    }

    private User addUser(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }
}
