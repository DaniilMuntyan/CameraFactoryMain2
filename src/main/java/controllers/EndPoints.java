package controllers;

public final class EndPoints {
    private static final String URL = "http://34.77.177.79:8080"; // IP LoadBalancer'а удаленного кластера gcp

    public static final String ASSEMBLE_CAMERA_BACK = URL + "/api/assembling/camera_back";
    public static final String ASSEMBLE_CAMERA_BODY = URL + "/api/assembling/camera_body";
    public static final String ASSEMBLE_CAMERA_LENS = URL + "/api/assembling/camera_lens";
    public static final String ASSEMBLE_CAMERA = URL + "/api/assembling/camera";

    public static final String CALIBRATE_CAMERA = URL + "/api/calibrating/calibrate";

    public static final String FINAL_STAGE = URL + "/api/final_stage";

    public static final String CREATE_COLLECTOR = URL + "/api/assembling/collectors";
    public static final String CREATE_MANAGER = URL + "/api/final_stage/managers";
    public static final String CREATE_TECHNICIAN = URL + "/api/final_stage/technicians";

    public static final String CREATE_CALIBRATOR = URL + "/api/calibrating/calibrators";
    public static final String CREATE_PACKER = URL + "/api/final_stage/packers";

    public static final String GET_ALL_MANAGERS = URL + "/api/final_stage/managers";
    public static final String GET_ALL_COLLECTORS = URL + "/api/assembling/collectors";
    public static final String GET_ALL_TECHNICIANS = URL + "/api/final_stage/technicians";

    public static final String GET_ALL_CALIBRATORS = URL + "/api/calibrating/calibrators";
    public static final String GET_ALL_PACKERS = URL + "/api/final_stage/packers";
}
