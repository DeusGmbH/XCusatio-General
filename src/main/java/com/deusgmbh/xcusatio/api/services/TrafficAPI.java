package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentDetails;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentLocation;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentTimes;
import com.deusgmbh.xcusatio.api.data.TrafficIncidentType;
import com.deusgmbh.xcusatio.context.wildcard.TrafficContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class TrafficAPI extends APIService {

    private static final Logger LOGGER = Logger.getLogger(TrafficAPI.class.getName());

    private String baseUrlText;
    private List<TrafficContext> trafficContexts;

    public TrafficAPI(String baseUrlText) {
        this.baseUrlText = baseUrlText;
    }

    @Override
    public TrafficContext get(UserSettings usersettings) {

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
        List<String> trafficItems = new LinkedList<>();
        List<String> startTimes = new LinkedList<>();
        List<String> intersections = new LinkedList<>();
        for (int i = 0; i < nodes.getLength(); ++i) {
            storeInListIfNamed("TRAFFIC_ITEMS", nodes.item(i), trafficItems);
            storeInListIfNamed("START_TIME", nodes.item(i), startTimes);
            storeInListIfNamed("INTERSECTION", nodes.item(i), intersections);
        }
        trafficItems.stream().forEach(System.out::printf);
        System.out.println();
        startTimes.stream().forEach(System.out::printf);
        System.out.println();
        intersections.stream().forEach(System.out::printf);

        TrafficIncidentDetails trd = new TrafficIncidentDetails(TrafficIncidentType.CONSTRUCTION, "Baustelle",
                "Strasse wegen Baustelle gesperrt");
        TrafficIncidentLocation tri = new TrafficIncidentLocation("DE", "Mannheim", "Seckenheimer Landstr.");

        TrafficIncidentTimes trt = new TrafficIncidentTimes("6:00", "10:30");

        TrafficContext trafficContext = new TrafficContext(trd, tri, trt);

        return trafficContext;
    }

    private List<String> storeInListIfNamed(String name, Node node, List<String> list) {
        LOGGER.info("Listing " + name + "\n\n");
        if (node.getNodeName().equals(name)) {
            list.add(node.getNodeName() + ": " + node.getTextContent());
        }
        return list;
    }

    // TRAFFIC_ITEMS - TRAFFIC_ITEM -
    // ..ID/..STATUS_SHORT/..TYPE_DESC/..START_TIME/..END_TIME/..LOCATION (-
    // INTERSECTION - ORIGIN - STREET1/STREET2)/..GEOLOC

    @Override
    public void transmitDataToWebsite() {

    }

    @Override
    public void extractDesiredInfoFromResponse() {

    }

    public static void main(String[] selenium)
            throws MalformedURLException, IOException, ParserConfigurationException, SAXException {

        TrafficAPI trafficAPI = new TrafficAPI(
                "https://traffic.cit.api.here.com/traffic/6.2/incidents/xml/8/134/86?app_id=ObXv79Ww3xdQ996uEDLw&app_code=74fsgcSubek54INvT13Rcg");
        trafficAPI.get(null);

        // "https://traffic.cit.api.here.com/traffic/6.2/incidents/xml/8/134/86?app_id=ObXv79Ww3xdQ996uEDLw&app_code=74fsgcSubek54INvT13Rcg");
        // https://geocoder.cit.api.here.com/6.2/geocode.json?app_id=ObXv79Ww3xdQ996uEDLw&app_code=74fsgcSubek54INvT13Rcg&searchtext=6+Coblitzallee+Mannheim+68163+DE
        // TrafficAPI trafficAPI = new TrafficAPI(
        // "https://traffic.cit.api.here.com/traffic/6.2/incidents/xml/8/134/86?app_id=ObXv79Ww3xdQ996uEDLw&app_code=74fsgcSubek54INvT13Rcg");
        // trafficAPI.requestWebsite();
        // trafficAPI.getResponseFromWebsite();
        // trafficAPI.extractDesiredInfoFromResponse();
    }

    // TRAFFIC_ITEMS - TRAFFIC_ITEM -
    // ..ID/..STATUS_SHORT/..TYPE_DESC/..START_TIME/..END_TIME/..LOCATION (-
    // INTERSECTION - ORIGIN - STREET1/STREET2)/..GEOLOC

}