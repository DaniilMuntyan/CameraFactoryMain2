package controllers.grpc;

import com.google.common.collect.Lists;
import converters.*;
import entities.camera.*;
import entities.camera.Dimensions;
import entities.camera.LensType;
import entities.employees.Collector;
import entities.employees.Manager;
import entities.employees.Technician;
import entities.machines.Calibrator;
import entities.machines.Packer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import kpi.trspo.restapp.*;
import org.springframework.stereotype.Service;

@Service
public class ServiceControllerGrpc {

    ManagedChannel channel;

    public ServiceControllerGrpc(ManagedChannel channel) {
        this.channel = channel;
    }

    public Camera finalStage(Technician technician, Packer packer, Manager manager, Camera camera) {
        FinalStageServiceGrpc.FinalStageServiceBlockingStub stub
                = FinalStageServiceGrpc.newBlockingStub(this.channel);
        MyUuid technicianId = MyUuid.newBuilder().setValue(technician.getId().toString()).build();
        MyUuid packerId = MyUuid.newBuilder().setValue(packer.getId().toString()).build();
        MyUuid managerId = MyUuid.newBuilder().setValue(manager.getId().toString()).build();

        long startTime = System.currentTimeMillis();
        FinalStageResponse finalStageResponse = stub.finalStage(FinalStageRequest
            .newBuilder()
            .setTechnicianId(technicianId)
            .setPackerId(packerId)
            .setManagerId(managerId)
            .setCamera(CameraConverter.convert(camera))
            .build());
        long endTime = System.currentTimeMillis();

        CameraGrpc cameraGrpc = finalStageResponse.getCamera();
        Camera newCamera = CameraConverter.convert(cameraGrpc);
        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) FINAL STAGE CAMERA: " +
                newCamera.toString());
        return newCamera;
    }

    public Camera calibrateCamera(Calibrator calibrator, Camera camera) {
        CalibrateCameraServiceGrpc.CalibrateCameraServiceBlockingStub stub
                = CalibrateCameraServiceGrpc.newBlockingStub(this.channel);
        MyUuid calibratorId = MyUuid.newBuilder().setValue(calibrator.getId().toString()).build();

        long startTime = System.currentTimeMillis();
        CalibrateCameraResponse calibrateCameraResponse = stub.calibrate(CalibrateCameraRequest
                .newBuilder()
                .setCalibratorId(calibratorId)
                .setCamera(CameraConverter.convert(camera))
                .build());
        long endTime = System.currentTimeMillis();

        CameraGrpc cameraGrpc = calibrateCameraResponse.getCamera();
        Camera newCamera = CameraConverter.convert(cameraGrpc);
        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) CALIBRATED CAMERA: " + newCamera.toString());
        return newCamera;
    }

    public Camera assembleCamera(Collector collector, CameraBack cameraBack,
                                CameraBody cameraBody, CameraLens cameraLens) {
        AssembleCameraServiceGrpc.AssembleCameraServiceBlockingStub stub
                = AssembleCameraServiceGrpc.newBlockingStub(this.channel);
        MyUuid id = MyUuid.newBuilder().setValue(collector.getId().toString()).build();
        MyUuid backId = MyUuid.newBuilder().setValue(cameraBack.getId().toString()).build();
        MyUuid bodyId = MyUuid.newBuilder().setValue(cameraBody.getId().toString()).build();
        MyUuid lensId = MyUuid.newBuilder().setValue(cameraLens.getId().toString()).build();

        long startTime = System.currentTimeMillis();
        AssembleCameraResponse assembleCameraResponse = stub.assembleCamera(AssembleCameraRequest
                .newBuilder()
                .setCollectorId(id)
                .setBackId(backId)
                .setBodyId(bodyId)
                .setLensId(lensId)
                .build());
        long endTime = System.currentTimeMillis();

        CameraGrpc cameraGrpc = assembleCameraResponse.getCamera();
        Camera camera = CameraConverter.convert(cameraGrpc);
        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) ASSEMBLED CAMERA: " + camera.toString());
        return camera;
    }

    public CameraLens assembleLens(Collector collector, Integer focalLength, LensType lensType) {
        AssembleLensServiceGrpc.AssembleLensServiceBlockingStub stub
                = AssembleLensServiceGrpc.newBlockingStub(this.channel);
        MyUuid id = MyUuid.newBuilder().setValue(collector.getId().toString()).build();

        long startTime = System.currentTimeMillis();
        AssembleLensResponse assembleLensResponse = stub.assembleLens(AssembleLensRequest
                .newBuilder()
                .setCollectorId(id)
                .setFocalLength(focalLength)
                .setLensType(kpi.trspo.restapp.LensType.valueOf(lensType.name()))
                .build());
        long endTime = System.currentTimeMillis();

        CameraLensGrpc cameraLensGrpc = assembleLensResponse.getCameraLens();
        CameraLens cameraLens = CameraLensConverter.convert(cameraLensGrpc);
        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) ASSEMBLED CAMERA LENS: " +
                cameraLens.toString());
        return cameraLens;
    }

    public CameraBody assemleBody(Collector collector, Dimensions dimensions, String color) {
        AssembleBodyServiceGrpc.AssembleBodyServiceBlockingStub stub
                = AssembleBodyServiceGrpc.newBlockingStub(this.channel);
        MyUuid id = MyUuid.newBuilder().setValue(collector.getId().toString()).build();

        long startTime = System.currentTimeMillis();
        AssembleBodyResponse assembleBodyResponse = stub.assembleBody(AssembleBodyRequest
                .newBuilder()
                .setCollectorId(id)
                .setDimensions(kpi.trspo.restapp.Dimensions.newBuilder()
                        .setWidth(dimensions.getWidth())
                        .setDepth(dimensions.getDepth())
                        .setLength(dimensions.getLength())
                        .build())
                .setColor(color)
                .build());
        long endTime = System.currentTimeMillis();

        CameraBodyGrpc cameraBodyGrpc = assembleBodyResponse.getCameraBody();
        CameraBody cameraBody = CameraBodyConverter.convert(cameraBodyGrpc);
        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) ASSEMBLED CAMERA BODY: " +
                cameraBody.toString());
        return cameraBody;
    }

    public CameraBack assembleBack(Collector collector, Dimensions dimensions,
                             Integer resolution, Integer colorDepth) {
        AssembleBackServiceGrpc.AssembleBackServiceBlockingStub stub
                = AssembleBackServiceGrpc.newBlockingStub(this.channel);
        MyUuid id = MyUuid.newBuilder().setValue(collector.getId().toString()).build();

        long startTime = System.currentTimeMillis();
        AssembleBackResponse assembleBackResponse = stub.assembleBack(AssembleBackRequest
                .newBuilder()
                .setCollectorId(id)
                .setDimensions(kpi.trspo.restapp.Dimensions.newBuilder()
                        .setWidth(dimensions.getWidth())
                        .setDepth(dimensions.getDepth())
                        .setLength(dimensions.getLength())
                        .build())
                .setColorDepth(colorDepth)
                .setResolution(resolution)
                .build());
        long endTime = System.currentTimeMillis();

        CameraBackGrpc cameraBackGrpc = assembleBackResponse.getCameraBack();
        CameraBack cameraBack = CameraBackConverter.convert(cameraBackGrpc);
        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) ASSEMBLED CAMERA BACK: " +
                cameraBack.toString());
        return cameraBack;
    }
}
