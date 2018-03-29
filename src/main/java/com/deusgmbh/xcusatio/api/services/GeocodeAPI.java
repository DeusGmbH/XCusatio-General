package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.data.usersettings.Address;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class GeocodeAPI extends APIService {

    private static final Logger LOGGER = Logger.getLogger(GeocodeAPI.class.getName());

    private String baseUrlText;

    public GeocodeAPI(String baseUrlText) {
        this.baseUrlText = baseUrlText;
    }

    @Override
    public <T> T get(UserSettings usersettings) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Document responseAsDocument = null;
        try {
            responseAsDocument = builder.parse(this.baseUrlText);
        } catch (SAXException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        NodeList nodes = responseAsDocument.getElementsByTagName("*");
        for (int i = 0; i < nodes.getLength(); ++i) {
            System.out.println(nodes.item(i)
                    .getNodeName() + ": "
                    + nodes.item(i)
                            .getTextContent());
        }

        Address homeAddress = usersettings.getHome();
        String addressAsText = homeAddress.getStreetnum() + "+" + homeAddress.getStreetName() + "+"
                + homeAddress.getCity() + "+" + homeAddress.getCountry();

        return null;
    }

    @Override
    public void transmitDataToWebsite() {
        // TODO Auto-generated method stub

    }

    @Override
    public void extractDesiredInfoFromResponse() {
        // Response - View - Result - Location - NavigationPosition =
        // UserSettings Lat/Long
        // Response - View - Result - Location - MapView - TopLeft/BottomRight

    }

    /* for testing purposes */
    // public static void main(String[] gold) throws IOException {
    // GeocodeAPI gApi = new GeocodeAPI(
    // "https://geocoder.cit.api.here.com/6.2/geocode.json?app_id=ObXv79Ww3xdQ996uEDLw&app_code=74fsgcSubek54INvT13Rcg&searchtext=6+Coblitzallee+Mannheim+68163+DE");
    // gApi.get(null);
    //
    // }

}
