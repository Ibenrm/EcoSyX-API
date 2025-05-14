package org.ibenrm01.ecoSyX;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class Utility {

    private final static Utility instance = new Utility();

    private Utility() {
    }

    public static String replace(String message, Map<String, String> keys) {
        for (Map.Entry<String, String> entry : keys.entrySet()) {
            message = message.replace("{" + entry.getKey().toLowerCase() + "}", entry.getValue());
        }
        return message;
    }

    public String formatToRupiah(Integer amount) {
        NumberFormat format = NumberFormat.getInstance(new Locale("id", "ID"));
        return format.format(amount);
    }

    public static Utility getInstance() {
        return instance;
    }

}
