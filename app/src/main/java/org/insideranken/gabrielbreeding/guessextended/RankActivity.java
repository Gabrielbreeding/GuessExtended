package org.insideranken.gabrielbreeding.guessextended;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class RankActivity extends AppCompatActivity {
    Button btnRankToHomePage;
    ImageView ivRank;
    TextView tvRank;
    Drawable drawable = null;

    Integer guesses = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        btnRankToHomePage = findViewById(R.id.btnRankToHomePage);
        ivRank            = findViewById(R.id.ivRank);
        tvRank            = findViewById(R.id.tvRank);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            if (extras.containsKey("guesses")){
                guesses = extras.getInt("guesses", 1);
            }

            if (guesses >= 1 && guesses <= 5) {
                drawable = getResources().getDrawable((R.drawable.wow));
                tvRank.setText("WOW");
            } else if (guesses >= 6 && guesses <= 10) {
                drawable = getResources().getDrawable((R.drawable.average));
                tvRank.setText("AVERAGE");
            } else if (guesses > 10) {
                drawable = getResources().getDrawable((R.drawable.novice));
                tvRank.setText("NOVICE");
            } else {
                tvRank.setText("ERROR");
            }

            ivRank.setImageDrawable(drawable);
            ivRank.setMaxWidth(100);
            ivRank.setMaxHeight(100);
        } else {
            tvRank.setText("ERROR NO DATA PASSED");
        }


        btnRankToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    // Its a toaster, it pops out toast. What more to explain?
    private void toaster (String message) {
        Toast.makeText(RankActivity.this, message, Toast.LENGTH_LONG).show();
    }
}