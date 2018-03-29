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
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;
import org.junit.Test;

public class Casus41OmzettenPartnerschapTest extends AbstractCasusTest {

    private static final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> CATEGORIEEN = new ArrayList<>();

    private static final String ROTTERDAM = "0599";
    private static final String DENHAAG = "0518";

    private final BrpTestObject<BrpRelatieInhoud> brpP2 = new BrpTestObject<>();
    private final BrpTestObject<BrpRelatieInhoud> brpP1 = new BrpTestObject<>();
    private final BrpTestObject<BrpRelatieInhoud> brpH2 = new BrpTestObject<>();
    private final BrpTestObject<BrpRelatieInhoud> brpH1 = new BrpTestObject<>();

    {
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo3 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo4 = new Lo3TestObject<>(Document.AKTE);

        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(19950104, null), "4A");
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19900204, null), "3A");
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19800202, null), "1A");

        lo4.vul(buildHuwelijk(null, null, 19950103, ROTTERDAM, P), null, 19950103, 19950104, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0));
        lo3.vul(buildHuwelijk(19900131, ROTTERDAM, null, null, P), null, 19900131, 19900204, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 1));
        lo2.vul(buildHuwelijk(19900201, ROTTERDAM, null, null, P), ONJUIST, 19900201, 19900203, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 2));
        lo1.vul(buildHuwelijk(19800201, DENHAAG, null, null, H), null, 19800201, 19800202, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 3));

        // verwachte BRP output

        // soort P
        // 31-1-1990 rotterdam 3-1-1995 Rotterdam - 4-1-1995 1:00 xxxxxxxx Ontbinding-1
        // 31-1-1990 rotterdam xxxxxxxx - - 4-2-1990 1:00 4-1-1995 1:00 Omzetting-1b
        // 1-2-1990 rotterdam xxxxxxxx - - 3-2-1990 1:00 3-2-1990 1:00 Omzetting-1a Omzetting-1a
        brpP1.vulHuwelijk(19900131, ROTTERDAM, null, null, 19900204010000L, 19900204010000L, actie3, actie3);
        brpP2.vulHuwelijk(19900131, ROTTERDAM, 19950103, ROTTERDAM, 19950104010000L, null, actie4, null);

        // soort H
        // 1-2-1980 den haag 31-1-1990 rotterdam Z 4-2-1990 1:00 xxxxxxxx Omzetting-1b
        // 1-2-1980 den haag xxxxxxxx - - 2-2-1980 1:00 4-2-1990 1:00 Sluiting-1a
        brpH1.vulHuwelijk(19800201, DENHAAG, null, null, null, 19800202010000L, 19800202010000L, actie1, actie1);
        brpH2.vulHuwelijk(19800201, DENHAAG, 19900131, ROTTERDAM, 'Z', 19900204010000L, null, actie3, null);

        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4);
    }

    @Override
    protected Lo3Stapel<Lo3HuwelijkOfGpInhoud> maakHuwelijkStapel() {
        return StapelUtils.createStapel(CATEGORIEEN);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3HuwelijkOfGpInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo : testObjecten) {
            CATEGORIEEN.add(new Lo3Categorie<>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo.getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);

        final List<BrpRelatie> huwelijkRelaties = brpPersoonslijst.getRelaties(BrpSoortRelatieCode.HUWELIJK);
        assertEquals(1, huwelijkRelaties.size());
        final BrpStapel<BrpRelatieInhoud> huwelijkStapel = huwelijkRelaties.get(0).getRelatieStapel();

        sorteerBrpStapel(huwelijkStapel);
        assertEquals(2, huwelijkStapel.size());

        assertHuwelijk(huwelijkStapel.get(0), brpH1);
        assertHuwelijk(huwelijkStapel.get(1), brpH2);

        final List<BrpRelatie> partnerRelaties = brpPersoonslijst.getRelaties(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP);
        assertEquals(1, partnerRelaties.size());
        final BrpStapel<BrpRelatieInhoud> partnerStapel = partnerRelaties.get(0).getRelatieStapel();

        sorteerBrpStapel(partnerStapel);
        assertEquals(2, partnerStapel.size());

        assertHuwelijk(partnerStapel.get(0), brpP1);
        assertHuwelijk(partnerStapel.get(1), brpP2);
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
