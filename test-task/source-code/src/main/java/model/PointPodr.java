package model;

import java.util.Objects;

public class PointPodr extends PointSum {
    private final double percOfTotal;

    protected PointPodr(double x, double y, double cSum, double percOfTotal) {
        super(x, y, cSum);
        this.percOfTotal = percOfTotal;
    }

    public static PointPodr of(double x, double y, double cSum, double percOfTotal) {
        return new PointPodr(x, y, cSum, percOfTotal);
    }

    public static PointPodr of(PointSum sum, double percOfTotal) {
        return new PointPodr(sum.getX(), sum.getY(), sum.getCSum(), percOfTotal);
    }

    public double getPercOfTotal() {
        return percOfTotal;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", cSum=" + getCSum() +
                ", percOfTotal=" + percOfTotal +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PointPodr pointPodr = (PointPodr) o;
        return Double.compare(pointPodr.percOfTotal, percOfTotal) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), percOfTotal);
    }
}
