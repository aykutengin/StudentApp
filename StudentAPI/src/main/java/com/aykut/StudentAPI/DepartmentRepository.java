package com.aykut.StudentAPI;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aykut.StudentAPI.model.Department;


public interface DepartmentRepository extends JpaRepository<Department, Long> {
	Department findByName(String name);
}
