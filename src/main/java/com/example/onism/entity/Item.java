package com.example.onism.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Long id;
    private String tenSP;
    private Double giaTien;
    private int quantity;
    public String getFormattedTongTien1() {
        String formattedPrice = String.format("%,.0f", giaTien  * quantity); // format giá tiền với dấu phân cách hàng ngàn và không có số thập phân
        return formattedPrice + " VND"; // thêm đơn vị tiền tệ vào cuối chuỗi
    }
    public String getFormattedGiaTien() {
        String formattedPrice = String.format("%,.0f", giaTien); // format giá tiền với dấu phân cách hàng ngàn và không có số thập phân
        return formattedPrice + " VND"; // thêm đơn vị tiền tệ vào cuối chuỗi
    }
}
