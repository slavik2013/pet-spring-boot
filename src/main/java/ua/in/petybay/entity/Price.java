package ua.in.petybay.entity;

import javax.validation.constraints.Max;

/**
 * Created by slavik on 05.04.15.
 */

public class Price {

    @Max(1000000000)
    int price;
    String free;
    String currency;
    String contractPrice;
    String change;

    public Price() {
    }

    public Price(int price, String free, String currency, String contractPrice, String change) {
        this.price = price;
        this.free = free;
        this.currency = currency;
        this.contractPrice = contractPrice;
        this.change = change;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(String contractPrice) {
        this.contractPrice = contractPrice;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    @Override
    public String toString() {
        return "Price{" +
                "price=" + price +
                ", free='" + free + '\'' +
                ", currency='" + currency + '\'' +
                ", contractPrice='" + contractPrice + '\'' +
                ", change='" + change + '\'' +
                '}';
    }
}
