package net.greeta.erp.services.organization.repository;

import org.springframework.data.repository.CrudRepository;

import net.greeta.erp.services.organization.model.Organization;

public interface OrganizationRepository extends CrudRepository<Organization, String> {
	
}
