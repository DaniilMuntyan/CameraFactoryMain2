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

        List<Collector> collectors = employeeController.getAllCollectors();
        Collector collectorCameraBack = this.inputDataHandler.getRandomFromList(collectors);
        Collector collectorCameraBody = this.inputDataHandler.getRandomFromList(collectors);
        Collector collectorCameraLens = this.inputDataHandler.getRandomFromList(collectors);
        Collector collectorCamera = this.inputDataHandler.getRandomFromList(collectors);
        Dimensions backDimensions = inputDataHandler.getRandomDimensions();
        Integer resolutionBack = 20 + rand.nextInt(100);
        Integer colorDepth = 10 + rand.nextInt(100);
        Dimensions bodyDimensions = inputDataHandler.getRandomDimensions();
        String color = inputDataHandler.getRandomColor();
        Integer focalLength = 10 + rand.nextInt(100);
        LensType lensType = inputDataHandler.getRandomLens();
        Camera camera = null;

        if (!grpc) {

            AssembleController assembleController = new AssembleController();

            CameraBack cameraBack = assembleController.assembleCameraBack(collectorCameraBack, backDimensions,
                    resolutionBack, colorDepth);
            System.out.println("ASSEMBLED BY " + collectorCameraBack.getName() + " " +
                    collectorCameraBack.getSurname() + "\n" + cameraBack.toString());

            CameraBody cameraBody = assembleController.assembleCameraBody(collectorCameraBody, bodyDimensions, color);
            System.out.println("ASSEMBLED BY " + collectorCameraBody.getName() + " " + collectorCameraBody.getSurname() +
                    "\n" + cameraBody.toString());

            CameraLens cameraLens = assembleController.assembleCameraLens(collectorCameraLens, focalLength, lensType);
            System.out.println("ASSEMBLED BY " + collectorCameraLens.getName() + " " + collectorCameraLens.getSurname() +
                    "\n" + cameraLens.toString());

            camera = assembleController.assembleCamera(collectorCamera, cameraBack, cameraBody, cameraLens);
            System.out.println("ASSEMBLED BY " + collectorCamera.getName() + " " + collectorCamera.getSurname() + "\n" +
                    camera.toString());
        } else {
            CameraBack cameraBack = serviceControllerGrpc.assembleBack(collectorCameraBack, backDimensions,
                    resolutionBack, colorDepth);
            CameraBody cameraBody = serviceControllerGrpc.assemleBody(collectorCameraBody, bodyDimensions, color);
            CameraLens cameraLens = serviceControllerGrpc.assembleLens(collectorCameraLens, focalLength, lensType);
            camera = serviceControllerGrpc.assembleCamera(collectorCamera, cameraBack, cameraBody, cameraLens);
        }

        return camera;
    }

    public Camera calibrating(Camera camera, Boolean grpc) {

        if(!grpc) {

            MachineController machineController = new MachineController();

            List<Calibrator> calibrators = machineController.getAllCalibrators();
            Machine calibrator = inputDataHandler.getRandomFromList(calibrators);

            CalibrationController calibrationController = new CalibrationController((Calibrator) calibrator);
            Camera newCamera = calibrationController.calibrateCamera(camera);
            System.out.println("CALIBRATED BY " + calibrator.getName() + "\n" + newCamera);

        } else {
            MachineControllerGrpc machineControllerGrpc = new MachineControllerGrpc(channel);

            List<Calibrator> calibrators = machineControllerGrpc.getCalibrators();
            Machine calibrator = inputDataHandler.getRandomFromList(calibrators);

            Camera newCamera = this.serviceControllerGrpc.calibrateCamera((Calibrator) calibrator, camera);
        }

        return camera;
    }

    public Camera finalStage(Camera camera, Boolean grpc) {
        EmployeeController employeeController = new EmployeeController();
        MachineController machineController = new MachineController();

        List<Technician> technicians = employeeController.getAllTechnicians();
        List<Manager> managers = employeeController.getAllManagers();
        List<Packer> packers = machineController.getAllPackers();

        Employee technician = inputDataHandler.getRandomFromList(technicians);
        Employee manager = inputDataHandler.getRandomFromList(managers);
        Machine packer = inputDataHandler.getRandomFromList(packers);
        Camera finalCamera = null;

        if(!grpc) {
            FinalStageController finalStageController =
                    new FinalStageController((Technician) technician, (Manager) manager, (Packer) packer);

            finalCamera = finalStageController.finalStage(camera);
            System.out.println("FINAL STAGE\n" + "MANAGER " + manager.getName() + " " + manager.getSurname() + "\n" +
                    "TECHNICIAN " + technician.getName() + " " + technician.getSurname() + "\n" +
                    "PACKER MACHINE " + packer.getName() + "\n" + finalCamera);
        } else {
            finalCamera = this.serviceControllerGrpc.finalStage((Technician) technician, (Packer) packer,
                    (Manager) manager, camera);
        }

        return finalCamera;
    }
}
