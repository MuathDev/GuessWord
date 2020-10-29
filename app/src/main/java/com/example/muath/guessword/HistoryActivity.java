package com.example.muath.guessword;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rv_history;
    private ArrayList<String> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rv_history = findViewById(R.id.rv_score);
        scores = new ArrayList<>();

        SharedPreferences pref = getSharedPreferences("com.abad", Context.MODE_PRIVATE);
        Map<String,?> data = pref.getAll();

        for (Map.Entry<String, ?> entery : data.entrySet()){
            scores.add(entery.getKey() + "\n" + entery.getValue());
        }

//        System.out.println(scores);

        Collections.sort(scores);

        ScoreAdapter adapter = new ScoreAdapter(LayoutInflater.from(this), scores);
        rv_history.setLayoutManager(new LinearLayoutManager(this));
        rv_history.setAdapter(adapter);

    }

}
