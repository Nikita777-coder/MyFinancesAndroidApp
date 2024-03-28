package com.example.myfinances.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.myfinances.myelements.MyStockTableAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyStockPortfolioScreen extends AppCompatActivity {
    private TextView profileLogin;
    private TextView myPortfolioCost, myPortfolioInvest;
    private TextView myPortfolioRisk, myPortfolioRiskDiff;
    private TextView stockTableDefaultStage;
    private LinearLayout stocksTableHead;
    private RecyclerView stockTable;
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

        stockTable.setLayoutManager(new LinearLayoutManager(this));
        List<String> data = new ArrayList<>();

        MyStockTableAdapter adapter = new MyStockTableAdapter(data);
        stockTable.setAdapter(adapter);
        stockTable.post(() -> stockTable.scrollToPosition(adapter.getItemCount() - 1));


//        for (int i = 0; i < 20; ++i) {
//            data.add("Новый элемент");
//            adapter.notifyItemInserted(data.size() - 1);
//        }
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
}