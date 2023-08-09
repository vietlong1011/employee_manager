package com.o7planning.repository.ERepository;
import com.o7planning.entity.employee.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
     List<Department> findAllByDepartment(String department);


}
