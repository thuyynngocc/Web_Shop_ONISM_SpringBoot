package com.example.onism.entity;

import com.example.onism.validator.annotation.ValidLoaiId;
import com.example.onism.validator.annotation.ValidUserId;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Generated;

@Data
@Entity
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenSP")
    @NotEmpty(message = "Ten khong duoc de trong")
    @Size(max = 50, min = 1, message = "Ten san pham cao nhat 50 ki tu")
    private String tenSP;


    @Column(name = "giaTien")
    @NotNull(message = "Khong duoc bo trong")
    private Double giaTien;

    @Column(name="soLuong")
    @NotNull(message = "Khong duoc bo trong")
    private Long soLuong;

    @Column(name="hinhAnh")
    @NotEmpty(message = "Ten khong duoc de trong")
    private String hinhAnh;

    @ManyToOne
    @JoinColumn(name = "Loai_id")
    @ValidLoaiId
    private Loai loai;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ValidUserId
    private User user;


    public String getFormattedGiaTien() {
        String formattedPrice = String.format("%,.0f", giaTien); // format giá tiền với dấu phân cách hàng ngàn và không có số thập phân
        return formattedPrice + " VND"; // thêm đơn vị tiền tệ vào cuối chuỗi
    }}
