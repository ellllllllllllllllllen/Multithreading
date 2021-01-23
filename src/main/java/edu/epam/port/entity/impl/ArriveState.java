package edu.epam.port.entity.impl;

import edu.epam.port.entity.ShipPort;
import edu.epam.port.entity.Ship;
import edu.epam.port.entity.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArriveState implements ShipState {
    private static final Logger logger = LogManager.getLogger(ArriveState.class);
    private static final ArriveState INSTANCE = new ArriveState();
    private static final ShipPort SHIP_PORT = ShipPort.getInstance();

    private ArriveState() {
    }

    public static ArriveState getInstance() {
        return INSTANCE;
    }

    @Override
    public void requestPier(Ship ship){
        SHIP_PORT.requestPier(ship);
        ship.setCurrentState(MooringState.getInstance());
    }

    @Override
    public void moorToPier(Ship ship) {
        throw new UnsupportedOperationException("Ship № " + ship.getShipId() + " cannot to moor to the pier at the arrival stage.");
    }

    @Override
    public void unloadCargo(Ship ship) {
        throw new UnsupportedOperationException("Unable to unload from the ship № " + ship.getShipId() + " at the arrival stage.");
    }

    @Override
    public void loadCargo(Ship ship) {
        throw new UnsupportedOperationException("Unable to load to the ship № " + ship.getShipId() + " at the arrival stage.");
    }

    @Override
    public void departure(Ship ship) {
        throw new UnsupportedOperationException("Ship № " + ship.getShipId() + " cannot to leave the pier at the arrival stage.");
    }
}
