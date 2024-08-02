package com.example.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class App {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws IOException {
        String folderRoot = Paths.get("").toAbsolutePath().normalize().toString();

        Set<String> folders = getAllFilesInDir(folderRoot + "\\data\\output", true);

        for (String folder : folders) {
            String currentFolder = folderRoot + "\\data\\output\\" + folder;
            Set<String> inputFiles = getAllFilesInDir(currentFolder, false);

            for (String file : inputFiles) {
                List<SuburbData> data = null;
                try {
                    data = readXMLFile(currentFolder + "\\" + file);
                } catch (ParserConfigurationException | SAXException | IOException ex) {
                    System.err.println(ex);
                    System.exit(1);
                }

                for (SuburbData sd : data)
                    System.out.println(sd);
            }
            break;
        }
    }

    public static List<SuburbData> readXMLFile(String fileName)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        File xmlFile = new File(fileName);
        Document xmlDoc = docBuilder.parse(xmlFile);

        List<SuburbData> res = new ArrayList<SuburbData>();

        Element root = xmlDoc.getDocumentElement();
        NodeList suburbs = root.getChildNodes();

        for (int i = 0; i < suburbs.getLength(); i++) {
            Node suburb = suburbs.item(i);

            if (!suburb.hasAttributes()) {
                continue;
            }

            NamedNodeMap subrubAttributes = suburb.getAttributes();
            String name = subrubAttributes.item(0).getTextContent();
            float temperature = -100f;
            Date date = null;

            NodeList suburbChildItems = suburb.getChildNodes();
            for (int j = 0; j < suburbChildItems.getLength(); j++) {
                String nodeName = suburbChildItems.item(j).getNodeName().toLowerCase().trim();
                String nodeContent = suburbChildItems.item(j).getTextContent();

                if (nodeName.equals("temperature")) {
                    temperature = Float.parseFloat(nodeContent);
                } else if (nodeName.equals("date")) {
                    try {
                        date = df.parse(nodeContent);
                    } catch (ParseException pe) {
                        System.err.println(pe);
                        continue;
                    }
                }
            }

            if (temperature != -100f && date != null) {
                res.add(new SuburbData(name, temperature, date));
            }
        }

        return res;
    }

    private static Set<String> getAllFilesInDir(String fileName, boolean directoryFlag) {
        Set<String> files = new HashSet<>();

        try {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(fileName))) {
                for (Path path : stream) {
                    if (directoryFlag && Files.isDirectory(path)) {
                        files.add(path.getFileName()
                                .toString());
                    } else if (!directoryFlag && !Files.isDirectory(path)) {
                        files.add(path.getFileName()
                                .toString());
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }

        return files;
    }
}
