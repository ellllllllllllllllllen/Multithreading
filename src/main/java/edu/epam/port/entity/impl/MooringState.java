package edu.epam.port.entity.impl;

import edu.epam.port.entity.Ship;
import edu.epam.port.entity.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class MooringState implements ShipState {
    private static final Logger logger = LogManager.getLogger(MooringState.class);
    private static final MooringState INSTANCE = new MooringState();
    private static final int MOORING_DURATION = 4;

    private MooringState() {
    }

    public static MooringState getInstance() {
        return INSTANCE;
    }

    @Override
    public void requestPier(Ship ship) {
        throw new UnsupportedOperationException("Pier was already requested.");
    }

    @Override
    public void moorToPier(Ship ship) {
        logger.info("Ship № " + ship.getShipId() + " is mooring to pier № " + ship.getPierId());
        TimeUnit.SECONDS.sleep(MOORING_DURATION);
        logger.info("Ship № " + ship.getShipId() + " arrived to pier № " + ship.getPierId());
        ship.setCurrentState(LoadState.getInstance());
    }

    @Override
    public void unloadCargo(Ship ship) {
        throw new UnsupportedOperationException("Unable to unload from the ship № " + ship.getShipId() + " at the mooring stage.");
    }

    @Override
    public void loadCargo(Ship ship) {
        throw new UnsupportedOperationException("Unable to load to the ship № " + ship.getShipId() + " at the mooring stage.");
    }

    @Override
    public void departure(Ship ship) {
        throw new UnsupportedOperationException("Unable to leave the pier at the mooring stage.");
    }
}
