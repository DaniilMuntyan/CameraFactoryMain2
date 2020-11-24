package entities.camera;

import converters.CameraBackConverter;
import converters.CameraBodyConverter;
import converters.CameraLensConverter;
import entities.employees.Manager;
import kpi.trspo.restapp.CameraGrpc;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public final class Camera {
    private UUID camera_id;
    private String innerInfo;
    private Boolean isPacked = false;
    private Boolean isFirmware = false;
    private Boolean isWipedClean = false;
    private Boolean isRejected = false;
    private CameraBack cameraBack;
    private CameraBody cameraBody;
    private CameraLens cameraLens;
    private Manager manager;

    public Camera(CameraBack cameraBack, CameraBody cameraBody, CameraLens cameraLens) {
        this.cameraBack = cameraBack;
        this.cameraBody = cameraBody;
        this.cameraLens = cameraLens;
    }

    public Camera(CameraGrpc cameraGrpc) {
        this.camera_id = UUID.fromString(cameraGrpc.getCameraId().getValue());
        this.innerInfo = cameraGrpc.getInnerInfo();
        this.isPacked = cameraGrpc.getIsPacked();
        this.isFirmware = cameraGrpc.getIsFirmware();
        this.isWipedClean = cameraGrpc.getIsWipedClean();
        this.isRejected = cameraGrpc.getIsRejected();
        this.cameraBack = CameraBackConverter.convert(cameraGrpc.getCameraBack());
        this.cameraBody = CameraBodyConverter.convert(cameraGrpc.getCameraBody());
        this.cameraLens = CameraLensConverter.convert(cameraGrpc.getCameraLens());
    }

    @Override
    public String toString() {
        String packing = isPacked ? "packed" : "unpacked";
        String flashing = isFirmware ? "flashed" : "unflashed";
        String cleaning = isWipedClean ? "wiped" : "not wiped";
        String rejected = isRejected ? "rejected": "not rejected";
        String gap = "\n\t";

        return "Camera id: " + camera_id + ", " + rejected + ", " + packing + ", "  + cleaning + ", " +  flashing +
                ((innerInfo != null && innerInfo.length() > 0) ? "\n" + innerInfo + gap : gap) + cameraBack + gap +
                cameraBody + gap + cameraLens + (manager != null ? gap + manager : "");
    }
}
