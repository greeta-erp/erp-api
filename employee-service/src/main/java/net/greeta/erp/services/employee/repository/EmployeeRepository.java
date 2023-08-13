package net.greeta.erp.services.employee.repository;

import java.util.List;

import net.greeta.erp.services.employee.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

    List<Employee> findByDepartmentId(String departmentId);

    List<Employee> findByOrganizationId(String organizationId);

}
