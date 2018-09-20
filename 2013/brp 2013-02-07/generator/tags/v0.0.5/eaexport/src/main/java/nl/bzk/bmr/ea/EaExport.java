/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.bmr.ea;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nl.bzk.brp.bmr.metamodel.Attribuut;
import nl.bzk.brp.bmr.metamodel.AttribuutType;
import nl.bzk.brp.bmr.metamodel.BasisType;
import nl.bzk.brp.bmr.metamodel.Domein;
import nl.bzk.brp.bmr.metamodel.ExportRegel;
import nl.bzk.brp.bmr.metamodel.Groep;
import nl.bzk.brp.bmr.metamodel.InSetOfModel;
import nl.bzk.brp.bmr.metamodel.Laag;
import nl.bzk.brp.bmr.metamodel.ModelElement;
import nl.bzk.brp.bmr.metamodel.ObjectType;
import nl.bzk.brp.bmr.metamodel.Schema;
import nl.bzk.brp.bmr.metamodel.SoortExport;
import nl.bzk.brp.bmr.metamodel.Tekst;
import nl.bzk.brp.bmr.metamodel.Type;
import nl.bzk.brp.bmr.metamodel.repository.BasisTypeRepository;
import nl.bzk.brp.bmr.metamodel.repository.DomeinRepository;
import nl.bzk.brp.bmr.metamodel.repository.ExportRegelRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


@Service
public class EaExport {

    private static final String        XMLNS             = "http://www.w3.org/2000/xmlns/";
    private static final String        XMINS             = "http://schema.omg.org/spec/XMI/2.1";
    private static final String        UMLNS             = "http://schema.omg.org/spec/UML/2.1";

    private static final Logger        LOGGER            = LoggerFactory.getLogger(EaExport.class);

    @Inject
    private DomeinRepository           domeinRepository;

    @Inject
    private BasisTypeRepository        basisTypeRepository;

    @Inject
    private ExportRegelRepository      exportRegelRepository;

    private Map<ModelElement, Element> exportedElements  = new HashMap<ModelElement, Element>();
    private Map<ModelElement, Element> extensionElements = new HashMap<ModelElement, Element>();
    private Set<Type>                  unexportedTypes   = new HashSet<Type>();
    private int                        klassen;
    private int                        attributen;
    private int                        associaties;
    private Document                   doc;
    private Element                    model             = null;

    @Transactional
    public void exportXMI(final String domeinNaam, final SoortExport soort, final String versieTag,
        final OutputStream output) throws Exception
    {
        Laag.setHuidigeLaag(Laag.LOGISCH_MODEL);
        Domein domein = null;
        List<BasisType> basisTypen = null;
        try {
            basisTypen = basisTypeRepository.findAll();
            domein = (Domein) domeinRepository.findByNaam(domeinNaam);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Domein '{}' niet gevonden. Einde.", domeinNaam);
            return;
        }
        LOGGER.info("Genereren Enterprise Architect XMI export voor Domein '{}'", domein.getNaam());

        SoortExport.setHuidigeSoort(soort);
        genereerEaExport(basisTypen, domein, versieTag, output);
        LOGGER.info(
                "Klaar. {} klassen, {} attributen en {} associaties geëxporteerd. Niet-geëxporteerde object typen: {}",
                new Object[] { klassen, attributen, associaties, unexportedTypes.size() });
    }

    private String boolean2Xmi(final boolean waarde) {
        return waarde ? "true" : "false";
    }

    private void genereerAssociatie(final Attribuut att, final Element parent) {
        ObjectType srcType = att.getObjectType();
        ObjectType dstType = (ObjectType) att.getType();
        if (exportedElements.get(srcType) == null) {
            LOGGER.debug("Dangling reference to {}", srcType.getNaam());
            unexportedTypes.add(srcType);
        }
        if (exportedElements.get(dstType) == null) {
            LOGGER.debug("Dangling reference to {}", dstType.getNaam());
            unexportedTypes.add(dstType);
        }
        if ((exportedElements.get(srcType) == null) || (exportedElements.get(dstType) == null)) {
            /*
             * Het heeft geen zin associaties tussen niet-meegeëxporteerde klassen te exporteren.
             */
            return;
        }

        associaties++;
        String naam = new StringBuilder().append(srcType.getNaam()).append(".").append(att.getNaam()).toString();
        String associationId = getId(ExportRegel.II_ASSOCIATION, naam, att.getSyncId());
        String srcEndId = getId(ExportRegel.II_ASSOCIATION_SRC, naam, att.getSyncId());
        String dstEndId = getId(ExportRegel.II_ASSOCIATION_DST, naam, att.getSyncId());
        String srcTypeId = getId(ExportRegel.II_OBJECTTYPE, srcType.getNaam(), srcType.getSyncId());
        String dstTypeId = getId(ExportRegel.II_OBJECTTYPE, dstType.getNaam(), dstType.getSyncId());
        String srcCardLoId = getId(ExportRegel.II_ASSOCIATION_SRC_CARD_LO, naam, att.getSyncId());
        String srcCardHiId = getId(ExportRegel.II_ASSOCIATION_SRC_CARD_HI, naam, att.getSyncId());
        String dstCardLoId = getId(ExportRegel.II_ASSOCIATION_DST_CARD_LO, naam, att.getSyncId());
        String dstCardHiId = getId(ExportRegel.II_ASSOCIATION_DST_CARD_HI, naam, att.getSyncId());

        Element associatie = (Element) parent.appendChild(doc.createElement("packagedElement"));
        associatie.setAttributeNS(XMINS, "xmi:type", "uml:Association");
        associatie.setAttributeNS(XMINS, "xmi:id", associationId);
        associatie.setAttribute("visibility", "public");

        /*
         * Destination owned end. Dit end is owned door de associatie, omdat we de associaties niet navigeerbaar willen
         * maken om te voorkomen dat EA pijlpunten tekent. Er is nu geen verband tussen de attributen die met deze
         * associatie corresponderen, en deze associatie. Dat nemen we dan maar voor lief.
         */
        Element dstEnd = (Element) associatie.appendChild(doc.createElement("ownedEnd"));
        dstEnd.setAttributeNS(XMINS, "xmi:type", "uml:Property");
        dstEnd.setAttributeNS(XMINS, "xmi:id", dstEndId);
        dstEnd.setAttribute("name", att.getNaam());
        dstEnd.setAttribute("type", dstTypeId);
        dstEnd.setAttribute("visibility", "private");
        dstEnd.setAttribute("association", associationId);

        Element dstLowervalue = (Element) dstEnd.appendChild(doc.createElement("lowerValue"));
        dstLowervalue.setAttributeNS(XMINS, "xmi:type", "uml:LiteralInteger");
        dstLowervalue.setAttributeNS(XMINS, "xmi:id", dstCardLoId);
        dstLowervalue.setAttribute("value", "0");

        Element dstUpperValue = (Element) dstEnd.appendChild(doc.createElement("upperValue"));
        dstUpperValue.setAttributeNS(XMINS, "xmi:type", "uml:LiteralUnlimitedNatural");
        dstUpperValue.setAttributeNS(XMINS, "xmi:id", dstCardHiId);
        dstUpperValue.setAttribute("value", "1");
        /*
         * Het member end verwijst eenvoudig naar het owned end.
         */
        Element dstMemberEnd = (Element) associatie.appendChild(doc.createElement("memberEnd"));
        dstMemberEnd.setAttributeNS(XMINS, "xmi:idref", dstEndId);

        /*
         * Source owned end.
         */
        Element srcEnd = (Element) associatie.appendChild(doc.createElement("ownedEnd"));
        srcEnd.setAttributeNS(XMINS, "xmi:type", "uml:Property");
        srcEnd.setAttributeNS(XMINS, "xmi:id", srcEndId);
        srcEnd.setAttribute("type", srcTypeId);
        srcEnd.setAttribute("visibility", "private");
        srcEnd.setAttribute("isDerived", "true");
        srcEnd.setAttribute("association", associationId);
        if (att.getInverseAssociatieNaam() != null) {
            srcEnd.setAttribute("name", att.getInverseAssociatieNaam());
            /*
             * Een attribuut met cardinaliteit [0..*] maken in het destination objecttype dat terugverwijst naar het
             * source objecttype.
             */
            Element dstElement = exportedElements.get(dstType);
            Element dstAttribuut = (Element) dstElement.appendChild(doc.createElement("ownedAttribute"));
            dstAttribuut.setAttributeNS(XMINS, "xmi:type", "uml:Property");
            String inverseAttribuutNaam = dstType.getNaam() + "." + att.getInverseAssociatieNaam();
            String inverseAttribuutId = getId(ExportRegel.II_INVERSE_ATTRIBUTE, inverseAttribuutNaam, att.getSyncId());
            dstAttribuut.setAttributeNS(XMINS, "xmi:id", inverseAttribuutId);
            dstAttribuut.setAttribute("name", att.getInverseAssociatieNaam());
            dstAttribuut.setAttribute("type", srcTypeId);
            dstAttribuut.setAttribute("visibility", "private");
            dstAttribuut.setAttribute("isDerived", "true");
            /*
             * Voeg de stereotype voor de groep toe aan het extension element.
             */
            // Element attributeContainer =
            // (Element) extensionElements.get(dstType).getElementsByTagName("attributes").item(0);
            // Element xAttributeElement = (Element) attributeContainer.appendChild(doc.createElement("attribute"));
            // xAttributeElement.setAttributeNS(XMINS, "xmi:idref", inverseAttribuutId);
            // Element stereoTypeElement = (Element) xAttributeElement.appendChild(doc.createElement("stereotype"));
            // stereoTypeElement.setAttribute("stereotype", "InverseAssociaties");
            // int position = attributeContainer.getElementsByTagName("attribute").getLength();
            // Element containmentElement = (Element) xAttributeElement.appendChild(doc.createElement("containment"));
            // containmentElement.setAttribute("position", String.valueOf(position));

            Element dstAttribuutLowerValue = (Element) dstAttribuut.appendChild(doc.createElement("lowerValue"));
            dstAttribuutLowerValue.setAttributeNS(XMINS, "xmi:type", "uml:LiteralInteger");
            dstAttribuutLowerValue.setAttributeNS(XMINS, "xmi:id",
                    getId(ExportRegel.II_ATTRIBUTE_CARD_LO, inverseAttribuutNaam, att.getSyncId()));
            dstAttribuutLowerValue.setAttribute("value", "0");

            Element dstAttribuutUpperValue = (Element) dstAttribuut.appendChild(doc.createElement("upperValue"));
            dstAttribuutUpperValue.setAttributeNS(XMINS, "xmi:type", "uml:LiteralUnlimitedNatural");
            dstAttribuutUpperValue.setAttributeNS(XMINS, "xmi:id",
                    getId(ExportRegel.II_ATTRIBUTE_CARD_HI, inverseAttribuutNaam, att.getSyncId()));
            dstAttribuutUpperValue.setAttribute("value", "-1");
        }

        Element srcLowerValue = (Element) srcEnd.appendChild(doc.createElement("lowerValue"));
        srcLowerValue.setAttributeNS(XMINS, "xmi:type", "uml:LiteralInteger");
        srcLowerValue.setAttributeNS(XMINS, "xmi:id", srcCardLoId);
        srcLowerValue.setAttribute("value", "0");

        Element srcUpperValue = (Element) srcEnd.appendChild(doc.createElement("upperValue"));
        srcUpperValue.setAttributeNS(XMINS, "xmi:type", "uml:LiteralUnlimitedNatural");
        srcUpperValue.setAttributeNS(XMINS, "xmi:id", srcCardHiId);
        srcUpperValue.setAttribute("value", "-1");
        /*
         * Het member end verwijst eenvoudig naar het owned end.
         */
        Element srcMemberEnd = (Element) associatie.appendChild(doc.createElement("memberEnd"));
        srcMemberEnd.setAttributeNS(XMINS, "xmi:idref", srcEndId);
        srcMemberEnd.setAttribute("type", srcTypeId);
    }

    private CharSequence genereerEaExport(final List<BasisType> basisTypen, final Domein domein,
        final String versieTag, final OutputStream output) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        doc = factory.newDocumentBuilder().newDocument();
        doc.setXmlVersion("1.0");
        doc.createAttributeNS(UMLNS, "uml");

        Element root = doc.createElementNS(XMINS, "xmi:XMI");
        root.setAttribute("xmi:version", "2.1");
        root.setAttributeNS(XMLNS, "xmlns:uml", UMLNS);
        root.setAttributeNS(XMLNS, "xmlns:xmi", XMINS);
        doc.appendChild(root);

        // Element documentation = doc.createElementNS(XMINS, "xmi:Documentation");
        // root.appendChild(documentation);
        // documentation.setAttribute("exporter", "Enterprise Architect");
        // documentation.setAttribute("exporterVersion", "6.5");

        genereerModel(basisTypen, domein, versieTag, root);

        serializeDocument(output, doc);
        return null;
    }

    /**
     * Serialiseer de DOM tree naar een bestand als XMLNS.
     *
     * @param output De outputstream waar het bestand naartoe gaat.
     * @param document Het DOM document dat geserialiseerd wordt.
     * @throws TransformerFactoryConfigurationError een uitzondering.
     * @throws TransformerConfigurationException een uitzondering
     * @throws TransformerException een uitzondering
     */
    private void serializeDocument(final OutputStream output, final Document document) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "windows-1252");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        Source source = new DOMSource(document);
        transformer.transform(source, new StreamResult(output));
    }

    /**
     * Genereer een leeg Enterprise Architect Extension element voor groepen en teksten. Later kunnen daar elementen aan
     * worden toegevoegd.
     *
     * @param parent Het XMLNS document element waaronder het model wordt opgehangen.
     */
    private void genereerExtensie(final Element parent) {
        Element extensie = doc.createElementNS(XMINS, "xmi:Extension");
        parent.appendChild(extensie);
        extensie.setAttribute("extender", "Enterprise Architect");
        extensie.setAttribute("extenderID", "6.5");
        Element elements = doc.createElement("elements");
        extensie.appendChild(elements);
    }

    /**
     * Genereer een model in UMLNS/XMINS formaat.
     *
     * @param basisTypen TODO
     * @param domein Het BMR domein waaruit het model wordt gegenereerd.
     * @param versieTag De tag van de versie van het model.
     * @param parent Het XMLNS document element waaronder het model wordt opgehangen.
     *
     * @throws Exception
     */
    private void genereerModel(final List<BasisType> basisTypen, final Domein domein, final String versieTag,
        final Element parent) throws Exception
    {
        model = (Element) parent.appendChild(doc.createElementNS(UMLNS, "uml:Model"));
        model.setAttributeNS(XMINS, "xmi:type", "uml:Model");
        model.setAttribute("name", "EA_Model");
        model.setAttribute("visibility", "public");

        // genereerExtensie(parent);

        Element logischModel = (Element) model.appendChild(doc.createElement("packagedElement"));
        logischModel.setAttributeNS(XMINS, "xmi:type", "uml:Package");
        SoortExport soortExport = SoortExport.getHuidigeSoort();
        logischModel.setAttributeNS(XMINS, "xmi:id",
                getId(ExportRegel.II_MODEL, soortExport.getOmschrijving(), soortExport.ordinal()));
        logischModel.setAttribute("name", soortExport.getOmschrijving());
        logischModel.setAttribute("visibility", "public");

        InSetOfModel inSom;
        if (soortExport == SoortExport.LM) {
            inSom = InSetOfModel.M;
        } else {
            inSom = InSetOfModel.S;
        }

        Element basisTypePackage = (Element) logischModel.appendChild(doc.createElement("packagedElement"));
        basisTypePackage.setAttributeNS(XMINS, "xmi:type", "uml:Package");
        basisTypePackage.setAttributeNS(XMINS, "xmi:id",
                getId(ExportRegel.II_BASISTYPEN, "BasisTypen", SoortExport.getHuidigeSoort().ordinal()));
        basisTypePackage.setAttribute("name", "BasisTypen");
        basisTypePackage.setAttribute("visibility", "public");
        for (BasisType basisType : basisTypen) {
            genereer(basisType, basisTypePackage);
        }

        for (Schema schema : domein.getNonEmptySchemas(inSom)) {
            Iterable<ObjectType> objectTypes = schema.getVersie(versieTag).getObjectTypes(inSom);

            Element schemaElement = (Element) logischModel.appendChild(doc.createElement("packagedElement"));
            schemaElement.setAttributeNS(XMINS, "xmi:type", "uml:Package");
            schemaElement.setAttributeNS(XMINS, "xmi:id",
                    getId(ExportRegel.II_SCHEMA, schema.getNaam(), schema.getSyncId()));
            schemaElement.setAttribute("name", schema.getNaam());
            schemaElement.setAttribute("visibility", "public");
            /*
             * Attribuuttypen.
             */
            for (AttribuutType attribuutType : schema.getVersie(versieTag).getAttribuutTypes()) {
                genereer(attribuutType, schemaElement);
            }
            /*
             * Eerst alle objecttypen, zodat daaraan door de associaties gerefereerd kan worden.
             */
            for (ObjectType type : objectTypes) {
                genereerObjectType(type, schemaElement, inSom);
            }
            /*
             * En nu de associaties.
             */
            for (ObjectType type : objectTypes) {
                for (Attribuut attribuut : type.getObjectTypeAttributen(inSom)) {
                    genereerAssociatie(attribuut, schemaElement);
                }
            }
        }
    }

    private void genereer(final BasisType basisType, final Element parent) {
        Element basisTypeElement = (Element) parent.appendChild(doc.createElement("packagedElement"));
        basisTypeElement.setAttributeNS(XMINS, "xmi:type", "uml:PrimitiveType");
        String id = getId(ExportRegel.II_BASISTYPE, basisType.getNaam(), basisType.getSyncId());
        basisTypeElement.setAttributeNS(XMINS, "xmi:id", id);
        basisTypeElement.setAttribute("name", basisType.getNaam());
        basisTypeElement.setAttribute("visibility", "public");
        basisTypeElement.setAttribute("isAbstract", "true");
    }

    private void genereer(final AttribuutType attribuutType, final Element parent) {
        Element attribuutTypeElement = (Element) parent.appendChild(doc.createElement("packagedElement"));
        attribuutTypeElement.setAttributeNS(XMINS, "xmi:type", "uml:DataType");
        String id = getId(ExportRegel.II_ATTRIBUUTTYPE, attribuutType.getNaam(), attribuutType.getSyncId());
        attribuutTypeElement.setAttributeNS(XMINS, "xmi:id", id);
        attribuutTypeElement.setAttribute("name", attribuutType.getNaam());
        attribuutTypeElement.setAttribute("visibility", "public");

        Element generalization = (Element) attribuutTypeElement.appendChild(doc.createElement("generalization"));
        generalization.setAttributeNS(XMINS, "xmi:type", "uml:Generalization");
        id = getId(ExportRegel.II_ATTRIBUUTTYPE_TYPE, attribuutType.getNaam(), attribuutType.getSyncId());
        generalization.setAttributeNS(XMINS, "xmi:id", id);
        BasisType basisType = attribuutType.getType();
        generalization.setAttribute("general",
                getId(ExportRegel.II_BASISTYPE, basisType.getNaam(), basisType.getSyncId()));
    }

    private void genereerObjectType(final ObjectType objectType, final Element parent, final InSetOfModel inSom) {
        klassen++;
        /*
         * Voeg UMLNS element toe.
         */
        Element objectTypeElement = (Element) parent.appendChild(doc.createElement("packagedElement"));
        exportedElements.put(objectType, objectTypeElement);
        objectTypeElement.setAttributeNS(XMINS, "xmi:type", "uml:Class");
        String objectTypeId = getId(ExportRegel.II_OBJECTTYPE, objectType.getNaam(), objectType.getSyncId());
        objectTypeElement.setAttributeNS(XMINS, "xmi:id", objectTypeId);
        objectTypeElement.setAttribute("name", objectType.getNaam());
        objectTypeElement.setAttribute("visibility", "public");
        ObjectType superType = objectType.getSuperType();
        if (superType != null) {
            Element superTypeElement = (Element) objectTypeElement.appendChild(doc.createElement("generalization"));
            superTypeElement.setAttributeNS(XMINS, "xmi:type", "uml:Generalization");
            superTypeElement.setAttribute("general",
                    getId(ExportRegel.II_OBJECTTYPE, superType.getNaam(), superType.getSyncId()));
            superTypeElement.setAttributeNS(XMINS, "xmi:id",
                    getId(ExportRegel.II_SUPERTYPE, objectType.getNaam(), objectType.getSyncId()));
        }
        /*
         * Commentaar.
         */
        for (Tekst tekst : objectType.getTeksten().values()) {
            Element comment = (Element) objectTypeElement.appendChild(doc.createElement("ownedComment"));
            comment.setAttributeNS(XMINS, "xmi:id", tekst.xmlId());
            comment.setAttribute("annotatedElement", objectTypeId);
            Element body = (Element) comment.appendChild(doc.createElement("body"));
            body.appendChild(doc.createTextNode(tekst.getTekst()));
        }
        /*
         * Voeg EA-specifiek extension element toe voor stereotype voor groepen en voor teksten.
         */
        Element elementContainer = getExtensionElementContainer(doc);
        Element xObjectTypeElement = doc.createElement("element");
        extensionElements.put(objectType, xObjectTypeElement);
        elementContainer.appendChild(xObjectTypeElement);
        xObjectTypeElement.setAttributeNS(XMINS, "xmi:idref", objectTypeId);

        Element propertiesElement = doc.createElement("properties");
        xObjectTypeElement.appendChild(propertiesElement);
        propertiesElement.setAttribute("sType", "Class");
        if (!objectType.getTeksten().isEmpty()) {
            propertiesElement.setAttribute("documentation", concatenateTeksten(objectType));
        }

        Element attributeContainer = doc.createElement("attributes");
        xObjectTypeElement.appendChild(attributeContainer);
        /*
         * Voeg attributen toe.
         */
        int position = 0;
        for (Attribuut att : objectType.getAttributen(inSom)) {
            attributen++;
            Type type = att.getType();
            Element attributeElement = (Element) objectTypeElement.appendChild(doc.createElement("ownedAttribute"));
            exportedElements.put(att, attributeElement);
            attributeElement.setAttributeNS(XMINS, "xmi:type", "uml:Property");
            String attributeId =
                getId(ExportRegel.II_ATTRIBUTE, objectType.getNaam() + "." + att.getNaam(), att.getSyncId());
            attributeElement.setAttributeNS(XMINS, "xmi:id", attributeId);
            attributeElement.setAttribute("name", att.getNaam());
            attributeElement.setAttribute("visibility", "private");
            attributeElement.setAttribute("isDerived", boolean2Xmi(att.isAfleidbaar()));
            String soortType = null;
            if (type.isAttribuutType()) {
                soortType = ExportRegel.II_ATTRIBUUTTYPE;
            } else if (type.isObjectType()) {
                soortType = ExportRegel.II_OBJECTTYPE;
            }
            attributeElement.setAttribute("type", getId(soortType, type.getNaam(), type.getSyncId()));
            /*
             * Commentaar.
             */
            for (Tekst tekst : att.getTeksten().values()) {
                Element comment = (Element) attributeElement.appendChild(doc.createElement("ownedComment"));
                comment.setAttribute("annotatedElement", attributeId);
                comment.setAttributeNS(XMINS, "xmi:id", tekst.xmlId());
                Element body = (Element) comment.appendChild(doc.createElement("body"));
                body.appendChild(doc.createTextNode(tekst.getTekst()));
            }
            /*
             * Extension element.
             */
            Element xAttributeElement = doc.createElement("attribute");
            attributeContainer.appendChild(xAttributeElement);
            xAttributeElement.setAttributeNS(XMINS, "xmi:idref", attributeId);

            if (!att.getTeksten().isEmpty()) {
                propertiesElement = doc.createElement("documentation");
                xAttributeElement.appendChild(propertiesElement);
                propertiesElement.setAttribute("value", concatenateTeksten(att));
            }

            Groep groep = att.getGroep();
            if (Arrays.asList(inSom, InSetOfModel.B).contains(groep.getInSetOfModel())) {
                Element stereoTypeElement = doc.createElement("stereotype");
                xAttributeElement.appendChild(stereoTypeElement);
                stereoTypeElement.setAttribute("stereotype", groep.getIdentifierCode());
            }
            Element containmentElement = doc.createElement("containment");
            xAttributeElement.appendChild(containmentElement);
            containmentElement.setAttribute("position", String.valueOf(position++));
        }
    }

    /**
     * Voeg alle teksten bij een metamodel element samen tot &eacute;&eacute;n tekstblok.
     *
     * @param element Het metamodel element waarvan de teksten samengevoegd worden.
     * @return Het tekstblok met de samengevoegde teksten.
     */
    private String concatenateTeksten(final nl.bzk.brp.bmr.metamodel.Element element) {
        StringBuilder documentation = new StringBuilder();
        for (Tekst tekst : element.getTeksten().values()) {
            documentation.append(tekst.getTekst());
        }
        return documentation.toString();
    }

    /**
     * @param ownerDocument
     * @return
     */
    private Element getExtensionElementContainer(final Document ownerDocument) {
        NodeList nodeList = ownerDocument.getDocumentElement().getElementsByTagName("xmi:Extension");
        Element extension = (Element) nodeList.item(0);
        Element elementContainer = (Element) extension.getElementsByTagName("elements").item(0);
        return elementContainer;
    }

    private String getId(final String interneIdentifier, final String naam, final Integer syncId) {
        LOGGER.debug("getId({}, {}, {})", new Object[] { interneIdentifier, naam, syncId });
        return exportRegelRepository.getExportIdentifier(interneIdentifier, naam, syncId);
    }
}
