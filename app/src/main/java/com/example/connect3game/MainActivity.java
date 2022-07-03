package com.example.connect3game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    TextView game_winner;
    ImageView player;
    Button r;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game_winner = findViewById(R.id.WinnerTV);
        r= findViewById(R.id.PlayButton);
        player = findViewById(R.id.player);
        linearLayout = findViewById(R.id.linearlayout);
    }

    //0 = red, 1 = yellow
    int active_player = 0;
    //2 = empty
    int [] game_state = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
    //positions on the grid where a player wins
    int [][] winning_pos = new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    //state of game
    boolean game_active = true;
    //Storing the color of winner
    String winner = "";

    int block_count =0 ;

    boolean is_draw = false;

    public void pressed(View view) {


        ImageView v = (ImageView) view;
        block_count++;
        //stores the value of the position of the grid which has been recently tapped
        int Tapped_counter = Integer.parseInt(v.getTag().toString());

        if(game_state[Tapped_counter]==2 && game_active) { //Validation when game ends and when a grid space is filled with a color

            //Setting that tapped position to a non empty value(0 or 1) i.e (Red or Yellow)
            game_state[Tapped_counter] = active_player;

            //sets position of the ImageView outside the display screen
            v.setY(-4000);


            if (active_player == 0 ) {
                v.setImageResource(R.drawable.red);
                active_player = 1;
                if(block_count < 9)
                    player.setImageResource(R.drawable.yellow);
                else
                    linearLayout.setVisibility(View.INVISIBLE);
            } else   {
                v.setImageResource(R.drawable.yellow);
                active_player = 0;
                player.setImageResource(R.drawable.red);
            }


            //Animates the ImageView and brings it on the grid
            v.animate().translationYBy(4100).alpha(1).setDuration(300);


            //Winning logic
            boolean someone_won=false;
            for (int[] winpos : winning_pos) {

                if (game_state[winpos[0]] == game_state[winpos[1]] && game_state[winpos[1]] == game_state[winpos[2]] && game_state[winpos[0]] != 2) {
                    someone_won =true;
                    game_active = false;

                    if (active_player == 1) {
                        winner = "Red";
                    } else
                        winner = "Yellow";

                    linearLayout.setVisibility(View.INVISIBLE);
                    game_winner.setText(String.format("%s has won!", winner));
                    game_winner.setVisibility(View.VISIBLE);
                    r.setVisibility(View.VISIBLE);

                }
            }

            //draw logic
            if(block_count == 9 && !someone_won ){
                game_winner.setText("Draw");
                game_winner.setVisibility(View.VISIBLE);
                r.setVisibility(View.VISIBLE);
            }

        }
    }

    public void play_again(View view) {
        TextView game_winner = findViewById(R.id.WinnerTV);
        Button r = findViewById(R.id.PlayButton);
        game_winner.setVisibility(View.INVISIBLE);
        r.setVisibility(View.INVISIBLE);
        this.recreate();
    }
}