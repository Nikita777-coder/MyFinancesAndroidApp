package com.example.myfinances.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myfinances.R;
import com.example.myfinances.connectorservices.AuthConnectorService;
import com.example.myfinances.dto.FooUserRisk;
import com.example.myfinances.dto.MarketStock;
import com.example.myfinances.dto.UserOutData;
import com.example.myfinances.dto.UserStock;
import com.example.myfinances.myelements.MyStockTableAdapter;
import com.example.myfinances.stockproviders.IEXCloudStockProvider;
import com.example.myfinances.stockproviders.StockProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class StocksScreen extends AppCompatActivity {
    private TextView profileLogin;
    private ImageButton profilePage;
    private StockProvider stockProvider;
    private RecyclerView stockTable;
    private List<String> data = new ArrayList<>();
    private MyStockTableAdapter adapter;
    private UserOutData userOutData;
    private Intent profileIntent;
    private Intent myPortfolioPageIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks_screen);

        setupElements();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
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

        ImageButton myPortfolio = findViewById(R.id.my_portfolio);
        myPortfolioPageIntent = new Intent(this, MyStockPortfolioScreen.class);
        myPortfolio.setOnClickListener(view -> startMyPortfolio());

        stockTable.setLayoutManager(new LinearLayoutManager(this));
        stockTable.setAdapter(adapter);
        stockTable.post(() -> stockTable.scrollToPosition(adapter.getItemCount() - 1));

        profileLogin.setText(getProfileLogin());
//        profilePage.setPhotoProfile(userOutData.getPhotoProfile());
        getStocks();
    }
    private void initFields() {
        profileLogin = findViewById(R.id.profile_login);
        stockTable = findViewById(R.id.stock_table);
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
        stockTable.setVisibility(View.VISIBLE);
        List<MarketStock> marketStocks = stockProvider.getStocks(userOutData.getEmail());

        for (var marketStock : marketStocks) {
            var text = userStockToMyPortfolioString(marketStock);
            data.add(text);
            adapter.notifyItemInserted(data.size() - 1);
        }
    }
    private String userStockToMyPortfolioString(MarketStock marketStock) {
        var cNCFColor = getResources().getColor(R.color.white);
        String companyName = marketStock.getCompanyName();
        double cost = scaleDoubleTo2Points(marketStock.getLatestPrice());
        double totalCost = scaleDoubleTo2Points(marketStock.getTotalCost());

        boolean condition = marketStock.getGrowMoney() < 0;
        var iIColor = getResources().getColor(condition ? R.color.error : R.color.active_menu_element);
        @SuppressLint("UseCompatLoadingForDrawables") var arrow =
                getResources().getDrawable(condition ? R.drawable.red_down_arrow : R.drawable.green_up_arrow);
        double growMoney = marketStock.getGrowMoney();
        growMoney = scaleDoubleTo2Points(Math.abs(growMoney));

        double growPercentage = scaleDoubleTo2Points(marketStock.getGrowPercentage());

        var cIColor = getResources().getColor(R.color.some_gray_color_stock);
        int count = marketStock.getCount();

        return String.format(
                "<font color=%s>%s\t%s\t%s</font>" +
                        "\t<font color=%s>%s/font>" +
                        "\t<font color=%s>%s/font>" +
                        "\t<font color=%s>%s</font>",
                cNCFColor, companyName, cost, totalCost, iIColor,
                growMoney, cIColor, count, iIColor, growPercentage
        );
    }
    private void goToProfile() {
        profileIntent.putExtra("user_data", userOutData);
        startActivityForResult(profileIntent, 2, new Bundle());
    }
    private Double scaleDoubleTo2Points(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
    private void startMyPortfolio() {
        myPortfolioPageIntent.putExtra("user_data", userOutData);
        startActivity(myPortfolioPageIntent);
    }
}