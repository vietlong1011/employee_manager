package com.o7planning.entity;

import org.thymeleaf.util.StringUtils;

import java.util.Optional;

public class PersonValidator {
    public boolean isValid(Person person) {
        return Optional.ofNullable(person)
                .filter(t -> !StringUtils.isEmpty(t.getNamePerson())) // Kiểm tra name khác rỗng
                .filter(t -> !StringUtils.isEmpty(t.getDepartment())) // Kiểm tra department khác rỗng
                .isPresent(); // Trả về true nếu hợp lệ, ngược lại false
    }
}
