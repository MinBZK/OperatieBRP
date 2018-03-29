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

public class Casus28NaamEnGeboorteTest extends AbstractCasusTest {
    private static final String JANSEN = "Jansen";

    private final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<>();

    private final BrpSamengesteldeNaamInhoud brpInhoudPietJansen = new BrpSamengesteldeNaamInhoud(
            null,
            new BrpString("Piet"),
            null,
            null,
            null,
            new BrpString(JANSEN),
            new BrpBoolean(false, null),
            new BrpBoolean(true, null));
    private final BrpSamengesteldeNaamInhoud brpInhoudKlaasJansen = new BrpSamengesteldeNaamInhoud(
            null,
            new BrpString("Klaas"),
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
    private final BrpSamengesteldeNaamInhoud brpInhoudHenkJansen = new BrpSamengesteldeNaamInhoud(
            null,
            new BrpString("Henk"),
            null,
            null,
            null,
            new BrpString(JANSEN),
            new BrpBoolean(false, null),
            new BrpBoolean(true, null));

    private final Lo3PersoonInhoud lo3PietJansen = buildPersoonMetNaamEnGeboorte("Piet", JANSEN, 20000101, RDAM);
    private final Lo3PersoonInhoud lo3KlaasJansen = buildPersoonMetNaamEnGeboorte("Klaas", JANSEN, 20000101, RDAM);
    private final Lo3PersoonInhoud lo3HenkJansen = buildPersoonMetNaamEnGeboorte("Henk", JANSEN, 20000101, RDAM);
    private final Lo3PersoonInhoud lo3JanJansen = buildPersoonMetNaamEnGeboorte("Jan", JANSEN, 20000101, RDAM);

    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brp1 = new BrpTestObject<>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brp3 = new BrpTestObject<>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brp4 = new BrpTestObject<>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brp2 = new BrpTestObject<>();

    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte1 = new BrpTestObject<>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte2 = new BrpTestObject<>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte3 = new BrpTestObject<>();
    private final BrpTestObject<BrpGeboorteInhoud> brpGeboorte4 = new BrpTestObject<>();

    {
        final Lo3TestObject<Lo3PersoonInhoud> lo4 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo3 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);

        final BrpActie actieA4 = buildBrpActie(20000102, "4A", Document.AKTE);
        final BrpActie actieA3 = buildBrpActie(20010102, "3A", Document.AKTE);
        final BrpActie actieA2 = buildBrpActie(19950102, "2A", Document.AKTE);
        final BrpActie actieA1 = buildBrpActie(19900102, "1A", Document.AKTE);

        // @formatter:off
        // LO3 input

        // Klaas Rotterdam 1-1-2000 2-1-2000 A4
        // Henk Rotterdam 1-1-1995 2-1-2001 A3
        // Piet Rotterdam O 1-1-1995 2-1-1995 A2
        // Jan Rotterdam 1-1-1990 2-1-1990 A1

        lo4.vul(lo3KlaasJansen, null, 20000101, 20000102, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        lo3.vul(lo3HenkJansen, null, 19950101, 20010102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 1));
        lo2.vul(lo3PietJansen, ONJUIST, 19950101, 19950102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 2));
        lo1.vul(lo3JanJansen, null, 19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 3));

        // verwachte BRP output
        // Jan 1-1-1990 1-1-1995 2-1-1990 Xxxxxxxx A1 X X
        // Piet 1-1-1995 xxxxxxxx 2-1-1995 2-1-1995 A2 X A2
        // Klaas 1-1-2000 xxxxxxxx 2-1-2000 Xxxxxxxx A4 X X
        // Henk 1-1-1995 1-1-2000 2-1-2001 xxxxxxxx A3 X X

        brp1.vul(brpInhoudJanJansen, 19900101, 19950101, 19900102010000L, null, null, actieA1, actieA3, null);
        brp2.vul(brpInhoudPietJansen, 19950101, null, 19950102010000L, 19950102010000L, 'O', actieA2, null, actieA2);
        brp3.vul(brpInhoudKlaasJansen, 20000101, null, 20000102010000L, null, null, actieA4, null, null);
        brp4.vul(brpInhoudHenkJansen, 19950101, 20000101, 20010102010000L, null, null, actieA3, actieA4, null);

        // rotterdam 2-1-1990 2-1-2000 A1
        // Rotterdam 2-1-1995 2-1-1995 A2 A2
        // Rotterdam 2-1-2000 2-1-2001 A3
        // Rotterdam 2-1-2001 Xxxxxxxx A4

        final BrpGeboorteInhoud brpGeboorteInhoudRdam = buildBrpGeboorteInhoud(20000101, RDAM);

        brpGeboorte1.vul(brpGeboorteInhoudRdam, null, null, 19900102010000L, 19900102010000L, null, actieA1, null, actieA1);
        brpGeboorte2.vul(brpGeboorteInhoudRdam, null, null, 19950102010000L, 19950102010000L, 'O', actieA2, null, actieA2);
        brpGeboorte4.vul(brpGeboorteInhoudRdam, null, null, 20010102010000L, 20010102010000L, null, actieA3, null, actieA3);
        brpGeboorte3.vul(brpGeboorteInhoudRdam, null, null, 20000102010000L, null, null, actieA4, null, null);

        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4);
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
        assertEquals(4, samengesteldeNaamStapel.size());

        assertSamengesteldeNaam(samengesteldeNaamStapel.get(0), brp1);
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(1), brp2);
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(2), brp3);
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(3), brp4);

        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = brpPersoonslijst.getGeboorteStapel();
        assertEquals(4, geboorteStapel.size());

        assertGeboorte(geboorteStapel.get(0), brpGeboorte1);
        assertGeboorte(geboorteStapel.get(1), brpGeboorte2);
        assertGeboorte(geboorteStapel.get(2), brpGeboorte3);
        assertGeboorte(geboorteStapel.get(3), brpGeboorte4);
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
