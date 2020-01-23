package com.app.mobilityapp.modals;

public class SingleItem {
    int qty;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public SingleItem(int qty) {
        this.qty = qty;
    }

    public SingleItem() {
    }

    @Override
    public String toString() {
        return "SingleItem{" +
                "qty=" + qty +
                '}';
    }
}
