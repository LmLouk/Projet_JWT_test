package Wise.EmployeeService.Controller;

import Wise.EmployeeService.Model.Departement;
import Wise.EmployeeService.Model.Employee;
import Wise.EmployeeService.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees(){

        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id){
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
        Employee existingEmployee = employeeService.getEmployeeById(id);
        if(existingEmployee != null){
            existingEmployee.setPrenom(employee.getPrenom());
            existingEmployee.setNom(employee.getNom());
            existingEmployee.setRole(employee.getRole());
            existingEmployee.setUsername((employee.getUsername()));
            existingEmployee.setPassword(employee.getPassword());
            existingEmployee.setVille(employee.getVille());
            existingEmployee.setDepartement(employee.getDepartement());
            return employeeService.saveEmployee(existingEmployee);

        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/{id}/departement")
    public Departement getEmployeeDepartement(@PathVariable Long id){
       return employeeService.getEmployeeDepartement(id);
    }
}

