package com.plannyb.accomodation.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class HouseRequest {

    private Long id;
    private String street;
    private Integer rooms;
    private Integer bedrooms;
    private Integer bathrooms;
    private String shortAddress;
    private byte[] image;
    private List<ImageReq> images;
    private LocationReq location;
    private CategoryReq category;
    private Double price;
    private Double rent;
    private String description;
    private String neighbourhood;

}
