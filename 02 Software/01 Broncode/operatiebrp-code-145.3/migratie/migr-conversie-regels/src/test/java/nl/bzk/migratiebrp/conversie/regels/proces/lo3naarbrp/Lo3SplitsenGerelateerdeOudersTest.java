/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractLoggingTest;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3SplitsenGerelateerdeOuders;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.OuderRelatie;
import org.junit.Test;

/**
 * Test voor het splitsen van gerelateerde ouders.
 */
public class Lo3SplitsenGerelateerdeOudersTest extends AbstractLoggingTest {

    private static final Lo3Datum DATUM_19800101 = new Lo3Datum(1980_01_01);
    private static final Lo3Datum DATUM_19800102 = new Lo3Datum(1980_01_02);
    private static final Lo3Datum DATUM_19800103 = new Lo3Datum(1980_01_03);
    private static final Lo3Datum DATUM_00000000 = new Lo3Datum(0);
    private static final Lo3Datum INGANGS_DATUM_0 = new Lo3Datum(1990_12_31);
    private static final Lo3Datum INGANGS_DATUM_1 = new Lo3Datum(1991_01_01);
    private static final Lo3Datum INGANGS_DATUM_2 = new Lo3Datum(1991_01_02);
    private static final Lo3Datum INGANGS_DATUM_3 = new Lo3Datum(1991_01_03);
    private static final Lo3Datum INGANGS_DATUM_4 = new Lo3Datum(1991_01_04);
    private static final Lo3Datum INGANGS_DATUM_5 = new Lo3Datum(1991_01_05);
    private static final Lo3Datum INGANGS_DATUM_6 = new Lo3Datum(1991_01_06);
    private static final Lo3Datum INGANGS_DATUM_7 = new Lo3Datum(1991_01_07);
    private static final Lo3Datum INGANGS_DATUM_8 = new Lo3Datum(1991_01_08);
    private static final Lo3Datum INGANGS_DATUM_9 = new Lo3Datum(1991_01_09);
    private static final Lo3Herkomst HERKOMST_02_0 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0);
    private static final Lo3Herkomst HERKOMST_52_1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_52, 0, 1);
    private static final Lo3Herkomst HERKOMST_52_2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_52, 0, 2);
    private static final Lo3Herkomst HERKOMST_52_3 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_52, 0, 3);
    private static final Lo3Herkomst HERKOMST_52_4 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_52, 0, 4);
    private static final Lo3Herkomst HERKOMST_52_5 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_52, 0, 5);
    private static final Lo3Herkomst HERKOMST_52_6 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_52, 0, 6);
    private static final Lo3Herkomst HERKOMST_52_7 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_52, 0, 7);
    private static final Lo3Herkomst HERKOMST_52_8 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_52, 0, 8);
    private static final Lo3Herkomst HERKOMST_52_9 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_52, 0, 9);

    @Test
    public void testPuntOuder_grote_stapel_1() {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder("1", "A", HERKOMST_02_0, DATUM_19800101, INGANGS_DATUM_8));
        ouders.add(VerplichteStapel.createOuder("1", "A", HERKOMST_52_1, DATUM_19800101, INGANGS_DATUM_7));
        ouders.add(VerplichteStapel.createOuder("1", "A", HERKOMST_52_3, DATUM_19800101, INGANGS_DATUM_6));

        ouders.add(VerplichteStapel.createOuder("1", "A", HERKOMST_52_4, DATUM_19800102, INGANGS_DATUM_5));
        ouders.add(VerplichteStapel.createOuder("1", "A", HERKOMST_52_5, DATUM_19800102, INGANGS_DATUM_4));

        ouders.add(VerplichteStapel.createOuder("1", "A", HERKOMST_52_6, DATUM_19800101, INGANGS_DATUM_3));

        ouders.add(VerplichteStapel.createOuder("1", "A", HERKOMST_52_7, DATUM_19800103, INGANGS_DATUM_2));
        ouders.add(VerplichteStapel.createOuder("1", "A", HERKOMST_52_8, DATUM_19800103, INGANGS_DATUM_1));

        ouders.add(VerplichteStapel.createOuder("1", "A", HERKOMST_52_9, DATUM_19800102, INGANGS_DATUM_0));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 5, lo3Stapels.size());
    }


    @Test
    public void testAnr() {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder(null, "A", HERKOMST_02_0, DATUM_00000000, INGANGS_DATUM_6));
        ouders.add(VerplichteStapel.createOuder(null, "A", HERKOMST_52_1, DATUM_00000000, INGANGS_DATUM_1));
        ouders.add(VerplichteStapel.createOuder("123", "A", HERKOMST_52_3, DATUM_00000000, INGANGS_DATUM_2));
        ouders.add(VerplichteStapel.createOuder(null, "A", HERKOMST_52_4, DATUM_00000000, INGANGS_DATUM_1));
        ouders.add(VerplichteStapel.createOuder("123", "A", HERKOMST_52_5, DATUM_00000000, INGANGS_DATUM_0));
        ouders.add(VerplichteStapel.createOuder("456", "A", HERKOMST_52_6, DATUM_00000000, INGANGS_DATUM_7));
        ouders.add(VerplichteStapel.createOuder(null, "A", HERKOMST_52_7, DATUM_00000000, INGANGS_DATUM_8));

        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 4, lo3Stapels.size());
        assertEquals(2, lo3Stapels.get(0).getRecords().size());
        assertEquals(4, lo3Stapels.get(1).getRecords().size());
        assertEquals(2, lo3Stapels.get(2).getRecords().size());
        assertEquals(2, lo3Stapels.get(3).getRecords().size());
    }

    @Test
    public void juridischGeenOuder() {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder("1", "A", HERKOMST_02_0, null, INGANGS_DATUM_8));
        ouders.add(VerplichteStapel.createOuder("2", "A", HERKOMST_52_1, DATUM_19800101, INGANGS_DATUM_7));
        ouders.add(VerplichteStapel.createOuder("3", "A", HERKOMST_52_3, null, INGANGS_DATUM_8));
        ouders.add(VerplichteStapel.createOuder("4", "A", HERKOMST_52_4, DATUM_19800101, INGANGS_DATUM_7));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 2, lo3Stapels.size());
        assertAantalInfos(0);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB016, 0);
    }

    @Test
    public void testPuntOuder_grote_stapel_2() {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder(null, "A", HERKOMST_02_0, DATUM_00000000, INGANGS_DATUM_8));

        ouders.add(VerplichteStapel.createOuder(null, ".", HERKOMST_52_1, DATUM_00000000, INGANGS_DATUM_9));

        ouders.add(VerplichteStapel.createOuder(null, "A", HERKOMST_52_3, DATUM_00000000, INGANGS_DATUM_7));

        ouders.add(VerplichteStapel.createOuder("4", "A", HERKOMST_52_4, DATUM_19800102, INGANGS_DATUM_6));
        ouders.add(VerplichteStapel.createOuder("5", "A", HERKOMST_52_5, DATUM_19800102, INGANGS_DATUM_5));

        ouders.add(VerplichteStapel.createOuder("6", "A", HERKOMST_52_6, DATUM_19800101, INGANGS_DATUM_4));

        ouders.add(VerplichteStapel.createOuder("8", "A", HERKOMST_52_8, DATUM_19800103, INGANGS_DATUM_3));
        ouders.add(VerplichteStapel.createOuder("7", "A", HERKOMST_52_7, DATUM_19800103, INGANGS_DATUM_2));

        ouders.add(VerplichteStapel.createOuder("9", "A", HERKOMST_52_9, DATUM_19800102, INGANGS_DATUM_1));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 7, lo3Stapels.size());
    }


    @Test
    public void testPuntOuder_grote_stapel_3() {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder("1", "A", HERKOMST_02_0, new Lo3Datum(2013_01_01), new Lo3Datum(2015_01_01)));
        ouders.add(VerplichteStapel.createOuder("2", "A", HERKOMST_52_1, new Lo3Datum(2013_01_01), new Lo3Datum(2016_01_01)));

        ouders.add(VerplichteStapel.createOuder("3", "A", HERKOMST_52_3, new Lo3Datum(2012_01_01), new Lo3Datum(2014_01_01)));

        ouders.add(VerplichteStapel.createOuder("4", "A", HERKOMST_52_4, new Lo3Datum(2013_01_01), new Lo3Datum(2013_01_01)));

        ouders.add(VerplichteStapel.createOuder("5", "A", HERKOMST_52_5, new Lo3Datum(2012_01_01), new Lo3Datum(2012_01_01)));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 4, lo3Stapels.size());
    }


    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB016)
    public void testBijzondereConditieMeerdereOuder1Personen() {
        final Lo3Categorie<Lo3OuderInhoud> ouder1Actueel = VerplichteStapel.createOuder("1000000000", Lo3CategorieEnum.CATEGORIE_02);
        final Lo3Categorie<Lo3OuderInhoud> ouder1Historie = VerplichteStapel.createOuder("2000000000", HERKOMST_52_1, DATUM_19800101);

        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(ouder1Actueel);
        ouders.add(ouder1Historie);

        Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));

        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB016, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB016)
    public void testBijzondereConditieMeerdereOuder1ZelfdePersonen() {
        final String anummer = "1000000000";
        final Lo3Categorie<Lo3OuderInhoud> ouder1Actueel = VerplichteStapel.createOuder(anummer, HERKOMST_02_0, DATUM_19800101);
        final Lo3Categorie<Lo3OuderInhoud> ouder1Historie = VerplichteStapel.createOuder(anummer, HERKOMST_52_1, DATUM_19800101);

        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(ouder1Actueel);
        ouders.add(ouder1Historie);

        Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));

        assertAantalInfos(0);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB016, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB016)
    public void testBijzondereConditieMeerdereOuder1ZelfdePersonenVerschillendAnummer() {
        final String anummer1 = "1000000000";
        final String anummer2 = "2000000000";
        final Lo3Categorie<Lo3OuderInhoud> ouder1Actueel = VerplichteStapel.createOuder(anummer1, HERKOMST_02_0, DATUM_19800101);
        final Lo3Categorie<Lo3OuderInhoud> ouder1Historie = VerplichteStapel.createOuder(anummer2, HERKOMST_52_1, DATUM_19800101);

        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(ouder1Actueel);
        ouders.add(ouder1Historie);

        Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));

        assertAantalInfos(0);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB016, 0);
    }

    @Test
    public void testPuntOuder_eerst_punt_daarna_bekend() {
        final String anummer = "1000000000";
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder(anummer, HERKOMST_02_0, DATUM_19800101));
        ouders.add(VerplichteStapel.createOuder(null, ".", HERKOMST_52_1, DATUM_00000000));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 2, lo3Stapels.size());
    }

    @Test
    public void testPuntOuder_eerst_bekend_daarna_punt() {
        final String anummer = "1000000000";
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder(null, ".", HERKOMST_02_0, DATUM_00000000));
        ouders.add(VerplichteStapel.createOuder(anummer, HERKOMST_52_1, DATUM_19800101));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 2, lo3Stapels.size());
    }

    @Test
    public void testPuntOuder_twee_keer_punt_daarna_bekend() {
        final String anummer = "1000000000";
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder(anummer, HERKOMST_52_2, DATUM_19800101));
        ouders.add(VerplichteStapel.createOuder(null, ".", HERKOMST_52_1, DATUM_00000000));
        ouders.add(VerplichteStapel.createOuder(null, ".", HERKOMST_02_0, DATUM_00000000));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 3, lo3Stapels.size());
    }

    @Test
    public void testPuntOuder_anummer_2_leeg_1_gevuld() {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder(null, "naam", HERKOMST_52_2, DATUM_19800101));
        ouders.add(VerplichteStapel.createOuder(null, "naam2", HERKOMST_52_1, DATUM_19800101));
        ouders.add(VerplichteStapel.createOuder("1", "naam2", HERKOMST_02_0, DATUM_19800101));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 1, lo3Stapels.size());
    }

    @Test
    public void testPuntOuder_anummer_leeg_gevuld_leeg() {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder(null, "naam", HERKOMST_52_2, DATUM_19800101));
        ouders.add(VerplichteStapel.createOuder("1", "naam2", HERKOMST_52_1, DATUM_19800101));
        ouders.add(VerplichteStapel.createOuder(null, "naam2", HERKOMST_02_0, DATUM_19800101));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 1, lo3Stapels.size());
    }

    @Test
    public void testPuntOuder_anummer_leeg_gevuld_leeg_metStandaardWaardes() {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder(null, "naam", HERKOMST_52_2, DATUM_00000000));
        ouders.add(VerplichteStapel.createOuder("1", "naam2", HERKOMST_52_1, DATUM_00000000));
        ouders.add(VerplichteStapel.createOuder(null, "naam2", HERKOMST_02_0, DATUM_00000000));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 2, lo3Stapels.size());
    }


    @Test
    public void testPuntOuder_anummer_leeg_gevuld_ander() {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder(null, "naam", HERKOMST_52_2, DATUM_19800101));
        ouders.add(VerplichteStapel.createOuder("1", "naam2", HERKOMST_52_1, DATUM_19800101));
        ouders.add(VerplichteStapel.createOuder("2", "naam2", HERKOMST_02_0, DATUM_19800101));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 1, lo3Stapels.size());
    }

    @Test
    public void testPuntOuder_anummer_leeg_gevuld_ander_met_standaardwaardes() {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder(null, ".", HERKOMST_52_2, DATUM_00000000));
        ouders.add(VerplichteStapel.createOuder("1", "naam2", HERKOMST_52_1, DATUM_00000000));
        ouders.add(VerplichteStapel.createOuder("2", "naam2", HERKOMST_02_0, DATUM_00000000));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 3, lo3Stapels.size());
    }

    @Test
    public void testPuntOuder_anummer_gevuld_ander_leeg() {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder("2", "naam", HERKOMST_52_2, DATUM_19800101));
        ouders.add(VerplichteStapel.createOuder("1", "naam2", HERKOMST_52_1, DATUM_19800101));
        ouders.add(VerplichteStapel.createOuder(null, "naam2", HERKOMST_02_0, DATUM_19800101));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 1, lo3Stapels.size());
    }

    @Test
    public void testPuntOuder_anummer_gevuld_ander_leeg_metStandaardWaarden() {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder("2", "naam", HERKOMST_52_2, DATUM_00000000, INGANGS_DATUM_1));
        ouders.add(VerplichteStapel.createOuder("1", "naam2", HERKOMST_52_1, DATUM_00000000, INGANGS_DATUM_2));
        ouders.add(VerplichteStapel.createOuder(null, "naam2", HERKOMST_02_0, DATUM_00000000, INGANGS_DATUM_3));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 3, lo3Stapels.size());
    }

    @Test
    public void testPuntOuder_anummer_gevuld_ander_leeg_metStandaardWaarden_volgorde2() {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(VerplichteStapel.createOuder("2", "naam", HERKOMST_52_2, DATUM_00000000, INGANGS_DATUM_1));
        ouders.add(VerplichteStapel.createOuder("1", "naam2", HERKOMST_52_1, DATUM_00000000, INGANGS_DATUM_3));
        ouders.add(VerplichteStapel.createOuder(null, "naam2", HERKOMST_02_0, DATUM_00000000, INGANGS_DATUM_2));
        final List<OuderRelatie> lo3Stapels = Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));
        assertEquals("aantal stapels na splitsen", 3, lo3Stapels.size());
    }

    public void testMeerdereOuder1ZelfdePersonen() {
        final Lo3Categorie<Lo3OuderInhoud> ouder1Actueel = VerplichteStapel.createOuder("1", Lo3CategorieEnum.CATEGORIE_02);
        final Lo3Categorie<Lo3OuderInhoud> ouder1Historie = VerplichteStapel.createOuder("1", HERKOMST_52_1, DATUM_19800101);

        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(ouder1Actueel);
        ouders.add(ouder1Historie);

        Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));

        assertAantalInfos(0);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB016, 0);
    }

}
