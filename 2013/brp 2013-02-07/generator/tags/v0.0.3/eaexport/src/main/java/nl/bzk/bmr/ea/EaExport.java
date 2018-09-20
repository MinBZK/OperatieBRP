/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.bmr.ea;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
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
import nl.bzk.brp.bmr.metamodel.Domein;
import nl.bzk.brp.bmr.metamodel.ExportRegel;
import nl.bzk.brp.bmr.metamodel.InSetOfModel;
import nl.bzk.brp.bmr.metamodel.Laag;
import nl.bzk.brp.bmr.metamodel.ObjectType;
import nl.bzk.brp.bmr.metamodel.Schema;
import nl.bzk.brp.bmr.metamodel.SoortExport;
import nl.bzk.brp.bmr.metamodel.Tekst;
import nl.bzk.brp.bmr.metamodel.Type;
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

    private static final String   XML             = "http://www.w3.org/2000/xmlns/";
    private static final String   XMI             = "http://schema.omg.org/spec/XMI/2.1";
    private static final String   UML             = "http://schema.omg.org/spec/UML/2.1";

    private static final Logger   LOGGER          = LoggerFactory.getLogger(EaExport.class);

    @Inject
    private DomeinRepository      domeinRepository;

    @Inject
    private ExportRegelRepository exportRegelRepository;

    private Set<Type>             exportedTypes   = new HashSet<Type>();
    private Set<Type>             unexportedTypes = new HashSet<Type>();
    private int                   attributen;
    private int                   associaties;

    @Transactional
    public void exportXMI(final String domeinNaam, final SoortExport soort, final String versieTag,
            final OutputStream output) throws Exception
    {
        Laag.setHuidigeLaag(Laag.LOGISCH_MODEL);
        Domein domein = null;
        try {
            domein = (Domein) domeinRepository.findByNaam(domeinNaam);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Domein '{}' niet gevonden. Einde.", domeinNaam);
            return;
        }
        LOGGER.info("Genereren Enterprise Architect XMI export voor Domein '{}'", domein.getNaam());

        SoortExport.setHuidigeSoort(soort);
        genereerEaExport(domein, versieTag, output);
        LOGGER.info(
                "Klaar. {} klassen, {} attributen en {} associaties geëxporteerd. Niet-geëxporteerde object typen: {}",
                new Object[] { exportedTypes.size(), attributen, associaties, unexportedTypes.size() });
    }

    private String boolean2Xmi(final boolean waarde) {
        return waarde ? "true" : "false";
    }

    private void genereerAssociatie(final Attribuut att, final Element parent) {
        ObjectType srcType = att.getObjectType();
        ObjectType dstType = (ObjectType) att.getType();
        if (!exportedTypes.contains(srcType)) {
            LOGGER.debug("Dangling reference to {}", srcType.getNaam());
            unexportedTypes.add(srcType);
        }
        if (!exportedTypes.contains(dstType)) {
            LOGGER.debug("Dangling reference to {}", dstType.getNaam());
            unexportedTypes.add(dstType);
        }
        if (!exportedTypes.contains(srcType) || !exportedTypes.contains(dstType)) {
            /*
             * Het heeft geen zin associaties tussen niet-meegeëxporteerde klassen te exporteren.
             */
            return;
        }

        associaties++;
        String naam = new StringBuilder().append(srcType.getNaam()).append(".").append(att.getNaam()).toString();
        /*
         * Id's.
         */
        String associationId = getId(ExportRegel.II_ASSOCIATION, naam, att.getSyncId());

        String associationDstId = getId(ExportRegel.II_ASSOCIATION_DST, naam, att.getSyncId());
        String dstTypeId = getId(ExportRegel.II_OBJECTTYPE, dstType.getNaam(), dstType.getSyncId());
        String associationDstCardHiId = getId(ExportRegel.II_ASSOCIATION_DST_CARD_HI, naam, att.getSyncId());
        String associationDstCardLoId = getId(ExportRegel.II_ASSOCIATION_DST_CARD_LO, naam, att.getSyncId());

        String associationSrcId = getId(ExportRegel.II_ASSOCIATION_SRC, naam, att.getSyncId());
        String srcTypeId = getId(ExportRegel.II_OBJECTTYPE, srcType.getNaam(), srcType.getSyncId());
        String associationSrcCardLoId = getId(ExportRegel.II_ASSOCIATION_SRC_CARD_LO, naam, att.getSyncId());
        String associationSrcCardHiId = getId(ExportRegel.II_ASSOCIATION_SRC_CARD_HI, naam, att.getSyncId());

        Document document = parent.getOwnerDocument();
        Element associatie = document.createElement("packagedElement");
        parent.appendChild(associatie);
        associatie.setAttributeNS(XMI, "xmi:type", "uml:Association");
        associatie.setAttributeNS(XMI, "xmi:id", associationId);
        associatie.setAttribute("visibility", "public");

        /*
         * Destination association end.
         */
        Element memberEnd = document.createElement("memberEnd");
        associatie.appendChild(memberEnd);
        memberEnd.setAttributeNS(XMI, "xmi:idref", associationDstId);

        Element ownedEnd = document.createElement("ownedEnd");
        associatie.appendChild(ownedEnd);
        ownedEnd.setAttributeNS(XMI, "xmi:type", "uml:Property");
        ownedEnd.setAttributeNS(XMI, "xmi:id", associationDstId);
        ownedEnd.setAttribute("association", associationId);
        if (!att.getNaam().equals(dstType.getNaam().toString())) {
            ownedEnd.setAttribute("name", att.getNaam());
        }
        ownedEnd.setAttribute("visibility", "public");
        ownedEnd.setAttribute("isStatic", "false");
        ownedEnd.setAttribute("isReadOnly", "false");
        ownedEnd.setAttribute("isDerived", "false");
        ownedEnd.setAttribute("isOrdered", "false");
        ownedEnd.setAttribute("isUnique", "true");
        ownedEnd.setAttribute("isDerivedUnion", "false");
        ownedEnd.setAttribute("aggregation", "none");

        Element type = document.createElement("type");
        ownedEnd.appendChild(type);
        type.setAttributeNS(XMI, "xmi:idref", dstTypeId);

        Element value = document.createElement("lowerValue");
        ownedEnd.appendChild(value);
        value.setAttributeNS(XMI, "xmi:type", "uml:LiteralInteger");
        value.setAttributeNS(XMI, "xmi:id", associationDstCardLoId);
        value.setAttribute("value", att.isVerplicht() ? "1" : "0");

        value = document.createElement("upperValue");
        ownedEnd.appendChild(value);
        value.setAttributeNS(XMI, "xmi:type", "uml:LiteralInteger");
        value.setAttributeNS(XMI, "xmi:id", associationDstCardHiId);
        value.setAttribute("value", "1");

        /*
         * Source association end.
         */
        memberEnd = document.createElement("memberEnd");
        associatie.appendChild(memberEnd);
        memberEnd.setAttributeNS(XMI, "xmi:idref", associationSrcId);

        ownedEnd = document.createElement("ownedEnd");
        associatie.appendChild(ownedEnd);
        ownedEnd.setAttributeNS(XMI, "xmi:type", "uml:Property");
        ownedEnd.setAttributeNS(XMI, "xmi:id", associationSrcId);
        ownedEnd.setAttribute("association", associationId);
        ownedEnd.setAttribute("visibility", "public");
        ownedEnd.setAttribute("isStatic", "false");
        ownedEnd.setAttribute("isReadOnly", "false");
        ownedEnd.setAttribute("isDerived", "false");
        ownedEnd.setAttribute("isOrdered", "false");
        ownedEnd.setAttribute("isUnique", "true");
        ownedEnd.setAttribute("isDerivedUnion", "false");
        ownedEnd.setAttribute("aggregation", "none");

        type = document.createElement("type");
        ownedEnd.appendChild(type);
        type.setAttributeNS(XMI, "xmi:idref", srcTypeId);

        value = document.createElement("lowerValue");
        ownedEnd.appendChild(value);
        value.setAttributeNS(XMI, "xmi:type", "uml:LiteralInteger");
        value.setAttributeNS(XMI, "xmi:id", associationSrcCardLoId);
        value.setAttribute("value", "0");

        value = document.createElement("upperValue");
        ownedEnd.appendChild(value);
        value.setAttributeNS(XMI, "xmi:type", "uml:LiteralUnlimitedNatural");
        value.setAttributeNS(XMI, "xmi:id", associationSrcCardHiId);
        value.setAttribute("value", "-1");

    }

    private CharSequence genereerEaExport(final Domein domein, final String versieTag, final OutputStream output)
            throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        document.setXmlVersion("1.0");
        document.createAttributeNS(UML, "uml");
        Element root = document.createElementNS(XMI, "xmi:XMI");
        root.setAttribute("xmi:version", "2.1");
        root.setAttributeNS(XML, "xmlns:uml", UML);
        root.setAttributeNS(XML, "xmlns:xmi", XMI);
        document.appendChild(root);

        Element documentation = document.createElementNS(XMI, "xmi:Documentation");
        root.appendChild(documentation);
        documentation.setAttribute("exporter", "Enterprise Architect");
        documentation.setAttribute("exporterVersion", "6.5");

        genereerModel(domein, versieTag, root);

        serializeDocument(output, document);
        return null;
    }

    /**
     * Serialiseer de DOM tree naar een bestand als XML.
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
     * @param parent Het XML document element waaronder het model wordt opgehangen.
     */
    private void genereerExtensie(final Element parent) {
        Element extensie = parent.getOwnerDocument().createElementNS(XMI, "xmi:Extension");
        parent.appendChild(extensie);
        extensie.setAttribute("extender", "Enterprise Architect");
        extensie.setAttribute("extenderID", "6.5");
        Element elements = parent.getOwnerDocument().createElement("elements");
        extensie.appendChild(elements);
    }

    /**
     * Genereer een model in UML/XMI formaat.
     *
     * @param domein Het BMR domein waaruit het model wordt gegenereerd.
     * @param versieTag De tag van de versie van het model.
     * @param parent Het XML document element waaronder het model wordt opgehangen.
     * @throws Exception
     */
    private void genereerModel(final Domein domein, final String versieTag, final Element parent) throws Exception {
        Element model = parent.getOwnerDocument().createElementNS(UML, "uml:Model");
        parent.appendChild(model);
        model.setAttributeNS(XMI, "xmi:type", "uml:Model");
        model.setAttribute("name", domein.getNaam());
        model.setAttribute("visibility", "public");

        genereerExtensie(parent);

        Element logischModel = parent.getOwnerDocument().createElement("packagedElement");
        model.appendChild(logischModel);
        logischModel.setAttributeNS(XMI, "xmi:type", "uml:Package");
        logischModel.setAttributeNS(XMI, "xmi:id", SoortExport.getHuidigeSoort().getNaam());
        logischModel.setAttribute("name", SoortExport.getHuidigeSoort().getOmschrijving());
        logischModel.setAttribute("visibility", "public");

        InSetOfModel inSom;
        if (SoortExport.getHuidigeSoort() == SoortExport.LM) {
            inSom = InSetOfModel.M;
        } else {
            inSom = InSetOfModel.S;
        }

        for (Schema schema : domein.getNonEmptySchemas(inSom)) {
            Iterable<ObjectType> objectTypes = schema.getVersie(versieTag).getObjectTypes(inSom);

            Element schemaElement = parent.getOwnerDocument().createElement("packagedElement");
            logischModel.appendChild(schemaElement);
            schemaElement.setAttributeNS(XMI, "xmi:type", "uml:Package");
            schemaElement.setAttributeNS(XMI, "xmi:id",
                    getId(ExportRegel.II_SCHEMA, schema.getNaam(), schema.getSyncId()));
            schemaElement.setAttribute("name", schema.getNaam());
            schemaElement.setAttribute("visibility", "public");
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
                for (Attribuut attribuut : type.getObjectTypeAttributen()) {
                    genereerAssociatie(attribuut, schemaElement);
                }
            }
        }
    }

    private void genereerObjectType(final ObjectType type, final Element parent, final InSetOfModel inSom) {
        exportedTypes.add(type);
        Document ownerDocument = parent.getOwnerDocument();
        /*
         * Voeg UML element toe.
         */
        Element objectTypeElement = ownerDocument.createElement("packagedElement");
        parent.appendChild(objectTypeElement);
        objectTypeElement.setAttributeNS(XMI, "xmi:type", "uml:Class");
        String objectTypeId = getId(ExportRegel.II_OBJECTTYPE, type.getNaam(), type.getSyncId());
        objectTypeElement.setAttributeNS(XMI, "xmi:id", objectTypeId);
        objectTypeElement.setAttribute("name", type.getNaam());
        objectTypeElement.setAttribute("visibility", "public");
        /*
         * Voeg EA-specifiek extension element toe voor stereotype voor groepen en voor teksten.
         */
        Element elementContainer = getExtensionElementContainer(ownerDocument);
        Element xObjectTypeElement = ownerDocument.createElement("element");
        elementContainer.appendChild(xObjectTypeElement);
        xObjectTypeElement.setAttributeNS(XMI, "xmi:idref", objectTypeId);

        Element propertiesElement = ownerDocument.createElement("properties");
        xObjectTypeElement.appendChild(propertiesElement);
        propertiesElement.setAttribute("sType", "Class");
        if (!type.getTeksten().isEmpty()) {
            propertiesElement.setAttribute("documentation", concatenateTeksten(type));
        }

        Element attributeContainer = ownerDocument.createElement("attributes");
        xObjectTypeElement.appendChild(attributeContainer);
        /*
         * Voeg attributen toe.
         */
        int position = 0;
        for (Attribuut att : type.getAttributen(inSom)) {
            attributen++;
            Element attributeElement = ownerDocument.createElement("ownedAttribute");
            objectTypeElement.appendChild(attributeElement);
            attributeElement.setAttributeNS(XMI, "xmi:type", "uml:Property");
            String attributeId = getId(ExportRegel.II_ATTRIBUTE, type.getNaam() + "." + att.getNaam(), att.getSyncId());
            attributeElement.setAttributeNS(XMI, "xmi:id", attributeId);
            attributeElement.setAttribute("name", att.getNaam());
            attributeElement.setAttribute("visibility", "private");
            attributeElement.setAttribute("isStatic", "false");
            attributeElement.setAttribute("isReadOnly", "false");
            attributeElement.setAttribute("isDerived", boolean2Xmi(att.isAfleidbaar()));
            attributeElement.setAttribute("isOrdered", "false");
            attributeElement.setAttribute("isUnique", "true");
            attributeElement.setAttribute("isDerivedUnion", "false");

            Element typeElement = ownerDocument.createElement("type");
            attributeElement.appendChild(typeElement);
            if (att.getType().isAttribuutType()) {
                typeElement.setAttributeNS(XMI, "xmi:idref", javaType(((AttribuutType) att.getType()).getType()
                        .getNaam()));
            } else if (att.getType().isObjectType()) {
                switch (SoortExport.getHuidigeSoort()) {
                    case GS:
                        /*
                         * Voor de gegevensset maken we van een associatie een gewoon attribuut.
                         */
                        typeElement.setAttributeNS(XMI, "xmi:idref", javaType(att.getType().getNaam()));
                        break;
                    case LM:
                        /*
                         * Hiervan maken we straks een associatie. Hier hoeven we dus niks te doen.
                         */
                        break;
                    case DM:
                        /*
                         * Data Model doen we helemaal nog niet.
                         */
                        break;
                }
            } else {
                throw new IllegalStateException("Type kan alleen een attribuuttype of een objecttype zijn");
            }
            /*
             * Extension element.
             */
            Element xAttributeElement = ownerDocument.createElement("attribute");
            attributeContainer.appendChild(xAttributeElement);
            xAttributeElement.setAttributeNS(XMI, "xmi:idref", attributeId);

            if (!att.getTeksten().isEmpty()) {
                propertiesElement = ownerDocument.createElement("documentation");
                xAttributeElement.appendChild(propertiesElement);
                propertiesElement.setAttribute("value", concatenateTeksten(att));
            }

            if (Arrays.asList(inSom, InSetOfModel.B).contains(att.getGroep().getInSetOfModel())) {
                Element stereoTypeElement = ownerDocument.createElement("stereotype");
                xAttributeElement.appendChild(stereoTypeElement);
                stereoTypeElement.setAttribute("stereotype", att.getGroep().getNaam());
            }
            Element containmentElement = ownerDocument.createElement("containment");
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

    private String javaType(final String typeNaam) {
        String resultaat;
        if ("Geheel getal".equals(typeNaam)) {
            resultaat = "EAJava_Integer";
        } else if ("Groot geheel getal".equals(typeNaam)) {
            resultaat = "EAJava_Integer";
        } else if ("Klein geheel getal".equals(typeNaam)) {
            resultaat = "EAJava_Integer";
        } else if ("Decimaal getal".equals(typeNaam)) {
            resultaat = "EAJava_Float";
        } else if ("Tekst".equals(typeNaam)) {
            resultaat = "EAJava_String";
        } else if ("Onbeperkte tekst".equals(typeNaam)) {
            resultaat = "EAJava_String";
        } else if ("Ongestructureerde data".equals(typeNaam)) {
            resultaat = "EAJava_String";
        } else if ("Datum".equals(typeNaam)) {
            resultaat = "EAJava_Date";
        } else if ("Tijd".equals(typeNaam)) {
            resultaat = "EAJava_Date";
        } else if ("Datum/Tijd".equals(typeNaam)) {
            resultaat = "EAJava_Date";
        } else {
            resultaat = "EAJava_String";
        }
        return resultaat;
    }
}
