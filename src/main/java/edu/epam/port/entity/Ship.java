package edu.epam.port.entity;

import edu.epam.port.entity.impl.ArriveState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ship extends Thread {
    private static final Logger logger = LogManager.getLogger(Ship.class);
    private static final int RESET_PIER_ID = -1;
    private final int shipId;
    private final int capacity;
    private int pierId;
    private List<Cargo> cargos = new ArrayList<>();
    private ShipState shipState;

    public Ship(int shipId, int capacity, List<Cargo> cargos) {
        this.shipId = shipId;
        this.capacity = capacity;
        this.cargos = cargos;
        pierId = RESET_PIER_ID;
        shipState = ArriveState.getInstance();
    }

    public Ship(int shipId, int capacity) {
        this.shipId = shipId;
        this.capacity = capacity;
        pierId = RESET_PIER_ID;
        shipState = ArriveState.getInstance();
    }

    public ShipState getCurrentState() {
        return shipState;
    }

    public void setCurrentState(ShipState currentState) {
        this.shipState = currentState;
    }

    public int getShipId() {
        return shipId;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPierId() {
        return pierId;
    }

    public void setPierId(int pierId) {
        this.pierId = pierId;
    }

    public void resetPierId() {
        pierId = RESET_PIER_ID;
    }

    public int getSize() {
        return cargos.size();
    }

    public void loadCargo(Cargo cargo) {
        if ((cargos.size() < capacity)) {
            cargos.add(cargo);
        } else {
            logger.error("Cargo loading error, capacity is full");
        }
    }

    public Optional<Cargo> unloadCargo(int containerId) {
        Cargo cargo = cargos.remove(containerId);
        return cargo == null ? Optional.empty() : Optional.of(cargo);
    }

    @Override
    public void run() {
        shipState.requestPier(this);
        shipState.moorToPier(this);
        shipState.unloadCargo(this);
        shipState.loadCargo(this);
        shipState.departure(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (shipId != ship.shipId) return false;
        if (capacity != ship.capacity) return false;
        if (pierId != ship.pierId) return false;
        if (!cargos.equals(ship.cargos)) return false;
        return shipState.equals(ship.shipState);
    }

    @Override
    public int hashCode() {
        int result = shipId;
        result = 31 * result + capacity;
        result = 31 * result + pierId;
        result = 31 * result + cargos.hashCode();
        result = 31 * result + shipState.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ship{");
        sb.append("shipId=").append(shipId);
        sb.append(", capacity=").append(capacity);
        sb.append(", pierId=").append(pierId);
        sb.append(", cargos=").append(cargos);
        sb.append(", shipState=").append(shipState);
        sb.append('}');
        return sb.toString();
    }
}
