package entities.camera;

import kpi.trspo.restapp.CameraLensGrpc;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public final class CameraLens {
    private UUID id;
    private Integer focalLength;
    private LensType lensType;
    private Camera camera;

    public CameraLens(Integer focalLength, LensType lensType) {
        this.focalLength = focalLength;
        this.lensType = lensType;
    }

    public CameraLens(CameraLensGrpc cameraLensGrpc) {
        this.id = UUID.fromString(cameraLensGrpc.getLensId().getValue());
        this.focalLength = cameraLensGrpc.getFocalLength();
        this.lensType = LensType.values()[cameraLensGrpc.getLensType().getNumber()];
    }

    @Override
    public String toString() {
        return "Camera lens id: " + id + ", focal length: " + focalLength + " mm, lens type: " + lensType;
    }
}
