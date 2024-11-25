package com.example.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.*;

import com.example.app.models.City;
import com.example.app.models.SuburbData;
import com.example.app.parsers.SuburbDataHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

public class App {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws IOException {
//        parseFiles();
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<City> cities = session.createQuery("FROM City", City.class).getResultList();
        for (City city : cities) {
            System.out.println("ID: " + city.getId() + ", Name: " + city.getName());
        }
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    private static void parseFiles() throws IOException {
        String folderRoot = Paths.get("").toAbsolutePath().normalize().toString();

        Set<String> folders = getAllFilesInDir(Paths.get(folderRoot, "data", "output").toString(), true);

        for (String folder : folders) {
            String currentFolder = Paths.get(folderRoot, "data", "output", folder).toString();
            Set<String> inputFiles = getAllFilesInDir(currentFolder, false);

            for (String file : inputFiles) {
                List<SuburbData> data = null;

                try {
                    SAXParserFactory parserFactory = SAXParserFactory.newInstance();
                    SAXParser parser = parserFactory.newSAXParser();
                    SuburbDataHandler handler = new SuburbDataHandler();
                    File f = new File(Paths.get(currentFolder, file).toString());
                    parser.parse(f, handler);

                    for (SuburbData sd : handler.getSuburbData()) {
                        System.out.println(sd.toString());
                    }

                } catch (SAXException | ParserConfigurationException e) {
                    System.err.println(e.toString());
                    System.exit(1);
                }
            }
            break;
        }
    }

    /**
     * Grabs all files of the given type (directory or not) within the given fileName.
     * @param fileName The file to search through.
     * @param directoryFlag True if you only want directories, False if you want everything else.
     * @return A set containing all the fileNames.
     */
    private static Set<String> getAllFilesInDir(String fileName, boolean directoryFlag)
        throws SecurityException {
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
            System.err.println(ex.toString());
            System.exit(1);
        }

        return files;
    }
}
