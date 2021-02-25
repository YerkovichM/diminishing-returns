package util;

import model.Point;
import model.PointPodr;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class IOUtilsTest {

    private static final String TEST_INPUT_FILE = "src/test/test-data/test-input.csv";
    private static final String TEST_OUTPUT_DIR = "src/test/test-data";
    private static final String TEST_OUTPUT_FILE = "src/test/test-data/result.csv";

    @Test
    void readInput() {
        Map<String, List<Point>> input = IOUtils.readInput(TEST_INPUT_FILE);
        Assertions.assertEquals(2, input.size());
        Assertions.assertEquals(252, input.get("82353292333796").size());
        Assertions.assertEquals(45, input.get("833385348473598").size());
        input.keySet()
                .forEach(Assertions::assertNotNull);
        input.values().stream()
                .flatMap(Collection::stream)
                .forEach(Assertions::assertNotNull);
    }

    @Test
    void appendOutput() throws IOException {
        Path filePath = Path.of(TEST_OUTPUT_FILE);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
        List<PointPodr> data = Stream.iterate(0, i -> i < 10, i -> i + 1)
                .map(i -> PointPodr.of(i, i, i, i))
                .collect(Collectors.toList());
        IOUtils.appendOutput(TEST_OUTPUT_DIR, "key", data);
        long count = Files.lines(filePath)
                .peek(s -> Assertions.assertTrue(StringUtils.isNotBlank(s)))
                .flatMap(s -> Stream.of(s.split(",")))
                .peek(Assertions::assertNotNull)
                .count();
        Assertions.assertEquals(40, count);
        Files.delete(filePath);
    }

    @Test
    void clearOutput() throws IOException {
        Path filePath = Path.of(TEST_OUTPUT_FILE);
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        IOUtils.clearOutput(TEST_OUTPUT_DIR);
        Assertions.assertFalse(Files.exists(filePath));
    }
}