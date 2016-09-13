package ua.in.petybay.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by slavik on 05.04.15.
 */
public class Price {

    @Min(0) @Max(1000000000)
    private int price;
    private String free;
    private String currency;
    private String contractPrice;
    private String change;

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
}
