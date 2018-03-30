package com.deusgmbh.xcusatio.api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com, jan.leiblein@gmail.com
 *
 * 
 *
 */

// TODO refactoring, simplify access to APIs
public abstract class APIService {

    private static final Logger LOGGER = Logger.getLogger(APIService.class.getName());

    private String baseUrlText;
    private URL baseUrl;
    private HttpURLConnection connection;
    private Document responseAsDocument;
    private NodeList allDocumentNodes;
    private String responseAsText;

    public abstract <T extends Object> T get(UserSettings usersettings);

    public void requestWebsite() throws IOException {
        try {
            this.baseUrl = new URL(this.baseUrlText);
        } catch (MalformedURLException e) {
            LOGGER.warning("Malformed URL, got " + this.baseUrlText + " as input string.");
        }
        this.connection = (HttpURLConnection) this.baseUrl.openConnection();
    }

    /*
     * must be abstract because special implementations are necessary: e.g. rnv
     * api needs transmitted header data and the others don't
     */
    public abstract void transmitDataToWebsite();

    public void getResponseFromWebsite() throws IOException, ParserConfigurationException, SAXException {
        if (statusCodeOkay()) {
            readResponseStream();
            connection.disconnect();
            LOGGER.info("Closed connection.");
        } else {
            LOGGER.warning("Bad http status: " + connection.getResponseCode());
        }
    }

    public abstract void extractDesiredInfoFromResponse();

    private boolean statusCodeOkay() throws IOException {
        return (this.connection.getResponseCode() == HttpURLConnection.HTTP_OK);
    }

    // TODO simplify
    private void readResponseStream() throws IOException, ParserConfigurationException, SAXException {
        InputStream inputStream = new BufferedInputStream(this.connection.getInputStream());
        // this.responseAsText = inputStreamToString(inputStream);
        getXMLTreeFrom(inputStream);
        inputStream.close();
    }

    // TODO use JSON instead
    private NodeList getXMLTreeFrom(InputStream inputStream)
            throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.responseAsDocument = builder.parse("Test");
        this.allDocumentNodes = this.responseAsDocument.getElementsByTagName("*");
        return this.allDocumentNodes;
    }

    public void setBaseUrlText(String urlText) {
        this.baseUrlText = urlText;
    }

    public String getResponseAsText() {
        return this.responseAsText;
    }

    public Document getResponseAsDocument() {
        return responseAsDocument;
    }

    public NodeList getAllDocumentNodes() {
        return allDocumentNodes;
    }

}