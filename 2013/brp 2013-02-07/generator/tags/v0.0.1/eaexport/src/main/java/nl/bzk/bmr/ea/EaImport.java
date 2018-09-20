/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.bmr.ea;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import nl.bzk.brp.bmr.metamodel.ExportRegel;
import nl.bzk.brp.bmr.metamodel.Laag;
import nl.bzk.brp.bmr.metamodel.SoortExport;
import nl.bzk.brp.bmr.metamodel.repository.ExportRegelRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Importeert de Globally Unique IDentifiers (GUID) uit een Enterprise Architect export XMI bestand. Het doel hiervan is
 * dat bij een volgende export uit het BMR dezelfde elementen ook weer hetzelfde GUID krijgen, waardoor Enterprise
 * Architect ze kan herkennen. EA zal deze informatie dan beschouwen als een wijziging op een reeds bestaand element, in
 * plaats van een heel nieuw element.
 *
 * De GUID's worden in het BRP metaregister (BMR) bewaard in de tabel EXPORTREGEL, kolom EXPORT_IDENTIFIER. Deze
 * tabel heeft een kunstmatige identifier, ID, maar die wordt verder nergens voor gebruikt. De twee kolommen LAAG en
 * SOORT fungeren als filter, omdat de tabel informatie kan bevatten van verschillende varianten EA modellen (class
 * model, data model en gegevensset) en verschillende varianten van BMR modellen (logisch model en operationeel model).
 * De worden per export en import run gekozen en veranderen daarbinnen niet meer.
 *
 * Bij het exporteren wordt de combinatie van INTERNE_IDENTIFIER en SYNCID als unieke sleutel gebruikt, maar bij het
 * importeren de combinatie van INTERNE_IDENTIFIER en NAAM, omdat de SYNCID niet bij EA bekend is.
 */
@Service
public class EaImport {

    private class AssociationEnd {

        private String id;
        private String name;
        private String typeId;
        private String cardLoId;
        private String cardHiId;
    }

    /**
     * Alle elementen van het niveau boven het eerste element van het type 'uml:Class' zijn dus schema packages.
     */
    private static final String   XPATH_SCHEMAS     = "//packagedElement[@xmi:type='uml:Class'][1]/..";
    private static final String   XPATH_CLASSES     = "packagedElement[@xmi:type='uml:Class']";
    private static final String   XPATH_ASSOCIATIES = "packagedElement[@xmi:type='uml:Association']";

    private static final Logger   LOGGER            = LoggerFactory.getLogger(EaImport.class);
    @Inject
    private ExportRegelRepository exportRegelRepository;
    private NamespaceContext      context;
    private XPath                 xpath;

    private Map<String, Node>     nodeMap           = new HashMap<String, Node>();

    private int                   associations;
    private int                   classes;
    private int                   attributes;

    public EaImport() {
        context = new NamespaceContextImpl();
        xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(context);
    }

    /**
     * Importeer de GUID's uit een Enterprise Architect export bestand en bewaar ze in de EXPORTREGEL tabel.
     *
     * @param input Het Enterprise Architect XMI export bestand.
     * @throws Exception Als er iets fout gaat waar we niets aan kunnen doen, behalve het programma beëindigen.
     */
    @Transactional
    public void importGuids(final File input) throws Exception {
        Laag.setHuidigeLaag(Laag.LOGISCH_MODEL);
        LOGGER.info("Importeren Enterprise Architect GUID's voor laag {} en soort {}", Laag.getHuidigeLaag(),
                SoortExport.getHuidigeSoort());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(input);
        Element root = document.getDocumentElement();

        NodeList schemas = (NodeList) xpath.evaluate(XPATH_SCHEMAS, root, XPathConstants.NODESET);
        for (int i = 0; i < schemas.getLength(); i++) {
            Element schema = (Element) schemas.item(i);
            setId(ExportRegel.II_SCHEMA, schema.getAttribute("name"), schema.getAttribute("xmi:id"));

            NodeList klasse = (NodeList) xpath.evaluate(XPATH_CLASSES, schema, XPathConstants.NODESET);
            for (int j = 0; j < klasse.getLength(); j++) {
                leesKlasse((Element) klasse.item(j));
            }
        }
        for (int i = 0; i < schemas.getLength(); i++) {
            Node schema = schemas.item(i);
            NodeList associaties = (NodeList) xpath.evaluate(XPATH_ASSOCIATIES, schema, XPathConstants.NODESET);
            for (int j = 0; j < associaties.getLength(); j++) {
                leesAssociatie((Element) associaties.item(j));
            }
        }
        LOGGER.info("Klaar. {} classes met {} attributen en {} associaties.", new Object[] { classes, attributes,
            associations });
    }

    /**
     * Lees een klasse met zijn properties en bewaar de GUID's.
     *
     * @param klasse Het klasse XML element.
     * @throws XPathException Als de xpath expressie ongeldig is.
     */
    private void leesKlasse(final Element klasse) throws XPathException {
        this.classes++;
        nodeMap.put(klasse.getAttribute("xmi:id"), klasse);
        String klasseNaam = klasse.getAttribute("name");
        setId(ExportRegel.II_OBJECTTYPE, klasseNaam, klasse.getAttribute("xmi:id"));

        NodeList properties = klasse.getElementsByTagName("ownedAttribute");
        for (int i = 0; i < properties.getLength(); i++) {
            this.attributes++;
            Element property = (Element) properties.item(i);
            nodeMap.put(property.getAttribute("xmi:id"), property);
            String naam =
                new StringBuilder().append(klasseNaam).append(".").append(property.getAttribute("name")).toString();
            setId(ExportRegel.II_ATTRIBUTE, naam, property.getAttribute("xmi:id"));
        }
    }

    /**
     * Lees een associatie met zijn member- en ownedEnds en bewaar de GUID's.
     *
     * @param associatie Het associatie XML element. *
     * @throws XPathException Als de xpath expressie ongeldig is.
     */
    private void leesAssociatie(final Element associatie) throws XPathExpressionException {
        this.associations++;
        NodeList memberEnds = associatie.getElementsByTagName("memberEnd");
        String dstId = ((Element) memberEnds.item(0)).getAttribute("xmi:idref");
        String srcId = ((Element) memberEnds.item(1)).getAttribute("xmi:idref");

        NodeList ownedEnds = associatie.getElementsByTagName("ownedEnd");
        AssociationEnd src = null;
        AssociationEnd dst = null;
        for (int i = 0; i < ownedEnds.getLength(); i++) {
            AssociationEnd associationEnd = createAssociationEnd((Element) ownedEnds.item(i));
            if (associationEnd.id.startsWith("EAID_dst")) {
                dst = associationEnd;
            } else {
                src = associationEnd;
            }
        }
        String rolNaam = dst.name;
        if (rolNaam == null) {
            rolNaam = bepaalElementNaam(dst.typeId);
        }
        String naam = new StringBuilder().append(bepaalElementNaam(src.typeId)).append(".").append(rolNaam).toString();

        setId(ExportRegel.II_ASSOCIATION, naam, associatie.getAttribute("xmi:id"));
        setId(ExportRegel.II_ASSOCIATION_DST, naam, dstId);
        setId(ExportRegel.II_ASSOCIATION_DST_CARD_LO, naam, dst.cardLoId);
        setId(ExportRegel.II_ASSOCIATION_DST_CARD_HI, naam, dst.cardHiId);
        setId(ExportRegel.II_ASSOCIATION_SRC, naam, srcId);
        setId(ExportRegel.II_ASSOCIATION_SRC_CARD_LO, naam, src.cardLoId);
        setId(ExportRegel.II_ASSOCIATION_SRC_CARD_HI, naam, src.cardHiId);
    }

    /**
     * Bepaal de waarde van het "name" attribuut van een element dat geïdentificeerd wordt met zijn xmi:id.
     *
     * @param id De waarde voor de xmi:id waarmee het element geïdentificeerd wordt.
     * @return De waarde van het "name" attribuut van het gevonden element.
     */
    private String bepaalElementNaam(final String id) {
        String elementNaam = ((Element) nodeMap.get(id)).getAttribute("name");
        assert elementNaam != null;
        return elementNaam;
    }

    /**
     * Cre&eum;er een AssociationEnd uit een XML element.
     *
     * @param end Het XML element.
     * @return Het {@link AssociationEnd}.
     * @throws XPathException Als de xpath expressie ongeldig is.
     */
    private AssociationEnd createAssociationEnd(final Element end) throws XPathExpressionException {
        AssociationEnd resultaat = new AssociationEnd();
        resultaat.id = end.getAttribute("xmi:id");
        resultaat.typeId = (String) xpath.evaluate("type/@xmi:idref", end, XPathConstants.STRING);
        resultaat.cardLoId = (String) xpath.evaluate("lowerValue/@xmi:id", end, XPathConstants.STRING);
        resultaat.cardHiId = (String) xpath.evaluate("upperValue/@xmi:id", end, XPathConstants.STRING);
        if (end.getAttributeNode("name") != null) {
            resultaat.name = (end.getAttribute("name"));
        }
        return resultaat;
    }

    /**
     * Bewaar de GUID in de tabel EXPORTREGEL.
     *
     * @param interneIdentifier Het soort exportregel.
     * @param naam De naam van de exportregel. Deze identifeert de regel, want dat is wat er in het XML bestand staat.
     * @param exportIdentifier De GUID.
     */
    private void setId(final String interneIdentifier, final String naam, final String exportIdentifier) {
        LOGGER.debug("setId({}, {}, {})", new Object[] { interneIdentifier, naam, exportIdentifier });
        exportRegelRepository.setExportIdentifier(interneIdentifier, naam, exportIdentifier);
    }
}
