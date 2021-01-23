package edu.epam.port.entity;

import edu.epam.port.exception.PortException;

public interface ShipState {
    void requestPier(Ship ship);
    void moorToPier(Ship ship);
    void unloadCargo(Ship ship);
    void loadCargo(Ship ship);
    void departure(Ship ship);
}
