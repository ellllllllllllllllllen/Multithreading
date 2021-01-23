package edu.epam.port.entity;

import java.util.Optional;

public class Pier {
    private final int pierId;
    private Ship ship;

    public Pier(int pierId) {
        this.pierId = pierId;
    }

    public int getPierId() {
        return pierId;
    }

    public Optional<Ship> getShip() {
        return ship == null
                ? Optional.empty()
                : Optional.of(ship);
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void removeShip() {
        ship = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pier pier = (Pier) o;
        if (pierId != pier.pierId) {
            return false;
        }
        return ship != null ? ship.equals(pier.ship) : pier.ship == null;
    }

    @Override
    public int hashCode() {
        int result = pierId;
        result = 31 * result + (ship != null ? ship.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pier{");
        sb.append("pierId=").append(pierId);
        sb.append(", ship=").append(ship);
        sb.append('}');
        return sb.toString();
    }
}
