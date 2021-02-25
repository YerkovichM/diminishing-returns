package util;

import model.Point;
import model.PointPodr;
import model.PointSum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MathUtilsTest {

    @Test
    void podr() {
        List<PointPodr> podr = MathUtils.podr(getPoints());
        Assertions.assertEquals(2, podr.size());
        Assertions.assertEquals(PointPodr.of(0.1, 0.02, 0.1, 83.33333333333334), podr.get(0));
        Assertions.assertEquals(PointPodr.of(0.12, 0.01, 0.12, 100.0), podr.get(1));
    }

    @Test
    void mergeXDuplicates(){
        List<Point> points = List.of(Point.of(0.1, 1),
                Point.of(0.1, 2),
                Point.of(0.1, 3),
                Point.of(0.2, 4),
                Point.of(0.2, 5));
        List<Point> merged = MathUtils.mergeXDuplicates(points);
        Assertions.assertEquals(2, merged.size());
        Assertions.assertEquals(Point.of(0.1, 6), merged.get(0));
        Assertions.assertEquals(Point.of(0.2, 9), merged.get(1));
    }

    @Test
    void cumulativeSumByY() {
        List<Point> points = Stream.iterate(0, i -> i < 10, i -> i + 1)
                .map(i -> Point.of(i, i))
                .collect(Collectors.toList());
        List<PointSum> sums = MathUtils.cumulativeSumByY(points);
        double sum = sums.stream()
                .peek(Assertions::assertNotNull)
                .peek(point -> Assertions.assertEquals(point.getX(), point.getY()))
                .mapToDouble(PointSum::getCSum)
                .sum();
        Assertions.assertEquals(165.0, sum);
        Assertions.assertEquals(45.0, sums.get(sums.size() - 1).getCSum());
    }

    private List<PointSum> getPoints() {
        return List.of(
                PointSum.of(0.00, 0.00, 0.00),
                PointSum.of(0.01, 0.01, 0.01),
                PointSum.of(0.03, 0.02, 0.03),
                PointSum.of(0.05, 0.00, 0.03),
                PointSum.of(0.08, 0.03, 0.06),
                PointSum.of(0.09, 0.02, 0.08),
                PointSum.of(0.10, 0.02, 0.10),
                PointSum.of(0.11, 0.00, 0.10),
                PointSum.of(0.12, 0.01, 0.11),
                PointSum.of(0.13, 0.01, 0.12),
                PointSum.of(0.14, 0.00, 0.12));
    }
}