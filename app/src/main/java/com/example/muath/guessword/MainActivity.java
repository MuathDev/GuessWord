package com.example.muath.guessword;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private TextView tv_score;
    private LinearLayout ll_word;
    private ArrayList<String> words;
    private String selectedword;
    private String incompleteword;
    private int score;

    private FloatingActionButton fab_next;
    private FloatingActionButton fab_save;
    private FloatingActionButton fab_help;
    private FloatingActionButton fab_reload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tv_score = findViewById(R.id.tv_sore);
        ll_word = findViewById(R.id.ll_word);
        fab_next = findViewById(R.id.fab_next);
        fab_save = findViewById(R.id.fab_save);
        fab_help = findViewById(R.id.fab_help);
        fab_reload = findViewById(R.id.fab_reload);


        loadwords();
        selectedword = selectRandomword();
        incompleteword = hideLetters();
        displayLetters(incompleteword);


        fab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkword();
            }
        });

        fab_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLetters(selectedword);
                fab_next.setEnabled(false);

            }
        });

        fab_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedword = selectRandomword();
                incompleteword = hideLetters();
                displayLetters(incompleteword);
                fab_next.setEnabled(true);
            }
        });

        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScore();
            }
        });
    }

    private void saveScore() {
        SharedPreferences pref = getSharedPreferences("com.abad",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(LocalDateTime.now().toString(),score);
        editor.apply();
    }

    private void checkword() {
        String answer = "";
        for (int i=0; i<ll_word.getChildCount(); i++){
            EditText et = (EditText) ll_word.getChildAt(i);
            answer += et.getText().toString();
        }

        if (answer.equals(selectedword)){
            score += 10;
            tv_score.setText(score + "");
            selectedword = selectRandomword();
            incompleteword = hideLetters();
            displayLetters(incompleteword);
        }
        else {
            Snackbar.make(ll_word, "Try Another Word", Snackbar.LENGTH_SHORT).show();
        }


    }

    private void displayLetters(String word) {
        ll_word.removeAllViewsInLayout();
        for (char letter : word.toCharArray()){
            EditText et = new EditText(this);

            et.setTextSize(64);

            if (letter == '?'){
                et.setHint(letter + "");
            }else {

                et.setText(letter + "");
                et.setFocusable(false);

            }

            ll_word.addView(et);

        }
    }

    private String hideLetters() {
        StringBuilder sb = new StringBuilder(selectedword);
        int indx = (int)(Math.random() * selectedword.length());
        sb.setCharAt(indx, '?');
        return sb.toString();
    }

    private String selectRandomword() {
        int idx = (int)(Math.random() * words.size());
        return words.get(idx);
    }

    private void loadwords(){

        try (Scanner sc = new Scanner(getAssets().open("words.txt"))){
            words = new ArrayList<>();
            while (sc.hasNext()){
                String word = sc.nextLine();
                if (word.length() > 3  && word.length() < 10){
                    words.add(word);
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
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
        if (id == R.id.action_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("tex/plain");
            intent.putExtra(Intent.EXTRA_TEXT,"I dare you to guess the word " + incompleteword);
            startActivity(intent);

            return true;
        }

        if (id == R.id.action_history){
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
