package nl.brp.acceptance.internal

import nl.bzk.brp.funqmachine.processors.xml.XmlUtils
import org.apache.commons.io.IOUtils
import org.apache.xerces.dom.DeferredAttrNSImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.w3c.dom.Document

import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * Builder voor het maken van een default Yaml en Xml (SOAP) template.
 */
class YamlVoorbeeldBuilder {
    private final Logger logger = LoggerFactory.getLogger(YamlVoorbeeldBuilder)

    final File projectRoot = new File(new File(".").getAbsolutePath());
//    final File werkDirectory = new File(getClass().getResource("/template_generatie").toURI())
    final File werkDirectory = new File(projectRoot, "/src/test/resources/template_generatie")

    File inputBestand
    File outputXmlBestand
    File outputYmlBestand

    YamlVoorbeeldBuilder(String input) {
        this.inputBestand = new File(werkDirectory, input)
    }

    YamlVoorbeeldBuilder metXmlOutput(String output) {
        this.outputXmlBestand = new File(werkDirectory, output)
        return this
    }

    YamlVoorbeeldBuilder metYamlOutput(String output) {
        this.outputYmlBestand = new File(werkDirectory, output)
        return this
    }

    void schrijf() {
        if (inputBestand == null) {
            throw new IllegalArgumentException("Bestand niet gevonden!")
        }
        verwijderOudeOutputAlsDieBestaat()

        final String inputXmlAlsString = inputBestand.text;
        final Document document = XmlUtils.bouwDocument(inputXmlAlsString)

        org.w3c.dom.Node startNode = XmlUtils.getNode("//soap:Body/*", document)

        if (startNode == null) {
            startNode = XmlUtils.getNode("//*", document)
        }

        if (startNode == null) {
            throw new IllegalArgumentException("Startnode niet gevonden!")
        }

        // Genereer YML bestand
        doSomething(startNode, startNode, 0)

        Reader inputYml = new FileReader(outputYmlBestand);
        StringWriter outputYml = new StringWriter();
        try {
            IOUtils.copy(inputYml, outputYml);
        } finally {
            inputYml.close();
        }
        logger.info('YML: \n' + outputYml.toString())

        // Genereer XML bestand
        final TransformerFactory tf = TransformerFactory.newInstance();
        final Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        final StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(startNode), new StreamResult(writer));
        final String outputXml = writer.getBuffer().toString();

        logger.info('XML: \n' + outputXml)
        schrijfStringNaarBestand(outputXmlBestand, outputXml)
    }

    private void verwijderOudeOutputAlsDieBestaat() {
        if (outputXmlBestand?.exists()) {
            outputXmlBestand.delete()
        }
        if (outputYmlBestand.exists()) {
            outputYmlBestand.delete()
        }
    }

    private void doSomething(final org.w3c.dom.Node node, final org.w3c.dom.Node startNode, final int identNiveau) {
        org.w3c.dom.NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            org.w3c.dom.Node currentNode = nodeList.item(i);

            if (currentNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                final String ymlRegel = geefNodeNaamMetIdents(currentNode, identNiveau, startNode)
                schrijfStringNaarBestand(outputYmlBestand, ymlRegel)

                if (currentNode.getAttributes() != null) {
                    for (int x = 0; x < currentNode.getAttributes().getLength(); x++) {
                        final org.w3c.dom.Node attribuut = currentNode.getAttributes().item(x)
                        if (!attribuut.getNodeName().equals('brp:objecttype')) {
                            final String ymlRegelVoorAttribuut = geefNodeNaamMetIdents(attribuut, identNiveau + 1, startNode)
                            schrijfStringNaarBestand(outputYmlBestand, ymlRegelVoorAttribuut)
                        }
                    }
                }

                //calls this method for all the children which is Element
                doSomething(currentNode, startNode, identNiveau + 1);
            }
        }
    }

    private String geefNodeNaamMetIdents(
            final org.w3c.dom.Node node, final int identNiveau, final org.w3c.dom.Node startNode) {
        final StringBuffer resultaat = new StringBuffer()
        for (int i = 0; i < identNiveau; i++) {
            resultaat.append("  ")
        }
        resultaat.append(getNodeNaamZonderNamespacePrefix(node))
        if (node.getChildNodes().getLength() != 1) {
            resultaat.append(":")
        } else if (isElementNode(node)) {
            resultaat.append(": '[]'")

            final String volledigeNodeNaam = getVolledigeNodeNaam(node, startNode)
            node.setTextContent("\${$volledigeNodeNaam}")
        } else {
            resultaat.append(": ''")

            final String volledigeNodeNaam = getVolledigeNodeNaam(node, startNode)
            node.setTextContent("\${$volledigeNodeNaam}")
        }

        resultaat
    }

    private static boolean isElementNode(org.w3c.dom.Node node) {
        node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE
    }

    private static String getNodeNaamZonderNamespacePrefix(org.w3c.dom.Node node) {
        final String nodeNaamZonderNamespacePrefix = node.getNodeName().replaceFirst("brp:", "")
        nodeNaamZonderNamespacePrefix
    }

    private String getVolledigeNodeNaam(final org.w3c.dom.Node node, final org.w3c.dom.Node startNode) {
        final StringBuffer stringBuffer = new StringBuffer(getNodeNaamZonderNamespacePrefix(node))
        if (isElementNode(node) && node.getParentNode() != null && node.getParentNode() != startNode) {
            stringBuffer.insert(0, ".")
            stringBuffer.insert(0, getVolledigeNodeNaam(node.getParentNode(), startNode))
        } else if (!isElementNode(node)) {
            org.w3c.dom.Node elementNodeVanAttribuut = ((DeferredAttrNSImpl) node).getOwnerElement();
            stringBuffer.insert(0, ".")
            stringBuffer.insert(0, getVolledigeNodeNaam(elementNodeVanAttribuut, startNode))
        }
        stringBuffer
    }

    private static String schrijfStringNaarBestand(final File bestand, final String inputString) {
        if (!bestand.exists()) {
            bestand.createNewFile()
        }
        inputString.eachLine {
            bestand << ("${it}\n")
        }
    }

}
