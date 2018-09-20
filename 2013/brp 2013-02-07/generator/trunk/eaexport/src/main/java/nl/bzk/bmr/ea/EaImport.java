/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.bmr.ea;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;

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

    private static final Logger   LOGGER  = LoggerFactory.getLogger(EaImport.class);

    @Inject
    private ExportRegelRepository exportRegelRepository;
    private Map<String, Node>     nodeMap = new HashMap<String, Node>();

    private int                   associations;
    private int                   classes;
    private int                   attributes;

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

        /*
         * Outermost package.
         */
        Element modelPackage = getModelElement(root);
        setId(ExportRegel.II_MODEL, modelPackage.getAttribute("name"), modelPackage.getAttribute("xmi:id"));

        /*
         * Basistypen.
         */
        Element basisTypePackage = getBasisTypenElement(modelPackage);
        setId(ExportRegel.II_BASISTYPEN, basisTypePackage.getAttribute("name"), basisTypePackage.getAttribute("xmi:id"));
        NodeList basisTypen = basisTypePackage.getElementsByTagName("packagedElement");
        for (int i = 0; i < basisTypen.getLength(); i++) {
            Element basisType = (Element) basisTypen.item(i);
            setId(ExportRegel.II_BASISTYPE, basisType.getAttribute("name"), basisType.getAttribute("xmi:id"));
        }

        Element domeinPackage = getDomeinElement(modelPackage);
        setId(ExportRegel.II_DOMEIN, domeinPackage.getAttribute("name"), domeinPackage.getAttribute("xmi:id"));

        Iterable<Element> schemas = getSchemaElementen(domeinPackage);
        for (Element schema : schemas) {
            setId(ExportRegel.II_SCHEMA, schema.getAttribute("name"), schema.getAttribute("xmi:id"));

            for (Element dataType : getDataTypes(schema)) {
                leesAttribuutType(dataType);
            }
            for (Element klasse : getKlassen(schema)) {
                leesKlasse(klasse);
            }
        }
        for (Element schema : schemas) {
            for (Element associatie : getAssociaties(schema)) {
                leesAssociatie(associatie);
            }
        }
        LOGGER.info("Klaar. {} classes met {} attributen en {} associaties. {} exportId's", new Object[] { classes,
            attributes, associations, exportIds });
    }

    private Iterable<Element> getAssociaties(final Element schema) {
        return getPackagedElements(schema, "uml:Association");
    }

    private Iterable<Element> getKlassen(final Element schema) {
        return getPackagedElements(schema, "uml:Class");
    }

    private Iterable<Element> getPackagedElements(final Element schema, final String umlMetaClass) {
        List<Element> resultaat = new ArrayList<Element>();
        NodeList children = schema.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node instanceof Element) {
                if ("packagedElement".equals(node.getNodeName())) {
                    if (umlMetaClass.equals(((Element) node).getAttribute("xmi:type"))) {
                        resultaat.add((Element) node);
                    }
                }
            }
        }
        return resultaat;
    }

    private Iterable<Element> getDataTypes(final Element schema) {
        return getPackagedElements(schema, "uml:DataType");
    }

    private Iterable<Element> getSchemaElementen(final Element domeinPackage) {
        return getPackagedElements(domeinPackage, "uml:Package");
    }

    private Element getDomeinElement(final Element modelPackage) {
        Element resultaat = null;
        NodeList nodeList1 = modelPackage.getChildNodes();
        for (int i = 0; i < nodeList1.getLength(); i++) {
            Node node1 = nodeList1.item(i);
            if (node1 instanceof Element) {
                if ("packagedElement".equals(node1.getLocalName())) {
                    // Dit kan het basistype of het domein package zijn.
                    NodeList nodeList2 = node1.getChildNodes();
                    for (int j = 0; j < nodeList2.getLength(); j++) {
                        Element node2 = (Element) nodeList2.item(i);
                        if (node2 instanceof Element) {
                            if ("packagedElement".equals(node2.getNodeName())) {
                                // Dit kan een PrimitiveType of een genest package zijn.
                                if ("uml:Package".equals(node2.getAttribute("xmi:type"))) {
                                    resultaat = (Element) node2.getParentNode();
                                }
                            }
                        }
                    }
                }
            }
        }
        return resultaat;
    }

    private Element getModelElement(final Element root) {
        Element resultaat = null;
        /*
         * De grandparent van het eerste packagedElement van het type "uml:PrimitiveType" is het modelelement.
         */
        NodeList nodeList = root.getElementsByTagName("packagedElement");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                if ("packagedElement".equals(node.getNodeName())) {
                    if ("uml:PrimitiveType".equals(((Element) node).getAttribute("xmi:type"))) {
                        resultaat = (Element) node.getParentNode().getParentNode();
                    }
                }
            }
        }
        return resultaat;
    }

    private Element getBasisTypenElement(final Element modelPackage) {
        Element resultaat = null;
        NodeList nodeList = modelPackage.getElementsByTagName("packagedElement");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element node = (Element) nodeList.item(i);
            if ("uml:PrimitiveType".equals(node.getAttribute("xmi:type"))) {
                resultaat = (Element) node.getParentNode();
                break;
            }
        }
        return resultaat;
    }

    /**
     * @param dataType
     */
    private void leesAttribuutType(final Element dataType) {
        setId(ExportRegel.II_ATTRIBUUTTYPE, dataType.getAttribute("name"), dataType.getAttribute("xmi:id"));
        Element generalization = (Element) dataType.getElementsByTagName("generalization").item(0);
        setId(ExportRegel.II_ATTRIBUUTTYPE_TYPE, dataType.getAttribute("name"), generalization.getAttribute("xmi:id"));
    }

    /**
     * Lees een klasse met zijn properties en bewaar de GUID's.
     *
     * @param klasse Het klasse XML element.
     * @throws XPathException Als de xpath expressie ongeldig is.
     */
    private void leesKlasse(final Element klasse) {
        this.classes++;
        nodeMap.put(klasse.getAttribute("xmi:id"), klasse);
        String klasseNaam = klasse.getAttribute("name");
        setId(ExportRegel.II_OBJECTTYPE, klasseNaam, klasse.getAttribute("xmi:id"));

        NodeList superTypeNodes = klasse.getElementsByTagName("generalization");
        if (superTypeNodes.getLength() > 0) {
            Element superTypeElement = (Element) superTypeNodes.item(0);
            setId(ExportRegel.II_SUPERTYPE, klasseNaam, superTypeElement.getAttribute("xmi:id"));
        }

        NodeList properties = klasse.getElementsByTagName("ownedAttribute");
        for (int i = 0; i < properties.getLength(); i++) {
            this.attributes++;
            Element property = (Element) properties.item(i);
            nodeMap.put(property.getAttribute("xmi:id"), property);
            String naam =
                new StringBuilder().append(klasseNaam).append(".").append(property.getAttribute("name")).toString();

            Element lowerValue = (Element) property.getElementsByTagName("lowerValue").item(0);
            Element upperValue = (Element) property.getElementsByTagName("upperValue").item(0);
            if (Arrays.asList("-1", "*").contains(upperValue.getAttribute("value"))) {
                setId(ExportRegel.II_INVERSE_ATTRIBUTE, naam, property.getAttribute("xmi:id"));
                setId(ExportRegel.II_ATTRIBUTE_CARD_HI, naam, upperValue.getAttribute("xmi:id"));
                setId(ExportRegel.II_ATTRIBUTE_CARD_LO, naam, lowerValue.getAttribute("xmi:id"));
            } else {
                setId(ExportRegel.II_ATTRIBUTE, naam, property.getAttribute("xmi:id"));
            }

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

        NodeList ownedEnds = associatie.getElementsByTagName("ownedEnd");
        AssociationEnd src = createSrcAssociationEnd(ownedEnds);
        AssociationEnd dst = createDstAssociationEnd(ownedEnds);
        String rolNaam = dst.name;
        if (rolNaam == null) {
            rolNaam = bepaalElementNaam(dst.typeId);
        }
        if (rolNaam == null) {
            LOGGER.warn("Associatie met destination {} niet gevonden.", dst.typeId);
        } else {
            String naam =
                new StringBuilder().append(bepaalElementNaam(src.typeId)).append(".").append(rolNaam).toString();

            setId(ExportRegel.II_ASSOCIATION, naam, associatie.getAttribute("xmi:id"));
            setId(ExportRegel.II_ASSOCIATION_DST, naam, dst.id);
            setId(ExportRegel.II_ASSOCIATION_DST_CARD_LO, naam, dst.cardLoId);
            setId(ExportRegel.II_ASSOCIATION_DST_CARD_HI, naam, dst.cardHiId);
            setId(ExportRegel.II_ASSOCIATION_SRC, naam, src.id);
            if (src.cardLoId != null) {
                setId(ExportRegel.II_ASSOCIATION_SRC_CARD_LO, naam, src.cardLoId);
            }
            if (src.cardHiId != null) {
                setId(ExportRegel.II_ASSOCIATION_SRC_CARD_HI, naam, src.cardHiId);
            }
        }
    }

    private AssociationEnd createSrcAssociationEnd(final NodeList ownedEnds) throws XPathExpressionException {
        return createAssociationEnd(ownedEnds, "-1");
    }

    private AssociationEnd createDstAssociationEnd(final NodeList ownedEnds) throws XPathExpressionException {
        return createAssociationEnd(ownedEnds, "1");
    }

    private AssociationEnd createAssociationEnd(final NodeList ownedEnds, final String value)
        throws XPathExpressionException
    {
        Element end0 = (Element) ownedEnds.item(0);
        Element end1 = (Element) ownedEnds.item(1);
        Element end = null;
        Node upperValue = end0.getElementsByTagName("upperValue").item(0);
        int upperCardinality = 0;
        if (upperValue != null) {
            String upperValueValue = ((Element) upperValue).getAttribute("value");
            if ("*".equals(upperValueValue)) {
                upperValueValue = "-1";
            }
            upperCardinality = Integer.valueOf(upperValueValue);
        } else {
            upperCardinality = 1;
        }
        if (upperCardinality == Integer.valueOf(value)) {
            end = end0;
        } else {
            end = end1;
        }
        AssociationEnd resultaat = new AssociationEnd();
        resultaat.id = end.getAttribute("xmi:id");
        resultaat.typeId = getTypeId(end);
        resultaat.cardLoId = getLowerBoundId(end);
        resultaat.cardHiId = getUpperBoundId(end);
        if (end.getAttributeNode("name") != null) {
            resultaat.name = (end.getAttribute("name"));
        }
        return resultaat;
    }

    private String getLowerBoundId(final Element end) {
        return getBoundId(end, "lowerValue");
    }

    private String getBoundId(final Element end, final String bound) {
        Element type = (Element) end.getElementsByTagName(bound).item(0);
        if (type == null) {
            return null;
        } else {
            return type.getAttribute("xmi:id");
        }
    }

    private String getUpperBoundId(final Element end) {
        return getBoundId(end, "upperValue");
    }

    private String getTypeId(final Element end) {
        Element type = (Element) end.getElementsByTagName("type").item(0);
        return type.getAttribute("xmi:idref");
    }

    /**
     * Bepaal de waarde van het "name" attribuut van een element dat geïdentificeerd wordt met zijn xmi:id.
     *
     * @param id De waarde voor de xmi:id waarmee het element geïdentificeerd wordt.
     * @return De waarde van het "name" attribuut van het gevonden element.
     */
    private String bepaalElementNaam(final String id) {
        String resultaat = null;
        Element element = (Element) nodeMap.get(id);
        if (element != null) {
            resultaat = element.getAttribute("name");
            assert resultaat != null;
        }
        return resultaat;
    }

    private int exportIds;

    /**
     * Bewaar de GUID in de tabel EXPORTREGEL.
     *
     * @param interneIdentifier Het soort exportregel.
     * @param naam De naam van de exportregel. Deze identifeert de regel, want dat is wat er in het XML bestand staat.
     * @param exportIdentifier De GUID.
     */
    private void setId(final String interneIdentifier, final String naam, final String exportIdentifier) {
        LOGGER.debug("setId({}, {}, {})", new Object[] { interneIdentifier, naam, exportIdentifier });
        exportIds++;
        exportRegelRepository.setExportIdentifier(interneIdentifier, naam, exportIdentifier);
    }
}
