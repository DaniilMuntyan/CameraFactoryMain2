package entities.employees;

import kpi.trspo.restapp.CollectorGrpc;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public final class Collector extends Employee {

    public Collector(String name, String surname, String phone) {
        super(name, surname, phone);
    }

    public Collector(CollectorGrpc collectorGrpc) {
        super.setId(UUID.fromString(collectorGrpc.getCollectorId().getValue()));
        super.setName(collectorGrpc.getName());
        super.setSurname(collectorGrpc.getSurname());
        super.setPhone(collectorGrpc.getPhone());
    }

    @Override
    public String toString() {
        return "Collector " +  getName() + " " + getSurname() + ", id: " +  getId() + ", phone: " + getPhone();
    }
}
