package ua.in.petybay.entity;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by slavik on 05.04.15.
 */
@Data
public class Price {

    @Min(0) @Max(1000000000)
    private int price;
    private String free;
    private String currency;
    private String contractPrice;
    private String change;

}
