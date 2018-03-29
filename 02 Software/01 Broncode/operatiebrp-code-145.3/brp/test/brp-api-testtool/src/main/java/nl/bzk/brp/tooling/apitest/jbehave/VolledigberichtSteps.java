/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.jbehave;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ElementObject;
import nl.bzk.brp.test.common.xml.XPathHelper;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;

/**
 */
public class VolledigberichtSteps extends Steps {

    private static final String TARGET_DIR = "target/actuals/VolledigPersoonsBeeld/VolledigBericht";

    //uitgesloten attrs
    private static final Set<AttribuutElement> UITGESLOTEN_ATTRIBUTEN = Sets.newHashSet(

            //attributen van verantwoording die genegeerd worden
            getAttribuutElement(Element.ACTIE_DATUMAANVANGGELDIGHEID),
            getAttribuutElement(Element.ACTIE_DATUMEINDEGELDIGHEID),
            getAttribuutElement(Element.ACTIE_NADEREAANDUIDINGVERVAL),
            getAttribuutElement(Element.ACTIE_PEILDATUMVERWIJDERING),
            getAttribuutElement(Element.ADMINISTRATIEVEHANDELING_BIJHOUDINGSPLAN),
            getAttribuutElement(Element.ADMINISTRATIEVEHANDELING_TOELICHTINGONTLENING),

            //attributen van Persoon die genegeerd worden
            getAttribuutElement(Element.PERSOON_GEBOORTE_BUITENLANDSEREGIO),
            getAttribuutElement(Element.PERSOON_OUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN),
            getAttribuutElement(Element.PERSOON_GEBOORTE_WOONPLAATSNAAM),
            getAttribuutElement(Element.PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE),

            //attributen van Persoon.NummerVerwijzing die genegeerd worden
            getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VORIGEBURGERSERVICENUMMER),
            getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VOLGENDEBURGERSERVICENUMMER),
            getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER),

            //attributen van GerelateerdKind die genegeerd worden
            getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEREGIO),
            getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_WOONPLAATSNAAM),
            getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE),

            //attributen van gerelateerdeGeregistreerdePartner die genegeerd worden
            getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE),
            getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO),

            //attributen van GerelateerdeOuder die genegeerd worden
            getAttribuutElement(Element.GERELATEERDEOUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN),
            getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEREGIO),
            getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_WOONPLAATSNAAM),
            getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE),

            //attributen van GeregistreerdPartnerschap die genegeerd worden
            getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMAANVANG),
            getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMEINDE),
            getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOAANVANG),
            getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOEINDE),
            getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEAANVANG),
            getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEEINDE),

            //attributen van Huwelijk die genegeerd worden
            getAttribuutElement(Element.HUWELIJK_WOONPLAATSNAAMAANVANG),
            getAttribuutElement(Element.HUWELIJK_WOONPLAATSNAAMEINDE),
            getAttribuutElement(Element.HUWELIJK_BUITENLANDSEREGIOAANVANG),
            getAttribuutElement(Element.HUWELIJK_BUITENLANDSEREGIOEINDE),
            getAttribuutElement(Element.HUWELIJK_OMSCHRIJVINGLOCATIEAANVANG),
            getAttribuutElement(Element.HUWELIJK_OMSCHRIJVINGLOCATIEEINDE),

            //attributen van Onderzoek die genegeerd worden
            getAttribuutElement(Element.GEGEVENINONDERZOEK_OBJECTSLEUTELGEGEVEN),

            //attributen van de overlijden groep die genegeerd worden
            getAttribuutElement(Element.PERSOON_OVERLIJDEN_OMSCHRIJVINGLOCATIE),
            getAttribuutElement(Element.PERSOON_OVERLIJDEN_BUITENLANDSEREGIO),
            getAttribuutElement(Element.PERSOON_OVERLIJDEN_WOONPLAATSNAAM),

            //attributen van de adres groep die genegeerd worden
            getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4),
            getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5),
            getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6),
            getAttribuutElement(Element.PERSOON_ADRES_ACTIEVERVAL),
            getAttribuutElement(Element.PERSOON_ADRES_NADEREAANDUIDINGVERVAL),
            getAttribuutElement(Element.PERSOON_ADRES_ACTIEVERVALTBVLEVERINGMUTATIES),
            getAttribuutElement(Element.PERSOON_ADRES_ACTIEAANPASSINGGELDIGHEID),
            getAttribuutElement(Element.PERSOON_ADRES_INDICATIEVOORKOMENTBVLEVERINGMUTATIES),
            getAttribuutElement(Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES),

            //attributen van GerelateerdeHuwelijkspartner die genegeerd worden
            getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO),
            getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM),
            getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE),

            // attributen van GerelateerdeGeregistreerdePartner die genegeerd worden
            getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM),

            //attributen van Persoon.Migratie die genegeerd worden
            getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL4),
            getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL5),
            getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL6),

            //attributen van persoon.verstrekkingsbeperking die genegeerd worden
            getAttribuutElement(Element.PERSOON_VERSTREKKINGSBEPERKING_GEMEENTEVERORDENINGPARTIJCODE),
            getAttribuutElement(Element.PERSOON_VERSTREKKINGSBEPERKING_PARTIJCODE),
            getAttribuutElement(Element.PERSOON_VERSTREKKINGSBEPERKING_OMSCHRIJVINGDERDE)
    );


    // attributen met groepsautorisatie worden generiek gemapt en aanwezigheid in bericht hoeft niet voor alle attributen getest te worden.
    // dit is opsomming van attributen die wel getest worden op aanwezigheid
    private static final Set<AttribuutElement> ATTRIBUTEN_MET_GROEPSAUTORISATIE = Sets.newHashSet(
            getAttribuutElement(Element.PERSOON_ADRES_ACTIEINHOUD),
            getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGGELDIGHEID),
            getAttribuutElement(Element.PERSOON_ADRES_DATUMEINDEGELDIGHEID));


    // soorten autorisatie waarvoor attributen geleverd moeten worden.
    private static final EnumSet<SoortElementAutorisatie> SRT_AUTORISATIE_LEVEREN = EnumSet.of(
            SoortElementAutorisatie.AANBEVOLEN,
            SoortElementAutorisatie.BIJHOUDINGSGEGEVENS,
            SoortElementAutorisatie.OPTIONEEL,
            SoortElementAutorisatie.VERPLICHT,
            SoortElementAutorisatie.UITZONDERING);

    //mapping identiteitsgroep op xml naam
    private static final EnumMap<Element, String> ATTR_IDENTITEITGROEP_CONTAINERNAAM = new EnumMap<>(
            new ImmutableMap.Builder<Element, String>()
                    .put(Element.PERSOON_IDENTITEIT, "persoon")
                    .put(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT, "buitenlandsPersoonsnummer")
                    .put(Element.PERSOON_REISDOCUMENT_IDENTITEIT, "reisdocument")
                    .put(Element.PERSOON_ADRES_IDENTITEIT, "adres")
                    .put(Element.PERSOON_NATIONALITEIT_IDENTITEIT, "nationaliteit")
                    .put(Element.PERSOON_VOORNAAM_IDENTITEIT, "voornaam")
                    .put(Element.PERSOON_GESLACHTSNAAMCOMPONENT_IDENTITEIT, "geslachtsnaamcomponent")
                    .put(Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT, "afnemerindicatie")
                    .put(Element.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT, "volledigeVerstrekkingsbeperking")
                    .put(Element.PERSOON_VERIFICATIE_IDENTITEIT, "verificatie")
                    .put(Element.ADMINISTRATIEVEHANDELING_IDENTITEIT, "administratieveHandeling")
                    .put(Element.ACTIE_IDENTITEIT, "actie")
                    .put(Element.ONDERZOEK_IDENTITEIT, "onderzoek")
                    .put(Element.GEGEVENINONDERZOEK_IDENTITEIT, "gegevenInOnderzoek")
                    .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTITEIT, "huwelijk")
                    .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTITEIT, "geregistreerdPartnerschap")
                    .put(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT, "ouder")
                    .put(Element.GERELATEERDEKIND_PERSOON_IDENTITEIT, "kind")
                    .put(Element.ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEREGEL_IDENTITEIT, "gedeblokkeerdeMeldingen")
                    .build());

    //mapping standaardgroep op xml naam
    private static final EnumMap<Element, String> ATTR_STANDAARDGROEP_CONTAINERNAAM = new EnumMap<>(
            new ImmutableMap.Builder<Element, String>()
                    .put(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD, "buitenlandsPersoonsnummer") //leeg
                    .put(Element.PERSOON_REISDOCUMENT_STANDAARD, "reisdocument")
                    .put(Element.PERSOON_ADRES_STANDAARD, "adressen")
                    .put(Element.PERSOON_VOORNAAM_STANDAARD, "voornaam")
                    .put(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD, "geslachtsnaamcomponent")
                    .put(Element.PERSOON_AFNEMERINDICATIE_STANDAARD, "afnemerindicatie")
                    .put(Element.PERSOON_NATIONALITEIT_STANDAARD, "nationaliteit")
                    .put(Element.PERSOON_VERIFICATIE_STANDAARD, "verificatie")
                    .put(Element.HUWELIJK_STANDAARD, "huwelijk")
                    .put(Element.GEREGISTREERDPARTNERSCHAP_STANDAARD, "geregistreerdPartnerschap")
                    .put(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_STANDAARD, "volledigeVerstrekkingsbeperking")
                    .put(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_STANDAARD, "vastgesteldNietNederlander")
                    .put(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_STANDAARD, "behandeldAlsNederlander")
                    .put(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_STANDAARD,
                            "signaleringMetBetrekkingTotVerstrekkenReisdocument")
                    .put(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_STANDAARD, "derdeHeeftGezag")
                    .put(Element.PERSOON_INDICATIE_ONDERCURATELE_STANDAARD, "onderCuratele")
                    .put(Element.PERSOON_INDICATIE_STAATLOOS_STANDAARD, "staatloos")
                    .put(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_STANDAARD, "onverwerktDocumentAanwezig")
                    .put(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_STANDAARD, "bijzondereVerblijfsrechtelijkePositie")
                    .put(Element.ADMINISTRATIEVEHANDELING_STANDAARD, "administratieveHandeling")
                    .put(Element.ONDERZOEK_STANDAARD, "onderzoek")
                    .build());

    //mapping betrokkenheid op xml naam
    private static final EnumMap<Element, String> ATTR_BETROKKENHEDEN_CONTAINERNAAM = new EnumMap<>(
            new ImmutableMap.Builder<Element, String>()
                    .put(Element.GERELATEERDEOUDER_PERSOON, "ouder")
                    .put(Element.GERELATEERDEKIND_PERSOON, "kind")
                    .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON, "huwelijk")
                    .put(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG, "ouderlijkGezag")
                    .put(Element.GERELATEERDEOUDER_OUDERSCHAP, "ouderschap")
                    .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON, "geregistreerdPartnerschap")
                    .build());

    /**
     * Controleer volledigheid : alle geautoriseerde attributen moeten minimaal in 1 responsebericht aanwezig zijn.
     */
    @Then("alle elementen staan in bericht")
    public void controleerVolledigheid() throws IOException {
        Set<AttribuutElement> geautoriseerdeAttributen = geefGeautoriseerdeAttributen();
        List<String> responseBerichten = geefAlleResponseBerichten();
        Set<AttribuutElement> attrsNietInBericht = new HashSet<>();

        for (AttribuutElement attribuutElement : geautoriseerdeAttributen) {
            if (!assertAttribuutAanwezig(attribuutElement, responseBerichten)) {
                attrsNietInBericht.add(attribuutElement);
            }
        }

        if (!attrsNietInBericht.isEmpty()) {
            throw new AssertionError(String.format(
                    "Van de %d verwachte attributen, zijn de volgende %d attributen NIET gemapt :\n%s", geautoriseerdeAttributen.size(),
                    attrsNietInBericht.size(), attrsNietInBericht));
        }
    }

    private boolean assertAttribuutAanwezig(AttribuutElement attribuutElement, List<String> responseBerichten) {
        for (String bericht : responseBerichten) {
            if (attribuutInBericht(bericht, attribuutElement)) {
                return true;
            }
        }
        return false;
    }

    private boolean attribuutInBericht(String bericht, AttribuutElement attribuutElement) {
        final String containerNaam = bepaalContainerNaam(attribuutElement);
        return new XPathHelper().bestaatAttribuutNodeBinnenContainer(bericht, containerNaam, geefXmlNaamBericht(attribuutElement));
    }


    private String bepaalContainerNaam(AttribuutElement attribuutElement) {
        final String container;
        if (attribuutElement.getGroep().isIdentiteitGroep()) {
            container = ATTR_IDENTITEITGROEP_CONTAINERNAAM.get(attribuutElement.getGroep().getElement());
        } else if (attribuutElement.getGroep().isStandaardGroep()) {
            container = ATTR_STANDAARDGROEP_CONTAINERNAAM.get(attribuutElement.getGroep().getElement());
        } else if (ATTR_BETROKKENHEDEN_CONTAINERNAAM.containsKey(attribuutElement.getGroep().getObjectElement().getElement())) {
            container = ATTR_BETROKKENHEDEN_CONTAINERNAAM.get(attribuutElement.getGroep().getObjectElement().getElement());
        } else {
            container = geefXmlNaamBericht(attribuutElement.getGroep());
        }

        if (container == null || container.isEmpty()) {
            throw new IllegalStateException(String.format("Containernaam voor attribuutelement %s kan niet bepaald worden.", attribuutElement.getNaam()));
        }
        return container;
    }

    private List<String> geefAlleResponseBerichten() throws IOException {
        final List<String> responseBerichten = new ArrayList<String>();
        FileVisitor<Path> blobDirectoryVisitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file,
                                             BasicFileAttributes attr) {
                if (file.toString().endsWith("RESPONSE-1-ACTUAL.xml")) {
                    try {
                        responseBerichten.add(new String(Files.readAllBytes(file)));
                    } catch (IOException e) {
                        throw new RuntimeException("Inlezen reponse bericht mislukt :" + file.getFileName().toString(), e);
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        };
        Path file = Paths.get(TARGET_DIR);
        Files.walkFileTree(file, blobDirectoryVisitor);
        return responseBerichten;
    }

    private Set<AttribuutElement> geefGeautoriseerdeAttributen() {
        final Set<AttribuutElement> attrs = new HashSet<>();
        final Collection<AttribuutElement> alleAttributen = ElementHelper.getAttributen();
        for (AttribuutElement attr : alleAttributen) {
            if (SRT_AUTORISATIE_LEVEREN.contains(attr.getAutorisatie())) {
                attrs.add(attr);
            }
        }

        attrs.addAll(ATTRIBUTEN_MET_GROEPSAUTORISATIE);
        attrs.removeAll(UITGESLOTEN_ATTRIBUTEN);
        return attrs;
    }

    private String geefXmlNaamBericht(final ElementObject element) {
        return Character.toLowerCase(element.getXmlNaam().charAt(0)) + element.getXmlNaam().substring(1);
    }
}
