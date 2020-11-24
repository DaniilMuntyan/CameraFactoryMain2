package entities.camera;

import kpi.trspo.restapp.CameraBackGrpc;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public final class CameraBack {
    private UUID id;
    private Boolean matrixCheck;
    private Dimensions dimensions;
    private Integer resolution;
    private Integer colorDepth;
    private Camera camera;

    public CameraBack(Dimensions dimensions, Integer resolution, Integer colorDepth) {
        this.dimensions = dimensions;
        this.resolution = resolution;
        this.colorDepth = colorDepth;
    }

    public CameraBack(CameraBackGrpc cameraBackGrpc) {
        this.id = UUID.fromString(cameraBackGrpc.getBackId().getValue());
        this.matrixCheck = cameraBackGrpc.getMatrixCheck();
        this.dimensions = new Dimensions(cameraBackGrpc.getDimensions());
        this.colorDepth = cameraBackGrpc.getColorDepth();
    }

    @Override
    public String toString() {
        return "Camera back id: " + id + ", dimensions: " + dimensions + ", resolution: " + resolution +
                " MP, color depth: " + colorDepth + " bpp" + ", matrix check: " + matrixCheck +
                (camera != null ? camera.getCamera_id() : "");
    }
}
