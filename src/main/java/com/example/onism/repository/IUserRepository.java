package com.example.onism.repository;


import com.example.onism.entity.Role;
import com.example.onism.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);



    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_role (user_id, role_id)"+
            "VALUES(?1,?2)",nativeQuery = true)
    void addRoleToUser(Long userId,Long roleId);


    @Modifying
    @Transactional
    @Query(value = "UPDATE user_role\n" +
            "SET role_id = ?2\n" +
            "WHERE user_id = ?1",nativeQuery = true)
    void updateRoleToUser(Long userId,Long roleId);
    @Query("SELECT u.id FROM User u WHERE u.username = ?1")
    Long getUserIdByUsername(String username);

    @Query(value = "SELECT r.name FROM role r INNER JOIN user_role ur ON r.id = ur.role_id WHERE ur.user_id = ?1", nativeQuery = true)
    String[] getRolesOfUser(Long id);

//    List<Role> findAllbyNhanVien(User user);
}

