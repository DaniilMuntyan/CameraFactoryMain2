package controllers.grpc;

import converters.CollectorConverter;
import converters.ManagerConverter;
import converters.TechnicianConverter;
import entities.employees.Collector;
import entities.employees.Employee;
import entities.employees.Manager;
import entities.employees.Technician;
import io.grpc.ManagedChannel;
import kpi.trspo.restapp.*;

import java.util.List;
import java.util.stream.Collectors;

public final class EmployeeControllerGrpc {

    ManagedChannel channel;

    public EmployeeControllerGrpc(ManagedChannel channel) {
        this.channel = channel;
    }

    public void createTechnician(Technician technician) {
        CreateTechnicianServiceGrpc.CreateTechnicianServiceBlockingStub stub
                = CreateTechnicianServiceGrpc.newBlockingStub(this.channel);

        long startTime = System.currentTimeMillis();
        CreateTechnicianResponse createTechnicianResponse = stub.createTechnician(CreateTechnicianRequest
                .newBuilder()
                .setName(technician.getName())
                .setSurname(technician.getSurname())
                .setPhone(technician.getPhone())
                .build());
        long endTime = System.currentTimeMillis();

        TechnicianGrpc technicianGrpc = createTechnicianResponse.getTechnician();
        Technician newTechnician = TechnicianConverter.convert(technicianGrpc);
        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) CREATED TECHNICIAN: " +
                newTechnician.toString());
    }

    public void createCollector(Collector collector) {
        CreateCollectorsServiceGrpc.CreateCollectorsServiceBlockingStub stub
                = CreateCollectorsServiceGrpc.newBlockingStub(this.channel);

        long startTime = System.currentTimeMillis();
        CreateCollectorResponse createCollectorResponse = stub.createCollectors(CreateCollectorRequest
                .newBuilder()
                .setName(collector.getName())
                .setSurname(collector.getSurname())
                .setPhone(collector.getPhone())
                .build());
        long endTime = System.currentTimeMillis();

        CollectorGrpc collectorGrpc = createCollectorResponse.getCollector();
        Collector newColector = CollectorConverter.convert(collectorGrpc);
        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) CREATED COLLECTOR: " +
                newColector.toString());
    }

    public void createManager(entities.employees.Manager manager) {
        CreateManagerServiceGrpc.CreateManagerServiceBlockingStub stub
                = CreateManagerServiceGrpc.newBlockingStub(this.channel);

        long startTime = System.currentTimeMillis();
        CreateManagerResponse createManagerResponse = stub.createManager(CreateManagerRequest
                .newBuilder()
                .setName(manager.getName())
                .setSurname(manager.getSurname())
                .setPhone(manager.getPhone())
                .build());
        long endTime = System.currentTimeMillis();

        ManagerGrpc managerGrpc = createManagerResponse.getManager();
        Manager newManager = ManagerConverter.convert(managerGrpc);
        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) CREATED MANAGER: " + newManager.toString());
    }

    public List<Manager> getManagers() {
        GetManagersServiceGrpc.GetManagersServiceBlockingStub stub
                = GetManagersServiceGrpc.newBlockingStub(this.channel);

        long startTime = System.currentTimeMillis();
        GetManagersResponse getManagersResponse = stub.getManagers(GetManagersRequest
                .newBuilder()
                .build());
        long endTime = System.currentTimeMillis();

        List<ManagerGrpc> managerGrpcs = getManagersResponse.getManagersList();
        List<Manager> managers = managerGrpcs.stream()
                .map(ManagerConverter::convert)
                .collect(Collectors.toList());

        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) ALL MANAGERS " + managers);
        return managers;
    }

    public List<Technician> getTechnicians() {
        GetTechniciansServiceGrpc.GetTechniciansServiceBlockingStub stub
                = GetTechniciansServiceGrpc.newBlockingStub(this.channel);

        long startTime = System.currentTimeMillis();
        GetTechniciansResponse getTechniciansResponse = stub.getTechnicians(GetTechniciansRequest
                .newBuilder()
                .build());
        long endTime = System.currentTimeMillis();

        List<TechnicianGrpc> technicianGrpcs = getTechniciansResponse.getTechniciansList();
        List<Technician> technicians = technicianGrpcs.stream()
                .map(TechnicianConverter::convert)
                .collect(Collectors.toList());

        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) ALL TECHNICIANS" + technicians);

        return technicians;

    }

    public List<Collector> getCollectors() {
        GetCollectorsServiceGrpc.GetCollectorsServiceBlockingStub stub
                = GetCollectorsServiceGrpc.newBlockingStub(this.channel);

        long startTime = System.currentTimeMillis();
        GetAllCollectorsResponse getAllCollectorsResponse = stub.getAllCollectors(GetAllCollectorsRequest
                .newBuilder()
                .build());
        long endTime = System.currentTimeMillis();

        List<CollectorGrpc> collectorGrpcs = getAllCollectorsResponse.getCollectorsList();
        List<Collector> collectors = collectorGrpcs.stream()
                .map(CollectorConverter::convert)
                .collect(Collectors.toList());

        System.out.println("[GRPC] (" + (endTime - startTime) + " ms) ALL COLLECTORS " + collectors);

        return collectors;
    }
}
