/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import static nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum.MAN;
import static nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum.GEREGISTREERD_PARTNERSCHAP;
import static nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum.HUWELIJK;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3HuwelijkOfGp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenRelatie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import nl.bzk.migratiebrp.conversie.regels.testutils.ReflectionUtil;
import org.junit.Before;
import org.junit.Test;

public class HuwelijkOfGpConverteerderTest extends AbstractRelatieConverteerderTest {

    private static final String JANSEN = "Jansen";
    private static final String JAN = "Jan";
    private static final Lo3LandCode LAND = new Lo3LandCode(LAND_CODE);
    private static final Lo3GemeenteCode GEMEENTE = new Lo3GemeenteCode(GEMEENTE_CODE);

    private static final String VOORNAMEN = "Piet";
    private static final String VOORVOEGSEL = "van";
    private static final String GESLACHTSNAAM = "Klaveren";
    private static final Character SCHEIDINGSTEKEN = ' ';
    private static final String BRP_PARTIJ_CODE = "051801";
    private static final Integer DATUM_GEBOORTE = 1980101;
    private static final String GESLACHT_MAN = "M";
    private static final String GESLACHT_VROUW = "V";
    private static final String LO3_ADELLIJKE_TITEL_MAN = "B";
    private static final String BRP_ADELLIJKE_TITEL = "B";
    private static final String LO3_PREDIKAAT_VROUW = "JV";
    private static final String BRP_PREDIKAAT = "J";
    private static final Integer DATUM_SLUITING = 20120101;
    private static final Integer DATUM_ONTBINDING = 20120501;
    private static final Character REDEN_EINDE = 'S';

    private static final Lo3String AKTENUMMER = Lo3String.wrap("1A");
    private static final Integer DATUM_DOCUMENT = 20120103;
    private static final Lo3String DOCUMENT_OMSCHRIJVING = Lo3String.wrap("omschrijving");
    private static final Lo3Integer GEGEVENS_IN_ONDERZOEK = Lo3Integer.wrap(20110);
    private static final Integer DATUM_INGANG_ONDERZOEK = 20120202;
    private static final Integer DATUM_EINDE_ONDERZOEK = 20120203;

    private static final boolean JUIST = false;
    private static final boolean ONJUIST = true;

    private int voorkomenTeller;

    private boolean heeftAdellijkeTitel;
    private boolean heeftAkte;
    private boolean heeftDocumentatie;
    private boolean heeftOnderzoek;
    private boolean heeftOnjuist;
    private boolean heeftPredikaat;
    private Lo3CategorieEnum categorie;
    private BrpGeslachtsaanduidingCode geslachtsaanduiding;

    private HuwelijkOfGpConverteerder huwelijkOfGpConverteerder = new HuwelijkOfGpConverteerder(new Lo3AttribuutConverteerder(new ConversietabelFactoryImpl()));

    @Before
    public void setup() {
        voorkomenTeller = 0;

        heeftOnderzoek = false;
        heeftAkte = true;
        heeftDocumentatie = true;
        heeftOnjuist = false;
        heeftPredikaat = false;
        heeftAdellijkeTitel = false;
        geslachtsaanduiding = BrpGeslachtsaanduidingCode.MAN;
    }

    @Test
    public void testLegeActueel() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueel =
                maakHuwelijkOfGpRij(null, null, null, null, null, null, null, AKTENUMMER, JUIST, 20100101, 20100102);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> hist1 =
                maakHuwelijkOfGpRij(
                        A_NUMMER,
                        BSN,
                        VOORNAMEN,
                        GESLACHTSNAAM,
                        DATUM_SLUITING,
                        null,
                        HUWELIJK,
                        AKTENUMMER,
                        JUIST,
                        DATUM_SLUITING,
                        DATUM_SLUITING + 1);

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpStapels = maakHuwelijksStapels(actueel, hist1);
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        huwelijkOfGpConverteerder.converteer(huwelijkOfGpStapels, builder, null);
        final List<TussenRelatie> relatieStapels = getMigratieRelaties(builder);
        assertEquals(0, relatieStapels.size());
    }

    @Test
    public void testOntbindingActueleSluiting() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueel =
                maakHuwelijkOfGpRij(
                        A_NUMMER,
                        BSN,
                        VOORNAMEN,
                        GESLACHTSNAAM,
                        DATUM_SLUITING,
                        null,
                        HUWELIJK,
                        AKTENUMMER,
                        JUIST,
                        DATUM_SLUITING,
                        DATUM_SLUITING + 1);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> hist1 =
                maakHuwelijkOfGpRij(
                        A_NUMMER,
                        BSN,
                        VOORNAMEN,
                        GESLACHTSNAAM,
                        null,
                        DATUM_ONTBINDING,
                        HUWELIJK,
                        Lo3String.wrap("2A"),
                        JUIST,
                        DATUM_ONTBINDING,
                        DATUM_ONTBINDING + 1);

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpStapels = maakHuwelijksStapels(actueel, hist1);
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        huwelijkOfGpConverteerder.converteer(huwelijkOfGpStapels, builder, null);
        final List<TussenRelatie> relatieStapels = getMigratieRelaties(builder);
        checkRelatieStapels(relatieStapels);
    }

    @Test
    public void testMeerdereSoortenMeerdereStapels() {
        Logging.initContext();
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueelHuwelijk =
                maakHuwelijkOfGpRij(
                        A_NUMMER,
                        BSN,
                        VOORNAMEN,
                        GESLACHTSNAAM,
                        DATUM_SLUITING,
                        null,
                        HUWELIJK,
                        AKTENUMMER,
                        JUIST,
                        DATUM_SLUITING,
                        DATUM_SLUITING + 1);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> gp1 =
                maakHuwelijkOfGpRij(
                        A_NUMMER,
                        BSN,
                        VOORNAMEN,
                        GESLACHTSNAAM,
                        DATUM_SLUITING,
                        null,
                        GEREGISTREERD_PARTNERSCHAP,
                        Lo3String.wrap("2A"),
                        JUIST,
                        DATUM_SLUITING,
                        DATUM_SLUITING + 1);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> gp2 =
                maakHuwelijkOfGpRij(
                        A_NUMMER,
                        null,
                        VOORNAMEN,
                        GESLACHTSNAAM,
                        DATUM_SLUITING,
                        null,
                        GEREGISTREERD_PARTNERSCHAP,
                        Lo3String.wrap("2A"),
                        JUIST,
                        20120101,
                        DATUM_SLUITING + 1);

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpStapels = maakHuwelijksStapels(actueelHuwelijk, gp1, gp2);
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        huwelijkOfGpConverteerder.converteer(huwelijkOfGpStapels, builder, null);
        final List<TussenRelatie> relatieStapels = getMigratieRelaties(builder);
        assertEquals(2, relatieStapels.size());
        Logging.destroyContext();
    }

    @Test
    public void testOntbindingDoorOverlijden() {

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkStapels =
                maakHuwelijksStapels(maakHuwelijkOfGpRij(
                        A_NUMMER,
                        BSN,
                        VOORNAMEN,
                        GESLACHTSNAAM,
                        DATUM_SLUITING,
                        null,
                        HUWELIJK,
                        AKTENUMMER,
                        JUIST,
                        DATUM_SLUITING,
                        DATUM_SLUITING + 1));

        final Lo3Stapel<Lo3OverlijdenInhoud> overlijdenStapel =
                new Lo3Stapel<>(Collections.singletonList(new Lo3Categorie<>(
                        new Lo3OverlijdenInhoud(new Lo3Datum(DATUM_ONTBINDING), new Lo3GemeenteCode(GEMEENTE_CODE), new Lo3LandCode(LAND_CODE)),
                        new Lo3Documentatie(667, null, null, null, null, null, null, null),
                        new Lo3Historie(null, new Lo3Datum(DATUM_ONTBINDING), new Lo3Datum(DATUM_ONTBINDING + 1)),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 0))));

        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        huwelijkOfGpConverteerder.converteer(huwelijkStapels, builder, overlijdenStapel);

        final List<TussenRelatie> relatieStapels = getMigratieRelaties(builder);
        assertEquals(1, relatieStapels.size());

        final TussenStapel<BrpRelatieInhoud> relatieStapel = relatieStapels.get(0).getRelatieStapel();
        assertEquals(2, relatieStapel.size());

        final TussenGroep<BrpRelatieInhoud> ontbinding = relatieStapel.getLaatsteElement();
        assertEquals(new BrpRedenEindeRelatieCode('O'), ontbinding.getInhoud().getRedenEindeRelatieCode());
        assertEquals(new BrpGemeenteCode(GEMEENTE_CODE), ontbinding.getInhoud().getGemeenteCodeEinde());
        assertEquals(new BrpLandOfGebiedCode(LAND_CODE), ontbinding.getInhoud().getLandOfGebiedCodeEinde());
        assertEquals(new BrpDatum(DATUM_ONTBINDING, null), ontbinding.getInhoud().getDatumEinde());
        assertSame(overlijdenStapel.get(0).getHistorie(), ontbinding.getHistorie());
        assertSame(overlijdenStapel.get(0).getLo3Herkomst(), ontbinding.getLo3Herkomst());
        assertSame(overlijdenStapel.get(0).getDocumentatie(), ontbinding.getDocumentatie());
    }

    @Test
    public void test() {

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkStapels =
                maakHuwelijksStapels(maakHuwelijkOfGpRij(
                        A_NUMMER,
                        BSN,
                        VOORNAMEN,
                        GESLACHTSNAAM,
                        DATUM_SLUITING,
                        null,
                        HUWELIJK,
                        AKTENUMMER,
                        JUIST,
                        DATUM_SLUITING,
                        DATUM_SLUITING + 1));

        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        huwelijkOfGpConverteerder.converteer(huwelijkStapels, builder, null);

        final List<TussenRelatie> relatieStapels = getMigratieRelaties(builder);
        checkRelatieStapels(relatieStapels);
    }

    /**
     * IST Relateren document - voorbeeld 1-1.
     */
    @Test
    public void istHuwelijk() {

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkStapels =
                maakHuwelijksStapels(maakHuwelijkOfGpRij(
                        null,
                        null,
                        JAN,
                        JANSEN,
                        19900102,
                        null,
                        HUWELIJK,
                        Lo3String.wrap("1A"),
                        JUIST,
                        19900102,
                        19900103));

        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        huwelijkOfGpConverteerder.converteer(huwelijkStapels, builder, null);

        checkRelaties(
                getMigratieRelaties(builder),
                verwachtRelatie(null, JAN, JANSEN, Lo3String.wrap("1A"), rij(19900102, null, 19900103, Lo3String.wrap("1A"))));
    }

    /**
     * IST Relateren document - voorbeeld 1-2.
     */
    @Test
    public void istHuwelijkNieuwereAkte() {

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkStapels =
                maakHuwelijksStapels(
                        maakHuwelijkOfGpRij(A_NUMMER, null, JAN, JANSEN, 19900102, null, HUWELIJK, Lo3String.wrap("2A"), JUIST, 19920102, 19920103),
                        maakHuwelijkOfGpRij(null, null, JAN, JANSEN, 19900102, null, HUWELIJK, Lo3String.wrap("1A"), JUIST, 19900102, 19900103));

        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        huwelijkOfGpConverteerder.converteer(huwelijkStapels, builder, null);

        checkRelaties(
                getMigratieRelaties(builder),
                verwachtRelatie(A_NUMMER, JAN, JANSEN, Lo3String.wrap("2A"), rij(19900102, null, 19900103, Lo3String.wrap("1A"))));
    }

    /**
     * IST Relateren document - voorbeeld 1-3.
     */
    @Test
    public void istHuwelijkOntbindingLegeSluiting() {

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkStapels =
                maakHuwelijksStapels(
                        maakHuwelijkOfGpRij(null, null, JAN, JANSEN, null, 19950102, HUWELIJK, Lo3String.wrap("2A"), JUIST, 19950102, 19950103),
                        maakHuwelijkOfGpRij(null, null, JAN, JANSEN, 19900102, null, HUWELIJK, Lo3String.wrap("1A"), JUIST, 19900102, 19900103));

        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        huwelijkOfGpConverteerder.converteer(huwelijkStapels, builder, null);

        checkRelaties(
                getMigratieRelaties(builder),
                verwachtRelatie(
                        null,
                        JAN,
                        JANSEN,
                        Lo3String.wrap("2A"),
                        rij(19900102, 19950102, 19950103, Lo3String.wrap("2A")),
                        rij(19900102, null, 19900103, Lo3String.wrap("1A"))));
    }

    /**
     * IST Relateren document - voorbeeld 1-4.
     */
    @Test
    public void istHuwelijkOntbindingLegeSluitingNieuwereAkte() {

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkStapels =
                maakHuwelijksStapels(
                        maakHuwelijkOfGpRij(A_NUMMER, null, JAN, JANSEN, null, 19950102, HUWELIJK, Lo3String.wrap("3A"), JUIST, 19970102, 19970103),
                        maakHuwelijkOfGpRij(null, null, JAN, JANSEN, null, 19950102, HUWELIJK, Lo3String.wrap("2A"), JUIST, 19950102, 19950103),
                        maakHuwelijkOfGpRij(null, null, JAN, JANSEN, 19900102, null, HUWELIJK, Lo3String.wrap("1A"), JUIST, 19900102, 19900103));

        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        huwelijkOfGpConverteerder.converteer(huwelijkStapels, builder, null);

        checkRelaties(
                getMigratieRelaties(builder),
                verwachtRelatie(
                        A_NUMMER,
                        JAN,
                        JANSEN,
                        Lo3String.wrap("3A"),
                        rij(19900102, 19950102, 19950103, Lo3String.wrap("2A")),
                        rij(19900102, null, 19900103, Lo3String.wrap("1A"))));
    }

    /**
     * IST Relateren document - voorbeeld 1-5.
     */
    @Test
    public void istHuwelijkNieuwereAkteOntbindingLegeSluiting() {

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkStapels =
                maakHuwelijksStapels(
                        maakHuwelijkOfGpRij(A_NUMMER, null, JAN, JANSEN, null, 19950102, HUWELIJK, Lo3String.wrap("3A"), JUIST, 19970102, 19970103),
                        maakHuwelijkOfGpRij(A_NUMMER, null, JAN, JANSEN, 19900102, null, HUWELIJK, Lo3String.wrap("2A"), JUIST, 19950102, 19950103),
                        maakHuwelijkOfGpRij(null, null, JAN, JANSEN, 19900102, null, HUWELIJK, Lo3String.wrap("1A"), JUIST, 19900102, 19900103));

        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        huwelijkOfGpConverteerder.converteer(huwelijkStapels, builder, null);

        checkRelaties(
                getMigratieRelaties(builder),
                verwachtRelatie(
                        A_NUMMER,
                        JAN,
                        JANSEN,
                        Lo3String.wrap("3A"),
                        rij(19900102, 19950102, 19970103, Lo3String.wrap("3A")),
                        rij(19900102, null, 19900103, Lo3String.wrap("1A"))));
    }

    /**
     * IST Relateren document - voorbeeld 1-6.
     */
    @Test
    public void istHuwelijkOntbindingOnjuist() {

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkStapels =
                maakHuwelijksStapels(
                        maakHuwelijkOfGpRij(null, null, null, null, null, null, null, Lo3String.wrap("3A"), JUIST, 19900102, 19950104),
                        maakHuwelijkOfGpRij(null, null, JAN, JANSEN, null, 19950102, HUWELIJK, Lo3String.wrap("2A"), ONJUIST, 19950102, 19950103),
                        maakHuwelijkOfGpRij(null, null, JAN, JANSEN, 19900102, null, HUWELIJK, Lo3String.wrap("1A"), ONJUIST, 19900102, 19900103));

        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        huwelijkOfGpConverteerder.converteer(huwelijkStapels, builder, null);

        checkRelaties(getMigratieRelaties(builder) /* leeg resultaat verwacht */);
    }

    /**
     * IST Relateren document - voorbeeld 1-7.
     */
    @Test
    public void istHuwelijkNieuweActeAfwijkendeSluiting() {

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkStapels =
                maakHuwelijksStapels(
                        maakHuwelijkOfGpRij(A_NUMMER, null, JAN, JANSEN, 19900303, null, HUWELIJK, Lo3String.wrap("2A"), JUIST, 19920102, 19920102),
                        maakHuwelijkOfGpRij(null, null, JAN, JANSEN, 19900102, null, HUWELIJK, Lo3String.wrap("1A"), JUIST, 19900102, 19900103));

        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        huwelijkOfGpConverteerder.converteer(huwelijkStapels, builder, null);

        checkRelaties(
                getMigratieRelaties(builder),
                verwachtRelatie(A_NUMMER, JAN, JANSEN, Lo3String.wrap("2A"), rij(19900303, null, 19920102, Lo3String.wrap("2A"))));
    }

    /**
     * IST Relateren document - voorbeeld 1-8.
     */
    @Test
    public void istHuwelijkNieuweActeAfwijkendeIngangGeldigheid() {

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkStapels =
                maakHuwelijksStapels(
                        maakHuwelijkOfGpRij(A_NUMMER, null, JAN, JANSEN, 19900102, null, HUWELIJK, Lo3String.wrap("2A"), JUIST, 19920102, 19920102),
                        maakHuwelijkOfGpRij(null, null, JAN, JANSEN, 19900102, null, HUWELIJK, Lo3String.wrap("1A"), JUIST, 19900103, 19900103));

        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        huwelijkOfGpConverteerder.converteer(huwelijkStapels, builder, null);

        checkRelaties(
                getMigratieRelaties(builder),
                verwachtRelatie(A_NUMMER, JAN, JANSEN, Lo3String.wrap("2A"), rij(19900102, null, 19920102, Lo3String.wrap("2A"))));
    }

    /**
     * IST Relateren document - voorbeeld 1-9.
     */
    @Test
    public void istHuwelijkNieuweNaamAfwijkendeIngangGeldigheid() {

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkStapels =
                maakHuwelijksStapels(
                        maakHuwelijkOfGpRij(A_NUMMER, null, VOORNAMEN, JANSEN, 19900303, null, HUWELIJK, Lo3String.wrap("3A"), JUIST, 19940102, 19940102),
                        maakHuwelijkOfGpRij(null, null, VOORNAMEN, JANSEN, 19900303, null, HUWELIJK, Lo3String.wrap("2A"), JUIST, 19920102, 19920102),
                        maakHuwelijkOfGpRij(null, null, JAN, JANSEN, 19900102, null, HUWELIJK, Lo3String.wrap("1A"), JUIST, 19900103, 19900103));

        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();
        huwelijkOfGpConverteerder.converteer(huwelijkStapels, builder, null);

        checkRelaties(
                getMigratieRelaties(builder),
                verwachtRelatie(A_NUMMER, VOORNAMEN, JANSEN, Lo3String.wrap("3A"), rij(19900303, null, 19940102, Lo3String.wrap("3A"))));
    }

    @Test
    public void testConverteerIstHuwelijkSluitingMetPredikaatVrouw() {
        categorie = Lo3CategorieEnum.CATEGORIE_05;
        heeftPredikaat = true;
        geslachtsaanduiding = BrpGeslachtsaanduidingCode.VROUW;

        final Lo3HuwelijkOfGpInhoud huwelijk =
                lo3HuwelijkOfGp(
                        A_NUMMER,
                        BSN,
                        VOORNAMEN,
                        LO3_PREDIKAAT_VROUW,
                        VOORVOEGSEL,
                        GESLACHTSNAAM,
                        DATUM_GEBOORTE,
                        GEMEENTE_CODE,
                        LAND_CODE,
                        GESLACHT_VROUW,
                        DATUM_SLUITING,
                        GEMEENTE_CODE,
                        LAND_CODE,
                        null,
                        null,
                        null,
                        null,
                        HUWELIJK.getCode());
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkStapel = maakLo3Stapel(huwelijk);
        final TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> istStapel = huwelijkOfGpConverteerder.converteerIst(huwelijkStapel);

        assertNotNull(istStapel);
        assertEquals(1, istStapel.size());
        final BrpIstHuwelijkOfGpGroepInhoud inhoud = istStapel.get(0).getInhoud();
        assertNotNull(inhoud);

        assertStandaardEnIdentificatie(inhoud.getStandaardGegevens());
        assertGerelateerden(inhoud.getRelatie());
        assertHuwelijk(inhoud, true);
    }

    @Test
    public void testConverteerIstHuwelijkOntbindingMetAdelijkeTitelMan() {
        categorie = Lo3CategorieEnum.CATEGORIE_05;
        heeftAdellijkeTitel = true;

        final Lo3HuwelijkOfGpInhoud huwelijk =
                lo3HuwelijkOfGp(
                        A_NUMMER,
                        BSN,
                        VOORNAMEN,
                        LO3_ADELLIJKE_TITEL_MAN,
                        VOORVOEGSEL,
                        GESLACHTSNAAM,
                        DATUM_GEBOORTE,
                        GEMEENTE_CODE,
                        LAND_CODE,
                        GESLACHT_MAN,
                        null,
                        null,
                        null,
                        DATUM_ONTBINDING,
                        GEMEENTE_CODE,
                        LAND_CODE,
                        "" + REDEN_EINDE,
                        HUWELIJK.getCode());
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkStapel = maakLo3Stapel(huwelijk);
        final TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> istStapel = huwelijkOfGpConverteerder.converteerIst(huwelijkStapel);

        assertNotNull(istStapel);
        assertEquals(1, istStapel.size());
        final BrpIstHuwelijkOfGpGroepInhoud inhoud = istStapel.get(0).getInhoud();
        assertNotNull(inhoud);

        assertStandaardEnIdentificatie(inhoud.getStandaardGegevens());
        assertGerelateerden(inhoud.getRelatie());
        assertHuwelijk(inhoud, false);
    }

    @Test
    public void testConverteerIstHuwelijkSluitingZonderDocumentHeeftOnjuistEnOnderzoek() {
        categorie = Lo3CategorieEnum.CATEGORIE_05;
        heeftDocumentatie = false;
        heeftOnjuist = true;
        heeftOnderzoek = true;

        final Lo3HuwelijkOfGpInhoud huwelijk =
                lo3HuwelijkOfGp(
                        A_NUMMER,
                        BSN,
                        VOORNAMEN,
                        null,
                        VOORVOEGSEL,
                        GESLACHTSNAAM,
                        DATUM_GEBOORTE,
                        GEMEENTE_CODE,
                        LAND_CODE,
                        GESLACHT_MAN,
                        DATUM_SLUITING,
                        GEMEENTE_CODE,
                        LAND_CODE,
                        null,
                        null,
                        null,
                        null,
                        HUWELIJK.getCode());
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkStapel = maakLo3Stapel(huwelijk);
        final TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> istStapel = huwelijkOfGpConverteerder.converteerIst(huwelijkStapel);

        assertNotNull(istStapel);
        assertEquals(1, istStapel.size());
        final BrpIstHuwelijkOfGpGroepInhoud inhoud = istStapel.get(0).getInhoud();
        assertNotNull(inhoud);

        assertStandaardEnIdentificatie(inhoud.getStandaardGegevens());
        assertGerelateerden(inhoud.getRelatie());
        assertHuwelijk(inhoud, true);
    }

    private static List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> maakHuwelijksStapels(final Lo3Categorie<Lo3HuwelijkOfGpInhoud>... huwelijkRij) {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> rijen = new ArrayList<>();
        Collections.addAll(rijen, huwelijkRij);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkStapel = new Lo3Stapel<>(rijen);
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkStapels = new ArrayList<>();
        huwelijkStapels.add(huwelijkStapel);
        return huwelijkStapels;
    }

    private Lo3Categorie<Lo3HuwelijkOfGpInhoud> maakHuwelijkOfGpRij(
            final String aNummerPartner,
            final String bsnPartner,
            final String voornaamPartner,
            final String achternaamPartner,
            final Integer sluitingsDatum,
            final Integer ontbindingsDatum,
            final Lo3SoortVerbintenisEnum soortRelatie,
            final Lo3String aktenummer,
            final boolean onjuist,
            final Integer ingangsdatumGeldigheid,
            final Integer datumOpneming) {
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();
        builder.aNummer(Lo3String.wrap(aNummerPartner))
                .burgerservicenummer(Lo3String.wrap(bsnPartner))
                .voornamen(Lo3String.wrap(voornaamPartner))
                .geslachtsnaam(Lo3String.wrap(achternaamPartner))
                .geboortedatum(new Lo3Datum(DATUM_GEBOORTE))
                .geboorteGemeenteCode(GEMEENTE)
                .geboorteLandCode(LAND)
                .geslachtsaanduiding(MAN.asElement());
        if (soortRelatie != null) {
            builder.soortVerbintenis(soortRelatie.asElement());
        }
        if (sluitingsDatum != null) {
            builder.datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(sluitingsDatum))
                    .gemeenteCodeSluitingHuwelijkOfAangaanGp(GEMEENTE)
                    .landCodeSluitingHuwelijkOfAangaanGp(LAND);
        }
        if (ontbindingsDatum != null) {
            builder.datumOntbindingHuwelijkOfGp(new Lo3Datum(ontbindingsDatum))
                    .gemeenteCodeOntbindingHuwelijkOfGp(GEMEENTE)
                    .landCodeOntbindingHuwelijkOfGp(LAND);
        }

        final Lo3Documentatie documentatie = maakAkte(aktenummer);
        final Lo3Historie historie = maakHistorie(onjuist, ingangsdatumGeldigheid, datumOpneming);
        final Lo3Herkomst herkomst =
                new Lo3Herkomst(voorkomenTeller == 0 ? Lo3CategorieEnum.CATEGORIE_05 : Lo3CategorieEnum.CATEGORIE_55, 0, voorkomenTeller);

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkRij = new Lo3Categorie<>(builder.build(), documentatie, historie, herkomst);

        voorkomenTeller += 1;

        return huwelijkRij;
    }

    private List<TussenRelatie> getMigratieRelaties(final TussenPersoonslijstBuilder builder) {
        return new ArrayList<>((List<TussenRelatie>) ReflectionUtil.getField(builder, "relaties"));
    }

    private void checkRelaties(final List<TussenRelatie> tussenRelaties, final RelatieVerwachting... relatieVerwachting) {
        assertEquals("Aantal relaties", relatieVerwachting.length, tussenRelaties.size());

        for (final RelatieVerwachting verwachting : relatieVerwachting) {
            TussenRelatie relatie = null;
            for (final TussenRelatie tussenRelatie : tussenRelaties) {
                assertEquals("1 betrokkenheid per relatie", 1, tussenRelatie.getBetrokkenheden().size());
                final TussenBetrokkenheid betrokkene = tussenRelatie.getBetrokkenheden().get(0);
                if (betrokkene.getSamengesteldeNaamStapel().get(0).getDocumentatie().getNummerAkte().equals(verwachting.partnerActieInhoud)) {
                    relatie = tussenRelatie;
                    break;
                }
            }

            assertNotNull("Geen tussen-relate gevonden", relatie);

            final TussenBetrokkenheid betrokkene = relatie.getBetrokkenheden().get(0);

            checkPartnerANummer(verwachting, betrokkene);
            checkPartnerVoornaam(verwachting, betrokkene);
            checkPartnerGeslachtsnaam(verwachting, betrokkene);

            assertEquals("verwachtte aantal rijen klopt niet", verwachting.rijen.length, relatie.getRelatieStapel().size());

            final List<TussenGroep<BrpRelatieInhoud>> tussenGroepen = relatie.getRelatieStapel().getGroepen();
            tussenGroepen.sort(new DatumGeldigheidComparator());
            final Iterator<TussenGroep<BrpRelatieInhoud>> migratieRijIterator = tussenGroepen.iterator();
            final Iterator<RelatieRij> verwachtteRijIterator = Arrays.asList(verwachting.rijen).iterator();

            while (migratieRijIterator.hasNext()) {
                final TussenGroep<BrpRelatieInhoud> migratieRij = migratieRijIterator.next();
                final RelatieRij verwachtteRij = verwachtteRijIterator.next();

                assertEquals("Datum sluiting", verwachtteRij.datumSluiting, migratieRij.getInhoud().getDatumAanvang().getWaarde());
                assertEquals("Datum-tijd registratie", verwachtteRij.tsRegistratie, migratieRij.getHistorie()
                        .getDatumVanOpneming()
                        .getIntegerWaarde());
                assertEquals("Aktenummer", verwachtteRij.relatieActieInhoud, migratieRij.getDocumentatie().getNummerAkte());
                if (verwachtteRij.datumOntbinding != null) {
                    assertNotNull("Datum ontbinding verwacht", migratieRij.getInhoud().getDatumEinde());
                    assertEquals("Datum ontbinding", verwachtteRij.datumOntbinding, migratieRij.getInhoud().getDatumEinde().getWaarde());
                } else {
                    assertNull("Geen datum ontbinding verwacht", migratieRij.getInhoud().getDatumEinde());
                }
            }
        }
    }

    private void checkPartnerGeslachtsnaam(final RelatieVerwachting verwachting, final TussenBetrokkenheid betrokkene) {
        if (verwachting.partnerGeslachtsnaam == null) {
            assertTrue("Geen geslachtsnaam partner verwacht", betrokkene.getSamengesteldeNaamStapel() == null
                    || betrokkene.getSamengesteldeNaamStapel().get(0).getInhoud().getGeslachtsnaamstam() == null);
        } else {
            assertNotNull("Geslachtsnaam partner verwacht", betrokkene.getSamengesteldeNaamStapel());
            assertEquals(
                    "Geslachtsnaam partner",
                    verwachting.partnerGeslachtsnaam,
                    BrpString.unwrap(betrokkene.getSamengesteldeNaamStapel().get(0).getInhoud().getGeslachtsnaamstam()));
        }
    }

    private void checkPartnerVoornaam(final RelatieVerwachting verwachting, final TussenBetrokkenheid betrokkene) {
        if (verwachting.partnerVoornaam == null) {
            assertTrue("Geen voornaam partner verwacht", betrokkene.getSamengesteldeNaamStapel() == null
                    || betrokkene.getSamengesteldeNaamStapel().get(0).getInhoud().getVoornamen() == null);
        } else {
            assertNotNull("Voornaam partner verwacht", betrokkene.getSamengesteldeNaamStapel());
            assertEquals(
                    "Voornaam partner",
                    verwachting.partnerVoornaam,
                    BrpString.unwrap(betrokkene.getSamengesteldeNaamStapel().get(0).getInhoud().getVoornamen()));
        }
    }

    private void checkPartnerANummer(final RelatieVerwachting verwachting, final TussenBetrokkenheid betrokkene) {
        if (verwachting.partnerANummer == null) {
            assertTrue("Geen A-nummer partner verwacht", betrokkene.getIdentificatienummersStapel() == null
                    || betrokkene.getIdentificatienummersStapel().get(0).getInhoud().getAdministratienummer() == null);
        } else {
            assertNotNull("A-nummer partner verwacht", betrokkene.getIdentificatienummersStapel());
            assertEquals(
                    "A-nummer partner",
                    verwachting.partnerANummer,
                    BrpString.unwrap(betrokkene.getIdentificatienummersStapel().get(0).getInhoud().getAdministratienummer()));
        }
    }

    private RelatieVerwachting verwachtRelatie(
            final String partnerANummer,
            final String partnerVoornaam,
            final String partnerGeslachtsnaam,
            final Lo3String partnerActieInhoud,
            final RelatieRij... rijen) {
        return new RelatieVerwachting(partnerANummer, partnerVoornaam, partnerGeslachtsnaam, partnerActieInhoud, rijen);
    }

    private RelatieRij rij(final int datumSluiting, final Integer datumOntbinding, final int tsRegistratie, final Lo3String relatieActieInhoud) {
        return new RelatieRij(datumSluiting, datumOntbinding, tsRegistratie, relatieActieInhoud);
    }

    private void checkRelatieStapels(final List<TussenRelatie> relaties) {
        assertEquals(1, relaties.size());
        final TussenRelatie relatie = relaties.get(0);

        assertNotNull(relatie);
        assertEquals(BrpSoortRelatieCode.HUWELIJK, relatie.getSoortRelatieCode());

        final TussenStapel<BrpRelatieInhoud> relatieStapel = relatie.getRelatieStapel();
        assertNotNull(relatieStapel);
        assertEquals(1, relatieStapel.size());
        final TussenGroep<BrpRelatieInhoud> relatieGroep = relatieStapel.get(0);
        assertNotNull(relatieGroep);
        assertEquals(new BrpDatum(DATUM_SLUITING, null), relatieGroep.getInhoud().getDatumAanvang());

        assertEquals(BrpSoortBetrokkenheidCode.PARTNER, relatie.getRolCode());

        final List<TussenBetrokkenheid> betrokkenheden = relatie.getBetrokkenheden();
        assertNotNull(betrokkenheden);
        assertEquals(1, betrokkenheden.size());

        final TussenBetrokkenheid betrokkenheid = betrokkenheden.get(0);
        assertNotNull(betrokkenheid);
        assertEquals(BrpSoortBetrokkenheidCode.PARTNER, betrokkenheid.getRol());

        final TussenStapel<BrpGeboorteInhoud> geboorteStapel = betrokkenheid.getGeboorteStapel();
        final TussenStapel<BrpGeslachtsaanduidingInhoud> geslachtStapel = betrokkenheid.getGeslachtsaanduidingStapel();
        final TussenStapel<BrpIdentificatienummersInhoud> identificatieStapel = betrokkenheid.getIdentificatienummersStapel();
        final TussenStapel<BrpOuderlijkGezagInhoud> gezagStapel = betrokkenheid.getOuderlijkGezagStapel();
        final TussenStapel<BrpSamengesteldeNaamInhoud> naamStapel = betrokkenheid.getSamengesteldeNaamStapel();

        assertNotNull(geboorteStapel);
        assertEquals(1, geboorteStapel.size());
        final TussenGroep<BrpGeboorteInhoud> geboorteGroep = geboorteStapel.get(0);
        assertNotNull(geboorteGroep);
        assertEquals(new BrpDatum(DATUM_GEBOORTE, null), geboorteGroep.getInhoud().getGeboortedatum());

        assertNotNull(geslachtStapel);
        assertEquals(1, geslachtStapel.size());
        final TussenGroep<BrpGeslachtsaanduidingInhoud> geslachtGroep = geslachtStapel.get(0);
        assertNotNull(geslachtGroep);
        assertEquals(BrpGeslachtsaanduidingCode.MAN, geslachtGroep.getInhoud().getGeslachtsaanduidingCode());

        assertNotNull(identificatieStapel);
        assertEquals(1, identificatieStapel.size());
        final TussenGroep<BrpIdentificatienummersInhoud> identificatieGroep = identificatieStapel.get(0);
        assertNotNull(identificatieGroep);
        assertEquals(A_NUMMER, BrpString.unwrap(identificatieGroep.getInhoud().getAdministratienummer()));
        assertEquals(BSN, BrpString.unwrap(identificatieGroep.getInhoud().getBurgerservicenummer()));

        assertNull(gezagStapel);

        assertNotNull(naamStapel);
        assertEquals(1, naamStapel.size());
        final TussenGroep<BrpSamengesteldeNaamInhoud> naamGroep = naamStapel.get(0);
        assertNotNull(naamGroep);
        assertEquals(VOORNAMEN, BrpString.unwrap(naamGroep.getInhoud().getVoornamen()));
    }

    private <T extends Lo3CategorieInhoud> Lo3Stapel<T> maakLo3Stapel(final T inhoud) {
        Lo3Documentatie documentatie = null;
        if (heeftDocumentatie) {
            final Lo3GemeenteCode gemeente = new Lo3GemeenteCode(GEMEENTE_CODE);
            if (heeftAkte) {
                documentatie = new Lo3Documentatie(0L, gemeente, AKTENUMMER, null, null, null, null, null);
            } else {
                documentatie = new Lo3Documentatie(0L, null, null, gemeente, new Lo3Datum(DATUM_DOCUMENT), DOCUMENT_OMSCHRIJVING, null, null);
            }
        }
        Lo3Onderzoek onderzoek = null;
        if (heeftOnderzoek) {
            onderzoek = new Lo3Onderzoek(GEGEVENS_IN_ONDERZOEK, new Lo3Datum(DATUM_INGANG_ONDERZOEK), new Lo3Datum(DATUM_EINDE_ONDERZOEK));
        }
        final Lo3IndicatieOnjuist onjuist = heeftOnjuist ? Lo3IndicatieOnjuist.O : null;
        final Lo3Historie historie = new Lo3Historie(onjuist, new Lo3Datum(DATUM_GELDIGHEID), new Lo3Datum(DATUM_OPNEMING));
        final Lo3Herkomst lo3Herkomst = new Lo3Herkomst(categorie, 0, 0);
        final Lo3Categorie<T> voorkomen = new Lo3Categorie<>(inhoud, documentatie, onderzoek, historie, lo3Herkomst);

        final List<Lo3Categorie<T>> voorkomens = new ArrayList<>();
        voorkomens.add(voorkomen);
        return new Lo3Stapel<>(voorkomens);

    }

    private void assertStandaardEnIdentificatie(final BrpIstStandaardGroepInhoud inhoud) {
        // Identificatie
        assertEquals(categorie, inhoud.getCategorie());
        assertEquals(0, inhoud.getStapel());
        assertEquals(0, inhoud.getVoorkomen());
        // Standaard
        if (heeftDocumentatie) {
            assertEquals(new BrpPartijCode(BRP_PARTIJ_CODE), inhoud.getPartij());
            if (heeftAkte) {
                assertEquals(new BrpSoortDocumentCode("1"), inhoud.getSoortDocument());
                assertEquals(new BrpString(Lo3String.unwrap(AKTENUMMER)), inhoud.getAktenummer());
            } else {
                assertEquals(BrpSoortDocumentCode.HISTORIE_CONVERSIE, inhoud.getSoortDocument());
                assertEquals(DATUM_DOCUMENT, inhoud.getRubriek8220DatumDocument().getWaarde());
                assertEquals(Lo3String.unwrap(DOCUMENT_OMSCHRIJVING), inhoud.getDocumentOmschrijving().getWaarde());
            }
        } else {
            assertNull(inhoud.getSoortDocument());
            assertNull(inhoud.getAktenummer());
            assertNull(inhoud.getPartij());
            assertNull(inhoud.getRubriek8220DatumDocument());
            assertNull(inhoud.getDocumentOmschrijving());
        }

        if (heeftOnderzoek) {
            assertEquals(GEGEVENS_IN_ONDERZOEK, Lo3Integer.wrap(BrpInteger.unwrap(inhoud.getRubriek8310AanduidingGegevensInOnderzoek())));
            assertEquals(new BrpInteger(DATUM_INGANG_ONDERZOEK), inhoud.getRubriek8320DatumIngangOnderzoek());
            assertEquals(new BrpInteger(DATUM_EINDE_ONDERZOEK), inhoud.getRubriek8330DatumEindeOnderzoek());
        } else {
            assertNull(inhoud.getRubriek8310AanduidingGegevensInOnderzoek());
            assertNull(inhoud.getRubriek8320DatumIngangOnderzoek());
            assertNull(inhoud.getRubriek8330DatumEindeOnderzoek());
        }

        if (heeftOnjuist) {
            assertEquals(new BrpCharacter(Lo3IndicatieOnjuist.O.getCodeAsCharacter()), inhoud.getRubriek8410OnjuistOfStrijdigOpenbareOrde());
        } else {
            assertNull(inhoud.getRubriek8410OnjuistOfStrijdigOpenbareOrde());
        }

        assertEquals(DATUM_GELDIGHEID, BrpInteger.unwrap(inhoud.getRubriek8510IngangsdatumGeldigheid()).intValue());
        assertEquals(DATUM_OPNEMING, BrpInteger.unwrap(inhoud.getRubriek8610DatumVanOpneming()).intValue());
    }

    private void assertGerelateerden(final BrpIstRelatieGroepInhoud inhoud) {
        assertEquals(A_NUMMER, BrpString.unwrap(inhoud.getAnummer()));
        assertEquals(BSN, BrpString.unwrap(inhoud.getBsn()));
        assertEquals(new BrpString(VOORNAMEN), inhoud.getVoornamen());
        if (heeftPredikaat) {
            final BrpPredicaatCode predikaat = new BrpPredicaatCode(BRP_PREDIKAAT);
            predikaat.setGeslachtsaanduiding(geslachtsaanduiding);
            assertEquals(predikaat, inhoud.getPredicaatCode());
        } else {
            assertNull(inhoud.getPredicaatCode());
        }

        if (heeftAdellijkeTitel) {
            final BrpAdellijkeTitelCode adellijkeTitel = new BrpAdellijkeTitelCode(BRP_ADELLIJKE_TITEL);
            adellijkeTitel.setGeslachtsaanduiding(geslachtsaanduiding);
            assertEquals(adellijkeTitel, inhoud.getAdellijkeTitelCode());
        } else {
            assertNull(inhoud.getAdellijkeTitelCode());
        }

        assertEquals(new BrpString(VOORVOEGSEL), inhoud.getVoorvoegsel());
        assertEquals(new BrpCharacter(SCHEIDINGSTEKEN), inhoud.getScheidingsteken());
        assertEquals(new BrpString(GESLACHTSNAAM), inhoud.getGeslachtsnaamstam());
        assertEquals(new BrpInteger(DATUM_GEBOORTE), inhoud.getDatumGeboorte());
        assertEquals(new BrpGemeenteCode(GEMEENTE_CODE), inhoud.getGemeenteCodeGeboorte());
        assertNull(inhoud.getBuitenlandsePlaatsGeboorte());
        assertNull(inhoud.getOmschrijvingLocatieGeboorte());
        assertEquals(geslachtsaanduiding, inhoud.getGeslachtsaanduidingCode());
    }

    private void assertHuwelijk(final BrpIstHuwelijkOfGpGroepInhoud inhoud, final boolean sluiting) {
        if (sluiting) {
            assertEquals(new BrpInteger(DATUM_SLUITING), inhoud.getDatumAanvang());
            assertEquals(new BrpGemeenteCode(GEMEENTE_CODE), inhoud.getGemeenteCodeAanvang());
            assertNull(inhoud.getBuitenlandsePlaatsAanvang());
            assertNull(inhoud.getOmschrijvingLocatieAanvang());
            assertEquals(new BrpLandOfGebiedCode(LAND_CODE), inhoud.getLandOfGebiedCodeAanvang());
            assertNull(inhoud.getDatumEinde());
            assertNull(inhoud.getGemeenteCodeEinde());
            assertNull(inhoud.getBuitenlandsePlaatsEinde());
            assertNull(inhoud.getOmschrijvingLocatieEinde());
            assertNull(inhoud.getLandOfGebiedCodeEinde());
            assertNull(inhoud.getRedenEindeRelatieCode());
        } else {
            assertNull(inhoud.getDatumAanvang());
            assertNull(inhoud.getGemeenteCodeAanvang());
            assertNull(inhoud.getBuitenlandsePlaatsAanvang());
            assertNull(inhoud.getOmschrijvingLocatieAanvang());
            assertNull(inhoud.getLandOfGebiedCodeAanvang());
            assertEquals(new BrpInteger(DATUM_ONTBINDING), inhoud.getDatumEinde());
            assertEquals(new BrpGemeenteCode(GEMEENTE_CODE), inhoud.getGemeenteCodeEinde());
            assertNull(inhoud.getBuitenlandsePlaatsEinde());
            assertNull(inhoud.getOmschrijvingLocatieEinde());
            assertEquals(new BrpLandOfGebiedCode(LAND_CODE), inhoud.getLandOfGebiedCodeEinde());
            assertEquals(new BrpRedenEindeRelatieCode(REDEN_EINDE), inhoud.getRedenEindeRelatieCode());
        }
        assertEquals(new BrpSoortRelatieCode(HUWELIJK.getCode()), inhoud.getSoortRelatieCode());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.AbstractRelatieConverteerderTest#getCategorie()
     */
    @Override
    protected Lo3CategorieEnum getCategorie() {
        return Lo3CategorieEnum.CATEGORIE_05;
    }

    private static final class RelatieVerwachting {
        private final RelatieRij[] rijen;
        private final String partnerANummer;
        private final String partnerVoornaam;
        private final String partnerGeslachtsnaam;
        private final Lo3String partnerActieInhoud;

        RelatieVerwachting(
                final String partnerANummer,
                final String partnerVoornaam,
                final String partnerGeslachtsnaam,
                final Lo3String partnerActieInhoud,
                final RelatieRij... rijen) {
            this.partnerANummer = partnerANummer;
            this.partnerVoornaam = partnerVoornaam;
            this.partnerGeslachtsnaam = partnerGeslachtsnaam;
            this.partnerActieInhoud = partnerActieInhoud;
            this.rijen = rijen;
        }
    }

    private static final class RelatieRij {
        private final Integer datumSluiting;
        private final Integer datumOntbinding;
        private final Integer tsRegistratie;
        private final Lo3String relatieActieInhoud;

        RelatieRij(final Integer datumSluiting, final Integer datumOntbinding, final Integer tsRegistratie, final Lo3String relatieActieInhoud) {
            this.datumSluiting = datumSluiting;
            this.datumOntbinding = datumOntbinding;
            this.tsRegistratie = tsRegistratie;
            this.relatieActieInhoud = relatieActieInhoud;
        }
    }

    /**
     * Sorteert de TussenGroep op 85.10 (datum ingang geldigheid) van nieuw naar oud.
     */
    private static class DatumGeldigheidComparator implements Comparator<TussenGroep<? extends BrpGroepInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final TussenGroep<? extends BrpGroepInhoud> o1, final TussenGroep<? extends BrpGroepInhoud> o2) {
            return o2.getHistorie().getIngangsdatumGeldigheid().compareTo(o1.getHistorie().getIngangsdatumGeldigheid());
        }

    }
}
