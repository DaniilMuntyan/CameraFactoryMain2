package controllers.rest.final_stage;

import controllers.EndPoints;
import controllers.GlobalVariables;
import dto.requests.final_stage.FinalCheckDTO;
import entities.camera.Camera;
import entities.employees.Manager;
import entities.employees.Technician;
import entities.machines.Packer;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public final class FinalStageController {
    private Technician technician;
    private Manager manager;
    private Packer packer;

    public FinalStageController(Technician technician, Manager manager, Packer packer) {
        this.technician = technician;
        this.manager = manager;
        this.packer = packer;
    }

    public Camera finalStage(Camera camera) {
        FinalCheckDTO finalCheckDTO = new FinalCheckDTO(technician.getId(), packer.getId(), manager.getId(), camera);

        HttpEntity<FinalCheckDTO> finalCheckDTOHttpEntity = new HttpEntity<>(finalCheckDTO);

        ResponseEntity<Camera> cameraResponseEntity = GlobalVariables.restTemplate
                .postForEntity(EndPoints.FINAL_STAGE, finalCheckDTOHttpEntity, Camera.class);

        return cameraResponseEntity.getBody();
    }
}
