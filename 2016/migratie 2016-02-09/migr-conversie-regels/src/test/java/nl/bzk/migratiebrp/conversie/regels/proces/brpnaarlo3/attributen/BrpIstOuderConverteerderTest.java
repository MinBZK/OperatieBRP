/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Ouder;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BrpIstOuderConverteerderTest extends AbstractBrpIstConverteerderTest<BrpIstRelatieGroepInhoud> {

    private Lo3Documentatie expectedAkte;
    private Lo3Historie expectedHistorie;
    private Lo3Herkomst expectedHerkomst;
    private Lo3OuderInhoud expectedInhoud;

    @Inject
    private BrpIstOuderConverteerder subject;

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.AbstractBrpIstConverteerderTest#getTestSubject()
     */
    @Override
    protected BrpIstOuderConverteerder getTestSubject() {
        return subject;
    }

    @Before
    public void setUp() {
        expectedAkte = maakDocumentatie(true);
        expectedHistorie = maakHistorie();
        expectedHerkomst = maakHerkomst(Lo3CategorieEnum.CATEGORIE_02);
        expectedInhoud =
                lo3Ouder(
                    ANUMMER,
                    BSN,
                    VOORNAMEN,
                    null,
                    VOORVOEGSEL,
                    GESLACHTSNAAMSTAM,
                    DATUM_GEBOORTE,
                    LO3_GEMEENTE_CODE,
                    LO3_LAND_CODE,
                    GESLACHT_MAN,
                    DATUM_GEBOORTE);

    }

    @Test
    public void testHappyFlow() {
        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();
        final BrpIstRelatieGroepInhoud inhoud = maakIstOuderGroepInhoud().build();

        groepen.add(maakGroep(inhoud));
        final Lo3Stapel<Lo3OuderInhoud> lo3Stapel = subject.converteer(new BrpStapel<>(groepen));

        controleerStapel(lo3Stapel, expectedInhoud, expectedAkte, null, expectedHistorie, expectedHerkomst);
    }

    private BrpIstStandaardGroepInhoud.Builder maakIstStandaarBuilder(final int voorkomen) {
        Lo3CategorieEnum categorie = Lo3CategorieEnum.CATEGORIE_02;
        if (voorkomen > 0) {
            categorie = Lo3CategorieEnum.bepaalHistorischeCategorie(categorie);
        }
        final BrpIstStandaardGroepInhoud.Builder standaardBuilder = new BrpIstStandaardGroepInhoud.Builder(categorie, 0, voorkomen);
        standaardBuilder.soortDocument(BRP_AKTE_DOCUMENT);
        standaardBuilder.partij(new BrpPartijCode(BRP_PARTIJ_CODE));
        standaardBuilder.rubriek8510IngangsdatumGeldigheid(new BrpInteger(DATUM_GELDIGHEID));
        standaardBuilder.rubriek8610DatumVanOpneming(new BrpInteger(DATUM_OPNEMING));
        standaardBuilder.aktenummer(new BrpString(AKTENUMMER));
        return standaardBuilder;
    }

    private BrpIstRelatieGroepInhoud.Builder maakIstOuderGroepInhoud() {
        return maakIstOuderGroepInhoud(maakIstStandaarBuilder(0).build());
    }

    private BrpIstRelatieGroepInhoud.Builder maakIstOuderGroepInhoud(final BrpIstStandaardGroepInhoud standaardGegevens) {

        final BrpIstRelatieGroepInhoud.Builder builder = new BrpIstRelatieGroepInhoud.Builder(standaardGegevens);
        builder.anummer(new BrpLong(ANUMMER));
        builder.bsn(new BrpInteger(BSN));
        builder.voornamen(new BrpString(VOORNAMEN));
        builder.voorvoegsel(new BrpString(VOORVOEGSEL));
        builder.scheidingsteken(new BrpCharacter(SCHEIDINGSTEKEN));
        builder.geslachtsnaamstam(new BrpString(GESLACHTSNAAMSTAM));
        builder.datumGeboorte(new BrpInteger(DATUM_GEBOORTE));
        builder.gemeenteCodeGeboorte(new BrpGemeenteCode(BRP_GEMEENTE_CODE));
        builder.landOfGebiedGeboorte(new BrpLandOfGebiedCode(BRP_LAND_OF_GEBIED_CODE_NL));
        builder.geslachtsaanduidingCode(BrpGeslachtsaanduidingCode.MAN);
        builder.rubriek6210DatumIngangFamilierechtelijkeBetrekking(new BrpInteger(DATUM_GEBOORTE));

        return builder;
    }

    @Test
    public void testAdellijkeTitel() {
        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();
        final BrpGeslachtsaanduidingCode geslachtsaanduiding = BrpGeslachtsaanduidingCode.MAN;
        final BrpAdellijkeTitelCode adellijkeTitel = new BrpAdellijkeTitelCode(BRP_ADELLIJKE_TITEL);
        adellijkeTitel.setGeslachtsaanduiding(geslachtsaanduiding);

        final BrpIstRelatieGroepInhoud.Builder builder = maakIstOuderGroepInhoud();
        builder.adellijkeTitel(adellijkeTitel);
        groepen.add(maakGroep(builder.build()));

        final Lo3Stapel<Lo3OuderInhoud> lo3Stapel = subject.converteer(new BrpStapel<>(groepen));

        expectedInhoud =
                lo3Ouder(
                    ANUMMER,
                    BSN,
                    VOORNAMEN,
                    LO3_ADELLIJKE_TITEL_MAN,
                    VOORVOEGSEL,
                    GESLACHTSNAAMSTAM,
                    DATUM_GEBOORTE,
                    LO3_GEMEENTE_CODE,
                    LO3_LAND_CODE,
                    GESLACHT_MAN,
                    DATUM_GEBOORTE);
        controleerStapel(lo3Stapel, expectedInhoud, expectedAkte, null, expectedHistorie, expectedHerkomst);
    }

    @Test
    public void testAdellijkeTitelVrouw() {
        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();
        final BrpGeslachtsaanduidingCode geslachtsaanduiding = BrpGeslachtsaanduidingCode.VROUW;
        final BrpAdellijkeTitelCode adellijkeTitel = new BrpAdellijkeTitelCode(BRP_ADELLIJKE_TITEL);
        adellijkeTitel.setGeslachtsaanduiding(geslachtsaanduiding);

        final BrpIstRelatieGroepInhoud.Builder builder = maakIstOuderGroepInhoud();
        builder.adellijkeTitel(adellijkeTitel);
        builder.geslachtsaanduidingCode(geslachtsaanduiding);
        groepen.add(maakGroep(builder.build()));

        final Lo3Stapel<Lo3OuderInhoud> lo3Stapel = subject.converteer(new BrpStapel<>(groepen));

        expectedInhoud =
                lo3Ouder(
                    ANUMMER,
                    BSN,
                    VOORNAMEN,
                    LO3_ADELLIJKE_TITEL_VROUW,
                    VOORVOEGSEL,
                    GESLACHTSNAAMSTAM,
                    DATUM_GEBOORTE,
                    LO3_GEMEENTE_CODE,
                    LO3_LAND_CODE,
                    GESLACHT_VROUW,
                    DATUM_GEBOORTE);

        controleerStapel(lo3Stapel, expectedInhoud, expectedAkte, null, expectedHistorie, expectedHerkomst);
    }

    @Test
    public void testPredikaat() {
        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();
        final BrpGeslachtsaanduidingCode geslachtsaanduidingCode = BrpGeslachtsaanduidingCode.MAN;
        final BrpPredicaatCode predicaatCode = new BrpPredicaatCode(BRP_PREDICAAT);
        predicaatCode.setGeslachtsaanduiding(geslachtsaanduidingCode);

        final BrpIstRelatieGroepInhoud.Builder builder = maakIstOuderGroepInhoud();
        builder.predicaat(predicaatCode);
        groepen.add(maakGroep(builder.build()));

        final Lo3Stapel<Lo3OuderInhoud> lo3Stapel = subject.converteer(new BrpStapel<>(groepen));

        expectedInhoud =
                lo3Ouder(
                    ANUMMER,
                    BSN,
                    VOORNAMEN,
                    LO3_PREDIKAAT_MAN,
                    VOORVOEGSEL,
                    GESLACHTSNAAMSTAM,
                    DATUM_GEBOORTE,
                    LO3_GEMEENTE_CODE,
                    LO3_LAND_CODE,
                    GESLACHT_MAN,
                    DATUM_GEBOORTE);

        controleerStapel(lo3Stapel, expectedInhoud, expectedAkte, null, expectedHistorie, expectedHerkomst);
    }

    @Test
    public void testPredikaatVrouw() {
        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();
        final BrpGeslachtsaanduidingCode geslachtsaanduidingCode = BrpGeslachtsaanduidingCode.VROUW;
        final BrpPredicaatCode predicaatCode = new BrpPredicaatCode(BRP_PREDICAAT);
        predicaatCode.setGeslachtsaanduiding(geslachtsaanduidingCode);

        final BrpIstRelatieGroepInhoud.Builder builder = maakIstOuderGroepInhoud();
        builder.predicaat(predicaatCode);
        builder.geslachtsaanduidingCode(geslachtsaanduidingCode);
        groepen.add(maakGroep(builder.build()));

        final Lo3Stapel<Lo3OuderInhoud> lo3Stapel = subject.converteer(new BrpStapel<>(groepen));

        expectedInhoud =
                lo3Ouder(
                    ANUMMER,
                    BSN,
                    VOORNAMEN,
                    LO3_PREDIKAAT_VROUW,
                    VOORVOEGSEL,
                    GESLACHTSNAAMSTAM,
                    DATUM_GEBOORTE,
                    LO3_GEMEENTE_CODE,
                    LO3_LAND_CODE,
                    GESLACHT_VROUW,
                    DATUM_GEBOORTE);

        controleerStapel(lo3Stapel, expectedInhoud, expectedAkte, null, expectedHistorie, expectedHerkomst);
    }

    @Test
    public void testDatumFamilierechtelijkeBetrekkingEnGeboortedatumLeeg() {
        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();
        final BrpIstRelatieGroepInhoud.Builder builder = maakIstOuderGroepInhoud();
        builder.datumGeboorte(null);
        builder.rubriek6210DatumIngangFamilierechtelijkeBetrekking(null);
        groepen.add(maakGroep(builder.build()));

        final Lo3Stapel<Lo3OuderInhoud> lo3Stapel = subject.converteer(new BrpStapel<>(groepen));

        expectedInhoud =
                lo3Ouder(ANUMMER, BSN, VOORNAMEN, null, VOORVOEGSEL, GESLACHTSNAAMSTAM, null, LO3_GEMEENTE_CODE, LO3_LAND_CODE, GESLACHT_MAN, null);

        controleerStapel(lo3Stapel, expectedInhoud, expectedAkte, null, expectedHistorie, expectedHerkomst);
    }

    @Test
    public void testOnderzoek() {
        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();
        final int gegevensInOnderzoek = 20610;
        final int datumIngangOnderzoek = 20120202;
        final int datumEindeOnderzoek = 20120203;

        final BrpIstStandaardGroepInhoud.Builder standaardBuilder = maakIstStandaarBuilder(0);
        standaardBuilder.rubriek8310AanduidingGegevensInOnderzoek(new BrpInteger(gegevensInOnderzoek, null));
        standaardBuilder.rubriek8320DatumIngangOnderzoek(new BrpInteger(datumIngangOnderzoek, null));
        standaardBuilder.rubriek8330DatumEindeOnderzoek(new BrpInteger(datumEindeOnderzoek, null));

        final BrpIstRelatieGroepInhoud.Builder builder = maakIstOuderGroepInhoud(standaardBuilder.build());
        final Lo3Onderzoek expectedOnderzoek =
                Lo3Onderzoek.build(
                    new Lo3Integer("0" + gegevensInOnderzoek, null),
                    new Lo3Datum(datumIngangOnderzoek),
                    new Lo3Datum(datumEindeOnderzoek),
                    expectedHerkomst);

        groepen.add(maakGroep(builder.build()));

        final Lo3Stapel<Lo3OuderInhoud> lo3Stapel = subject.converteer(new BrpStapel<>(groepen));

        controleerStapel(lo3Stapel, expectedInhoud, expectedAkte, expectedOnderzoek, expectedHistorie, expectedHerkomst);
    }

    @Test
    public void testHistorischVoorkomen() {
        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();

        final BrpIstRelatieGroepInhoud.Builder builderActueel = maakIstOuderGroepInhoud();

        final BrpIstStandaardGroepInhoud.Builder standaardBuilder = maakIstStandaarBuilder(1);
        standaardBuilder.rubriek8410OnjuistOfStrijdigOpenbareOrde(new BrpCharacter('O', null));
        final BrpIstRelatieGroepInhoud.Builder builderHistorisch = maakIstOuderGroepInhoud(standaardBuilder.build());

        groepen.add(maakGroep(builderActueel.build()));
        groepen.add(maakGroep(builderHistorisch.build()));

        final Lo3Herkomst herkomstHistorisch = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_52, 0, 1);
        final Lo3Stapel<Lo3OuderInhoud> lo3Stapel = subject.converteer(new BrpStapel<>(groepen));

        Assert.assertNotNull("Stapel is null", lo3Stapel);
        Assert.assertFalse("Stapel is leeg", lo3Stapel.isEmpty());
        Assert.assertEquals("2 stapels verwacht", 2, lo3Stapel.getCategorieen().size());

        final Lo3Categorie<Lo3OuderInhoud> voorkomenActueel = lo3Stapel.get(1);
        Assert.assertNotNull("Voorkomen is leeg", voorkomenActueel);
        Assert.assertEquals("Inhoud niet gelijk", expectedInhoud, voorkomenActueel.getInhoud());
        Assert.assertEquals("Herkomst niet gelijk", expectedHerkomst, voorkomenActueel.getLo3Herkomst());

        final Lo3Categorie<Lo3OuderInhoud> voorkomenHistorisch = lo3Stapel.get(0);
        Assert.assertNotNull("Voorkomen is leeg", voorkomenHistorisch);
        Assert.assertEquals("Inhoud niet gelijk", expectedInhoud, voorkomenHistorisch.getInhoud());
        Assert.assertEquals("Herkomst niet gelijk", herkomstHistorisch, voorkomenHistorisch.getLo3Herkomst());
    }
}
