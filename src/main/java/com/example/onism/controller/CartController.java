package com.example.onism.controller;

import com.example.onism.entity.Cart;
import com.example.onism.entity.Item;
import com.example.onism.entity.OrderViewModel;
import com.example.onism.services.CartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private JavaMailSender javaMailSender;

    @Autowired
    public CartController(CartService cartService, JavaMailSender javaMailSender) {
        this.cartService = cartService;
        this.javaMailSender = javaMailSender;
    }
    @GetMapping
    public String showCart(HttpSession session, Model model) {
        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("totalPrice", cartService.getFormattedGiaTien(cartService.getSumPrice(session)));
        model.addAttribute("totalQuantity", cartService.getSumQuantity(session));
        return "sanpham/cart";
    }
    @GetMapping("/removeFromCart/{id}")
    public String removeFromCart(HttpSession session,
                                 @PathVariable Long id) {
        var cart = cartService.getCart(session);
        cart.removeItems(id);
        return "redirect:/cart";
    }
    @GetMapping("/updateCart/{id}/{quantity}")
    public String updateCart(HttpSession session,
                             @PathVariable Long id,
                             @PathVariable int quantity) {
        var cart = cartService.getCart(session);
        cart.updateItems(id, quantity);
        return "sanpham/cart";
    }
    @GetMapping("/clearCart")
    public String clearCart(HttpSession session) {
        cartService.removeCart(session);
        return "redirect:/cart ";
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, @ModelAttribute("ord") OrderViewModel ord) {
        session.setAttribute("ord", ord);
        String to = "tranminhtrung1306@gmail.com"; // Địa chỉ email của mình
        String subject = "Đơn hàng mới";
        String body = ord.getEmail()+"\n"
                +ord.getCustomerName()+"\n"
                +ord.getAddress()+"\n"
                +ord.getPhone()+"\n";
        Cart cart = cartService.getCart(session);
        List<Item> cartItems = cart.getCartItems();
        DecimalFormat decimalFormat1 = new DecimalFormat("#,### VND");
        for (Item cartItem : cartItems) {
            String formattedTotalAmount1 = decimalFormat1.format(cartItem.getQuantity() * cartItem.getGiaTien());
            body += "Tên Sản Phẩm: " + cartItem.getTenSP()  + "\n"+ "Số Lượng : "+ cartItem.getQuantity() + "\n" +"Thành Tiền : " + formattedTotalAmount1 ;
            body += "\n----------------";
            body += "\n";
        }
        body += "Tổng tiền: " + decimalFormat1.format(cartService.getSumPrice(session)) +"\n";
        sendEmail(to, subject, body);
        cartService.saveCart(session);
        return "redirect:/cart";
    }
    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
}