package controllers.rest.models;

import controllers.EndPoints;
import controllers.GlobalVariables;
import entities.machines.Calibrator;
import entities.machines.Machine;
import entities.machines.Packer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

public final class MachineController {

    public Calibrator createCalibrator(String name) {
        Calibrator calibrator = new Calibrator(name);
        return this.createMachine(EndPoints.CREATE_CALIBRATOR, calibrator, Calibrator.class);
    }

    public Packer createPacker(String name) {
        Packer packer = new Packer(name);
        return this.createMachine(EndPoints.CREATE_PACKER, packer, Packer.class);
    }

    public List<Calibrator> getAllCalibrators() {
        ResponseEntity<List<Calibrator>> responseEntity = GlobalVariables.restTemplate
                .exchange(EndPoints.GET_ALL_CALIBRATORS, HttpMethod.GET, GlobalVariables.headersEntity,
                        new ParameterizedTypeReference<List<Calibrator>>(){});
        return responseEntity.getBody();
    }

    public List<Packer> getAllPackers() {
        ResponseEntity<List<Packer>> responseEntity = GlobalVariables.restTemplate
                .exchange(EndPoints.GET_ALL_PACKERS, HttpMethod.GET, GlobalVariables.headersEntity,
                        new ParameterizedTypeReference<List<Packer>>(){});
        return responseEntity.getBody();
    }

    private <T extends Machine> T createMachine(String endpoint, T newMachine, Class<T> response) {
        ResponseEntity<T> machineResponseEntity = GlobalVariables.restTemplate
                .postForEntity(endpoint, newMachine, response);
        return machineResponseEntity.getBody();
    }
}
