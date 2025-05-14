package org.ibenrm01.ecoSyX.api;

import org.ibenrm01.ecoSyX.YAMLconfig.Money;
import org.ibenrm01.ecoSyX.YAMLconfig.settings;

public class EcoSystem {

    private final static EcoSystem instance = new EcoSystem();

    private EcoSystem() {
    }

    public boolean newUser(String ids) {
        return Money.getInstance().getConfig().contains("player." + ids);
    }

    public String[] createData(String ids) {
        int firstMoney = settings.getInstance().getConfig().getInt("moneyLogin");
        if (!newUser(ids)) {
            Money.getInstance().getConfig().set("player." + ids, firstMoney);
            Money.getInstance().save();
            return new String[]{"success"};
        }
        return new String[]{"exists"};
    }

    public String[] myMoney(String ids) {
        if (!newUser(ids)) {
            this.createData(ids);
        }
        int money = Money.getInstance().getConfig().getInt("player." + ids);
        Money.getInstance().save();
        return new String[]{"success", String.valueOf(money)};
    }

    public String[] getMoney(String ids) {
        if (!newUser(ids)) {
            this.createData(ids);
        }
        int money = Money.getInstance().getConfig().getInt("player." + ids);
        Money.getInstance().save();
        return new String[]{"success", String.valueOf(money)};
    }

    public String[] setMoney(String ids, int amount) {
        if (!newUser(ids)) {
            this.createData(ids);
        }
        Money.getInstance().getConfig().set("player." + ids, amount);
        Money.getInstance().save();
        return new String[]{"success", String.valueOf(amount)};
    }

    public String[] giveMoney(String ids, int amount) {
        if (!newUser(ids)) {
            this.createData(ids);
        }
        int current = Money.getInstance().getConfig().getInt("player." + ids);
        Money.getInstance().getConfig().set("player." + ids, current + amount);
        Money.getInstance().save();
        return new String[]{"success", String.valueOf(current + amount)};
    }

    public String[] reduceMoney(String ids, int amount) {
        if (!newUser(ids)) {
            this.createData(ids);
        }
        int current = Money.getInstance().getConfig().getInt("player." + ids);
        Money.getInstance().getConfig().set("player." + ids, current - amount);
        Money.getInstance().save();
        return new String[]{"success", String.valueOf(current - amount)};
    }

    public String[] payMoney(String buyId, String sellId, Integer amount) {
        if (buyId.equals(sellId)) {
            return new String[]{"samepeople", amount.toString()};
        }
        if (newUser(buyId)) {
            if (newUser(sellId)) {
                if (Money.getInstance().getConfig().getInt("player." + buyId) >= amount) {
                    Integer addAmount = Money.getInstance().getConfig().getInt("player." + sellId) + amount;
                    Integer reduceAmount = Money.getInstance().getConfig().getInt("player." + buyId) - amount;

                    Money.getInstance().getConfig().set("player." + sellId, addAmount);
                    Money.getInstance().getConfig().set("player." + buyId, reduceAmount);
                    Money.getInstance().save();

                    return new String[]{"success", reduceAmount.toString()};
                } else {
                    return new String[]{"notenough"};
                }
            } else {
                this.createData(sellId);
                return new String[]{"notexists", settings.getInstance().getConfig().getString("moneyLogin").toString()};
            }
        } else {
            this.createData(buyId);
            return new String[]{"notexists", settings.getInstance().getConfig().getString("moneyLogin").toString()};
        }
        // Ini bisa diganti juga karena tidak akan sampai sini jika return di atas
        // return new String[]{"error", "Call Developer"};
    }

    public String typeMoney() {
        return new String(settings.getInstance().getConfig().getString("type-money").toString());
    }

    public static EcoSystem getInstance() {
        return instance;
    }
}
