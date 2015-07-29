package ua.in.petybay.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by slavik on 05.04.15.
 */
@Data
public class Location {
    @NotNull
    private Region region;

    @NotNull
    private City city;

    private String detailAddress;

}
