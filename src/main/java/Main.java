import controllers.GlobalVariables;
import controllers.grpc.*;
import entities.employees.Manager;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.http.MediaType;

public class Main {
    private static final String splitter = "-----------------------------------------------------------------------" +
            "------------------------------------------------------------------------------------------------------";

    private static void print() {
        System.out.println(splitter);
    }

    public static void main(String[] args) {
        GlobalVariables.headers.setContentType(MediaType.APPLICATION_JSON);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("104.155.28.150", 6565)
                .usePlaintext()
                .build();

        //collector.createManager(manager);

        ProductLifecycle productLifecycle = new ProductLifecycle();

        print();

        /*for(int i = 0; i < 3; i++) {

            // Assembling
            Camera newCamera = productLifecycle.assembling();
            print();

            // Calibrating
            Camera calibratedCamera = productLifecycle.calibrating(newCamera);
            print();

            // Final stage
            productLifecycle.finalStage(calibratedCamera);
            print();

        }*/
    }
}
