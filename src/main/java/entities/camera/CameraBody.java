package entities.camera;

import kpi.trspo.restapp.CameraBodyGrpc;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public final class CameraBody {
    private UUID id;
    private Dimensions dimensions;
    private String color;
    private Camera camera;

    public CameraBody(Dimensions dimensions, String color) {
        this.dimensions = dimensions;
        this.color = color;
    }

    public CameraBody(CameraBodyGrpc cameraBodyGrpc) {
        this.id = UUID.fromString(cameraBodyGrpc.getBodyId().getValue());
        this.dimensions = new Dimensions(cameraBodyGrpc.getDimensions());
        this.color = cameraBodyGrpc.getColor();
    }

    @Override
    public String toString() {
        return "Camera body id: " + id + ", dimensions: " + dimensions + ", color: " + color;
    }
}
