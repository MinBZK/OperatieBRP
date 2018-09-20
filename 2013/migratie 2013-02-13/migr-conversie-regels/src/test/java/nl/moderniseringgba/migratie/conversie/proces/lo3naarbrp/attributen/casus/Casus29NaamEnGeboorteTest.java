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
public class Casus29NaamEnGeboorteTest extends CasusTest {

    private final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

    private final BrpSamengesteldeNaamInhoud brpInhoudPietJansen = new BrpSamengesteldeNaamInhoud(null, "Piet", null,
            null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudKlaasJansen = new BrpSamengesteldeNaamInhoud(null, "Klaas",
            null, null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudJanJansen = new BrpSamengesteldeNaamInhoud(null, "Jan", null,
            null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudHenkJansen = new BrpSamengesteldeNaamInhoud(null, "Henk", null,
            null, null, "Jansen", false, true);

    private final Lo3PersoonInhoud lo3PietRdam = buildPersoonMetNaamEnGeboorte("Piet", "Jansen", 20000101, RDAM);
    private final Lo3PersoonInhoud lo3KlaasAdam = buildPersoonMetNaamEnGeboorte("Klaas", "Jansen", 20000101, ADAM);
    private final Lo3PersoonInhoud lo3KlaasRdam = buildPersoonMetNaamEnGeboorte("Klaas", "Jansen", 20000101, RDAM);
    private final Lo3PersoonInhoud lo3HenkAdam = buildPersoonMetNaamEnGeboorte("Henk", "Jansen", 20000101, ADAM);
    private final Lo3PersoonInhoud lo3JanAdam = buildPersoonMetNaamEnGeboorte("Jan", "Jansen", 20000101, ADAM);
    private final Lo3PersoonInhoud lo3JanRdam = buildPersoonMetNaamEnGeboorte("Jan", "Jansen", 20000101, RDAM);

    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpJanAdam =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpJanRdam =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpKlaasAdam =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpKlaasRdam =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpPietRdam =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpHenkAdam =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();

    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte1 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte2 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte3 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte4 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte5 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte6 = new BrpTestObject<BrpGeboorteInhoud>();

    {
        final Lo3TestObject<Lo3PersoonInhoud> lo6 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo5 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo4 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo3 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo2 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo1 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);

        final BrpActie actieA6 = buildBrpActie(BrpDatumTijd.fromDatum(20000602), "A6", Document.AKTE);
        final BrpActie actieA5 = buildBrpActie(BrpDatumTijd.fromDatum(20000102), "A5", Document.AKTE);
        final BrpActie actieA4 = buildBrpActie(BrpDatumTijd.fromDatum(20010102), "A4", Document.AKTE);
        final BrpActie actieA3 = buildBrpActie(BrpDatumTijd.fromDatum(19950102), "A3", Document.AKTE);
        final BrpActie actieA2 = buildBrpActie(BrpDatumTijd.fromDatum(20000602), "A2", Document.AKTE);
        final BrpActie actieA1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102), "A1", Document.AKTE);

        // @formatter:off
        // LO3 input

        // Klaas   Amsterdam       1-1-2000    2-6-2000 A3
        // Klaas   Rotterdam   O   1-1-2000    2-1-2000 A2
        // Henk    Amsterdam       1-1-1995    2-1-2001 A5
        // Piet    Rotterdam   O   1-1-1995    2-1-1995 A4
        // Jan     Amsterdam       1-1-1990    2-6-2000 A6
        // Jan     Rotterdam   O   1-1-1990    2-1-1990 A1

        lo6.vul(lo3KlaasAdam,   null,    20000101, 20000602, 6, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 5));
        lo5.vul(lo3KlaasRdam,   ONJUIST, 20000101, 20000102, 5, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 4));
        lo4.vul(lo3HenkAdam,    null,    19950101, 20010102, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 3));
        lo3.vul(lo3PietRdam,    ONJUIST, 19950101, 19950102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 2));
        lo2.vul(lo3JanAdam,     null,    19900101, 20000602, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 1));
        lo1.vul(lo3JanRdam,     ONJUIST, 19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));

        // verwachte BRP output
        // Henk     1-1-1995    1-1-2000    2-1-2001    xxxxxxxx    A4
        // Klaas    1-1-2000    xxxxxxxx    2-6-2000    xxxxxxxx    A6
        // Jan      1-1-1990    1-1-1995    2-6-2000    xxxxxxxx    A2
        // Klaas    1-1-2000    xxxxxxxx    2-1-2000    2-1-2000    A5      A5
        // Piet     1-1-1995    xxxxxxxx    2-1-1995    2-1-1995    A3      A3
        // Jan      1-1-1990    xxxxxxxx    2-1-1990    2-1-1990    A1      A1

  brpHenkAdam. vul(brpInhoudHenkJansen,  19950101, 20000101, 20010102000000L, null,            actieA4, null, null);
  brpKlaasRdam.vul(brpInhoudKlaasJansen, 20000101, null,     20000602000000L, null,            actieA6, null, null);
  brpJanAdam.  vul(brpInhoudJanJansen,   19900101, 19950101, 20000602000000L, null,            actieA2, null, null);
  brpKlaasAdam.vul(brpInhoudKlaasJansen, 20000101, null,     20000102000000L, 20000102000000L, actieA5, null, actieA5);
  brpPietRdam. vul(brpInhoudPietJansen,  19950101, null,     19950102000000L, 19950102000000L, actieA3, null, actieA3);
  brpJanRdam.  vul(brpInhoudJanJansen,   19900101, null,     19900102000000L, 19900102000000L, actieA1, null, actieA1);

        // amsterdam   2-1-2001    xxxxxxxx    A4
        // amsterdam   2-6-2000    2-1-2001    A6
        // amsterdam   2-6-2000    2-6-2000    A2
        // rotterdam   2-1-2000    2-1-2000    A5  A5
        // rotterdam   2-1-1995    2-1-1995    A3  A3
        // rotterdam   2-1-1990    2-1-1990    A1  A1



        final BrpGeboorteInhoud brpGeboorteInhoudRdam = buildBrpGeboorteInhoud(20000101, RDAM);
        final BrpGeboorteInhoud brpGeboorteInhoudAdam = buildBrpGeboorteInhoud(20000101, ADAM);

  brpGeboorte1.vul(brpGeboorteInhoudAdam, null, null, 20010102000000L, null,            actieA4, null, null);
  brpGeboorte2.vul(brpGeboorteInhoudAdam, null, null, 20000602000100L, 20010102000000L, actieA6, null, null);
  brpGeboorte3.vul(brpGeboorteInhoudAdam, null, null, 20000602000000L, 20000602000100L, actieA2, null, null);
  brpGeboorte4.vul(brpGeboorteInhoudRdam, null, null, 20000102000000L, 20000102000000L, actieA5, null, actieA5);
  brpGeboorte5.vul(brpGeboorteInhoudRdam, null, null, 19950102000000L, 19950102000000L, actieA3, null, actieA3);
  brpGeboorte6.vul(brpGeboorteInhoudRdam, null, null, 19900102000000L, 19900102000000L, actieA1, null, actieA1);

        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4, lo5, lo6);
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
        assertEquals(6, samengesteldeNaamStapel.size());

        System.out.println(samengesteldeNaamStapel.get(0));
        System.out.println(samengesteldeNaamStapel.get(1));
        System.out.println(samengesteldeNaamStapel.get(2));
        System.out.println(samengesteldeNaamStapel.get(3));
        System.out.println(samengesteldeNaamStapel.get(4));
        System.out.println(samengesteldeNaamStapel.get(5));

        // jan vervallen
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(0), brpJanRdam);
        // piet vervallen
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(1), brpPietRdam);
        // klaas vervallen
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(2), brpKlaasAdam);
        // jan
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(3), brpJanAdam);
        // klaas
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(4), brpKlaasRdam);
        // henk
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(5), brpHenkAdam);

        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = brpPersoonslijst.getGeboorteStapel();
        assertEquals(6, geboorteStapel.size());

        System.out.println(geboorteStapel.get(0));
        System.out.println(geboorteStapel.get(1));
        System.out.println(geboorteStapel.get(2));
        System.out.println(geboorteStapel.get(3));
        System.out.println(geboorteStapel.get(4));
        System.out.println(geboorteStapel.get(5));

        // 1990
        assertGeboorte(geboorteStapel.get(0), brpGeboorte6);
        // 1995
        assertGeboorte(geboorteStapel.get(1), brpGeboorte5);
        // 2000, 2000-01-02
        assertGeboorte(geboorteStapel.get(2), brpGeboorte4);
        // 2000, 2000-06-02
        assertGeboorte(geboorteStapel.get(3), brpGeboorte3);
        // 2000, 2001-01-02
        assertGeboorte(geboorteStapel.get(4), brpGeboorte2);
        // 2001
        assertGeboorte(geboorteStapel.get(5), brpGeboorte1);
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
