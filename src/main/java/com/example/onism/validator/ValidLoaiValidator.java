package com.example.onism.validator;


import com.example.onism.entity.Loai;
import com.example.onism.validator.annotation.ValidLoaiId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidLoaiValidator implements ConstraintValidator<ValidLoaiId, Loai>{

    @Override
    public boolean isValid(Loai loai, ConstraintValidatorContext context){
        return loai != null && loai.getId() != null;
    }
}