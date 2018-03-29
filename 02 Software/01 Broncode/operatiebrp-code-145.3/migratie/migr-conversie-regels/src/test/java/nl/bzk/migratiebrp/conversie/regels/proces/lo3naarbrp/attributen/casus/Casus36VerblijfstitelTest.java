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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;
import org.junit.Test;

public class Casus36VerblijfstitelTest extends AbstractCasusTest {

    private final List<Lo3Categorie<Lo3VerblijfstitelInhoud>> categorieen = new ArrayList<>();

    private final BrpVerblijfsrechtInhoud brpVerblijfstitel21 =
            new BrpVerblijfsrechtInhoud(new BrpVerblijfsrechtCode("21"), new BrpDatum(20000101, null), new BrpDatum(20040101, null), null);
    private final BrpVerblijfsrechtInhoud brpVerblijfstitel98 =
            new BrpVerblijfsrechtInhoud(new BrpVerblijfsrechtCode("98"), new BrpDatum(20040101, null), null, null);

    private final Lo3VerblijfstitelInhoud lo3Verblijfstitel21 =
            new Lo3VerblijfstitelInhoud(new Lo3AanduidingVerblijfstitelCode("21"), new Lo3Datum(20040101), new Lo3Datum(20000101));
    private final Lo3VerblijfstitelInhoud lo3Verblijfstitel98 =
            new Lo3VerblijfstitelInhoud(new Lo3AanduidingVerblijfstitelCode("98"), null, new Lo3Datum(20040101));

    private final BrpTestObject<BrpVerblijfsrechtInhoud> brp1 = new BrpTestObject<>();
    private final BrpTestObject<BrpVerblijfsrechtInhoud> brp2 = new BrpTestObject<>();

    {
        final Lo3TestObject<Lo3VerblijfstitelInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3VerblijfstitelInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);

        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(20040102, null), "2A");
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(20000102, null), "1A");

        // LO3 input
        // @formatter:off
        // 98 xxxxxxxx 1-1-2004 1-1-2004 2-1-2004 <A2>
        // 21 1-1-2004 1-1-2000 1-1-2000 2-1-2000 <A1>

        lo2.vul(lo3Verblijfstitel98, null, 20040101, 20040102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_10, 0, 0));
        lo1.vul(lo3Verblijfstitel21, null, 20000101, 20000102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_60, 0, 1));

        // verwachte BRP output
        // 98 1-1-2004 xxxxxxxx 2-1-2004 A2
        // 21 1-1-2000 1-1-2004 2-1-2000 A1 A2

        brp1.vul(brpVerblijfstitel21, 20000101, 20040101, 20000102010000L, null, null, actie1, actie2, null);
        brp2.vul(brpVerblijfstitel98, 20040101, null, 20040102010000L, null, null, actie2, null, null);
        // @formatter:on

        vulCategorieen(lo1, lo2);
    }

    @Override
    protected Lo3Stapel<Lo3VerblijfstitelInhoud> maakVerblijfstitelStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3VerblijfstitelInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3VerblijfstitelInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo.getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);
        final BrpStapel<BrpVerblijfsrechtInhoud> verblijfstitelStapel = brpPersoonslijst.getVerblijfsrechtStapel();
        assertEquals(1, verblijfstitelStapel.size());
    }

    @Override
    @Test
    public void testRondverteer() {
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);
        final Lo3Stapel<Lo3VerblijfstitelInhoud> rondverteerdeStapel = terug.getVerblijfstitelStapel();
        final Lo3Stapel<Lo3VerblijfstitelInhoud> origineleStapel = getLo3Persoonslijst().getVerblijfstitelStapel();
        assertEquals(1, rondverteerdeStapel.size());
    }
}
