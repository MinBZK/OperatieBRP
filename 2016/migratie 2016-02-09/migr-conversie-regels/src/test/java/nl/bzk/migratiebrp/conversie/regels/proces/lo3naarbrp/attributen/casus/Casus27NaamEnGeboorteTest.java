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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;

import org.junit.Test;

public class Casus27NaamEnGeboorteTest extends AbstractCasusTest {
    private static final String GEMEENTE_CODE = "4321";

    private static final String JANSEN = "Jansen";
    private final BrpSamengesteldeNaamInhoud brpInhoudPietJansen = new BrpSamengesteldeNaamInhoud(
        null,
        new BrpString("Piet"),
        null,
        null,
        null,
        new BrpString(JANSEN),
        new BrpBoolean(false, null),
        new BrpBoolean(true, null));
    private final BrpSamengesteldeNaamInhoud brpInhoudJansJansen = new BrpSamengesteldeNaamInhoud(
        null,
        new BrpString("Jans"),
        null,
        null,
        null,
        new BrpString(JANSEN),
        new BrpBoolean(false, null),
        new BrpBoolean(true, null));
    private final BrpSamengesteldeNaamInhoud brpInhoudJanJansen = new BrpSamengesteldeNaamInhoud(
        null,
        new BrpString("Jan"),
        null,
        null,
        null,
        new BrpString(JANSEN),
        new BrpBoolean(false, null),
        new BrpBoolean(true, null));
    private final Lo3PersoonInhoud lo3PietJansenRdam = buildPersoonMetNaamEnGeboorte("Piet", JANSEN, 20000101, "1234");
    private final Lo3PersoonInhoud lo3PietJansenAdam = buildPersoonMetNaamEnGeboorte("Piet", JANSEN, 20000101, GEMEENTE_CODE);
    private final Lo3PersoonInhoud lo3JansJansen = buildPersoonMetNaamEnGeboorte("Jans", JANSEN, 20000101, GEMEENTE_CODE);
    private final Lo3PersoonInhoud lo3JanJansenRdam = buildPersoonMetNaamEnGeboorte("Jan", JANSEN, 20000101, "1234");
    private final Lo3PersoonInhoud lo3JanJansenAdam = buildPersoonMetNaamEnGeboorte("Jan", JANSEN, 20000101, GEMEENTE_CODE);
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpJanJansenOnjuist = new BrpTestObject<>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpJanJansenOnjuistRdam = new BrpTestObject<>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpJansJansen = new BrpTestObject<>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpPietJansen = new BrpTestObject<>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpPietJansenOnjuist = new BrpTestObject<>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte1 = new BrpTestObject<>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte2 = new BrpTestObject<>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte3 = new BrpTestObject<>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte4 = new BrpTestObject<>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte5 = new BrpTestObject<>();
    private List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<>();

    {
        final Lo3TestObject<Lo3PersoonInhoud> lo4 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo3b = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo3a = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);

        final BrpActie actieA5 = buildBrpActie(20100111, "5A", Document.AKTE);
        final BrpActie actieA4 = buildBrpActie(20100110, "4A", Document.AKTE);
        final BrpActie actieA3 = buildBrpActie(20100107, "3A", Document.AKTE);
        final BrpActie actieA2 = buildBrpActie(20100110, "2A", Document.AKTE);
        final BrpActie actieA1 = buildBrpActie(20000102, "1A", Document.AKTE);

        // @formatter:off
        // LO3 input

        // 4 Jans Jansen 1-1-2000, Amsterdam 6-1-2010 11-1-2010 Naamsw-J2 A5
        // 3b Jan Jansen 1-1-2000, Amsterdam O (11-1-2010) 1-1-2010 10-1-2010 Naamsw-J1 A4
        // 2 Jan Jansen 1-1-2000, rotterdam O (10-1-2010) 1-1-2010 7-1-2010 Naamsw-J1 A3
        // 3a Piet Jansen 1-1-2000, Amsterdam 1-1-2000 10-1-2010 Geb.akte-2 A2
        // 1 Piet Jansen 1-1-2000, rotterdam O (10-1-2010) 1-1-2000 2-1-2000 Geb.akte-1 A1

        lo4.vul(lo3JansJansen, null, 20100106, 20100111, 5, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 0));
        lo3b.vul(lo3JanJansenAdam, ONJUIST, 20100101, 20100110, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 1));
        lo2.vul(lo3JanJansenRdam, ONJUIST, 20100101, 20100107, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 2));
        lo3a.vul(lo3PietJansenAdam, null, 20000101, 20100110, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 3));
        lo1.vul(lo3PietJansenRdam, ONJUIST, 20000101, 20000102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 4));

        // verwachte BRP output
        // Jans Jansen 6-1-2010 xxxxxxxx 11-1-2010 xxxxxxxx Naamsw-J2 X X
        // Jan Jansen 1-1-2010 xxxxxxxx 10-1-2010 10-1-2010 Naamsw-J1 X Naamsw-J1
        // Jan Jansen 1-1-2010 xxxxxxxx 7-1-2010 7-1-2010 Naamsw-J1 X Naamsw-J1
        // Piet Jansen 1-1-2000 6-1-2010 10-1-2010 xxxxxxxx Geb.akte-2 X X
        // Piet Jansen 1-1-2000 xxxxxxxx 2-1-2000 2-1-2000 Geb.akte-1 x Geb.akte-1

        brpPietJansen.vul(brpInhoudPietJansen, 20000101, 20100106, 20100110010000L, null, null, actieA2, actieA5, null);
        brpJansJansen.vul(brpInhoudJansJansen, 20100106, null, 20100111010000L, null, null, actieA5, null, null);
        brpPietJansenOnjuist.vul(brpInhoudPietJansen, 20000101, null, 20000102010000L, 20000102010000L, 'O', actieA1, null, actieA1);
        brpJanJansenOnjuistRdam.vul(brpInhoudJanJansen, 20100101, null, 20100107010000L, 20100107010000L, 'O', actieA3, null, actieA3);
        brpJanJansenOnjuist.vul(brpInhoudJanJansen, 20100101, null, 20100110010000L, 20100110010000L, 'O', actieA4, null, actieA4);

        // 1-1-2000 rotterdam 2-1-2000 2-1-2000 Geb.akte-1 Geb.akte-1 (A1)
        // 1-1-2000 rotterdam 7-1-2010 7-1-2010 Naamsw-J1 Naamsw-J1 (A3)
        // 1-1-2000 amsterdam 10-1-2010 11-1-2010 Geb.akte-2 x (A2)
        // 1-1-2000 amsterdam 10-1-2010 10-1-2010 Naamsw-J1 Naamsw-J1 (A4)
        // 1-1-2000 amsterdam 11-1-2010 xxxxxxxx Naamsw-J2 x (A5)

        final BrpGeboorteInhoud brpGeboorteInhoudRdam = buildBrpGeboorteInhoud(20000101, "1234");
        final BrpGeboorteInhoud brpGeboorteInhoudAdam = buildBrpGeboorteInhoud(20000101, GEMEENTE_CODE);

        brpGeboorte1.vul(brpGeboorteInhoudRdam, null, null, 20000102010000L, 20000102010000L, 'O', actieA1, null, actieA1);
        brpGeboorte2.vul(brpGeboorteInhoudRdam, null, null, 20100107010000L, 20100107010000L, 'O', actieA3, null, actieA3);
        brpGeboorte3.vul(brpGeboorteInhoudAdam, null, null, 20100110010000L, 20100110010000L, null, actieA2, null, actieA2);
        brpGeboorte4.vul(brpGeboorteInhoudAdam, null, null, 20100110010100L, 20100110010100L, 'O', actieA4, null, actieA4);
        brpGeboorte5.vul(brpGeboorteInhoudAdam, null, null, 20100111010000L, null, null, actieA5, null, null);

        // @formatter:on

        categorieen = vulCategorieen(lo1, lo2, lo3a, lo3b, lo4);
    }

    @Override
    protected Lo3Stapel<Lo3PersoonInhoud> maakPersoonStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private List<Lo3Categorie<Lo3PersoonInhoud>> vulCategorieen(final Lo3TestObject<Lo3PersoonInhoud>... testObjecten) {
        final List<Lo3Categorie<Lo3PersoonInhoud>> cat = new ArrayList<>();
        for (final Lo3TestObject<Lo3PersoonInhoud> lo : testObjecten) {
            cat.add(new Lo3Categorie<>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo.getLo3Herkomst()));
        }
        return cat;
    }

    @Override
    @Test
    public void testLo3NaarBrp() {
        // LO3 naar BRP
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);

        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel = brpPersoonslijst.getSamengesteldeNaamStapel();
        assertEquals(5, samengesteldeNaamStapel.size());

        assertSamengesteldeNaam(samengesteldeNaamStapel.get(0), brpPietJansenOnjuist);
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(1), brpJanJansenOnjuistRdam);
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(2), brpPietJansen);
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(3), brpJanJansenOnjuist);
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(4), brpJansJansen);

        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = brpPersoonslijst.getGeboorteStapel();
        assertEquals(5, geboorteStapel.size());

        assertGeboorte(geboorteStapel.get(0), brpGeboorte1);
        assertGeboorte(geboorteStapel.get(1), brpGeboorte2);
        assertGeboorte(geboorteStapel.get(2), brpGeboorte3);
        assertGeboorte(geboorteStapel.get(3), brpGeboorte4);
        assertGeboorte(geboorteStapel.get(4), brpGeboorte5);
    }

    @Override
    @Test
    public void testRondverteer() {
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);
        final Lo3Stapel<Lo3PersoonInhoud> rondverteerdeStapel = terug.getPersoonStapel();
        final Lo3Stapel<Lo3PersoonInhoud> origineleStapel = getLo3Persoonslijst().getPersoonStapel();
        assertEquals(origineleStapel.size(), rondverteerdeStapel.size());
        Lo3StapelHelper.vergelijk(origineleStapel, rondverteerdeStapel);
    }

}
