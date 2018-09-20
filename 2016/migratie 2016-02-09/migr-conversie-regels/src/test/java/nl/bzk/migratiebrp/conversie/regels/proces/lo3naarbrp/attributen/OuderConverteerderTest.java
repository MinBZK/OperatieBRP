/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenRelatie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class OuderConverteerderTest extends AbstractRelatieConverteerderTest {

    @Inject
    private OuderConverteerder ouderConverteerder;

    private Lo3OuderInhoud.Builder ouderBuilder;
    private final List<ExpectedBetrokkene> expectedBetrokkenen = new ArrayList<>();
    private int expectedAantalIstOuderGroepen;
    private int expectedAantalIstGezagsverhoudingGroepen;
    private boolean isOuder2;

    @Before
    public void setUp() {
        ouderBuilder = new Lo3OuderInhoud.Builder();
        ouderBuilder.anummer(Lo3Long.wrap(A_NUMMER));
        ouderBuilder.burgerservicenummer(Lo3Integer.wrap(BSN));
        ouderBuilder.voornamen(Lo3String.wrap(VOORNAAM));
        ouderBuilder.geslachtsnaam(Lo3String.wrap(GESLACHTSNAAM));
        ouderBuilder.geboortedatum(new Lo3Datum(DATUM_GEBOORTE));
        ouderBuilder.geboorteGemeenteCode(new Lo3GemeenteCode(GEMEENTE_CODE));
        ouderBuilder.geboorteLandCode(new Lo3LandCode(LAND_CODE));
        ouderBuilder.geslachtsaanduiding(Lo3GeslachtsaanduidingEnum.MAN.asElement());
        ouderBuilder.familierechtelijkeBetrekking(new Lo3Datum(DATUM_FAMILIERECHTELIJKE_BETREKKING));

        expectedAantalIstGezagsverhoudingGroepen = 0;
        isOuder2 = false;
        Logging.initContext();
    }

    @After
    public void tearDown() {
        Logging.destroyContext();
    }

    private Lo3Categorie<Lo3OuderInhoud> maakOuderCategorie(
        final Lo3OuderInhoud inhoud,
        final Lo3Documentatie documentatie,
        final Lo3Historie historie,
        final Lo3Herkomst herkomst)
    {
        return new Lo3Categorie<>(inhoud, documentatie, historie, herkomst);
    }

    private Lo3Categorie<Lo3GezagsverhoudingInhoud> maakGezagsverhoudingCategorie(
        final Lo3GezagsverhoudingInhoud inhoud,
        final Lo3Documentatie documentatie,
        final Lo3Historie historie,
        final Lo3Herkomst herkomst)
    {
        return new Lo3Categorie<>(inhoud, documentatie, historie, herkomst);
    }

    private Lo3OuderInhoud maakLegeInhoud() {
        final Lo3OuderInhoud.Builder ouderInhoudBuilder = new Lo3OuderInhoud.Builder(ouderBuilder.build());
        ouderInhoudBuilder.anummer(null);
        ouderInhoudBuilder.burgerservicenummer(null);
        ouderInhoudBuilder.voornamen(null);
        ouderInhoudBuilder.geslachtsnaam(null);
        ouderInhoudBuilder.geboortedatum(null);
        ouderInhoudBuilder.geboorteGemeenteCode(null);
        ouderInhoudBuilder.geboorteLandCode(null);
        ouderInhoudBuilder.geslachtsaanduiding(null);
        ouderInhoudBuilder.familierechtelijkeBetrekking(null);
        return ouderInhoudBuilder.build();
    }

    private Lo3OuderInhoud.Builder maakPuntOuderBuilder() {
        final Lo3OuderInhoud.Builder ouderInhoudBuilder = new Lo3OuderInhoud.Builder(ouderBuilder.build());
        ouderInhoudBuilder.anummer(null);
        ouderInhoudBuilder.burgerservicenummer(null);
        ouderInhoudBuilder.voornamen(null);
        ouderInhoudBuilder.geslachtsnaam(Lo3String.wrap("."));
        ouderInhoudBuilder.geboortedatum(null);
        ouderInhoudBuilder.geboorteGemeenteCode(null);
        ouderInhoudBuilder.geboorteLandCode(null);
        ouderInhoudBuilder.geslachtsaanduiding(null);
        return ouderInhoudBuilder;
    }

    /**
     * Situatie 1: 1 enkel voorkomen gevuld met gegevens en niet onjuist<br>
     * Test: Dit test Relatiegegevens, niet lege voorkomen stap 1<br>
     * Input:<br>
     * Cat02 gevuld, niet onjuist, 6210 A, 8510 B<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat02<br>
     */
    @Test
    public void testEnkelVoorkomen() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(1);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));

        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 1;
        converteerEnCheck(new Lo3Stapel<>(Collections.singletonList(actOuder)));
    }

    /**
     * Situatie 1a: 1 enkel voorkomen gevuld met gegevens en niet onjuist voor Ouder2<br>
     * Test: Dit test Relatiegegevens, niet lege voorkomen stap 1<br>
     * Input:<br>
     * Cat03 gevuld, niet onjuist, 6210 A, 8510 B<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat03<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat03<br>
     */
    @Test
    public void testEnkelVoorkomenOuder2() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(Lo3CategorieEnum.CATEGORIE_03, 1);

        // Cat03
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));

        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 1;
        isOuder2 = true;
        converteerEnCheck(new Lo3Stapel<>(Collections.singletonList(actOuder)));
    }

    /**
     * Situatie 2: 3 voorkomens, waarbij 1 voorkomen de 62.10 en 85.10 een gelijke waarde bevat.<br>
     * Test: Dit test Relatiegegevens, niet lege voorkomen stap 2<br>
     * Input:<br>
     * Cat02 gevuld, niet onjuist, 6210 A, 8510 B<br>
     * Cat52-1 gevuld, niet onjuist, 6210 A, 8510 C<br>
     * Cat52-2 gevuld, niet onjuist, 6210 A, 8510 A<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat52-2<br>
     */
    @Test
    public void testMeerdereVoorkomen() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(3);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat52-1
        ouderBuilder.anummer(null);
        final Lo3OuderInhoud histInhoud1 = ouderBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(false, DATUM_GELDIGHEID_2, DATUM_OPNEMING_2);
        final Lo3Categorie<Lo3OuderInhoud> histOuder1 = maakOuderCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        // Cat52-2
        final Lo3OuderInhoud histInhoud2 = ouderBuilder.build();
        final Lo3Documentatie histDoc2 = maakAkte(Lo3String.wrap("3A"));
        final Lo3Historie histHistorie2 = maakHistorie(false, DATUM_FAMILIERECHTELIJKE_BETREKKING, DATUM_FAMILIERECHTELIJKE_BETREKKING);
        final Lo3Categorie<Lo3OuderInhoud> histOuder2 = maakOuderCategorie(histInhoud2, histDoc2, histHistorie2, herkomsten[2]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(histInhoud2, histDoc2, histHistorie2));

        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 3;
        converteerEnCheck(new Lo3Stapel<>(Arrays.asList(histOuder2, histOuder1, actOuder)));
    }

    /**
     * Situatie 3: 3 voorkomens met allen dezelfde 62.10, waarbij 1 voorkomen de 85.10 een standaarwaarde (00000000)
     * waarde bevat.<br>
     * Test: Dit test Relatiegegevens, niet lege voorkomen stap 3<br>
     * Input:<br>
     * Cat02 gevuld, niet onjuist, , 6210 A, 8510 B<br>
     * Cat52-1 gevuld, niet onjuist, 6210 A, 8510 STD<br>
     * Cat52-2 gevuld, niet onjuist, 6210 A, 8510 C<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat52-1<br>
     */
    @Test
    public void testMeerdereVoorkomen8510StandaardDatum() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(3);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat52-1
        ouderBuilder.anummer(null);
        final Lo3OuderInhoud histInhoud1 = ouderBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(false, DATUM_STANDAARD, DATUM_OPNEMING_2);
        final Lo3Categorie<Lo3OuderInhoud> histOuder1 = maakOuderCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        // Cat52-2
        final Lo3OuderInhoud histInhoud2 = ouderBuilder.build();
        final Lo3Documentatie histDoc2 = maakAkte(Lo3String.wrap("3A"));
        final Lo3Historie histHistorie2 = maakHistorie(false, DATUM_GELDIGHEID_3, DATUM_OPNEMING_3);
        final Lo3Categorie<Lo3OuderInhoud> histOuder2 = maakOuderCategorie(histInhoud2, histDoc2, histHistorie2, herkomsten[2]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(histInhoud1, histDoc1, histHistorie1));

        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 3;
        converteerEnCheck(new Lo3Stapel<>(Arrays.asList(histOuder2, histOuder1, actOuder)));
    }

    /**
     * Situatie 4: 2 voorkomens met allen dezelfde 62.10, met verschillende 85.10 en niet gelijk aan 62.10<br>
     * Test: Dit test Relatiegegevens, niet lege voorkomen stap 4<br>
     * Input:<br>
     * Cat02 gevuld, niet onjuist, 6210 A, 8510 B<br>
     * Cat52-1 gevuld, niet onjuist, 6210 A, 8510 C<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat02<br>
     */
    @Test
    public void testMeerdereVoorkomenVerschillende8510() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(2);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat52-1
        ouderBuilder.anummer(null);
        final Lo3OuderInhoud histInhoud1 = ouderBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(false, DATUM_GELDIGHEID_2, DATUM_OPNEMING_2);
        final Lo3Categorie<Lo3OuderInhoud> hist1Ouder = maakOuderCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));

        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 2;
        converteerEnCheck(new Lo3Stapel<>(Arrays.asList(hist1Ouder, actOuder)));
    }

    /**
     * Situatie 4a: 4 voorkomens, 2 met dezelfde 62.10 (A) en verschillende 85.10, 2 met dezelfde standaard 62.10 en
     * verschillende 85.10<br>
     * Test: Dit test Relatiegegevens, niet lege voorkomen stap 2 en 3<br>
     * Input:<br>
     * Cat02 gevuld, niet onjuist, 6210 A, 8510 B<br>
     * Cat52-1 gevuld, niet onjuist, 6210 A, 8510 A<br>
     * Cat52-2 gevuld, niet onjuist, 6210 STD, 8510 C<br>
     * Cat52-3 gevuld, niet onjuist, 6210 STD, 8510 STD<br>
     * <br>
     * Verwacht:<br>
     * - 2 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02, Cat52-2<br>
     * - 2 stapel voor ouderstapel gevuld vanuit Cat52-1 Cat51-3<br>
     */
    @Test
    public void testMeerdereWaarde6210MetStandaard() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(4);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat52-1
        ouderBuilder.anummer(null);
        final Lo3OuderInhoud histInhoud1 = ouderBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(false, DATUM_FAMILIERECHTELIJKE_BETREKKING, DATUM_OPNEMING_2);
        final Lo3Categorie<Lo3OuderInhoud> histOuder1 = maakOuderCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        // Cat52-2
        ouderBuilder.burgerservicenummer(null);
        ouderBuilder.familierechtelijkeBetrekking(Lo3Datum.NULL_DATUM);
        final Lo3OuderInhoud histInhoud2 = ouderBuilder.build();
        final Lo3Documentatie histDoc2 = maakAkte(Lo3String.wrap("3A"));
        final Lo3Historie histHistorie2 = maakHistorie(false, DATUM_GELDIGHEID_3, DATUM_OPNEMING_3);
        final Lo3Categorie<Lo3OuderInhoud> histOuder2 = maakOuderCategorie(histInhoud2, histDoc2, histHistorie2, herkomsten[2]);

        // Cat52-3
        ouderBuilder.geboortedatum(Lo3Datum.NULL_DATUM);
        final Lo3OuderInhoud histInhoud3 = ouderBuilder.build();
        final Lo3Documentatie histDoc3 = maakAkte(Lo3String.wrap("4A"));
        final Lo3Historie histHistorie3 = maakHistorie(false, DATUM_STANDAARD, DATUM_OPNEMING_4);
        final Lo3Categorie<Lo3OuderInhoud> histOuder3 = maakOuderCategorie(histInhoud3, histDoc3, histHistorie3, herkomsten[3]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(histInhoud1, histDoc1, histHistorie1));

        final ExpectedBetrokkene expectedBetrokkene1 = new ExpectedBetrokkene();
        expectedBetrokkene1.gpGroepen.add(new ExpectedOuderGroep(histInhoud2, histDoc2, histHistorie2));
        // expectedBetrokkene1.gpGroepen.add(new ExpectedOuderGroep(maakLegeInhoud(), histDoc1, histHistorie1));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(histInhoud3, histDoc3, histHistorie3));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(null, histDoc1, histHistorie1));

        expectedBetrokkenen.add(expectedBetrokkene1);
        expectedBetrokkenen.add(expectedBetrokkene);

        expectedAantalIstOuderGroepen = 4;
        converteerEnCheck(new Lo3Stapel<>(Arrays.asList(histOuder3, histOuder2, histOuder1, actOuder)));
    }

    /**
     * Situatie 5: Meerdere ouders in 1 stapel (2 voorkomens, verschillende 62.10)<br>
     * Test: Dit test het splitsen van de verschillende ouders<br>
     * Input:<br>
     * Cat02 gevuld, niet onjuist, 6210 A, 8510 B<br>
     * Cat52-1 gevuld, niet onjuist, 6210 C, 8510 D<br>
     * <br>
     * Verwacht:<br>
     * - 2 stapels voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02 en Cat52-1<br>
     * - 2 stapels voor ouderstapel gevuld vanuit Cat02 en Cat51-1<br>
     */
    @Test
    public void testMeerdereOudersInStapel() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(2);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat52-1
        ouderBuilder.anummer(Lo3Long.wrap(A_NUMMER_2));
        ouderBuilder.burgerservicenummer(Lo3Integer.wrap(BSN_2));
        ouderBuilder.voornamen(Lo3String.wrap(VOORNAAM_2));
        ouderBuilder.geboortedatum(new Lo3Datum(DATUM_GEBOORTE_2));
        ouderBuilder.geslachtsnaam(Lo3String.wrap(GESLACHTSNAAM_2));
        ouderBuilder.familierechtelijkeBetrekking(new Lo3Datum(DATUM_FAMILIERECHTELIJKE_BETREKKING_2));
        final Lo3OuderInhoud histInhoud1 = ouderBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(false, DATUM_GELDIGHEID_2, DATUM_OPNEMING_2);
        final Lo3Categorie<Lo3OuderInhoud> histOuder1 = maakOuderCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));

        final Lo3Historie afsluitendeHistorie = maakAfsluitendeHistorie(actInhoud, actHistorie);

        final ExpectedBetrokkene expectedBetrokkene1 = new ExpectedBetrokkene();
        expectedBetrokkene1.gpGroepen.add(new ExpectedOuderGroep(histInhoud1, histDoc1, histHistorie1));
        // expectedBetrokkene1.gpGroepen.add(new ExpectedOuderGroep(maakLegeInhoud(), actDoc, afsluitendeHistorie));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(histInhoud1, histDoc1, histHistorie1));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(null, actDoc, afsluitendeHistorie));

        expectedBetrokkenen.add(expectedBetrokkene1);
        expectedBetrokkenen.add(expectedBetrokkene);

        expectedAantalIstOuderGroepen = 2;
        converteerEnCheck(new Lo3Stapel<>(Arrays.asList(histOuder1, actOuder)));
    }

    /**
     * Situatie 6: Meerdere ouders in 1 stapel (2 voorkomens per ouders.)<br>
     * Test: Dit test het splitsen van de verschillende ouders en relatiegegevens, niet-leeg voorkomen stap 2<br>
     * Input:<br>
     * Cat02 gevuld, niet onjuist, 6210 A, 8510 C<br>
     * Cat52-1 gevuld, niet onjuist, 6210 A, 8510 A<br>
     * Cat52-2 gevuld, niet onjuist, 6210 B, 8510 D<br>
     * Cat52-3 gevuld, niet onjuist, 6210 B, 8510 B<br>
     * <br>
     * Verwacht:<br>
     * - 2 stapels voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02 en Cat52-2<br>
     * - 2 stapels voor ouderstapel gevuld vanuit Cat52-1 en Cat52-3<br>
     */
    @Test
    public void testMeerdereOuderMeerdereVoorkomens() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(4);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat52-1
        ouderBuilder.anummer(null);
        final Lo3OuderInhoud histInhoud1 = ouderBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(false, DATUM_FAMILIERECHTELIJKE_BETREKKING, DATUM_FAMILIERECHTELIJKE_BETREKKING);
        final Lo3Categorie<Lo3OuderInhoud> histOuder1 = maakOuderCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        // Cat52-2
        ouderBuilder.anummer(Lo3Long.wrap(A_NUMMER_2));
        ouderBuilder.burgerservicenummer(Lo3Integer.wrap(BSN_2));
        ouderBuilder.voornamen(Lo3String.wrap(VOORNAAM_2));
        ouderBuilder.geboortedatum(new Lo3Datum(DATUM_GEBOORTE_2));
        ouderBuilder.geslachtsnaam(Lo3String.wrap(GESLACHTSNAAM_2));
        ouderBuilder.familierechtelijkeBetrekking(new Lo3Datum(DATUM_FAMILIERECHTELIJKE_BETREKKING_2));
        final Lo3OuderInhoud histInhoud2 = ouderBuilder.build();
        final Lo3Documentatie histDoc2 = maakAkte(Lo3String.wrap("3A"));
        final Lo3Historie histHistorie2 = maakHistorie(false, DATUM_GELDIGHEID_2, DATUM_OPNEMING_2);
        final Lo3Categorie<Lo3OuderInhoud> histOuder2 = maakOuderCategorie(histInhoud2, histDoc2, histHistorie2, herkomsten[2]);

        // Cat52-3
        ouderBuilder.anummer(null);
        final Lo3OuderInhoud histInhoud3 = ouderBuilder.build();
        final Lo3Documentatie histDoc3 = maakAkte(Lo3String.wrap("4A"));
        final Lo3Historie histHistorie3 = maakHistorie(false, DATUM_FAMILIERECHTELIJKE_BETREKKING_2, DATUM_FAMILIERECHTELIJKE_BETREKKING_2);
        final Lo3Categorie<Lo3OuderInhoud> histOuder3 = maakOuderCategorie(histInhoud3, histDoc3, histHistorie3, herkomsten[3]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(histInhoud1, histDoc1, histHistorie1));

        final ExpectedBetrokkene expectedBetrokkene1 = new ExpectedBetrokkene();
        expectedBetrokkene1.gpGroepen.add(new ExpectedOuderGroep(histInhoud2, histDoc2, histHistorie2));
        // expectedBetrokkene1.gpGroepen.add(new ExpectedOuderGroep(maakLegeInhoud(), histDoc1, histHistorie1));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(histInhoud3, histDoc3, histHistorie3));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(null, histDoc1, histHistorie1));

        expectedBetrokkenen.add(expectedBetrokkene1);
        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 4;

        converteerEnCheck(new Lo3Stapel<>(Arrays.asList(histOuder3, histOuder2, histOuder1, actOuder)));
    }

    /**
     * Situatie 7: 3 voorkomens met dezelfde 62.10 waarvan 1 voorkomen onjuist is<br>
     * Test: Dit test het opschonen van onjuiste voorkomen en relatiegegevens, niet-leeg voorkomen stap 2<br>
     * Input:<br>
     * Cat02 gevuld, niet onjuist, 6210 A, 8510 C<br>
     * Cat52-1 gevuld, onjuist, 6210 A, 8510 B<br>
     * Cat52-2 gevuld, niet onjuist, 6210 A, 8510 A<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat52-2<br>
     */
    @Test
    public void testMeerdereVoorkomens1Onjuist() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(3);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat52-1
        ouderBuilder.anummer(Lo3Long.wrap(A_NUMMER_2));
        final Lo3OuderInhoud histInhoud1 = ouderBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(true, DATUM_GELDIGHEID_2, DATUM_OPNEMING_2);
        final Lo3Categorie<Lo3OuderInhoud> histOuder1 = maakOuderCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        // Cat52-2
        ouderBuilder.anummer(null);
        final Lo3OuderInhoud histInhoud2 = ouderBuilder.build();
        final Lo3Documentatie histDoc2 = maakAkte(Lo3String.wrap("3A"));
        final Lo3Historie histHistorie2 = maakHistorie(false, DATUM_FAMILIERECHTELIJKE_BETREKKING, DATUM_FAMILIERECHTELIJKE_BETREKKING);
        final Lo3Categorie<Lo3OuderInhoud> histOuder2 = maakOuderCategorie(histInhoud2, histDoc2, histHistorie2, herkomsten[2]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(histInhoud2, histDoc2, histHistorie2));
        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 3;

        converteerEnCheck(new Lo3Stapel<>(Arrays.asList(histOuder2, histOuder1, actOuder)));
    }

    /**
     * Situatie 8: 1 voorkomen die alleen de elementen 02.40 (waarde: .) en 62.10 gevuld heeft (punt-ouder)<br>
     * Test: Dit test de omgang met punt-ouders en relatiegegevens, niet-leeg voorkomen stap 1<br>
     * Input:<br>
     * Cat02 gevuld met puntouder, niet onjuist, 6210 A, 8510 B<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat02<br>
     */
    @Test
    public void testPuntOuder() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(1);

        // Cat02
        final Lo3OuderInhoud actInhoud = maakPuntOuderBuilder().build();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));

        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 1;

        converteerEnCheck(new Lo3Stapel<>(Collections.singletonList(actOuder)));
    }

    /**
     * Situatie 9: Leeg voorkomen (juridisch geen ouder)<br>
     * Test: Dit test de omgang met een leeg voorkomen<br>
     * Input:<br>
     * Cat02 leeg (juridisch geen ouder), niet onjuist, 6210 null, 8510 B<br>
     * <br>
     * Verwacht:<br>
     * - Alleen een relatie, geen betrokkenheden
     */
    @Test
    public void testJuridischGeenOuder() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(1);

        // Cat02
        final Lo3OuderInhoud actInhoud = maakLegeInhoud();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        expectedAantalIstOuderGroepen = 1;

        converteerEnCheck(new Lo3Stapel<>(Collections.singletonList(actOuder)));
    }

    /**
     * Situatie 10: 2 voorkomens, 1 gevuld en 1 leeg (actueel)<br>
     * Test: Dit test de omgang van een gevulde ouder naar een lege ouder<br>
     * Input:<br>
     * Cat02 leeg, niet onjuist, 6210 null, 8510 B<br>
     * Cat52-1 gevuld, niet onjuist, 6210 A, 8510 D<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat52-1<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat52-1<br>
     */
    @Test
    public void testOudernaarJuridischGeenOuder() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(2);

        // Cat02
        final Lo3OuderInhoud actInhoud = maakLegeInhoud();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat52-1
        final Lo3OuderInhoud histInhoud1 = ouderBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(false, DATUM_GELDIGHEID_2, DATUM_OPNEMING_2);
        final Lo3Categorie<Lo3OuderInhoud> histOuder1 = maakOuderCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        final ExpectedBetrokkene expectedBetrokkene1 = new ExpectedBetrokkene();
        expectedBetrokkene1.gpGroepen.add(new ExpectedOuderGroep(histInhoud1, histDoc1, histHistorie1));
        // expectedBetrokkene1.gpGroepen.add(new ExpectedOuderGroep(maakLegeInhoud(), actDoc, actHistorie));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(histInhoud1, histDoc1, histHistorie1));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(null, actDoc, actHistorie));

        expectedBetrokkenen.add(expectedBetrokkene1);
        expectedAantalIstOuderGroepen = 2;

        converteerEnCheck(new Lo3Stapel<>(Arrays.asList(histOuder1, actOuder)));
    }

    /**
     * Situatie 11: 2 voorkomens, 1 gevuld/onjuist en 1 leeg (actueel)<br>
     * Test: Dit test het opschonen van onjuiste voorkomen en de omgang met een leeg voorkomen<br>
     * Input:<br>
     * Cat02 leeg, niet onjuist, 6210 null, 8510 B<br>
     * Cat52-1 gevuld, onjuist, 6210 A, 8510 C<br>
     * <br>
     * Verwacht:<br>
     * - Alleen een relatie, geen betrokkenheden<br>
     */
    @Test
    public void testOnjuistVoorkomenLeegVoorkomen() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(2);

        // Cat02
        final Lo3OuderInhoud actInhoud = maakLegeInhoud();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat52-1
        final Lo3OuderInhoud histInhoud1 = ouderBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(true, DATUM_GELDIGHEID_2, DATUM_OPNEMING_2);
        final Lo3Categorie<Lo3OuderInhoud> histOuder1 = maakOuderCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        expectedAantalIstOuderGroepen = 2;
        converteerEnCheck(new Lo3Stapel<>(Arrays.asList(histOuder1, actOuder)));
    }

    /**
     * Situatie 12: 2 voorkomens, 1 gevuld(actueel) en 1 punt-ouder <br>
     * Test: Dit test de omgang van een punt-ouder naar een gevulde ouder<br>
     * Input:<br>
     * Cat02 gevuld, niet onjuist, 6210 A, 8510 B<br>
     * Cat52-1 gevuld, niet onjuist, 6210 C, 8510 D<br>
     * <br>
     * Verwacht:<br>
     * - 2 stapels voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02 en Cat52-1<br>
     * - 2 stapels voor ouderstapel gevuld vanuit Cat02 en Cat52-1<br>
     */
    @Test
    public void testPuntOuderNaarGevuldeOuder() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(2);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat52-1
        final Lo3OuderInhoud.Builder puntOuderBuilder = maakPuntOuderBuilder();
        puntOuderBuilder.familierechtelijkeBetrekking(new Lo3Datum(DATUM_FAMILIERECHTELIJKE_BETREKKING_2));
        final Lo3OuderInhoud histInhoud1 = puntOuderBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(false, DATUM_GELDIGHEID_2, DATUM_OPNEMING_2);
        final Lo3Categorie<Lo3OuderInhoud> histOuder1 = maakOuderCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));

        final ExpectedBetrokkene expectedBetrokkene1 = new ExpectedBetrokkene();
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(histInhoud1, histDoc1, histHistorie1));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(null, actDoc, maakAfsluitendeHistorie(actInhoud, actHistorie)));

        expectedBetrokkenen.add(expectedBetrokkene1);
        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 2;

        converteerEnCheck(new Lo3Stapel<>(Arrays.asList(histOuder1, actOuder)));
    }

    /**
     * Situatie 13: 2 voorkomens, 1 gevuld(actueel) en 1 leeg voorkomen <br>
     * Test: Dit test de omgang van een juridisch geen ouder naar een gevulde ouder<br>
     * Input:<br>
     * Cat02 gevuld, niet onjuist, 6210 A, 8510 B<br>
     * Cat52-1 leeg, niet onjuist, 6210 null, 8510 D<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat02<br>
     */
    @Test
    public void testJuridischGeenOuderNaarGevuldeOuder() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(2);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat52-1
        final Lo3OuderInhoud histInhoud1 = maakLegeInhoud();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(false, DATUM_GELDIGHEID_2, DATUM_OPNEMING_2);
        final Lo3Categorie<Lo3OuderInhoud> histOuder1 = maakOuderCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));

        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 2;

        converteerEnCheck(new Lo3Stapel<>(Arrays.asList(histOuder1, actOuder)));
    }

    /*
     * Gezagsverhouding tests
     */
    /**
     * Situatie 1: 2 verschillende ouders + 1 gezagsverhouding <br>
     * Test: Dit test of gezagsverhouding aan de actuele ouder wordt gekoppeld<br>
     * Input:<br>
     * Cat02 gevuld, niet onjuist, 6210 A, 8510 A<br>
     * Cat52-1 gevuld, niet onjuist, 6210 B, 8510 B<br>
     * Cat11 3210 1 8510 A<br>
     * Verwacht:<br>
     * - 2 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02 en Cat52-1<br>
     * - 2 stapels voor ouderstapel gevuld vanuit Cat02 en Cat52-1<br>
     * - 2 stapels voor oudergezag gevuld met true voor cat02 en false voor Cat52-1
     */
    @Test
    public void testGezagsverhoudingOuder1ActueelVoorkomen() {
        final Lo3Herkomst[] ouderHerkomsten = maakLo3Herkomsten(2);
        final Lo3Herkomst[] gezagHerkomsten = maakLo3Herkomsten(Lo3CategorieEnum.CATEGORIE_11, 1);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING);
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, ouderHerkomsten[0]);

        // Cat52-1
        ouderBuilder.anummer(Lo3Long.wrap(A_NUMMER_2));
        ouderBuilder.burgerservicenummer(Lo3Integer.wrap(BSN_2));
        ouderBuilder.geboortedatum(new Lo3Datum(DATUM_GEBOORTE_2));
        ouderBuilder.voornamen(Lo3String.wrap(VOORNAAM_2));
        ouderBuilder.geslachtsnaam(Lo3String.wrap(GESLACHTSNAAM_2));
        ouderBuilder.familierechtelijkeBetrekking(new Lo3Datum(DATUM_FAMILIERECHTELIJKE_BETREKKING_2));
        final Lo3OuderInhoud histInhoud1 = ouderBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING_2);
        final Lo3Categorie<Lo3OuderInhoud> histOuder1 = maakOuderCategorie(histInhoud1, histDoc1, histHistorie1, ouderHerkomsten[1]);

        // Cat11
        final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder();
        builder.indicatieGezagMinderjarige(new Lo3IndicatieGezagMinderjarige(Lo3IndicatieGezagMinderjarigeEnum.OUDER_1.getCode()));

        final Lo3GezagsverhoudingInhoud gezagActInhoud = builder.build();
        final Lo3Documentatie gezagActDoc = maakDocumentatie();
        final Lo3Historie gezagActHist = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING);

        final Lo3Categorie<Lo3GezagsverhoudingInhoud> actGezag =
                maakGezagsverhoudingCategorie(gezagActInhoud, gezagActDoc, gezagActHist, gezagHerkomsten[0]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.gezagsverhoudingGroepen.add(new ExpectedGezagsverhoudingGroep(new BrpBoolean(true, null), gezagActDoc, gezagActHist));

        final Lo3Historie afsluitendeHistorie = maakAfsluitendeHistorie(actInhoud, actHistorie);
        final ExpectedBetrokkene expectedBetrokkene1 = new ExpectedBetrokkene();
        expectedBetrokkene1.gpGroepen.add(new ExpectedOuderGroep(histInhoud1, histDoc1, histHistorie1));
        // expectedBetrokkene1.gpGroepen.add(new ExpectedOuderGroep(maakLegeInhoud(), actDoc, afsluitendeHistorie));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(histInhoud1, histDoc1, histHistorie1));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(null, actDoc, afsluitendeHistorie));
        expectedBetrokkene1.gezagsverhoudingGroepen.add(new ExpectedGezagsverhoudingGroep(null, gezagActDoc, gezagActHist));

        expectedBetrokkenen.add(expectedBetrokkene1);
        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 2;
        expectedAantalIstGezagsverhoudingGroepen = 1;

        converteerEnCheck(new Lo3Stapel<>(Arrays.asList(histOuder1, actOuder)), new Lo3Stapel<>(Collections.singletonList(actGezag)));
    }

    /**
     * Situatie 2: 2 verschillende ouders + 2 gezagsverhoudingen <br>
     * Test: Dit test of gezagsverhouding aan de historische ouder wordt gekoppeld<br>
     * Input:<br>
     * Cat02 gevuld, niet onjuist, 6210 A, 8510 A<br>
     * Cat52-1 gevuld, niet onjuist, 6210 B, 8510 B<br>
     * Cat11 3210 leeg 8510 A<br>
     * Cat61-1 3210 1 8510 B<br>
     * Verwacht:<br>
     * - 2 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02 en Cat52-1<br>
     * - 2 stapels voor ouderstapel gevuld vanuit Cat02 en Cat52-1<br>
     * - 2 stapels voor oudergezag gevuld met false voor cat02 en true voor Cat52-1
     */
    @Test
    public void testGezagsverhoudingOuder1HistorischVoorkomen() {
        final Lo3Herkomst[] ouderHerkomsten = maakLo3Herkomsten(2);
        final Lo3Herkomst[] gezagHerkomsten = maakLo3Herkomsten(Lo3CategorieEnum.CATEGORIE_11, 2);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING);
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, ouderHerkomsten[0]);

        // Cat52-1
        ouderBuilder.anummer(Lo3Long.wrap(A_NUMMER_2));
        ouderBuilder.burgerservicenummer(Lo3Integer.wrap(BSN_2));
        ouderBuilder.geboortedatum(new Lo3Datum(DATUM_GEBOORTE_2));
        ouderBuilder.voornamen(Lo3String.wrap(VOORNAAM_2));
        ouderBuilder.geslachtsnaam(Lo3String.wrap(GESLACHTSNAAM_2));
        ouderBuilder.familierechtelijkeBetrekking(new Lo3Datum(DATUM_FAMILIERECHTELIJKE_BETREKKING_2));
        final Lo3OuderInhoud histInhoud1 = ouderBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING_2);
        final Lo3Categorie<Lo3OuderInhoud> histOuder1 = maakOuderCategorie(histInhoud1, histDoc1, histHistorie1, ouderHerkomsten[1]);

        // Cat11
        final Lo3GezagsverhoudingInhoud gezagActInhoud = new Lo3GezagsverhoudingInhoud();
        final Lo3Documentatie gezagActDoc = maakDocumentatie();
        final Lo3Historie gezagActHist = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> actGezag =
                maakGezagsverhoudingCategorie(gezagActInhoud, gezagActDoc, gezagActHist, gezagHerkomsten[0]);

        // Cat61-1
        final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder();
        builder.indicatieGezagMinderjarige(new Lo3IndicatieGezagMinderjarige(Lo3IndicatieGezagMinderjarigeEnum.OUDER_1.getCode()));
        final Lo3GezagsverhoudingInhoud gezagHist1Inhoud = builder.build();
        final Lo3Documentatie gezagHist1Doc = maakDocumentatie();
        final Lo3Historie gezagHist1Hist = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING_2);

        final Lo3Categorie<Lo3GezagsverhoudingInhoud> hist1Gezag =
                maakGezagsverhoudingCategorie(gezagHist1Inhoud, gezagHist1Doc, gezagHist1Hist, gezagHerkomsten[1]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.gezagsverhoudingGroepen.add(new ExpectedGezagsverhoudingGroep(null, gezagActDoc, gezagActHist));

        final Lo3Historie afsluitendeHistorie = maakAfsluitendeHistorie(actInhoud, actHistorie);
        final ExpectedBetrokkene expectedBetrokkene1 = new ExpectedBetrokkene();
        expectedBetrokkene1.gpGroepen.add(new ExpectedOuderGroep(histInhoud1, histDoc1, histHistorie1));
        // expectedBetrokkene1.gpGroepen.add(new ExpectedOuderGroep(maakLegeInhoud(), actDoc, afsluitendeHistorie));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(histInhoud1, histDoc1, histHistorie1));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(null, actDoc, afsluitendeHistorie));
        expectedBetrokkene1.gezagsverhoudingGroepen.add(new ExpectedGezagsverhoudingGroep(new BrpBoolean(true, null), gezagHist1Doc, gezagHist1Hist));
        expectedBetrokkene1.gezagsverhoudingGroepen.add(new ExpectedGezagsverhoudingGroep(null, gezagHist1Doc, afsluitendeHistorie));

        expectedBetrokkenen.add(expectedBetrokkene1);
        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 2;
        expectedAantalIstGezagsverhoudingGroepen = 2;

        converteerEnCheck(new Lo3Stapel<>(Arrays.asList(histOuder1, actOuder)), new Lo3Stapel<>(Arrays.asList(hist1Gezag, actGezag)));
    }

    /**
     * Situatie 3: 2 verschillende ouders + 2 gezagsverhoudingen <br>
     * Test: Dit test of gezagsverhouding aan de historische ouder wordt gekoppeld als de 85.10 van de gezagsverhouding
     * tussen de 85.10 van de historische en actuele ouder ligt<br>
     * Input:<br>
     * Cat02 gevuld, niet onjuist, 6210 A, 8510 A<br>
     * Cat52-1 gevuld, niet onjuist, 6210 B, 8510 B<br>
     * Cat11 3210 leeg 8510 A<br>
     * Cat61 3210 1 A > 8510 > B<br>
     * Verwacht:<br>
     * - 2 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02 en Cat52-1<br>
     * - 2 stapels voor ouderstapel gevuld vanuit Cat02 en Cat52-1<br>
     * - 2 stapels voor oudergezag gevuld met false voor cat02 en true voor Cat52-1
     */
    @Test
    public void testGezagsverhoudingOuder1HistorischVoorkomenJongerDanHistorischVoorkomenOuder1() {
        final Lo3Herkomst[] ouderHerkomsten = maakLo3Herkomsten(2);
        final Lo3Herkomst[] gezagHerkomsten = maakLo3Herkomsten(Lo3CategorieEnum.CATEGORIE_11, 2);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING);
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, ouderHerkomsten[0]);

        // Cat52-1
        ouderBuilder.anummer(Lo3Long.wrap(A_NUMMER_2));
        ouderBuilder.burgerservicenummer(Lo3Integer.wrap(BSN_2));
        ouderBuilder.geboortedatum(new Lo3Datum(DATUM_GEBOORTE_2));
        ouderBuilder.voornamen(Lo3String.wrap(VOORNAAM_2));
        ouderBuilder.geslachtsnaam(Lo3String.wrap(GESLACHTSNAAM_2));
        ouderBuilder.familierechtelijkeBetrekking(new Lo3Datum(DATUM_FAMILIERECHTELIJKE_BETREKKING_2));
        final Lo3OuderInhoud histInhoud1 = ouderBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING_2);
        final Lo3Categorie<Lo3OuderInhoud> histOuder1 = maakOuderCategorie(histInhoud1, histDoc1, histHistorie1, ouderHerkomsten[1]);

        // Cat11
        final Lo3GezagsverhoudingInhoud gezagActInhoud = new Lo3GezagsverhoudingInhoud();
        final Lo3Documentatie gezagActDoc = maakDocumentatie();
        final Lo3Historie gezagActHist = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> actGezag =
                maakGezagsverhoudingCategorie(gezagActInhoud, gezagActDoc, gezagActHist, gezagHerkomsten[0]);

        // Cat61-1
        final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder();
        builder.indicatieGezagMinderjarige(new Lo3IndicatieGezagMinderjarige(Lo3IndicatieGezagMinderjarigeEnum.OUDER_1.getCode()));
        final Lo3GezagsverhoudingInhoud gezagHist1Inhoud = builder.build();
        final Lo3Documentatie gezagHist1Doc = maakDocumentatie();
        final Lo3Historie gezagHist1Hist = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING_2);

        final Lo3Categorie<Lo3GezagsverhoudingInhoud> hist1Gezag =
                maakGezagsverhoudingCategorie(gezagHist1Inhoud, gezagHist1Doc, gezagHist1Hist, gezagHerkomsten[1]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.gezagsverhoudingGroepen.add(new ExpectedGezagsverhoudingGroep(null, gezagActDoc, gezagActHist));

        final Lo3Historie afsluitendeHistorie = maakAfsluitendeHistorie(actInhoud, actHistorie);
        final ExpectedBetrokkene expectedBetrokkene1 = new ExpectedBetrokkene();
        expectedBetrokkene1.gpGroepen.add(new ExpectedOuderGroep(histInhoud1, histDoc1, histHistorie1));
        // expectedBetrokkene1.gpGroepen.add(new ExpectedOuderGroep(maakLegeInhoud(), actDoc, afsluitendeHistorie));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(histInhoud1, histDoc1, histHistorie1));
        expectedBetrokkene1.relatieGroepen.add(new ExpectedOuderGroep(null, actDoc, afsluitendeHistorie));
        expectedBetrokkene1.gezagsverhoudingGroepen.add(new ExpectedGezagsverhoudingGroep(new BrpBoolean(true, null), gezagHist1Doc, gezagHist1Hist));
        expectedBetrokkene1.gezagsverhoudingGroepen.add(new ExpectedGezagsverhoudingGroep(null, gezagHist1Doc, afsluitendeHistorie));

        expectedBetrokkenen.add(expectedBetrokkene1);
        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 2;
        expectedAantalIstGezagsverhoudingGroepen = 2;

        converteerEnCheck(new Lo3Stapel<>(Arrays.asList(histOuder1, actOuder)), new Lo3Stapel<>(Arrays.asList(hist1Gezag, actGezag)));
    }

    /**
     * Situatie 4: 1 ouder + 1 gezagsverhouding<br>
     * Test: Dit test of de gezagsverhouding voor ouder 2 niet aan ouder 1 wordt gekoppeld<br>
     * Input:<br>
     * Cat02 gevuld, niet onjuist, 6210 A, 8510 A<br>
     * Cat11 3210 2 8510 A<br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat02<br>
     * - 1 stapel voor oudergezag gevuld met false voor Cat02
     */
    @Test
    public void testGezagsverhoudingOuder2() {
        final Lo3Herkomst[] ouderHerkomsten = maakLo3Herkomsten(1);
        final Lo3Herkomst[] gezagHerkomsten = maakLo3Herkomsten(Lo3CategorieEnum.CATEGORIE_11, 1);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING);
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, ouderHerkomsten[0]);

        // Cat11
        final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder();
        builder.indicatieGezagMinderjarige(new Lo3IndicatieGezagMinderjarige(Lo3IndicatieGezagMinderjarigeEnum.OUDER_2.getCode()));
        final Lo3GezagsverhoudingInhoud gezagActInhoud = builder.build();
        final Lo3Documentatie gezagActDoc = maakDocumentatie();
        final Lo3Historie gezagActHist = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING);

        final Lo3Categorie<Lo3GezagsverhoudingInhoud> actGezag =
                maakGezagsverhoudingCategorie(gezagActInhoud, gezagActDoc, gezagActHist, gezagHerkomsten[0]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.gezagsverhoudingGroepen.add(new ExpectedGezagsverhoudingGroep(new BrpBoolean(false, null), gezagActDoc, gezagActHist));

        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 1;
        expectedAantalIstGezagsverhoudingGroepen = 1;

        converteerEnCheck(new Lo3Stapel<>(Collections.singletonList(actOuder)), new Lo3Stapel<>(Collections.singletonList(actGezag)));
    }

    /**
     * 1 ouder + 1 gezagsverhouding<br>
     * Test: Dit test of de gezagsverhouding afgesloten wordt als er een actueel voorkomen is met 85.10 standaarwaarde.
     * Input:<br>
     * Cat02 gevuld, niet onjuist, 6210 A, 8510 A<br>
     * Cat11 3210 2 8510 00000000<br>
     * Cat61 3210 2 8510 A<br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam-, geslacht- en geboortestapel gevuld vanuit Cat02<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat02<br>
     * - 1 stapel voor oudergezag gevuld met false voor Cat02
     */
    @Test
    public void testGezagsverhoudingOuderEinddatumStandaardwaarde() {
        final Lo3Herkomst[] ouderHerkomsten = maakLo3Herkomsten(1);
        final Lo3Herkomst[] gezagHerkomsten = maakLo3Herkomsten(Lo3CategorieEnum.CATEGORIE_11, 2);

        // Cat02
        final Lo3OuderInhoud actInhoud = ouderBuilder.build();
        final Lo3Historie actHistorie = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING);
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3OuderInhoud> actOuder = maakOuderCategorie(actInhoud, actDoc, actHistorie, ouderHerkomsten[0]);

        // Cat11
        final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder();
        builder.indicatieGezagMinderjarige(new Lo3IndicatieGezagMinderjarige(Lo3IndicatieGezagMinderjarigeEnum.OUDER_2.getCode()));
        final Lo3GezagsverhoudingInhoud gezagActInhoud = builder.build();
        final Lo3Documentatie gezagActDoc = maakDocumentatie();
        final Lo3Historie gezagActHist = maakHistorie(false, DATUM_STANDAARD, DATUM_FAMILIERECHTELIJKE_BETREKKING);

        // Cat61
        final Lo3GezagsverhoudingInhoud.Builder builder2 = new Lo3GezagsverhoudingInhoud.Builder();
        builder2.indicatieGezagMinderjarige(new Lo3IndicatieGezagMinderjarige(Lo3IndicatieGezagMinderjarigeEnum.OUDER_2.getCode()));
        final Lo3GezagsverhoudingInhoud gezagActInhoud2 = builder2.build();
        final Lo3Documentatie gezagActDoc2 = maakDocumentatie();
        final Lo3Historie gezagActHist2 = maakJuisteHistorie(DATUM_FAMILIERECHTELIJKE_BETREKKING);

        final Lo3Categorie<Lo3GezagsverhoudingInhoud> actGezag =
                maakGezagsverhoudingCategorie(gezagActInhoud, gezagActDoc, gezagActHist, gezagHerkomsten[0]);

        final Lo3Categorie<Lo3GezagsverhoudingInhoud> actGezag2 =
                maakGezagsverhoudingCategorie(gezagActInhoud2, gezagActDoc2, gezagActHist2, gezagHerkomsten[1]);

        final ExpectedBetrokkene expectedBetrokkene = new ExpectedBetrokkene();
        expectedBetrokkene.gpGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.relatieGroepen.add(new ExpectedOuderGroep(actInhoud, actDoc, actHistorie));
        expectedBetrokkene.gezagsverhoudingGroepen.add(new ExpectedGezagsverhoudingGroep(new BrpBoolean(false, null), gezagActDoc2, gezagActHist2));
        expectedBetrokkene.gezagsverhoudingGroepen.add(new ExpectedGezagsverhoudingGroep(null, gezagActDoc, gezagActHist));

        expectedBetrokkenen.add(expectedBetrokkene);
        expectedAantalIstOuderGroepen = 1;
        expectedAantalIstGezagsverhoudingGroepen = 2;

        converteerEnCheck(new Lo3Stapel<>(Collections.singletonList(actOuder)), new Lo3Stapel<>(Arrays.asList(actGezag2, actGezag)));
    }

    @Test
    public void testDummyPL() {
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        ouderConverteerder.converteer(null, null, null, true, builder);
        final TussenPersoonslijst migrPersLijst = builder.build();
        Assert.assertEquals(0, migrPersLijst.getRelaties().size());

    }

    private void converteerEnCheck(final Lo3Stapel<Lo3OuderInhoud> ouderStapel) {
        converteerEnCheck(ouderStapel, null);
    }

    private void converteerEnCheck(final Lo3Stapel<Lo3OuderInhoud> ouderStapel, final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel) {
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        if (isOuder2) {
            ouderConverteerder.converteer(null, ouderStapel, gezagsverhoudingStapel, false, builder);
        } else {
            ouderConverteerder.converteer(ouderStapel, null, gezagsverhoudingStapel, false, builder);
        }

        final TussenPersoonslijst migrPerslijst = builder.build();
        assertPersoonIstStapels(migrPerslijst);
        checkRelaties(migrPerslijst.getRelaties());
    }

    private void checkRelaties(final List<TussenRelatie> relaties) {
        assertEquals("Maar 1 relatie verwacht", 1, relaties.size());
        final TussenRelatie relatie = relaties.get(0);

        assertNotNull(relatie);
        assertEquals(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, relatie.getSoortRelatieCode());
        assertEquals(BrpSoortBetrokkenheidCode.KIND, relatie.getRolCode());
        assertNull(relatie.getRelatieStapel());

        assertRelatieIstStapels(relatie);

        final List<TussenBetrokkenheid> betrokkenheden = relatie.getBetrokkenheden();
        assertNotNull(betrokkenheden);
        assertEquals("Aantal betrokkene klopt niet", expectedBetrokkenen.size(), betrokkenheden.size());

        for (int index = 0; index < betrokkenheden.size(); index++) {
            final TussenBetrokkenheid betrokkene = betrokkenheden.get(index);
            final ExpectedBetrokkene expectedBetrokkene = expectedBetrokkenen.get(index);
            assertNotNull(betrokkene);
            assertEquals(BrpSoortBetrokkenheidCode.OUDER, betrokkene.getRol());

            assertGeboorteStapel(expectedBetrokkene, betrokkene);
            assertGeslachtStapel(expectedBetrokkene, betrokkene);
            assertIdentificatieStapel(expectedBetrokkene, betrokkene);
            assertOuderlijkGezagStapel(expectedBetrokkene, betrokkene);
            assertSamengesteldeNaamStapel(expectedBetrokkene, betrokkene);
            assertOuderStapel(expectedBetrokkene, betrokkene);
        }
    }

    private void assertPersoonIstStapels(final TussenPersoonslijst persoonslijst) {
        TussenStapel<BrpIstRelatieGroepInhoud> istOuderGegevens = persoonslijst.getIstOuder1Stapel();
        if (isOuder2) {
            istOuderGegevens = persoonslijst.getIstOuder2Stapel();
        }
        assertNotNull(istOuderGegevens);
        assertEquals("Aantal IST ouder groepen klopt niet", expectedAantalIstOuderGroepen, istOuderGegevens.size());

        final TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingGegevens = persoonslijst.getIstGezagsverhoudingStapel();
        if (expectedAantalIstGezagsverhoudingGroepen > 0) {
            assertEquals("Aantal IST gezagsverhouding groepen klopt niet", expectedAantalIstGezagsverhoudingGroepen, istGezagsverhoudingGegevens.size());
        } else {
            assertNull(istGezagsverhoudingGegevens);
        }
    }

    private void assertRelatieIstStapels(final TussenRelatie relatie) {
        TussenStapel<BrpIstRelatieGroepInhoud> istOuderGegevens = relatie.getIstOuder1();
        if (isOuder2) {
            istOuderGegevens = relatie.getIstOuder2();
        }
        assertNotNull(istOuderGegevens);
        assertEquals("Aantal IST ouder groepen klopt niet", expectedAantalIstOuderGroepen, istOuderGegevens.size());

        final TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingGegevens = relatie.getIstGezagsverhouding();
        if (expectedAantalIstGezagsverhoudingGroepen > 0) {
            assertEquals("Aantal IST gezagsverhouding groepen klopt niet", expectedAantalIstGezagsverhoudingGroepen, istGezagsverhoudingGegevens.size());
        } else {
            assertNull(istGezagsverhoudingGegevens);
        }
    }

    private void assertOuderStapel(final ExpectedBetrokkene expectedBetrokkene, final TussenBetrokkenheid betrokkenheid) {
        final TussenStapel<BrpOuderInhoud> stapel = betrokkenheid.getOuderStapel();

        if (expectedBetrokkene.relatieGroepen.isEmpty()) {
            assertNull(stapel);
            return;
        }

        assertNotNull(stapel);
        final List<ExpectedOuderGroep> expectedGroepen = expectedBetrokkene.relatieGroepen;
        assertEquals("Aantal ouder groepen klopt niet", expectedGroepen.size(), stapel.getGroepen().size());
        for (int index = 0; index < expectedGroepen.size(); index++) {
            final TussenGroep<BrpOuderInhoud> groep = stapel.get(index);
            final ExpectedOuderGroep expectedGroep = expectedGroepen.get(index);
            assertNotNull(groep);
            if (expectedGroep.inhoud != null) {
                if (expectedGroep.inhoud.isLeeg()) {
                    Assert.assertFalse(BrpBoolean.unwrap(groep.getInhoud().getIndicatieOuder()));
                } else {
                    assertTrue(BrpBoolean.unwrap(groep.getInhoud().getIndicatieOuder()));
                }
            } else {
                assertNull(groep.getInhoud().getIndicatieOuder());
            }
            final Lo3Historie expectedHistorie;
            if (expectedGroep.inhoud != null) {
                expectedHistorie =
                        new Lo3Historie(
                            expectedGroep.historie.getIndicatieOnjuist(),
                            expectedGroep.inhoud.getFamilierechtelijkeBetrekking(),
                            expectedGroep.historie.getDatumVanOpneming());
            } else {
                expectedHistorie = expectedGroep.historie;
            }
            assertEquals("Ouder stapel historie klopt niet", expectedHistorie, groep.getHistorie());
            assertEquals("Ouder stapel documentatie klopt niet", expectedGroep.documentatie, groep.getDocumentatie());
        }
    }

    private void assertSamengesteldeNaamStapel(final ExpectedBetrokkene expectedBetrokkene, final TussenBetrokkenheid betrokkenheid) {
        final TussenStapel<BrpSamengesteldeNaamInhoud> stapel = betrokkenheid.getSamengesteldeNaamStapel();

        if (expectedBetrokkene.gpGroepen.isEmpty()) {
            assertNull(stapel);
            return;
        }

        assertNotNull(stapel);
        final List<ExpectedOuderGroep> expectedGroepen = expectedBetrokkene.gpGroepen;
        assertEquals("Aantal samengestelde naam groepen klopt niet", expectedGroepen.size(), stapel.getGroepen().size());
        for (int index = 0; index < expectedGroepen.size(); index++) {
            final TussenGroep<BrpSamengesteldeNaamInhoud> groep = stapel.get(index);
            final ExpectedOuderGroep expectedGroep = expectedGroepen.get(index);
            assertNotNull(groep);
            assertEquals(Lo3String.unwrap(expectedGroep.inhoud.getVoornamen()), BrpString.unwrap(groep.getInhoud().getVoornamen()));
            assertEquals("Samengestelde naam stapel historie klopt niet", expectedGroep.historie, groep.getHistorie());
            assertEquals("Samengestelde naam stapel documentatie klopt niet", expectedGroep.documentatie, groep.getDocumentatie());
        }
    }

    private void assertOuderlijkGezagStapel(final ExpectedBetrokkene expectedBetrokkene, final TussenBetrokkenheid betrokkenheid) {
        final TussenStapel<BrpOuderlijkGezagInhoud> stapel = betrokkenheid.getOuderlijkGezagStapel();
        if (expectedBetrokkene.gezagsverhoudingGroepen.isEmpty()) {
            assertNull("Geen ouderlijk gezag stapel verwacht", stapel);
            return;
        }
        assertNotNull(stapel);

        final List<ExpectedGezagsverhoudingGroep> expectedGroepen = expectedBetrokkene.gezagsverhoudingGroepen;
        assertEquals("Aantal gezagsverhouding groepen klopt niet", expectedGroepen.size(), stapel.getGroepen().size());
        for (int index = 0; index < expectedGroepen.size(); index++) {
            final TussenGroep<BrpOuderlijkGezagInhoud> groep = stapel.get(index);
            final ExpectedGezagsverhoudingGroep expectedGroep = expectedGroepen.get(index);

            assertNotNull(groep);
            assertEquals(expectedGroep.ouderHeeftGezag, groep.getInhoud().getOuderHeeftGezag());
            assertEquals("Ouderlijk gezag stapel historie klopt niet", expectedGroep.historie, groep.getHistorie());
            assertEquals("Ouderlijk gezag stapel documentatie klopt niet", expectedGroep.documentatie, groep.getDocumentatie());
        }

    }

    private void assertGeboorteStapel(final ExpectedBetrokkene expectedBetrokkene, final TussenBetrokkenheid betrokkenheid) {
        final TussenStapel<BrpGeboorteInhoud> stapel = betrokkenheid.getGeboorteStapel();
        if (expectedBetrokkene.gpGroepen.isEmpty()) {
            assertNull(stapel);
            return;
        }

        assertNotNull(stapel);

        final List<ExpectedOuderGroep> expectedGroepen = expectedBetrokkene.gpGroepen;
        assertEquals("Aantal geboorte groep klopt niet", expectedGroepen.size(), stapel.getGroepen().size());
        for (int index = 0; index < expectedGroepen.size(); index++) {
            final TussenGroep<BrpGeboorteInhoud> groep = stapel.get(index);
            final ExpectedOuderGroep expectedGroep = expectedGroepen.get(index);

            assertNotNull(groep);
            if (expectedGroep.inhoud.getGeboortedatum() == null) {
                assertNull(groep.getInhoud().getGeboortedatum());
            } else {
                assertEquals("Geboortedatum komt niet overeen", BrpDatum.fromLo3Datum(expectedGroep.inhoud.getGeboortedatum()), groep.getInhoud()
                                                                                                                                     .getGeboortedatum());
            }
            assertEquals("Geboorte stapel historie klopt niet", expectedGroep.historie, groep.getHistorie());
            assertEquals("Geboorte stapel documentatie klopt niet", expectedGroep.documentatie, groep.getDocumentatie());
        }
    }

    private void assertGeslachtStapel(final ExpectedBetrokkene expectedBetrokkene, final TussenBetrokkenheid betrokkenheid) {
        final TussenStapel<BrpGeslachtsaanduidingInhoud> stapel = betrokkenheid.getGeslachtsaanduidingStapel();

        if (expectedBetrokkene.gpGroepen.isEmpty()) {
            assertNull(stapel);
            return;
        }

        assertNotNull("Een geslacht stapel verwacht", stapel);

        final List<ExpectedOuderGroep> expectedGroepen = expectedBetrokkene.gpGroepen;
        assertEquals("aantal geslacht groepen klopt niet", expectedGroepen.size(), stapel.getGroepen().size());
        for (int index = 0; index < expectedGroepen.size(); index++) {
            final TussenGroep<BrpGeslachtsaanduidingInhoud> groep = stapel.get(index);
            final ExpectedOuderGroep expectedGroep = expectedGroepen.get(index);

            assertNotNull(groep);
            if (expectedGroep.inhoud.getGeslachtsaanduiding() == null) {
                assertNull(groep.getInhoud().getGeslachtsaanduidingCode());
            } else {
                assertEquals(expectedGroep.inhoud.getGeslachtsaanduiding().getWaarde(), groep.getInhoud().getGeslachtsaanduidingCode().getWaarde());
            }
            assertEquals("Geslacht stapel historie klopt niet", expectedGroep.historie, groep.getHistorie());
            assertEquals("Geslacht stapel documentatie klopt niet", expectedGroep.documentatie, groep.getDocumentatie());
        }
    }

    private void assertIdentificatieStapel(final ExpectedBetrokkene expectedBetrokkene, final TussenBetrokkenheid betrokkenheid) {
        final TussenStapel<BrpIdentificatienummersInhoud> stapel = betrokkenheid.getIdentificatienummersStapel();

        if (expectedBetrokkene.gpGroepen.isEmpty()) {
            assertNull(stapel);
            return;
        }

        assertNotNull(stapel);
        final List<ExpectedOuderGroep> expectedGroepen = expectedBetrokkene.gpGroepen;
        assertEquals("Aantal identificatienummers groep klopt niet", expectedGroepen.size(), stapel.getGroepen().size());

        for (int index = 0; index < expectedGroepen.size(); index++) {
            final TussenGroep<BrpIdentificatienummersInhoud> groep = stapel.get(index);
            final ExpectedOuderGroep expectedGroep = expectedGroepen.get(index);

            assertNotNull(groep);
            assertEquals(Lo3Long.unwrap(expectedGroep.inhoud.getaNummer()), BrpLong.unwrap(groep.getInhoud().getAdministratienummer()));
            assertEquals(Lo3Integer.unwrap(expectedGroep.inhoud.getBurgerservicenummer()), BrpInteger.unwrap(groep.getInhoud().getBurgerservicenummer()));
            assertEquals("Identificatienummers stapel historie klopt niet", expectedGroep.historie, groep.getHistorie());
            assertEquals("Identificatienummers stapel documentatie klopt niet", expectedGroep.documentatie, groep.getDocumentatie());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.AbstractRelatieConverteerderTest#getCategorie()
     */
    @Override
    protected Lo3CategorieEnum getCategorie() {
        return Lo3CategorieEnum.CATEGORIE_02;
    }

    private Lo3Historie maakAfsluitendeHistorie(final Lo3OuderInhoud inhoud, final Lo3Historie historie) {
        return new Lo3Historie(historie.getIndicatieOnjuist(), inhoud.getFamilierechtelijkeBetrekking(), historie.getDatumVanOpneming());
    }

    private static final class ExpectedOuderGroep {
        private final Lo3OuderInhoud inhoud;
        private final Lo3Historie historie;
        private final Lo3Documentatie documentatie;

        public ExpectedOuderGroep(final Lo3OuderInhoud inhoud, final Lo3Documentatie documentatie, final Lo3Historie historie) {
            this.inhoud = inhoud;
            this.historie = historie;
            this.documentatie = documentatie;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("inhoud", inhoud).append("historie", historie).append("documentatie", documentatie).toString();
        }
    }

    private static final class ExpectedGezagsverhoudingGroep {
        private final BrpBoolean ouderHeeftGezag;
        private final Lo3Historie historie;
        private final Lo3Documentatie documentatie;

        public ExpectedGezagsverhoudingGroep(final BrpBoolean ouderHeeftGezag, final Lo3Documentatie documentatie, final Lo3Historie historie) {
            this.ouderHeeftGezag = ouderHeeftGezag;
            this.historie = historie;
            this.documentatie = documentatie;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("ouderHeeftGezag", ouderHeeftGezag)
                                            .append("historie", historie)
                                            .append("documentatie", documentatie)
                                            .toString();
        }
    }

    private static final class ExpectedBetrokkene {
        private final List<ExpectedOuderGroep> gpGroepen = new ArrayList<>();
        private final List<ExpectedOuderGroep> relatieGroepen = new ArrayList<>();
        private final List<ExpectedGezagsverhoudingGroep> gezagsverhoudingGroepen = new ArrayList<>();
    }
}
