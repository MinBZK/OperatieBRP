/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.casus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;

import org.junit.Test;

public class Casus37NationaliteitTest extends AbstractCasusTest {

    private static final String NAT_CODE = "0001";

    private final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

    private final BrpNationaliteitInhoud brpNederlands001 =
            new BrpNationaliteitInhoud(
                    new BrpNationaliteitCode(NAT_CODE),
                    new BrpRedenVerkrijgingNederlandschapCode("001"),
                    null,
                    null,
                    null,
                    null,
                    null);
    private final BrpNationaliteitInhoud brpNederlands002 =
            new BrpNationaliteitInhoud(
                    new BrpNationaliteitCode(NAT_CODE),
                    new BrpRedenVerkrijgingNederlandschapCode("002"),
                    new BrpRedenVerliesNederlandschapCode("033"),
                    null,
                    null,
                    null,
                    null);
    private final BrpNationaliteitInhoud brpNederlands003 =
            new BrpNationaliteitInhoud(
                    new BrpNationaliteitCode(NAT_CODE),
                    new BrpRedenVerkrijgingNederlandschapCode("003"),
                    new BrpRedenVerliesNederlandschapCode("034"),
                    null,
                    null,
                    null,
                    null);
    private final BrpNationaliteitInhoud brpNederlands004 =
            new BrpNationaliteitInhoud(
                    new BrpNationaliteitCode(NAT_CODE),
                    new BrpRedenVerkrijgingNederlandschapCode("004"),
                    null,
                    null,
                    null,
                    null,
                    null);

    private final Lo3NationaliteitInhoud lo3Nederlands001 =
            new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), new Lo3RedenNederlandschapCode("001"), null, null, null);
    private final Lo3NationaliteitInhoud lo3Nederlands002 =
            new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), new Lo3RedenNederlandschapCode("002"), null, null, null);
    private final Lo3NationaliteitInhoud lo3Nederlands003 =
            new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), new Lo3RedenNederlandschapCode("003"), null, null, null);
    private final Lo3NationaliteitInhoud lo3Nederlands004 =
            new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), new Lo3RedenNederlandschapCode("004"), null, null, null);

    private final Lo3NationaliteitInhoud lo3Leeg033 = new Lo3NationaliteitInhoud(null, null, new Lo3RedenNederlandschapCode("033"), null, null);
    private final Lo3NationaliteitInhoud lo3Leeg034 = new Lo3NationaliteitInhoud(null, null, new Lo3RedenNederlandschapCode("034"), null, null);
    private final BrpTestObject<BrpNationaliteitInhoud> brp1 = new BrpTestObject<>();
    private final BrpTestObject<BrpNationaliteitInhoud> brp2 = new BrpTestObject<>();
    private final BrpTestObject<BrpNationaliteitInhoud> brp3 = new BrpTestObject<>();
    private final BrpTestObject<BrpNationaliteitInhoud> brp4 = new BrpTestObject<>();

    {
        final Lo3TestObject<Lo3NationaliteitInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo3 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo4 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo5 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo6 = new Lo3TestObject<>(Document.AKTE);

        final BrpActie actie6 = buildBrpActie(BrpDatumTijd.fromDatum(20080102, null), "6A");
        final BrpActie actie5 = buildBrpActie(BrpDatumTijd.fromDatum(20070102, null), "5A");
        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(20060110, null), "4A");
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(20060102, null), "3A", BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST);
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(20040110, null), "2A");
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(20040102, null), "1A");

        // LO3 input
        // @formatter:off

        // NL 004 1-1-2005 2-1-2008 A6
        // NL 003 1-1-1990 2-1-2007 A5
        // <leeg> 034 1-1-2000 10-1-2006 A4
        // <leeg> 033 O 1-1-2006 2-1-2006 A3
        // NL 002 O 1-1-1992 10-1-2004 A2
        // NL 001 O 1-1-2004 2-1-2004 A1

        lo6.vul(lo3Nederlands004, null, 20050101, 20080102, 6, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0));
        lo5.vul(lo3Nederlands003, null, 19900101, 20070102, 5, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1));
        lo4.vul(lo3Leeg034, null, 20000101, 20060110, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 2));
        lo3.vul(lo3Leeg033, ONJUIST, 20060101, 20060102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 3));
        lo2.vul(lo3Nederlands002, ONJUIST, 19920101, 20040110, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 4));
        lo1.vul(lo3Nederlands001, ONJUIST, 20040101, 20040102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 5));

        // verwachte BRP output
        // NL 004 1-1-2005 xxxxxxxx 1-1-2005 xxxxxxxx A6
        // NL 003 034 1-1-1990 1-1-2000 2-1-2007 xxxxxxxx A5 A4
        // NL 002 033 1-1-1992 1-1-2006 10-1-2004 10-1-2004 A2 A3 A2
        // NL 001 1-1-2004 xxxxxxxx 2-1-2004 2-1-2004 A1 A1

        brp1.vul(brpNederlands001, 20040101, null, 20040102010000L, 20040102010000L, 'O', actie1, null, actie1);
        brp2.vul(brpNederlands002, 19920101, 20060101, 20040110010000L, 20040110010000L, 'O', actie2, actie3, actie2);
        brp3.vul(brpNederlands003, 19900101, 20000101, 20070102010000L, null, null, actie5, actie4, null);
        brp4.vul(brpNederlands004, 20050101, null, 20080102010000L, null, null, actie6, null, null);
        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4, lo5, lo6);
    }

    @Override
    protected Lo3Stapel<Lo3NationaliteitInhoud> maakNationaliteitStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3NationaliteitInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3NationaliteitInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo.getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() {
        // LO3 naar BRP
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);
        final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels = brpPersoonslijst.getNationaliteitStapels();
        assertEquals(1, nationaliteitStapels.size());
        final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel = nationaliteitStapels.get(0);
        sorteerBrpStapel(nationaliteitStapel);
        assertEquals(4, nationaliteitStapel.size());

        assertNationaliteit(nationaliteitStapel.get(0), brp1);
        assertNationaliteit(nationaliteitStapel.get(1), brp2);
        assertNationaliteit(nationaliteitStapel.get(2), brp3);
        assertNationaliteit(nationaliteitStapel.get(3), brp4);

    }

    @Override
    @Test
    public void testRondverteer() {
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> rondverteerdeStapels = terug.getNationaliteitStapels();
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> origineleStapels = getLo3Persoonslijst().getNationaliteitStapels();
        assertEquals(1, rondverteerdeStapels.size());
        assertEquals(origineleStapels.get(0).size(), rondverteerdeStapels.get(0).size());
        Lo3StapelHelper.vergelijk(origineleStapels, rondverteerdeStapels);
    }
}
