package entities.employees;

import kpi.trspo.restapp.TechnicianGrpc;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public final class Technician extends Employee {

    public Technician(String name, String surname, String phone) {
        super(name, surname, phone);
    }

    public Technician(TechnicianGrpc technicianGrpc) {
        super.setId(UUID.fromString(technicianGrpc.getId().getValue()));
        super.setName(technicianGrpc.getName());
        super.setSurname(technicianGrpc.getSurname());
        super.setPhone(technicianGrpc.getPhone());
    }

    @Override
    public String toString() {
        return "Technician " + getName() + " " + getSurname() + ", id: " +  getId() + ", phone: " + getPhone();
    }
}
