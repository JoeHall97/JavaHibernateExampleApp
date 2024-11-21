package com.example.app.parsers;

import com.example.app.models.City;
import com.example.app.models.Suburb;
import com.example.app.models.SuburbData;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class SuburbDataHandler extends DefaultHandler{
    private static final String SUBURB = "Suburb";
    private static final String LOCATION = "Location";
    private static final String TEMPERATURE = "Temperature";
    private static final String DATE = "Date";

    private List<SuburbData> suburbDataList;
    private City city;
    private Suburb suburb;
    private SuburbData suburbData;
    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        suburbData = null;
        suburbDataList = new ArrayList<>();
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
            throws SAXException {
        switch (qName) {
            case LOCATION:
                if (atts.getLength() == 0 || atts.getIndex("name") == -1)
                    throw new SAXException("Location does not have a name attribute");
                city = new City();
                city.setName(atts.getValue(atts.getIndex("name")));
                break;
            case SUBURB:
                if (atts.getLength() == 0 || atts.getIndex("name") == -1)
                    throw new SAXException("Suburb does not have a name attribute.");
                suburb = new Suburb();
                suburb.setName(atts.getValue(atts.getIndex("name")));
                suburbData = new SuburbData();
                suburbData.setSuburb(suburb);
                break;
            case TEMPERATURE, DATE:
                elementValue = new StringBuilder();
                break;
            default:
                throw new SAXException(qName + " is not a valid element.");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException, NullPointerException, DateTimeParseException, NumberFormatException {
        switch (qName) {
            case TEMPERATURE:
                suburbData.setTemperature(elementValue.toString());
                break;
            case DATE:
                suburbData.setDate(elementValue.toString());
                break;
            case SUBURB:
                suburbDataList.add(suburbData);
                break;
        }
    }

    public List<SuburbData> getSuburbData() { return this.suburbDataList; }
}
