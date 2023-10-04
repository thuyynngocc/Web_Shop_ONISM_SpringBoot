package com.example.onism.services;

import com.example.onism.entity.Invoice;
import com.example.onism.entity.SanPham;
import com.example.onism.repository.IInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private IInvoiceRepository invoiceRepository;
    public List<Invoice> getAllInvoices() {return invoiceRepository.findAll();}







}