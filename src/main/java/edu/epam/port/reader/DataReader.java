package edu.epam.port.reader;

import edu.epam.port.exception.DataReaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataReader {
    private static final Logger logger = LogManager.getLogger(DataReader.class);

    public List<String> readFromFile(String fileName) throws DataReaderException {
        List<String> stringList;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            String message;
            stringList = stream.collect(Collectors.toList());
            message = String.format("File %s was successfully read!", fileName);
            logger.info(message);
        } catch (IOException e) {
            throw new DataReaderException("Error while reading file!", e);
        }
        return stringList;
    }
}
