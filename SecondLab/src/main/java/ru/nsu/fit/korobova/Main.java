package ru.nsu.fit.korobova;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {
    static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args){
        PropertyConfigurator.configure(Main.class.getClassLoader().getResource("log4j2.properties"));
        logger.info("application start");
        try{
            OsmParser osmParser = new OsmParser();
            MapStatistics mapStatistics = osmParser.getStatistics("RU-NVS.osm.bz2");
            mapStatistics.printStatistics();
            logger.info("statistics printed");
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
