package com.games.memorygame.ui.score;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.games.memorygame.R;
import com.games.memorygame.model.Score;
import com.games.memorygame.ui.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

class ScoreListViewAdapter extends RecyclerView.Adapter<ScoreListViewAdapter.ViewHolder> {

    private List<Score> scores;

    ScoreListViewAdapter(List<Score> scores) {
        this.scores = scores;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_score, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindView(scores.get(position));
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    class ViewHolder extends BaseViewHolder<Score> {

        @BindView(R.id.user_name)
        TextView userNameView;

        @BindView(R.id.date)
        TextView dateView;

        @BindView(R.id.score)
        TextView scoreView;

        ViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindView(Score item) {
            super.onBindView(item);
            userNameView.setText(item.getUser());
            dateView.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(item.getDate()));
            scoreView.setText(String.valueOf(item.getScorePoints()));
        }

    }
}
