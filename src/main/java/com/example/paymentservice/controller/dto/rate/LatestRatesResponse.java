package com.example.paymentservice.controller.dto.rate;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class LatestRatesResponse implements Serializable {
    String base;
    String date;
    Map<String, Double> rates;
}
