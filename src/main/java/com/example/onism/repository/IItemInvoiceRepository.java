package com.example.onism.repository;



import com.example.onism.entity.ItemInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IItemInvoiceRepository extends JpaRepository<ItemInvoice, Long>{
}

