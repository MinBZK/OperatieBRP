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

public class Casus29NaamEnGeboorteTest extends AbstractCasusTest {

    private static final String JANSEN = "Jansen";

    private final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<>();

    private final BrpSamengesteldeNaamInhoud brpInhoudPiet = new BrpSamengesteldeNaamInhoud(null, new BrpString("Piet"), null, null, null, new BrpString(
            JANSEN), new BrpBoolean(false, null), new BrpBoolean(true, null));
    private final BrpSamengesteldeNaamInhoud brpInhoudKlaas = new BrpSamengesteldeNaamInhoud(
            null,
            new BrpString("Klaas"),
            null,
            null,
            null,
            new BrpString(JANSEN),
            new BrpBoolean(false, null),
            new BrpBoolean(true, null));
    private final BrpSamengesteldeNaamInhoud brpInhoudJan = new BrpSamengesteldeNaamInhoud(null, new BrpString("Jan"), null, null, null, new BrpString(
            JANSEN), new BrpBoolean(false, null), new BrpBoolean(true, null));
    private final BrpSamengesteldeNaamInhoud brpInhoudHenk = new BrpSamengesteldeNaamInhoud(null, new BrpString("Henk"), null, null, null, new BrpString(
            JANSEN), new BrpBoolean(false, null), new BrpBoolean(true, null));

    private final Lo3PersoonInhoud lo3PietRdam = buildPersoonMetNaamEnGeboorte("Piet", JANSEN, 20000101, RDAM);
    private final Lo3PersoonInhoud lo3KlaasAdam = buildPersoonMetNaamEnGeboorte("Klaas", JANSEN, 20000101, ADAM);
    private final Lo3PersoonInhoud lo3KlaasRdam = buildPersoonMetNaamEnGeboorte("Klaas", JANSEN, 20000101, RDAM);
    private final Lo3PersoonInhoud lo3HenkAdam = buildPersoonMetNaamEnGeboorte("Henk", JANSEN, 20000101, ADAM);
    private final Lo3PersoonInhoud lo3JanAdam = buildPersoonMetNaamEnGeboorte("Jan", JANSEN, 20000101, ADAM);
    private final Lo3PersoonInhoud lo3JanRdam = buildPersoonMetNaamEnGeboorte("Jan", JANSEN, 20000101, RDAM);

    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brp4 = new BrpTestObject<>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brp1 = new BrpTestObject<>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brp3 = new BrpTestObject<>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brp5 = new BrpTestObject<>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brp2 = new BrpTestObject<>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brp6 = new BrpTestObject<>();

    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte1 = new BrpTestObject<>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte2 = new BrpTestObject<>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte3 = new BrpTestObject<>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte4 = new BrpTestObject<>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte5 = new BrpTestObject<>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte6 = new BrpTestObject<>();

    {
        final Lo3TestObject<Lo3PersoonInhoud> lo6 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo5 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo4 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo3 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);

        final BrpActie actieA6 = buildBrpActie(20000602, "6A", Document.AKTE);
        final BrpActie actieA5 = buildBrpActie(20000102, "5A", Document.AKTE);
        final BrpActie actieA4 = buildBrpActie(20010102, "4A", Document.AKTE);
        final BrpActie actieA3 = buildBrpActie(19950102, "3A", Document.AKTE);
        final BrpActie actieA2 = buildBrpActie(20000602, "2A", Document.AKTE);
        final BrpActie actieA1 = buildBrpActie(19900102, "1A", Document.AKTE);

        // @formatter:off
        // LO3 input

        // Klaas Amsterdam 1-1-2000 2-6-2000 A3
        // Klaas Rotterdam O 1-1-2000 2-1-2000 A2
        // Henk Amsterdam 1-1-1995 2-1-2001 A5
        // Piet Rotterdam O 1-1-1995 2-1-1995 A4
        // Jan Amsterdam 1-1-1990 2-6-2000 A6
        // Jan Rotterdam O 1-1-1990 2-1-1990 A1

        lo6.vul(lo3KlaasAdam, null, 20000101, 20000602, 6, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        lo5.vul(lo3KlaasRdam, ONJUIST, 20000101, 20000102, 5, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 1));
        lo4.vul(lo3HenkAdam, null, 19950101, 20010102, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 2));
        lo3.vul(lo3PietRdam, ONJUIST, 19950101, 19950102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 3));
        lo2.vul(lo3JanAdam, null, 19900101, 20000602, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 4));
        lo1.vul(lo3JanRdam, ONJUIST, 19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 5));

        // verwachte BRP output
        // Henk 1-1-1995 1-1-2000 2-1-2001 xxxxxxxx A4
        // Klaas 1-1-2000 xxxxxxxx 2-6-2000 xxxxxxxx A6
        // Jan 1-1-1990 1-1-1995 2-6-2000 xxxxxxxx A2
        // Klaas 1-1-2000 xxxxxxxx 2-1-2000 2-1-2000 A5 A5
        // Piet 1-1-1995 xxxxxxxx 2-1-1995 2-1-1995 A3 A3
        // Jan 1-1-1990 xxxxxxxx 2-1-1990 2-1-1990 A1 A1

        brp4.vul(brpInhoudJan, 19900101, 19950101, 20000602010000L, null, null, actieA2, actieA4, null);
        brp6.vul(brpInhoudHenk, 19950101, 20000101, 20010102010000L, null, null, actieA4, actieA6, null);
        brp5.vul(brpInhoudKlaas, 20000101, null, 20000602010000L, null, null, actieA6, null, null);
        brp1.vul(brpInhoudJan, 19900101, null, 19900102010000L, 19900102010000L, 'O', actieA1, null, actieA1);
        brp2.vul(brpInhoudPiet, 19950101, null, 19950102010000L, 19950102010000L, 'O', actieA3, null, actieA3);
        brp3.vul(brpInhoudKlaas, 20000101, null, 20000102010000L, 20000102010000L, 'O', actieA5, null, actieA5);

        // amsterdam 2-6-2000 xxxxxxxx A6
        // amsterdam 2-1-2001 2-1-2001 A4
        // amsterdam 2-6-2000 2-6-2000 A2
        // rotterdam 2-1-2000 2-1-2000 A5 A5
        // rotterdam 2-1-1995 2-1-1995 A3 A3
        // rotterdam 2-1-1990 2-1-1990 A1 A1

        final BrpGeboorteInhoud brpGeboorteInhoudRdam = buildBrpGeboorteInhoud(20000101, RDAM);
        final BrpGeboorteInhoud brpGeboorteInhoudAdam = buildBrpGeboorteInhoud(20000101, ADAM);

        brpGeboorte1.vul(brpGeboorteInhoudRdam, null, null, 19900102010000L, 19900102010000L, 'O', actieA1, null, actieA1);
        brpGeboorte2.vul(brpGeboorteInhoudRdam, null, null, 19950102010000L, 19950102010000L, 'O', actieA3, null, actieA3);
        brpGeboorte3.vul(brpGeboorteInhoudRdam, null, null, 20000102010000L, 20000102010000L, 'O', actieA5, null, actieA5);
        brpGeboorte4.vul(brpGeboorteInhoudAdam, null, null, 20000602010000L, 20000602010000L, null, actieA2, null, actieA2);
        brpGeboorte5.vul(brpGeboorteInhoudAdam, null, null, 20010102010000L, 20010102010000L, null, actieA4, null, actieA4);
        brpGeboorte6.vul(brpGeboorteInhoudAdam, null, null, 20000602010100L, null, null, actieA6, null, null);

        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4, lo5, lo6);
    }

    @Override
    protected Lo3Stapel<Lo3PersoonInhoud> maakPersoonStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3PersoonInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3PersoonInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo.getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);

        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel = brpPersoonslijst.getSamengesteldeNaamStapel();
        assertEquals(6, samengesteldeNaamStapel.size());

        assertSamengesteldeNaam(samengesteldeNaamStapel.get(0), brp1);
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(1), brp2);
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(2), brp3);
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(3), brp4);
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(4), brp5);
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(5), brp6);

        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = brpPersoonslijst.getGeboorteStapel();
        assertEquals(6, geboorteStapel.size());

        assertGeboorte(geboorteStapel.get(0), brpGeboorte1);
        assertGeboorte(geboorteStapel.get(1), brpGeboorte2);
        assertGeboorte(geboorteStapel.get(2), brpGeboorte3);
        assertGeboorte(geboorteStapel.get(3), brpGeboorte4);
        assertGeboorte(geboorteStapel.get(5), brpGeboorte5);
        assertGeboorte(geboorteStapel.get(4), brpGeboorte6);
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
