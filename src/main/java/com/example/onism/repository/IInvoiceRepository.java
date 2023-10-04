package com.example.onism.repository;


import com.example.onism.entity.Invoice;

import com.example.onism.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Long>{
    @Query("""
                SELECT b FROM SanPham b
                WHERE b.tenSP LIKE %?1%
                OR b.loai.tenLoai LIKE %?1%
                """)
    List<SanPham> searchBook(String keyword);
    @Query("SELECT b FROM SanPham b WHERE b.tenSP LIKE %?1%")
    SanPham findSPByName(String tenSP);
}

