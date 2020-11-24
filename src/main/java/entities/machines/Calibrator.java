package entities.machines;

import kpi.trspo.restapp.CalibratorGrpc;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
public final class Calibrator extends Machine {

    public Calibrator(String name) {
        super(name);
    }

    public Calibrator(CalibratorGrpc calibratorGrpc) {
        super(UUID.fromString(calibratorGrpc.getCalibratorId().getValue()), calibratorGrpc.getName());
    }

    @Override
    public String toString() {
        return "Calibration machine id: " + getId() + ", name: " + getName();
    }
}
