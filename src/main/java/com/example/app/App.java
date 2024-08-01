package com.example.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
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

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        String folderRoot = Paths.get("").toAbsolutePath().normalize().toString();

        Set<String> folders = getAllFilesInDir(folderRoot + "\\data\\output", true);
        
        for (String folder : folders) 
        {
            String currentFolder = folderRoot + "\\data\\output\\" + folder;
            Set<String> inputFiles = getAllFilesInDir(currentFolder, false);
           
            for (String file : inputFiles) 
            {
                List<SuburbData> data = readXMLFile(currentFolder + "\\" + file);
                for (SuburbData sd : data)
                    System.out.println(sd);
            }
            break;
        }
    }

    public static List<SuburbData> readXMLFile(String fileName) 
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            File xmlFile = new File(fileName);

            Document xmlDoc = docBuilder.parse(xmlFile);

            List<SuburbData> res = new ArrayList<SuburbData>();

            Element root = xmlDoc.getDocumentElement();
            NodeList suburbs = root.getChildNodes();

            for (int i=0; i<suburbs.getLength(); i++) 
            {
                Node suburb = suburbs.item(i);
                
                if (!suburb.hasAttributes()) 
                {
                    continue;
                }
                
                NamedNodeMap subrubAttributes = suburb.getAttributes();
                String name = subrubAttributes.item(0).getTextContent();
                float temperature = -100f;
                LocalDate date = null;

                NodeList suburbChildItems = suburb.getChildNodes();
                for (int j=0; j<suburbChildItems.getLength(); j++) 
                {
                    if (suburbChildItems.item(j).getNodeName().toLowerCase().trim().equals("temperature"))
                    {
                        temperature = Float.parseFloat(suburbChildItems.item(j).getTextContent());
                    }
                    else if (suburbChildItems.item(j).getNodeName().toLowerCase().trim().equals("date"))
                    {
                        date = LocalDate.parse(suburbChildItems.item(j).getTextContent());
                    }
                }

                if (temperature != -100f && date != null)
                {
                    res.add(new SuburbData(name, temperature, date));
                }
            }

            return res;
        } 
        catch(ParserConfigurationException | SAXException | IOException ex) 
        {
            System.err.println(ex);
            System.exit(1);
        }

        return null;
    }

    private static Set<String> getAllFilesInDir(String fileName, boolean directoryFlag) 
    {
        Set<String> files = new HashSet<>();
       
        try 
        {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(fileName))) 
            {
                for (Path path : stream) 
                {
                    if (directoryFlag && Files.isDirectory(path)) 
                    {
                        files.add(path.getFileName()
                            .toString());
                    } else if (!directoryFlag && !Files.isDirectory(path)) 
                    {
                        files.add(path.getFileName()
                            .toString());
                    }
                }
            }
        } 
        catch(IOException ex) 
        {
            System.err.println(ex);
            System.exit(1);
        } 

        return files;
    }
}
