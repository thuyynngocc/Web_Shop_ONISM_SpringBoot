package com.example.onism.services;

import com.example.onism.entity.Cart;
import com.example.onism.entity.Invoice;
import com.example.onism.entity.Item;
import com.example.onism.entity.ItemInvoice;
import com.example.onism.repository.IInvoiceRepository;
import com.example.onism.repository.IItemInvoiceRepository;
import com.example.onism.repository.ISanPhamRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE,
        rollbackFor = {Exception.class, Throwable.class})
public class CartService {

    private static final String CART_SESSION_KEY = "cart";
    private final IInvoiceRepository invoiceRepository;

    private final IItemInvoiceRepository itemInvoiceRepository;

    private final ISanPhamRepository sanPhamRepository;
    public Cart getCart(@NotNull HttpSession session) {
        return Optional.ofNullable((Cart)
                        session.getAttribute(CART_SESSION_KEY))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    session.setAttribute(CART_SESSION_KEY, cart);
                    return cart;
                });
    }
    public void updateCart(@NotNull HttpSession session, Cart cart) {
        session.setAttribute(CART_SESSION_KEY, cart);
    }
    public void removeCart(@NotNull HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }
    public int getSumQuantity(@NotNull HttpSession session) {
        return getCart(session).getCartItems().stream()
                .mapToInt(Item::getQuantity)
                .sum();
    }
    public double getSumPrice(@NotNull HttpSession session) {
        return getCart(session).getCartItems().stream()
                .mapToDouble(item -> item.getGiaTien() *
                        item.getQuantity())
                .sum();
    }

    public void saveCart(@NotNull HttpSession session) {
        var cart = getCart(session);
        if (cart.getCartItems().isEmpty()) return;
        var invoice = new Invoice();
        invoice.setInvoiceDate(new Date(new Date().getTime()));
        invoice.setGiaTien(getSumPrice(session));
        invoiceRepository.save(invoice);
        cart.getCartItems().forEach(item -> {
            var items = new ItemInvoice();
            items.setInvoice(invoice);
            items.setQuantity(item.getQuantity());
            items.setSanPham(sanPhamRepository.findById(item.getId())
                    .orElseThrow());
            itemInvoiceRepository.save(items);
        });
        removeCart(session);
    }
    public String getFormattedGiaTien(double giaTien) {
        String formattedPrice = String.format("%,.0f", giaTien); // format giá tiền với dấu phân cách hàng ngàn và không có số thập phân
        return formattedPrice + " VND"; // thêm đơn vị tiền tệ vào cuối chuỗi
    }
}

