package Wise.EmployeeService.Client;

import Wise.EmployeeService.Model.Departement;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "departement-service")
public interface DepartementClient {
    @GetMapping("/departements/{id}")
    Departement getDepartementById(@PathVariable("id") Long id);
}
