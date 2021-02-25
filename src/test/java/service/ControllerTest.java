package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private static final String TEST_INPUT_FILE = "src/test/test-data/test-input.csv";
    private static final String TEST_OUTPUT_DIR = "src/test/test-data";
    private static final String TEST_OUTPUT_FILE = "src/test/test-data/result.csv";
    private static final String TEST_PREPARED_FILE = "src/test/test-data/test-result.csv";
    private static final String TEST_GRAPH_1_FILE = "src/test/test-data/82353292333796-graph.jpeg";
    private static final String TEST_GRAPH_2_FILE = "src/test/test-data/833385348473598-graph.jpeg";


    @Test
    void process() throws IOException {
        Controller.process(TEST_INPUT_FILE, TEST_OUTPUT_DIR);
        Path graphPath1 = Path.of(TEST_GRAPH_1_FILE);
        Path graphPath2 = Path.of(TEST_GRAPH_2_FILE);
        Path resultPath = Path.of(TEST_OUTPUT_FILE);
        Assertions.assertTrue(Files.exists(graphPath1));
        Assertions.assertTrue(Files.exists(graphPath2));
        Assertions.assertTrue(Files.exists(resultPath));
        Path preparedResultPath = Path.of(TEST_PREPARED_FILE);
        List<String> actual = Files.readAllLines(resultPath);
        List<String> expected = Files.readAllLines(preparedResultPath);
        Assertions.assertEquals(expected, actual);
        Files.delete(graphPath1);
        Files.delete(graphPath2);
        Files.delete(resultPath);
    }
}