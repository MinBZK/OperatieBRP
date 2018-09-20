/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils.findreplaceconverters;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nl.bzk.brp.test.utils.ConverterConfiguratie;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BevragingSoapResponseConverterBerichtconsolidatie {

    public static void main(final String[] args) throws Exception {
        new BevragingSoapResponseConverterBerichtconsolidatie().startConversie();
    }

    private List<Map.Entry<String, String>> findReplaceValues;

    public BevragingSoapResponseConverterBerichtconsolidatie() throws Exception {
        findReplaceValues = ConverterConfiguratie.maakCustomPropertyMap(
                new File("D:/Werk/QSD/mGBA/Eclipse Workspace/art-utils-trunk/utils/converter/src/test/resources/Berichtconsolidatie/find-replace-bevraging-response.properties"));
    }

    public void startConversie() throws Exception {
        String pad1 = "D:/Werk/QSD/mGBA/Eclipse Workspace/art-bijhouding-trunk/PUC501-Kandidaat-vader";
        String pad2 = "D:/Werk/QSD/mGBA/Eclipse Workspace/art-bijhouding-trunk/PUC501-OPvB-BepalenPersonenOpAdresMetRelaties";
        String pad3 = "D:/Werk/QSD/mGBA/Eclipse Workspace/art-bijhouding-trunk/PUC501-OPvB-OpvragenDetailsPersoonEnRelaties";
        Collection<File> inputFiles = FileUtils.listFiles(new File(pad1), new String[] {"xml"}, true);
        inputFiles.addAll(FileUtils.listFiles(new File(pad2), new String[] {"xml"}, true));
        inputFiles.addAll(FileUtils.listFiles(new File(pad3), new String[] {"xml"}, true));
        for (File inputFile : inputFiles) {
            // Skip niet soapresponse files
            if (!inputFile.getName().toLowerCase().endsWith("soapresponse.xml")) {
                continue;
            } else {
                System.out.println("Now converting " + inputFile.getName() + " ...");
            }

            String newContents = this.pasFindReplaceToe(inputFile);


            // Haal nog even door de XML formatter.
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document inputDocument = documentBuilder.parse(new ByteArrayInputStream(newContents.getBytes()));
            verwijderLegeTextNodes(inputDocument.getChildNodes());

            // Schrijf de output weg.
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(inputDocument);
            StreamResult result = new StreamResult(inputFile);
            transformer.transform(source, result);
        }
    }

    private String pasFindReplaceToe(final File inputFile) throws Exception {
        String inputContent = IOUtils.toString(new FileInputStream(inputFile));
        for (Entry<String, String> findReplaceEntry : findReplaceValues) {
            // Vervang de oude tekst door de nieuwe tekst.
            inputContent = inputContent.replaceAll(findReplaceEntry.getKey(), findReplaceEntry.getValue());
        }
        return inputContent;
    }

    private void verwijderLegeTextNodes(final NodeList nodeList) {
        for (int index = 0; index < nodeList.getLength(); index++) {
            Node node = nodeList.item(index);
            if (node.getNodeType() == Node.TEXT_NODE && node.getTextContent().trim().equals("")) {
                node.getParentNode().removeChild(node);
                index--;
            } else {
                // Recursieve call voor de huidige node.
                verwijderLegeTextNodes(node.getChildNodes());
            }
        }
    }

}