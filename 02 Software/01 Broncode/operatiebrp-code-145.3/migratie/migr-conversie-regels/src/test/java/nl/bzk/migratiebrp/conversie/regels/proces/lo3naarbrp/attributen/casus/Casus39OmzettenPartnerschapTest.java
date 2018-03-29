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

public class Casus39OmzettenPartnerschapTest extends AbstractCasusTest {

    private static final String ROTTERDAM = "0599";
    private static final String DENHAAG = "0518";

    private final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen = new ArrayList<>();

    {
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo3 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo4 = new Lo3TestObject<>(Document.AKTE);

        // @formatter:off

        // xxxxxxxx - Xxxxxxxx - - - 1-2-1990 5-1-1995 Rotterdam A4 (correctie-1)
        // Xxxxxxxx - 3-1-1995 Rotterdam P O 3-1-1995 4-1-1995 Rotterdam A3 (ontbinding-1)
        // 1-2-1990 Rotterdam Xxxxxxxx - P O 1-2-1990 3-2-1990 Rotterdam A2 (sluiting-1b)
        // 1-2-1990 Den Haag xxxxxxxx - H O 1-2-1990 2-2-1990 Rotterdam A1 (sluiting-1a)

        // LO3 input
        lo4.vul(buildHuwelijk(null, null, null, null), null, 19900201, 19950105, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0));
        lo3.vul(buildHuwelijk(null, null, 19950103, ROTTERDAM, P),
                ONJUIST,
                19950103,
                19950104,
                3,
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 1));
        lo2.vul(buildHuwelijk(19900201, ROTTERDAM, null, null, P),
                ONJUIST,
                19900201,
                19900203,
                2,
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 2));
        lo1.vul(buildHuwelijk(19900201, DENHAAG, null, null, H),
                ONJUIST,
                19900201,
                19900202,
                1,
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 3));

        // verwacht geen BRP output

        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4);
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

        final List<BrpRelatie> huwelijkRelaties = brpPersoonslijst.getRelaties(BrpSoortRelatieCode.HUWELIJK);
        assertEquals(0, huwelijkRelaties.size());

        final List<BrpRelatie> partnerRelaties = brpPersoonslijst.getRelaties(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP);
        assertEquals(0, partnerRelaties.size());
    }

    @Override
    @Test
    public void testRondverteer() {
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> rondverteerdeStapels = terug.getHuwelijkOfGpStapels();
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> origineleStapels = getLo3Persoonslijst().getHuwelijkOfGpStapels();

        Lo3StapelHelper.vergelijk(origineleStapels, rondverteerdeStapels);
        //
        // assertEquals(1, rondverteerdeStapels.size());
        // assertEquals(4, rondverteerdeStapels.get(0).size());
        // assertEquals(origineleStapels, rondverteerdeStapels);
    }

}
