package edu.epam.port.entity.impl;

import edu.epam.port.entity.Ship;
import edu.epam.port.entity.ShipPort;
import edu.epam.port.entity.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class DepartureState implements ShipState {
    private static final Logger logger = LogManager.getLogger(DepartureState.class);
    private static final DepartureState INSTANCE = new DepartureState();
    private static final ShipPort SHIP_PORT = ShipPort.getInstance();
    private static final int LEAVING_DURATION = 5;

    private DepartureState() {
    }

    public static DepartureState getInstance() {
        return INSTANCE;
    }

    @Override
    public void requestPier(Ship ship) {
        throw new UnsupportedOperationException("Ship № " + ship.getShipId() + " cannot request the pier at the departure stage.");
    }

    @Override
    public void moorToPier(Ship ship) {
        throw new UnsupportedOperationException("Ship № " + ship.getShipId() + " cannot to moor to the pier at the departure stage.");
    }

    @Override
    public void unloadCargo(Ship ship) {
        throw new UnsupportedOperationException("Unable to unload from the ship № " + ship.getShipId() + " at the departure stage.");
    }

    @Override
    public void loadCargo(Ship ship) {
        throw new UnsupportedOperationException("Unable to load to the ship № " + ship.getShipId() + " at the departure stage.");
    }

    @Override
    public void departure(Ship ship) {
        logger.info("Ship № " + ship.getShipId() + " is leaving the pier № " + ship.getPierId());
        int pierId = ship.getPierId();
        TimeUnit.SECONDS.sleep(LEAVING_DURATION);
        logger.info("Ship № " + ship.getShipId() + " has left the pier № " + ship.getPierId());
        SHIP_PORT.leavePier(pierId);
    }
}
