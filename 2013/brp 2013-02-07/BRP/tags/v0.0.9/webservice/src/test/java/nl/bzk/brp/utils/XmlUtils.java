/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public final class XmlUtils {
	/**
	 * Deze static variabele kunnen we gebruiken omdat onze unit testen niet multitreading zijn.
	 * Dit versnelt het testen, omdat ze niet elke keer aangemaakt hoeven worden.
	 */
	private static DocumentBuilderFactory documentFactory  = DocumentBuilderFactory.newInstance();
	private static DocumentBuilder documentBuilder = null;
    private static TransformerFactory factory = TransformerFactory.newInstance();
    private static Transformer transformer = null;
    private static XPath xpath = XPathFactory.newInstance().newXPath();
 
    /**
     * Deze methode converteert een DOMtree object naar een XML string, zodat dit menselijk leesbaar wordt en eventueel
     * later naar een echte DOM document geconverteerd kan worden.
     * @param node de "root node"
     * @return een XML string of null als er iets fout gaat.
     */
    public static String toXmlString(Node node) throws TransformerConfigurationException, TransformerException {
    	if (null == transformer) {
    		transformer = factory.newTransformer();
    		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    	}
        Source source = new DOMSource(node);
        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        transformer.transform(source, result);
        return stringWriter.getBuffer().toString();
    }

    /**
     * Maakt een DOM Document object gebaseerd opeen xml string
     * @param xmlString de xml string
     * @return het Document
     * @throws ParserConfigurationException in geval val misconfiguratie
     * @throws SAXException in geval van geen geldige xml
     * @throws IOException IO string buffer fouten
     */
    public static Document bouwDocument(String xmlString) throws ParserConfigurationException, SAXException, IOException {
		if (null == documentBuilder) {
			documentBuilder = documentFactory.newDocumentBuilder();
		}
    	return documentBuilder.parse(new InputSource(new StringReader(xmlString)));
    }
    
    public static Document bouwDocumentVanBestand(String berichtBestand) throws ParserConfigurationException, SAXException, IOException {
    	InputStream is = XmlUtils.class.getResourceAsStream(berichtBestand);
    	if (null == is) {
    		throw new IOException("Can't find file "+ berichtBestand);
    	}
    	return documentBuilder.parse(is);
    }

    public static Document bouwDocumentVanBestand(InputStream is) throws ParserConfigurationException, SAXException, IOException {
    	return documentBuilder.parse(is);
    }

    /**
     * Geeft de string representatie van een element (plus alle onderliggende elementen)
     * @param xpathExpression de xpath expressie
     * @param document het DOM Document
     * @return de text, lege string als het pad niet bestaat.
     * @throws XPathExpressionException ongeldige expressie.
     */
    public static String getNodeWaarde(String xpathExpression, Document document) throws XPathExpressionException {
		return (String) xpath.evaluate(xpathExpression, document, XPathConstants.STRING);
    }

    /**
     * 
     * @param xpathExpression
     * @param document
     * @return
     * @throws XPathExpressionException
     */
    public static Integer getNodeCount(String xpathExpression, Document document) throws XPathExpressionException {
		return ((Double) xpath.evaluate(xpathExpression, document, XPathConstants.NUMBER)).intValue();
    }
    
    /**
     * Test of een node bestaat.
     * @param nodePath de xpath expressie
     * @param document het DOM document
     * @return true als de node bestaat, false anders.
     * @throws XPathExpressionException
     */
    public static boolean isNodeAanwezig(String nodePath, Document document) throws XPathExpressionException {
		Object o = xpath.evaluate(nodePath, document, XPathConstants.NODE);
//    		System.out.println("******** Node  "+nodePath+" returns: "+o + ":" + ((null == o ) ? "" : o.getClass().getName())); 
		return (null != o);
    }
    
}
