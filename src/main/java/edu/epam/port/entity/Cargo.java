package edu.epam.port.entity;

public class Cargo {
    private final long cargoId;
    private final double size;

    public Cargo(long cargoId, double size) {
        this.cargoId = cargoId;
        this.size = size;
    }

    public long getCargoId() {
        return cargoId;
    }

    public double getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cargo cargo = (Cargo) o;

        if (cargoId != cargo.cargoId) return false;
        return Double.compare(cargo.size, size) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (cargoId ^ (cargoId >>> 32));
        temp = Double.doubleToLongBits(size);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
