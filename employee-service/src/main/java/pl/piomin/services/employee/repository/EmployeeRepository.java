package pl.piomin.services.employee.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.piomin.services.employee.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

    List<Employee> findByDepartmentId(String departmentId);

    List<Employee> findByOrganizationId(String organizationId);

}
