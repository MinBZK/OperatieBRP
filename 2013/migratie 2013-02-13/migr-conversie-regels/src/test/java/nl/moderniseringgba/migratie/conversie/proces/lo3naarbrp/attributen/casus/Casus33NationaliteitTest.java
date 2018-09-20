/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.casus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.testutils.StapelUtils;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class Casus33NationaliteitTest extends CasusTest {

    private final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen =
            new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

    private final BrpNationaliteitInhoud brpNl002034 = new BrpNationaliteitInhoud(new BrpNationaliteitCode(
            Integer.valueOf("1234")), new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("002")),
            new BrpRedenVerliesNederlandschapCode(new BigDecimal("034")));
    private final BrpNationaliteitInhoud brpNl002035 = new BrpNationaliteitInhoud(new BrpNationaliteitCode(
            Integer.valueOf("1234")), new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("002")),
            new BrpRedenVerliesNederlandschapCode(new BigDecimal("035")));
    private final BrpNationaliteitInhoud brpNl001 = new BrpNationaliteitInhoud(new BrpNationaliteitCode(
            Integer.valueOf("1234")), new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("001")), null);
    private final Lo3NationaliteitInhoud lo3Nl002 = new Lo3NationaliteitInhoud(new Lo3NationaliteitCode("1234"),
            new Lo3RedenNederlandschapCode("002"), null, null);
    private final Lo3NationaliteitInhoud lo3Nl001 = new Lo3NationaliteitInhoud(new Lo3NationaliteitCode("1234"),
            new Lo3RedenNederlandschapCode("001"), null, null);
    private final Lo3NationaliteitInhoud lo3Verlies035 = new Lo3NationaliteitInhoud(null, null,
            new Lo3RedenNederlandschapCode("035"), null);
    private final Lo3NationaliteitInhoud lo3Verlies034 = new Lo3NationaliteitInhoud(null, null,
            new Lo3RedenNederlandschapCode("034"), null);
    private final BrpTestObject<BrpNationaliteitInhoud> brp1 = new BrpTestObject<BrpNationaliteitInhoud>();
    private final BrpTestObject<BrpNationaliteitInhoud> brp2 = new BrpTestObject<BrpNationaliteitInhoud>();
    private final BrpTestObject<BrpNationaliteitInhoud> brp3 = new BrpTestObject<BrpNationaliteitInhoud>();

    {
        final Lo3TestObject<Lo3NationaliteitInhoud> lo1 = new Lo3TestObject<Lo3NationaliteitInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo2 = new Lo3TestObject<Lo3NationaliteitInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo3 = new Lo3TestObject<Lo3NationaliteitInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo4 = new Lo3TestObject<Lo3NationaliteitInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo5 = new Lo3TestObject<Lo3NationaliteitInhoud>(Document.AKTE);

        final BrpActie actie5 = buildBrpActie(BrpDatumTijd.fromDatum(20050102), "A5");
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(20000102), "A3");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19950102), "A2");
        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(20040102), "A4");
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102), "A1");

        // @formatter:off
        // LO3 input
        // <leeg>      xxx  035       1-1-2005    2-1-2005    <A5>
        // Nederlands  002  xxx       1-1-2000    2-1-2000    <A3>
        // <leeg>      xxx  034       1-1-1995    2-1-1995    <A2>
        // Nederlands  002  xxx       1-1-1990    2-1-2004    <A4>
        // Nederlands  001  xxx   O   1-1-1990    2-1-1990    <A1>

        lo5.vul(lo3Verlies035, null,    20050101, 20050102, 5, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0));
        lo4.vul(lo3Nl002,      null,    20000101, 20000102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 1));
        lo3.vul(lo3Verlies034, null,    19950101, 19950102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 2));
        lo2.vul(lo3Nl002,      null,    19900101, 20040102, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 3));
        lo1.vul(lo3Nl001,      ONJUIST, 19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 4));


        // verwachte BRP output
        // NL  002 034   1-1-1990    1-1-1995    2-1-2004                A4  A2
        // NL  002 035   1-1-2000    1-1-2005    2-1-2000                A3  A5
        // NL  001 ---   1-1-1990    xxxxxxxx    2-1-1990    2-1-1990    A1  --   A1

        brp1.vul(brpNl002034, 19900101, 19950101, 20040102000000L, null,            actie4, actie2, null);
        brp2.vul(brpNl002035, 20000101, 20050101, 20000102000000L, null,            actie3, actie5, null);
        brp3.vul(brpNl001,    19900101, null,     19900102000000L, 19900102000000L, actie1, null,   actie1);
        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4, lo5);
    }

    @Override
    protected Lo3Stapel<Lo3NationaliteitInhoud> maakNationaliteitStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3NationaliteitInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3NationaliteitInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<Lo3NationaliteitInhoud>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(),
                    lo.getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() throws Exception {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);
        final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels =
                brpPersoonslijst.getNationaliteitStapels();
        assertEquals(1, nationaliteitStapels.size());
        final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel = nationaliteitStapels.get(0);
        sorteerBrpStapel(nationaliteitStapel);
        assertEquals(3, nationaliteitStapel.size());

        System.out.println(nationaliteitStapel.get(0));
        System.out.println(nationaliteitStapel.get(1));
        System.out.println(nationaliteitStapel.get(2));

        // 001, null
        assertNationaliteit(nationaliteitStapel.get(0), brp3);
        // 002, 035
        assertNationaliteit(nationaliteitStapel.get(1), brp2);
        // 002, 034
        assertNationaliteit(nationaliteitStapel.get(2), brp1);
    }

    @Override
    @Test
    public void testRondverteer() throws Exception {
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = conversieService.converteerBrpPersoonslijst(brpPersoonslijst);
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> rondverteerdeStapels = terug.getNationaliteitStapels();
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> origineleStapels =
                getLo3Persoonslijst().getNationaliteitStapels();

        assertEquals(1, rondverteerdeStapels.size());
        assertEquals(origineleStapels.get(0).size(), rondverteerdeStapels.get(0).size());
        Lo3StapelHelper.vergelijk(origineleStapels, rondverteerdeStapels);
    }

}
