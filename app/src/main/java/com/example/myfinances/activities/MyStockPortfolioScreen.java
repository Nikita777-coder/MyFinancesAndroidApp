package com.example.myfinances.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myfinances.R;
import com.example.myfinances.connectorservices.AuthConnectorService;
import com.example.myfinances.dto.FooUserRisk;
import com.example.myfinances.dto.UserStock;
import com.example.myfinances.stockproviders.IEXCloudStockProvider;
import com.example.myfinances.dto.UserOutData;
import com.example.myfinances.myelements.MyStockTableAdapter;
import com.example.myfinances.stockproviders.StockProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MyStockPortfolioScreen extends AppCompatActivity {
    private TextView profileLogin;
    private TextView myPortfolioCost, myPortfolioInvest;
    private TextView myPortfolioRisk, myPortfolioRiskDiff;
    private ImageButton profilePage;
    private TextView stockTableDefaultStage;
    private StockProvider stockProvider;
    private RecyclerView stockTable;
    private List<String> data = new ArrayList<>();
    private MyStockTableAdapter adapter;
    private UserOutData userOutData;
    private Intent profileIntent;
    private Intent stockPageIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stock_portfolio);

        setupElements();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                userOutData = data.getParcelableExtra("user_data");
                profileLogin.setText(getProfileLogin());
            }
        }
    }
    private void setupElements() {
        initFields();

        profileIntent = new Intent(this, ProfileSettings.class);
        profileIntent.putExtra("user_data", userOutData);
        profilePage.setOnClickListener(view ->
                goToProfile()
        );

        ImageButton stocksPage = findViewById(R.id.stocks_page);
        stockPageIntent = new Intent(this, StocksScreen.class);
        stocksPage.setOnClickListener(view -> clickStockPage());

        stockTable.setLayoutManager(new LinearLayoutManager(this));
        stockTable.setAdapter(adapter);
        stockTable.post(() -> stockTable.scrollToPosition(adapter.getItemCount() - 1));

        profileLogin.setText(getProfileLogin());
//        profilePage.setPhotoProfile(userOutData.getPhotoProfile());
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
        stockProvider = IEXCloudStockProvider.getInstance();
        userOutData = this.getIntent().getParcelableExtra(getResources().getString(R.string.user_data));
        adapter = new MyStockTableAdapter(data);
        profilePage = findViewById(R.id.profile_page);
    }
    private String getProfileLogin() {
        if (userOutData.getLogin() == null) {
            return userOutData.getEmail();
        }

        return userOutData.getLogin();
    }
    private void getStocks() {
        var val = 0.2 + (Math.random() * 1.3 + 0.1);
        var value = BigDecimal.valueOf(val).
                setScale(2, RoundingMode.HALF_UP).intValue();
        boolean getStocks = value == 1;

        if (!getStocks && AuthConnectorService.getUserStocks(userOutData.getEmail()).body().isEmpty()) {
            return;
        }

        stockTableDefaultStage.setVisibility(View.GONE);
        stockTable.setVisibility(View.VISIBLE);
        List<UserStock> userStocks = stockProvider.getUserStocks(userOutData.getEmail());
        setMyPortfolioReviewElements(userStocks);

        for (var userStock : userStocks) {
            var text = userStockToMyPortfolioString(userStock);
            data.add(text);
            adapter.notifyItemInserted(data.size() - 1);
        }
    }
    private String userStockToMyPortfolioString(UserStock userStock) {
        var cNCFColor = getResources().getColor(R.color.white);
        String companyName = userStock.getCompanyName();
        BigDecimal cost = userStock.getCost();
        cost = cost.setScale(2, RoundingMode.HALF_UP);
        Double frac = userStock.getFraction();
        frac = BigDecimal.valueOf(frac)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        boolean condition = userStock.getIncome().compareTo(BigDecimal.ZERO) < 0;
        var iIColor = getResources().getColor(condition ? R.color.error : R.color.active_menu_element);
        @SuppressLint("UseCompatLoadingForDrawables") var arrow =
                getResources().getDrawable(condition ? R.drawable.red_down_arrow : R.drawable.green_up_arrow);
        BigDecimal income = userStock.getIncome();
        income = income.setScale(2, RoundingMode.HALF_UP);
        double diffOfOneStockCost = userStock.getDiffOfOneStockCost();
        diffOfOneStockCost = BigDecimal.valueOf(diffOfOneStockCost)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        var cIColor = getResources().getColor(R.color.some_gray_color_stock);
        int count = userStock.getCount();
        BigDecimal invested = userStock.getInvested();
        invested = invested.setScale(2, RoundingMode.HALF_UP);

        String text = String.format(
                "<font color=%s>%s\t%s</font>" +
                        "\t <font color=%s>%s/font>" +
                        "\t <font color=%s>%s/font>" +
                        "\n <font color=%s>%s\t%s</font>" +
                        "\t <font color=%s>%s/font>",
                cNCFColor, companyName, cost, iIColor, income,
                cNCFColor, frac, cIColor, count, invested,
                iIColor, diffOfOneStockCost
        );

        return text;
    }
    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceAsColor"})
    private void setMyPortfolioReviewElements(List<UserStock> userStocks) {
        BigDecimal cost = getTotalCost(userStocks);
        String text = String.format("%s ₽", cost);
        myPortfolioCost.setText(text);

        BigDecimal invest = getTotalInvest(userStocks);
        text = String.format("вложено %s ₽", invest);
        myPortfolioInvest.setText(text);

        double risk, previousRisk;
        var ans = AuthConnectorService.getUserRisk(userOutData.getEmail());

        if (ans.code() == 400) {
            risk = BigDecimal.valueOf(Math.random() * (0.51) + 0.01).
                    setScale(2, RoundingMode.HALF_UP).doubleValue();
            previousRisk = BigDecimal.valueOf(Math.random() * (0.82) + 0.01).
                    setScale(2, RoundingMode.HALF_UP).doubleValue();

            FooUserRisk fooUserRisk = new FooUserRisk();
            fooUserRisk.setEmail(userOutData.getEmail());
            fooUserRisk.setCurrentRisk(risk);
            fooUserRisk.setPreviousRisk(previousRisk);

            AuthConnectorService.saveUserRisk(fooUserRisk);
        } else {
            risk = ans.body().getCurrentRisk();
            previousRisk = ans.body().getPreviousRisk();
        }

        myPortfolioRisk.setText(Double.toString(risk));
        double diff = BigDecimal.valueOf(risk - previousRisk).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        myPortfolioRiskDiff.setEnabled(true);
        int icon;
        int color;

        if (diff > 0) {
            icon = R.drawable.red_up_arrow;
            color = getResources().getColor(R.color.error);
        } else {
            icon = R.drawable.green_down_arrow;
            color = getResources().getColor(R.color.active_menu_element);
        }

        myPortfolioRiskDiff.setText(Double.toString(Math.abs(diff)));
        myPortfolioRiskDiff.setTextColor(color);
        myPortfolioRiskDiff.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
    }
    private BigDecimal getTotalCost(List<UserStock> userStocks) {
        BigDecimal res = BigDecimal.ZERO;

        for (var userStock : userStocks) {
            BigDecimal cost = userStock.getCost();
            res = res.add(cost);
        }

        return res;
    }
    private BigDecimal getTotalInvest(List<UserStock> userStocks) {
        BigDecimal invested = BigDecimal.ZERO;

        for (var userStock : userStocks) {
            BigDecimal invest = userStock.getInvested();
            invested = invested.add(invest);
        }

        return invested;
    }
    private void goToProfile() {
        profileIntent.putExtra("user_data", userOutData);
        startActivityForResult(profileIntent, 1, new Bundle());
    }
    private void clickStockPage() {
        stockPageIntent.putExtra("user_data", userOutData);
        startActivity(stockPageIntent);
    }
}