package com.example.ex31_contextmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class result_activity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, View.OnCreateContextMenuListener {
    int pos;
    String action;
    ListView lVFirstTwenty;
    double firstTerm1, difference, multiplier, result;
    boolean type;
    TextView resultTv;
    String[] terms = new String[20];
    Intent intentBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultTv = findViewById(R.id.result);
        lVFirstTwenty = findViewById(R.id.listV);
        intentBack = getIntent();

        Intent intent = getIntent();
        type = intent.getBooleanExtra("type", false);
        firstTerm1 = intent.getDoubleExtra("firstTerm", 0);

        if (type) {
            difference = intent.getDoubleExtra("difference", 0);
            generateArithmeticSeries();
        } else {
            multiplier = intent.getDoubleExtra("multiplier", 0);
            generateGeometricSeries();
        }
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,terms);
        lVFirstTwenty.setAdapter(adp);
        lVFirstTwenty.setOnItemLongClickListener(this);
        registerForContextMenu(lVFirstTwenty);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
        return false;
    }

    public static String differentView(double term) {
        if (term % 1 == 0 && term < 10000 && term > -10000) {
            return String.valueOf((int) term);
        }

        if (term >= 10000 || term <= -10000) {
            int exponent = 0;
            double coefficient = term;

            while (Math.abs(coefficient) >= 10000) {
                coefficient /= 10;
                exponent++;
            }
            return String.format("%d * 10^%d", (int) coefficient, exponent);
        }

        int exponent = 0;
        double coefficient = term;

        if (Math.abs(term) >= 1) {
            while (Math.abs(coefficient) >= 10) {
                coefficient /= 10;
                exponent++;
            }
        } else {
            while (Math.abs(coefficient) < 1) {
                coefficient *= 10;
                exponent--;
            }
        }
        return String.format("%.3f * 10^%d", coefficient, exponent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Series Operations");
        menu.add("sum");
        menu.add("location");
    }

    public void generateArithmeticSeries() {
        double term;
        for (int i = 0; i < 20; i++) {
            term = firstTerm1 + i * difference;
            terms[i] = differentView(term);
        }
    }

    public void generateGeometricSeries() {
        double term;
        for (int i = 0; i < 20; i++) {
            term = firstTerm1 * Math.pow(multiplier, i);
            terms[i] = differentView(term);
        }
    }

    public double sumArithmetic(int n) {
        return n * (2 * firstTerm1 + (n - 1) * difference) / 2;
    }

    public double sumGeometric(int n) {
        return firstTerm1 * (Math.pow(multiplier, n) - 1) / (multiplier - 1);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        action = item.getTitle().toString();
        if (action.equals("sum")) {
            if (type) {
                resultTv.setText(differentView(sumArithmetic(pos + 1)));
            } else {
                resultTv.setText(differentView(sumGeometric(pos + 1)));
            }
        } else if (action.equals("location")) {
            resultTv.setText(String.valueOf(pos + 1));
        }
        return true;
    }

    public void back(View view) {
        setResult(RESULT_OK, intentBack);
        finish();
    }
}