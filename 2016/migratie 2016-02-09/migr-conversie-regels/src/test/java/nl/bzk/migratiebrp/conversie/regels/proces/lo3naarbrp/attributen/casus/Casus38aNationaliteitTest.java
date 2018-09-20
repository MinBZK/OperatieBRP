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
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;

import org.junit.Test;

public class Casus38aNationaliteitTest extends AbstractCasusTest {

    private static final String NAT_CODE = "0001";

    private final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

    private final BrpNationaliteitInhoud brpNederlands001 =
            new BrpNationaliteitInhoud(
                new BrpNationaliteitCode(Short.parseShort(NAT_CODE)),
                new BrpRedenVerkrijgingNederlandschapCode(Short.parseShort("001")),
                null,
                null,
                null,
                null,
                null);
    private final BrpNationaliteitInhoud brpNederlands001033 =
            new BrpNationaliteitInhoud(
                new BrpNationaliteitCode(Short.parseShort(NAT_CODE)),
                new BrpRedenVerkrijgingNederlandschapCode(Short.parseShort("001")),
                new BrpRedenVerliesNederlandschapCode(Short.parseShort("033")),
                null,
                null,
                null,
                null);
    private final BrpNationaliteitInhoud brpNederlands002 =
            new BrpNationaliteitInhoud(
                new BrpNationaliteitCode(Short.parseShort(NAT_CODE)),
                new BrpRedenVerkrijgingNederlandschapCode(Short.parseShort("002")),
                null,
                null,
                null,
                null,
                null);
    private final Lo3NationaliteitInhoud lo3Nederlands001 =
            new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), new Lo3RedenNederlandschapCode("001"), null, null);
    private final Lo3NationaliteitInhoud lo3Nederlands002 =
            new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), new Lo3RedenNederlandschapCode("002"), null, null);
    private final Lo3NationaliteitInhoud lo3Leeg = new Lo3NationaliteitInhoud(null, null, new Lo3RedenNederlandschapCode("033"), null);
    private final BrpTestObject<BrpNationaliteitInhoud> brp1 = new BrpTestObject<>();
    private final BrpTestObject<BrpNationaliteitInhoud> brp2 = new BrpTestObject<>();
    private final BrpTestObject<BrpNationaliteitInhoud> brp3 = new BrpTestObject<>();

    {
        final Lo3TestObject<Lo3NationaliteitInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo3 = new Lo3TestObject<>(Document.AKTE);

        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(20040103, null), "3A");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(20040102, null), "2A", BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST);
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(20000102, null), "1A");

        // LO3 input
        // @formatter:off

        // NL 002 1-1-2004 3-1-2004 A3
        // <leeg> 033 O 1-1-2004 2-1-2004 A2
        // NL 001 1-1-2000 2-1-2000 A1

        lo3.vul(lo3Nederlands002, null, 20040101, 20040103, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0));
        lo2.vul(lo3Leeg, ONJUIST, 20040101, 20040102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1));
        lo1.vul(lo3Nederlands001, null, 20000101, 20000102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 2));

        // verwachte BRP output
        // NL 002 1-1-2004 xxxxxxxx 3-1-2004 xxxxxxxx A3
        // NL 001 1-1-2000 1-1-2004 2-1-2000 xxxxxxxx A1
        // NL 001 033 1-1-2000 1-1-2004 2-1-2000 2-1-2000 A1 A2 A1

        brp1.vul(brpNederlands001, 20000101, 20040101, 20000102010000L, null, null, actie1, actie3, null);
        brp2.vul(brpNederlands001033, 20000101, 20040101, 20000102010100L, 20000102010100L, 'O', actie1, actie2, actie1);
        brp3.vul(brpNederlands002, 20040101, null, 20040103010000L, null, null, actie3, null, null);
        // @formatter:on

        vulCategorieen(lo1, lo2, lo3);
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
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);
        final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels = brpPersoonslijst.getNationaliteitStapels();
        assertEquals(1, nationaliteitStapels.size());
        final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel = nationaliteitStapels.get(0);
        sorteerBrpStapel(nationaliteitStapel);
        assertEquals(3, nationaliteitStapel.size());

        assertNationaliteit(nationaliteitStapel.get(0), brp1);
        assertNationaliteit(nationaliteitStapel.get(1), brp2);
        assertNationaliteit(nationaliteitStapel.get(2), brp3);
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
        assertEquals(origineleStapels, rondverteerdeStapels);
    }
}
