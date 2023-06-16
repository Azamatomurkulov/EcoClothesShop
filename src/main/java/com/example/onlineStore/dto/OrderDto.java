package com.example.onlineStore.dto;

import com.example.onlineStore.entity.Payment;
import com.example.onlineStore.entity.Product;
import com.example.onlineStore.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    private User user;

    private List<Product> products;
    private String address;

    private Payment payment;
    private Double sum;

    private LocalDate orderTime;

    private LocalDate rdt;

}
