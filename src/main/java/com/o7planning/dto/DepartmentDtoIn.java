package com.o7planning.dto;

import com.o7planning.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepartmentDtoIn {
    private Department department;

}
