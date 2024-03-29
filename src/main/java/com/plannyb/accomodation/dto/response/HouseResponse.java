package com.plannyb.accomodation.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class HouseResponse {

    private String street;
    private Integer rooms;
    private Integer bedrooms;
    private Integer bathrooms;
    private String shortAddress;
    private byte[] image;
    private List<ImageRes> images;
    private LocationRes location;
    private CategoryRes category;
    private Double price;
    private Double rent;
    private String description;
    private String neighbourhood;
    private FacilitiesRes facilities;
    private List<ReservationRes> reservations;
}
