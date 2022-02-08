package org.insideranken.gabrielbreeding.guessextended;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Widgets
    EditText etGuessNumber;
    TextView tvGuesses;
    Button btnCalculate;
    Button btnRank;
    Button btnTotals;
    Button btnClear;

    // Global Constants;
    final Integer MINIMUM = 1;
    final Integer MAXIMUM = 100;

    // Global Variables
    ArrayList<Integer> usedNumbers = new ArrayList<>();
    Integer totalWow = 0;
    Integer totalAverage = 0;
    Integer totalNovice = 0;
    Integer secretNumber;

    // Errors
    final String NOVAL          = "You Must Enter a Number";
    final String ALREADYGUESSED = " Has Already Been Guessed!";
    final String OUTOFRANGE     = " Is Out Of Range between " + MINIMUM + " " + MAXIMUM;
    final String TOOHIGH        = " Is Too High";
    final String TOOLOW         = " Is Too Low";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        secretNumber = getSecret();

        etGuessNumber = findViewById(R.id.etGuessNumber);
        tvGuesses    = findViewById(R.id.tvGuesses);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnRank      = findViewById(R.id.btnRank);
        btnClear     = findViewById(R.id.btnClear);
        btnTotals    = findViewById(R.id.btnTotals);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkGuess(view);
                etGuessNumber.setText("");
            }
        });
        btnRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer guesses = usedNumbers.size();
                toaster(guesses.toString());

                Bundle extras = new Bundle();
                extras.putInt("guesses", guesses);

                Intent rankIntent = new Intent(getApplicationContext(), RankActivity.class);

                rankIntent.putExtras(extras);

                startActivity(rankIntent);

                newGame();
            }
        });
        btnTotals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent totalsIntent = new Intent(getApplicationContext(), TotalsActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("totalWow", totalWow);
                extras.putInt("totalAverage", totalAverage);
                extras.putInt("totalNovice", totalNovice);
                totalsIntent.putExtras(extras);
                startActivity(totalsIntent);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvGuesses.setText("");
                etGuessNumber.setText("");
                etGuessNumber.requestFocus();
            }
        });

        btnRank.setEnabled(false);
    }

    protected void checkGuess (android.view.View v) {
        Integer guessedNum   = 0;
        String  input        = etGuessNumber.getText().toString().trim();
        String  toastMessage = "";
        String  numberStatus = "";

        if (!input.equals("") && isNumeric(input)) { // if it's a valid input
            guessedNum = Integer.parseInt(input); // Convert Editable into Integer value
            numberStatus = guessLogic(guessedNum);
            toastMessage = guessedNum + numberStatus;
        } else {
            toastMessage = NOVAL; // give user an error message

        }
        toaster(toastMessage);
    }

    protected String guessLogic (Integer guessedNum) {
        if (usedNumbers.contains(guessedNum)) {
            return ALREADYGUESSED;
        } else if (guessedNum < 1 || guessedNum > MAXIMUM) {
            return OUTOFRANGE;
        } else if (guessedNum > secretNumber) {
            usedNumbers.add(guessedNum);    // add to list of used (valid) numbers
            return TOOHIGH;
        } else if (guessedNum < secretNumber) {
            usedNumbers.add(guessedNum);
            return TOOLOW;
        } else if (guessedNum.equals(secretNumber)) { // the .equals solves some weird problems for bigger numbers.
            usedNumbers.add(guessedNum);
            onWin();
            return " Was The Correct Number!";
        }
        return "ERROR_LOGIC_ERROR";
    }

    protected void onWin () {
        // force user to check the rank
        tvGuesses.setText("You Guessed the right number " + secretNumber + " in " + usedNumbers.size() + " guesses!");
        btnRank.setEnabled(true);
        btnCalculate.setEnabled(false);
    }

    protected void newGame() {
        getRank();
        btnRank.setEnabled(false);
        btnCalculate.setEnabled(true);
        usedNumbers.clear();
        secretNumber = getSecret();
    }

    protected void getRank () {
        Integer guesses = usedNumbers.size();
        if (guesses >= 1 && guesses <= 5) {
            totalWow++;
        } else if (guesses >= 6 && guesses <= 10) {
            totalAverage++;
        } else if (guesses > 10) {
            totalNovice++;
        }
    }

    protected Integer getSecret () {
        return (int)(Math.random() * MAXIMUM);
    }

    private Boolean isNumeric (String number){
        Boolean numeric = false;
        Integer num;
        try {
            num = Integer.parseInt(number);
            numeric = true;
        } catch (Exception e) {
            numeric = false;
        } finally {
            return numeric;
        }
    }

    // Its a toaster, it pops out toast. What more to explain?
    private void toaster (String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }
}