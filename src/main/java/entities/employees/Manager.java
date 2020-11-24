package entities.employees;

import kpi.trspo.restapp.ManagerGrpc;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public final class Manager extends Employee {

    public Manager(String name, String surname, String phone) {
        super(name, surname, phone);
    }

    public Manager(ManagerGrpc managerGrpc) {
        super.setId(UUID.fromString(managerGrpc.getId().getValue()));
        super.setName(managerGrpc.getName());
        super.setSurname(managerGrpc.getSurname());
        super.setPhone(managerGrpc.getPhone());
    }

    @Override
    public String toString() {
        return "Manager " + getName() + " " + getSurname() + ", id: " +  getId() + ", phone: " + getPhone();
    }
}
