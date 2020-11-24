package converters;

import entities.employees.Manager;
import kpi.trspo.restapp.CalibratorGrpc;
import kpi.trspo.restapp.ManagerGrpc;
import kpi.trspo.restapp.MyUuid;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class ManagerConverter {

    public static Manager convert(ManagerGrpc managerGrpc) {
        return new Manager(managerGrpc);
    }

    public static ManagerGrpc convert(Manager manager) {
        MyUuid id = MyUuid.newBuilder().setValue(manager.getId().toString()).build();
        return ManagerGrpc.newBuilder()
                .setId(id)
                .setName(manager.getName())
                .setSurname(manager.getSurname())
                .setPhone(manager.getPhone())
                .build();
    }

}
