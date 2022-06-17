package perczynski.kamil.evolution.gameservice.libs;

import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Value
public class Money {

    public static Money ZERO = new Money(0);

    private static final NumberFormat EUR_CURRENCY_FORMAT;

    static {
        final NumberFormat currencyFormat = DecimalFormat.getCurrencyInstance(Locale.US);
        currencyFormat.setCurrency(Currency.getInstance("EUR"));
        EUR_CURRENCY_FORMAT = currencyFormat;
    }

    int amount;

    public Money plus(Money money) {
        if (money.getAmount() == 0) {
            return this;
        }
        return new Money(amount + money.getAmount());
    }

    public Money minus(Money money) {
        if (money.getAmount() == 0) {
            return this;
        }

        return new Money(amount - money.getAmount());
    }

    public Money multiply(int multiplier) {
        return new Money(amount * multiplier);
    }

    public boolean isGreater(Money money) {
        return amount > money.getAmount();
    }

    public String getFormatted() {
        final BigDecimal divide = BigDecimal.valueOf(amount)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.UNNECESSARY);
        return EUR_CURRENCY_FORMAT.format(divide);
    }

}
