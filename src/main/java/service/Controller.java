package service;

import model.Point;
import model.PointPodr;
import model.PointSum;
import org.apache.commons.lang3.tuple.Triple;
import util.IOUtils;
import util.MathUtils;
import util.VisualUtils;

import java.util.Comparator;
import java.util.List;

public class Controller {

    /**
     * Processes input data
     */
    public static void process(String inputFilePath, String outputDirPath) {
        IOUtils.clearOutput(outputDirPath);
        IOUtils.readInput(inputFilePath).entrySet().stream()
                .map(entry -> processData(entry.getKey(), entry.getValue()))
                .forEach(triple -> setOutput(outputDirPath, triple.getLeft(), triple.getMiddle(), triple.getRight()));
    }

    private static Triple<String, List<PointSum>, List<PointPodr>> processData(String key, List<Point> points) {
        points.sort(Comparator.comparingDouble(Point::getX));
        List<Point> mergedPoints = MathUtils.mergeXDuplicates(points);
        List<PointSum> pointsWithSum = MathUtils.cumulativeSumByY(mergedPoints);
        List<PointPodr> podrs = MathUtils.podr(pointsWithSum);
        return Triple.of(key, pointsWithSum, podrs);
    }

    private static void setOutput(String dirPath, String key, List<PointSum> points, List<PointPodr> podrs) {
        VisualUtils.generateGraph(dirPath, key, points, podrs);
        IOUtils.appendOutput(dirPath, key, podrs);
    }
}