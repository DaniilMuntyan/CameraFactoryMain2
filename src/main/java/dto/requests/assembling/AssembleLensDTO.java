package dto.requests.assembling;

import entities.camera.LensType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class AssembleLensDTO {
    private UUID collectorId;
    private Integer focalLength;
    private LensType lensType;
}
