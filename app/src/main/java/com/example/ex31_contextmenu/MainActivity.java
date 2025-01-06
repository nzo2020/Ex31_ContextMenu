package com.example.ex31_contextmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    boolean type;
    EditText firstTermEt, ratioOrDifference;
    Switch swt;
    Intent intentSecondActivity;
    static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstTermEt = findViewById(R.id.firstTerm);
        swt = findViewById(R.id.switch2);
        ratioOrDifference = findViewById(R.id.ratioOrDifference);
        intentSecondActivity = new Intent(this, result_activity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            firstTermEt.setText("");
            ratioOrDifference.setText("");
            swt.setChecked(false);
        }
    }

    public boolean check(String st) {
        return st.equals("+") || st.equals("+.") || st.equals("-") || st.equals("-.") || st.equals(".") || st.isEmpty();
    }

    public void next(View view) {
        String firstTermSt = firstTermEt.getText().toString();
        String ratioOrDiffSt = ratioOrDifference.getText().toString();

        if (check(firstTermSt) || check(ratioOrDiffSt)) {
            Toast.makeText(this, "Please enter again, There is invalid input here", Toast.LENGTH_SHORT).show();
            return;
        }

        double firstTerm = Double.parseDouble(firstTermSt);
        intentSecondActivity.putExtra("firstTerm", firstTerm);

        if (swt.isChecked()) {
            type = true;
            double difference = Double.parseDouble(ratioOrDiffSt);
            intentSecondActivity.putExtra("difference", difference);
        } else {
            type = false;
            double multiplier = Double.parseDouble(ratioOrDiffSt);
            intentSecondActivity.putExtra("multiplier", multiplier);
        }

        intentSecondActivity.putExtra("type", type);
        startActivityForResult(intentSecondActivity, REQUEST_CODE);
    }
}