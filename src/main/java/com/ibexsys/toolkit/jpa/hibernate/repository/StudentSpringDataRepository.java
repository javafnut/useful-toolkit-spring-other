package com.ibexsys.toolkit.jpa.hibernate.repository;

import com.ibexsys.toolkit.jpa.hibernate.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentSpringDataRepository extends JpaRepository<Student, Long> {
	
	/* Leaving this blank still gives all JpaRepository Methods or create custom see course repo
    
    @Autowire
    private StudentSpringDataRepository repository;
 
    */
}
