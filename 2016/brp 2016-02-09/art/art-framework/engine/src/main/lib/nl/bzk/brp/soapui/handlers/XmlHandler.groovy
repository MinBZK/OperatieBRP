package nl.bzk.brp.soapui.handlers

import com.eviware.soapui.support.GroovyUtils
import groovy.util.slurpersupport.NodeChild
import groovy.xml.XmlUtil
import junit.framework.AssertionFailedError
import nl.bzk.brp.soapui.AssertionMisluktExceptie
import nl.bzk.brp.soapui.utils.DateSyntaxTranslatorUtil
import nl.bzk.brp.soapui.utils.NegeerWildcardsListener
import org.apache.log4j.Logger
import org.custommonkey.xmlunit.*

/**
 * Handles modifying XML.
 */
class XmlHandler {
    static final Logger log = Logger.getLogger(XmlHandler.class)

    def context
    def supportedRegExp = [
            "\\[nil\\]",
            "\\[-[1-9]\\]|\\[\\]",
            "\\[vandaag.*\\]",
            "\\[moment.*\\]",
            "\\[moment_volgen.*\\]",
            "\\[spatie\\]"
    ]

    final String BRP_NS_PREFIX = 'brp'
    final String BRP_NAMESPACE = 'http://www.bzk.nl/brp/brp0200'

    final String SOAP_NS_PREFIX = 'soap'
    final String SOAP_NAMESPACE = 'http://schemas.xmlsoap.org/soap/envelope/'

    /**
     * Factory method.
     *
     * @param context de SoapUI context
     * @return instantie
     */
    static XmlHandler fromContext(def context) {
        new XmlHandler(context)
    }

    /**
     * Protected constructor.
     * @param ctx SoapUI context
     */
    protected XmlHandler(def ctx) {
        this.context = ctx
    }

    /**
     * Vervangt in de template tekst de geplaatste placeholders.
     *
     * @param template de template tekst
     * @param timeStamp tijdstempel, gebruikt in de placeholders
     * @return een geparste template
     */
    String leesEnParseTemplate(String template, String timeStamp) {
        def xml = context.expand template

        NodeChild gpathResult = new XmlSlurper().parseText(xml).declareNamespace(brp: 'http://www.bzk.nl/brp/brp0200')

        replaceOrDeleteNodes(timeStamp, gpathResult, supportedRegExp.join('|'))
        replaceOrDeleteAttributes(timeStamp, gpathResult, supportedRegExp.join('|'))

        return XmlUtil.serialize(gpathResult)
    }

    private def replaceOrDeleteNodes(String timestamp, NodeChild node, String withText) {
        Map<String, String> maNil = ["$BRP_NS_PREFIX:nil": "true"]
        if (node.size() > 0) {
            node.'*'.each {
                replaceOrDeleteNodes(timestamp, it, withText)
            }

            node.find { it.text().matches(withText) }.each {
                //In node.find geeft alle nodes terug waarin de childrens de text bevat.
                //<node1><child1>[]</child1</node1> met find wordt node1 en child1 teruggeven
                if (!(it.children().size() > 0)) {

                    switch (it.text()) {
                        case ~/\[spatie.*\]/:
                            node.replaceBody(" ");
                            break;
                        case ~/\[nil\]/:
                            // zet de attribute op "nil=true" en geen inhoud.
                            node.replaceBody(null);
                            node.attributes().putAll(maNil);
                            break;
                        case ~/\[-[1-9]\]|\[\]/:
                            int parentFromNode = 0
                            if (it.text() != "[]") {
                                parentFromNode = it.text()[2].toInteger();
                            }

                            // Verwijder parent aangegeven door een negatief getal, -1 is
                            // directe parent -2 is parent daarboven
                            def nodeToRemove = node;
                            for (int i = 0; i < parentFromNode; i++) {
                                nodeToRemove = nodeToRemove.parent();
                            }
                            nodeToRemove.replaceNode {}
                            break;
                        case ~/\[vandaag.*\]/:
                            node.replaceBody(DateSyntaxTranslatorUtil.parseString(timestamp, it.text()));
                            break;
                        case ~/\[moment.*\]/:
                            node.replaceBody(DateSyntaxTranslatorUtil.parseString(timestamp, it.text()));
                            break;
                        case ~/\[moment_volgen.*\]/:
                            node.replaceBody(DateSyntaxTranslatorUtil.parseString(timestamp, it.text()));
                            break;

                        default:
                            //log.info ("[Build Request] default replaceOrDeleteNodes: " + it.text());
                            break;
                    }
                }
            }
        }
    }

    private def replaceOrDeleteAttributes(String timestamp, NodeChild node, String withText) {
        Map<String, String> currentAttributes = new HashMap();
        String value = ''

        if (node.size() > 0) {
            node.'*'.each {
                replaceOrDeleteAttributes(timestamp, it, withText)
            }
            //copy to avoid concurrent Exceptions
            currentAttributes.putAll(node.attributes())

            currentAttributes.keySet().each {
                value = node.attributes().getAt(it); // 'it' is the key
                switch (value) {
                    case ~/\[-[1-9]\]|\[\]/:
                        int parentFromNode = 0
                        if (value != "[]") {
                            parentFromNode = value[2].toInteger()
                            parentFromNode--
                            // Verwijder node aangegeven door een negatief getal, -1 is
                            // directe node; -2 is parent daarboven
                            def nodeToRemove = node;
                            if (parentFromNode > 0) {
                                for (int i = 0; i < parentFromNode; i++) {
                                    nodeToRemove = nodeToRemove.parent();
                                }
                            }
                            nodeToRemove.replaceNode {}
                        } else {
                            node.attributes().remove(it)
                        }
                        break;
                    default:
                        //log.info ("[Build Request] default replaceOrDeleteAttributes: " + it);
                        break;
                }
            }
        }
    }

    /**
     * Formatteert de XML.
     *
     * @param xml de xml om te formatteren
     * @return geformatteerde xml
     */
    String prettyfyXml(String xml) {
        def result = xml.replace('> &lt;', '>&amp;#160;&lt;')
        def holder = new GroovyUtils(context).getXmlHolder(result)
        holder.namespaces[BRP_NS_PREFIX] = BRP_NAMESPACE
        holder.namespaces[SOAP_NS_PREFIX] = SOAP_NAMESPACE

        return holder.getPrettyXml()
    }

    /**
     * Past waardes uit een XML aan, zodat dit makkelijker gebruikt kan worden
     *
     * @param xml de xml om op te schonen
     * @return opgeschoonde xml
     */
    static String cleanupDatabaseResult(String xml) {
        def result = xml.replace('&lt;', '<').replace('&gt;', '>')

        // aanpassen xml om tekst rond een soap-bericht uit het db result te halen
        result = result.replace('\n', '')
        result = result.replace('\r', '')
        result = result.replaceAll('<DATA>.*?<soap:Envelope', '<DATA><soap:Envelope')
        result = result.replaceAll('</soap:Envelope>.*?</DATA>', '</soap:Envelope></DATA>')

        return result
    }

    /**
     * Haalt nodes uit de gegeven xml, dmv de xpath expressie. Beide namespaces worden toegevoegd, SOAP en BRP.
     *
     * @param xml de xml om node(s) uit te halen
     * @param xpath expressie naar nodes om eruit te halen
     * @return de gezochte node
     */
    org.w3c.dom.Node haalNodeUitXml(String xml, String xpath) {
        def groovyUtils = new GroovyUtils(context)

        log.debug("[XmlHandler] xml: " + xml);
        log.debug("[XmlHandler] xpath: " + xpath);

        def holder = groovyUtils.getXmlHolder(xml)

        holder.namespaces[BRP_NS_PREFIX] = BRP_NAMESPACE
        holder.namespaces[SOAP_NS_PREFIX] = SOAP_NAMESPACE
        return holder.getDomNode(xpath)
    }

    /**
     * Doet een assertion, of in het resultaat op de gegeven xpath expressie de verwachte xml te vinden is.
     *
     * @param xpath xpath naar het te vergelijken deel van de xml
     * @param expected het stuk xml zoals verwacht
     * @param result het resultaat xml, waarin we kijken of het voldoet
     */
    void vergelijk(String xpath, def expected, def result) {
        String expectedXmlDeel = haalNodeUitXml(XmlUtil.serialize(expected), xpath).toString()
        String actualXmlDeel = haalNodeUitXml(result, xpath).toString()

        Map<String, String> m = new HashMap<String, String>();
        m.put(BRP_NS_PREFIX, BRP_NAMESPACE);
        m.put(SOAP_NS_PREFIX, SOAP_NAMESPACE);
        NamespaceContext nsCtx = new SimpleNamespaceContext(m);
        XMLUnit.setXpathNamespaceContext(nsCtx);

        XMLUnit.setIgnoreWhitespace(true)
        XMLUnit.setIgnoreComments(true)
        Diff myDiff = new Diff(expectedXmlDeel, actualXmlDeel)
        myDiff.overrideElementQualifier(new ElementNameAndTextQualifier())
        myDiff.overrideDifferenceListener(new NegeerWildcardsListener())

        try {
            XMLAssert.assertXMLEqual(myDiff, true)
        } catch (AssertionFailedError e){
            //AssertionFailedError is geen subklasse van Exceptie, daarom wrappen we hier
            throw new AssertionMisluktExceptie(e);
        }
    }
}
