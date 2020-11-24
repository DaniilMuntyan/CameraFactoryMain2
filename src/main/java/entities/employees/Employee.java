package entities.employees;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Employee {
    private UUID id;
    private String name;
    private String surname;
    private String phone;

    public Employee(String name, String surname, String phone) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Employee " + this.name + " " + this.surname + "\nID: " +  this.id + "\nPhone number: " + this.phone;
    }
}
