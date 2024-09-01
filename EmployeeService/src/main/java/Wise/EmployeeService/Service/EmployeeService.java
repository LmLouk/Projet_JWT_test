package Wise.EmployeeService.Service;

import Wise.EmployeeService.Client.DepartementClient;
import Wise.EmployeeService.Model.Departement;
import Wise.EmployeeService.Dao.EmployeeRepository;
import Wise.EmployeeService.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Employee> getAllEmployees(){

        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id){

        return employeeRepository.findById(id).orElse(null);
    }

    public Employee saveEmployee(Employee employee){
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }

    public Employee findByUsername(String username){
        return employeeRepository.findByUsername(username);
    }

    @Autowired
    private DepartementClient departementClient;

    public Departement getEmployeeDepartement(Long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        if (employee != null && employee.getDepartement() != null) {
            return departementClient.getDepartementById(employee.getDepartement().getId());
        }
        return null;
    }

}
