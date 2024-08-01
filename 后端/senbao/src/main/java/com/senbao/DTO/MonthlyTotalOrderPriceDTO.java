package com.senbao.DTO;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyTotalOrderPriceDTO {
    private List<String> months;
    private List<String> monthlyOrderTotals;
}
