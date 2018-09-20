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
public class Casus24NaamTest extends CasusTest {
    private final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

    private final BrpSamengesteldeNaamInhoud brpInhoudPietJansen = new BrpSamengesteldeNaamInhoud(null, "Piet", null,
            null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudPietJans = new BrpSamengesteldeNaamInhoud(null, "Piet", null,
            null, null, "Jans", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudDickJansen = new BrpSamengesteldeNaamInhoud(null, "Dick", null,
            null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudKasJansen = new BrpSamengesteldeNaamInhoud(null, "Kas", null,
            null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudBertJansen = new BrpSamengesteldeNaamInhoud(null, "Bert", null,
            null, null, "Jansen", false, true);
    private final Lo3PersoonInhoud lo3KasJansen = buildPersoonMetNamen("Kas", "Jansen");
    private final Lo3PersoonInhoud lo3PietJansen = buildPersoonMetNamen("Piet", "Jansen");
    private final Lo3PersoonInhoud lo3PietJans = buildPersoonMetNamen("Piet", "Jans");
    private final Lo3PersoonInhoud lo3BertJansen = buildPersoonMetNamen("Bert", "Jansen");
    private final Lo3PersoonInhoud lo3DickJansen = buildPersoonMetNamen("Dick", "Jansen");
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpKasJansen =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpDickJansen =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpBertJansen =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpPietJans =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpPietJansen =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();

    {
        final Lo3TestObject<Lo3PersoonInhoud> lo1 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo2 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo3 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo4 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo5 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);

        final BrpActie actie5 = buildBrpActie(BrpDatumTijd.fromDatum(19960102), "A5");
        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(19950102), "A4");
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19940102), "A3");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19930102), "A2");
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19920102), "A1");

        // @formatter:off
        // LO3 input

        // Kas     Jansen      1-1-1995    2-1-1996    <A5>
        // Dick    Jansen      1-1-1994    2-1-1995    <A4>
        // Bert    Jansen      1-1-1991    2-1-1994    <A3>
        // Piet    Jans    O   1-1-1993    2-1-1993    <A2>
        // Piet    Jansen      1-1-1992    2-1-1992    <A1>

        lo5.vul(lo3KasJansen,  null,    19950101, 19960102, 5, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 4));
        lo4.vul(lo3DickJansen, null,    19940101, 19950102, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 3));
        lo3.vul(lo3BertJansen, null,    19910101, 19940102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 2));
        lo2.vul(lo3PietJans,   ONJUIST, 19930101, 19930102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 1));
        lo1.vul(lo3PietJansen, null,    19920101, 19920102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));

        // verwachte BRP output
        // Kas     Jansen  1-1-1995    xxxxxxxx    2-1-1996    xxxxxxxx    <A5>    X   X
        // Dick    Jansen  1-1-1994    1-1-1995    2-1-1995    xxxxxxxx    <A4>    X   X
        // Bert    Jansen  1-1-1991    1-1-1992    2-1-1994    xxxxxxxx    <A3>    X   X
        // Piet    Jans    1-1-1993    xxxxxxxx    2-1-1993    2-1-1993    <A2>    X   <A2>
        // Piet    Jansen  1-1-1992    1-1-1994    2-1-1992    xxxxxxxx    <A1>    x   X

        brpKasJansen. vul(brpInhoudKasJansen,  19950101, null,     19960102000000L, null,            actie5, null, null);
        brpDickJansen.vul(brpInhoudDickJansen, 19940101, 19950101, 19950102000000L, null,            actie4, null, null);
        brpBertJansen.vul(brpInhoudBertJansen, 19910101, 19920101, 19940102000000L, null,            actie3, null, null);
        brpPietJans.  vul(brpInhoudPietJans,   19930101, null,     19930102000000L, 19930102000000L, actie2, null, actie2);
        brpPietJansen.vul(brpInhoudPietJansen, 19920101, 19940101, 19920102000000L, null,            actie1, null, null);
        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4, lo5);
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
        assertEquals(5, samengesteldeNaamStapel.size());

        System.out.println(samengesteldeNaamStapel.get(0));
        System.out.println(samengesteldeNaamStapel.get(1));
        System.out.println(samengesteldeNaamStapel.get(2));
        System.out.println(samengesteldeNaamStapel.get(3));
        System.out.println(samengesteldeNaamStapel.get(4));

        // piet jans
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(0), brpPietJans);
        // piet jansen
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(1), brpPietJansen);
        // bert
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(2), brpBertJansen);
        // dick
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(3), brpDickJansen);
        // kas
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(4), brpKasJansen);
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
