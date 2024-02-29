package com.example.hw224a10357.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw224a10357.Model.Player;
import com.example.hw224a10357.R;
import com.example.hw224a10357.interfaces.Callback_highScoreClicked;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private Context context;
    private Player[] players;

    private Callback_highScoreClicked callbackHighScoreClicked;

    public PlayerAdapter(Context context, Player[] players) {
        this.context = context;
        this.players = players;
    }

    private Player getItem(int position) {
        return players[position];
    }


    public void setCallbackHighScoreClicked(Callback_highScoreClicked callbackHighScoreClicked) {
        this.callbackHighScoreClicked = callbackHighScoreClicked;
    }


    @NonNull
    @Override
    public PlayerAdapter.PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_player_info, parent, false);
        return new PlayerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.PlayerViewHolder holder, int position) {
        Player player = getItem(position);
        holder.player_info_TXT_name.setText(player.getName());
        int rank = position+1;
        holder.player_info_TXT_pos.setText("#" +rank);
        holder.player_info_TXT_score.setText(String.valueOf(player.getPoints()));

    }

    @Override
    public int getItemCount() {
        return players == null ? 0 : players.length;
    }


    public class PlayerViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView player_info_TXT_pos;
        private MaterialTextView player_info_TXT_name;
        private MaterialTextView player_info_TXT_score;
        private MaterialCardView player_CRD;

        public PlayerViewHolder(@NonNull View itemView) {

            super(itemView);
            player_info_TXT_pos = itemView.findViewById(R.id.player_info_TXT_pos);
            player_info_TXT_name = itemView.findViewById(R.id.player_info_TXT_name);
            player_info_TXT_score = itemView.findViewById(R.id.player_info_TXT_score);
            player_CRD = itemView.findViewById(R.id.player_CRD);
            player_CRD.setOnClickListener(v-> {
                if(callbackHighScoreClicked!= null)
                    callbackHighScoreClicked.highScoreClicked(getItem(getAdapterPosition()),getAdapterPosition());
            });
        }
    }


}







