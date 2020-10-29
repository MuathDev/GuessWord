package com.example.muath.guessword;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder>{

    private ArrayList<String> scores;
    private LayoutInflater inflater;

    public ScoreAdapter(LayoutInflater inflater, ArrayList<String> scores){
        this.inflater = inflater;
        this.scores = scores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.score_card,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.tv_score.setText(scores.get(i));
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_score = itemView.findViewById(R.id.tv_score);
        }
    }
}
