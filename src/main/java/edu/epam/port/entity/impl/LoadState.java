package edu.epam.port.entity.impl;

import edu.epam.port.entity.Cargo;
import edu.epam.port.entity.Ship;
import edu.epam.port.entity.ShipPort;
import edu.epam.port.entity.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.TimeUnit;


public class LoadState implements ShipState {
    private static final Logger logger = LogManager.getLogger(LoadState.class);
    private static final LoadState INSTANCE = new LoadState();
    private static final ShipPort PORT = ShipPort.getInstance();
    private static final int LOAD_NUMBER = 5;
    private static final int LOAD_DURATION = 2;

    private LoadState() {
    }

    public static LoadState getInstance() {
        return INSTANCE;
    }

    @Override
    public void requestPier(Ship ship) {
        throw new UnsupportedOperationException("Pier was already requested.");
    }

    @Override
    public void moorToPier(Ship ship) {
        throw new UnsupportedOperationException("Ship № " + ship.getShipId() + " cannot to moor to pier at the load stage.");
    }

    @Override
    public void unloadCargo(Ship ship) {
        while (ship.getSize() != 0) {
            Optional<Cargo> optional = ship.unloadCargo(0);
            if (optional.isPresent()) {
                Cargo cargo = optional.get();
                PORT.addContainer(cargo);
                logger.info("Cargo № " + cargo.getCargoId() + " was moved from ship № " + ship.getShipId()
                        + " to warehouse, warehouse size is " + PORT.warehouseSize());
                TimeUnit.SECONDS.sleep(LOAD_DURATION);
            }
        }
    }

    @Override
    public void loadCargo(Ship ship) {
        for (int i = 0; i < LOAD_NUMBER; i++) {
            Optional<Cargo> optional = PORT.getContainer();
            if (optional.isPresent()) {
                Cargo cargo = optional.get();
                ship.loadCargo(cargo);
                logger.info("Cargo № " + cargo.getCargoId() + " was moved from warehouse to ship № " + ship.getShipId() +
                                " warehouse size is " + PORT.warehouseSize());
                TimeUnit.SECONDS.sleep(LOAD_DURATION);
            }
        }
        ship.setCurrentState(DepartureState.getInstance());
    }

    @Override
    public void departure(Ship ship) {
        throw new UnsupportedOperationException("Ship № " + ship.getShipId() + " cannot to leave the pier at the load stage.");
    }
}
