package util;

import model.Point;
import model.PointPodr;
import model.PointSum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MathUtils {

    /**
     * Finds podrs for points cumulativeSum
     *
     * @param pointSums points with cumulative, assumes that sequence is sorted by X
     * @return list of podrs
     */
    public static List<PointPodr> podr(List<PointSum> pointSums) {
        List<PointPodr> podrs = new ArrayList<>();
        Boolean isPrevDerPositive = null;
        for (int i = 1; i < pointSums.size() - 1; i++) {
            PointSum point = pointSums.get(i);
            double derivative = calculateFiniteDerivative(pointSums.get(i - 1), point, pointSums.get(i + 1));
            if (isPrevDerPositive != null && isPrevDerPositive && derivative < 0) {
                podrs.add(PointPodr.of(point, getPercOfTotal(pointSums, point)));
            }
            if (derivative > 0) {
                isPrevDerPositive = true;
            } else if (derivative < 0) {
                isPrevDerPositive = false;
            }
        }
        return podrs;
    }

    /**
     * Computes podr percent of total in cumulative sum sequence
     */
    private static double getPercOfTotal(List<PointSum> points, PointSum podr) {
        return podr.getCSum() / points.get(points.size() - 1).getCSum() * 100;
    }

    /**
     * Computes cumulative sum by Y
     *
     * @param points sequence of points
     * @return points extended with cumulative sum sequence
     */
    public static List<PointSum> cumulativeSumByY(List<Point> points) {
        BigDecimal sum = BigDecimal.ZERO;
        List<PointSum> cumulativeSum = new ArrayList<>();
        for (Point point : points) {
            sum = sum.add(BigDecimal.valueOf(point.getY()));
            cumulativeSum.add(PointSum.of(point, sum.doubleValue()));
        }
        return cumulativeSum;
    }

    /**
     * Computes central second finite derivative for cumulative sum
     *
     * @return second finite derivative
     */
    private static Double calculateFiniteDerivative(PointSum left, PointSum central, PointSum right) {
        BigDecimal centralX = BigDecimal.valueOf(central.getX());
        BigDecimal leftX = BigDecimal.valueOf(left.getX());
        BigDecimal rightX = BigDecimal.valueOf(right.getX());

        BigDecimal leftStep = centralX.subtract(leftX);
        BigDecimal rightStep = rightX.subtract(centralX);

        BigDecimal centralCSum = BigDecimal.valueOf(central.getCSum());
        BigDecimal leftCSum = BigDecimal.valueOf(left.getCSum());
        BigDecimal rightCSum = BigDecimal.valueOf(right.getCSum());

        BigDecimal leftDer = centralCSum.subtract(leftCSum).divide(leftStep, RoundingMode.HALF_UP);
        BigDecimal rightDer = rightCSum.subtract(centralCSum).divide(rightStep, RoundingMode.HALF_UP);

        return rightDer.subtract(leftDer)
                .divide(leftStep.add(rightStep), RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Merges all X duplicates adding their Y
     */
    public static List<Point> mergeXDuplicates(List<Point> points) {
        if (points.isEmpty()) {
            return points;
        }
        ArrayList<Point> merged = new ArrayList<>();
        Point prev = points.get(0);
        merged.add(prev);
        for (int i = 1; i < points.size(); i++) {
            Point point = points.get(i);
            if (points.get(i).getX() == prev.getX()) {
                double newY = BigDecimal.valueOf(point.getY()).add(BigDecimal.valueOf(prev.getY())).doubleValue();
                Point mergedPoint = Point.of(point.getX(), newY);
                merged.set(merged.size() - 1, mergedPoint);
                prev = mergedPoint;
            } else {
                merged.add(point);
                prev = point;
            }
        }
        return merged;
    }
}
