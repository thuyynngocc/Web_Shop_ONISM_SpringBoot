package com.example.onism.services;

import com.example.onism.entity.Loai;
import com.example.onism.entity.SanPham;
import com.example.onism.repository.ILoaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoaiService {

    @Autowired
    private final ILoaiRepository loaiRepository;

    public List<Loai> getAllLoai(){
        return loaiRepository.findAll();
    }

    public Loai getLoaiById(Long id){
        return loaiRepository.findById(id).orElse(null);
    }

    public void addSanPham(Loai loai){
        loaiRepository.save(loai);
    }

    public void updateSanPham(Loai loai){
        loaiRepository.save(loai);
    }

    public void deleteSanPham(Long id){
        loaiRepository.deleteById(id);
    }
}
