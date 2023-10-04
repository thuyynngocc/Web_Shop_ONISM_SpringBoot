package com.example.onism.repository;

import com.example.onism.entity.Loai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoaiRepository extends JpaRepository<Loai, Long> {
}
