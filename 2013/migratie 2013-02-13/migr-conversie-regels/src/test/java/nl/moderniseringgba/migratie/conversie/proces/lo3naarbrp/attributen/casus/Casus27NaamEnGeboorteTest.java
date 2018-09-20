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
public class Casus27NaamEnGeboorteTest extends CasusTest {
    private List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

    private final BrpSamengesteldeNaamInhoud brpInhoudPietJansen = new BrpSamengesteldeNaamInhoud(null, "Piet", null,
            null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudJansJansen = new BrpSamengesteldeNaamInhoud(null, "Jans", null,
            null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudJanJansen = new BrpSamengesteldeNaamInhoud(null, "Jan", null,
            null, null, "Jansen", false, true);

    private final Lo3PersoonInhoud lo3PietJansenRdam = buildPersoonMetNaamEnGeboorte("Piet", "Jansen", 20000101,
            "1234");
    private final Lo3PersoonInhoud lo3PietJansenAdam = buildPersoonMetNaamEnGeboorte("Piet", "Jansen", 20000101,
            "4321");
    private final Lo3PersoonInhoud lo3JansJansen = buildPersoonMetNaamEnGeboorte("Jans", "Jansen", 20000101, "4321");
    private final Lo3PersoonInhoud lo3JanJansenRdam =
            buildPersoonMetNaamEnGeboorte("Jan", "Jansen", 20000101, "1234");
    private final Lo3PersoonInhoud lo3JanJansenAdam =
            buildPersoonMetNaamEnGeboorte("Jan", "Jansen", 20000101, "4321");

    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpJanJansenAdam =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpJanJansenRdam =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpJansJansen =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpPietJansenAdam =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpPietJansenRdam =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();

    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte1 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte2 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte3 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte4 = new BrpTestObject<BrpGeboorteInhoud>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte5 = new BrpTestObject<BrpGeboorteInhoud>();

    {
        final Lo3TestObject<Lo3PersoonInhoud> lo4 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo3b = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo2 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo3a = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo1 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);

        final BrpActie actieA5 = buildBrpActie(BrpDatumTijd.fromDatum(20100111), "A5", Document.AKTE);
        final BrpActie actieA4 = buildBrpActie(BrpDatumTijd.fromDatum(20100110), "A4", Document.AKTE);
        final BrpActie actieA3 = buildBrpActie(BrpDatumTijd.fromDatum(20100107), "A3", Document.AKTE);
        final BrpActie actieA2 = buildBrpActie(BrpDatumTijd.fromDatum(20100110), "A2", Document.AKTE);
        final BrpActie actieA1 = buildBrpActie(BrpDatumTijd.fromDatum(20000102), "A1", Document.AKTE);

        // @formatter:off
        // LO3 input

        // 4   Jans Jansen 1-1-2000, Amsterdam                 6-1-2010    11-1-2010   Naamsw-J2  A5
        // 3b  Jan Jansen  1-1-2000, Amsterdam O (11-1-2010)   1-1-2010    10-1-2010   Naamsw-J1  A4
        // 2   Jan Jansen  1-1-2000, rotterdam O (10-1-2010)   1-1-2010    7-1-2010    Naamsw-J1  A3
        // 3a  Piet Jansen 1-1-2000, Amsterdam                 1-1-2000    10-1-2010   Geb.akte-2 A2
        // 1   Piet Jansen 1-1-2000, rotterdam O (10-1-2010)   1-1-2000    2-1-2000    Geb.akte-1 A1

        lo4. vul(lo3JansJansen,     null,    20100106, 20100111, 5, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 4));
        lo3b.vul(lo3JanJansenAdam,  ONJUIST, 20100101, 20100110, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 3));
        lo2. vul(lo3JanJansenRdam,  ONJUIST, 20100101, 20100107, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 2));
        lo3a.vul(lo3PietJansenAdam, null,    20000101, 20100110, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 1));
        lo1. vul(lo3PietJansenRdam, ONJUIST, 20000101, 20000102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));

        // verwachte BRP output
        // Jans Jansen  6-1-2010    xxxxxxxx    11-1-2010   xxxxxxxx    Naamsw-J2   X   X
        // Jan  Jansen  1-1-2010    xxxxxxxx    10-1-2010   10-1-2010   Naamsw-J1   X   Naamsw-J1
        // Jan  Jansen  1-1-2010    xxxxxxxx    7-1-2010    7-1-2010    Naamsw-J1   X   Naamsw-J1
        // Piet Jansen  1-1-2000    6-1-2010    10-1-2010   xxxxxxxx    Geb.akte-2  X   X
        // Piet Jansen  1-1-2000    xxxxxxxx    2-1-2000    2-1-2000    Geb.akte-1  x   Geb.akte-1

        brpJansJansen.    vul(brpInhoudJansJansen, 20100106, null,     20100111000000L, null,            actieA5, null, null);
        brpJanJansenAdam. vul(brpInhoudJanJansen,  20100101, null,     20100110000000L, 20100110000000L, actieA4, null, actieA4);
        brpJanJansenRdam. vul(brpInhoudJanJansen,  20100101, null,     20100107000000L, 20100107000000L, actieA3, null, actieA3);
        brpPietJansenAdam.vul(brpInhoudPietJansen, 20000101, 20100106, 20100110000000L, null,            actieA2, null, null);
        brpPietJansenRdam.vul(brpInhoudPietJansen, 20000101, null,     20000102000000L, 20000102000000L, actieA1, null, actieA1);

        // 1-1-2000    rotterdam   2-1-2000    2-1-2000    Geb.akte-1  Geb.akte-1
        // 1-1-2000    rotterdam   7-1-2010    7-1-2010    Naamsw-J1   Naamsw-J1
        // 1-1-2000    amsterdam   10-1-2010   11-1-2010   Geb.akte-2  x
        // 1-1-2000    amsterdam   10-1-2010   10-1-2010   Naamsw-J1   Naamsw-J1
        // 1-1-2000    amsterdam   11-1-2010   xxxxxxxx    Naamsw-J2   x

        final BrpGeboorteInhoud brpGeboorteInhoudRdam = buildBrpGeboorteInhoud(20000101, "1234");
        final BrpGeboorteInhoud brpGeboorteInhoudAdam = buildBrpGeboorteInhoud(20000101, "4321");

        brpGeboorte1.vul(brpGeboorteInhoudRdam, null, null, 20000102000000L, 20000102000000L, actieA1,  null, actieA1);
        brpGeboorte2.vul(brpGeboorteInhoudRdam, null, null, 20100107000000L, 20100107000000L, actieA3, null, actieA3);
        brpGeboorte3.vul(brpGeboorteInhoudAdam, null, null, 20100110000000L, 20100111000000L, actieA2,  null, null);
        brpGeboorte4.vul(brpGeboorteInhoudAdam, null, null, 20100110000100L, 20100110000100L, actieA4, null, actieA4);
        brpGeboorte5.vul(brpGeboorteInhoudAdam, null, null, 20100111000000L, null,            actieA5,  null, null);

        // @formatter:on

        categorieen = vulCategorieen(lo1, lo2, lo3a, lo3b, lo4);
    }

    @Override
    protected Lo3Stapel<Lo3PersoonInhoud> maakPersoonStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private List<Lo3Categorie<Lo3PersoonInhoud>>
            vulCategorieen(final Lo3TestObject<Lo3PersoonInhoud>... testObjecten) {
        final List<Lo3Categorie<Lo3PersoonInhoud>> cat = new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();
        for (final Lo3TestObject<Lo3PersoonInhoud> lo : testObjecten) {
            cat.add(new Lo3Categorie<Lo3PersoonInhoud>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo
                    .getLo3Herkomst()));
        }
        return cat;
    }

    @Override
    @Test
    public void testLo3NaarBrp() throws Exception {
        // LO3 naar BRP
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);

        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel =
                brpPersoonslijst.getSamengesteldeNaamStapel();
        assertEquals(5, samengesteldeNaamStapel.size());

        System.out.println(samengesteldeNaamStapel.get(0));
        System.out.println(samengesteldeNaamStapel.get(1));
        System.out.println(samengesteldeNaamStapel.get(2));
        System.out.println(samengesteldeNaamStapel.get(3));
        System.out.println(samengesteldeNaamStapel.get(4));

        // piet vervallen
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(0), brpPietJansenRdam);
        // jan vervallen
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(1), brpJanJansenRdam);
        // jan vervallen 20100110
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(2), brpJanJansenAdam);
        // piet eindigt
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(3), brpPietJansenAdam);
        // jans
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(4), brpJansJansen);

        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = brpPersoonslijst.getGeboorteStapel();
        assertEquals(5, geboorteStapel.size());

        System.out.println(geboorteStapel.get(0));
        System.out.println(geboorteStapel.get(1));
        System.out.println(geboorteStapel.get(2));
        System.out.println(geboorteStapel.get(3));
        System.out.println(geboorteStapel.get(4));

        // rdam reg=20000102
        assertGeboorte(geboorteStapel.get(0), brpGeboorte1);
        // rdam reg=20100107
        assertGeboorte(geboorteStapel.get(1), brpGeboorte2);
        // adam reg=20100110, actie=j1
        assertGeboorte(geboorteStapel.get(2), brpGeboorte3);
        // adam reg=20100110, actie=A2
        assertGeboorte(geboorteStapel.get(3), brpGeboorte4);
        // adam reg=20100111
        assertGeboorte(geboorteStapel.get(4), brpGeboorte5);
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
