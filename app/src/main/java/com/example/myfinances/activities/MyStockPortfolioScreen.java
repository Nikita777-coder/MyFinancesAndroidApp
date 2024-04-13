package com.example.myfinances.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myfinances.R;
import com.example.myfinances.dto.UserStock;
import com.example.myfinances.stockproviders.IEXCloudStockProvider;
import com.example.myfinances.dto.UserOutData;
import com.example.myfinances.myelements.MyStockTableAdapter;
import com.example.myfinances.stockproviders.StockProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import forremove.MyStockMockData;

public class MyStockPortfolioScreen extends AppCompatActivity {
    private TextView profileLogin;
    private TextView myPortfolioCost, myPortfolioInvest;
    private TextView myPortfolioRisk, myPortfolioRiskDiff;
    private TextView stockTableDefaultStage;
    private LinearLayout stocksTableHead;
    private StockProvider stockProvider;
    private RecyclerView stockTable;
    private List<String> data = new ArrayList<>();
    private MyStockTableAdapter adapter;
    private UserOutData userOutData;
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

        MyStockTableAdapter adapter = new MyStockTableAdapter(data);
        stockTable.setAdapter(adapter);
        stockTable.post(() -> stockTable.scrollToPosition(adapter.getItemCount() - 1));

        profileLogin.setText(getProfileLogin());
        getStocks();
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
        stockProvider = IEXCloudStockProvider.getInstance();
        userOutData = this.getIntent().getParcelableExtra(getResources().getString(R.string.user_data));
    }
    private String getProfileLogin() {
        if (userOutData.getLogin() == null) {
            return userOutData.getEmail();
        }

        return userOutData.getLogin();
    }
    private void getStocks() {
        boolean getStocks = new Random().nextBoolean();

        if (!getStocks) {
            return;
        }

        List<UserStock> userStocks = stockProvider.getUserStocks(userOutData.getEmail());
        for (var userStock : userStocks) {
            data.add(userStockToMyPortfolioString(userStock));
            adapter.notifyItemInserted(data.size() - 1);
        }
    }
    private String userStockToMyPortfolioString(UserStock userStock) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        EditText userStringStock = new EditText(this);
        userStringStock.
        return userStringStock.getText().toString();
    }
}