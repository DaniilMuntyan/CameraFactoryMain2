import controllers.grpc.EmployeeControllerGrpc;
import controllers.grpc.MachineControllerGrpc;
import controllers.grpc.ServiceControllerGrpc;
import controllers.rest.assembling.AssembleController;
import controllers.rest.calibration.CalibrationController;
import controllers.rest.final_stage.FinalStageController;
import controllers.rest.models.EmployeeController;
import controllers.rest.models.MachineController;
import entities.camera.*;
import entities.employees.Collector;
import entities.employees.Employee;
import entities.employees.Manager;
import entities.employees.Technician;
import entities.machines.Calibrator;
import entities.machines.Machine;
import entities.machines.Packer;
import io.grpc.ManagedChannel;

import java.util.List;
import java.util.Random;

public final class ProductLifecycle {
    private final InputDataHandler inputDataHandler;
    private final Random rand;
    private final ServiceControllerGrpc serviceControllerGrpc;
    private final ManagedChannel channel;

    public ProductLifecycle(ManagedChannel channel) {
        this.channel = channel;
        this.serviceControllerGrpc = new ServiceControllerGrpc(channel);
        this.inputDataHandler = new InputDataHandler(channel);
        this.rand = new Random();
    }

    public Camera assembling(Boolean grpc) {
        EmployeeController employeeController = new EmployeeController();
        EmployeeControllerGrpc employeeControllerGrpc = new EmployeeControllerGrpc(this.channel);

        List<Collector> collectors;
        Collector collectorCameraBack;
        Collector collectorCameraBody;
        Collector collectorCameraLens;
        Collector collectorCamera;

        Dimensions backDimensions = inputDataHandler.getRandomDimensions();
        Integer resolutionBack = 20 + rand.nextInt(100);
        Integer colorDepth = 10 + rand.nextInt(100);
        Dimensions bodyDimensions = inputDataHandler.getRandomDimensions();
        String color = inputDataHandler.getRandomColor();
        Integer focalLength = 10 + rand.nextInt(100);
        LensType lensType = inputDataHandler.getRandomLens();
        Camera camera = null;

        long startTime;
        long endTime;

        if (!grpc) {

            collectors = employeeController.getAllCollectors();

            collectorCameraBack = this.inputDataHandler.getRandomFromList(collectors);
            collectorCameraBody = this.inputDataHandler.getRandomFromList(collectors);
            collectorCameraLens = this.inputDataHandler.getRandomFromList(collectors);
            collectorCamera = this.inputDataHandler.getRandomFromList(collectors);

            AssembleController assembleController = new AssembleController();

            startTime = System.currentTimeMillis();
            CameraBack cameraBack = assembleController.assembleCameraBack(collectorCameraBack, backDimensions,
                    resolutionBack, colorDepth);
            endTime = System.currentTimeMillis();

            System.out.println("[REST] (" + (endTime - startTime) + " ms) ASSEMBLED BY " +
                    collectorCameraBack.getName() + " " + collectorCameraBack.getSurname() + "\n" +
                    cameraBack.toString());

            startTime = System.currentTimeMillis();
            CameraBody cameraBody = assembleController.assembleCameraBody(collectorCameraBody, bodyDimensions, color);
            endTime = System.currentTimeMillis();

            System.out.println("[REST] (" + (endTime - startTime) + " ms) ASSEMBLED BY " +
                    collectorCameraBody.getName() + " " + collectorCameraBody.getSurname() +
                    "\n" + cameraBody.toString());

            startTime = System.currentTimeMillis();
            CameraLens cameraLens = assembleController.assembleCameraLens(collectorCameraLens, focalLength, lensType);
            endTime = System.currentTimeMillis();

            System.out.println("[REST] (" + (endTime - startTime) + " ms) ASSEMBLED BY " +
                    collectorCameraLens.getName() + " " + collectorCameraLens.getSurname() +
                    "\n" + cameraLens.toString());

            startTime = System.currentTimeMillis();
            camera = assembleController.assembleCamera(collectorCamera, cameraBack, cameraBody, cameraLens);
            endTime = System.currentTimeMillis();

            System.out.println("[REST] (" + (endTime - startTime) + " ms) ASSEMBLED BY " +
                    collectorCamera.getName() + " " + collectorCamera.getSurname() + "\n" +
                    camera.toString());
        } else {
            collectors = employeeControllerGrpc.getCollectors();

            collectorCameraBack = this.inputDataHandler.getRandomFromList(collectors);
            collectorCameraBody = this.inputDataHandler.getRandomFromList(collectors);
            collectorCameraLens = this.inputDataHandler.getRandomFromList(collectors);
            collectorCamera = this.inputDataHandler.getRandomFromList(collectors);

            CameraBack cameraBack = serviceControllerGrpc.assembleBack(collectorCameraBack, backDimensions,
                    resolutionBack, colorDepth);
            CameraBody cameraBody = serviceControllerGrpc.assemleBody(collectorCameraBody, bodyDimensions, color);
            CameraLens cameraLens = serviceControllerGrpc.assembleLens(collectorCameraLens, focalLength, lensType);
            camera = serviceControllerGrpc.assembleCamera(collectorCamera, cameraBack, cameraBody, cameraLens);
        }

        return camera;
    }

    public Camera calibrating(Camera camera, Boolean grpc) {
        Camera newCamera;
        long startTime;
        long endTime;

        if(!grpc) {
            MachineController machineController = new MachineController();

            List<Calibrator> calibrators = machineController.getAllCalibrators();
            Machine calibrator = inputDataHandler.getRandomFromList(calibrators);

            CalibrationController calibrationController = new CalibrationController((Calibrator) calibrator);

            startTime = System.currentTimeMillis();
            newCamera = calibrationController.calibrateCamera(camera);
            endTime = System.currentTimeMillis();

            System.out.println("[REST] (" + (endTime - startTime) + " ms) CALIBRATED BY " +
                    calibrator.getName() + "\n" + newCamera);

        } else {
            MachineControllerGrpc machineControllerGrpc = new MachineControllerGrpc(channel);

            List<Calibrator> calibrators = machineControllerGrpc.getCalibrators();
            Machine calibrator = inputDataHandler.getRandomFromList(calibrators);
            newCamera = this.serviceControllerGrpc.calibrateCamera((Calibrator) calibrator, camera);
        }

        return newCamera;
    }

    public Camera finalStage(Camera camera, Boolean grpc) {
        EmployeeController employeeController = new EmployeeController();
        MachineController machineController = new MachineController();
        EmployeeControllerGrpc employeeControllerGrpc = new EmployeeControllerGrpc(this.channel);
        MachineControllerGrpc machineControllerGrpc = new MachineControllerGrpc(this.channel);
        long startTime;
        long endTime;

        List<Technician> technicians;
        List<Manager> managers;
        List<Packer> packers;

        Employee technician;
        Employee manager;
        Machine packer;
        Camera finalCamera = null;

        if(!grpc) {
            technicians = employeeController.getAllTechnicians();
            managers = employeeController.getAllManagers();
            packers = machineController.getAllPackers();

            technician = inputDataHandler.getRandomFromList(technicians);
            manager = inputDataHandler.getRandomFromList(managers);
            packer = inputDataHandler.getRandomFromList(packers);

            FinalStageController finalStageController =
                    new FinalStageController((Technician) technician, (Manager) manager, (Packer) packer);

            startTime = System.currentTimeMillis();
            finalCamera = finalStageController.finalStage(camera);
            endTime = System.currentTimeMillis();

            System.out.println("[REST] (" + (endTime - startTime) + " ms) FINAL STAGE\n" + "MANAGER " +
                    manager.getName() + " " + manager.getSurname() + "\n" +
                    "TECHNICIAN " + technician.getName() + " " + technician.getSurname() + "\n" +
                    "PACKER MACHINE " + packer.getName() + "\n" + finalCamera);
        } else {
            technicians = employeeControllerGrpc.getTechnicians();
            managers = employeeControllerGrpc.getManagers();
            packers = machineControllerGrpc.getPackers();

            technician = inputDataHandler.getRandomFromList(technicians);
            manager = inputDataHandler.getRandomFromList(managers);
            packer = inputDataHandler.getRandomFromList(packers);

            finalCamera = this.serviceControllerGrpc.finalStage((Technician) technician, (Packer) packer,
                    (Manager) manager, camera);
        }

        return finalCamera;
    }
}
