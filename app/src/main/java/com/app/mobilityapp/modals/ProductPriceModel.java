package com.app.mobilityapp.modals;

import java.io.Serializable;

public class ProductPriceModel implements Serializable {
    String from,to,amount;

    public ProductPriceModel(String from, String to, String amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
