package edu.epam.port.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Warehouse {
    private static final Logger LOGGER = LogManager.getLogger(Warehouse.class);
    private static final int MAX_CAPACITY = 50;
    private static final Warehouse INSTANCE = new Warehouse();
    private final List<Cargo> cargos = new ArrayList<>();

    public static Warehouse getInstance() {
        return INSTANCE;
    }

    public Optional<Cargo> getContainer() {
        Optional<Cargo> optional = Optional.empty();
        if (!cargos.isEmpty()) {
            optional = Optional.of(cargos.remove(0));
        }
        return optional;
    }

    public void addContainer(Cargo cargo) {
        if (cargos.size() < MAX_CAPACITY) {
            cargos.add(cargo);
        } else {
            LOGGER.warn("Unable to add cargo, warehouse is full!");
        }
    }

    public int size() {
        return cargos.size();
    }
}
