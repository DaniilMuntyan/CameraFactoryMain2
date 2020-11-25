package controllers.grpc;

import converters.CalibratorConverter;
import converters.PackerConverter;
import entities.machines.Calibrator;
import entities.machines.Packer;
import io.grpc.ManagedChannel;
import kpi.trspo.restapp.*;

import java.util.List;
import java.util.stream.Collectors;

public final class MachineControllerGrpc {

    ManagedChannel channel;

    public MachineControllerGrpc(ManagedChannel channel) {
        this.channel = channel;
    }

    public void createPacker(Packer packer) {
        CreatePackersServiceGrpc.CreatePackersServiceBlockingStub stub
                = CreatePackersServiceGrpc.newBlockingStub(this.channel);

        long startTime = System.currentTimeMillis();
        CreatePackerResponse createPackerResponse = stub.createPackers(CreatePackerRequest
                .newBuilder()
                .setName(packer.getName())
                .build());
        long endTime = System.currentTimeMillis();

        PackerGrpc packerGrpc = createPackerResponse.getPacker();
        Packer newPacker = PackerConverter.convert(packerGrpc);
        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) CREATED PACKER: " + newPacker.toString());
    }

    public void createCalibrator(Calibrator calibrator) {
        CreateCalibratorsServiceGrpc.CreateCalibratorsServiceBlockingStub stub
                = CreateCalibratorsServiceGrpc.newBlockingStub(this.channel);

        long startTime = System.currentTimeMillis();
        CreateCalibratorResponse createCalibratorResponse = stub.createCalibrator(CreateCalibratorRequest
                .newBuilder()
                .setName(calibrator.getName())
                .build());
        long endTime = System.currentTimeMillis();

        CalibratorGrpc calibratorGrpc = createCalibratorResponse.getCalibrator();
        Calibrator newCalibrator = CalibratorConverter.convert(calibratorGrpc);
        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) CREATED CALIBRATOR: " +
                newCalibrator.toString());
    }

    public List<Packer> getPackers() {
        GetPackersServiceGrpc.GetPackersServiceBlockingStub stub
                = GetPackersServiceGrpc.newBlockingStub(this.channel);

        long startTime = System.currentTimeMillis();
        GetAllPackersResponse getAllPackersResponse = stub.getAllPackers(GetAllPackersRequest
                .newBuilder()
                .build());
        long endTime = System.currentTimeMillis();

        List<PackerGrpc> packerGrpcs = getAllPackersResponse.getPackersList();
        List<Packer> packers = packerGrpcs.stream()
                .map(PackerConverter::convert)
                .collect(Collectors.toList());

        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) ALL PACKERS " + packers);
        return packers;
    }

    public List<Calibrator> getCalibrators() {
        GetCalibratorsServiceGrpc.GetCalibratorsServiceBlockingStub stub
                = GetCalibratorsServiceGrpc.newBlockingStub(this.channel);

        long startTime = System.currentTimeMillis();
        GetCalibratorsResponse calibratorsResponse = stub.getAllCalibrators(GetCalibratorsRequest
                .newBuilder()
                .build());
        long endTime = System.currentTimeMillis();

        List<CalibratorGrpc> calibratorGrpcs = calibratorsResponse.getCalibratorsList();
        List<Calibrator> calibrators = calibratorGrpcs.stream()
                .map(CalibratorConverter::convert)
                .collect(Collectors.toList());

        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) ALL CALIBRATORS " + calibrators);
        return calibrators;
    }

}
