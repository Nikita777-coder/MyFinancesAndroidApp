package forremove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MyStockMockData {
    private final Random random = new Random();
    private final List<String> companies = Arrays.asList("AT&T",
            "AWB Limited",
            "AXA",
            "Aalberts Industries",
            "American Airlines",
            "Aavin",
            "Abbott Laboratories",
            "AbeBooks",
            "Abercrombie & Fitch",
            "Bayer",
            "Bazooka",
            "Bean Media Group",
            "Beanie Babies",
            "Beano",
            "Beaurepaires",
            "Beaver Buzz",
            "Bebe",
            "Becker Entertainment",
            "Beck's",
            "Bed Head",
            "Bed, Bath & Beyond",
            "Beeline",
            "Granini",
            "Grape Nuts",
            "Gree",
            "Green Giant",
            "Green Island Cement",
            "Greenpeace",
            "Gregg's",
            "Grey Goose",
            "Greyhound",
            "Griffin's",
            "Grocon",
            "Group Sense PDA",
            "Grupo Folha",
            "Grupo Schincariol",
            "Gruppo Generali",
            "Guararapes",
            "Gucci",
            "Guerlain",
            "Guess?",
            "Gueuze",
            "Guinness",
            "Gunns",
            "Guylian",
            "H&M",
            "H&R Block",
            "H-E-B",
            "H2",
            "HBO",
            "HDFC Bank",
            "HIH Insurance",
            "ПАО Сбербанк",
            "Тинькофф Банк",
            "Alfa Bank"
    );
    private final List<StockMockData> stockData = generateStockData();
    private List<StockMockData> generateStockData() {
        for (int i = 0; i < 1000; ++i) {
            String title = companies.get(random.nextInt(companies.size()));
        }

        return new ArrayList<>();
    }
}
