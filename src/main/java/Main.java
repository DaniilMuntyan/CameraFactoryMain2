import controllers.EndPoints;
import controllers.GlobalVariables;
import controllers.grpc.*;
import entities.camera.Camera;
import entities.employees.Manager;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import kpi.trspo.restapp.CameraGrpc;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final String splitter = "-----------------------------------------------------------------------" +
            "------------------------------------------------------------------------------------------------------";

    private static void print() {
        System.out.println(splitter);
    }

    public static void main(String[] args) {
        GlobalVariables.headers.setContentType(MediaType.APPLICATION_JSON);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(EndPoints.IP, EndPoints.GRPC_PORT)
                .usePlaintext()
                .build();

        ProductLifecycle productLifecycle = new ProductLifecycle(channel);
        print();

        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                // Assembling
                Camera newCamera = productLifecycle.assembling(false);
                print();

                // Calibrating
                Camera calibratedCamera = productLifecycle.calibrating(newCamera, false);
                print();

                // Final stage
                productLifecycle.finalStage(calibratedCamera, false);
                print();
            } else {
                Camera newCamera = productLifecycle.assembling(true);
                print();

                Camera calibratedCamera = productLifecycle.calibrating(newCamera, true);
                print();

                productLifecycle.finalStage(calibratedCamera, true);
                print();
            }

        }
    }
}
