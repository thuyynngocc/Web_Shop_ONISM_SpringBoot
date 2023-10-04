package com.example.onism.controller;

import com.example.onism.entity.Invoice;
import com.example.onism.entity.SanPham;
import com.example.onism.services.InvoiceService;
import com.example.onism.services.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/invoices")
@Controller
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/view")
    public String getAllInvoices(Model model){
        List<Invoice> invoice = invoiceService.getAllInvoices();
        model.addAttribute("invoice", invoice);
        return "donhang/view";
    }


}