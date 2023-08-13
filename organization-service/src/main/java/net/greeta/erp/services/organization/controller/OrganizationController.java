package net.greeta.erp.services.organization.controller;

import net.greeta.erp.services.organization.client.DepartmentClient;
import net.greeta.erp.services.organization.client.EmployeeClient;
import net.greeta.erp.services.organization.repository.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import net.greeta.erp.services.organization.model.Organization;

@RestController
public class OrganizationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    OrganizationRepository repository;
    @Autowired
    DepartmentClient departmentClient;
    @Autowired
    EmployeeClient employeeClient;

    @PostMapping
    public Organization add(@RequestBody Organization organization) {
        LOGGER.info("Organization add: {}", organization);
        return repository.save(organization);
    }

    @GetMapping
    public Iterable<Organization> findAll() {
        LOGGER.info("Organization find");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Organization findById(@PathVariable("id") String id) {
        LOGGER.info("Organization find: id={}", id);
        return repository.findById(id).orElseThrow();
    }

    @GetMapping("/{id}/with-departments")
    public Organization findByIdWithDepartments(@PathVariable("id") String id) {
        LOGGER.info("Organization find: id={}", id);
        Organization organization = repository.findById(id).orElseThrow();
        organization.setDepartments(departmentClient.findByOrganization(organization.getId()));
        return organization;
    }

    @GetMapping("/{id}/with-departments-and-employees")
    public Organization findByIdWithDepartmentsAndEmployees(@PathVariable("id") String id) {
        LOGGER.info("Organization find: id={}", id);
        Organization organization = repository.findById(id).orElseThrow();
        organization.setDepartments(departmentClient.findByOrganizationWithEmployees(organization.getId()));
        return organization;
    }

    @GetMapping("/{id}/with-employees")
    public Organization findByIdWithEmployees(@PathVariable("id") String id) {
        LOGGER.info("Organization find: id={}", id);
        Organization organization = repository.findById(id).orElseThrow();
        organization.setEmployees(employeeClient.findByOrganization(organization.getId()));
        return organization;
    }

}
