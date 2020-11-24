package dto.requests.final_stage;

import entities.camera.Camera;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public final class FinalCheckDTO {
    private UUID technicianId;
    private UUID packerId;
    private UUID managerId;
    private Camera camera;
}
