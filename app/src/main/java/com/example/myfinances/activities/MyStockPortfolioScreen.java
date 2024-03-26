package com.example.myfinances.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.myfinances.R;

public class MyStockPortfolioScreen extends AppCompatActivity {
    private TextView profileLogin;
    private TextView myPortfolioCost, myPortfolioInvest;
    private TextView myPortfolioRisk, myPortfolioRiskDiff;
    private TextView stockTableDefaultStage;
    private LinearLayout stocksTableHead;
    private ScrollView stockTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stock_portfolio);

        setupElements();
    }
    private void setupElements() {
        initFields();

        ImageButton portfolioPage = findViewById(R.id.profile_page);
        portfolioPage.setOnClickListener(view ->
                startActivity(new Intent(this, ProfileSettings.class))
        );

        ImageButton stocksPage = findViewById(R.id.stocks_page);
        stocksPage.setOnClickListener(view -> startActivity(new Intent(this, StocksScreen.class)));

        TextView newMyPortfolioStock = generateNewMyPortfolioStock();
        stockTable.addView(newMyPortfolioStock);
    }
    private void initFields() {
        profileLogin = findViewById(R.id.profile_login);
        myPortfolioCost = findViewById(R.id.my_portfolio_cost);
        myPortfolioInvest = findViewById(R.id.my_portfolio_invest);
        myPortfolioRisk = findViewById(R.id.my_portfolio_risk);
        myPortfolioRiskDiff = findViewById(R.id.my_portfolio_risk_diff);
        stockTable = findViewById(R.id.stock_table);
        stockTableDefaultStage = findViewById(R.id.default_state);
        stocksTableHead = findViewById(R.id.stocks_table);
    }
    private TextView generateNewMyPortfolioStock() {
        TextView newMyPortfolioStock = new TextView(this);
        newMyPortfolioStock.setWidth(stocksTableHead.getWidth());
        newMyPortfolioStock.setHeight(stocksTableHead.getHeight());
        newMyPortfolioStock.setBackgroundColor(getResources().getColor(R.color.stock_element_background));

        return newMyPortfolioStock;
    }
//    private void fillTable(View v, Cursor c) {
//        TableLayout ll = (TableLayout) findViewById(R.id.tableLayoutList);
//
//        View mTableRow = null;
//        int i = 0;
//        while(!c.isAfterLast()){
//            i++;
//            mTableRow = (TableRow) View.inflate(, R.layout.mRowLayout, null);
//
//            CheckBox cb = (CheckBox)mTableRow.findViewById(R.id.checkBoxServEmail);
//            cb.setText( c.getString(c.getColumnIndex(Empleado.EMAIL)));
//
//
//            mTableRow.setTag(i);
//
//            //add TableRows to TableLayout
//            ll.addView(mTableRow);
//
//            c.moveToNext();
//        }
//    }
}