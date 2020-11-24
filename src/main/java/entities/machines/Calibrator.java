package entities.machines;

import controllers.grpc.MachineControllerGrpc;
import kpi.trspo.restapp.CalibratorGrpc;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
//@EqualsAndHashCode(callSuper = true)
public final class Calibrator extends Machine {
    private UUID id;
    private String name;
    public Calibrator(String name) {
        this.name = name;
        //super(name);
    }

    public Calibrator(CalibratorGrpc calibratorGrpc) {
        this.id = UUID.fromString(calibratorGrpc.getCalibratorId().getValue());
        this.name = calibratorGrpc.getName();
        //super(UUID.fromString(calibratorGrpc.getCalibratorId().getValue()), calibratorGrpc.getName());
    }

    @Override
    public String toString() {
        return "Calibration machine id: " + getId() + ", name: " + getName();
    }
}
