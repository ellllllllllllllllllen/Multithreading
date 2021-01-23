package edu.epam.port.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShipPort {
    private static final Logger LOGGER = LogManager.getLogger(ShipPort.class);
    private static final ShipPort instance = new ShipPort();
    private static final Warehouse warehouse = Warehouse.getInstance();
    private static final int PIERS_NUMBER = 5;
    private final Semaphore pierSemaphore = new Semaphore(PIERS_NUMBER, true);
    private final Lock lock = new ReentrantLock();
    private final Queue<Pier> freePiers;
    private final List<Pier> usedPiers;

    public static ShipPort getInstance() {
        return instance;
    }

    private ShipPort() {
        freePiers = new LinkedList<>();
        usedPiers = new ArrayList<>();
        for (int i = 1; i <= PIERS_NUMBER; i++) {
            Pier pier = new Pier(i);
            freePiers.add(pier);
        }
    }

    public Optional<Cargo> getContainer() {
        lock.lock();
        Optional<Cargo> optional = warehouse.getContainer();
        lock.unlock();
        return optional;
    }

    public void addContainer(Cargo cargo) {
        lock.lock();
        ShipPort.warehouse.addContainer(cargo);
        lock.unlock();
    }

    public int warehouseSize() {
        return warehouse.size();
    }

    public void requestPier(Ship ship){
        Pier pier;

        LOGGER.info(freePiers.size() + " piers available for ship № " + ship.getShipId() + " ,ships in queue: " +
                + pierSemaphore.getQueueLength());
        pierSemaphore.acquire();
        pier = freePiers.poll();
        usedPiers.add(pier);
        pier.setShip(ship);
        ship.setPierId(pier.getPierId());
        LOGGER.info("Ship № " + ship.getShipId() + " got a pier № " + pier.getPierId() + ", " + freePiers.size()
                + " piers available, ships in queue: " + pierSemaphore.getQueueLength());
    }

    public void leavePier(int pierId) {
        Optional<Pier> optionalPier =
                usedPiers.stream().filter(d -> d.getPierId() == pierId).findFirst();
        if (optionalPier.isPresent()) {
            Pier pier = optionalPier.get();
            Optional<Ship> optionalShip = pier.getShip();
            if (optionalShip.isPresent()) {
                Ship ship = optionalShip.get();
                int shipId = ship.getShipId();
                pier.removeShip();
                ship.resetPierId();
                if (usedPiers.remove(pier)) {
                    freePiers.offer(pier);
                    LOGGER.info("Ship № " + shipId + " left pier number: " + pier.getPierId());
                    pierSemaphore.release();
                }
            }
        }
    }
}
