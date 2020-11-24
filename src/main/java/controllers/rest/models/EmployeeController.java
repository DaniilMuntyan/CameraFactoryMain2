package controllers.rest.models;

import controllers.EndPoints;
import controllers.GlobalVariables;
import entities.employees.Collector;
import entities.employees.Employee;
import entities.employees.Manager;
import entities.employees.Technician;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

public final class EmployeeController {

    public Collector createCollector(String name, String surname, String phone) {
        Collector collector = new Collector(name, surname, phone);
        return this.createEmployee(EndPoints.CREATE_COLLECTOR, collector, Collector.class);
    }

    public Manager createManager(String name, String surname, String phone) {
        Manager manager = new Manager(name, surname, phone);
        return this.createEmployee(EndPoints.CREATE_MANAGER, manager, Manager.class);
    }

   /* public Manager createManagerGrpc(String name, String surname, String phone) {
        Manager manager = new Manager(name, surname, phone);

    }*/

    public Technician createTechnician(String name, String surname, String phone) {
        Technician technician = new Technician(name, surname, phone);
        return this.createEmployee(EndPoints.CREATE_TECHNICIAN, technician, Technician.class);
    }

    public List<Manager> getAllManagers() {
        ResponseEntity<List<Manager>> responseEntity = GlobalVariables.restTemplate
                .exchange(EndPoints.GET_ALL_MANAGERS, HttpMethod.GET, GlobalVariables.headersEntity,
                        new ParameterizedTypeReference<List<Manager>>(){});
        return responseEntity.getBody();
    }

    public List<Collector> getAllCollectors() {
        ResponseEntity<List<Collector>> responseEntity = GlobalVariables.restTemplate
                .exchange(EndPoints.GET_ALL_COLLECTORS, HttpMethod.GET, GlobalVariables.headersEntity,
                        new ParameterizedTypeReference<List<Collector>>(){});
        return responseEntity.getBody();
    }

    public List<Technician> getAllTechnicians() {
        ResponseEntity<List<Technician>> responseEntity = GlobalVariables.restTemplate
                .exchange(EndPoints.GET_ALL_TECHNICIANS, HttpMethod.GET, GlobalVariables.headersEntity,
                        new ParameterizedTypeReference<List<Technician>>(){});
        return responseEntity.getBody();
    }

    private <T extends Employee> T createEmployee(String endpoint, T newEmployee, Class<T> typeResponse) {
        ResponseEntity<T> employeeResponseEntity = GlobalVariables.restTemplate
                .postForEntity(endpoint, newEmployee, typeResponse);
        return employeeResponseEntity.getBody();
    }
}
