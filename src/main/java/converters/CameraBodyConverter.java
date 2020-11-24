package converters;

import entities.camera.CameraBody;
import kpi.trspo.restapp.CameraBackGrpc;
import kpi.trspo.restapp.CameraBodyGrpc;
import kpi.trspo.restapp.Dimensions;
import kpi.trspo.restapp.MyUuid;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class CameraBodyConverter {
    public static CameraBody convert(CameraBodyGrpc cameraBodyGrpc) {
        return new CameraBody(cameraBodyGrpc);
    }

    public static CameraBodyGrpc convert(CameraBody cameraBody) {
        MyUuid id = MyUuid.newBuilder().setValue(cameraBody.getId().toString()).build();
        entities.camera.Dimensions dimensions = cameraBody.getDimensions();
        return CameraBodyGrpc
                .newBuilder()
                .setBodyId(id)
                .setDimensions(Dimensions.newBuilder()
                        .setLength(dimensions.getLength())
                        .setDepth(dimensions.getDepth())
                        .setWidth(dimensions.getWidth()))
                .setColor(cameraBody.getColor())
                .build();
    }
}
