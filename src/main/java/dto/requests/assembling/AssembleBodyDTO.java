package dto.requests.assembling;

import entities.camera.Dimensions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class AssembleBodyDTO {
    private UUID collectorId;
    private Dimensions dimensions;
    private String color;
}
