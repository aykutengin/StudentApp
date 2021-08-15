package com.aykut.StudentAPI.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aykut.StudentAPI.DepartmentRepository;
import com.aykut.StudentAPI.model.Department;


@CrossOrigin(origins = "*")
@RestController
public class DepartmentController {

	private final DepartmentRepository repository;
	
	public DepartmentController(DepartmentRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("/departments")
	List<Department> all() {
		return repository.findAll();
	}
	
	@PostMapping("/departments")
	Department newDepartment(@RequestBody Department newDepartment) {
		return repository.save(newDepartment);
	}
}
