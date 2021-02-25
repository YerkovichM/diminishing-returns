package util;

import model.PointPodr;
import model.PointSum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class VisualUtilsTest {

    private static final String TEST_OUTPUT_DIR = "src/test/test-data";
    private static final String TEST_OUTPUT_PLOT_FILE = "/key-graph.jpeg";

    @Test
    void generatePlot() throws IOException {
        Path filePath = Path.of(TEST_OUTPUT_DIR + TEST_OUTPUT_PLOT_FILE);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
        List<PointPodr> podrs = Stream.iterate(0, i -> i < 10, i -> i + 1)
                .map(i -> PointPodr.of(i, i, i, i))
                .collect(Collectors.toList());
        List<PointSum> sums = Stream.iterate(0, i -> i < 10, i -> i + 1)
                .map(i -> PointPodr.of(i, i, i, i))
                .collect(Collectors.toList());
        VisualUtils.generateGraph(TEST_OUTPUT_DIR, "key", sums, podrs);
        Assertions.assertTrue(Files.exists(filePath));
        Files.delete(filePath);
    }
}