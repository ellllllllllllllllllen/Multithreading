package edu.epam.port.parser;

import edu.epam.port.entity.Cargo;
import edu.epam.port.entity.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Parser {
    private static final String REGEX_SHIP = "(?<shipId>\\d+)\\s(?<shipCapacity>\\d+):\\s(?<cargos>.+)";
    private static final String REGEX_CONTAINER = "(?<cargoId>\\d+)\\s(?<cargoSizet>\\d+.\\d+);?";
    public static final String SHIP_ID = "shipId";
    public static final String SHIP_CAPACITY = "shipCapacity";
    public static final String CONTAINERS = "cargos";
    public static final String CONTAINER_ID = "cargoId";
    public static final String CONTAINER_WEIGHT = "cargoSize";

    public List<Ship> parse(List<String> lines) {
        return lines.stream().map(this::parse).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Ship parse(String line) {
        Pattern pattern = Pattern.compile(REGEX_SHIP);
        Matcher matcher = pattern.matcher(line);
        Ship ship = null;
        if (matcher.find()) {
            String stringShipId = matcher.group(SHIP_ID);
            int shipId = Integer.parseInt(stringShipId);
            String stringShipCapacity = matcher.group(SHIP_CAPACITY);
            int shipCapacity = Integer.parseInt(stringShipCapacity);
            String stringContainers = matcher.group(CONTAINERS);
            List<Cargo> cargos = parseContainers(stringContainers);
            ship = new Ship(shipId, shipCapacity, cargos);
        }
        return ship;
    }

    private List<Cargo> parseContainers(String data) {
        List<Cargo> cargos = new ArrayList<>();
        Pattern pattern = Pattern.compile(REGEX_CONTAINER);
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            String stringContainerId = matcher.group(CONTAINER_ID);
            int containerId = Integer.parseInt(stringContainerId);
            String stringContainerWeight = matcher.group(CONTAINER_WEIGHT);
            double containerWeight = Double.parseDouble(stringContainerWeight);
            Cargo cargo = new Cargo(containerId, containerWeight);
            cargos.add(cargo);
        }
        return cargos;
    }
}
