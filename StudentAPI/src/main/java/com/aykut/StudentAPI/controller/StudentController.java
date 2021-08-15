package com.aykut.StudentAPI.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aykut.StudentAPI.DepartmentRepository;
import com.aykut.StudentAPI.StudentNotFoundException;
import com.aykut.StudentAPI.StudentRepository;
import com.aykut.StudentAPI.model.Department;
import com.aykut.StudentAPI.model.Student;

@CrossOrigin(origins = "*")
@RestController
public class StudentController {
	private final StudentRepository studentRepository;
	private final DepartmentRepository departmenRepository;

	StudentController(StudentRepository studentRepository, DepartmentRepository departmenRepository) {
		this.studentRepository = studentRepository;
		this.departmenRepository = departmenRepository;
	}

	@GetMapping("/students")
	List<Student> all() {
		return studentRepository.findAll();
	}

	@GetMapping("/students/{id}")
	Student getStudent(@PathVariable Long id) {
		return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
	}

	@PostMapping("/students")
	Student newStudent(@RequestBody Student newStudent) {
		Department department = departmenRepository.findByName(newStudent.getDepartment().getName());
		newStudent.setDepartment(department);
		return studentRepository.save(newStudent);
	}

	@PutMapping("/students/")
	Student replaceStudent(@RequestBody Student newStudent) {
		Department department = departmenRepository.findByName(newStudent.getDepartment().getName());
		return studentRepository.findById(newStudent.getId()).map(student -> {
			student.setName(newStudent.getName());
			student.setLastName(newStudent.getLastName());
			student.setDepartment(department);
			return studentRepository.save(student);
		}).orElseGet(() -> {
			newStudent.setId(newStudent.getId());
			return studentRepository.save(newStudent);
		});
	}

	@DeleteMapping("/students/{id}")
	void deleteStudent(@PathVariable Long id) {
		studentRepository.deleteById(id);
	}
}
