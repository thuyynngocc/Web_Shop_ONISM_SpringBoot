package com.example.onism.services;


import com.example.onism.entity.Loai;
import com.example.onism.entity.Role;
import com.example.onism.entity.User;
import com.example.onism.repository.IRoleRepository;
import com.example.onism.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    public void save(User user){

        userRepository.save(user);
        Long userId = userRepository.getUserIdByUsername(user.getUsername());
        Long roleId = roleRepository.getRoleIdByName("USER");
        if(roleId != 0 && userId != 0){
            userRepository.addRoleToUser(userId,roleId);
        }
    }

    public void addNV(User user, Long id){

        userRepository.save(user);
        Long userId = userRepository.getUserIdByUsername(user.getUsername());

        if(id != null && userId != 0){
            userRepository.addRoleToUser(userId, id);
        }
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public List<Role> getAllRole(){
        return roleRepository.findAll();
    }


    public Role getRoleById(Long id){
        return roleRepository.findById(id).orElse(null);
    }

    public Role getAllUser_id(Long id){
        return roleRepository.getRoleIdById(id);
    }

    public void addUser(User user) {userRepository.save(user);}


    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void updateUser(User user, Long id) {userRepository.save(user);
        Long userId = userRepository.getUserIdByUsername(user.getUsername());
        userRepository.updateRoleToUser(userId, id);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public void deleteNhanVien(Long id) {
        User nhanVien = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid NhanVien ID: " + id));
        userRepository.delete(nhanVien);
    }
}

