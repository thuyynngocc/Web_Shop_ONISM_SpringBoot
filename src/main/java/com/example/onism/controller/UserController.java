package com.example.onism.controller;


import com.example.onism.entity.Role;
import com.example.onism.entity.SanPham;
import com.example.onism.services.UserService;
import jakarta.validation.Valid;
import com.example.onism.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(error -> model.addAttribute(error.getField() + "_error", error.getDefaultMessage()));
            return "user/register";
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/nhanViens")
    public String getAllNhanViens(Model model){
        model.addAttribute("nhanviens", userService.getAllUser());
        return "user/nhanvien";
    }


    @GetMapping("/addnv")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", userService.getAllRole());
        return "user/addnv"; // Thay thế "add-user" bằng tên template của trang thêm người dùng của bạn
    }

    // Phương thức xử lý yêu cầu lưu người dùng mới
    @PostMapping("/addnv")
    public String addUser(@ModelAttribute("user") User user, @RequestParam Long role) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
       userService.addNV(user, role);
        return "redirect:/nhanViens"; // Thay thế "/nhanViens" bằng đường dẫn tới trang danh sách người dùng của bạn
    }

    // Phương thức xử lý yêu cầu hiển thị trang chỉnh sửa người dùng
//    @GetMapping("/edit/{id}")
//    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
//        User user = userService.getUserById(id);
//        model.addAttribute("user", user);
//        return "user/editnv"; // Thay thế "edit-user" bằng tên template của trang chỉnh sửa người dùng của bạn
//    }
//
//    // Phương thức xử lý yêu cầu cập nhật thông tin người dùng
//    @PostMapping("/edit/{id}")
//    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
//        user.setId(id);
//        userService.updateUser(user);
//        return "redirect:/nhanViens"; // Thay thế "/nhanViens" bằng đường dẫn tới trang danh sách người dùng của bạn
//    }


    @GetMapping("/editnv/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model){
        User user = userService.getUserById(id);
        if(user != null){
            model.addAttribute("user", user);
            model.addAttribute("roles", userService.getAllRole());
            return "user/editnv";
        }
        else{
            return "not-found";
        }
    }
    @PostMapping("/editnv")
    public String editSanPham(@Valid @ModelAttribute("sanpham") User update, BindingResult result, @RequestParam Long role){
        User user = userService.getUserById(update.getId());
        if(result.hasErrors()){
            return "user/editnv";
        }
        userService.updateUser(update, role);
        return "redirect:/nhanViens";
    }

    @GetMapping("/delete/{id}")
    public String deleteNhanVien(@PathVariable("id") Long id) {
        userService.deleteNhanVien(id);
        return "redirect:/nhanViens";
    }
}

