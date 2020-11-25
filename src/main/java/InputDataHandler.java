import controllers.grpc.EmployeeControllerGrpc;
import controllers.grpc.MachineControllerGrpc;
import controllers.rest.models.EmployeeController;
import controllers.rest.models.MachineController;
import entities.camera.Dimensions;
import entities.camera.LensType;
import entities.employees.Collector;
import entities.employees.Employee;
import entities.employees.Manager;
import entities.employees.Technician;
import entities.machines.Calibrator;
import entities.machines.Machine;
import entities.machines.Packer;
import io.grpc.ManagedChannel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class InputDataHandler {
    private final DataForTesting dataForTesting;
    private final Random rand = new Random();
    private final ManagedChannel channel;

    public InputDataHandler(ManagedChannel channel) {
        this.channel = channel;
        this.dataForTesting = new DataForTesting();
        initializeEntities(dataForTesting);
    }

    public List<String> getListFromFile(String fileName) {
        File file = new File(fileName);
        List<String> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String currentLine;
            while((currentLine = br.readLine()) != null) {
                list.add(currentLine);
            }
            return list;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getNameSurnamePhone(DataForTesting dataForTesting) {
        String name = getRandomFromList(dataForTesting.names);
        String surname = getRandomFromList(dataForTesting.surnames);
        String phone = getRandomFromList(dataForTesting.phones);

        return new ArrayList<>(Arrays.asList(name, surname, phone));
    }

    public String getRandomColor() {
        return this.getRandomFromList(this.dataForTesting.colors);
    }

    public LensType getRandomLens() {
        return this.getRandomFromList(Arrays.asList(LensType.values()));
    }

    public Dimensions getRandomDimensions() {
        // From 16 to 26 (standard, described in tester.checkDimensions: 25)
        Integer width = 16 + rand.nextInt(10);

        // From 11 to 21 (standard: 20)
        Integer length = 11 + rand.nextInt(10);

        // From 6 to 16 (standard: 15)
        Integer depth = 6 + rand.nextInt(10);

        return new Dimensions(width, length, depth);
    }

    public <T> T getRandomFromList(List<T> list) {
        return list.get(rand.nextInt(list.size()));
    }

    public void initializeEntities(DataForTesting dataForTesting){
        EmployeeController employeeController = new EmployeeController();
        MachineController machineController = new MachineController();

        EmployeeControllerGrpc employeeControllerGrpc = new EmployeeControllerGrpc(this.channel);
        MachineControllerGrpc machineControllerGrpc = new MachineControllerGrpc(this.channel);
        long startTime;
        long endTime;

        // Managers initialization
        for(int i = 0; i < 5; i++) {
            List<String> nameSurnamePhone = getNameSurnamePhone(dataForTesting);
            Employee manager = null;
            if (i % 2 == 0) {
                startTime = System.currentTimeMillis();
                manager = employeeController
                        .createManager(nameSurnamePhone.get(0), nameSurnamePhone.get(1), nameSurnamePhone.get(2));
                endTime = System.currentTimeMillis();

                System.out.println("[REST] (" + (endTime - startTime) + " ms) CREATED " + manager.toString());
            } else {
                employeeControllerGrpc.createManager(new Manager(nameSurnamePhone.get(0),
                        nameSurnamePhone.get(1), nameSurnamePhone.get(2)));
            }

        }

        // Collectors and technicians initialization
        for(int i = 0; i < 10; i++) {
            List<String> nameSurnamePhone = getNameSurnamePhone(dataForTesting);
            Employee collector;
            Employee technician;

            if(i % 2 == 0) {
                startTime = System.currentTimeMillis();
                collector = employeeController
                        .createCollector(nameSurnamePhone.get(0), nameSurnamePhone.get(1), nameSurnamePhone.get(2));
                endTime = System.currentTimeMillis();

                nameSurnamePhone = getNameSurnamePhone(dataForTesting);
                technician = employeeController
                        .createTechnician(nameSurnamePhone.get(0), nameSurnamePhone.get(1), nameSurnamePhone.get(2));

                System.out.println("[REST] (" + (endTime - startTime) + " ms) CREATED " + collector.toString());
                System.out.println("[REST] (" + (endTime - startTime) + " ms) CREATED " + technician.toString());
            } else {
                employeeControllerGrpc.createCollector(new Collector(nameSurnamePhone.get(0),
                        nameSurnamePhone.get(1), nameSurnamePhone.get(2)));
                employeeControllerGrpc.createTechnician(new Technician(nameSurnamePhone.get(0),
                        nameSurnamePhone.get(1), nameSurnamePhone.get(2)));
            }
        }

        // Machines initialization
        for(int i = 0; i < 5; ++i) {

            if (i % 2 == 0) {
                startTime = System.currentTimeMillis();
                Machine calibrator = machineController.createCalibrator(getRandomFromList(dataForTesting.robots));
                endTime = System.currentTimeMillis();

                System.out.println("[REST] (" + (endTime - startTime) + " ms) CREATED " + calibrator.toString());

                startTime = System.currentTimeMillis();
                Machine packer = machineController.createPacker(getRandomFromList(dataForTesting.robots));
                endTime = System.currentTimeMillis();

                System.out.println("[REST] (" + (endTime - startTime) + " ms) CREATED " + packer.toString());
            } else {
                machineControllerGrpc.createCalibrator(new Calibrator(getRandomFromList(dataForTesting.robots)));
                machineControllerGrpc.createPacker(new Packer(getRandomFromList(dataForTesting.robots)));
            }
        }
    }

    public List<String> getPhones() {
        List<String> listNumbers = new ArrayList<>();
        for(int i = 0; i < 100; ++i) {
            int n1 = rand.nextInt(7) + 1;
            int n2 = rand.nextInt(8);
            int n3 = rand.nextInt(8);
            int set2 = rand.nextInt(899) + 100;
            int set3 = rand.nextInt(8999) + 1000;
            listNumbers.add("(" + n1 + n2 + n3 + ")" + "-" + set2 + "-" + set3);
        }
        return listNumbers;
    }

    public final class DataForTesting {
        final List<String> names;
        final List<String> surnames;
        final List<String> colors;
        final List<String> robots;
        final List<String> phones;

        DataForTesting() {
            this.names = getListFromFile("./src/main/java/files/Names.txt");
            this.surnames = getListFromFile("./src/main/java/files/Surnames.txt");
            this.colors = getListFromFile("./src/main/java/files/Colors.txt");
            this.robots = getListFromFile("./src/main/java/files/Robot companies.txt");
            this.phones = getPhones();
        }
    }
}
