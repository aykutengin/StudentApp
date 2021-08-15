package com.aykut.StudentAPI;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aykut.StudentAPI.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
