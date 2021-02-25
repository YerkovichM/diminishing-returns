package util;

import model.Point;
import model.PointPodr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.APPEND;

public class IOUtils {

    private static final String OUTPUT_FILE_PATTERN = "%s/result.csv";
    private static final String OUTPUT_LINE_PATTERN = "%s,%.2f,%.2f,%.2f";

    public static Map<String, List<Point>> readInput(String inputFilePath) {
        try {
            return Files.lines(Path.of(inputFilePath))
                    .map(s -> s.split(","))
                    .collect(Collectors.groupingBy(ar -> ar[0].trim(),
                            Collectors.mapping(ar -> Point.of(Double.parseDouble(ar[1]), Double.parseDouble(ar[2])),
                                    Collectors.toList())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Map.of();
    }

    public static void appendOutput(String dirPath, String key, List<PointPodr> podrs) {
        try {
            List<String> lines = podrs.stream()
                    .map(point -> String.format(Locale.US, OUTPUT_LINE_PATTERN, key, point.getX(), point.getY(), point.getPercOfTotal()))
                    .collect(Collectors.toList());
            Path filePath = Path.of(String.format(OUTPUT_FILE_PATTERN, dirPath));
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            Files.write(filePath, lines, APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearOutput(String dirPath) {
        try {
            Path filePath = Path.of(String.format(OUTPUT_FILE_PATTERN, dirPath));
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
