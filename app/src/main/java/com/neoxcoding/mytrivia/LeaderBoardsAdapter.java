package com.neoxcoding.mytrivia;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import static com.neoxcoding.mytrivia.PrefrenceData.getCurrentUserCountry;
import static com.neoxcoding.mytrivia.PrefrenceData.getCurrentUserName;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class LeaderBoardsAdapter extends RecyclerView.Adapter<LeaderBoardsAdapter.ViewHolder> {

    private ViewHolder holder;
    private int position;


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textRank;
        TextView textName;
        TextView textScore;
        TextView textCountry;

        LinearLayout mainLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            mainLayout = itemView.findViewById(R.id.main_layout);
            textCountry = itemView.findViewById(R.id.country_text);//done
            textName = itemView.findViewById(R.id.name_text);//done
            textScore = itemView.findViewById(R.id.score_text);//done
            textRank = itemView.findViewById(R.id.rank_text);

        }
    }

    private List<LeaderBoards> reportList;


    private Context context;

    public LeaderBoardsAdapter(List<LeaderBoards> reportsList) {

        this.reportList = reportsList;
    }

    @NonNull
    @Override
    public LeaderBoardsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View leaderboardsView = LayoutInflater.from(context).inflate(R.layout.item_report, parent, false);
        return new LeaderBoardsAdapter.ViewHolder(leaderboardsView);
    }


    @Override
    public void onBindViewHolder(@NonNull final LeaderBoardsAdapter.ViewHolder holder, int position) {

        //MAKE A REFERENCE TO ITEM_REPORT CONTEXT
        final View tavnit = LayoutInflater.from(context).inflate(R.layout.item_report, null);
        //MAKE A REFERENCE TO THE MAIN LAYOUT INSIDE ITEM_REPORT
        LinearLayout mylayout = tavnit.findViewById(R.id.main_layout);





        LeaderBoards leaderBoards = reportList.get(position);


        //SET COUNTRY

        String userCountry = String.valueOf(leaderBoards.getCountry());

        switch (userCountry){

            case "ISRAEL":
                holder.textCountry.setBackground(ContextCompat.getDrawable(context, R.drawable.israel_flag));;
break;

            case "USA":
                holder.textCountry.setBackground(ContextCompat.getDrawable(context, R.drawable.usa_flag));;
                break;

            case "CANADA":
                holder.textCountry.setBackground(ContextCompat.getDrawable(context, R.drawable.canada_flag));;
                break;

            case "JAPAN":
                holder.textCountry.setBackground(ContextCompat.getDrawable(context, R.drawable.japa_flag));;
                break;

            case "FRANCE":
                holder.textCountry.setBackground(ContextCompat.getDrawable(context, R.drawable.france_flag));;
                break;



        }








        //SET SCORE
        holder.textScore.setText(String.valueOf(leaderBoards.getScore()));
        //SET RANK
        holder.textRank.setText(""+(position+1));

        //SET NAME
        try {

            //If this(name in leaderboard) is the same as the current user
            if (String.valueOf(leaderBoards.getName()).equals(getCurrentUserName(context))) {
                holder.textName.setText("You");

                holder.mainLayout.setBackgroundColor(Color.parseColor("#B7FBD819"));
            } else {
                holder.textName.setText(String.valueOf(leaderBoards.getName()));


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }
}