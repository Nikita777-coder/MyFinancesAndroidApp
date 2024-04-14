package com.example.myfinances.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myfinances.R;
import com.example.myfinances.connectorservices.AuthConnectorService;
import com.example.myfinances.dto.UserStock;
import com.example.myfinances.stockproviders.IEXCloudStockProvider;
import com.example.myfinances.dto.UserOutData;
import com.example.myfinances.myelements.MyStockTableAdapter;
import com.example.myfinances.stockproviders.StockProvider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
        boolean getStocks = true;//ThreadLocalRandom.current().nextBoolean();

        if (!getStocks && AuthConnectorService.getUserStocks(userOutData.getEmail()).body().isEmpty()) {
            return;
        }

        List<UserStock> userStocks = stockProvider.getUserStocks(userOutData.getEmail());
        for (var userStock : userStocks) {
            data.add(userStockToMyPortfolioString(userStock));
            adapter.notifyItemInserted(data.size() - 1);
        }
    }
    private String userStockToMyPortfolioString(UserStock userStock) {
        var cNCFColor = getResources().getColor(R.color.white);
        String companyName = userStock.getCompanyName();
        BigDecimal cost = userStock.getCost();
        Double frac = userStock.getFraction();

        boolean condition = userStock.getIncome().compareTo(BigDecimal.ZERO) < 0;
        var iIColor = getResources().getColor(condition ? R.color.error : R.color.active_menu_element);
        @SuppressLint("UseCompatLoadingForDrawables") var arrow =
                getResources().getDrawable(condition ? R.drawable.red_down_arrow : R.drawable.green_up_arrow);
        BigDecimal income = userStock.getIncome();
        double diffOfOneStockCost = userStock.getDiffOfOneStockCost();

        var cIColor = getResources().getColor(R.color.notActiveAuthPage);
        int count = userStock.getCount();
        BigDecimal invested = userStock.getInvested();

        TextView textView = new TextView(this);
        String text = String.format(
                "<font color=%s>%s\t%s</font>\t<font color=%s>%s</font> " +
                "<font color=%s>%s</font>\n<font color=%s>%s\t%s</font>\t<font color=%s>%s</font>",
                cNCFColor, companyName, cost, iIColor, income, cNCFColor, frac, cIColor, count, invested,
                iIColor, diffOfOneStockCost
        );
        textView.setText(Html.fromHtml(text));

        return textView.getText().toString();
    }
}