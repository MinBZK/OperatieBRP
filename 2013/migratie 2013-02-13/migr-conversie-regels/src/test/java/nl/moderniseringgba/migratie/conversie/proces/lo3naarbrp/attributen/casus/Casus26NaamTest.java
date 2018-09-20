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
public class Casus26NaamTest extends CasusTest {
    private final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

    private final BrpSamengesteldeNaamInhoud brpInhoudPietJansen = new BrpSamengesteldeNaamInhoud(null, "Piet", null,
            null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudPietJans = new BrpSamengesteldeNaamInhoud(null, "Piet", null,
            null, null, "Jans", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudDickJansen = new BrpSamengesteldeNaamInhoud(null, "Dick", null,
            null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudKlaasJansen = new BrpSamengesteldeNaamInhoud(null, "Klaas",
            null, null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpInhoudBertJansen = new BrpSamengesteldeNaamInhoud(null, "Bert", null,
            null, null, "Jansen", false, true);
    private final Lo3PersoonInhoud lo3KlaasJansen = buildPersoonMetNamen("Klaas", "Jansen");
    private final Lo3PersoonInhoud lo3PietJansen = buildPersoonMetNamen("Piet", "Jansen");
    private final Lo3PersoonInhoud lo3PietJans = buildPersoonMetNamen("Piet", "Jans");
    private final Lo3PersoonInhoud lo3BertJansen = buildPersoonMetNamen("Bert", "Jansen");
    private final Lo3PersoonInhoud lo3DickJansen = buildPersoonMetNamen("Dick", "Jansen");
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpKlaasJansen =
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
        final Lo3TestObject<Lo3PersoonInhoud> lo1 = new Lo3TestObject<Lo3PersoonInhoud>(Document.DOCUMENT);
        final Lo3TestObject<Lo3PersoonInhoud> lo2 = new Lo3TestObject<Lo3PersoonInhoud>(Document.DOCUMENT);
        final Lo3TestObject<Lo3PersoonInhoud> lo3 = new Lo3TestObject<Lo3PersoonInhoud>(Document.DOCUMENT);
        final Lo3TestObject<Lo3PersoonInhoud> lo4 = new Lo3TestObject<Lo3PersoonInhoud>(Document.DOCUMENT);
        final Lo3TestObject<Lo3PersoonInhoud> lo5 = new Lo3TestObject<Lo3PersoonInhoud>(Document.DOCUMENT);

        final BrpActie actie5 = buildBrpActie(BrpDatumTijd.fromDatum(19970102), "d5", Document.DOCUMENT);
        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(19960102), "d4", Document.DOCUMENT);
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19950102), "d3", Document.DOCUMENT);
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19940102), "d2", Document.DOCUMENT);
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19930102), "d1", Document.DOCUMENT);

        // @formatter:off
        // LO3 input

        // Klaas   Jansen      1-1-1996    2-1-1997    d5
        // Dick    Jansen      1-1-1995    2-1-1996    d4
        // Bert    Jansen      1-1-1992    2-1-1995    d3
        // Piet    Jans    O   1-1-1994    2-1-1994    d2
        // Piet    Jansen      1-1-1993    2-1-1993    d1

        lo5.vul(lo3KlaasJansen, null,    19960101, 19970102, 5, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 4));
        lo4.vul(lo3DickJansen,  null,    19950101, 19960102, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 3));
        lo3.vul(lo3BertJansen,  null,    19920101, 19950102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 2));
        lo2.vul(lo3PietJans,    ONJUIST, 19940101, 19940102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 1));
        lo1.vul(lo3PietJansen,  null,    19930101, 19930102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));

        // verwachte BRP output
        // Klaas   Jansen  1-1-1996    xxxxxxxx    2-1-1997    Xxxxxxxx    d5  X   X
        // Dick    Jansen  1-1-1995    1-1-1996    2-1-1996    Xxxxxxxx    d4  X   X
        // Piet    Jans    1-1-1994    xxxxxxxx    2-1-1994    2-1-1994    d2  X   d2
        // Piet    Jansen  1-1-1993    1-1-1995    2-1-1993    Xxxxxxxx    d1  X   X
        // Bert    Jansen  1-1-1992    1-1-1993    2-1-1995    xxxxxxxx    d3  X   X

        brpKlaasJansen.vul(brpInhoudKlaasJansen, 19960101, null,     19970102000000L, null,            actie5, null, null);
        brpDickJansen. vul(brpInhoudDickJansen,  19950101, 19960101, 19960102000000L, null,            actie4, null, null);
        brpPietJans.   vul(brpInhoudPietJans,    19940101, null,     19940102000000L, 19940102000000L, actie2, null, actie2);
        brpPietJansen. vul(brpInhoudPietJansen,  19930101, 19950101, 19930102000000L, null,            actie1, null, null);
        brpBertJansen. vul(brpInhoudBertJansen,  19920101, 19930101, 19950102000000L, null,            actie3, null, null);
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

        // piet jans
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(0), brpPietJans);
        // piet jansen
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(1), brpPietJansen);
        // bert
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(2), brpBertJansen);
        // dick
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(3), brpDickJansen);
        // klaas
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(4), brpKlaasJansen);
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
