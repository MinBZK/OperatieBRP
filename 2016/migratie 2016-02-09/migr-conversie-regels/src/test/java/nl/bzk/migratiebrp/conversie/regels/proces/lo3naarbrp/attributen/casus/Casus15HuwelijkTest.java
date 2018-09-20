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
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;
import org.junit.Test;

public class Casus15HuwelijkTest extends AbstractCasusTest {

    private static final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> CATEGORIEN = new ArrayList<>();

    private static final String ROTTERDAM = "0599";
    private static final String DENHAAG = "0518";
    private static final String UTRECHT = "0344";

    {
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo3 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo4 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo5 = new Lo3TestObject<>(Document.AKTE);

        // @formatter:off

        // <leeg> <leeg> 1-1-1990 2-1-1995 <A5>
        // <leeg> 2-1-1994, Utrecht O 2-1-1994 4-1-1994 <A4>
        // 1-1-1994, Rotterdam <leeg> O 1-1-1994 3-1-1994 <A3>
        // <leeg> 1-1-1994, Utrecht O 1-1-1994 2-1-1994 <A2>
        // 1-1-1990, Den Haag <leeg> O 1-1-1990 2-1-1990 <A1>

        // LO3 input
        lo5.vul(buildHuwelijk(null, null, null, null), null, 19900101, 19950102, 5, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0));
        lo4.vul(buildHuwelijk(null, null, 19940102, UTRECHT),
                ONJUIST,
                19940102,
                19940104,
                4,
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 1));
        lo3.vul(buildHuwelijk(19940101, ROTTERDAM, null, null),
                ONJUIST,
                19940101,
                19940103,
                3,
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 2));
        lo2.vul(buildHuwelijk(null, null, 19940101, UTRECHT),
                ONJUIST,
                19940101,
                19940102,
                2,
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 3));
        lo1.vul(buildHuwelijk(19900101, DENHAAG, null, null),
                ONJUIST,
                19900101,
                19900102,
                1,
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 4));

        // 1-1-1994, Rotterdam 2-1-1994, Utrecht 4-1-1994 2-1-1995 <A4> <A5>
        // 1-1-1994, Rotterdam Xxxxxxxxxxxxxxxx 3-1-1994 3-1-1994 <A3> <A3>
        // 1-1-1990, Den Haag 1-1-1994, Utrecht 2-1-1994 2-1-1994 <A2> <A2>
        // 1-1-1990, Den Haag xxxxxxxxxxxxxxxx 2-1-1990 2-1-1990 <A1> <A1>

        // verwacht geen BRP output

        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4, lo5);
    }

    @Override
    protected Lo3Stapel<Lo3HuwelijkOfGpInhoud> maakHuwelijkStapel() {
        return StapelUtils.createStapel(CATEGORIEN);
    }

    private static void vulCategorieen(final Lo3TestObject<Lo3HuwelijkOfGpInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo : testObjecten) {
            CATEGORIEN.add(new Lo3Categorie<>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo.getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);

        final List<BrpRelatie> relaties = brpPersoonslijst.getRelaties(BrpSoortRelatieCode.HUWELIJK);
        assertEquals(0, relaties.size());
    }

    @Override
    @Test
    public void testRondverteer() {
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> rondverteerdeStapels = terug.getHuwelijkOfGpStapels();
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> origineleStapels = getLo3Persoonslijst().getHuwelijkOfGpStapels();
        Lo3StapelHelper.vergelijk(rondverteerdeStapels, origineleStapels);
        //
        // assertEquals(1, rondverteerdeStapels.size());
        // assertEquals(5, rondverteerdeStapels.get(0).size());
        // assertEquals(origineleStapels, rondverteerdeStapels);
    }

}
