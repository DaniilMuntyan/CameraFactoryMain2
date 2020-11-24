package converters;

import entities.employees.Technician;
import kpi.trspo.restapp.ManagerGrpc;
import kpi.trspo.restapp.MyUuid;
import kpi.trspo.restapp.TechnicianGrpc;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class TechnicianConverter {
    public static Technician convert(TechnicianGrpc technicianGrpc) {
        return new Technician(technicianGrpc);
    }

    public static TechnicianGrpc convert(Technician technician) {
        MyUuid id = MyUuid.newBuilder().setValue(technician.getId().toString()).build();
        return TechnicianGrpc.newBuilder()
                .setId(id)
                .setName(technician.getName())
                .setSurname(technician.getSurname())
                .setPhone(technician.getPhone())
                .build();
    }
}
