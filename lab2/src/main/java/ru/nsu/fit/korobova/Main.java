package ru.nsu.fit.korobova;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ru.nsu.fit.korobova.db.Database;
import ru.nsu.fit.korobova.generatedSheme.xjc.Osm;
import ru.nsu.fit.korobova.services.NodeService;
import ru.nsu.fit.korobova.services.OsmParser;

public class Main {
    static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args){
        PropertyConfigurator.configure(Main.class.getClassLoader().getResource("log4j2.properties"));
        logger.info("application start");
        try{
            OsmParser osmParser = new OsmParser();
            Osm osm = osmParser.getOsm("RU-NVS.osm.bz2");
            Database database = new Database();
            NodeService nodeService = new NodeService(database);
            nodeService.addNodesByBatch(osm.getNode());
            database.Dispose();
            logger.info("statistics printed");
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
