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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.testutils.StapelUtils;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class Casus28NaamEnGeboorteTest extends CasusTest {
    private final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

    private final BrpSamengesteldeNaamInhoud brpInhoudPietJansen = new BrpSamengesteldeNaamInhoud(null, "Piet", null,
            null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudKlaasJansen = new BrpSamengesteldeNaamInhoud(null, "Klaas",
            null, null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudJanJansen = new BrpSamengesteldeNaamInhoud(null, "Jan", null,
            null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudHenkJansen = new BrpSamengesteldeNaamInhoud(null, "Henk", null,
            null, null, "Jansen", false, true);

    private final Lo3PersoonInhoud lo3PietJansen = buildPersoonMetNaamEnGeboorte("Piet", "Jansen", 20000101, RDAM);
    private final Lo3PersoonInhoud lo3KlaasJansen = buildPersoonMetNaamEnGeboorte("Klaas", "Jansen", 20000101, RDAM);
    private final Lo3PersoonInhoud lo3HenkJansen = buildPersoonMetNaamEnGeboorte("Henk", "Jansen", 20000101, RDAM);
    private final Lo3PersoonInhoud lo3JanJansen = buildPersoonMetNaamEnGeboorte("Jan", "Jansen", 20000101, RDAM);

    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpJanJansen =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpKlaasJansen =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpPietJansen =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpHenkJansen =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();

    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte1 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte2 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte3 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte4 = new BrpTestObject<BrpGeboorteInhoud>();

    {
        final Lo3TestObject<Lo3PersoonInhoud> lo4 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo3 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo2 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo1 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);

        final BrpActie actieA4 = buildBrpActie(BrpDatumTijd.fromDatum(20000102), "A4", Document.AKTE);
        final BrpActie actieA3 = buildBrpActie(BrpDatumTijd.fromDatum(20010102), "A3", Document.AKTE);
        final BrpActie actieA2 = buildBrpActie(BrpDatumTijd.fromDatum(19950102), "A2", Document.AKTE);
        final BrpActie actieA1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102), "A1", Document.AKTE);

        // @formatter:off
        // LO3 input

        // Klaas   Rotterdam       1-1-2000    2-1-2000    A4
        // Henk    Rotterdam       1-1-1995    2-1-2001    A3
        // Piet    Rotterdam   O   1-1-1995    2-1-1995    A2
        // Jan     Rotterdam       1-1-1990    2-1-1990    A1


        lo4.vul(lo3KlaasJansen, null,    20000101, 20000102, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 3));
        lo3.vul(lo3HenkJansen,  null,    19950101, 20010102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 2));
        lo2.vul(lo3PietJansen,  ONJUIST, 19950101, 19950102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 1));
        lo1.vul(lo3JanJansen,   null,    19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));

        // verwachte BRP output
        // Jan         1-1-1990    1-1-1995    2-1-1990    Xxxxxxxx    A1  X   X
        // Piet        1-1-1995    xxxxxxxx    2-1-1995    2-1-1995    A2  X   A2
        // Klaas       1-1-2000    xxxxxxxx    2-1-2000    Xxxxxxxx    A4  X   X
        // Henk        1-1-1995    1-1-2000    2-1-2001    xxxxxxxx    A3  X   X

  brpJanJansen.  vul(brpInhoudJanJansen,   19900101, 19950101, 19900102000000L, null,            actieA1, null, null);
  brpPietJansen. vul(brpInhoudPietJansen,  19950101, null,     19950102000000L, 19950102000000L, actieA2, null, actieA2);
  brpKlaasJansen.vul(brpInhoudKlaasJansen, 20000101, null,     20000102000000L, null,            actieA4, null, null);
  brpHenkJansen. vul(brpInhoudHenkJansen,  19950101, 20000101, 20010102000000L, null,            actieA3, null, null);

        // rotterdam   2-1-1990    2-1-2000    A1
        // Rotterdam   2-1-1995    2-1-1995    A2  A2
        // Rotterdam   2-1-2000    2-1-2001    A3
        // Rotterdam   2-1-2001    Xxxxxxxx    A4


        final BrpGeboorteInhoud brpGeboorteInhoudRdam = buildBrpGeboorteInhoud(20000101, RDAM);

  brpGeboorte1.vul(brpGeboorteInhoudRdam, null, null, 19900102000000L, 20000102000000L, actieA1, null, null);
  brpGeboorte2.vul(brpGeboorteInhoudRdam, null, null, 19950102000000L, 19950102000000L, actieA2, null, actieA2);
  brpGeboorte3.vul(brpGeboorteInhoudRdam, null, null, 20000102000000L, 20010102000000L, actieA4, null, null);
  brpGeboorte4.vul(brpGeboorteInhoudRdam, null, null, 20010102000000L, null,            actieA3, null, null);

        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4);
    }

    @Override
    protected Lo3Stapel<Lo3PersoonInhoud> maakPersoonStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3PersoonInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3PersoonInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<Lo3PersoonInhoud>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo
                    .getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() throws Exception {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);

        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel =
                brpPersoonslijst.getSamengesteldeNaamStapel();
        assertEquals(4, samengesteldeNaamStapel.size());

        System.out.println(samengesteldeNaamStapel.get(0));
        System.out.println(samengesteldeNaamStapel.get(1));
        System.out.println(samengesteldeNaamStapel.get(2));
        System.out.println(samengesteldeNaamStapel.get(3));

        // piet
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(0), brpPietJansen);
        // jan
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(1), brpJanJansen);
        // klaas
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(2), brpKlaasJansen);
        // henk
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(3), brpHenkJansen);

        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = brpPersoonslijst.getGeboorteStapel();
        assertEquals(4, geboorteStapel.size());

        System.out.println(geboorteStapel.get(0));
        System.out.println(geboorteStapel.get(1));
        System.out.println(geboorteStapel.get(2));
        System.out.println(geboorteStapel.get(3));

        // 1990
        assertGeboorte(geboorteStapel.get(0), brpGeboorte1);
        // 1995
        assertGeboorte(geboorteStapel.get(1), brpGeboorte2);
        // 2000
        assertGeboorte(geboorteStapel.get(2), brpGeboorte3);
        // 2001
        assertGeboorte(geboorteStapel.get(3), brpGeboorte4);
    }

    @Override
    @Test
    public void testRondverteer() throws Exception {
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = conversieService.converteerBrpPersoonslijst(brpPersoonslijst);
        final Lo3Stapel<Lo3PersoonInhoud> rondverteerdeStapel = terug.getPersoonStapel();
        final Lo3Stapel<Lo3PersoonInhoud> origineleStapel = getLo3Persoonslijst().getPersoonStapel();
        assertEquals(origineleStapel.size(), rondverteerdeStapel.size());
        Lo3StapelHelper.vergelijk(origineleStapel, rondverteerdeStapel);
    }

}
