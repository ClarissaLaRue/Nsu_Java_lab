package ru.nsu.fit.korobova;

import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
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

    MapStatistics getStatistics(String fileName) {
        try {
            BufferedReader reader = getReaderByFilePath(fileName);
            XMLEventReader xmlEventReader = xmlFactory.createXMLEventReader(reader);
            while (xmlEventReader.hasNext()) {
                XMLEvent event = xmlEventReader.nextEvent();
                if (!event.isStartElement()) {
                    continue;
                }
                StartElement startElement = event.asStartElement();
                processElement(startElement);
            }
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
        return mapStatistics;
    }

    private BufferedReader getReaderByFilePath(String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        CompressorInputStream compressorInputStream = new BZip2CompressorInputStream(bufferedInputStream);
        return new BufferedReader(new InputStreamReader(compressorInputStream));
    }

    private void processElement(StartElement startElement) {
        if (NODE_NAME.equals(startElement.getName().getLocalPart())) {
            Attribute userAttr = startElement.getAttributeByName(USER_NAME);
            if (userAttr != null) {
                mapStatistics.addUser(userAttr.getValue());
            }
        }
        if (TAG_NAME.equals(startElement.getName().getLocalPart())) {
            Attribute keyAttr = startElement.getAttributeByName(KEY_NAME);
            if (keyAttr != null) {
                mapStatistics.addTagKey(keyAttr.getValue());
            }
        }
    }
}
