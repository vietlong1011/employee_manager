package com.o7planning.entity;

import com.o7planning.dto.PersonDtoIn;
import org.thymeleaf.util.StringUtils;

import java.util.Optional;

public class PersonValidator {
    public boolean isValid(PersonDtoIn person) {
        return Optional.ofNullable(person)
                .filter(t -> !StringUtils.isEmpty(t.getNamePerson())) // Kiểm tra name khác rỗng
                .filter(t -> !StringUtils.isEmpty(t.getDepartment())) // Kiểm tra department khác rỗng
                .isPresent(); // Trả về true nếu hợp lệ, ngược lại false
    }
}
/** ban dau de department la string nhung da chuyen doi sang obj de thuc hien relationships**/
