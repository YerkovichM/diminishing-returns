import org.apache.commons.lang3.StringUtils;
import service.Controller;

public class Main {
    public static final String FIRST_PARAM_ERROR_MESSAGE = "Program should run with parameter, input csv file path(ex. \"/tmp/input.csv\")";
    public static final String SECOND_PARAM_ERROR_MESSAGE = "Program should run with 2 parameters, 2nd is missing, that is output dir path(ex. \"/tmp\")";

    public static void main(String[] args) {
        Controller.process(getInputFilePath(args), getOutputDirPath(args));
    }

    private static String getInputFilePath(String[] args) {
        if (args.length < 1 || StringUtils.isBlank(args[0])) {
            throw new RuntimeException(FIRST_PARAM_ERROR_MESSAGE);
        }
        return args[0];
    }

    private static String getOutputDirPath(String[] args) {
        if (args.length < 2 || StringUtils.isBlank(args[1])) {
            throw new RuntimeException(SECOND_PARAM_ERROR_MESSAGE);
        }
        return args[1];
    }
}
