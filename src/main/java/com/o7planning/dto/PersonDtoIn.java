package com.o7planning.dto;

import com.o7planning.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class PersonDtoIn {

    private Long id;
    private String namePerson;
    private int old;
    private boolean gender;
    private String country;
    @NonNull
    private String department;
}
/**
 * Controller nhận DTO > Service chuyển DTO thành model hoặc entity(tao ham convert),
 * rồi xử lý > Repository nhận Entity đưa vào DB
 * Repository lấy từ DB ra Entity > Service xử lý sao đó rồi thành DTO
 * > Controller và trả về DTO
 **/
