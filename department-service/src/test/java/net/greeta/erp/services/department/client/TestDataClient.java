package net.greeta.erp.services.department.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;

@FeignClient(name = "testData")
public interface TestDataClient {

    @DeleteMapping("/delete/department")
    public void deleteAllDepartments();
}
