package com.example.paymentservice.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    List<T> content;
    long totalElements;
    int totalPages;
    int page;
    int size;
}
