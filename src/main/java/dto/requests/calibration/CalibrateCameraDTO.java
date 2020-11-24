package dto.requests.calibration;

import entities.camera.Camera;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public final class CalibrateCameraDTO {
    private UUID calibratorId;
    private Camera camera;
}
