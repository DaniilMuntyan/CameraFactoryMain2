package controllers.rest.calibration;

import controllers.EndPoints;
import controllers.GlobalVariables;
import dto.requests.calibration.CalibrateCameraDTO;
import entities.camera.Camera;
import entities.machines.Calibrator;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public final class CalibrationController {
    private Calibrator calibrator;

    public CalibrationController(Calibrator calibrator) {
        this.calibrator = calibrator;
    }

    public Camera calibrateCamera(Camera camera) {
        CalibrateCameraDTO calibrateCameraDTO = new CalibrateCameraDTO(calibrator.getId(), camera);

        HttpEntity<CalibrateCameraDTO> calibrateCameraDTOHttpEntity = new HttpEntity<>(calibrateCameraDTO);
        //System.out.println(calibrateCameraDTOHttpEntity);
        ResponseEntity<Camera> calibrateCameraResponseEntity = GlobalVariables.restTemplate
                .postForEntity(EndPoints.CALIBRATE_CAMERA, calibrateCameraDTOHttpEntity, Camera.class);

        return calibrateCameraResponseEntity.getBody();
    }
}
