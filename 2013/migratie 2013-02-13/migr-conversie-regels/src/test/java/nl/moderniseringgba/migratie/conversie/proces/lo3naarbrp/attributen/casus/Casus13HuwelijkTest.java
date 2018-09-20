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
import nl.moderniseringgba.migratie.testutils.StapelUtils;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class Casus13HuwelijkTest extends CasusTest {

    private final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen =
            new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>();

    private final String rotterdam = "0599";
    private final String denhaag = "0518";

    private final BrpTestObject<BrpRelatieInhoud> brp1 = new BrpTestObject<BrpRelatieInhoud>();
    private final BrpTestObject<BrpRelatieInhoud> brp2 = new BrpTestObject<BrpRelatieInhoud>();

    {
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo1 = new Lo3TestObject<Lo3HuwelijkOfGpInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo2 = new Lo3TestObject<Lo3HuwelijkOfGpInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo3 = new Lo3TestObject<Lo3HuwelijkOfGpInhoud>(Document.AKTE);

        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102), "A1", "PL - Verbintenis stapel 1");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19900103), "A2", "PL - Verbintenis stapel 1");
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19950102), "A3", "PL - Verbintenis stapel 1");

        // @formatter:off
        // <leeg>               <leeg>      1-1-1990    2-1-1995    <A3>
        // 1-1-1990, Rotterdam  <leeg>  O   1-1-1990    3-1-1990    <A2>
        // 1-1-1990, Den Haag   <leeg>  O   1-1-1990    2-1-1990    <A1>

        // LO3 input
        lo3.vul(buildHuwelijk(null,     null,        null, null), null,    19900101, 19950102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 2));
        lo2.vul(buildHuwelijk(19900101, rotterdam,   null, null), ONJUIST, 19900101, 19900103, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 1));
        lo1.vul(buildHuwelijk(19900101, denhaag,     null, null), ONJUIST, 19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0));

        // 1-1-1990, rotterdam   xxxxxxxxx  3-1-1990    2-1-1995    <A2>    <A3>
        // 1-1-1990, den haag    xxxxxxxxx  2-1-1990    2-1-1990    <A1>    <A1>

        // verwachte BRP output
        brp1.vulHuwelijk(19900101, rotterdam, null, null, 19900103000000L, 19950102000000L, actie2, actie3);
        brp2.vulHuwelijk(19900101, denhaag,   null, null, 19900102000000L, 19900102000000L, actie1, actie1);
        // @formatter:on

        vulCategorieen(lo1, lo2, lo3);
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
        assertEquals(2, relatieStapel.size());

        System.out.println(relatieStapel.get(0));
        System.out.println(relatieStapel.get(1));
        assertHuwelijk(relatieStapel.get(0), brp2);
        assertHuwelijk(relatieStapel.get(1), brp1);
    }

    @Override
    @Test
    public void testRondverteer() throws Exception {
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = conversieService.converteerBrpPersoonslijst(brpPersoonslijst);
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> rondverteerdeStapels = terug.getHuwelijkOfGpStapels();
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> origineleStapels =
                getLo3Persoonslijst().getHuwelijkOfGpStapels();
        assertEquals(1, rondverteerdeStapels.size());
        assertEquals(3, rondverteerdeStapels.get(0).size());
        assertEquals(origineleStapels, rondverteerdeStapels);
    }

}
