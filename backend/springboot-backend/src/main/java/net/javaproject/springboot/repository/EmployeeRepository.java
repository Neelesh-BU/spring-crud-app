package net.javaproject.springboot.repository;

import net.javaproject.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
