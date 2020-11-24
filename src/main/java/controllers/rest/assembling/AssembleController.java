package controllers.rest.assembling;

import controllers.EndPoints;
import controllers.GlobalVariables;
import dto.requests.assembling.AssembleBackDTO;
import dto.requests.assembling.AssembleBodyDTO;
import dto.requests.assembling.AssembleCameraDTO;
import dto.requests.assembling.AssembleLensDTO;
import entities.camera.*;
import entities.employees.Collector;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public final class AssembleController {

    public CameraBack assembleCameraBack(Collector collector, Dimensions dimensions,
                                         Integer resolution, Integer colorDepth) {
        AssembleBackDTO cameraBackDTO = new AssembleBackDTO(collector.getId(), dimensions, resolution, colorDepth);

        HttpEntity<AssembleBackDTO> assembleBack = new HttpEntity<>(cameraBackDTO);

        ResponseEntity<CameraBack> assembleBackResponseEntity = GlobalVariables.restTemplate
                .postForEntity(EndPoints.ASSEMBLE_CAMERA_BACK, assembleBack, CameraBack.class);

        return assembleBackResponseEntity.getBody();
    }

    public CameraBody assembleCameraBody(Collector collector, Dimensions dimensions, String color) {
        AssembleBodyDTO cameraBodyDTO = new AssembleBodyDTO(collector.getId(), dimensions, color);

        HttpEntity<AssembleBodyDTO> assembleBody = new HttpEntity<>(cameraBodyDTO);

        ResponseEntity<CameraBody> cameraBodyResponseEntity = GlobalVariables.restTemplate
                .postForEntity(EndPoints.ASSEMBLE_CAMERA_BODY, assembleBody, CameraBody.class);

        return cameraBodyResponseEntity.getBody();
    }

    public CameraLens assembleCameraLens(Collector collector, Integer focalLength, LensType lensType) {
        AssembleLensDTO cameraLensDTO = new AssembleLensDTO(collector.getId(), focalLength, lensType);

        HttpEntity<AssembleLensDTO> assembleLens = new HttpEntity<>(cameraLensDTO);

        ResponseEntity<CameraLens> cameraLensResponseEntity = GlobalVariables.restTemplate
                .postForEntity(EndPoints.ASSEMBLE_CAMERA_LENS, assembleLens, CameraLens.class);

        return cameraLensResponseEntity.getBody();
    }

    public Camera assembleCamera(Collector collector, CameraBack cameraBack,
                                 CameraBody cameraBody, CameraLens cameraLens) {
        AssembleCameraDTO cameraDTO = new AssembleCameraDTO(collector.getId(), cameraBack.getId(),
                cameraBody.getId(), cameraLens.getId());

        HttpEntity<AssembleCameraDTO> assembleCamera = new HttpEntity<>(cameraDTO);

        ResponseEntity<Camera> cameraResponseEntity = GlobalVariables.restTemplate
                .postForEntity(EndPoints.ASSEMBLE_CAMERA, assembleCamera, Camera.class);

        return cameraResponseEntity.getBody();
    }
}
