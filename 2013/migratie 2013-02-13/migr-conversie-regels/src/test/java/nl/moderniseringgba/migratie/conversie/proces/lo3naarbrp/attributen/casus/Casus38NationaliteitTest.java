/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.casus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.testutils.StapelUtils;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class Casus38NationaliteitTest extends CasusTest {

    private final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen =
            new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

    private final BrpNationaliteitInhoud brpDuits = new BrpNationaliteitInhoud(new BrpNationaliteitCode(
            Integer.valueOf("1234")), null, null);
    private final Lo3NationaliteitInhoud lo3Duits = new Lo3NationaliteitInhoud(new Lo3NationaliteitCode("1234"),
            null, null, null);
    private final Lo3NationaliteitInhoud lo3Leeg = new Lo3NationaliteitInhoud(null, null, null, null);
    private final BrpTestObject<BrpNationaliteitInhoud> brp1 = new BrpTestObject<BrpNationaliteitInhoud>();
    private final BrpTestObject<BrpNationaliteitInhoud> brp2 = new BrpTestObject<BrpNationaliteitInhoud>();
    private final BrpTestObject<BrpNationaliteitInhoud> brp3 = new BrpTestObject<BrpNationaliteitInhoud>();

    {
        final Lo3TestObject<Lo3NationaliteitInhoud> lo1 = new Lo3TestObject<Lo3NationaliteitInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo2 = new Lo3TestObject<Lo3NationaliteitInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo3 = new Lo3TestObject<Lo3NationaliteitInhoud>(Document.AKTE);

        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(20040103), "A3");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(20040102), "A2");
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(20000102), "A1");

        // LO3 input
        // @formatter:off

        // Duits               1-1-2004    3-1-2004    A3
        // <leeg>          O   1-1-2004    2-1-2004    A2
        // Duits               1-1-2000    2-1-2000    A1

        lo3.vul(lo3Duits, null,    20040101, 20040103, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0));
        lo2.vul(lo3Leeg,  ONJUIST, 20040101, 20040102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 1));
        lo1.vul(lo3Duits, null,    20000101, 20000102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 2));

        // verwachte BRP output
        // Duits   1-1-2004    xxxxxxxx    3-1-2004    xxxxxxxx    A3
        // Duits   1-1-2000    1-1-2004    2-1-2000    xxxxxxxx    A1
        // Duits   1-1-2000    1-1-2004    2-1-2000    2-1-2000    A1  A2  A1

        brp1.vul(brpDuits, 20040101, null,     20040103000000L, null,            actie3, null,   null);
        brp2.vul(brpDuits, 20000101, 20040101, 20000102000000L, null,            actie1, null,   null);
        brp3.vul(brpDuits, 20000101, 20040101, 20000102000100L, 20000102000100L, actie1, actie2, actie1);
        // @formatter:on

        vulCategorieen(lo1, lo2, lo3);
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

        // aanvang 2000
        assertNationaliteit(nationaliteitStapel.get(0), brp2);
        // aanvang 2000, vervallen
        assertNationaliteit(nationaliteitStapel.get(1), brp3);
        // aanvang 2004
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
        assertEquals(origineleStapels, rondverteerdeStapels);
    }
}
