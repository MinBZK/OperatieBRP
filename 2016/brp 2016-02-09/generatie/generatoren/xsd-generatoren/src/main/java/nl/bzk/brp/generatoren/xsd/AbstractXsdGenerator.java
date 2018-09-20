/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.xsd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.dataaccess.BmrElementFilterObject;
import nl.bzk.brp.generatoren.xsd.util.XsdElement;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;
import org.jdom2.Namespace;


/** Utility klasse voor de XSD generatoren. Kan gebruikt worden als super klasse. */
public abstract class AbstractXsdGenerator extends AbstractGenerator {

    protected static final String NAMESPACE_ZONDER_VERSIE = "http://www.bzk.nl/brp/brp";
    protected static final String MAJOR_VERSIE = "0200";
    protected static final String MINOR_VERSIE_ATTRIBUUT_TYPEN = "020006";
    protected static final String MINOR_VERSIE_OBJECT_TYPEN = "020006";

    /** De namespace van het XML Schema. */
    public static final Namespace XSD_NAMESPACE  =
        Namespace.getNamespace("http://www.w3.org/2001/XMLSchema");

    /** De namespace van BRP. */
    public static final Namespace BRP_NAMESPACE =
        Namespace.getNamespace("brp", NAMESPACE_ZONDER_VERSIE + MAJOR_VERSIE);

    /**
     * Haal de lijst met stamgegevens op die we voor de generatie willen inzetten.
     *
     * @return de lijst met object typen
     */
    protected List<ObjectType> getStamgegevensVoorGeneratie() {
        BmrElementFilterObject filter = new BmrElementFilterObject(BmrLaag.LOGISCH);
        filter.setInBericht(true);
        // Alle niet dynamische object typen, oftewel de stamgegevens.
        filter.setSoortInhoud('D');
        filter.setSoortInhoudExclusief();
        List<ObjectType> objectTypen = this.getBmrDao().getObjectTypen(filter);

        // Sorteer op naam, zodat object typen dan makkelijker te vinden zijn in de gegenereerde XSD.
        GeneratieUtil.sorteerOpIdentCode(objectTypen);
        return objectTypen;
    }

    /**
     * Bouw een root element op voor de XSD, oftewel een 'schema' tag met namespaces etc.
     *
     * @param minorVersie de minor versie van de xsd, kan verschillen
     * @return het root element van een xsd
     */
    protected Element bouwRootElement(final String minorVersie) {
        // Bouw de root tag 'schema' op met alle namespaces en settings.
        Element root = new Element("schema", XSD_NAMESPACE);
        root.addNamespaceDeclaration(BRP_NAMESPACE);
        root.setAttribute("targetNamespace", NAMESPACE_ZONDER_VERSIE + MAJOR_VERSIE);
        root.setAttribute("elementFormDefault", "qualified");
        root.setAttribute("attributeFormDefault", "unqualified");
        root.setAttribute("version", minorVersie);

        return root;
    }

    /**
     * Bouw een (JDOM) element op uit een xsd element DTO.
     *
     * @param xsdElement het xsd element
     * @return het JDOM element
     */
    protected Element bouwElement(final XsdElement xsdElement) {
        return this.bouwElement("element",
            "name", xsdElement.getNaam(),
            "type", xsdElement.getType(),
            "nillable", "" + xsdElement.isNillable(),
            "minOccurs", "" + xsdElement.getMinOccurs(),
            "maxOccurs", this.getMaxOccursAsString(xsdElement.getMaxOccurs()));
    }

    /**
     * Retourneert de max occurs als string representatie. Geeft het getal als string terug,
     * behalve als het gelijk is aan XsdElement.UNBOUNDED (= Integer.MAX_VALUE),
     * dan geeft het "unbounded" terug.
     *
     * @param maxOccurs de integer waarde
     * @return de max occurs als string
     */
    protected String getMaxOccursAsString(final int maxOccurs) {
        String maxOccursString = "" + maxOccurs;
        if (maxOccurs == XsdElement.UNBOUNDED) {
            maxOccursString = "unbounded";
        }
        return maxOccursString;
    }

    /**
     * Bouw een documentatie element op, bestaande uit een annotation element met genest
     * documentation element met daarin de meegegeven tekst.
     *
     * @param documentatie de documentatie tekst
     * @return het documentatie element
     */
    protected Element bouwDocumentatieElement(final String documentatie) {
        Element annotatieElement = new Element("annotation");
        Element documentatieElement = new Element("documentation");
        documentatieElement.setText(documentatie);
        annotatieElement.addContent(documentatieElement);
        return annotatieElement;
    }

    /**
     * Bouw een (JDOM) include element.
     *
     * @param lokatie relatieve lokatie van het te includen xsd bestand
     * @return het opgebouwde element.
     */
    protected Element bouwIncludeElement(final String lokatie) {
        return this.bouwElement("include", "schemaLocation", lokatie);
    }

    /**
     * Bouw een (JDOM) complex type element.
     *
     * @param naam de naam van het complex type
     * @param basisType het basis type voor dit complex type
     * @param inhoud de elementen die in de sequence moeten komen
     * @return het opgebouwde element.
     */
    protected Element bouwComplexTypeElement(final String naam, final String basisType, final List<Element> inhoud) {
        Element complexType = this.bouwElement("complexType", "name", naam);
        Element complextContent = this.bouwElement("complexContent");
        Element extension = bouwElement("extension", "base", basisType);
        Element sequence = this.bouwElement("sequence");
        sequence.addContent(inhoud);
        extension.addContent(sequence);
        complextContent.addContent(extension);
        complexType.addContent(complextContent);
        return complexType;
    }

    /**
     * Bouw een (JDOM) element op met behulp van de parameters.
     *
     * @param naam de naam van het element (de tag)
     * @param attributenEnWaardes de lijst van attribuut namen en waardes (attr1, value1, attr2, value2, ...)
     * @return het opgebouwde element.
     */
    protected Element bouwElement(final String naam, final String... attributenEnWaardes) {
        if (attributenEnWaardes.length % 2 != 0) {
            throw new IllegalArgumentException("attributenEnWaardes moet een even aantal elementen bevatten:"
                + "paren van attribuutnaam en waarde.");
        }
        Element element = new Element(naam);
        for (int i = 0; i < attributenEnWaardes.length; i += 2) {
            element.setAttribute(attributenEnWaardes[i], attributenEnWaardes[i + 1]);
        }
        return element;
    }

    /**
     * Verzamel alle attribuut typen (ident codes) die in dit element en zijn kind elementen gebruikt worden.
     *
     * @param element het element
     * @return de lijst met ident codes
     */
    protected List<String> verzamelGebruikteAttribuutTypen(final Element element) {
        // Gebruik een accumulator om het telkens opnieuw aanmaken en 'optellen' van lijsten te voorkomen.
        List<String> accumulator = new ArrayList<>();
        this.verzamelGebruikteAttribuutTypenMetAccumulator(element, accumulator);
        return accumulator;
    }

    /**
     * Verzamel alle attribuut typen (ident codes) die in dit element en zijn kind elementen gebruikt worden.
     * Recursieve methode met behulp van een accumulator.
     *
     * @param element het element
     * @param accumulator de accumulator
     */
    private void verzamelGebruikteAttribuutTypenMetAccumulator(
        final Element element, final List<String> accumulator)
    {
        String brpNamespace = "brp:";
        // Als dit een 'element' element is, dan kijken we naar het type.
        if (element.getName().equals("element")) {
            String type = element.getAttributeValue("type");
            // Als het een brp type is en er geen underscore (_) in voor komt, nemen we dit type over.
            // Zo sluiten we alle verwijzingen naar object typen, containers, etc uit.
            if (type.startsWith(brpNamespace) && !type.contains("_")) {
                accumulator.add(type.substring(brpNamespace.length()));
            }
        } else {
            for (Element kindElement : element.getChildren()) {
                this.verzamelGebruikteAttribuutTypenMetAccumulator(kindElement, accumulator);
            }
        }
    }

    /**
     * Gaat na of alle tuples een geheel getal als code hebben.
     * NB: null telt niet als getal
     *
     * @param tuples de tuples
     * @return of alle tuples een geheel getal als code hebben of niet
     */
    protected boolean tupleCodesZijnGetallen(final List<Tuple> tuples) {
        boolean zijnGetallen = true;
        for (Tuple tuple : tuples) {
            zijnGetallen &= StringUtils.isNumeric(tuple.getCode());
        }
        return zijnGetallen;
    }

}
