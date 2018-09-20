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
public class Casus16NaamTest extends CasusTest {
    private final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

    private final BrpSamengesteldeNaamInhoud brpSamengesteldPiet = new BrpSamengesteldeNaamInhoud(null, "Piet", null,
            null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpSamengesteldJan = new BrpSamengesteldeNaamInhoud(null, "Jan", null,
            null, null, "Jansen", false, true);
    private final BrpSamengesteldeNaamInhoud brpSamengesteldLeeg = new BrpSamengesteldeNaamInhoud(null, null, null,
            null, null, "Jansen", false, true);
    private final Lo3PersoonInhoud lo3Leeg = buildPersoonMetVoornaam(null);
    private final Lo3PersoonInhoud lo3Piet = buildPersoonMetVoornaam("Piet");
    private final Lo3PersoonInhoud lo3Jan = buildPersoonMetVoornaam("Jan");
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpSamengesteld1 =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpSamengesteld2 =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpSamengesteld3 =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();
    private final BrpTestObject<BrpSamengesteldeNaamInhoud> brpSamengesteld4 =
            new BrpTestObject<BrpSamengesteldeNaamInhoud>();

    {
        final Lo3TestObject<Lo3PersoonInhoud> lo1 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo2 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo3 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo4 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);

        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(19960202), "A4");
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19950204), "A3");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19950203), "A2");
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19920508), "A1");

        // @formatter:off
        // Jan     Jansen      01-03-1992  02-02-1996  <A4>
        // <leeg>  Jansen      02-02-1995  04-02-1995  <A3>
        // <leeg>  Jansen   O  01-02-1995  03-02-1995  <A2>
        // Piet    Jansen      01-04-1992  08-05-1992  <A1>

        // LO3 input
        lo4.vul(lo3Jan,   null,    19920301, 19960202, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 3));
        lo3.vul(lo3Leeg,  null,    19950202, 19950204, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 2));
        lo2.vul(lo3Leeg,  ONJUIST, 19950201, 19950203, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 1));
        lo1.vul(lo3Piet,  null,    19920401, 19920508, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));

        // verwachte BRP output
        // <leeg>  Jansen  1-2-1995    xxxxxx      3-2-1995    3-2-1995    <A2>    x   <A2>
        // Jan     Jansen  1-3-1992    1-4-1992    2-2-1996    xxxxxxx     <A4>    x   x
        // <leeg>  Jansen  2-2-1995    xxxxxxx     4-2-1995    xxxxxxx     <A3>    x   x
        // Piet    Jansen  1-4-1992    2-2-1995    8-5-1992    xxxxxxx     <A1>    x   x


        brpSamengesteld1.vul(brpSamengesteldLeeg, 19950201, null,     19950203000000L, 19950203000000L, actie2, null, actie2);
        brpSamengesteld2.vul(brpSamengesteldJan,  19920301, 19920401, 19960202000000L, null,            actie4, null, null);
        brpSamengesteld3.vul(brpSamengesteldLeeg, 19950202, null,     19950204000000L, null,            actie3, null, null);
        brpSamengesteld4.vul(brpSamengesteldPiet, 19920401, 19950202, 19920508000000L, null,            actie1, null, null);
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

        // null, vervallen
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(0), brpSamengesteld1);
        // piet
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(1), brpSamengesteld4);
        // null
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(2), brpSamengesteld3);
        // jan
        assertSamengesteldeNaam(samengesteldeNaamStapel.get(3), brpSamengesteld2);
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
        // assertEquals(origineleStapels, rondverteerdeStapels); }
    }

}
