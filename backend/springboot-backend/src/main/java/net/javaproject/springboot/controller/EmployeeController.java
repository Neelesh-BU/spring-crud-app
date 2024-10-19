package net.javaproject.springboot.controller;

import net.javaproject.springboot.exception.ResourceNotFoundException;
import net.javaproject.springboot.model.Employee;
import net.javaproject.springboot.repository.EmployeeRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/employees")

public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping()
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    //Adding Create Employee REST API
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        if (employee.getName().isEmpty()
                || employee.getEmail().isEmpty()
                || employee.getAddress().isEmpty()
                || employee.getDateOfBirth().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more fields are empty");
        }
        return employeeRepository.save(employee);
    }

    //Adding Read Employee REST API
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found with id " + id));
        return ResponseEntity.ok(employee);
    }

    //Adding Update Employee REST API
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
        Employee updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found with id " + id));

        updateEmployee.setName(employee.getName());
        updateEmployee.setEmail(employee.getEmail());
        updateEmployee.setDateOfBirth(employee.getDateOfBirth());
        updateEmployee.setAddress(employee.getAddress());

        employeeRepository.save(updateEmployee);

        return ResponseEntity.ok(updateEmployee);
    }

    //Adding Delete Employee REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found with id " + id));

        employeeRepository.delete(employee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
