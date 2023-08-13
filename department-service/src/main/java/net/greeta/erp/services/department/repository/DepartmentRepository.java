package net.greeta.erp.services.department.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.greeta.erp.services.department.model.Department;

public interface DepartmentRepository extends CrudRepository<Department, String> {

	List<Department> findByOrganizationId(String organizationId);
	
}
