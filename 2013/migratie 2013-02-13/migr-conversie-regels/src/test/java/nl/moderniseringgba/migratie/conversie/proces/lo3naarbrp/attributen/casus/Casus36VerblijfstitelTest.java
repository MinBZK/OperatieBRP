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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.testutils.StapelUtils;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class Casus36VerblijfstitelTest extends CasusTest {

    private final List<Lo3Categorie<Lo3VerblijfstitelInhoud>> categorieen =
            new ArrayList<Lo3Categorie<Lo3VerblijfstitelInhoud>>();

    private final BrpVerblijfsrechtInhoud brpVerblijfsrecht21 = new BrpVerblijfsrechtInhoud(
            new BrpVerblijfsrechtCode("21"), new BrpDatum(20000101), new BrpDatum(20040101), null);
    private final BrpVerblijfsrechtInhoud brpVerblijfsrecht98 = new BrpVerblijfsrechtInhoud(
            new BrpVerblijfsrechtCode("98"), new BrpDatum(20040101), null, null);

    private final Lo3VerblijfstitelInhoud lo3Verblijfstitel21 = new Lo3VerblijfstitelInhoud(
            new Lo3AanduidingVerblijfstitelCode("21"), new Lo3Datum(20040101), new Lo3Datum(20000101));
    private final Lo3VerblijfstitelInhoud lo3Verblijfstitel98 = new Lo3VerblijfstitelInhoud(
            new Lo3AanduidingVerblijfstitelCode("98"), null, new Lo3Datum(20040101));

    private final BrpTestObject<BrpVerblijfsrechtInhoud> brp1 = new BrpTestObject<BrpVerblijfsrechtInhoud>();
    private final BrpTestObject<BrpVerblijfsrechtInhoud> brp2 = new BrpTestObject<BrpVerblijfsrechtInhoud>();

    {
        final Lo3TestObject<Lo3VerblijfstitelInhoud> lo1 = new Lo3TestObject<Lo3VerblijfstitelInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3VerblijfstitelInhoud> lo2 = new Lo3TestObject<Lo3VerblijfstitelInhoud>(Document.AKTE);

        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(20040102), "A2");
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(20000102), "A1");

        // LO3 input
        // @formatter:off
        // 98  xxxxxxxx    1-1-2004     1-1-2004    2-1-2004    <A2>
        // 21  1-1-2004    1-1-2000     1-1-2000    2-1-2000    <A1>

        lo2.vul(lo3Verblijfstitel98, null, 20040101, 20040102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_10, 0, 0));
        lo1.vul(lo3Verblijfstitel21, null, 20000101, 20000102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_10, 0, 1));

        // verwachte BRP output
        // 98  1-1-2004    xxxxxxxx    2-1-2004        A2
        // 21  1-1-2000    1-1-2004    2-1-2000        A1  A2

        brp1.vul(brpVerblijfsrecht98, 20040101, null,     20040102000000L, null, actie2, null, null);
        brp2.vul(brpVerblijfsrecht21, 20000101, 20040101, 20000102000000L, null, actie1, null, null);
        // @formatter:on

        vulCategorieen(lo1, lo2);
    }

    @Override
    protected Lo3Stapel<Lo3VerblijfstitelInhoud> maakVerblijfstitelStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3VerblijfstitelInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3VerblijfstitelInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<Lo3VerblijfstitelInhoud>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(),
                    lo.getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() throws Exception {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);
        final BrpStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel = brpPersoonslijst.getVerblijfsrechtStapel();
        assertEquals(2, verblijfsrechtStapel.size());

        System.out.println(verblijfsrechtStapel.get(0));
        System.out.println(verblijfsrechtStapel.get(1));

        // 21
        assertVerblijfsrecht(verblijfsrechtStapel.get(0), brp2);
        // 98
        assertVerblijfsrecht(verblijfsrechtStapel.get(1), brp1);
    }

    @Override
    @Test
    public void testRondverteer() throws Exception {
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = conversieService.converteerBrpPersoonslijst(brpPersoonslijst);
        final Lo3Stapel<Lo3VerblijfstitelInhoud> rondverteerdeStapel = terug.getVerblijfstitelStapel();
        final Lo3Stapel<Lo3VerblijfstitelInhoud> origineleStapel = getLo3Persoonslijst().getVerblijfstitelStapel();
        assertEquals(origineleStapel.size(), rondverteerdeStapel.size());
        assertEquals(origineleStapel, rondverteerdeStapel);
    }
}
