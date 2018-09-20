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
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.testutils.StapelUtils;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class Casus14HuwelijkTest extends CasusTest {

    private final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen =
            new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>();

    private final String rotterdam = "0599";
    private final String denhaag = "0518";
    private final String utrecht = "0344";

    private final BrpTestObject<BrpRelatieInhoud> brp1 = new BrpTestObject<BrpRelatieInhoud>();
    private final BrpTestObject<BrpRelatieInhoud> brp2 = new BrpTestObject<BrpRelatieInhoud>();
    private final BrpTestObject<BrpRelatieInhoud> brp3 = new BrpTestObject<BrpRelatieInhoud>();
    private final BrpTestObject<BrpRelatieInhoud> brp4 = new BrpTestObject<BrpRelatieInhoud>();

    {
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo1 = new Lo3TestObject<Lo3HuwelijkOfGpInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo2 = new Lo3TestObject<Lo3HuwelijkOfGpInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo3 = new Lo3TestObject<Lo3HuwelijkOfGpInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo4 = new Lo3TestObject<Lo3HuwelijkOfGpInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo5 = new Lo3TestObject<Lo3HuwelijkOfGpInhoud>(Document.AKTE);

        final BrpActie actie5 = buildBrpActie(BrpDatumTijd.fromDatum(19950102), "A5", "PL - Verbintenis stapel 1");
        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(19940104), "A4", "PL - Verbintenis stapel 1");
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19940103), "A3", "PL - Verbintenis stapel 1");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19940102), "A2", "PL - Verbintenis stapel 1");
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102), "A1", "PL - Verbintenis stapel 1");

        // @formatter:off

        // <leeg>               <leeg>                  1-1-1990    2-1-1995    <A5>
        // <leeg>               2-1-1994, Utrecht   O   2-1-1994    4-1-1994    <A4>
        // 1-1-1990, Rotterdam  <leeg>              O   1-1-1990    3-1-1994    <A3>
        // <leeg>               1-1-1994, Utrecht   O   1-1-1994    2-1-1994    <A2>
        // 1-1-1990, Den Haag   <leeg>              O   1-1-1990    2-1-1990    <A1>


        // LO3 input
        lo5.vul(buildHuwelijk(null,     null,      null,      null),    null,    19900101, 19950102, 5, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 4));
        lo4.vul(buildHuwelijk(null,     null,      19940102,  utrecht), ONJUIST, 19940102, 19940104, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 3));
        lo3.vul(buildHuwelijk(19900101, rotterdam, null,      null),    ONJUIST, 19900101, 19940103, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 2));
        lo2.vul(buildHuwelijk(null,     null,      19940101,  utrecht), ONJUIST, 19940101, 19940102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 1));
        lo1.vul(buildHuwelijk(19900101, denhaag,   null,      null),    ONJUIST, 19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0));


        // 1-1-1990, Rotterdam 2-1-1994, Utrecht   4-1-1994    4-1-1994    <A4>    <A4>
        // 1-1-1990, Rotterdam xxxxxxxxxxxxxxxxx   3-1-1994    2-1-1995    <A3>    <A5>
        // 1-1-1990, Den Haag  1-1-1994, Utrecht   2-1-1994    2-1-1994    <A2>    <A2>
        // 1-1-1990, Den Haag  xxxxxxxxxxxxxxxxx   2-1-1990    2-1-1990    <A1>    <A1>

        // verwachte BRP output
        brp1.vulHuwelijk(19900101, rotterdam, 19940102, utrecht, 19940104000000L, 19950102000000L, actie4, actie5);
        brp2.vulHuwelijk(19900101, rotterdam, null,     null,    19940103000000L, 19950102000000L, actie3, actie5);
        brp3.vulHuwelijk(19900101, denhaag,   19940101, utrecht, 19940102000000L, 19940102000000L, actie2, actie2);
        brp4.vulHuwelijk(19900101, denhaag,   null,     null,    19900102000000L, 19900102000000L, actie1, actie1);

        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4, lo5);
    }

    @Override
    protected Lo3Stapel<Lo3HuwelijkOfGpInhoud> maakHuwelijkStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3HuwelijkOfGpInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<Lo3HuwelijkOfGpInhoud>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(),
                    lo.getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() throws Exception {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);

        final List<BrpRelatie> relaties = brpPersoonslijst.getRelaties(BrpSoortRelatieCode.HUWELIJK);
        assertEquals(1, relaties.size());
        final BrpStapel<BrpRelatieInhoud> relatieStapel = relaties.get(0).getRelatieStapel();

        sorteerBrpStapel(relatieStapel);
        assertEquals(4, relatieStapel.size());

        System.out.println(relatieStapel.get(0));
        System.out.println(relatieStapel.get(1));
        System.out.println(relatieStapel.get(2));
        System.out.println(relatieStapel.get(3));

        // aanvang=denhaag
        assertHuwelijk(relatieStapel.get(0), brp4);
        // aanvang=denhaag, ontbonden
        assertHuwelijk(relatieStapel.get(1), brp3);
        // aanvang=rotterdam
        assertHuwelijk(relatieStapel.get(2), brp2);
        // aanvang=rotterdam, ontbonden
        assertHuwelijk(relatieStapel.get(3), brp1);
    }

    @Override
    @Test
    public void testRondverteer() throws Exception {
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = conversieService.converteerBrpPersoonslijst(brpPersoonslijst);
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> rondverteerdeStapels = terug.getHuwelijkOfGpStapels();
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> origineleStapels =
                getLo3Persoonslijst().getHuwelijkOfGpStapels();

        Lo3StapelHelper.vergelijk(rondverteerdeStapels, origineleStapels);
        //
        // assertEquals(1, rondverteerdeStapels.size());
        // assertEquals(5, rondverteerdeStapels.get(0).size());
        // assertEquals(origineleStapels, rondverteerdeStapels);
    }

}
