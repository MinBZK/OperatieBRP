/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.casus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedList;
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

public class Casus13HuwelijkTest extends AbstractCasusTest {

    private static final String ROTTERDAM = "0599";
    private static final String DENHAAG = "0518";

    private final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen = new LinkedList<>();

    {
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo3 = new Lo3TestObject<>(Document.AKTE);

        // @formatter:off
        // <leeg> <leeg> 1-1-1990 2-1-1995 <A3>
        // 1-1-1990, Rotterdam <leeg> O 1-1-1990 3-1-1990 <A2>
        // 1-1-1990, Den Haag <leeg> O 1-1-1990 2-1-1990 <A1>

        // LO3 input
        lo3.vul(buildHuwelijk(null, null, null, null), null, 19900101, 19950102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0));
        lo2.vul(buildHuwelijk(19900101, ROTTERDAM, null, null),
                ONJUIST,
                19900101,
                19900103,
                2,
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 1));
        lo1.vul(buildHuwelijk(19900101, DENHAAG, null, null),
                ONJUIST,
                19900101,
                19900102,
                1,
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 2));

        // 1-1-1990, rotterdam xxxxxxxxx 3-1-1990 2-1-1995 <A2> <A3>
        // 1-1-1990, den haag xxxxxxxxx 2-1-1990 2-1-1990 <A1> <A1>

        // verwacht geen BRP output

        // @formatter:on

        vulCategorieen(lo1, lo2, lo3);
    }

    @Override
    protected Lo3Stapel<Lo3HuwelijkOfGpInhoud> maakHuwelijkStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3HuwelijkOfGpInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo.getLo3Herkomst()));
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

        Lo3StapelHelper.vergelijk(origineleStapels, rondverteerdeStapels);
        // assertEquals(1, rondverteerdeStapels.size());
        // assertEquals(3, rondverteerdeStapels.get(0).size());
        // assertEquals(origineleStapels, rondverteerdeStapels);
    }

}
