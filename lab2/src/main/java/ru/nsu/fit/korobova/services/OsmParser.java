package ru.nsu.fit.korobova.services;

import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import ru.nsu.fit.korobova.generatedSheme.xjc.Osm;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;

public class OsmParser {

    private final MapStatistics mapStatistics = new MapStatistics();
    private final XMLInputFactory xmlFactory = XMLInputFactory.newFactory();
    private static final String NODE_NAME = "node";
    private static final String TAG_NAME = "tag";
    private static final QName USER_NAME = QName.valueOf("user");
    private static final QName KEY_NAME = QName.valueOf("k");

    public Osm getOsm(String fileName) {
        try {
            BufferedReader reader = getReaderByFilePath(fileName);
            XMLStreamReader xmlEventReader = xmlFactory.createXMLStreamReader(reader);

            JAXBContext jc = JAXBContext.newInstance(Osm.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            Osm osm = (Osm) unmarshaller.unmarshal(xmlEventReader);
            return osm;
        } catch (XMLStreamException | IOException | JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BufferedReader getReaderByFilePath(String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        CompressorInputStream compressorInputStream = new BZip2CompressorInputStream(bufferedInputStream);
        return new BufferedReader(new InputStreamReader(compressorInputStream));
    }
}
