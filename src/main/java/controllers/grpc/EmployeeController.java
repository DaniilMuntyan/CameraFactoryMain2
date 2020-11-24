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

public final class EmployeeController {

    ManagedChannel channel;

    public EmployeeController(ManagedChannel channel) {
        this.channel = channel;
    }

    public void createTechnician(Technician technician) {
        CreateTechnicianServiceGrpc.CreateTechnicianServiceBlockingStub stub
                = CreateTechnicianServiceGrpc.newBlockingStub(this.channel);

        CreateTechnicianResponse createTechnicianResponse = stub.createTechnician(CreateTechnicianRequest
                .newBuilder()
                .setName(technician.getName())
                .setSurname(technician.getSurname())
                .setPhone(technician.getPhone())
                .build());

        channel.shutdown();

        TechnicianGrpc technicianGrpc = createTechnicianResponse.getTechnician();
        Technician newTechnician = TechnicianConverter.convert(technicianGrpc);
        System.out.println("CREATED TECHNICIAN: " + newTechnician.toString());
    }

    public void createCollector(Collector collector) {
        CreateCollectorsServiceGrpc.CreateCollectorsServiceBlockingStub stub
                = CreateCollectorsServiceGrpc.newBlockingStub(this.channel);

        CreateCollectorResponse createCollectorResponse = stub.createCollectors(CreateCollectorRequest
                .newBuilder()
                .setName(collector.getName())
                .setSurname(collector.getSurname())
                .setPhone(collector.getPhone())
                .build());

        channel.shutdown();

        CollectorGrpc collectorGrpc = createCollectorResponse.getCollector();
        Collector newColector = CollectorConverter.convert(collectorGrpc);
        System.out.println("CREATED COLLECTOR: " + newColector.toString());
    }

    public void createManager(entities.employees.Manager manager) {
        CreateManagerServiceGrpc.CreateManagerServiceBlockingStub stub
                = CreateManagerServiceGrpc.newBlockingStub(this.channel);

        CreateManagerResponse createManagerResponse = stub.createManager(CreateManagerRequest
                .newBuilder()
                .setName(manager.getName())
                .setSurname(manager.getSurname())
                .setPhone(manager.getPhone())
                .build());

        channel.shutdown();

        ManagerGrpc managerGrpc = createManagerResponse.getManager();
        Manager newManager = ManagerConverter.convert(managerGrpc);
        System.out.println("CREATED MANAGER: " + newManager.toString());
    }

    public void getManagers() {
        GetManagersServiceGrpc.GetManagersServiceBlockingStub stub
                = GetManagersServiceGrpc.newBlockingStub(this.channel);

        GetManagersResponse getManagersResponse = stub.getManagers(GetManagersRequest
                .newBuilder()
                .build());

        channel.shutdown();

        List<ManagerGrpc> managerGrpcs = getManagersResponse.getManagersList();
        List<Manager> managers = managerGrpcs.stream()
                .map(ManagerConverter::convert)
                .collect(Collectors.toList());

        System.out.println(managers);
    }

    public void getTechnicians() {
        GetTechniciansServiceGrpc.GetTechniciansServiceBlockingStub stub
                = GetTechniciansServiceGrpc.newBlockingStub(this.channel);

        GetTechniciansResponse getTechniciansResponse = stub.getTechnicians(GetTechniciansRequest
                .newBuilder()
                .build());

        List<TechnicianGrpc> technicianGrpcs = getTechniciansResponse.getTechniciansList();
        List<Technician> technicians = technicianGrpcs.stream()
                .map(TechnicianConverter::convert)
                .collect(Collectors.toList());

        System.out.println(technicians);

    }

    public void getCollectors() {
        GetCollectorsServiceGrpc.GetCollectorsServiceBlockingStub stub
                = GetCollectorsServiceGrpc.newBlockingStub(this.channel);

        GetAllCollectorsResponse getAllCollectorsResponse = stub.getAllCollectors(GetAllCollectorsRequest
                .newBuilder()
                .build());

        List<CollectorGrpc> collectorGrpcs = getAllCollectorsResponse.getCollectorsList();
        List<Collector> collectors = collectorGrpcs.stream()
                .map(CollectorConverter::convert)
                .collect(Collectors.toList());

        System.out.println(collectors);
    }
}
