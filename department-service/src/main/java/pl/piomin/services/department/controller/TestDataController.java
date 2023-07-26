package pl.piomin.services.department.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piomin.services.department.model.Department;
import pl.piomin.services.department.repository.DepartmentRepository;

@RestController
@RequestMapping("/test-data")
public class TestDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    DepartmentRepository repository;

    public TestDataController(DepartmentRepository repository) {
        this.repository = repository;
    }

    @DeleteMapping("/delete/department")
    public ResponseEntity<Void> deleteAllDepartments() {
        repository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/department/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("id") String id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
