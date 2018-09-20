/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.xsd;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorExceptie;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.BmrTargetPlatform;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.common.InverseAssociatie;
import nl.bzk.brp.generatoren.algemeen.dataaccess.BasisTypeOverrulePaar;
import nl.bzk.brp.generatoren.algemeen.dataaccess.BmrElementFilterObject;
import nl.bzk.brp.generatoren.xsd.util.StructuurType;
import nl.bzk.brp.generatoren.xsd.util.TupleCodeComparator;
import nl.bzk.brp.generatoren.xsd.util.XsdElement;
import nl.bzk.brp.generatoren.xsd.util.XsdNaamgevingUtil;
import nl.bzk.brp.generatoren.xsd.util.XsdObjectTypeContext;
import nl.bzk.brp.generatoren.xsd.writer.XsdWriter;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.SoortActieSoortAdmhnd;
import nl.bzk.brp.metaregister.model.Tuple;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;


/** XSD generator voor het bouwen van de object typen uit het BMR. */
@Component("objectTypenXsdGenerator")
public class ObjectTypenGenerator extends AbstractXsdGenerator {

    private static final int ID_ADM_HAND_ATTRIBUUT_IN_AFGL_ADM = 8321;

    private static Logger log = LoggerFactory.getLogger(ObjectTypenGenerator.class);

    /** Commentaar voor bovenaan de xsd. */
    private static final String COMMENTAAR_BEGIN =
        "Schema met de basis definities (supertypen) en "
            + "complexTypes zoals gebruikt in de berichten voor de BRP.";

    /** Commentaar voor boven de structuur includes. */
    private static final String COMMENTAAR_STRUCTUUR = " ... ";
    private static final String OBJECTTYPE_PREFIX    = "Objecttype_";
    private static final String BRP_NAMESPACE        = "brp:";

    @Inject
    private XsdWriter xsdWriter;

    // Mapping van choices op hun min en max waardes.
    // Key: ident code van bovenliggende tag, value: int[] met size 2: index 0 min value, index 1 max value.
    // Optioneel een 3e int die de specifieke max occurs van de elementen in de choice aangeeft.
    private Map<String, int[]> choiceMinMaxMap;

    // Specifieke collectie met object type ident codes die niet identificeerbaar zijn.
    private Set<String> nietIdentificeerbaar;

    /** Maak een nieuwe object type generator aan. */
    public ObjectTypenGenerator() {
        this.choiceMinMaxMap = new HashMap<>();
        // Fixed mapping voor min en max values. Er is geen patroon uit te herleiden, vandaar
        // deze oplossing. Eventueel in BMR toe te voegen.
        int[] nulUnbounded = new int[]{ 0, XsdElement.UNBOUNDED, XsdElement.UNBOUNDED };
        this.choiceMinMaxMap.put("ActieBronnen", new int[]{ 0, XsdElement.UNBOUNDED });
        this.choiceMinMaxMap.put("Bericht", new int[]{ 1, 1 });
        this.choiceMinMaxMap.put("PersoonBetrokkenheden", nulUnbounded);
        this.choiceMinMaxMap.put("Betrokkenheid", new int[]{ 0, 1 });
        this.choiceMinMaxMap.put("PersoonIndicaties", nulUnbounded);
        this.choiceMinMaxMap.put("RelatieBetrokkenheden", nulUnbounded);
        this.choiceMinMaxMap.put("AfgeleidAdministratief", new int[]{ 0, 1 });
        this.choiceMinMaxMap.put("AdministratieveHandelingBijhoudingen", new int[]{ 0, 1 });
        this.choiceMinMaxMap.put("AdministratieveHandelingBronnen", new int[]{ 0, XsdElement.UNBOUNDED });
        this.choiceMinMaxMap.put("AdministratieveHandelingActies", new int[]{ 0, 1 });
        this.choiceMinMaxMap.put("PersoonAdministratieveHandelingen", new int[]{ 0, 1 });

        this.nietIdentificeerbaar = new HashSet<>();
        this.nietIdentificeerbaar.add("Bericht");
    }

    /**
     * Haal alle gebruikte attribuut typen (ident codes) op. Dit dupliceert een beetje
     * de onderstaande code en een beetje veel de onderstaande processing, maar het is
     * de beste manier om de attribuut typen generator toch nog los aanroepbaar te houden.
     * De attribuut typen generator moet namelijk alleen attribuut typen genereren die
     * ook daadwerkelijk gebruikt worden in de object typen generator.
     *
     * @return de lijst met ident codes
     */
    public List<String> getGebruikteAttribuutTypen() {
        List<String> gebruikteAttribuutTypen = new ArrayList<>();

        // Haal eerst alle elementen op waar een attribuut type verwijzing in kan zitten.
        // Deze zijn aanwezig als kinderen van een object type, een groep of een inverse associatie.
        List<Element> teScannenElementen = new ArrayList<>();
        for (ObjectType objectType : this.getObjectTypenVoorGeneratie()) {
            XsdObjectTypeContext context = new XsdObjectTypeContext(objectType);
            this.bouwComplexTypeVoorObjectType(context);
            teScannenElementen.addAll(context.getToeTeVoegenElementen());
            for (Groep groep : context.getGevondenGroepen()) {
                teScannenElementen.add(this.bouwComplexTypeVoorGroep(objectType, groep));
            }
            for (InverseAssociatie inverseAssociatie : context.getGevondenInverseAssociaties()) {
                teScannenElementen.add(this.bouwComplexTypeVoorInverseAssociatie(inverseAssociatie));
            }
        }
        // Neem ook de stamgegevens elementen mee om te zoeken naar attribuut typen.
        teScannenElementen.addAll(this.bouwStamgegevensElementen());
        // Scan nu alle elementen naar verwijzingen naar attribuut typen.
        for (Element element : teScannenElementen) {
            gebruikteAttribuutTypen.addAll(this.verzamelGebruikteAttribuutTypen(element));
        }

        return gebruikteAttribuutTypen;
    }

    /** {@inheritDoc} */
    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        Element rootElement = this.bouwRootElement(MINOR_VERSIE_OBJECT_TYPEN);
        Document document = new Document(rootElement);
        document.addContent(0, new Comment(COMMENTAAR_BEGIN));
        GeneratieUtil.voegVersieCommentaarToe(document, this);

        rootElement.addContent(this.bouwIncludeElement("brp" + MAJOR_VERSIE + "_brpAttribuuttypen.xsd"));
        rootElement.addContent(new Comment(COMMENTAAR_STRUCTUUR));
        rootElement.addContent(this.bouwStandaardStructuren());

        final List<ObjectType> objectTypen = getObjectTypenVoorGeneratie();

        // Genereer voor alle betreffende object typen de benodigde stukken XSD.
        for (final ObjectType objectType : objectTypen) {
            // Een context voor het bijhouden van de elementen die voor een object type gevonden zijn.
            XsdObjectTypeContext context = new XsdObjectTypeContext(objectType);

            // Deze aanroep bouwt het complex type voor het object type en verzamelt ook de groepen
            // van het object type in de meegegeven lijst. Hierna worden de elementen voor deze groepen
            // verder uitgewerkt. Op deze manier nemen we alleen de daadwerkelijk gebruikte groepen mee.
            // Hetzelfde geldt voor de inverse associaties.
            rootElement.addContent(this.bouwComplexTypeVoorObjectType(context));

            // Itereer over de gevonden groepen en bouw hier ook complex typen voor.
            for (Groep groep : context.getGevondenGroepen()) {
                rootElement.addContent(this.bouwComplexTypeVoorGroep(objectType, groep));
            }

            // Itereer over de gevonden inverse associaties en bouw hier ook complex typen voor.
            for (InverseAssociatie inverseAssociatie : context.getGevondenInverseAssociaties()) {
                rootElement.addContent(this.bouwComplexTypeVoorInverseAssociatie(inverseAssociatie));
            }

            // Is er een view per discriminator waarde? Zo ja, maak deze dan aan.
            if (objectType.getXsdViewsPerDiscriminatorWaarde() != null
                && 'J' == objectType.getXsdViewsPerDiscriminatorWaarde())
            {
                rootElement.addContent(this.bouwComplexTypenVoorDiscriminatorViews(objectType));
            }
        }

        // Genereer nu nog het stuk XSD mbt de stamgegevens.
        rootElement.addContent(this.bouwStamgegevensElementen());

        // Alle benodigde info is verzameld, tijd om de file weg te schrijven.
        this.xsdWriter.writeXsd(generatorConfiguratie.getPad(),
            generatorConfiguratie.isOverschrijf(),
            "brp" + MAJOR_VERSIE + "_brpObjecttypen", document);
    }

    /**
     * Haal de lijst met object typen op die we voor de generatie willen inzetten.
     *
     * @return de lijst met object typen
     */
    private List<ObjectType> getObjectTypenVoorGeneratie() {
        BmrElementFilterObject filter = new BmrElementFilterObject(BmrLaag.LOGISCH);
        filter.setInBericht(true);
        filter.setSoortInhoud('D');
        List<ObjectType> objectTypen = this.getBmrDao().getObjectTypen(filter);

        // Pas nog een extra filter toe, op koppeling. Object types die puur als koppeling
        // fungeren, hoeven namelijk niet als zodanig gegenereerd te worden.
        objectTypen = new ArrayList<ObjectType>(Collections2.filter(objectTypen, new Predicate<ObjectType>() {
            @Override
            public boolean apply(final ObjectType objectType) {
                return objectType.getKoppeling() == null || objectType.getKoppeling() == 'N';
            }
        }));

        // Sorteer op naam, zodat object typen dan makkelijker te vinden zijn in de gegenereerde XSD.
        GeneratieUtil.sorteerOpNaam(objectTypen);
        return objectTypen;
    }

    /**
     * Bouw de standaard structuren op die altijd hetzelfde zijn, voor bovenaan de xsd.
     * Dit wordt gedaan door een stukje xsd snippet in te lezen en daaruit een DOM tree
     * op te bouwen.
     *
     * @return de lijst met standaard structuur elementen.
     */
    private List<Element> bouwStandaardStructuren() {
        InputStream structurenInputStream = null;
        try {
            structurenInputStream =
                ObjectTypenGenerator.class.getResourceAsStream("/standaardStructuren.xsd.snippet");
            return GeneratieUtil.bouwElementenUitXmlSnippet(structurenInputStream, false);
        } finally {
            IOUtils.closeQuietly(structurenInputStream);
        }
    }

    /**
     * Bouw een complex type element.
     *
     * @param context de context
     * @return het complex type element
     */
    private Element bouwComplexTypeVoorObjectType(final XsdObjectTypeContext context) {
        ObjectType objectType = context.getObjectType();
        // Bouw de groepen en inverse associaties op en sla het resultaat op in de context.
        this.bouwGroepen(context);
        this.bouwInverseAssociaties(context);

        // Voeg een eventuele custom xsd code fragment toe aan de context.
        if (StringUtils.isNotBlank(objectType.getXsdCodeFragment())) {
            List<Element> fragmenten = GeneratieUtil.bouwElementenUitXmlSnippet(objectType.getXsdCodeFragment(), false);
            for (Element fragment : fragmenten) {
                String sorteerNaam = fragment.getName();
                if (sorteerNaam.equals("element")) {
                    // Als het fragment een element definieert, gebruik dan de naam van dat element als sorteernaam.
                    sorteerNaam = fragment.getAttributeValue("name");
                }
                context.voegLosElementToe(sorteerNaam, fragment);
            }
        }

        // Voeg nu alle gevonden element uit de context toe aan de inhoud (sorteert automatisch in DTO).
        List<Element> inhoud = context.getToeTeVoegenElementen();

        final String naam = OBJECTTYPE_PREFIX + objectType.getIdentCode();
        final StructuurType subtypeStructuur = bepaalStructuurTypeVoorObjectType(objectType);
        String xsdBasisType = subtypeStructuur.getXsdType();
        if (this.heeftSupertype(objectType)) {
            final StructuurType supertypeStructuur = bepaalStructuurTypeVoorObjectType(objectType.getSuperType());
            if (supertypeStructuur != subtypeStructuur) {
                this.vulInhoudAanMetAfwijkendeStructuurInformatie(inhoud, supertypeStructuur, subtypeStructuur);
            }
            xsdBasisType = OBJECTTYPE_PREFIX + objectType.getSuperType().getIdentCode();
        }

        /*
         * NB: Hier volgt een aanpassing, specifiek voor Betrokkenheid, waarbij de xsd definitie zo wordt gewijzigd,
         * dat we aan de XSD 1.0 blijven voldoen in de latere restricties. De betreffende corner case is namelijk
         * een hiaat in XSD 1.0. In XSD 1.1 (draft) is dit opgelost, maar die is nog niet officieel gereleased.
         * Vandaar dat we er voor kiezen hier een workaround te implementeren. Zie voor meer informatie:
         * https://www.modernodam.nl/confluence/display/mGBA/XSD+definitie+voor+Betrokkenheid+-+Uitleg+bij+workaround
         * http://www.velocityreviews.com/forums/t167343-xsd-question-on-derivation-ok-restriction-5-4-2-a.html
         * http://www.w3.org/TR/xmlschema-1/#cos-particle-restrict
         */
        if (objectType.getIdentCode().equals("Betrokkenheid")) {
            // We gaan uit van 2 elementen in 'inhoud':
            // index 0: de choice van relaties
            // index 1: het persoon attribuut
            // Voor het fixen van de issue: verplaats het persoon attribuut binnen de choice van relaties.
            inhoud.get(0).addContent(inhoud.get(1));
            // Het persoon attribuut zit niet langer direct in de inhoud collectie.
            inhoud.remove(1);
        }

        return this.bouwComplexTypeElement(naam, BRP_NAMESPACE + xsdBasisType, inhoud);
    }

    /**
     * Bouw de groepen, inclusief de bijbehorende verwijzende elementen.
     * Voeg alles toe in de meegegeven context.
     *
     * @param context de context
     */
    private void bouwGroepen(final XsdObjectTypeContext context) {
        ObjectType objectType = context.getObjectType();
        for (Groep groep : this.getBmrDao().getGroepenVoorObjectType(objectType)) {
            if (this.behoortInXsdOnderEenObjectType(groep)) {
                context.voegGroepElementenToe(groep, this.bouwGroepAttributen(groep));
            } else if (this.behoortInXsdAlsLosStaandType(groep)) {
                context.voegGroepElementToe(groep, this.bouwVerwijzingNaarGroepElement(objectType, groep), true);
            }
        }
    }

    /**
     * Bouw de inverse associaties, inclusief de bijbehorende verwijzende elementen.
     * Voeg alles toe in de meegegeven context.
     *
     * @param context de context
     */
    private void bouwInverseAssociaties(final XsdObjectTypeContext context) {
        ObjectType objectType = context.getObjectType();

        List<Attribuut> inverseAssociatieAttributen = this.getBmrDao().getXsdInverseAttributenVoorObjectType(objectType);

        // Sorteer op inverse ident code voor leesbaarheid.
        Collections.sort(inverseAssociatieAttributen, new Comparator<Attribuut>() {
            @Override
            public int compare(final Attribuut attribuut1, final Attribuut attribuut2) {
                return attribuut1.getXsdInverseAssociatieIdentCode().compareTo(attribuut2.getXsdInverseAssociatieIdentCode());
            }
        });

        for (Attribuut inverseAssociatieAttribuut : inverseAssociatieAttributen) {
            String xsdInverseAssociatieIdentCode = inverseAssociatieAttribuut.getXsdInverseAssociatieIdentCode();

            ObjectType associatieObjectType = inverseAssociatieAttribuut.getObjectType();
            List<ObjectType> naarObjectTypen = new ArrayList<>();
            naarObjectTypen.add(associatieObjectType);
            // Maak een object aan en onthoudt hem, zodat we hiermee later de container kunnen opbouwen.
            // Het type van de elementen in de collectie kan verschillen. In principe is het van het
            // object type waar het attribuut 'aanhangt'. Dit kan echter overruled worden door de property
            // 'koppeling' van dat object type. In dat geval is het een pure koppel tabel en kan er
            // direct verwezen worden naar het te koppelen object type.
            if (associatieObjectType.getKoppeling() != null && associatieObjectType.getKoppeling() == 'J') {
                // Bijzonder geval: naar object type(n) van de koppeling.
                naarObjectTypen = this.bepaalGekoppeldeObjectTypen(inverseAssociatieAttribuut);
            }

            InverseAssociatie inverseAssociatie =
                new InverseAssociatie(objectType, xsdInverseAssociatieIdentCode, naarObjectTypen,
                        inverseAssociatieAttribuut.getXsdInverseAssociatieViewsPerDiscriminatorWaardeOnderdrukken());
            final String naam = GeneratieUtil.lowerTheFirstCharacter(xsdInverseAssociatieIdentCode);
            final String type =
                BRP_NAMESPACE + "Container_" + objectType.getIdentCode() + xsdInverseAssociatieIdentCode;
            Element element = this.bouwElement(new XsdElement(naam, type, false, false));
            context.voegInverseAssociatieToe(inverseAssociatie, element);
        }
    }

    /**
     * Bouwt een complex type voor groep.
     *
     * @param objectType Object type waar de groep in zit.
     * @param groep De groep.
     * @return Een complex type voor deze groep.
     */
    private Element bouwComplexTypeVoorGroep(final ObjectType objectType, final Groep groep) {
        final String naam = "Groep_" + objectType.getIdentCode() + this.bepaalGroepsNaamVoorXsd(groep);
        String basisXsdType = BRP_NAMESPACE + bepaalXsdBasisType(groep.getHistorieVastleggen()).getXsdType();
        return this.bouwComplexTypeElement(naam, basisXsdType, this.bouwGroepAttributen(groep));
    }

    /**
     * Bouwt een complex type voor een inverse associatie.
     *
     * @param inverseAssociatie de inverse associatie
     * @return het element
     */
    private Element bouwComplexTypeVoorInverseAssociatie(final InverseAssociatie inverseAssociatie) {
        ObjectType vanObjectType = inverseAssociatie.getVanObjectType();
        String identCode = inverseAssociatie.getIdentCode();
        List<Element> inhoud = new ArrayList<>();
        List<ObjectType> naarObjectTypen = inverseAssociatie.getNaarObjectTypen();

        for (ObjectType naarObjectType : naarObjectTypen) {
            Element gebouwdeElement = this.bouwElementVoorObjectTypeVerwijzing(
                vanObjectType.getIdentCode() + identCode, naarObjectType, inverseAssociatie.getInverseAssociatieViewsPerDiscriminatorWaardeOnderdrukken());
            // Specifieke override voor stamgegevens.
            if (naarObjectType.getSoortInhoud() != BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode()) {
                Attribuut logischeIdentiteitAttribuut = this.bepaalLogischeIdentiteitVoorStamgegeven(naarObjectType);
                String xsdTypeVoorAttribuut = BRP_NAMESPACE + logischeIdentiteitAttribuut.getType().getIdentCode();
                String elementNaam = GeneratieUtil.lowerTheFirstCharacter(
                    naarObjectType.getIdentCode() + logischeIdentiteitAttribuut.getIdentCode());
                gebouwdeElement = this.bouwElement(new XsdElement(
                    elementNaam, xsdTypeVoorAttribuut, false, true));
            }
            if (gebouwdeElement == null) {
                // Als in de bovenstaande aanroep geen element is aangemaakt,
                // hebben we te maken met een 'gewone' object referentie.
                final String naam = GeneratieUtil.lowerTheFirstCharacter(
                    naarObjectType.getIdentCode().replace(vanObjectType.getIdentCode(), ""));
                final String xsdType = BRP_NAMESPACE + OBJECTTYPE_PREFIX + naarObjectType.getIdentCode();
                gebouwdeElement = this.bouwElement(new XsdElement(naam, xsdType, false, true));
            }
            inhoud.add(gebouwdeElement);
        }
        // Als er meerdere 'naar' object typen zijn, dan gaat het om een choice naar 1 van deze typen.
        if (inhoud.size() > 1) {
            String bovenliggendeIdentCode = inverseAssociatie.getVanObjectType().getIdentCode()
                + inverseAssociatie.getIdentCode();
            // 'Wrap around' van een choice element om de inhoud elementen heen.
            int[] minMaxOccurs = this.choiceMinMaxMap.get(bovenliggendeIdentCode);
            if (minMaxOccurs == null) {
                throw new GeneratorExceptie("choiceMinMaxMap bevat geen waarde voor identCode " + bovenliggendeIdentCode);
            }
            Element choiceElement = this.bouwElement("choice", "minOccurs", "" + minMaxOccurs[0],
                "maxOccurs", this.getMaxOccursAsString(minMaxOccurs[1]));
            for (Element inhoudElement : inhoud) {
                choiceElement.addContent(inhoudElement);
            }
            inhoud.clear();
            inhoud.add(choiceElement);
        }
        final String naam = "Container_" + vanObjectType.getIdentCode() + identCode;
        return this.bouwComplexTypeElement(naam, BRP_NAMESPACE + StructuurType.STRUCTUUR.getXsdType(), inhoud);
    }

    /**
     * Bouwt de complex typen voor object typen die een discriminator attribuut hebben en per discriminator waarde een
     * view in de XSD krijgen.
     *
     * @param objectType Object type dat discriminator waarden kent.
     * @return Lijst van complex types.
     */
    private List<Element> bouwComplexTypenVoorDiscriminatorViews(final ObjectType objectType) {
        final List<Element> viewComplexTypen = new ArrayList<>();
        final String xsdViewPrefix = objectType.getXsdViewPrefix();
        final ObjectType discriminatorObjectType = getBmrDao().getDiscriminatorObjectType(objectType);

        if ("Administratieve handeling".equals(objectType.getNaam())) {
            viewComplexTypen.addAll(
                bouwViewComplexTypenVoorAdministratieveHandeling(objectType,
                    discriminatorObjectType, xsdViewPrefix));
        } else {
            final String basisType = BRP_NAMESPACE + OBJECTTYPE_PREFIX + objectType.getIdentCode();
            for (Tuple tuple : discriminatorObjectType.getTuples()) {
                String identifier = GeneratieUtil.maakIdentifier(tuple.getNaam());
                // Er zijn voorkomens met trema's die ergens in de database conversie verloren gaan.
                // Dit lossen we hierhandmatig op, niet fraai, maar het zij zo.
                if (identifier.startsWith("ConversieGBAMateri")) {
                    identifier = "ConversieGBAMaterieleHistorie";
                }
                final String naam = xsdViewPrefix + "_" + identifier;
                viewComplexTypen.add(this.bouwComplexTypeElement(naam, basisType, new ArrayList<Element>()));
            }
        }
        return viewComplexTypen;
    }

    /**
     * Bouwt de xsd elementen die in een groep complex type moeten. Kan ook gebruikt worden voor
     * het direct uitschrijven, omdat er extra checks aanwezig zijn en de lijst van elementen
     * naar keuze onder een groep of direct onder een object type gehangen kan worden.
     *
     * @param groep de groep
     * @return Een lijst van elementen behorende bij de complex type van de groep.
     */
    private List<Element> bouwGroepAttributen(final Groep groep) {
        final List<Element> elementen = new ArrayList<>();
        for (Attribuut attribuut : this.getBmrDao().getAttributenVanGroep(groep)) {
            if (this.behoortInXsd(attribuut)) {
                //FIXME: TODO: Temp adm.hand. op afgel.adm. eruit gefilterd omdat de status nog onduidelijk is.
                if (attribuut.getId() != ID_ADM_HAND_ATTRIBUUT_IN_AFGL_ADM) {
                    elementen.add(bouwElementVoorAttribuut(groep, attribuut));
                }
            }
        }
        return elementen;
    }

    /**
     * Bouwt een element voor een attribuut. Deze functie is generiek voor alle attributen,
     * dus zowel voor verwijzende (naar een ObjectType) als scalaire (van een AttribuutType).
     *
     * @param groep De groep waar het attribuut bij hoort.
     * @param attribuut Het attribuut.
     * @return Een xsd element.
     */
    private Element bouwElementVoorAttribuut(final Groep groep, final Attribuut attribuut) {
        Element element = null;
        GeneriekElement type = attribuut.getType();
        // Als het attribuut het logische attribuut van een stamgegeven IS (let op, dus niet verwijst naar)
        boolean isLogischeIdentiteitAttribuutVanStamgegeven = false;
        try {
            isLogischeIdentiteitAttribuutVanStamgegeven = isStamgegeven(attribuut.getObjectType()) && this.
                    bepaalLogischeIdentiteitVoorStamgegeven(attribuut.getObjectType()).getId() == attribuut.getId();
        } catch (GeneratorExceptie e) {
            // TODO: Er zijn stamgegevens met meerdere logische identiteit attributen, daar is nog geen oplossing voor.
            // Voor nu niet generator op laten crashen.
        }

        String xsdTypeVoorAttribuut = "";
        String elementNaam = "";

        BmrElementSoort elementSoort = BmrElementSoort.getBmrElementSoortBijCode(type.getSoortElement().getCode());

        // Optie 1: er is een handmatige XSD type override gedaan.
        if (StringUtils.isNotBlank(attribuut.getXsdType())) {
            elementNaam = XsdNaamgevingUtil.bepaalElementNaamVoorAttribuutTypeAttribuut(attribuut);
            xsdTypeVoorAttribuut = BRP_NAMESPACE + attribuut.getXsdType();
        // Optie 2 & 3: Het type van het attribuut is een verwijzend type, dus een ObjectType
        } else if (elementSoort == BmrElementSoort.OBJECTTYPE || isLogischeIdentiteitAttribuutVanStamgegeven) {
            ObjectType objectType;
            if (isLogischeIdentiteitAttribuutVanStamgegeven) {
                // We willen graag het logische identiteit attribuut van het stamgegeven zelf
                // op dezelfde manier genereren als bij een verwijzing naar een stamgegeven.
                objectType = attribuut.getObjectType();
            } else {
                // Haal het object type op dat dit generiek element eigenlijk 'is',
                // zodat we de specifieke velden kunnen gebruiken.
                objectType = this.getBmrDao().getObjectTypeMetIdentCode(type.getIdentCode());
            }

            // Optie 1: Het type van het attribuut is een stamgegeven.
            if (objectType.getSoortInhoud() != BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode()) {
                Attribuut logischeIdentiteitAttribuut = this.bepaalLogischeIdentiteitVoorStamgegeven(objectType);
                if (logischeIdentiteitAttribuut.getIdentCode().equals("Naam")
                        && objectType.getSoortInhoud() == BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode()
                        && logischeIdentiteitAttribuut.getType().getIdentCode().equals("NaamEnumeratiewaarde")
                        && (objectType.getXsdStatischStamgegevenUitschrijven() == null
                                || objectType.getXsdStatischStamgegevenUitschrijven() == 'J'))
                {
                    // Specifiek patroon voor naam als logische identiteit attribuut:
                    // maak gebruik van tussentype voor 'naam' ipv direct een string.
                    // (indien object type is statisch stamgegeven)
                    xsdTypeVoorAttribuut = BRP_NAMESPACE
                            + objectType.getIdentCode() + logischeIdentiteitAttribuut.getIdentCode();
                } else {
                    xsdTypeVoorAttribuut = BRP_NAMESPACE + logischeIdentiteitAttribuut.getType().getIdentCode();
                }
                if (isLogischeIdentiteitAttribuutVanStamgegeven) {
                    // 'Normale' naamgeving in geval van genereren logische identiteit attribuut zelf.
                    elementNaam = XsdNaamgevingUtil.bepaalElementNaamVoorAttribuutTypeAttribuut(attribuut);
                } else {
                    elementNaam = XsdNaamgevingUtil.bepaalElementNaamVoorStamgegevenAttribuut(attribuut,
                        logischeIdentiteitAttribuut);
                }

            // Optie 2: Het type van het attribuut is een dynamisch object type.
            } else if (type.getSoortInhoud() == BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode()) {

                String bovenliggendeIdentCode = groep.getIdentCode();
                if (this.behoortInXsdOnderEenObjectType(groep)) {
                    bovenliggendeIdentCode = groep.getObjectType().getIdentCode();
                }
                element = this.bouwElementVoorObjectTypeVerwijzing(bovenliggendeIdentCode, objectType, null);
                if (element == null) {
                    // Als in de bovenstaande aanroep geen element is aangemaakt,
                    // hebben we te maken met een 'gewone' object referentie.
                    xsdTypeVoorAttribuut = BRP_NAMESPACE + OBJECTTYPE_PREFIX + objectType.getIdentCode();
                    // In dit geval is de element naam afleiding gelijk aan die voor een attribuut type.
                    elementNaam = XsdNaamgevingUtil.bepaalElementNaamVoorAttribuutTypeAttribuut(attribuut);
                }

            }

        // Optie 4: Het type van het attribuut is scalair, dus een AttribuutType
        } else if (elementSoort == BmrElementSoort.ATTRIBUUTTYPE) {
            elementNaam = XsdNaamgevingUtil.bepaalElementNaamVoorAttribuutTypeAttribuut(attribuut);
            final AttribuutType attribuutType = this.getBmrDao().getElement(type.getId(), AttribuutType.class);
            BasisTypeOverrulePaar basisTypeOverrulePaar =
                this.getBmrDao().getBasisTypeVoorAttribuutType(attribuutType, BmrTargetPlatform.XSD);
            // Als er een ':' in de basis type naam voorkomt, dan is er een specifiek XSD type genoemd
            // en moeten we dat dus direct gebruiken, anders gebruiken we de default naamgeving.
            if (basisTypeOverrulePaar.getBasisType().getNaam().contains(":")) {
                xsdTypeVoorAttribuut = basisTypeOverrulePaar.getBasisType().getNaam();
            } else {
                // Speciale uitzondering: als er een xsd_ident voor dit attribuut type bestaat, gebruik die naam
                // in plaats van de ident code.
                if (StringUtils.isNotBlank(type.getXsdIdentificatie())) {
                    xsdTypeVoorAttribuut = BRP_NAMESPACE + type.getXsdIdentificatie();
                } else {
                    xsdTypeVoorAttribuut = BRP_NAMESPACE + type.getIdentCode();
                }
            }
        }

        // Als het element op dit moment nog null is, is het dus niet eerder gevuld en moeten
        // we zelf nog een element opbouwen voor dit attribuut.
        if (element == null) {
            XsdElement xsdElement = new XsdElement(
                    elementNaam, xsdTypeVoorAttribuut, this.isElementVerplichtInXsd(attribuut));

            // Specifiek override voor nillable=true in het geval van een attribuut type dat noValue ondersteunt.
            if (elementSoort == BmrElementSoort.ATTRIBUUTTYPE) {
                final AttribuutType attribuutType = this.getBmrDao().getElement(type.getId(), AttribuutType.class);
                if (attribuutType.getXsdOndersteuntNoValue() != null
                        && attribuutType.getXsdOndersteuntNoValue() == 'J')
                {
                    xsdElement.setNillable(true);
                }
            }
            element = this.bouwElement(xsdElement);
        }
        return element;
    }

    /**
     * Bouw een element met de verwijzing naar een object type.
     *
     * @param bovenliggendeIdentCode bovenliggende ident code (voor index in choice map)
     * @param naarObjectType naar welk object type
     * @return het element
     */
    private Element bouwElementVoorObjectTypeVerwijzing(
        final String bovenliggendeIdentCode, final ObjectType naarObjectType,
        final Character inverseAssociatieViewsPerDiscriminatorWaardeOnderdrukken)
    {
        Element gebouwdeElement = null;
        // Optie A: Het object type heeft subtypen, oftewel het is zelf een supertype.
        if (heeftSubtypen(naarObjectType)) {
            // Verzamel de finale subtypen, oftewel de bladeren onderaan de 'subtype-boom'.
            List<ObjectType> finaleSubtypen = verzamelFinaleSubtypen(naarObjectType);
            int[] minMaxOccurs = this.choiceMinMaxMap.get(bovenliggendeIdentCode);
            Element choiceElement = this.bouwElement("choice", "minOccurs", "" + minMaxOccurs[0],
                "maxOccurs", this.getMaxOccursAsString(minMaxOccurs[1]));
            for (ObjectType finaalSubtype : finaleSubtypen) {
                final String naam = GeneratieUtil.lowerTheFirstCharacter(finaalSubtype.getIdentCode());
                final String xsdType = BRP_NAMESPACE + OBJECTTYPE_PREFIX + finaalSubtype.getIdentCode();
                // Als er een derde element in de minMaxOccurs aanwezig is, geeft dat de max occurs aan.
                // @Checkstyle: Haha, now I can use 3 as a 'magic number' :).
                if (minMaxOccurs.length == 2 + 1) {
                    choiceElement.addContent(this.bouwElement(
                        new XsdElement(naam, xsdType, true, 0, minMaxOccurs[2])));
                } else {
                    choiceElement.addContent(this.bouwElement(new XsdElement(naam, xsdType)));
                }
            }
            gebouwdeElement = choiceElement;

        // Optie B: Het object type heeft een discriminator en deze wordt niet onderdrukt
        } else if (naarObjectType.getDiscriminatorAttribuut() != null
        		  	&& (inverseAssociatieViewsPerDiscriminatorWaardeOnderdrukken == null || inverseAssociatieViewsPerDiscriminatorWaardeOnderdrukken == 'N')) {
            gebouwdeElement = bouwVerwijzingNaarDiscriminatorViews(bovenliggendeIdentCode, naarObjectType);
        }
        return gebouwdeElement;
    }

    /**
     * Bouwt een xsd element waarvan het type een verwijzing is naar een groep complex type.
     *
     * @param objectType Object type waar de verwijzing bij hoort.
     * @param groep De groep waar naar verwezen moet worden.
     * @return Een xsd element die de verwijzing voorstelt.
     */
    private Element bouwVerwijzingNaarGroepElement(final ObjectType objectType, final Groep groep) {
        String groepsNaam = this.bepaalGroepsNaamVoorXsd(groep);
        String xsdTypeVoorGroep = BRP_NAMESPACE + "Groep_" + objectType.getIdentCode() + groepsNaam;
        // Check voor een override in het BMR.
        if (StringUtils.isNotBlank(groep.getXsdType())) {
            xsdTypeVoorGroep = groep.getXsdType();
        }

        final String naam = GeneratieUtil.lowerTheFirstCharacter(groepsNaam);
        return this.bouwElement(new XsdElement(naam, xsdTypeVoorGroep, this.isElementVerplichtInXsd(groep), true));
    }

    /**
     * Bouw een element met de verwijzing naar een discriminator view.
     *
     * @param bovenliggendeIdentCode de bovenliggende ident code.
     * @param objectType voor welk object type
     * @return het element
     */
    private Element bouwVerwijzingNaarDiscriminatorViews(
        final String bovenliggendeIdentCode, final ObjectType objectType)
    {
        ObjectType discriminatorObjectType = this.getBmrDao().getDiscriminatorObjectType(objectType);
        int[] minMaxOccurs = this.choiceMinMaxMap.get(bovenliggendeIdentCode);
        if (minMaxOccurs == null) {
            throw new GeneratorExceptie("choiceMinMaxMap bevat geen waarde voor identCode " + bovenliggendeIdentCode);
        }
        Element choiceElement = this.bouwElement("choice", "minOccurs", "" + minMaxOccurs[0],
            "maxOccurs", this.getMaxOccursAsString(minMaxOccurs[1]));
        List<Tuple> tuples = new ArrayList<Tuple>(discriminatorObjectType.getTuples());
        // Als de codes van de tuples getallen zijn, dan sorteren we ze daarnaar (m.n. van toepassing op adm.hand.).
        if (tupleCodesZijnGetallen(tuples)) {
            Collections.sort(tuples, new TupleCodeComparator());
        }
        for (Tuple tuple : tuples) {
            if (tuple.getInBericht() == null || tuple.getInBericht() == 'J') {
                String identifier = GeneratieUtil.maakIdentifier(tuple.getNaam());
                final String naam = GeneratieUtil.lowerTheFirstCharacter(identifier);
                final String type = BRP_NAMESPACE + objectType.getXsdViewPrefix() + "_" + identifier;
                if (minMaxOccurs.length == 2 + 1) {
                    choiceElement.addContent(this.bouwElement(
                            new XsdElement(naam, type, true, 0, minMaxOccurs[2])));
                } else {
                    choiceElement.addContent(this.bouwElement(new XsdElement(naam, type)));
                }
            }
        }
        return choiceElement;
    }

    /**
     * Vul de inhoud van het type aan, aan de hand van de verschillen tussen super type en subtype.
     * Dit is nodig, omdat anders historie veldjes gemist worden in een subtype.
     *
     * @param inhoud de XSD inhoud van het object type
     * @param supertypeStructuur de structuur van het supertype
     * @param subtypeStructuur de structuur van het subtype
     */
    private void vulInhoudAanMetAfwijkendeStructuurInformatie(final List<Element> inhoud,
            final StructuurType supertypeStructuur, final StructuurType subtypeStructuur)
    {
        InputStream structurenInputStream = null;
        try {
            if (supertypeStructuur == StructuurType.STRUCTUUR_IDENTIFICEERBAAR
                    && subtypeStructuur == StructuurType.STRUCTUUR_FORMELE_HISTORIE)
            {
                structurenInputStream =
                        ObjectTypenGenerator.class.getResourceAsStream(
                                "/structuurFormeleHistorieElementen.xsd.snippet");
            } else if (supertypeStructuur == StructuurType.STRUCTUUR_FORMELE_HISTORIE
                    && subtypeStructuur == StructuurType.STRUCTUUR_MATERIELE_HISTORIE)
            {
                structurenInputStream =
                        ObjectTypenGenerator.class.getResourceAsStream(
                                "/structuurMaterieleHistorieElementen.xsd.snippet");
            } else if (supertypeStructuur == StructuurType.STRUCTUUR_IDENTIFICEERBAAR
                    && subtypeStructuur == StructuurType.STRUCTUUR_MATERIELE_HISTORIE)
            {
                structurenInputStream =
                        ObjectTypenGenerator.class.getResourceAsStream(
                                "/structuurFormeleEnMaterieleHistorieElementen.xsd.snippet");
            }
            if (structurenInputStream != null) {
                // Als er een structuur aanvulling gevonden is, voeg dan de betreffende veldjes vooraan in.
                inhoud.addAll(0, GeneratieUtil.bouwElementenUitXmlSnippet(structurenInputStream, false));
            }
        } finally {
            if (structurenInputStream != null) {
                try {
                    structurenInputStream.close();
                } catch (IOException e) {
                    log.error("IOException bij het sluiten van een input stream.", e);
                }
            }
        }
    }

    /**
     * Bepaal van welke basisstructuur het objecttype een extensie moet zijn.
     * Is er een identiteit groep? Zo ja, heeft die historie?
     * Is er een standaard groep? Zo ja, heeft die historie?
     * Foutmelding indien beide groepen aanwezig en beiden voorzien van historie?
     * Historie patroon voor objecttype wordt dus een van beide.
     *
     * @param objectType het object type
     * @return de xsd basis structuur als structuur enum type
     */
    private StructuurType bepaalStructuurTypeVoorObjectType(final ObjectType objectType) {
        Groep standaardGroep = null;
        Groep identiteitGroep = null;
        for (Groep groep : this.getBmrDao().getGroepenVoorObjectType(objectType)) {
            if (isIdentiteitGroep(groep)) {
                identiteitGroep = groep;
            } else if (isStandaardGroep(groep) && !isStandaardGroepMetHistorieVanNietLeafNode(groep)) {
                // We sluiten de standaard groep met historie van niet leaf nodes uit, omdat
                // deze apart uitgeschreven wordt en dus niet meetelt voor de historie van het object type.
                standaardGroep = groep;
            }
        }

        final boolean standaardGroepHeeftHistorie = standaardGroep != null
            && standaardGroep.getHistorieVastleggen() != null
            && standaardGroep.getHistorieVastleggen() != 'G';

        final boolean identiteitGroepHeeftHistorie = identiteitGroep != null
            && identiteitGroep.getHistorieVastleggen() != null
            && identiteitGroep.getHistorieVastleggen() != 'G';

        if (standaardGroepHeeftHistorie && identiteitGroepHeeftHistorie) {
            throw new GeneratorExceptie("Identiteitgroep en standaardgroep hebben beiden een historie patroon. "
                + "Objecttype = " + objectType.getNaam());
        }

        Character historieVastleggen = null;
        if (identiteitGroepHeeftHistorie) {
            historieVastleggen = identiteitGroep.getHistorieVastleggen();
        } else if (standaardGroepHeeftHistorie) {
            historieVastleggen = standaardGroep.getHistorieVastleggen();
        }

        StructuurType basisXsdType = bepaalXsdBasisType(historieVastleggen);
        if (this.nietIdentificeerbaar.contains(objectType.getIdentCode())) {
            basisXsdType = StructuurType.STRUCTUUR;
        }
        return basisXsdType;
    }

    /**
     * Bepaalt het xsd basis type voor een complex type.
     *
     * @param historieVastleggen Historie patroon volgens het BMR.
     * @return Het basis type as structuur enum type.
     */
    private StructuurType bepaalXsdBasisType(final Character historieVastleggen) {
        StructuurType basisXsdType = StructuurType.STRUCTUUR_IDENTIFICEERBAAR;
        if (historieVastleggen != null) {
            if (historieVastleggen == 'B') {
                basisXsdType = StructuurType.STRUCTUUR_MATERIELE_HISTORIE;
            } else if (historieVastleggen == 'F') {
                basisXsdType = StructuurType.STRUCTUUR_FORMELE_HISTORIE;
            } else if (historieVastleggen == 'P') {
                basisXsdType = StructuurType.STRUCTUUR_BESTAANSPERIODE;
            }
        }
        return basisXsdType;
    }

    /**
     * Bouwt de complex types voor de views die horen bij "Administratieve handeling".
     * Wijkt af van de 'standaard' discriminator view opbouw, vandaar een specifiek
     * uitgewerkte methode.
     *
     * @param objectType Objecttype administratieve handeling.
     * @param discriminatorObjectType Object type dat de discriminator waarden definieert. (Stamgegeven)
     * @param xsdViewPrefix Prefix voor de naam van de xsd complex type. (De view)
     * @return Lijst van complex typen.
     */
    private List<Element> bouwViewComplexTypenVoorAdministratieveHandeling(final ObjectType objectType,
        final ObjectType discriminatorObjectType,
        final String xsdViewPrefix)
    {
        List<Element> viewComplexTypen = new ArrayList<>();
        String basisType = BRP_NAMESPACE + OBJECTTYPE_PREFIX + objectType.getIdentCode();
        // ROMEO-336: Sorteer de soorten administratieve handeling op code, voor de leesbaarheid.
        List<Tuple> gesorteerdeTuples = new ArrayList<Tuple>(discriminatorObjectType.getTuples());
        Collections.sort(gesorteerdeTuples, new TupleCodeComparator());
        for (Tuple tuple : gesorteerdeTuples) {
            if (tuple.getInBericht() == null || tuple.getInBericht() == 'J') {
                final String containerNaamVoorActies = "Container_" + xsdViewPrefix + "_Acties_" + tuple.getIdentCode();
                final String typeNaam = xsdViewPrefix + "_" + tuple.getIdentCode();
                List<Element> inhoud = new ArrayList<>();
                inhoud.add(this.bouwElement(
                        new XsdElement("acties", BRP_NAMESPACE + containerNaamVoorActies, false, false)));

                viewComplexTypen.add(this.bouwComplexTypeElement(typeNaam, basisType, inhoud));

                List<SoortActieSoortAdmhnd> soortActieSoortAdmHandelingen =
                        this.getBmrDao().getSoortActiesVoorSoortAdministratieveHandeling(tuple);

                List<Element> containerInhoud = new ArrayList<>();
                for (SoortActieSoortAdmhnd soortActieSoortAdmhnd : soortActieSoortAdmHandelingen) {
                    final boolean nillable;
                    final int minOccurs;
                    final int maxOccurs;
                    if (soortActieSoortAdmhnd.getMinCardinaliteit() == null
                            || soortActieSoortAdmhnd.getMinCardinaliteit() == '0') {
                        minOccurs = 0;
                        nillable = true;
                    } else if (soortActieSoortAdmhnd.getMinCardinaliteit() == '*') {
                        throw new GeneratorExceptie("Minimale cardinaliteit kan niet * zijn.");
                    } else {
                        minOccurs = Character.getNumericValue(soortActieSoortAdmhnd.getMinCardinaliteit());
                        nillable = false;
                    }

                    if (soortActieSoortAdmhnd.getMaxCardinaliteit() == null
                            || soortActieSoortAdmhnd.getMaxCardinaliteit() == '*') {
                        maxOccurs = XsdElement.UNBOUNDED;
                    } else {
                        maxOccurs = Character.getNumericValue(soortActieSoortAdmhnd.getMaxCardinaliteit());
                    }

                    String identCode = soortActieSoortAdmhnd.getElementBySoortActie().getIdentCode();
                    final String naam = GeneratieUtil.lowerTheFirstCharacter(identCode);
                    final String type = BRP_NAMESPACE + "Actie_" + identCode;
                    containerInhoud.add(this.bouwElement(new XsdElement(naam, type, nillable, minOccurs, maxOccurs)));
                }
                viewComplexTypen.add(
                        this.bouwComplexTypeElement(containerNaamVoorActies, BRP_NAMESPACE + "Structuur", containerInhoud));
            }
        }
        return viewComplexTypen;
    }

    /**
     * Bouw de elementen die nodig zijn voor het specifieke XSD stuk mbt stamgegevens.
     *
     * @return lijst van elementen
     */
    private List<Element> bouwStamgegevensElementen() {
        List<Element> stamgegevensElementen = new ArrayList<>();
        stamgegevensElementen.add(this.bouwComplexTypeElement(
                "Container_BerichtStamgegevens", BRP_NAMESPACE + "Structuur", Arrays.asList(bouwStamgegevensChoice())));
        for (ObjectType stamgegeven : getStamgegevensVoorGeneratie()) {
            stamgegevensElementen.add(bouwStamgegevenContainerComplexType(stamgegeven));
            stamgegevensElementen.add(bouwStamgegevenObjectTypeComplexType(stamgegeven));
        }
        return stamgegevensElementen;
    }

    /**
     * Bouw de choice met alle stamgegevens.
     *
     * @return het choice element
     */
    private Element bouwStamgegevensChoice() {
        Element stamgegevensChoice = this.bouwElement("choice", "minOccurs", "1", "maxOccurs", "unbounded");

        for (ObjectType stamgegeven : getStamgegevensVoorGeneratie()) {
            String naam = GeneratieUtil.lowerTheFirstCharacter(stamgegeven.getIdentCode()) + "Tabel";
            String type = BRP_NAMESPACE + "Container_" + stamgegeven.getIdentCode();
            stamgegevensChoice.addContent(this.bouwElement(new XsdElement(naam, type)));
        }

        return stamgegevensChoice;
    }

    /**
     * Bouw een container complex type voor een stamgegeven.
     *
     * @param stamgegeven het stamgegeven
     * @return de container
     */
    private Element bouwStamgegevenContainerComplexType(final ObjectType stamgegeven) {
        String naam = GeneratieUtil.lowerTheFirstCharacter(stamgegeven.getIdentCode());
        String type = BRP_NAMESPACE + "Objecttype_" + stamgegeven.getIdentCode();
        Element element = this.bouwElement(new XsdElement(naam, type, true, 0, XsdElement.UNBOUNDED));
        return this.bouwComplexTypeElement(
                "Container_" + stamgegeven.getIdentCode(), BRP_NAMESPACE + "Structuur", Arrays.asList(element));
    }

    /**
     * Bouw een object type complex type voor een stamgegeven.
     *
     * @param stamgegeven het stamgegeven
     * @return het object type
     */
    private Element bouwStamgegevenObjectTypeComplexType(final ObjectType stamgegeven) {
        List<Element> attribuutElementen = new ArrayList<>();
        for (Attribuut attribuut : this.getBmrDao().getAttributenVanObjectType(stamgegeven)) {
            if (!attribuut.getNaam().equals("ID")
                    && (attribuut.getInBericht() == null || attribuut.getInBericht() == 'J'))
            {
                attribuutElementen.add(bouwElementVoorAttribuut(attribuut.getGroep(), attribuut));
            }
        }
        // Indien identiteit groep historie patroon 'bestaansperiode' heeft, nog 2 extra veldjes opnemen (DEG/DAG).
        List<Groep> groepen = this.getBmrDao().getGroepenVoorObjectType(stamgegeven);
        for (Groep groep : groepen) {
            if (groep.getNaam().equals(IDENTITEIT) && groep.getHistorieVastleggen() == 'P') {
                attribuutElementen.add(this.bouwElement(
                        new XsdElement("datumAanvangGeldigheid", "brp:DatumMetOnzekerheid", true, 0, 1)));
                attribuutElementen.add(this.bouwElement(
                        new XsdElement("datumEindeGeldigheid", "brp:DatumMetOnzekerheid", true, 0, 1)));
            }
        }
        return this.bouwComplexTypeElement(
                "Objecttype_" + stamgegeven.getIdentCode(), BRP_NAMESPACE + "StructuurIdentificeerbaar",
                attribuutElementen);
    }

}
