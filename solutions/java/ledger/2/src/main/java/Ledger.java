import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Ledger {

    public LedgerEntry createLedgerEntry(String date, String description, int change) {
        LedgerEntry le = new LedgerEntry();
        le.setChange(change);
        le.setDescription(description);
        le.setLocalDate(LocalDate.parse(date));
        return le;
    }

    public String format(String currency, String location, LedgerEntry[] ledgerEntries) {
        validateArguments(currency, location);

        Locale locale = Locale.forLanguageTag(location);
        DecimalFormat currencyFormat = createCurrencyFormatter(locale, currency);
        FormatConfig formatConfig = getFormatConfig(location);

        if (ledgerEntries.length == 0) {
            return formatConfig.header;
        }

        List<LedgerEntry> allLedgerEntries = getAllLedgerEntries(ledgerEntries);

        StringBuilder result = new StringBuilder(formatConfig.header);
        for (LedgerEntry ledgerEntry : allLedgerEntries) {
            result.append(formattedEntry(ledgerEntry, formatConfig, currencyFormat));
        }

        return result.toString();
    }

    private static void validateArguments(String currency, String location) {
        if (!(currency.equals("USD") || currency.equals("EUR"))) {
            throw new IllegalArgumentException("Invalid currency");
        }

        if (!(location.equals("en-US") || location.equals("nl-NL"))) {
            throw new IllegalArgumentException("Invalid locale");
        }
    }

    private static List<LedgerEntry> getAllLedgerEntries(LedgerEntry[] ledgerEntries) {
        return Arrays.stream(ledgerEntries)
                .sorted(Comparator
                        .comparing((LedgerEntry e) -> e.getChange() >= 0) // negatives first
                        .thenComparing(LedgerEntry::getLocalDate))
                .toList();
    }

    private static DecimalFormat createCurrencyFormatter(Locale locale, String currencyCode) {
        DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
        format.setCurrency(Currency.getInstance(currencyCode));

        boolean isUS = locale.getCountry().equals("US");
        boolean isUSD = currencyCode.equals("USD");

        String pattern;
        if (isUS && isUSD) {
            pattern = "$#,##0.00 ;($#,##0.00)";
        } else if (isUS) {
            pattern = "€ #,##0.00 ;(€#,##0.00)";
        } else if (isUSD) {
            pattern = "$ #,##0.00 ;$ -#,##0.00 ";
        } else {
            pattern = "€ #,##0.00 ;€#,##0.00";
        }

        format.applyPattern(pattern);
        return format;
    }

    private static String formattedEntry(LedgerEntry ledgerEntry, FormatConfig formatConfig, DecimalFormat currencyFormat) {
        String formattedDescription = formatDescription(ledgerEntry.getDescription());
        String formattedDate = ledgerEntry.getLocalDate().format(formatConfig.dateFormatter);
        String formattedAmount = formatAmount(ledgerEntry.getChange(), currencyFormat);

        return String.format("\n%s | %-25s | %13s",
                formattedDate,
                formattedDescription,
                formattedAmount);
    }

    private static String formatDescription(String description) {
        if (description.length() <= 25) {
            return description;
        }

        return description.substring(0, 22) + "...";
    }

    private static String formatAmount(double amount, DecimalFormat decimalFormat) {
        return decimalFormat.format(amount / 100)
                .replace('\u00A0', ' ');
    }

    private static FormatConfig getFormatConfig(String location) {
        return switch (location) {
            case "en-US" -> new FormatConfig(
                    "Date       | Description               | Change       ",
                    "MM/dd/yyyy"
            );
            case "nl-NL" -> new FormatConfig(
                    "Datum      | Omschrijving              | Verandering  ",
                    "dd/MM/yyyy"
            );
            default -> throw new IllegalArgumentException("Invalid locale");
        };
    }

    public static class LedgerEntry {
        LocalDate localDate;
        String description;
        double change;

        public LocalDate getLocalDate() {
            return localDate;
        }

        public void setLocalDate(LocalDate localDate) {
            this.localDate = localDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getChange() {
            return change;
        }

        public void setChange(double change) {
            this.change = change;
        }
    }

    private record FormatConfig(String header, DateTimeFormatter dateFormatter) {

        FormatConfig(String header, String pattern) {
            this(header, DateTimeFormatter.ofPattern(pattern));
        }
    }
}
