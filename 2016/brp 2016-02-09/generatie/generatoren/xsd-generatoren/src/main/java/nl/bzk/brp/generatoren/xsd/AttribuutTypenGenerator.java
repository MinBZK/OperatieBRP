/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.xsd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrTargetPlatform;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.dataaccess.BasisTypeOverrulePaar;
import nl.bzk.brp.generatoren.xsd.util.AttribuutTypenElementComparator;
import nl.bzk.brp.generatoren.xsd.util.TupleCodeComparator;
import nl.bzk.brp.generatoren.xsd.writer.XsdWriter;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.BasisType;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;
import nl.bzk.brp.metaregister.model.TypeImpl;
import nl.bzk.brp.metaregister.model.WaarderegelWaarde;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.springframework.stereotype.Component;

/**
 * XSD generator voor het bouwen van de attribuut typen uit het BMR.
 */
@Component("attribuutTypenXsdGenerator")
public class AttribuutTypenGenerator extends AbstractXsdGenerator {

    /** Commentaar voor bovenaan de xsd. */
    private static final String COMMENTAAR_BEGIN =
            "Schema met de definitie van de simple types (-s), "
            + "en de complex types die gebruikt worden in de berichten voor de BRP.";
    /** Commentaar voor boven de simpel types. */
    private static final String COMMENTAAR_SIMPEL_TYPES =
            "Simple types voor de attribuuttypen gesorteerd op alfabet.";
    /** Commentaar voor boven de complex types. */
    private static final String COMMENTAAR_COMPLEX_TYPES =
            "Complex types voor de attribuuttypen gesorteerd op alfabet.";

    @Inject
    private XsdWriter xsdWriter;

    @Inject
    private ObjectTypenGenerator objectTypenXsdGenerator;

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        Element rootElement = this.bouwRootElement(MINOR_VERSIE_ATTRIBUUT_TYPEN);
        Document document = new Document(rootElement);
        document.addContent(0, new Comment(COMMENTAAR_BEGIN));
        GeneratieUtil.voegVersieCommentaarToe(document, this);

        List<AttribuutType> attribuutTypen = this.getBmrDao().getAttribuutTypen();
        // Sorteer de attribuut typen op ident code, zodat ze makkelijker terug te vinden zijn.
        GeneratieUtil.sorteerOpIdentCode(attribuutTypen);

        // De lijst met ident codes van daadwerkelijk gebruikte attribuut typen (in xsd object typen).
        List<String> gebruikteAttribuutTypen = this.objectTypenXsdGenerator.getGebruikteAttribuutTypen();

        List<Element> simpelTypeElementen = new ArrayList<>();
        List<Element> complexTypeElementen = new ArrayList<>();
        for (AttribuutType attribuutType : attribuutTypen) {
            // Alleen aanmaken als het een gebruikt type is of expliciet op 'in bericht' staat.
            if (gebruikteAttribuutTypen.contains(attribuutType.getIdentCode())
                    || attribuutType.getInBericht() != null && attribuutType.getInBericht() == 'J'
                    )
            {
                genereerAttribuutType(attribuutType, simpelTypeElementen, complexTypeElementen);
            }
        }
        Set<String> gegenereerdeTussenTypes = new HashSet<>();
        for (String gebruiktAttribuutType : gebruikteAttribuutTypen) {
            // Als het type op 'Naam' eindigt, dan hebben we te maken met een zgn 'tussentype',
            // bij voor bepaalde statische stamgegevens worden die wel in de XSD aangemaakt,
            // ook al bestaan ze niet direct in het BMR. (en we het type niet al gehad hebben)
            if (gebruiktAttribuutType.endsWith("Naam") && !gegenereerdeTussenTypes.contains(gebruiktAttribuutType)) {
                gegenereerdeTussenTypes.add(gebruiktAttribuutType);
                // Zoek het bijbehorende object type.
                String objectTypeIdentCode = gebruiktAttribuutType
                        .substring(0, gebruiktAttribuutType.length() - "Naam".length());
                ObjectType objectType;
                try {
                    objectType = this.getBmrDao().getObjectTypeMetIdentCode(objectTypeIdentCode);
                } catch (NoResultException e) {
                    objectType = null;
                }
                // Als het object type gevonden kon worden en een statisch stamgegeven betreft, dan genereren.
                if (objectType != null && isStatischStamgegeven(objectType)) {
                    genereerAttribuutTussenType(objectType, simpelTypeElementen, complexTypeElementen);
                }
            }
        }

        // Voeg nu alle simpel en complex elementen gesorteerd op naam toe aan het root element,
        // zodat ze in die volgorde in de xsd komen.
        rootElement.addContent(new Comment(COMMENTAAR_SIMPEL_TYPES));
        Collections.sort(simpelTypeElementen, new AttribuutTypenElementComparator());
        for (Element element : simpelTypeElementen) {
            rootElement.addContent(element);
        }
        rootElement.addContent(new Comment(COMMENTAAR_COMPLEX_TYPES));
        Collections.sort(complexTypeElementen, new AttribuutTypenElementComparator());
        for (Element element : complexTypeElementen) {
            rootElement.addContent(element);
        }

        this.xsdWriter.writeXsd(generatorConfiguratie.getPad(),
                generatorConfiguratie.isOverschrijf(),
                "brp" + MAJOR_VERSIE + "_brpAttribuuttypen", document);
    }

    /**
     * Genereer een attribuut type, simpel en complex type definitie.
     * De methode voegt deze definities toe aan de meegegeven lijsten, voor later gebruik.
     *
     * @param attribuutType het attribuut type
     * @param simpelTypeElementen de lijst met simple types
     * @param complexTypeElementen de lijst met complext types
     */
    private void genereerAttribuutType(final AttribuutType attribuutType,
            final List<Element> simpelTypeElementen, final List<Element> complexTypeElementen)
    {
        BasisTypeOverrulePaar basisTypeOverrulePaar = this.getBmrDao().
                getBasisTypeVoorAttribuutType(attribuutType, BmrTargetPlatform.XSD);
        BasisType basisType = basisTypeOverrulePaar.getBasisType();
        TypeImpl typeImpl = basisTypeOverrulePaar.getTypeImpl();

        // Zet nu de waarden van min/max/decimalen/xsd patroon goed in het attribuut type object.
        // Deze kunnen namelijk eventueel overruled worden door de type impl.
        // Dat is het geval als er een attribuut type specifieke overrule bestaat
        // en als er geen data aanwezig is in het attribuut type zelf.
        if (basisTypeOverrulePaar.isOverrule()
                || attribuutType.getMinimumLengte() == null
                && attribuutType.getMaximumLengte() == null
                && attribuutType.getAantalDecimalen() == null)
        {
            attribuutType.setMinimumLengte(typeImpl.getMinimumLengte());
            attribuutType.setMaximumLengte(typeImpl.getMaximumLengte());
            attribuutType.setAantalDecimalen(typeImpl.getAantalDecimalen());
        }
        // XSD patroon wordt apart bekeken.
        if (basisTypeOverrulePaar.isOverrule() || attribuutType.getXsdPatroon() == null)
        {
            attribuutType.setXsdPatroon(typeImpl.getXsdPatroon());
        }

        // Verzamel alle simpel en complex type elementen.
        simpelTypeElementen.add(bouwSimpelTypeVoorAttribuutType(attribuutType, basisType.getNaam()));
        complexTypeElementen.add(bouwComplexTypeVoorAttribuutType(attribuutType));
    }

    /**
     * Genereer een attribuut type, simpel en complex type definitie.
     * De methode voegt deze definities toe aan de meegegeven lijsten, voor later gebruik.
     * Alleen specifiek voor de 'naam-tussentypes' voor statische stamgegevens.
     *
     * @param statischStamgegeven het statische stamgegeven
     * @param simpelTypeElementen de lijst met simple types
     * @param complexTypeElementen de lijst met complext types
     */
    private void genereerAttribuutTussenType(final ObjectType statischStamgegeven,
            final List<Element> simpelTypeElementen, final List<Element> complexTypeElementen)
    {
        String typeNaam = statischStamgegeven.getIdentCode() + "Naam";
        Element simpleTypeElement = this.bouwElement("simpleType", "name", typeNaam + "-s");

        // Altijd enumeraties op basis van strings.
        Element restrictionElement = this.bouwElement("restriction", "base", "string");
        simpleTypeElement.addContent(restrictionElement);

        List<Tuple> tuples = this.getBmrDao().getTupleEnumeratieVoorObjectType(statischStamgegeven);
        if (tuples.size() == 0) {
            throw new IllegalStateException("Geen tuple waardes gevonden voor 'naam-tussentype' "
                    + statischStamgegeven.getNaam() + ".");
        }

        // Voeg alle tuple restrictions toe.
        for (Tuple tuple : tuples) {
            Element enumerationElement = this.bouwElement("enumeration", "value", tuple.getNaam());
            String documentatie = this.getDocumentatie(tuple, false);
            if (documentatie != null) {
                enumerationElement.addContent(this.bouwDocumentatieElement(documentatie));
            }
            restrictionElement.addContent(enumerationElement);
        }
        simpelTypeElementen.add(simpleTypeElement);

        // Nu nog een complex type element maken.
        Element complexTypeElement = this.bouwElement("complexType", "name", typeNaam);
        Element simpleContentElement = this.bouwElement("simpleContent");
        Element extensionElement = this.bouwElement("extension", "base", "brp:" + typeNaam + "-s");
        simpleContentElement.addContent(extensionElement);
        complexTypeElement.addContent(simpleContentElement);
        complexTypeElementen.add(complexTypeElement);
    }

    /**
     * Bouw een XSD simpel type voor het gegeven attribuut type, met het meegegeven xsd type.
     * NB: Gebruikt voor min/max/decimalen/xsd patroon de waarden uit het attribuut type object,
     * aangezien de in de aanroepende methode correct gezet (moeten) zijn.
     *
     * @param attribuutType het attribuut type waar het om gaat
     * @param xsdTypeNaam de naam van het xsd type
     * @return het opgebouwde element
     */
    private Element bouwSimpelTypeVoorAttribuutType(
            final AttribuutType attribuutType, final String xsdTypeNaam)
    {
        Integer minimumLengte = attribuutType.getMinimumLengte();
        Integer maximumLengte = attribuutType.getMaximumLengte();
        Integer aantalDecimalen = attribuutType.getAantalDecimalen();
        String xsdPatroon = attribuutType.getXsdPatroon();

        Element attribuutTypeElement = this.bouwElement("simpleType", "name",
                getAttribuutTypeElementNaam(attribuutType) + "-s");

        Element restrictionElement = this.bouwElement("restriction", "base", xsdTypeNaam);
        attribuutTypeElement.addContent(restrictionElement);

        // Haal alle enumeratie restrictie element op.
        List<Element> enumeratieRestrictieElementen = this.genereerEnumeratieRestricties(attribuutType);

        // Als er geen minimum lengte bekend is dan gebruiken we een default minimum lengte van 1,
        // om lege strings als verkapte nill's te voorkomen.
        if (minimumLengte == null && xsdTypeNaam.equals("string")) {
            minimumLengte = 1;
        }

        // Voeg alleen maar minimum en maximum lengte restricties toe als er geen enumeratie waardes zijn.
        // Als die er wel zijn, hebben de lengte restricties namelijk geen toegevoegde waarde.
        if (enumeratieRestrictieElementen.size() == 0) {
            if (minimumLengte != null && minimumLengte > -1) {
                restrictionElement.addContent(this.bouwElement("minLength", "value", "" + minimumLengte));
            }
            if (maximumLengte != null && maximumLengte > -1) {
                String tagName = "maxLength";
                if (xsdTypeNaam.equals("decimal")) {
                    // Uitzondering voor decimals.
                    tagName = "totalDigits";
                }
                restrictionElement.addContent(this.bouwElement(tagName, "value", "" + maximumLengte));
            }
        }

        if (aantalDecimalen != null && aantalDecimalen > -1) {
            restrictionElement.addContent(this.bouwElement("fractionDigits", "value", "" + aantalDecimalen));
        }
        if (xsdPatroon != null && !xsdPatroon.trim().equals("")) {
            restrictionElement.addContent(this.bouwElement("pattern", "value", xsdPatroon.trim()));
        }

        // Voeg als laatste nog de enumeratie restricties toe (kan leeg zijn).
        restrictionElement.addContent(enumeratieRestrictieElementen);

        return attribuutTypeElement;
    }

    /**
     * Bepaal de naam van het element voor dit attribuut type.
     * Is de ident code, behalve als de xsd ident is ingevuld.
     *
     * @param type attribuut type
     * @return de element naam
     */
    private String getAttribuutTypeElementNaam(final AttribuutType type) {
        String elementNaam;
        // Speciale uitzondering: als er een xsd_ident voor dit attribuut type bestaat, gebruik die naam
        // in plaats van de ident code.
        if (StringUtils.isNotBlank(type.getXsdIdentificatie())) {
            elementNaam = type.getXsdIdentificatie();
        } else {
            elementNaam = type.getIdentCode();
        }
        return elementNaam;
    }

    /**
     * Genereer de enumeratie restricties. Dit kan zijn op basis van tupel waardes of
     * waarderegel waardes. Zie code voor details.
     *
     * @param attribuutType het attribuut type
     * @return de lijst met enumeratie restrictie elementen
     */
    private List<Element> genereerEnumeratieRestricties(final AttribuutType attribuutType) {
        List<Element> enumeratieRestrictieElementen = new ArrayList<>();

        // Haal nu de tuples en waarderegel waardes voor dit attribuut type op. Als het goed is,
        // is slechts een van beide constructies, of geen enkele van toepassing.

        List<Tuple> tuples = this.getBmrDao().getTupleEnumeratieVoorAttribuutType(attribuutType);
        List<WaarderegelWaarde> waarderegelWaardes =
                this.getBmrDao().getWaardeEnumeratiesVoorElement(attribuutType, true, false);

        if (tuples.size() > 0 && waarderegelWaardes.size() > 0) {
            throw new IllegalStateException("Zowel een enumeratie gevonden voor tuples als waarderegel waardes.");
        }

        // Loop de tuples of waarderegel waardes af en voeg de mogelijke waardes als enumatation elementen
        // toe aan de XSD.
        // NOTE: Er zit wat code duplicatie in onderstaande for loops, maar die abstraheren maakt het
        // geheel er zeker niet mooier en leesbaarder op, vandaar deze pragmatische keuze.
        if (tuples.size() > 0) {
            // Als de codes van de tuples getallen zijn, dan sorteren we ze daarnaar (m.n. van toepassing op adm.hand.).
            if (tupleCodesZijnGetallen(tuples)) {
                Collections.sort(tuples, new TupleCodeComparator());
            }
            for (Tuple tuple : tuples) {
                Element enumerationElement = this.bouwElement("enumeration", "value", tuple.getCode());
                enumerationElement.addContent(this.bouwDocumentatieElement(this.getDocumentatie(tuple, true)));
                enumeratieRestrictieElementen.add(enumerationElement);
            }
        }

        if (waarderegelWaardes.size() > 0) {
            for (WaarderegelWaarde waarderegelWaarde : waarderegelWaardes) {
                Element enumerationElement = this.bouwElement("enumeration", "value", waarderegelWaarde.getWaarde());
                enumerationElement.addContent(this.bouwDocumentatieElement(waarderegelWaarde.getWeergave()));
                enumeratieRestrictieElementen.add(enumerationElement);
            }
        }

        // Speciaal geval: bij stamgegevens halen we het restrictielijstje op een andere manier op.
        if (attribuutType.getNaam().equals("Stamgegeven")) {
            for (ObjectType stamgegeven : getStamgegevensVoorGeneratie()) {
                enumeratieRestrictieElementen.add(this.bouwElement(
                        "enumeration", "value", stamgegeven.getIdentCode() + "Tabel"));
            }
        }

        return enumeratieRestrictieElementen;
    }

    /**
     * Bouw een complex XSD type voor het attribuuttype. Deze functioneert puur als wrapper om het simpel type.
     *
     * @param attribuutType het attribuut type
     * @return het opgebouwde complex element
     */
    private Element bouwComplexTypeVoorAttribuutType(final AttribuutType attribuutType) {
        String elementNaam = getAttribuutTypeElementNaam(attribuutType);
        Element attribuutTypeElement = this.bouwElement("complexType", "name", elementNaam);
        Element simpleContentElement = this.bouwElement("simpleContent");
        Element extensionElement = this.bouwElement("extension", "base", "brp:" + elementNaam + "-s");

        simpleContentElement.addContent(extensionElement);
        attribuutTypeElement.addContent(simpleContentElement);
        return attribuutTypeElement;
    }

    /**
     * Haal de documentatie op voor een tuple. Dat is zijn beschrijving, of als die leeg is, zijn naam.
     *
     * @param tuple het tuple
     * @param naamAlsBackup of de naam van de tuple als backup documentatie gebruikt kan worden
     * @return de beschrijving
     */
    private String getDocumentatie(final Tuple tuple, final boolean naamAlsBackup) {
        String documentatie = tuple.getBeschrijving();
        if ((documentatie == null || documentatie.equals("")) && naamAlsBackup) {
            documentatie = tuple.getNaam();
        }
        return documentatie;
    }

}
