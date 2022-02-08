package org.insideranken.gabrielbreeding.guessextended;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TotalsActivity extends AppCompatActivity {
    Button btnTotalsToHomePage;
    TextView tvWow;
    TextView tvAverage;
    TextView tvNovice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totals);

        btnTotalsToHomePage = findViewById(R.id.btnTotalsToHomePage);
        tvWow = findViewById(R.id.tvWow);
        tvAverage = findViewById(R.id.tvAverage);
        tvNovice = findViewById(R.id.tvNovice);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (!extras.isEmpty() && extras != null) {
            if (extras.containsKey("totalWow")) {
                tvWow.setText("Total Amount of WOW Ranks: " + extras.getInt("totalWow"));
            }
            if (extras.containsKey("totalAverage")) {
                tvAverage.setText("Total Amount of AVERAGE Ranks: " + extras.getInt("totalAverage"));
            }
            if (extras.containsKey("totalNovice")) {
                tvNovice.setText("Total Amount of NOVICE Ranks: " + extras.getInt("totalNovice"));
            }
        }

        btnTotalsToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}