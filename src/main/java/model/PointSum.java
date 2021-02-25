package model;

import java.util.Objects;

public class PointSum extends Point {
    private final double cSum;

    protected PointSum(double x, double y, double cSum) {
        super(x, y);
        this.cSum = cSum;
    }

    public static PointSum of(double x, double y, double cSum) {
        return new PointSum(x, y, cSum);
    }

    public static PointSum of(Point point, double cSum) {
        return new PointSum(point.getX(), point.getY(), cSum);
    }

    public double getCSum() {
        return cSum;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", cSum=" + cSum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointSum pointSum = (PointSum) o;
        return Double.compare(pointSum.cSum, cSum) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cSum);
    }
}
