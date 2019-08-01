package net.ukr.dreamsicle.dao;


import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.service.CurrencyController;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class XMLToJSON {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLToJSON.class);

    public List<Currency> parseCurrencyXML(File file) {
        List<Currency> currencies = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            NodeList currency = document.getElementsByTagName("currency");
            for (int i = 0; i < currency.getLength(); i++) {
                currencies.add(getCurrency(currency.item(i), FilenameUtils.removeExtension(file.getName())));
            }
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            LOGGER.error("Error", ex);
        }
        return currencies;
    }

    private Currency getCurrency(Node node, String name) {
        Currency currency = new Currency();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            currency.setBankName(name);
            currency.setCurrencyCode(getTagValue("currencyCode", element));
            currency.setPurchaseCurrency(getTagValue("purchaseCurrency", element));
            currency.setSaleOfCurrency(getTagValue("saleOfCurrency", element));
        }
        return currency;
    }

    private String getTagValue(String name, Element element) {
        return element.getElementsByTagName(name).item(0).getChildNodes().item(0).getNodeValue();
    }
}