package converters;

import entities.camera.CameraLens;
import kpi.trspo.restapp.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class CameraLensConverter {
    public static CameraLens convert(CameraLensGrpc cameraLensGrpc) {
        return new CameraLens(cameraLensGrpc);
    }

    public static CameraLensGrpc convert(CameraLens cameraLens) {
        MyUuid id = MyUuid.newBuilder().setValue(cameraLens.getId().toString()).build();
        LensType lensTypeGrpc = LensType.valueOf(cameraLens.getLensType().name());
        return CameraLensGrpc
                .newBuilder()
                .setLensId(id)
                .setFocalLength(cameraLens.getFocalLength())
                .setLensType(lensTypeGrpc)
                .build();
    }
}
