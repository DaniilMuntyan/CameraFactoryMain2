package converters;

import entities.machines.Calibrator;
import kpi.trspo.restapp.CalibratorGrpc;
import kpi.trspo.restapp.MyUuid;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class CalibratorConverter {

    public static Calibrator convert(CalibratorGrpc calibratorGrpc) {
        return new Calibrator(calibratorGrpc);
    }

    public static CalibratorGrpc convert(Calibrator calibrator) {
        MyUuid id = MyUuid.newBuilder().setValue(calibrator.getId().toString()).build();
        return CalibratorGrpc
                .newBuilder()
                .setCalibratorId(id)
                .setName(calibrator.getName())
                .build();
    }

}
