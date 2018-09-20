/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
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
import org.junit.Before;
import org.junit.Test;

public class KindConverteerderTest extends AbstractRelatieConverteerderTest {

    @Inject
    private KindConverteerder kindConverteerder;

    private Lo3KindInhoud.Builder kindBuilder;
    private final List<ExpectedResult> expectedGerelateerde = new ArrayList<>();
    private final List<ExpectedResult> expectedRelatie = new ArrayList<>();
    private int expectedAantalIstStapels;

    @Before
    public void setUp() {
        kindBuilder = new Lo3KindInhoud.Builder();
        kindBuilder.aNummer(Lo3Long.wrap(A_NUMMER));
        kindBuilder.burgerservicenummer(Lo3Integer.wrap(BSN));
        kindBuilder.voornamen(Lo3String.wrap(VOORNAAM));
        kindBuilder.geslachtsnaam(Lo3String.wrap(GESLACHTSNAAM));
        kindBuilder.geboortedatum(new Lo3Datum(DATUM_GEBOORTE));
        kindBuilder.geboorteGemeenteCode(new Lo3GemeenteCode(GEMEENTE_CODE));
        kindBuilder.geboorteLandCode(new Lo3LandCode(LAND_CODE));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.AbstractRelatieConverteerderTest#getCategorie()
     */
    @Override
    protected Lo3CategorieEnum getCategorie() {
        return Lo3CategorieEnum.CATEGORIE_09;
    }

    private Lo3Categorie<Lo3KindInhoud> maakCategorie(
        final Lo3KindInhoud inhoud,
        final Lo3Documentatie documentatie,
        final Lo3Historie historie,
        final Lo3Herkomst herkomst)
    {
        return new Lo3Categorie<>(inhoud, documentatie, historie, herkomst);
    }

    private List<Lo3Stapel<Lo3KindInhoud>> maakKindStapel(final List<Lo3Categorie<Lo3KindInhoud>> categorieen) {
        final List<Lo3Stapel<Lo3KindInhoud>> stapels = new ArrayList<>();
        stapels.add(new Lo3Stapel<>(categorieen));
        return stapels;
    }

    private Lo3KindInhoud maakLegeInhoud() {
        final Lo3KindInhoud.Builder actInhoudBuilder = new Lo3KindInhoud.Builder(kindBuilder.build());
        actInhoudBuilder.aNummer(null);
        actInhoudBuilder.burgerservicenummer(null);
        actInhoudBuilder.geboortedatum(null);
        actInhoudBuilder.geboorteGemeenteCode(null);
        actInhoudBuilder.geboorteLandCode(null);
        actInhoudBuilder.geslachtsnaam(null);
        actInhoudBuilder.voornamen(null);
        actInhoudBuilder.voorvoegselGeslachtsnaam(null);
        return actInhoudBuilder.build();
    }

    /**
     * Input:<br>
     * Cat09 gevuld, niet onjuist<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam- en geboortestapel gevuld vanuit Cat09<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat09<br>
     */
    @Test
    public void test1RijGevuld() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(1);

        // Cat09
        final Lo3KindInhoud actInhoud = kindBuilder.build();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();

        final Lo3Categorie<Lo3KindInhoud> categorie = maakCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);
        final List<Lo3Stapel<Lo3KindInhoud>> kindStapels = maakKindStapel(Collections.singletonList(categorie));

        final List<TussenRelatie> relaties = converteerKind(kindStapels);

        final ExpectedResult gerelateerdeResult = new ExpectedResult();
        gerelateerdeResult.inhoud = actInhoud;
        gerelateerdeResult.documentatie = actDoc;
        gerelateerdeResult.historie = actHistorie;

        final ExpectedResult relatieResult = new ExpectedResult();
        relatieResult.inhoud = actInhoud;
        relatieResult.documentatie = actDoc;
        relatieResult.historie = actHistorie;

        expectedGerelateerde.add(gerelateerdeResult);
        expectedRelatie.add(relatieResult);
        expectedAantalIstStapels = 1;

        checkRelaties(relaties);
    }

    /**
     * Input:<br>
     * Cat09 gevuld, niet onjuist<br>
     * Cat59 gevuld, onjuist<br>
     * <br>
     * Verwacht: Geen relatie<br>
     *
     * Overigens worden er voor het Kind wel IST gegevens aangemaakt op Persoonslijst niveau tbv de terugconversie. Dit
     * wordt hier niet getest.
     */
    @Test
    public void test2RijenGevuld1Onjuist() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(2);

        // Cat09
        final Lo3KindInhoud actInhoud = kindBuilder.build();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3KindInhoud> actKind = maakCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat59
        kindBuilder.aNummer(null);
        kindBuilder.burgerservicenummer(null);
        final Lo3Historie histHistorie = maakHistorie(true, 20080101, 20080102);
        final Lo3Categorie<Lo3KindInhoud> histKind = maakCategorie(kindBuilder.build(), actDoc, histHistorie, herkomsten[1]);

        final List<Lo3Stapel<Lo3KindInhoud>> kindStapels = maakKindStapel(Arrays.asList(actKind, histKind));

        final List<TussenRelatie> relaties = converteerKind(kindStapels);

        final ExpectedResult gerelateerdeResult = new ExpectedResult();
        gerelateerdeResult.inhoud = actInhoud;
        gerelateerdeResult.documentatie = actDoc;
        gerelateerdeResult.historie = actHistorie;

        final ExpectedResult relatieResult = new ExpectedResult();
        relatieResult.inhoud = actInhoud;
        relatieResult.documentatie = actDoc;
        relatieResult.historie = actHistorie;

        expectedGerelateerde.add(gerelateerdeResult);
        expectedRelatie.add(relatieResult);
        expectedAantalIstStapels = 2;

        checkRelaties(relaties);
    }

    /**
     * Input:<br>
     * Cat09 leeg, niet onjuist<br>
     * Cat59 gevuld, onjuist<br>
     * <br>
     * Verwacht: Geen relatie<br>
     * Overigens worden er voor het Kind wel IST gegevens aangemaakt tbv de
     */
    @Test
    public void test1LegeRij1GevuldeOnjuisteRij() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(2);

        // Cat09
        final Lo3KindInhoud actInhoud = maakLegeInhoud();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Categorie<Lo3KindInhoud> actKind = maakCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat59
        final Lo3KindInhoud histKindInhoud = kindBuilder.build();
        final Lo3Historie histHistorie = maakHistorie(true, 20080101, 20080102);
        final Lo3Documentatie histDoc = maakAkte(Lo3String.wrap("2A"));
        final Lo3Categorie<Lo3KindInhoud> histKind = maakCategorie(histKindInhoud, histDoc, histHistorie, herkomsten[1]);

        final List<Lo3Stapel<Lo3KindInhoud>> kindStapels = maakKindStapel(Arrays.asList(actKind, histKind));
        final List<TussenRelatie> relaties = converteerKind(kindStapels);

        final ExpectedResult gerelateerdeResult = new ExpectedResult();
        gerelateerdeResult.inhoud = actInhoud;
        gerelateerdeResult.documentatie = actDoc;
        gerelateerdeResult.historie = actHistorie;

        assertEquals("Geen relaties verwacht", 0, relaties.size());
    }

    /**
     * Input:<br>
     * Cat09 gevuld, niet onjuist<br>
     * Cat59-1 gevuld, niet onjuist<br>
     * Cat59-2 gevuld, niet onjuist<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam- en geboortestapel gevuld vanuit Cat09<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat59-2<br>
     */
    @Test
    public void test3GevuldeJuisteRijen() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(3);
        // Cat09
        final Lo3KindInhoud actInhoud = kindBuilder.build();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Categorie<Lo3KindInhoud> actKind = maakCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat59-1
        kindBuilder.burgerservicenummer(null);
        final Lo3Documentatie histDoc = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie = maakHistorie(false, 20080101, 20080102);
        final Lo3Categorie<Lo3KindInhoud> histKind = maakCategorie(kindBuilder.build(), histDoc, histHistorie, herkomsten[1]);

        // Cat59-2
        kindBuilder.aNummer(null);
        final Lo3Documentatie histDoc2 = maakAkte(Lo3String.wrap("3A"));
        final Lo3Historie histHistorie2 = maakHistorie(false, 20070101, 20070102);
        final Lo3Categorie<Lo3KindInhoud> histKind2 = maakCategorie(kindBuilder.build(), histDoc2, histHistorie2, herkomsten[2]);

        final List<Lo3Stapel<Lo3KindInhoud>> kindStapels = maakKindStapel(Arrays.asList(actKind, histKind, histKind2));
        final List<TussenRelatie> relaties = converteerKind(kindStapels);

        final ExpectedResult gerelateerdeResult = new ExpectedResult();
        gerelateerdeResult.inhoud = actInhoud;
        gerelateerdeResult.documentatie = actDoc;
        gerelateerdeResult.historie = actHistorie;

        final ExpectedResult relatieResult = new ExpectedResult();
        relatieResult.inhoud = actInhoud;
        relatieResult.documentatie = histDoc2;
        relatieResult.historie = histHistorie2;

        expectedGerelateerde.add(gerelateerdeResult);
        expectedRelatie.add(relatieResult);
        expectedAantalIstStapels = 3;

        checkRelaties(relaties);
    }

    /**
     * Input:<br>
     * Cat09 gevuld, niet onjuist<br>
     * Cat59-1 gevuld, niet onjuist<br>
     * Cat59-2 leeg, niet onjuist<br>
     * Cat59-3 leeg, niet onjuist<br>
     * Cat59-4 gevuld, niet onjuist<br>
     * Cat59-5 gevuld, niet onjuist<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam- en geboortestapel gevuld vanuit Cat09<br>
     * - 2 stapels voor ouderstapel gevuld vanuit Cat59-1 en Cat59-5<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat59-3<br>
     */
    @Test
    public void testHeradoptie() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(6);
        // Cat09
        final Lo3KindInhoud actInhoud = kindBuilder.build();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Categorie<Lo3KindInhoud> actKind = maakCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat59-1
        kindBuilder.burgerservicenummer(null);
        final Lo3KindInhoud histInhoud1 = kindBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(false, 20080101, 20080102);
        final Lo3Categorie<Lo3KindInhoud> histKind1 = maakCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        // Cat59-2
        final Lo3KindInhoud histInhoud2 = maakLegeInhoud();
        final Lo3Documentatie histDoc2 = maakAkte(Lo3String.wrap("3A"));
        final Lo3Historie histHistorie2 = maakHistorie(false, 20070101, 20070102);
        final Lo3Categorie<Lo3KindInhoud> histKind2 = maakCategorie(histInhoud2, histDoc2, histHistorie2, herkomsten[2]);

        // Cat59-3
        final Lo3KindInhoud histInhoud3 = maakLegeInhoud();
        final Lo3Documentatie histDoc3 = maakAkte(Lo3String.wrap("3A2"));
        final Lo3Historie histHistorie3 = maakHistorie(false, 20060101, 20060102);
        final Lo3Categorie<Lo3KindInhoud> histKind3 = maakCategorie(histInhoud3, histDoc3, histHistorie3, herkomsten[3]);

        // Cat59-4
        final Lo3KindInhoud histInhoud4 = kindBuilder.build();
        final Lo3Documentatie histDoc4 = maakAkte(Lo3String.wrap("5A"));
        final Lo3Historie histHistorie4 = maakHistorie(false, 20050101, 20050102);
        final Lo3Categorie<Lo3KindInhoud> histKind4 = maakCategorie(histInhoud4, histDoc4, histHistorie4, herkomsten[4]);

        // Cat59-5
        kindBuilder.aNummer(null);
        final Lo3KindInhoud histInhoud5 = kindBuilder.build();
        final Lo3Documentatie histDoc5 = maakAkte(Lo3String.wrap("6A"));
        final Lo3Historie histHistorie5 = maakHistorie(false, 20040101, 20040102);
        final Lo3Categorie<Lo3KindInhoud> histKind5 = maakCategorie(histInhoud5, histDoc5, histHistorie5, herkomsten[5]);

        final List<Lo3Stapel<Lo3KindInhoud>> kindStapels = maakKindStapel(Arrays.asList(actKind, histKind1, histKind2, histKind3, histKind4, histKind5));
        final List<TussenRelatie> relaties = converteerKind(kindStapels);

        final ExpectedResult gerelateerdeResult = new ExpectedResult();
        gerelateerdeResult.inhoud = actInhoud;
        gerelateerdeResult.documentatie = actDoc;
        gerelateerdeResult.historie = actHistorie;

        final ExpectedResult relatieResult1 = new ExpectedResult();
        relatieResult1.inhoud = histInhoud1;
        relatieResult1.documentatie = histDoc1;
        relatieResult1.historie = histHistorie1;

        final ExpectedResult relatieResult3 = new ExpectedResult();
        relatieResult3.inhoud = null;
        relatieResult3.documentatie = histDoc3;
        relatieResult3.historie = histHistorie3;

        final ExpectedResult relatieResult5 = new ExpectedResult();
        relatieResult5.inhoud = histInhoud5;
        relatieResult5.documentatie = histDoc5;
        relatieResult5.historie = histHistorie5;

        expectedGerelateerde.add(gerelateerdeResult);
        expectedRelatie.add(relatieResult1);
        expectedRelatie.add(relatieResult3);
        expectedRelatie.add(relatieResult5);
        expectedAantalIstStapels = 6;

        checkRelaties(relaties);
    }

    /**
     * Input:<br>
     * Cat09 gevuld, niet onjuist<br>
     * Cat59-2 leeg, niet onjuist, 8510 jonger dan Cat09<br>
     * Cat59-4 gevuld, niet onjuist<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam- en geboortestapel gevuld vanuit Cat09<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat09<br>
     */
    @Test
    public void testHeradoptieHistorieOverlapMetActueel() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(3);
        // Cat09
        final Lo3KindInhoud actInhoud = kindBuilder.build();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Categorie<Lo3KindInhoud> actKind = maakCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat59-2
        final Lo3KindInhoud histInhoud2 = maakLegeInhoud();
        final Lo3Documentatie histDoc2 = maakAkte(Lo3String.wrap("3A"));
        final Lo3Historie histHistorie2 = maakHistorie(false, 20100101, 20100102);
        final Lo3Categorie<Lo3KindInhoud> histKind2 = maakCategorie(histInhoud2, histDoc2, histHistorie2, herkomsten[1]);

        // Cat59-4
        final Lo3KindInhoud histInhoud4 = kindBuilder.build();
        final Lo3Documentatie histDoc4 = maakAkte(Lo3String.wrap("5A"));
        final Lo3Historie histHistorie4 = maakHistorie(false, 20050101, 20050102);
        final Lo3Categorie<Lo3KindInhoud> histKind4 = maakCategorie(histInhoud4, histDoc4, histHistorie4, herkomsten[2]);

        final List<Lo3Stapel<Lo3KindInhoud>> kindStapels = maakKindStapel(Arrays.asList(actKind, histKind2, histKind4));
        final List<TussenRelatie> relaties = converteerKind(kindStapels);

        final ExpectedResult gerelateerdeResult = new ExpectedResult();
        gerelateerdeResult.inhoud = actInhoud;
        gerelateerdeResult.documentatie = actDoc;
        gerelateerdeResult.historie = actHistorie;

        final ExpectedResult relatieResult1 = new ExpectedResult();
        relatieResult1.inhoud = actInhoud;
        relatieResult1.documentatie = actDoc;
        relatieResult1.historie = actHistorie;

        expectedGerelateerde.add(gerelateerdeResult);
        expectedRelatie.add(relatieResult1);
        expectedAantalIstStapels = 3;

        checkRelaties(relaties);
    }

    /**
     * Input:<br>
     * Cat09 gevuld, niet onjuist<br>
     * Cat59-2 leeg, niet onjuist, 8510 jonger dan Cat09<br>
     * Cat59-1 gevuld, niet onjuist<br>
     * Cat59-3 leeg, niet onjuist<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapel voor identificatie-, samengesteldenaam- en geboortestapel gevuld vanuit Cat09<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat09<br>
     */
    @Test
    public void testMeerdereHeradoptiesMetOverlapActueel() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(4);
        // Cat09
        final Lo3KindInhoud actInhoud = kindBuilder.build();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Categorie<Lo3KindInhoud> actKind = maakCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat59-2
        final Lo3KindInhoud histInhoud2 = maakLegeInhoud();
        final Lo3Documentatie histDoc2 = maakAkte(Lo3String.wrap("3A"));
        final Lo3Historie histHistorie2 = maakHistorie(false, 20100101, 20100102);
        final Lo3Categorie<Lo3KindInhoud> histKind2 = maakCategorie(histInhoud2, histDoc2, histHistorie2, herkomsten[1]);

        // Cat59-1
        kindBuilder.burgerservicenummer(null);
        final Lo3KindInhoud histInhoud1 = kindBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(false, 20080101, 20080102);
        final Lo3Categorie<Lo3KindInhoud> histKind1 = maakCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[2]);

        // Cat59-3
        final Lo3KindInhoud histInhoud3 = maakLegeInhoud();
        final Lo3Documentatie histDoc3 = maakAkte(Lo3String.wrap("3A2"));
        final Lo3Historie histHistorie3 = maakHistorie(false, 20060101, 20060102);
        final Lo3Categorie<Lo3KindInhoud> histKind3 = maakCategorie(histInhoud3, histDoc3, histHistorie3, herkomsten[3]);

        final List<Lo3Stapel<Lo3KindInhoud>> kindStapels = maakKindStapel(Arrays.asList(actKind, histKind2, histKind1, histKind3));
        final List<TussenRelatie> relaties = converteerKind(kindStapels);

        final ExpectedResult gerelateerdeResult = new ExpectedResult();
        gerelateerdeResult.inhoud = actInhoud;
        gerelateerdeResult.documentatie = actDoc;
        gerelateerdeResult.historie = actHistorie;

        final ExpectedResult relatieResult1 = new ExpectedResult();
        relatieResult1.inhoud = actInhoud;
        relatieResult1.documentatie = actDoc;
        relatieResult1.historie = actHistorie;

        expectedGerelateerde.add(gerelateerdeResult);
        expectedRelatie.add(relatieResult1);
        expectedAantalIstStapels = 4;

        checkRelaties(relaties);
    }

    /**
     * Input:<br>
     * Cat09 leeg, niet onjuist<br>
     * Cat59-1 leeg, niet onjuist<br>
     * Cat59-2 gevuld, onjuist<br>
     * Cat59-3 gevuld, niet onjuist<br>
     * Cat59-4 gevuld, niet onjuist<br>
     * <br>
     * Verwacht:<br>
     * - 2 stapels voor identificatie-, samengesteldenaam- en geboortestapel gevuld vanuit Cat59-1 en Cat59-2<br>
     * - 1 stapels voor ouderstapel gevuld vanuit Cat59-3<br>
     * - 1 stapel voor ouderstapel gevuld vanuit Cat59-1<br>
     */
    @Test
    public void testLegeDanGevuldeRijen() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(5);
        // Cat09
        final Lo3KindInhoud actInhoud = maakLegeInhoud();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Categorie<Lo3KindInhoud> actKind = maakCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat59-1
        final Lo3KindInhoud histInhoud1 = maakLegeInhoud();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(false, 20080101, 20080102);
        final Lo3Categorie<Lo3KindInhoud> histKind1 = maakCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        // Cat59-2
        final Lo3KindInhoud histInhoud2 = kindBuilder.build();
        final Lo3Documentatie histDoc2 = maakAkte(Lo3String.wrap("3A"));
        final Lo3Historie histHistorie2 = maakHistorie(true, 20070202, 20070203);
        final Lo3Categorie<Lo3KindInhoud> histKind2 = maakCategorie(histInhoud2, histDoc2, histHistorie2, herkomsten[2]);

        // Cat59-3
        final Lo3KindInhoud histInhoud3 = kindBuilder.build();
        final Lo3Documentatie histDoc3 = maakAkte(Lo3String.wrap("3Aa"));
        final Lo3Historie histHistorie3 = maakHistorie(false, 20070101, 20070102);
        final Lo3Categorie<Lo3KindInhoud> histKind3 = maakCategorie(histInhoud3, histDoc3, histHistorie3, herkomsten[3]);

        // Cat59-4
        kindBuilder.aNummer(null);
        kindBuilder.burgerservicenummer(null);
        final Lo3KindInhoud histInhoud4 = kindBuilder.build();
        final Lo3Documentatie histDoc4 = maakAkte(Lo3String.wrap("5A"));
        final Lo3Historie histHistorie4 = maakHistorie(false, 20060101, 20060102);
        final Lo3Categorie<Lo3KindInhoud> histKind4 = maakCategorie(histInhoud4, histDoc4, histHistorie4, herkomsten[4]);

        final List<Lo3Stapel<Lo3KindInhoud>> kindStapels = maakKindStapel(Arrays.asList(actKind, histKind1, histKind2, histKind3, histKind4));
        final List<TussenRelatie> relaties = converteerKind(kindStapels);

        final ExpectedResult relatieResult1 = new ExpectedResult();
        relatieResult1.inhoud = null;
        relatieResult1.documentatie = histDoc1;
        relatieResult1.historie = histHistorie1;

        final ExpectedResult gerelateerdeResult2 = new ExpectedResult();
        gerelateerdeResult2.inhoud = histInhoud3;
        gerelateerdeResult2.documentatie = histDoc3;
        gerelateerdeResult2.historie = histHistorie3;

        final ExpectedResult relatieResult2 = new ExpectedResult();
        relatieResult2.inhoud = histInhoud4;
        relatieResult2.documentatie = histDoc4;
        relatieResult2.historie = histHistorie4;

        expectedGerelateerde.add(gerelateerdeResult2);
        expectedRelatie.add(relatieResult1);
        expectedRelatie.add(relatieResult2);
        expectedAantalIstStapels = 5;

        checkRelaties(relaties);
    }

    /**
     * Input:<br>
     * Cat09 gevuld, niet onjuist<br>
     * Cat59-1 gevuld, niet onjuist<br>
     * Cat59-2 leeg, niet onjuist<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapels voor identificatie-, samengesteldenaam- en geboortestapel gevuld vanuit Cat09<br>
     * - 1 stapels voor ouderstapel gevuld vanuit Cat59-1<br>
     */
    @Test
    public void testGevuldeDanLegeRijen() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(3);
        // Cat09
        final Lo3KindInhoud actInhoud = kindBuilder.build();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Categorie<Lo3KindInhoud> actKind = maakCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat59-1
        kindBuilder.aNummer(null);
        kindBuilder.burgerservicenummer(null);
        final Lo3KindInhoud histInhoud1 = kindBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(false, 20080101, 20080102);
        final Lo3Categorie<Lo3KindInhoud> histKind1 = maakCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        // Cat59-2
        final Lo3KindInhoud histInhoud2 = maakLegeInhoud();
        final Lo3Documentatie histDoc2 = maakAkte(Lo3String.wrap("3A"));
        final Lo3Historie histHistorie2 = maakHistorie(false, 20070101, 20070102);
        final Lo3Categorie<Lo3KindInhoud> histKind2 = maakCategorie(histInhoud2, histDoc2, histHistorie2, herkomsten[2]);

        final List<Lo3Stapel<Lo3KindInhoud>> kindStapels = maakKindStapel(Arrays.asList(actKind, histKind1, histKind2));
        final List<TussenRelatie> relaties = converteerKind(kindStapels);

        final ExpectedResult gerelateerdeResult = new ExpectedResult();
        gerelateerdeResult.inhoud = actInhoud;
        gerelateerdeResult.documentatie = actDoc;
        gerelateerdeResult.historie = actHistorie;

        final ExpectedResult relatieResult1 = new ExpectedResult();
        relatieResult1.inhoud = histInhoud1;
        relatieResult1.documentatie = histDoc1;
        relatieResult1.historie = histHistorie1;

        expectedGerelateerde.add(gerelateerdeResult);
        expectedRelatie.add(relatieResult1);
        expectedAantalIstStapels = 3;

        checkRelaties(relaties);
    }

    /**
     * Input:<br>
     * Cat09 gevuld, niet onjuist<br>
     * Cat59-1 gevuld, onjuist<br>
     * Cat59-2 leeg, niet onjuist<br>
     * Cat59-3 gevuld, onjuist<br>
     * Cat59-4 gevuld, niet onjuist<br>
     * <br>
     * Verwacht:<br>
     * - 1 stapels voor identificatie-, samengesteldenaam- en geboortestapel gevuld vanuit Cat09<br>
     * - 2 stapels voor ouderstapel gevuld vanuit Cat59-2, cat59-4<br>
     */
    @Test
    public void testMengelingGevuldOnjuistLegeRijen() {
        final Lo3Herkomst[] herkomsten = maakLo3Herkomsten(5);
        // Cat09
        final Lo3KindInhoud actInhoud = kindBuilder.build();
        final Lo3Documentatie actDoc = maakAkte();
        final Lo3Historie actHistorie = maakActueleHistorie();
        final Lo3Categorie<Lo3KindInhoud> actKind = maakCategorie(actInhoud, actDoc, actHistorie, herkomsten[0]);

        // Cat59-1
        kindBuilder.aNummer(null);
        final Lo3KindInhoud histInhoud1 = kindBuilder.build();
        final Lo3Documentatie histDoc1 = maakAkte(Lo3String.wrap("2A"));
        final Lo3Historie histHistorie1 = maakHistorie(true, 20080101, 20080102);
        final Lo3Categorie<Lo3KindInhoud> histKind1 = maakCategorie(histInhoud1, histDoc1, histHistorie1, herkomsten[1]);

        // Cat59-2
        final Lo3KindInhoud histInhoud2 = maakLegeInhoud();
        final Lo3Documentatie histDoc2 = maakAkte(Lo3String.wrap("3A"));
        final Lo3Historie histHistorie2 = maakHistorie(false, 20070101, 20070102);
        final Lo3Categorie<Lo3KindInhoud> histKind2 = maakCategorie(histInhoud2, histDoc2, histHistorie2, herkomsten[2]);

        // Cat59-3
        kindBuilder.burgerservicenummer(null);
        final Lo3KindInhoud histInhoud3 = kindBuilder.build();
        final Lo3Documentatie histDoc3 = maakAkte(Lo3String.wrap("3A1"));
        final Lo3Historie histHistorie3 = maakHistorie(true, 20060101, 20060102);
        final Lo3Categorie<Lo3KindInhoud> histKind3 = maakCategorie(histInhoud3, histDoc3, histHistorie3, herkomsten[3]);

        // Cat59-4
        kindBuilder.voornamen(Lo3String.wrap(VOORNAAM + " Joe"));
        final Lo3KindInhoud histInhoud4 = kindBuilder.build();
        final Lo3Documentatie histDoc4 = maakAkte(Lo3String.wrap("5A"));
        final Lo3Historie histHistorie4 = maakHistorie(false, 20050101, 20050102);
        final Lo3Categorie<Lo3KindInhoud> histKind4 = maakCategorie(histInhoud4, histDoc4, histHistorie4, herkomsten[4]);

        final List<Lo3Stapel<Lo3KindInhoud>> kindStapels = maakKindStapel(Arrays.asList(actKind, histKind1, histKind2, histKind3, histKind4));
        final List<TussenRelatie> relaties = converteerKind(kindStapels);

        final ExpectedResult gerelateerdeResult = new ExpectedResult();
        gerelateerdeResult.inhoud = actInhoud;
        gerelateerdeResult.documentatie = actDoc;
        gerelateerdeResult.historie = actHistorie;

        final ExpectedResult relatieResult1 = new ExpectedResult();
        relatieResult1.inhoud = actInhoud;
        relatieResult1.documentatie = actDoc;
        relatieResult1.historie = actHistorie;

        final ExpectedResult relatieResult2 = new ExpectedResult();
        relatieResult2.inhoud = null;
        relatieResult2.documentatie = histDoc2;
        relatieResult2.historie = histHistorie2;

        final ExpectedResult relatieResult3 = new ExpectedResult();
        relatieResult3.inhoud = histInhoud4;
        relatieResult3.documentatie = histDoc4;
        relatieResult3.historie = histHistorie4;

        expectedGerelateerde.add(gerelateerdeResult);
        expectedRelatie.add(relatieResult1);
        expectedRelatie.add(relatieResult2);
        expectedRelatie.add(relatieResult3);
        expectedAantalIstStapels = 5;

        checkRelaties(relaties);
    }

    @Test
    public void testControleLeegGevuldNiksVerwijderd(){
        List<Lo3Categorie<Lo3KindInhoud>> rijen = new ArrayList<>();
        rijen.add(createKindRijTBVControleLeegGevuld(510,510));
        rijen.add(createKindRijTBVControleLeegGevuld(501,501));
        rijen.add(createKindRijTBVControleLeegGevuld(502,502));
        kindConverteerder.contoleLeegGevuld(rijen,503);
        assertEquals("er is niks verwijderd",3,rijen.size());

    }

    @Test
    public void testControleLeegGevuldRijenVerwijderd(){
        List<Lo3Categorie<Lo3KindInhoud>> rijen = new ArrayList<>();
        rijen.add(createKindRijTBVControleLeegGevuld(500,500));
        rijen.add(createKindRijTBVControleLeegGevuld(495,495));
        rijen.add(createKindRijTBVControleLeegGevuld(490,490));
        rijen.add(createKindRijTBVControleLeegGevuld(395,395));
        rijen.add(createKindRijTBVControleLeegGevuld(390,390));
        rijen.add(createKindRijTBVControleLeegGevuld(295,295));
        rijen.add(createKindRijTBVControleLeegGevuld(290,290));
        kindConverteerder.contoleLeegGevuld(rijen,493);
        assertEquals("er zijn rijen verwijderd",5,rijen.size());

    }


    private Lo3Categorie<Lo3KindInhoud> createKindRijTBVControleLeegGevuld(int geldigheid,int opneming) {
        Lo3KindInhoud inhoud = new Lo3KindInhoud();
        Lo3Documentatie documentatie = new Lo3Documentatie(1, Lo3GemeenteCode.ONBEKEND,null,null,null,null,null,null);
        Lo3IndicatieOnjuist indicatie = new Lo3IndicatieOnjuist(null);
        Lo3Datum datumGeldigheid = new Lo3Datum(geldigheid);
        Lo3Datum datumOpneming = new Lo3Datum(opneming);
        Lo3Historie history = new Lo3Historie(indicatie,datumGeldigheid,datumOpneming);
        Lo3Herkomst herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01,1,1);
        return new Lo3Categorie<>(inhoud, documentatie, history, herkomst);
    }

    private List<TussenRelatie> converteerKind(final List<Lo3Stapel<Lo3KindInhoud>> kindStapels) {
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        kindConverteerder.converteer(kindStapels, builder);

        final TussenPersoonslijst migrPerslijst = builder.build();
        return migrPerslijst.getRelaties();
    }

    private void checkRelaties(final List<TussenRelatie> relaties) {
        assertEquals("Maar 1 relatie verwacht", 1, relaties.size());
        final TussenRelatie relatie = relaties.get(0);

        assertNotNull(relatie);
        assertEquals(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, relatie.getSoortRelatieCode());
        assertEquals(BrpSoortBetrokkenheidCode.OUDER, relatie.getRolCode());
        assertNull(relatie.getRelatieStapel());

        final TussenStapel<BrpIstRelatieGroepInhoud> istGegevens = relatie.getIstKind();
        assertNotNull(istGegevens);
        assertEquals("Aantal IST stapels klopt niet", expectedAantalIstStapels, istGegevens.size());

        final List<TussenBetrokkenheid> betrokkenheden = relatie.getBetrokkenheden();
        assertNotNull(betrokkenheden);
        assertEquals("Maar 1 betrokkene verwacht", 1, betrokkenheden.size());

        final TussenBetrokkenheid betrokkenheid = betrokkenheden.get(0);
        assertNotNull(betrokkenheid);
        assertEquals(BrpSoortBetrokkenheidCode.KIND, betrokkenheid.getRol());

        assertGeboorteStapel(betrokkenheid);
        assertGeslachtStapel(betrokkenheid);
        assertIdentificatieStapel(betrokkenheid);
        assertOuderlijkGezagStapel(betrokkenheid);
        assertSamengesteldeNaamStapel(betrokkenheid);
        assertOuderStapel(betrokkenheid);
    }

    private void assertOuderStapel(final TussenBetrokkenheid betrokkenheid) {
        final TussenStapel<BrpOuderInhoud> stapel = betrokkenheid.getOuderStapel();
        assertNotNull(stapel);
        assertEquals("Aantal ouder stapels klopt niet", expectedRelatie.size(), stapel.size());
        for (int index = 0; index < expectedRelatie.size(); index++) {
            final TussenGroep<BrpOuderInhoud> groep = stapel.get(index);
            final ExpectedResult expectedResult = expectedRelatie.get(index);

            assertNotNull(groep);
            if (expectedResult.inhoud != null) {
                assertTrue(BrpBoolean.unwrap(groep.getInhoud().getIndicatieOuder()));
            } else {
                assertNull(BrpBoolean.unwrap(groep.getInhoud().getIndicatieOuder()));
            }
            assertEquals("Ouder stapel historie klopt niet", expectedResult.historie, groep.getHistorie());
            assertEquals("Ouder stapel documentatie klopt niet", expectedResult.documentatie, groep.getDocumentatie());
        }
    }

    private void assertSamengesteldeNaamStapel(final TussenBetrokkenheid betrokkenheid) {
        final TussenStapel<BrpSamengesteldeNaamInhoud> stapel = betrokkenheid.getSamengesteldeNaamStapel();
        assertNotNull(stapel);
        assertEquals("Aantal samengestelde naam stapels klopt niet", expectedGerelateerde.size(), stapel.size());

        for (int index = 0; index < expectedGerelateerde.size(); index++) {
            final TussenGroep<BrpSamengesteldeNaamInhoud> groep = stapel.get(index);
            final ExpectedResult expectedResult = expectedGerelateerde.get(index);
            assertNotNull(groep);
            assertEquals(Lo3String.unwrap(expectedResult.inhoud.getVoornamen()), BrpString.unwrap(groep.getInhoud().getVoornamen()));
            assertEquals("Samengestelde naam stapel historie klopt niet", expectedResult.historie, groep.getHistorie());
            assertEquals("Samengestelde naam stapel documentatie klopt niet", expectedResult.documentatie, groep.getDocumentatie());
        }
    }

    private void assertOuderlijkGezagStapel(final TussenBetrokkenheid betrokkenheid) {
        final TussenStapel<BrpOuderlijkGezagInhoud> stapel = betrokkenheid.getOuderlijkGezagStapel();
        assertNull("Geen ouderlijk gezag stapel verwacht", stapel);
    }

    private void assertGeboorteStapel(final TussenBetrokkenheid betrokkenheid) {
        final TussenStapel<BrpGeboorteInhoud> stapel = betrokkenheid.getGeboorteStapel();
        assertNotNull(stapel);
        assertEquals("Aantal samengestelde naam stapels klopt niet", expectedGerelateerde.size(), stapel.size());

        for (int index = 0; index < expectedGerelateerde.size(); index++) {
            final TussenGroep<BrpGeboorteInhoud> groep = stapel.get(index);
            final ExpectedResult expectedResult = expectedGerelateerde.get(index);
            assertNotNull(groep);
            if (expectedResult.inhoud.getGeboortedatum() == null) {
                assertNull(groep.getInhoud().getGeboortedatum());
            } else {
                assertEquals(BrpDatum.fromLo3Datum(expectedResult.inhoud.getGeboortedatum()), groep.getInhoud().getGeboortedatum());
            }
            assertEquals("Geboorte stapel historie klopt niet", expectedResult.historie, groep.getHistorie());
            assertEquals("Geboorte stapel documentatie klopt niet", expectedResult.documentatie, groep.getDocumentatie());
        }
    }

    private void assertGeslachtStapel(final TussenBetrokkenheid betrokkenheid) {
        final TussenStapel<BrpGeslachtsaanduidingInhoud> stapel = betrokkenheid.getGeslachtsaanduidingStapel();
        assertNull("Geen geslacht stapel verwacht", stapel);
    }

    private void assertIdentificatieStapel(final TussenBetrokkenheid betrokkenheid) {
        final TussenStapel<BrpIdentificatienummersInhoud> stapel = betrokkenheid.getIdentificatienummersStapel();
        assertNotNull(stapel);
        assertEquals("Aantal identificatienummers stapels klopt niet", expectedGerelateerde.size(), stapel.size());

        for (int index = 0; index < expectedGerelateerde.size(); index++) {
            final TussenGroep<BrpIdentificatienummersInhoud> groep = stapel.get(index);
            final ExpectedResult expectedResult = expectedGerelateerde.get(index);

            assertNotNull(groep);
            assertEquals(Lo3Long.unwrap(expectedResult.inhoud.getaNummer()), BrpLong.unwrap(groep.getInhoud().getAdministratienummer()));
            assertEquals(Lo3Integer.unwrap(expectedResult.inhoud.getBurgerservicenummer()), BrpInteger.unwrap(groep.getInhoud().getBurgerservicenummer()));
            assertEquals("Identificatienummers stapel historie klopt niet", expectedResult.historie, groep.getHistorie());
            assertEquals("Identificatienummers stapel documentatie klopt niet", expectedResult.documentatie, groep.getDocumentatie());
        }
    }

    private static final class ExpectedResult {
        private Lo3KindInhoud inhoud;
        private Lo3Historie historie;
        private Lo3Documentatie documentatie;
    }
}
