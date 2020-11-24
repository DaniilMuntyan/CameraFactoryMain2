package converters;

import entities.employees.Collector;
import kpi.trspo.restapp.CollectorGrpc;
import kpi.trspo.restapp.MyUuid;
import kpi.trspo.restapp.TechnicianGrpc;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class CollectorConverter {
    public static Collector convert(CollectorGrpc collectorGrpc) {
        return new Collector(collectorGrpc);
    }

    public static CollectorGrpc convert(Collector collector) {
        MyUuid id = MyUuid.newBuilder().setValue(collector.getId().toString()).build();
        return CollectorGrpc.newBuilder()
                .setCollectorId(id)
                .setName(collector.getName())
                .setSurname(collector.getSurname())
                .setPhone(collector.getPhone())
                .build();
    }
}
