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
public class Casus30NaamEnGeboorteTest extends CasusTest {

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
    private final Lo3PersoonInhoud lo3PietAdam = buildPersoonMetNaamEnGeboorte("Piet", "Jansen", 20000101, ADAM);
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
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpPietAdam =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpHenkAdam =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();

    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte1 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte2 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte3 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte4 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte5 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte6 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte7 = new BrpTestObject<BrpGeboorteInhoud>();

    {
        final Lo3TestObject<Lo3PersoonInhoud> lo7 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo6 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo5 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo4 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo3 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo2 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo1 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);

        // @formatter:off
        final BrpActie actieA31 =buildBrpActie(BrpDatumTijd.fromDatum(20000602), "A31", Document.AKTE);
        final BrpActie actieA3 = buildBrpActie(BrpDatumTijd.fromDatum(20000102), "A3", Document.AKTE);
        final BrpActie actieA5 = buildBrpActie(BrpDatumTijd.fromDatum(20010102), "A5", Document.AKTE);
        final BrpActie actieA21 =buildBrpActie(BrpDatumTijd.fromDatum(20000602), "A21", Document.AKTE);
        final BrpActie actieA2 = buildBrpActie(BrpDatumTijd.fromDatum(19950102), "A2", Document.AKTE);
        final BrpActie actieA4 = buildBrpActie(BrpDatumTijd.fromDatum(20000602), "A4", Document.AKTE);
        final BrpActie actieA1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102), "A1", Document.AKTE);

        // LO3 input

        // Klaas   Amsterdam       1-1-2000    2-6-2000    A3-1
        // Klaas   Rotterdam   O   1-1-2000    2-1-2000    A3
        // Henk    Amsterdam       1-1-1995    2-1-2001    A5
        // Piet    Amsterdam   O   1-1-1995    2-6-2000    A2-1
        // Piet    Rotterdam   O   1-1-1995    2-1-1995    A2
        // Jan     Amsterdam       1-1-1990    2-6-2000    A4
        // Jan     Rotterdam   O   1-1-1990    2-1-1990    A1


        lo7.vul(lo3KlaasAdam,   null,    20000101, 20000602, 31, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 6));
        lo6.vul(lo3KlaasRdam,   ONJUIST, 20000101, 20000102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 5));
        lo5.vul(lo3HenkAdam,    null,    19950101, 20010102, 5, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 4));
        lo4.vul(lo3PietAdam,    ONJUIST, 19950101, 20000602, 21, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 3));
        lo3.vul(lo3PietRdam,    ONJUIST, 19950101, 19950102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 2));
        lo2.vul(lo3JanAdam,     null,    19900101, 20000602, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 1));
        lo1.vul(lo3JanRdam,     ONJUIST, 19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));

        // verwachte BRP output
        // Jan         1-1-1990                2-1-1990    2-1-1990    A1      A1
        // Piet        1-1-1995                2-1-1995    2-1-1995    A2      A2
        // Klaas       1-1-2000                2-1-2000    2-1-2000    A3      A3
        // Jan         1-1-1990    1-1-1995    2-6-2000                A4
        // Piet        1-1-1995    <leeg>      2-6-2000    2-6-2000    A2-1        A2-1
        // Klaas       1-1-2000    <leeg>      2-6-2000                A3-1
        // Henk        1-1-1995    1-1-2000    2-1-2001                A5

  brpJanRdam.  vul(brpInhoudJanJansen,   19900101, null,     19900102000000L, 19900102000000L, actieA1,  null, actieA1);
  brpPietRdam. vul(brpInhoudPietJansen,  19950101, null,     19950102000000L, 19950102000000L, actieA2,  null, actieA2);
  brpKlaasAdam.vul(brpInhoudKlaasJansen, 20000101, null,     20000102000000L, 20000102000000L, actieA3,  null, actieA3);
  brpJanAdam.  vul(brpInhoudJanJansen,   19900101, 19950101, 20000602000000L, null,            actieA4,  null, null);
  brpPietAdam. vul(brpInhoudPietJansen,  19950101, null,     20000602000000L, 20000602000000L, actieA21, null, actieA21);
  brpKlaasRdam.vul(brpInhoudKlaasJansen, 20000101, null,     20000602000000L, null,            actieA31, null, null);
  brpHenkAdam. vul(brpInhoudHenkJansen,  19950101, 20000101, 20010102000000L, null,            actieA5,  null, null);

  final BrpGeboorteInhoud brpGeboorteInhoudRdam = buildBrpGeboorteInhoud(20000101, RDAM);
  final BrpGeboorteInhoud brpGeboorteInhoudAdam = buildBrpGeboorteInhoud(20000101, ADAM);
      // rotterdam   2-1-1990    2-1-1990    A1  A1
      // rotterdam   2-1-1995    2-1-1995    A2  A2
      // rotterdam   2-1-2000    2-1-2000    A3  A3
      // amsterdam   2-6-2000    2-6-2000    A4
      // Amsterdam   2-6-2000    2-6-2000    A2-1    A2-1
      // Amsterdam   2-6-2000    2-1-2001    A3-1
      // Amsterdam   2-1-2001                A5

  brpGeboorte1.vul(brpGeboorteInhoudRdam, null, null, 19900102000000L, 19900102000000L, actieA1,  null, actieA1);
  brpGeboorte2.vul(brpGeboorteInhoudRdam, null, null, 19950102000000L, 19950102000000L, actieA2,  null, actieA2);
  brpGeboorte3.vul(brpGeboorteInhoudRdam, null, null, 20000102000000L, 20000102000000L, actieA3,  null, actieA3);
  brpGeboorte4.vul(brpGeboorteInhoudAdam, null, null, 20000602000000L, 20000602000200L, actieA4,  null, null);
  brpGeboorte5.vul(brpGeboorteInhoudAdam, null, null, 20000602000100L, 20000602000100L, actieA21, null, actieA21);
  brpGeboorte6.vul(brpGeboorteInhoudAdam, null, null, 20000602000200L, 20010102000000L, actieA31, null, null);
  brpGeboorte7.vul(brpGeboorteInhoudAdam, null, null, 20010102000000L, null,            actieA5,  null, null);

        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4, lo5, lo6, lo7);
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
        assertEquals(7, samengesteldeNaamStapel.size());

        System.out.println(samengesteldeNaamStapel.get(0));
        System.out.println(samengesteldeNaamStapel.get(1));
        System.out.println(samengesteldeNaamStapel.get(2));
        System.out.println(samengesteldeNaamStapel.get(3));
        System.out.println(samengesteldeNaamStapel.get(4));
        System.out.println(samengesteldeNaamStapel.get(5));
        System.out.println(samengesteldeNaamStapel.get(6));

        // jan vervallen
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(0), brpJanRdam);
        // piet vervallen
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(1), brpPietRdam);
        // klaas vervallen
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(2), brpKlaasAdam);
        // piet
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(3), brpPietAdam);
        // jan
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(4), brpJanAdam);
        // klaas
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(5), brpKlaasRdam);
        // henk
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(6), brpHenkAdam);

        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = brpPersoonslijst.getGeboorteStapel();
        assertEquals(7, geboorteStapel.size());

        System.out.println(geboorteStapel.get(0));
        System.out.println(geboorteStapel.get(1));
        System.out.println(geboorteStapel.get(2));
        System.out.println(geboorteStapel.get(3));
        System.out.println(geboorteStapel.get(4));
        System.out.println(geboorteStapel.get(5));
        System.out.println(geboorteStapel.get(6));

        // datumTijdRegistratie=19900102120000],datumTijdVerval=19900102120000
        assertGeboorte(geboorteStapel.get(0), brpGeboorte1);
        // datumTijdRegistratie=19950102120000],datumTijdVerval=19950102120000
        assertGeboorte(geboorteStapel.get(1), brpGeboorte2);
        // datumTijdRegistratie=20000102120000],datumTijdVerval=20000102120000
        assertGeboorte(geboorteStapel.get(2), brpGeboorte3);
        // datumTijdRegistratie=20000602120000],datumTijdVerval=20000602120000 A21
        assertGeboorte(geboorteStapel.get(3), brpGeboorte4);
        // datumTijdRegistratie=20000602120100],datumTijdVerval=20000602120200
        assertGeboorte(geboorteStapel.get(4), brpGeboorte5);
        // datumTijdRegistratie=20000602120200],datumTijdVerval=20010102120000
        assertGeboorte(geboorteStapel.get(5), brpGeboorte6);
        // datumTijdRegistratie=20010102120000],datumTijdVerval=<null>
        assertGeboorte(geboorteStapel.get(6), brpGeboorte7);
    }

    @Override
    @Test
    public void testRondverteer() throws Exception {
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = conversieService.converteerBrpPersoonslijst(brpPersoonslijst);
        final Lo3Stapel<Lo3PersoonInhoud> rondverteerdeStapel = terug.getPersoonStapel();
        final Lo3Stapel<Lo3PersoonInhoud> origineleStapel = getLo3Persoonslijst().getPersoonStapel();

        Lo3StapelHelper.vergelijk(origineleStapel, rondverteerdeStapel);
        // assertEquals(origineleStapels, rondverteerdeStapels); }
    }

}
